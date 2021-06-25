package com.ferullogaming.countercraft.client.cloud.item;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.EnumChatFormatting;

public enum CloudItemRarity {
   DEFAULT(0, "Default", "0x56585c", EnumChatFormatting.GRAY),
   TIER1(1, "Coal", "0x53738a", EnumChatFormatting.DARK_AQUA),
   TIER2(2, "Iron", "0x0082df", EnumChatFormatting.AQUA),
   TIER3(3, "Lapis", "0x5549ad", EnumChatFormatting.DARK_PURPLE),
   TIER4(4, "Gold", "0xac43a1", EnumChatFormatting.LIGHT_PURPLE),
   TIER5(5, "Diamond", "0xe73046", EnumChatFormatting.RED),
   TIER6(6, "Obsidian", "0xffc438", EnumChatFormatting.GOLD);

   private static final Map rarityMapping = new HashMap();
   public int rarityID = 0;
   public String rarityName;
   public String rarityColor;
   public EnumChatFormatting chatColor;

   private CloudItemRarity(int par1, String par2, String par3, EnumChatFormatting par4) {
      this.rarityID = par1;
      this.rarityName = par2;
      this.rarityColor = par3;
      this.chatColor = par4;
   }

   public static CloudItemRarity getEnumFromID(int par1) {
      return (CloudItemRarity)rarityMapping.get(par1);
   }

   public int getColorInteger() {
      return Integer.parseInt(this.rarityColor.replace("0x", ""), 16);
   }

   static {
      CloudItemRarity[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         CloudItemRarity var3 = var0[var2];
         rarityMapping.put(var3.rarityID, var3);
      }

   }
}
