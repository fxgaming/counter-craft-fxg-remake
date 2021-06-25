package com.ferullogaming.countercraft.item.gun;

public enum EnumFiremode {
   AUTO(1, "AUTO"),
   SEMI(2, "SEMI"),
   BURST(3, "BURST");

   public String displayName;
   public int firemodeID;

   private EnumFiremode(int par1, String par2) {
      this.firemodeID = par1;
      this.displayName = par2;
   }
}
