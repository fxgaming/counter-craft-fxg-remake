package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.entity.EntityGrenade;
import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMapBarrier extends BlockContainer {
   private Icon barrier1;
   private Icon barrier2;

   public BlockMapBarrier(int par1) {
      super(par1, Material.glass);
      this.setBlockUnbreakable();
      this.setCreativeTab(ItemManager.tabCounterCraft);
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 0;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      TileEntityBarrier tile = (TileEntityBarrier)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
      return tile != null && !tile.canSee ? this.barrier1 : this.barrier2;
   }

   @SideOnly(Side.CLIENT)
   public Icon getIcon(int par1, int par2) {
      return this.barrier2;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.barrier1 = par1IconRegister.registerIcon("countercraft:barrier_1");
      this.barrier2 = par1IconRegister.registerIcon("countercraft:barrier_2");
   }

   public TileEntity createNewTileEntity(World world) {
      return new TileEntityBarrier();
   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
      if (!(par7Entity instanceof EntityGrenade)) {
         AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
         if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1)) {
            par6List.add(axisalignedbb1);
         }

      }
   }
}
