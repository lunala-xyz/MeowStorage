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

import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class ChestMenuScreen extends AbstractContainerScreen<ChestMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MODID, "textures/gui/copper_chest_menu.png");

    private static final int VISIBLE_ROWS = 6;
    private static final int SLOT_COLS = 9;
    private final int totalRows;
    private final int magicScrollNumber = 4;
    private int scrollOffset = magicScrollNumber;

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
        this.imageWidth = 176;
        this.imageHeight = 220;

        this.totalRows = (int)Math.ceil(getInventorySlots() / (float)SLOT_COLS);

        needsScrollbar = Math.max(0, totalRows - VISIBLE_ROWS) > magicScrollNumber;
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

            if (scrollOffset < magicScrollNumber) {
                scrollOffset = magicScrollNumber;
            } else if (scrollOffset > maxOffset) {
                scrollOffset = maxOffset;
            }
        }

        rearrange();

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void rearrange() {
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
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        this.inventoryLabelY = 127;

        if (needsScrollbar) {
            guiGraphics.blit(BACKGROUND, this.leftPos + scrollBarLeft, this.topPos + scrollBarTop, scrollBarLeft, scrollBarTop, scrollBarWidth, scrollBarHeight);

            int floaterOffset = (int) (((scrollOffset - magicScrollNumber) / (double) (totalRows - VISIBLE_ROWS - magicScrollNumber)) * (floaterLowerLimit - floaterUpperLimit));

            if(floaterDragged) {
                floaterOffset = mouseY - (int) (floaterHeight * 1.5);

                if (floaterOffset < 0) {
                    floaterOffset = 0;
                } else if (floaterOffset > 99) {
                    floaterOffset = 99;
                }
            }

            guiGraphics.blit(BACKGROUND, this.leftPos + 181, this.topPos + floaterTop + floaterOffset, floaterLeft, floaterTop, floaterWidth, floaterHeight);
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int floaterOffset = (int) (((scrollOffset - magicScrollNumber) / (double) (totalRows - VISIBLE_ROWS - magicScrollNumber)) * (floaterLowerLimit - floaterUpperLimit));

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
                * (totalRows - VISIBLE_ROWS - magicScrollNumber))
                + magicScrollNumber;

        if (scrollOffset < magicScrollNumber) {
            scrollOffset = magicScrollNumber;
        } else if (scrollOffset > maxOffset) {
            scrollOffset = maxOffset;
        }
    }


    private double getInventorySlots() {
        return menu.slots.size();
    }
}
