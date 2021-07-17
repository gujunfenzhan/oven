package com.mhxks.mooncake.client.gui;

import com.mhxks.mooncake.MoonCakeMain;
import com.mhxks.mooncake.container.ContainerOven;
import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiContainerOven
extends GuiContainer {
    public static final ResourceLocation TEXTURES_PATH = new ResourceLocation(MoonCakeMain.mod_id,"textures/gui/oven.png");
    public ContainerOven containerOven;
    public GuiContainerOven(Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.containerOven = (ContainerOven) inventorySlotsIn;
        this.xSize=176;
        this.ySize=166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String title = I18n.format("oven.title");
        this.fontRenderer.drawString(title,(this.xSize-this.fontRenderer.getStringWidth(title))/2,5,0x404040);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F,1.0F,1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURES_PATH);
        int offSetX = (this.width-this.xSize)/2,offsetY = (this.height-this.ySize)/2;
        this.drawTexturedModalRect(offSetX,offsetY,0,0,this.xSize,this.ySize);
        int burnTime = containerOven.tileEntityOven.burnTime;
        int progress = containerOven.tileEntityOven.progress;
        int MAX_BURNTIME = containerOven.tileEntityOven.max_burnTime;


        if(burnTime>0) {
            this.drawTexturedModalRect(offSetX + 79, offsetY + 34, 176, 14, (int) ((float)progress / TileEntityOven.PROGRESS*24), 17);
        }
        if(burnTime>0){
            int shaodiao =14-(int)((float)burnTime/MAX_BURNTIME*14);

            this.drawTexturedModalRect(offSetX+16,offsetY+22+shaodiao,176,shaodiao,14,14-shaodiao);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
