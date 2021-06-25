package com.ferullogaming.countercraft.item.gun;

public class WallBangable {
   public int blockID;
   public double reduction;
   public boolean particels = true;

   public WallBangable(int par1, double par2) {
      this.blockID = par1;
      this.reduction = par2;
   }

   public WallBangable(int par1, double par2, boolean par3) {
      this.blockID = par1;
      this.reduction = par2;
      this.particels = par3;
   }
}
