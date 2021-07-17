package com.mhxks.mooncake.container;

import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOven
extends Container {
    public TileEntityOven tileEntityOven;
    public ContainerOven(TileEntityOven tileEntityOven,EntityPlayer entityPlayer) {
        this.tileEntityOven = tileEntityOven;
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, k, 8 + k * 18, 142));
        }
        this.addSlotToContainer(tileEntityOven.ranliao);
        this.addSlotToContainer(tileEntityOven.slot1);
        this.addSlotToContainer(tileEntityOven.slot2);
        this.addSlotToContainer(tileEntityOven.slot3);
        this.addSlotToContainer(tileEntityOven.slot4);
        this.addSlotToContainer(tileEntityOven.slot5);
        this.addSlotToContainer(tileEntityOven.slot6);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(tileEntityOven.getPos())<64;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : this.listeners) {
            listener.sendWindowProperty(this,0,this.tileEntityOven.burnTime);
            listener.sendWindowProperty(this,1,this.tileEntityOven.progress);
            listener.sendWindowProperty(this,2,this.tileEntityOven.max_burnTime);
        }
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id){
            case 0:
                this.tileEntityOven.burnTime = data;
            case 1:
                this.tileEntityOven.progress = data;
            case 2:
                this.tileEntityOven.max_burnTime = data;
        }
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();
        boolean isMerged;
        int length = inventorySlots.size() - 36;
        if (index < length) {
            isMerged = mergeItemStack(newStack, length, 36 + length, true);
        } else if (index < 27 + length) {
            isMerged = mergeItemStack(newStack, 0, length, false)
                    || mergeItemStack(newStack, 27 + length, 36 + length, false);
        } else {
            isMerged = mergeItemStack(newStack, 0, length, false)
                    || mergeItemStack(newStack, length, 27 + length, false);
        }
        if (!isMerged) {
            return ItemStack.EMPTY;
        }
        if (newStack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }
        return oldStack;
    }
    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean changed = false;
        int i = reverseDirection ? endIndex - 1 : startIndex;
        if (stack.isStackable()) {
            while (!stack.isEmpty()) {
                if (reverseDirection && i < startIndex)
                    break;
                else if (i >= endIndex)
                    break;
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                if (slot.isItemValid(itemstack) && !itemstack.isEmpty() && itemstack.getItem() == stack.getItem()
                        && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata())
                        && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
                    int j = itemstack.getCount() + stack.getCount();
                    int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
                    if (j <= maxSize) {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        changed = true;
                    } else if (itemstack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.onSlotChanged();
                        changed = true;
                    }
                }
                i += reverseDirection ? -1 : 1;
            }
        }
        if (!stack.isEmpty()) {
            i = reverseDirection ? endIndex - 1 : startIndex;
            while (true) {
                if (reverseDirection && i < startIndex)
                    break;
                else if (i >= endIndex)
                    break;
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                if (itemstack.isEmpty() && slot.isItemValid(stack)) {
                    if (stack.getCount() > slot.getSlotStackLimit())
                        slot.putStack(stack.splitStack(slot.getItemStackLimit(stack)));
                    else
                        slot.putStack(stack.splitStack(stack.getCount()));
                    slot.onSlotChanged();
                    changed = true;
                    break;
                }
                i += reverseDirection ? -1 : 1;
            }
        }
        return changed;
    }
}
