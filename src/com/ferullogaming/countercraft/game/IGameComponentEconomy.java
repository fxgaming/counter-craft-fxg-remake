package com.ferullogaming.countercraft.game;

import net.minecraft.item.ItemStack;

public interface IGameComponentEconomy {
   int getItemPrice(ItemStack var1, int var2);

   int getItemReward(ItemStack var1, int var2);

   boolean isBuyMenuAdjusted();

   void setPlayerEconomy(String var1, int var2);

   void addPlayerEconomy(String var1, int var2);

   void removePlayerEconomy(String var1, int var2);

   int getPlayerEconomy(String var1);

   boolean hasPlayerEconomy(String var1, int var2);
}
