package com.backpack.securitybackpack.security;

import net.neoforged.neoforge.items.ItemStackHandler;
import net.minecraft.world.item.ItemStack;

public class SecurityCaseInventory extends ItemStackHandler {
    public SecurityCaseInventory(int size) {
        super(size);
    }

    // Safely resize while preserving items
    public void resize(int newSize) {
        if (newSize == this.getSlots()) return;
        ItemStack[] old = new ItemStack[this.getSlots()];
        for (int i = 0; i < old.length; i++) {
            old[i] = this.getStackInSlot(i);
        }
        // rebuild stacks with new size, preserving existing items
        net.minecraft.core.NonNullList<ItemStack> newStacks = net.minecraft.core.NonNullList.withSize(newSize, ItemStack.EMPTY);
        for (int i = 0; i < Math.min(old.length, newSize); i++) {
            newStacks.set(i, old[i]);
        }
        this.stacks = newStacks;
        onContentsChanged(-1);
    }
}
