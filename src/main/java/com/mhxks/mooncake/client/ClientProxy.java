package com.mhxks.mooncake.client;

import com.mhxks.mooncake.client.render.TESROven;
import com.mhxks.mooncake.common.CommonProxy;
import com.mhxks.mooncake.init.ModBlockLoader;
import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.tools.nsc.doc.model.ModelFactory;

public class ClientProxy
extends CommonProxy {
    @SubscribeEvent
    public void onRenderRegister(ModelRegistryEvent reg){
        registerBlockModel(ModBlockLoader.OVEN);
        //绑定TileEntityOven和TESROven
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TESROven());
    }
    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(item.getRegistryName(),"inventory"));
    }
    public void registerBlockModel(Block block) {
        registerItemModel(Item.getItemFromBlock(block));
    }

}
