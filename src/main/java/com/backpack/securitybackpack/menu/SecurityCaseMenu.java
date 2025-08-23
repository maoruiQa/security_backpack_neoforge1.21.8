package com.backpack.securitybackpack.menu;

import com.backpack.securitybackpack.registry.ModMenus;
import com.backpack.securitybackpack.registry.ModAttachments;
import com.backpack.securitybackpack.security.SecurityCaseInventory;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SecurityCaseMenu extends AbstractContainerMenu {
    private final SecurityCaseInventory handler;
    private final int caseSlots;

    // Client-side constructor (reads slot count from extra data)
    public SecurityCaseMenu(int containerId, Inventory playerInv, RegistryFriendlyByteBuf extraData) {
        this(containerId, playerInv, new SecurityCaseInventory(Math.max(1, extraData.readVarInt())));
    }

    // Server-side constructor
    public SecurityCaseMenu(int containerId, Inventory playerInv, SecurityCaseInventory handler) {
        super(ModMenus.SECURITY_CASE_MENU.get(), containerId);
        this.handler = handler;
        this.caseSlots = Math.max(1, handler.getSlots());

        // Security Case: single vertical column on the left
        int leftX = 8;
        int startY = 18;
        for (int i = 0; i < this.caseSlots; i++) {
            addSlot(new SlotItemHandler(this.handler, i, leftX, startY + i * 18));
        }

        // Player inventory (same y baseline as vanilla: 84 for main rows, 142 for hotbar)
        int invX = 80; // shifted to the right to leave room for the left column
        int invY = 84;
        // 3 rows of 9
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, invX + col * 18, invY + row * 18));
            }
        }
        // hotbar
        int hotbarY = 142;
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, invX + col * 18, hotbarY));
        }
    }

    // Convenience factory for server side using player's attachment
    public static SecurityCaseMenu forPlayer(int containerId, Inventory playerInv, Player player) {
        SecurityCaseInventory inv = player.getData(ModAttachments.SECURITY_CASE.get());
        return new SecurityCaseMenu(containerId, playerInv, inv);
    }

    @Override
    public boolean stillValid(Player player) {
        // Always valid for the owning player
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();
            int caseEnd = this.caseSlots; // indices [0, caseSlots)
            int playerInvStart = caseEnd; // next index
            int playerInvEnd = playerInvStart + 27; // 3*9
            int hotbarStart = playerInvEnd;
            int hotbarEnd = hotbarStart + 9;

            if (index < caseEnd) {
                // Move from case -> player inventory + hotbar
                if (!this.moveItemStackTo(stackInSlot, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Move from player -> case
                if (!this.moveItemStackTo(stackInSlot, 0, caseEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }
}
