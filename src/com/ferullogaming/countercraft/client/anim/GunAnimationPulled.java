package com.ferullogaming.countercraft.client.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GunAnimationPulled extends GunAnimation {
   private float rotation1 = -180.0F;
   private float lastRotation1 = 0.0F;

   public void onUpdate(Minecraft par1, EntityPlayer par2, ItemStack par3) {
      this.lastRotation1 = this.rotation1;
      this.rotation1 += 35.0F;
      if (this.rotation1 > 0.0F) {
         this.rotation1 = 0.0F;
      }

   }

   public void doRender(ItemStack par1, float par2) {
      float progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
      GL11.glRotatef(progress, 0.0F, 1.0F, 0.0F);
   }

   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
      GL11.glRotatef(-progress * 0.3F, 0.0F, 1.0F, 0.0F);
   }

   public void onAnimationStopped(ItemStack par1) {
   }

   public float getMaxAnimationTick() {
      return 12.0F;
   }
}
