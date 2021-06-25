package com.ferullogaming.countercraft.game;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IGameComponentBomb {
   boolean canPlantBomb(World var1, EntityPlayer var2, ItemStack var3);

   void onBombPlanted(World var1, EntityPlayer var2);

   void onBombDefused(World var1, String var2);

   void onBombExplodes(World var1, EntityPlayer var2);

   BombPoint getBombPoint(EntityPlayer var1);

   int getBombFuse();

   boolean isBombPlanted();
}
