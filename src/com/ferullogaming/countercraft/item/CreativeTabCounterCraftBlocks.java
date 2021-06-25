package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.block.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCounterCraftBlocks extends CreativeTabs {
   public CreativeTabCounterCraftBlocks(String label) {
      super(label);
   }

   public ItemStack getIconItemStack() {
      return new ItemStack(BlockManager.roadBroken);
   }
}
