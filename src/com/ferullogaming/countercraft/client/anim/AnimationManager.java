package com.ferullogaming.countercraft.client.anim;

import com.ferullogaming.countercraft.CounterCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class AnimationManager {
   private GunAnimation currentAnimation;
   private GunAnimation nextAnimation;
   private ItemStack lastStack = null;
   private ItemStack stack = null;

   public static AnimationManager instance() {
      return CounterCraft.getClientManager().getTickHandler().getAnimationManager();
   }

   public void onUpdate(Minecraft par1) {
      if (par1.thePlayer != null) {
         this.lastStack = this.stack;
         ItemStack itemstack = par1.thePlayer.getCurrentEquippedItem();
         this.stack = itemstack;
         if (this.lastStack != null && this.stack != null && this.stack.itemID != this.lastStack.itemID) {
            this.cancelCurrentAnimation();
            return;
         }

         if (this.nextAnimation != null) {
            this.currentAnimation = this.nextAnimation;
            this.nextAnimation = null;
         }

         if (this.currentAnimation != null && this.stack == null) {
            this.cancelCurrentAnimation();
            return;
         }

         if (this.stack != null && this.currentAnimation != null) {
            this.currentAnimation.onUpdate(par1, par1.thePlayer, itemstack);
            if (this.currentAnimation != null) {
               ++this.currentAnimation.animationTicker;
               if ((float)this.currentAnimation.animationTicker > this.currentAnimation.getMaxAnimationTick()) {
                  this.currentAnimation.onAnimationStopped(itemstack);
                  this.currentAnimation = null;
               }
            }
         }
      }

   }

   public void setNextGunAnimation(GunAnimation par1) {
      this.nextAnimation = par1;
   }

   public void cancelCurrentAnimation() {
      if (this.currentAnimation != null) {
         this.currentAnimation.onAnimationStopped((ItemStack)null);
         this.currentAnimation = null;
      }

   }

   public void cancelAllAnimations() {
      this.cancelCurrentAnimation();
      this.nextAnimation = null;
   }

   public GunAnimation getCurrentAnimation() {
      return this.currentAnimation;
   }
}
