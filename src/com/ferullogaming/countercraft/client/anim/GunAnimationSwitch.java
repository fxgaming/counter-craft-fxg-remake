package com.ferullogaming.countercraft.client.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GunAnimationSwitch extends GunAnimation {
   protected float rotation1 = 0.0F;
   protected float lastRotation1 = 0.0F;
   protected float maxRotation1 = 55.0F;
   protected float trans1 = 0.0F;
   protected float lastTrans1 = 0.0F;
   protected float maxTrans1 = 0.6F;
   protected boolean up = true;

   public void onUpdate(Minecraft par1, EntityPlayer par2, ItemStack par3) {
      if ((double)super.animationTicker > (double)this.getMaxAnimationTick() * 0.8D) {
         this.up = false;
      }

      this.lastRotation1 = this.rotation1;
      this.lastTrans1 = this.trans1;
      float roation1Speed = 15.0F;
      float transSpeed = 0.15F;
      if (this.up) {
         this.trans1 += transSpeed;
         if (this.trans1 > this.maxTrans1) {
            this.trans1 = this.maxTrans1;
         }

         this.rotation1 += roation1Speed;
      } else {
         this.rotation1 -= roation1Speed;
         this.trans1 -= transSpeed;
         if (this.trans1 < 0.0F) {
            this.trans1 = 0.0F;
         }
      }

      if (this.rotation1 > this.maxRotation1) {
         this.rotation1 = this.maxRotation1;
      }

      if (this.rotation1 < 0.0F) {
         this.rotation1 = 0.0F;
      }

   }

   public void doRender(ItemStack par1, float par2) {
      float transprogress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
      GL11.glTranslatef(transprogress, 0.0F, transprogress);
      float progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
      GL11.glRotatef(-progress, 1.0F, 1.0F, 0.0F);
      GL11.glRotatef(progress / 2.0F, 1.0F, -1.0F, 0.0F);
   }

   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float transprogress;
      float progress;
      if (par3) {
         transprogress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
         GL11.glTranslatef(transprogress * 0.95F, transprogress * 0.4F, 0.0F);
         GL11.glTranslatef(0.0F, 0.04F, 0.1F);
         progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
         GL11.glRotatef(-progress * 0.1F, 0.0F, 0.0F, 1.0F);
      } else {
         transprogress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
         GL11.glTranslatef(-transprogress, 0.0F, transprogress);
         progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
         GL11.glRotatef(progress, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-progress / 2.0F, 1.0F, 0.0F, 0.0F);
      }

   }

   public void onAnimationStopped(ItemStack par1) {
   }

   public float getMaxAnimationTick() {
      return 100.0F;
   }
}
