package com.mhxks.mooncake.te;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class TileEntityOven
extends TileEntity
implements ITickable {
    public int burnTime = 0;
    public int max_burnTime = 0;
    public int progress = 0;
    public static int PROGRESS = 200;
    public ItemStackHandler itemStackHandler = new ItemStackHandler(7);
    public SlotItemHandler ranliao = new SlotItemHandler(itemStackHandler,0,15,39){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return TileEntityFurnace.isItemFuel(stack) || stack.getItem() == Items.BUCKET;
        }
    };
    public SlotItemHandler slot1 = new SlotItemHandler(itemStackHandler,1,55,15){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return  FurnaceRecipes.instance().getSmeltingResult(stack)!=ItemStack.EMPTY;
        }
    };
    public SlotItemHandler slot2 = new SlotItemHandler(itemStackHandler,2,55,35){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return  FurnaceRecipes.instance().getSmeltingResult(stack)!=ItemStack.EMPTY;
        }
    };
    public SlotItemHandler slot3 = new SlotItemHandler(itemStackHandler,3,55,55){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return  FurnaceRecipes.instance().getSmeltingResult(stack)!=ItemStack.EMPTY;
        }
    };
    public SlotItemHandler slot4 = new SlotItemHandler(itemStackHandler,4,113,15){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };
    public SlotItemHandler slot5 = new SlotItemHandler(itemStackHandler,5,113,35){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };
    public SlotItemHandler slot6 = new SlotItemHandler(itemStackHandler,6,113,55){
        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return false;
        }
    };



    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.burnTime = compound.getInteger("burnTime");
        this.progress = compound.getInteger("progress");
        this.max_burnTime = compound.getInteger("max_burnTime");
        itemStackHandler.deserializeNBT(compound.getCompoundTag("items"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("burnTime",burnTime);
        compound.setInteger("progress",progress);
        compound.setInteger("max_burnTime",max_burnTime);
        compound.setTag("items",itemStackHandler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if(world.isRemote){
            return;
        }
        //补充burnTime
        if(burnTime<=0&&ranliao.getHasStack()&&(slot1.getHasStack()||slot2.getHasStack()||slot3.getHasStack())){
            this.burnTime = TileEntityFurnace.getItemBurnTime(ranliao.getStack());
            this.max_burnTime = burnTime;
            ranliao.getStack().shrink(1);
            this.markDirty();
        }
        if(burnTime>0){
            burnTime--;
            if(slot1.getHasStack()||slot2.getHasStack()||slot3.getHasStack()){
                progress++;
            }
        }
        if(progress>=PROGRESS){
            this.markDirty();
            progress=0;
            if(slot1.getHasStack()){
                ItemStack to = FurnaceRecipes.instance().getSmeltingResult(slot1.getStack());
                if(to.isItemEqual(slot4.getStack())){
                    slot1.getStack().shrink(1);
                    slot4.getStack().grow(1);
                }else if(!slot4.getHasStack()){
                    slot1.getStack().shrink(1);
                    slot4.putStack(to.copy());
                }
            }
            if(slot2.getHasStack()){
                ItemStack to = FurnaceRecipes.instance().getSmeltingResult(slot2.getStack());
                if(to.isItemEqual(slot5.getStack())){
                    slot2.getStack().shrink(1);
                    slot5.getStack().grow(1);
                }else if(!slot5.getHasStack()){
                    slot2.getStack().shrink(1);
                    slot5.putStack(to.copy());
                }
            }
            if(slot3.getHasStack()){
                ItemStack to = FurnaceRecipes.instance().getSmeltingResult(slot3.getStack());
                if(to.isItemEqual(slot6.getStack())){
                    slot3.getStack().shrink(1);
                    slot6.getStack().grow(1);
                }else if(!slot6.getHasStack()){
                    slot3.getStack().shrink(1);
                    slot6.putStack(to.copy());
                }
            }
        }
        if(!slot1.getHasStack()&&!slot2.getHasStack()&&!slot3.getHasStack()){
            progress = 0;
            this.markDirty();
        }

    }
}
