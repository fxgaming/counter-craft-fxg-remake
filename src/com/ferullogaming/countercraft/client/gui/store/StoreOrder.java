package com.ferullogaming.countercraft.client.gui.store;

public class StoreOrder {
   public int catID;
   public int packageID;
   public int amount;

   public StoreOrder(int par1, int par2, int par3) {
      this.catID = par1;
      this.packageID = par2;
      this.amount = par3;
   }
}
