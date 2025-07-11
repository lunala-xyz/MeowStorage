package xyz.lunala.meowstorage.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import net.minecraftforge.items.SlotItemHandler;

import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.menu.ChestMenu;
import xyz.lunala.meowstorage.util.IMovableSlot;

public class ChestMenuScreen extends AbstractContainerScreen<ChestMenu> {
    private static final ResourceLocation BIG_BACKGROUND = ResourceLocation.parse("meowstorage:textures/gui/big_chest_menu.png");
    private static final ResourceLocation SMALL_BACKGROUND = ResourceLocation.parse("meowstorage:textures/gui/small_chest_menu.png");

    private static final int VISIBLE_ROWS = 6;
    private static final int SLOT_COLS = 9;
    private final int totalRows;
    private final int playerInventoryRows = 4;
    private int scrollOffset = 0;

    private boolean needsScrollbar = false;
    private int scrollBarLeft = 177;
    private int scrollBarTop = 3;
    private int scrollBarWidth = 15;
    private int scrollBarHeight = 122;

    private int floaterLeft = 194;
    private int floaterTop = 7;
    private int floaterWidth = 7;
    private int floaterHeight = 15;
    private boolean floaterDragged = false;
    private int floaterUpperLimit = 7;
    private int floaterLowerLimit = 106;

    public ChestMenuScreen(ChestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.totalRows = (int)Math.ceil(getInventorySlots() / (float)SLOT_COLS) - playerInventoryRows;

        Meowstorage.getLogger().info("Total rows: {}", totalRows);

        this.imageWidth = 176;
        this.imageHeight = totalRows > 3 ? 220 : 166;
        this.inventoryLabelY = totalRows > 3 ? 127 : 73;

        needsScrollbar = Math.max(0, totalRows - VISIBLE_ROWS) > 0;
        Meowstorage.getLogger().info(String.valueOf(Math.max(0, totalRows - VISIBLE_ROWS)));
        rearrange();
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int maxOffset = Math.max(0, totalRows - VISIBLE_ROWS);
        if (maxOffset > 0) {
            scrollOffset = scrollOffset - (int)Math.signum(delta);

            if (scrollOffset < playerInventoryRows) {
                scrollOffset = playerInventoryRows;
            } else if (scrollOffset > maxOffset) {
                scrollOffset = maxOffset;
            }
        }

        rearrange();

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void rearrange() {
        if (!needsScrollbar) return;

        for (int i = (9 * 3 + 9); i < getInventorySlots(); i++) {
            int row = i / SLOT_COLS;
            int col = i % SLOT_COLS;
            SlotItemHandler slot = (SlotItemHandler) menu.slots.get(i);

            if(!(slot instanceof IMovableSlot)) {
                Meowstorage.getLogger().error("Expected IMovableSlot but got " + slot.getClass().getCanonicalName());
                Meowstorage.getLogger().error("Game prolly ate the throw!");
                throw new IllegalStateException("Expected IMovableSlot but got " + slot.getClass().getCanonicalName());
            }

            if (row >= scrollOffset && row < scrollOffset + VISIBLE_ROWS) {
                int slotX = 8 + col * 18;
                int slotY = 18 + (row - scrollOffset) * 18;
                ((IMovableSlot) slot).meowstorage$setX(slotX);
                ((IMovableSlot) slot).meowstorage$setY(slotY);
            } else {
                ((IMovableSlot) slot).meowstorage$setX(-1000);
                ((IMovableSlot) slot).meowstorage$setY(-1000);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        renderBackground(guiGraphics);
        guiGraphics.blit(getAppropriateBackground(), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        if (needsScrollbar) {
            guiGraphics.blit(BIG_BACKGROUND, this.leftPos + scrollBarLeft, this.topPos + scrollBarTop, scrollBarLeft, scrollBarTop, scrollBarWidth, scrollBarHeight);

            int floaterOffset = (int) (((scrollOffset - playerInventoryRows) / (double) (totalRows - VISIBLE_ROWS - playerInventoryRows)) * (floaterLowerLimit - floaterUpperLimit));

            if(floaterDragged) {
                floaterOffset = mouseY - (int) (floaterHeight * 1.5);
            }

            if (floaterOffset < 0) {
                floaterOffset = 0;
            } else if (floaterOffset > 99) {
                floaterOffset = 99;
            }

            guiGraphics.blit(BIG_BACKGROUND, this.leftPos + 181, this.topPos + floaterTop + floaterOffset, floaterLeft, floaterTop, floaterWidth, floaterHeight);
        }
    }

    private ResourceLocation getAppropriateBackground() {
        if (totalRows > 3) {
            return BIG_BACKGROUND;
        } else {
            return SMALL_BACKGROUND;
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int floaterOffset = (int) (((scrollOffset - playerInventoryRows) / (double) (totalRows - VISIBLE_ROWS - playerInventoryRows)) * (floaterLowerLimit - floaterUpperLimit));

        if (this.isHovering(181, floaterTop + floaterOffset, floaterWidth, floaterHeight, pMouseX, pMouseY)) {
            floaterDragged = true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        floaterDragged = false;

        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (floaterDragged) {
            updateFloater(pMouseY);
            rearrange();
        }

        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    private void updateFloater(double pMouseY) {
        int maxOffset = Math.max(0, totalRows - VISIBLE_ROWS);

        scrollOffset = (int) Math.round(((
                pMouseY -
                (this.topPos + floaterTop))
                / (double) (floaterLowerLimit - floaterUpperLimit))
                * (totalRows - VISIBLE_ROWS));

        if (scrollOffset < playerInventoryRows) {
            scrollOffset = playerInventoryRows;
        } else if (scrollOffset > maxOffset) {
            scrollOffset = maxOffset;
        }
    }


    private double getInventorySlots() {
        return menu.slots.size();
    }
}
