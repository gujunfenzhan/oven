package com.mhxks.mooncake.init;

import com.mhxks.mooncake.MoonCakeMain;
import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntityLoader {
    public ModTileEntityLoader(){
        GameRegistry.registerTileEntity(TileEntityOven.class,new ResourceLocation(MoonCakeMain.mod_id,"oven"));
    }
}
