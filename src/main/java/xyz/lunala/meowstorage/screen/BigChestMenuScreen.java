package xyz.lunala.meowstorage.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import xyz.lunala.meowstorage.Meowstorage;
import xyz.lunala.meowstorage.menu.ChestMenu;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BigChestMenuScreen extends AbstractContainerScreen<ChestMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(MODID, "textures/gui/big_chest_menu.png");

    private static final int VISIBLE_ROWS = 6;
    private static final int SLOT_COLS = 9;
    private final int totalRows;
    private int scrollOffset = 4;

    public BigChestMenuScreen(ChestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 220;
        // Calculate total rows (round up)
        this.totalRows = (int)Math.ceil(getInventorySlots() / (float)SLOT_COLS);
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

            Meowstorage.getLogger().info("Scroll offset: " + scrollOffset);

            return true;
        }
        Meowstorage.getLogger().info("Scroll offset: " + scrollOffset);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        renderBackground(guiGraphics);
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        this.inventoryLabelX = this.leftPos + 8;
        this.inventoryLabelY = this.topPos + 127;

        this.titleLabelX = this.leftPos + 8;
        this.titleLabelY = this.topPos + 6;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);

        for (int i = 0; i < (9 * 3 + 9); i++) {
            Slot slot = menu.slots.get(i);
            renderSlotWithOffset(pGuiGraphics, slot.x, slot.y, pPartialTick, slot, pMouseX, pMouseY);
        }

        for (int i = (9 * 3 + 9); i < getInventorySlots(); i++) {
            int row = i / SLOT_COLS;
            int col = i % SLOT_COLS;
            if (row >= scrollOffset && row < scrollOffset + VISIBLE_ROWS) {
                Slot slot = menu.slots.get(i);
                int slotX = 8 + col * 18;
                int slotY = 18 + (row - scrollOffset) * 18;
                // Draw item
                renderSlotWithOffset(pGuiGraphics, slotX, slotY, pPartialTick, slot, pMouseX, pMouseY);
            }
        }

        renderLabels(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderSlotWithOffset(GuiGraphics guiGraphics, int offsetX, int offsetY, float partialTick, Slot slot, int mouseX, int mouseY) {
        offsetX += this.leftPos;
        offsetY += this.topPos;

        if (!slot.hasItem()) {
            renderSlotDetails(guiGraphics, slot, offsetX, offsetY, mouseX, mouseY);
            return;
        }
        ItemStack stack = slot.getItem();
        if (stack.isEmpty()) {
            stack = ItemStack.EMPTY;
        }

        guiGraphics.renderItem(stack, offsetX, offsetY);
        guiGraphics.renderItemDecorations(font, stack, offsetX, offsetY);

        renderSlotDetails(guiGraphics, slot, offsetX, offsetY, mouseX, mouseY);
    }

    private void renderSlotDetails(GuiGraphics guiGraphics, Slot slot, int x, int y, int mouseX, int mouseY) {
        if (isMouseOver(mouseX, mouseY, x, y, 16, 16)) {
            this.hoveredSlot = slot;
            renderSlotHighlight(guiGraphics, x, y, 0);
            renderTooltip(guiGraphics, mouseX, mouseY);
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private double getInventorySlots() {
        return menu.slots.size();
    }
}
