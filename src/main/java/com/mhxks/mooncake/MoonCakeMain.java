package com.mhxks.mooncake;

import com.mhxks.mooncake.common.CommonProxy;
import com.mhxks.mooncake.init.ModGuiLoader;
import com.mhxks.mooncake.init.ModTileEntityLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
@Mod(name = MoonCakeMain.modname,modid = MoonCakeMain.mod_id,version = MoonCakeMain.version)
public class MoonCakeMain {
    public static final String modname = "MoonCake";
    public static final String mod_id = "mooncake";
    public static final String version = "1.0.0";
    @SidedProxy(serverSide = "com.mhxks.mooncake.common.CommonProxy",clientSide = "com.mhxks.mooncake.client.ClientProxy")
    public static CommonProxy PROXY;
    @Mod.Instance
    public static MoonCakeMain INSTANCE;
    @Mod.EventHandler
    public void pre(FMLPreInitializationEvent event){
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(PROXY);
        //注册TileEntity
        new ModTileEntityLoader();
        //注册GUI
        new ModGuiLoader();

    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

    }
    @Mod.EventHandler
    public void post(FMLPostInitializationEvent event){

    }
}
