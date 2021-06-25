package com.ferullogaming.countercraft.client.cloud;

import net.minecraft.util.EnumChatFormatting;

public enum EnumCompRank {
   IRON_1("Iron I", "iron_1", EnumChatFormatting.WHITE),
   IRON_2("Iron II", "iron_2", EnumChatFormatting.WHITE),
   IRON_3("Iron III", "iron_3", EnumChatFormatting.WHITE),
   IRON_MASTER("Iron Master", "iron_master", EnumChatFormatting.WHITE),
   GOLD_1("Gold I", "gold_1", EnumChatFormatting.YELLOW),
   GOLD_2("Gold II", "gold_2", EnumChatFormatting.YELLOW),
   GOLD_3("Gold III", "gold_3", EnumChatFormatting.YELLOW),
   GOLD_MASTER("Gold Master", "gold_master", EnumChatFormatting.YELLOW),
   DIAMOND_1("Diamond I", "diamond_1", EnumChatFormatting.AQUA),
   DIAMOND_2("Diamond II", "diamond_2", EnumChatFormatting.AQUA),
   DIAMOND_3("Diamond III", "diamond_3", EnumChatFormatting.AQUA),
   DIAMOND_MASTER("Diamond Master", "diamond_master", EnumChatFormatting.AQUA),
   OBSIDIAN_1("Obsidian I", "obsidian_1", EnumChatFormatting.LIGHT_PURPLE),
   OBSIDIAN_2("Obsidian II", "obsidian_2", EnumChatFormatting.LIGHT_PURPLE),
   OBSIDIAN_3("Obsidian III", "obsidian_3", EnumChatFormatting.LIGHT_PURPLE),
   OBSIDIAN_MASTER("Obsidian Master", "obsidian_master", EnumChatFormatting.LIGHT_PURPLE),
   ENDER_ELITE("Ender Elite", "ender_elite", EnumChatFormatting.DARK_PURPLE);

   private String text;
   private String resourceName;
   private EnumChatFormatting rankColor;

   private EnumCompRank(String text, String givenResourceName, EnumChatFormatting givenRankColor) {
      this.text = text;
      this.resourceName = givenResourceName;
      this.rankColor = givenRankColor;
   }

   public static EnumCompRank fromString(String text) {
      EnumCompRank[] arr$ = values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         EnumCompRank b = arr$[i$];
         if (b.text.equalsIgnoreCase(text)) {
            return b;
         }
      }

      return null;
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public String getAsName() {
      return this.text;
   }

   public EnumChatFormatting getRankFormatting() {
      return this.rankColor;
   }
}
