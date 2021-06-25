package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRoad extends BlockCC {
   @SideOnly(Side.CLIENT)
   protected Icon blockIconSide;
   @SideOnly(Side.CLIENT)
   protected Icon blockIconTop;
   @SideOnly(Side.CLIENT)
   protected Icon blockIconTop1;
   private String blockType;

   public BlockRoad(int par1, String par2) {
      super(par1);
      this.blockType = par2;
      if (ItemManager.tabCounterCraftBlocks != null) {
         this.setCreativeTab(ItemManager.tabCounterCraftBlocks);
      }

   }

   public void onBlockAdded(World par1World, int par2, int par3, int par4) {
      super.onBlockAdded(par1World, par2, par3, par4);
   }

   public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
      int direction = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
      switch(direction) {
      case 0:
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
         break;
      case 1:
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
         break;
      case 2:
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
         break;
      case 3:
         par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
      }

   }

   @SideOnly(Side.CLIENT)
   public Icon getIcon(int par1, int par2) {
      if (par1 == 1) {
         switch(par2) {
         case 1:
            return this.blockIconTop;
         case 2:
            return this.blockIconTop1;
         case 3:
            return this.blockIconTop;
         case 4:
            return this.blockIconTop1;
         }
      }

      return par1 == 1 ? this.blockIconTop : this.blockIconSide;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.blockIconTop = par1IconRegister.registerIcon("countercraft:road_" + this.blockType);
      this.blockIconTop1 = par1IconRegister.registerIcon("countercraft:road_" + this.blockType + "_1");
      this.blockIconSide = par1IconRegister.registerIcon("countercraft:road");
   }
}
