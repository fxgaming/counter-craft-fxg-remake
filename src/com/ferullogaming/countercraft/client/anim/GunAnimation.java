package com.ferullogaming.countercraft.client.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class GunAnimation {
   public int animationTicker;

   public abstract void onUpdate(Minecraft var1, EntityPlayer var2, ItemStack var3);

   public abstract void doRender(ItemStack var1, float var2);

   public abstract void doRenderHand(ItemStack var1, float var2, boolean var3);

   public boolean renderHand(boolean par1) {
      return true;
   }

   public abstract void onAnimationStopped(ItemStack var1);

   public abstract float getMaxAnimationTick();
}
