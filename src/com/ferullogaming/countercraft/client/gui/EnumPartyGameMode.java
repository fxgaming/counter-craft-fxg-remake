package com.ferullogaming.countercraft.client.gui;

public enum EnumPartyGameMode {
   COMPETITIVE(1, "Соревновательный", new String[]{"5 на 5, 20 Раундов,", "Поиск и уничтожение ", "матч."}),
   TEAMDEATHMATCH(2, "Командная битва насмерть", new String[]{"8 на 8 Битва насмерть", "до истечения времени."});

   public int id = 0;
   public String displayName;
   public String[] description;

   private EnumPartyGameMode(int par1, String par2, String[] par3) {
      this.id = par1;
      this.displayName = par2;
      this.description = par3;
   }

   public static EnumPartyGameMode getGameMode(int par1) {
      if (par1 == 1) {
         return COMPETITIVE;
      } else {
         return par1 == 2 ? TEAMDEATHMATCH : null;
      }
   }
}
