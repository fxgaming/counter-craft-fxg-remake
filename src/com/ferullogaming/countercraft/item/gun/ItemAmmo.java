package com.ferullogaming.countercraft.item.gun;

import com.ferullogaming.countercraft.item.ItemCC;

public class ItemAmmo extends ItemCC {
   public int ammoAmount = 0;

   public ItemAmmo(int par1, int par2) {
      super(par1);
      this.ammoAmount = par2;
      this.setMaxDamage(par2 + 1);
      this.setMaxStackSize(1);
   }
}
