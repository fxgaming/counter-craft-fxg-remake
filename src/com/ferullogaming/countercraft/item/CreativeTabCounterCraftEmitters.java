package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.block.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCounterCraftEmitters extends CreativeTabs {
   public CreativeTabCounterCraftEmitters(String label) {
      super(label);
   }

   public ItemStack getIconItemStack() {
      return new ItemStack(BlockManager.emitterSoundWind);
   }
}
