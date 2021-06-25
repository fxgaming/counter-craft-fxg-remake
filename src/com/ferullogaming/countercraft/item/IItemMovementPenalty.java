package com.ferullogaming.countercraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemMovementPenalty {
   boolean isMovementAffected(EntityPlayer var1, ItemStack var2);

   double getMovementPenalty(ItemStack var1);
}
