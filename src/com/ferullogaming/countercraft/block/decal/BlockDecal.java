package com.ferullogaming.countercraft.block.decal;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDecal extends BlockContainer {
   private String decalName;

   public BlockDecal(int par1, String givenName) {
      super(par1, Material.air);
      this.setBlockUnbreakable();
      this.setCreativeTab(ItemManager.tabCounterCraftDecals);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setUnlocalizedName("decal." + givenName);
      this.decalName = givenName;
   }

   @SideOnly(Side.CLIENT)
   protected String getTextureName() {
      return "countercraft:decal/" + this.decalName;
   }

   public TileEntity createNewTileEntity(World world) {
      return new TileEntityDecal(this.decalName);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      if (l == 0) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
      }

      if (l == 1) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
      }

      if (l == 2) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
      }

      if (l == 3) {
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
      }

   }

   public int getRenderType() {
      return -1;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
      int meta = world.getBlockMetadata(x, y, z);
      if (meta == 2) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
      }

      if (meta == 5) {
         this.setBlockBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
      }

      if (meta == 3) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
      }

      if (meta == 4) {
         this.setBlockBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
   }
}
