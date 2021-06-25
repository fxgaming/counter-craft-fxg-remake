package com.ferullogaming.countercraft.game;

public enum GameStatus {
   IDLE(1),
   PREGAME(2),
   GAME(3),
   POSTGAME(4);

   public final int id;

   private GameStatus(int par1) {
      this.id = par1;
   }

   public static GameStatus get(int par1) {
      if (par1 == 2) {
         return PREGAME;
      } else if (par1 == 3) {
         return GAME;
      } else {
         return par1 == 4 ? POSTGAME : IDLE;
      }
   }
}
