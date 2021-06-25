package com.ferullogaming.countercraft.game;

public enum EnumMapTime {
   DAY(0),
   NIGHT(0);

   public int timeID;

   private EnumMapTime(int givenID) {
      this.timeID = givenID;
   }

   public static EnumMapTime getById(int id) {
      EnumMapTime[] arr$ = values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         EnumMapTime e = arr$[i$];
         if (e.timeID == id) {
            return e;
         }
      }

      return DAY;
   }

   public int getTimeID() {
      return this.timeID;
   }
}
