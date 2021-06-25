package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.item.ItemManager;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;

public class BlockSlabCC extends BlockHalfSlab {
   public BlockSlabCC(int p_i2208_1_, boolean p_i2208_2_, Material p_i2208_3_) {
      super(p_i2208_1_, p_i2208_2_, p_i2208_3_);
      this.setCreativeTab(ItemManager.tabCounterCraftBlocks);
   }

   public String getFullSlabName(int i) {
      return null;
   }
}
