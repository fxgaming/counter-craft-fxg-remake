package com.ferullogaming.countercraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCounterCraft extends CreativeTabs {
   public CreativeTabCounterCraft(String label) {
      super(label);
   }

   public ItemStack getIconItemStack() {
      return new ItemStack(ItemManager.ak47);
   }
}
