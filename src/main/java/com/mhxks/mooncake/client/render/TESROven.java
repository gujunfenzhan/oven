package com.mhxks.mooncake.client.render;

import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class TESROven
extends TileEntitySpecialRenderer<TileEntityOven> {

    @Override
    public void render(TileEntityOven te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        ItemStack slot1 = te.slot1.getStack();
        ItemStack slot2 = te.slot2.getStack();
        ItemStack slot3 = te.slot3.getStack();
        ItemStack slot4 = te.slot4.getStack();
        ItemStack slot5 = te.slot5.getStack();
        ItemStack slot6 = te.slot6.getStack();
        ItemStack first = slot1.isEmpty()?slot4:slot1;
        ItemStack second = slot2.isEmpty()?slot5:slot2;
        ItemStack third = slot3.isEmpty()?slot6:slot3;
        renderItem(first,itemRenderer,x+0.5,y+0.2,z+0.5,te.burnTime>0);
        renderItem(second,itemRenderer,x+0.5,y+0.3,z+0.5,te.burnTime>0);
        renderItem(third,itemRenderer,x+0.5,y+0.4,z+0.5,te.burnTime>0);
        //托盘
        GlStateManager.pushMatrix();
        GlStateManager.translate(x+0.5,y+0.08,z+0.5);
        GlStateManager.rotate(90,1.0F,0,0);
        if(te.burnTime>0)
        GlStateManager.rotate(System.currentTimeMillis()%1440/4,0,0,1.0F);
        GlStateManager.scale(0.45F,0.45F,0.45F);
        GlStateManager.scale(1.0F,1.0F,0.1F);
        GlStateManager.enableRescaleNormal();
        itemRenderer.renderItem(new ItemStack(Blocks.IRON_BLOCK), ItemCameraTransforms.TransformType.NONE);
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
    //渲染盘子上的物品
    public void renderItem(ItemStack itemStack,RenderItem renderItem,double x,double y,double z,boolean isBurn){
        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y,z);
        if(itemStack.getItem() instanceof ItemBlock){
            GlStateManager.translate(0,-0.06D,0);
        }else{
            GlStateManager.translate(0,-0.08F,0);
        }
        GlStateManager.rotate(90,1.0F,0,0);
        if(isBurn)
        GlStateManager.rotate(System.currentTimeMillis()%1440/4,0,0,1.0F);
        GlStateManager.scale(0.5F,0.5F,0.5F);
        if(itemStack.getItem() instanceof ItemBlock){
           // GlStateManager.translate(0,-0.5,0);
            GlStateManager.scale(0.2F,0.2F,0.2F);
        }
        GlStateManager.enableRescaleNormal();
        if(!itemStack.isEmpty()){
            renderItem.renderItem(itemStack, ItemCameraTransforms.TransformType.NONE);
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
