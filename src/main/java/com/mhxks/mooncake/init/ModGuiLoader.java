package com.mhxks.mooncake.init;

import com.mhxks.mooncake.MoonCakeMain;
import com.mhxks.mooncake.client.gui.GuiContainerOven;
import com.mhxks.mooncake.container.ContainerOven;
import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

public class ModGuiLoader
implements IGuiHandler {
    public static final int OVEN = 0;
    public ModGuiLoader(){
        NetworkRegistry.INSTANCE.registerGuiHandler(MoonCakeMain.INSTANCE,this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case OVEN:
                return new ContainerOven((TileEntityOven) getTe(world,x,y,z),player);
            default:
                return null;
        }
    }
    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID){
            case OVEN:
                return new GuiContainerOven(new ContainerOven((TileEntityOven) getTe(world,x,y,z),player));
            default:
                return null;
        }
    }
    public static TileEntity getTe(World world,int x,int y,int z){
        return world.getTileEntity(new BlockPos(x,y,z));
    }
}
