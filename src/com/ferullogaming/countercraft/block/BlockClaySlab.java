package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockClaySlab extends BlockHalfSlab {
   @SideOnly(Side.CLIENT)
   private Icon[] iconArray;
   private Icon[] iconArray2;
   private boolean isFirst = true;
   private boolean isSecondInit = false;

   public BlockClaySlab(int par1, boolean par2, boolean par3) {
      super(par1, par2, Material.clay);
      this.isFirst = par3;
      if (ItemManager.tabCounterCraftBlocks != null) {
         this.setCreativeTab(ItemManager.tabCounterCraftBlocks);
      }

      Block.useNeighborBrightness[par1] = true;
   }

   public static int getDyeFromBlock(int par0) {
      return ~par0 & 15;
   }

   @SideOnly(Side.CLIENT)
   public Icon getIcon(int par1, int par2) {
      return this.isFirst ? this.iconArray[par2 % 8] : this.iconArray2[par2 % 8];
   }

   public BlockClaySlab setSecond() {
      this.isSecondInit = true;
      return this;
   }

   public int idDropped(int par1, Random par2Random, int par3) {
      return this.isSecondInit ? BlockManager.stainedClaySlab2.blockID : BlockManager.stainedClaySlab.blockID;
   }

   protected ItemStack createStackedBlock(int par1) {
      return this.isSecondInit ? new ItemStack(BlockManager.stainedClaySlab2.blockID, 2, par1) : new ItemStack(BlockManager.stainedClaySlab.blockID, 2, par1);
   }

   public int idPicked(World par1World, int par2, int par3, int par4) {
      return this.isSecondInit ? BlockManager.stainedClaySlab2.blockID : BlockManager.stainedClaySlab.blockID;
   }

   public String getFullSlabName(int par1) {
      if (par1 < 0 || par1 >= ItemDye.dyeColors.length) {
         par1 = 0;
      }

      return super.getUnlocalizedName() + "." + ItemDye.dyeColorNames[par1];
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      if (!super.isDoubleSlab) {
         int j;
         if (this.isFirst) {
            if (par1 != BlockManager.stainedClayDoubleSlab.blockID) {
               for(j = 0; j < 7; ++j) {
                  par3List.add(new ItemStack(par1, 1, j));
               }
            }
         } else if (par1 != BlockManager.stainedClayDoubleSlab.blockID) {
            for(j = 0; j < 8; ++j) {
               par3List.add(new ItemStack(par1, 1, j));
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.iconArray = new Icon[16];
      this.iconArray2 = new Icon[16];
      int i;
      if (this.isFirst) {
         for(i = 0; i < 7; ++i) {
            this.iconArray[i] = par1IconRegister.registerIcon("countercraft:clay/hardened_clay_stained_" + ItemDye.dyeColorNames[getDyeFromBlock(i)].toLowerCase());
         }
      } else {
         for(i = 0; i < 8; ++i) {
            this.iconArray2[i] = par1IconRegister.registerIcon("countercraft:clay/hardened_clay_stained_" + ItemDye.dyeColorNames[getDyeFromBlock(i + 7)].toLowerCase());
         }
      }

   }
}
