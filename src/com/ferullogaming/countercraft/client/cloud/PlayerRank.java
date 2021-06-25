package com.ferullogaming.countercraft.client.cloud;

public class PlayerRank {
   public static PlayerRank[] ranksList = new PlayerRank[16];
   public static PlayerRank CHICKEN = new PlayerRank(0, "Chicken", "chicken_1", 0);
   public static PlayerRank PIG = new PlayerRank(1, "Pig", "pig_1", 1000);
   public static PlayerRank COW = new PlayerRank(2, "Cow", "cow_1", 2000);
   public static PlayerRank OCELOT = new PlayerRank(3, "Ocelot", "ocelot_1", 3000);
   public static PlayerRank WOLF = new PlayerRank(4, "Wolf", "wolf_1", 4000);
   public static PlayerRank SPIDER = new PlayerRank(5, "Spider", "spider_2", 6000);
   public static PlayerRank SKELETON = new PlayerRank(6, "Skeleton", "skeleton_2", 7500);
   public static PlayerRank ZOMBIE = new PlayerRank(7, "Zombie", "zombie_2", 9000);
   public static PlayerRank CREEPER = new PlayerRank(8, "Creeper", "creeper_2", 10500);
   public static PlayerRank ENDERMAN = new PlayerRank(9, "Enderman", "enderman_2", 12000);
   public static PlayerRank BLAZE = new PlayerRank(10, "Blaze", "blaze_3", 14000);
   public static PlayerRank GHAST = new PlayerRank(11, "Ghast", "ghast_3", 16000);
   public static PlayerRank WITHER = new PlayerRank(12, "Wither", "wither_3", 18000);
   public static PlayerRank ENDERDRAGON = new PlayerRank(13, "Dragon", "dragon_3", 28000);
   private int id;
   private String title;
   private String texture;
   private String textureBack;
   private int expRequired = 0;

   public PlayerRank(int par1, String par2, String par3, int par4) {
      ranksList[par1] = this;
      this.id = par1;
      this.title = par2;
      this.texture = par3.split("_")[0];
      this.textureBack = par3.split("_")[1];
      this.expRequired = par4;
   }

   public static PlayerRank getNextRank(int par1) {
      return ranksList[par1 + 1];
   }

   public static PlayerRank getRankFromID(int par1) {
      PlayerRank[] arr$ = ranksList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         PlayerRank rank = arr$[i$];
         if (rank != null && rank.getID() == par1) {
            return rank;
         }
      }

      return null;
   }

   public static int getEXPForNextRank(int par1) {
      int added = 0;
      PlayerRank rank = getRankFromEXP(par1);

      for(int i = 0; i < ranksList.length; ++i) {
         if (ranksList[i] != null && ranksList[i].getID() <= rank.getID() + 1) {
            added += ranksList[i].expRequired;
         }
      }

      return added - par1;
   }

   public static PlayerRank getRankFromEXP(int par1) {
      int added = 0;

      for(int i = 0; i < ranksList.length; ++i) {
         PlayerRank rank = ranksList[i];
         if (rank != null) {
            added += rank.getEXPRequired();
            if (par1 < added) {
               return ranksList[i - 1];
            }
         }
      }

      if (par1 > added) {
         return ENDERDRAGON;
      } else {
         return CHICKEN;
      }
   }

   public int getID() {
      return this.id;
   }

   public String getTitle() {
      return this.title;
   }

   public String getTexture() {
      return this.texture;
   }

   public String getTextureBackground() {
      return this.textureBack;
   }

   public int getEXPRequired() {
      return this.expRequired;
   }
}
