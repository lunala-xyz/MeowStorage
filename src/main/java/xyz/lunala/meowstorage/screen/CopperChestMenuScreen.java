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

public class CopperChestMenuScreen extends AbstractContainerScreen<ChestMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MODID, "textures/gui/copper_chest_menu.png");

    private static final int VISIBLE_ROWS = 6;
    private static final int SLOT_COLS = 9;
    private final int totalRows;
    private int scrollOffset = 4;

    public CopperChestMenuScreen(ChestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 220;

        this.totalRows = (int)Math.ceil(getInventorySlots() / (float)SLOT_COLS);

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

            if (scrollOffset < 4) {
                scrollOffset = 4;
            } else if (scrollOffset > maxOffset) {
                scrollOffset = maxOffset;
            }

        }

        rearrange();

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    private void rearrange() {
        Meowstorage.getLogger().info("Rearranging Chest Menu: {}", getInventorySlots());
        for (int i = (9 * 3 + 9); i < getInventorySlots(); i++) {
            int row = i / SLOT_COLS;
            int col = i % SLOT_COLS;
            SlotItemHandler slot = (SlotItemHandler) menu.slots.get(i);

            Meowstorage.getLogger().info("Slot " + i + ": " + slot.toString());

            if(!(slot instanceof IMovableSlot)) {
                Meowstorage.getLogger().error("Expected IMovableSlot but got " + slot.getClass().getCanonicalName());
                Meowstorage.getLogger().error("Game ate the throw!");
                throw new IllegalStateException("Expected IMovableSlot but got " + slot.getClass().getCanonicalName());
            }

            if (row >= scrollOffset && row < scrollOffset + VISIBLE_ROWS) {
                int slotX = 8 + col * 18;
                int slotY = 18 + (row - scrollOffset) * 18;
                ((IMovableSlot) slot).meowstorage$setX(slotX);
                ((IMovableSlot) slot).meowstorage$setY(slotY);
                Meowstorage.getLogger().info("Setting slot " + i + " to position: " + slotX + ", " + slotY);
            } else {
                ((IMovableSlot) slot).meowstorage$setX(-1000);
                ((IMovableSlot) slot).meowstorage$setY(-1000);
                Meowstorage.getLogger().info("Hiding slot " + i + " at position: -1000, -1000");
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        renderBackground(guiGraphics);
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        this.inventoryLabelY = 127;

    }

    private double getInventorySlots() {
        return menu.slots.size();
    }
}
