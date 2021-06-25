package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockCC extends Block {
   protected String blockTexture = "defaultcdblock";

   public BlockCC(int par1) {
      super(par1, Material.rock);
      if (ItemManager.tabCounterCraftBlocks != null) {
         this.setCreativeTab(ItemManager.tabCounterCraftBlocks);
      }

   }

   public BlockCC(int par1, Material par2) {
      super(par1, par2);
      this.setCreativeTab(ItemManager.tabCounterCraft);
   }

   @SideOnly(Side.CLIENT)
   public Icon getIcon(int par1, int par2) {
      return super.blockIcon;
   }

   public BlockCC setCDTexture(String par1) {
      this.blockTexture = par1;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      super.blockIcon = par1IconRegister.registerIcon("countercraft:" + this.blockTexture);
   }
}
