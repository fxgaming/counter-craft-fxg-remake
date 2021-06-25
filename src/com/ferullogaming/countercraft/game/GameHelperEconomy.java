package com.ferullogaming.countercraft.game;

import java.util.ArrayList;
import java.util.Iterator;

public class GameHelperEconomy {
   public static void setPlayersEconomy(IGameComponentEconomy par1, ArrayList par2, int par3) {
      Iterator i$ = par2.iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         par1.setPlayerEconomy(var1, par3);
      }

   }

   public static void addPlayersEconomy(IGameComponentEconomy par1, ArrayList par2, int par3) {
      Iterator i$ = par2.iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         par1.addPlayerEconomy(var1, par3);
      }

   }

   public static void removePlayersEconomy(IGameComponentEconomy par1, ArrayList par2, int par3) {
      Iterator i$ = par2.iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         par1.removePlayerEconomy(var1, par3);
      }

   }
}
