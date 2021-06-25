package com.ferullogaming.countercraft.client.cloud;

public enum PunishmentType {
   MUTE("Muted"),
   WARNING("Warned", true),
   BAN_COMP("Competitive Cooldown"),
   BAN_MM("AC Banned"),
   BAN_TRADING("Trade Banned"),
   BAN_CLOUD("Cloud Banned");

   public String displayName;
   public boolean isOverlapping = false;

   private PunishmentType(String par1) {
      this.displayName = par1;
   }

   private PunishmentType(String par1, boolean par2) {
      this.displayName = par1;
      this.isOverlapping = par2;
   }

   public static PunishmentType getFromString(String par1) {
      PunishmentType[] arr$ = values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         PunishmentType var1 = arr$[i$];
         if (var1.toString().equalsIgnoreCase(par1)) {
            return var1;
         }
      }

      return null;
   }
}
