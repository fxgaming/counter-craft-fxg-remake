package com.ferullogaming.countercraft.item;

public class ItemPowerup extends ItemCC {
   public String gicon;

   public ItemPowerup(int par1) {
      super(par1);
   }

   public ItemPowerup setGameIcon(String par1) {
      this.gicon = par1;
      return this;
   }
}
