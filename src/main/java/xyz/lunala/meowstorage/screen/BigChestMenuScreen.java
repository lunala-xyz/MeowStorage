package xyz.lunala.meowstorage.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import xyz.lunala.meowstorage.menu.ChestMenu;
import static xyz.lunala.meowstorage.Meowstorage.MODID;

public class BigChestMenuScreen extends AbstractContainerScreen<ChestMenu> {
    private static final ResourceLocation background = new ResourceLocation(MODID, "textures/gui/big_chest_menu.png");

    public BigChestMenuScreen(ChestMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 220;
        this.inventoryLabelY = 127;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        renderBackground(guiGraphics);
        guiGraphics.blit(background, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
