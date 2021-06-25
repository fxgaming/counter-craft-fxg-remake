package com.ferullogaming.countercraft.client.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class KnifeAnimationInspect extends GunAnimation {
   protected float rotation1 = 0.0F;
   protected float lastRotation1 = 0.0F;
   protected float maxRotation1 = 55.0F;
   protected float trans1 = 0.0F;
   protected float lastTrans1 = 0.0F;
   protected float maxTrans1 = 0.6F;
   protected boolean up = true;
   protected float trans2 = 0.0F;
   protected float lastTrans2 = 0.0F;
   protected float maxTrans2 = 0.6F;
   protected boolean trans2toggle = true;
   protected int trans2pause = 25;

   public void onUpdate(Minecraft par1, EntityPlayer par2, ItemStack par3) {
      if ((double)super.animationTicker > (double)this.getMaxAnimationTick() * 0.8D) {
         this.up = false;
      }

      this.lastRotation1 = this.rotation1;
      this.lastTrans1 = this.trans1;
      this.lastTrans2 = this.trans2;
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

      if (this.up && this.trans1 >= this.maxTrans1) {
         if (this.trans2pause > 0) {
            --this.trans2pause;
         }

         float trans2speed = 15.0F;
         this.maxTrans2 = 140.0F;
         int maxPause = 25;
         if (this.trans2toggle) {
            if (this.trans2pause <= 0) {
               this.trans2 += trans2speed;
            }

            if (this.trans2 >= this.maxTrans2) {
               this.trans2 = this.maxTrans2;
               this.trans2toggle = false;
               this.trans2pause = maxPause;
            }
         } else {
            if (this.trans2pause <= 0) {
               this.trans2 -= trans2speed;
            }

            if (this.trans2 <= 0.0F) {
               this.trans2 = 0.0F;
               this.trans2toggle = true;
               this.trans2pause = maxPause;
            }
         }
      } else if (this.trans2 > 0.0F) {
         this.trans2 -= 25.0F;
         if (this.trans2 < 0.0F) {
            this.trans2 = 0.0F;
         }
      }

   }

   public void doRender(ItemStack par1, float par2) {
      float transprogress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
      GL11.glTranslatef(transprogress * 0.1F, -transprogress * 0.6F, -transprogress * 0.5F);
      float progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
      GL11.glRotatef(-progress * 1.0F, 0.0F, 1.0F, 0.0F);
      float transprogress2 = this.lastTrans2 + (this.trans2 - this.lastTrans2) * par2;
      GL11.glRotatef(transprogress2 * 0.8F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(transprogress2 * 0.0F, transprogress2 * 0.0F, transprogress2 * 0.001F);
   }

   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float transprogress;
      float progress;
      if (par3) {
         transprogress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
         GL11.glTranslatef(transprogress * 0.0F, transprogress * 0.3F, -transprogress * 0.7F);
         progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
         GL11.glRotatef(progress * 0.1F, 0.0F, 0.0F, 1.0F);
         float transprogress2 = this.lastTrans2 + (this.trans2 - this.lastTrans2) * par2;
         GL11.glRotatef(-transprogress2 * 0.1F, 0.0F, 1.0F, 0.0F);
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
      return 140.0F;
   }
}
