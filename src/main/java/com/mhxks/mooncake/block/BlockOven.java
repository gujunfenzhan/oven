package com.mhxks.mooncake.block;

import com.mhxks.mooncake.MoonCakeMain;
import com.mhxks.mooncake.init.ModGuiLoader;
import com.mhxks.mooncake.te.TileEntityOven;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BlockOven
extends BlockContainer {
    public static final PropertyInteger FACING = PropertyInteger.create("facing",0,3);
    //两个状态的碰撞箱,对应烤箱的东西方向
    public static AxisAlignedBB OVEN_AABB = new AxisAlignedBB(2/16D,0,0,14/16D,9/16D,1);
    public static AxisAlignedBB OVEN_AABB2 = new AxisAlignedBB(0,0,2/16D,1,9/16D,14/16D);
    public BlockOven() {
        super(Material.ROCK);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.fullBlock = false;
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(FACING,0));
        this.setHardness(1.0F);
        //没有写破坏等级，自己写上即可

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityOven();
    }
    /*
    如果继承的是BlockContainer需要覆写掉getRenderType
     */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityOven){
                TileEntityOven tileEntityOven = (TileEntityOven) tileEntity;
                playerIn.openGui(MoonCakeMain.INSTANCE, ModGuiLoader.OVEN,worldIn,pos.getX(),pos.getY(),pos.getZ());
            }
        }
        return true;
    }
    //放置方块的时候使用不同的IBlockstate
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        if(facing== EnumFacing.NORTH) {
            worldIn.setBlockState(pos, this.blockState.getBaseState().withProperty(FACING, 0));
        }else if(facing==EnumFacing.SOUTH) {
            worldIn.setBlockState(pos, this.blockState.getBaseState().withProperty(FACING, 1));
        }else if(facing==EnumFacing.WEST) {
            worldIn.setBlockState(pos, this.blockState.getBaseState().withProperty(FACING, 2));
        }else if(facing==EnumFacing.EAST) {
            worldIn.setBlockState(pos, this.blockState.getBaseState().withProperty(FACING, 3));
        }else
            super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    //写着，不写好像要崩
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING);
    }
    //写着，不写好像要崩
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.blockState.getBaseState().withProperty(FACING, meta);
    }
    //写着，不写好像要崩
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,new IProperty[]{FACING});
    }
    //返回两个形态的碰撞箱
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int meta = getMetaFromState(state);
        if (meta==0||meta==1){
            return OVEN_AABB2;
        }else{
            return OVEN_AABB;
        }
    }
    /*
    isOpaqueCube()（是否为透视方块） 和isFullCube()（是否为完整方块）。
    对于前者，除非为某些十分特殊的方块（如透视方块（作弊警告！）），请将它的返回值设置为false，
    否则会出现地形透视的渲染问题。对于后者，只要我们的模型不是一个完整的1x1x1的方块，就请将它的返回值设置为false。
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    /*
该函数只能在客户端上运行，因此你需要在函数前加上 @SideOnly(Side.CLIENT) 的注解。该函数的返回值是一种叫做BlockRenderLayer的Minecraft自带类型。其中BlockRenderLayer.CUTOUT_MIPPED为类似树叶这样材质仅覆盖方块的一部分的方块，BlockRenderLayer.CUTOUT为材质中有光线可以透过部分的方块，BlockRenderLayer.TRANSLUCENT为材质中有 半透明部分（光线可以透过一部分） 的方块。
     */
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
    //破坏之后的掉落
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityOven)
            {

                TileEntityOven tileEntityOven = (TileEntityOven) tileentity;
                ItemStackHandler itemStackHandler = tileEntityOven.itemStackHandler;
                for (int i = 0; i < itemStackHandler.getSlots(); i++) {

                    worldIn.spawnEntity(new EntityItem(worldIn,pos.getX(),pos.getY(),pos.getZ(),itemStackHandler.getStackInSlot(i)));
                }

        }

        super.breakBlock(worldIn, pos, state);
    }

}
