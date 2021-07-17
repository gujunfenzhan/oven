package com.mhxks.mooncake.common;

import com.mhxks.mooncake.init.ModBlockLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {
    @SubscribeEvent
    public void onPlayerItemRegister(RegistryEvent.Register<Item> reg){
        registerItemBlock(reg,ModBlockLoader.OVEN);
    }
    @SubscribeEvent
    public void onPlayerBlockRegister(RegistryEvent.Register<Block> reg){
        reg.getRegistry().register(ModBlockLoader.OVEN);

    }
    public void registerItemBlock(RegistryEvent.Register<Item> reg,Block block){
        reg.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
}
