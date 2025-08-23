package com.backpack.securitybackpack.client.screen;

import com.backpack.securitybackpack.menu.SecurityCaseMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class SecurityCaseScreen extends AbstractContainerScreen<SecurityCaseMenu> {
    private static final ResourceLocation INVENTORY_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/inventory.png");

    public SecurityCaseScreen(SecurityCaseMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        // Widen the GUI to make room for the left column
        this.imageWidth = 248; // 176 default + ~72px for left column spacing
        this.imageHeight = 166; // vanilla inventory height
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partialTick, int mouseX, int mouseY) {
        // Draw vanilla inventory background aligned to the right portion
        int left = this.leftPos;
        int top = this.topPos;

        // Draw a simple panel for the left column (security case)
        // Panel width roughly 72px to match the slot column spacing used in the menu
        int leftPanelWidth = this.imageWidth - 176; // extra width we added
        if (leftPanelWidth < 0) leftPanelWidth = 0;
        // Fill panel background with a semi-transparent dark color
        gfx.fill(left, top, left + leftPanelWidth, top + this.imageHeight, 0x88000000);

        // Subtle separator line between the left panel and the vanilla inventory
        if (leftPanelWidth > 0) {
            gfx.fill(left + leftPanelWidth - 1, top, left + leftPanelWidth, top + this.imageHeight, 0x66FFFFFF);
        }

        // Highlight usable slots in the Security Case (all slots placed in the left panel)
        // We consider slots with x-position within the left panel width as Security Case slots
        for (Slot slot : this.menu.slots) {
            if (slot.x < leftPanelWidth) {
                int sx = left + slot.x;
                int sy = top + slot.y;
                int pad = 1; // small padding around the 16x16 slot area
                int w = 16;
                int h = 16;
                int x0 = sx - pad;
                int y0 = sy - pad;
                int x1 = sx + w + pad;
                int y1 = sy + h + pad;
                // Bright, semi-transparent fill to make usable slots stand out
                gfx.fill(x0, y0, x1, y1, 0x66FFD54F); // amber highlight
                // Slightly stronger border
                int border = 0xAAFFD54F;
                // top
                gfx.fill(x0, y0, x1, y0 + 1, border);
                // bottom
                gfx.fill(x0, y1 - 1, x1, y1, border);
                // left
                gfx.fill(x0, y0, x0 + 1, y1, border);
                // right
                gfx.fill(x1 - 1, y0, x1, y1, border);
            }
        }

        // Draw the standard inventory texture on the right side
        // We draw it starting at left + extra to align the player inventory slots defined in the menu
        gfx.blit(INVENTORY_TEXTURE, left + leftPanelWidth, top, 0, 0, 176, 166, 256, 256);
    }

    @Override
    protected void renderLabels(GuiGraphics gfx, int mouseX, int mouseY) {
        // Title on the left panel
        gfx.drawString(this.font, this.title, 8, 6, 0x404040, false);
        // Player inventory title is drawn by vanilla at y=72 normally; align to the right section
        int rightOffset = this.imageWidth - 176;
        gfx.drawString(this.font, this.playerInventoryTitle, rightOffset + 8, 72, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gfx, mouseX, mouseY, partialTick);
        super.render(gfx, mouseX, mouseY, partialTick);
        this.renderTooltip(gfx, mouseX, mouseY);
    }
}
