package com.mhxks.mooncake.init;

import com.mhxks.mooncake.block.BlockOven;
import net.minecraft.block.Block;

public interface ModBlockLoader {
    Block OVEN = new BlockOven().setRegistryName("oven").setUnlocalizedName("oven");
}
