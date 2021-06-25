package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.block.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCounterCraftDecals extends CreativeTabs {
   public CreativeTabCounterCraftDecals(String label) {
      super(label);
   }

   public ItemStack getIconItemStack() {
      return new ItemStack(BlockManager.decalSiteA);
   }
}
