package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.block.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCounterCraftProps extends CreativeTabs {
   public CreativeTabCounterCraftProps(String label) {
      super(label);
   }

   public ItemStack getIconItemStack() {
      return new ItemStack(BlockManager.propBarrel);
   }
}
