package com.ferullogaming.countercraft;

public class CCUtils {
   public static String getTickAsTime(int par1) {
      int var1 = par1 / 20 / 60;
      int var2 = par1 / 20 % 60;
      String var3 = var2 < 10 ? "0" : "";
      return var1 + ":" + var3 + var2;
   }
}
