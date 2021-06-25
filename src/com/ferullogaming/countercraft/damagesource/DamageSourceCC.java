package com.ferullogaming.countercraft.damagesource;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class DamageSourceCC extends DamageSource {
   protected DamageSourceCC(String par1Str) {
      super(par1Str);
   }

   public int getDamageID() {
      return 0;
   }

   public ItemStack getWeaponUsed() {
      return null;
   }
}
