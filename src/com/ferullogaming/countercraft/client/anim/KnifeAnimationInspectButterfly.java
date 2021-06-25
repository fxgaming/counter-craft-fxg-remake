package com.ferullogaming.countercraft.client.anim;

import com.ferullogaming.countercraft.client.ClientVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class KnifeAnimationInspectButterfly extends GunAnimation {
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
      float roation1Speed = 25.0F;
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

         float trans2speed = 10.0F;
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
      float var10000 = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
      float val = (float)(Math.sin((double)(ClientVariables.swing / 10.0F)) / 50.0D);
      float val2 = (float)(Math.sin((double)(ClientVariables.swing / 20.0F)) / 35.0D);
      float val3 = (float)(Math.sin((double)(ClientVariables.swing / 20.0F)) / 35.0D);
      GL11.glTranslated((double)val, (double)val2, (double)val3);
      GL11.glRotatef(ClientVariables.swing * 10.0F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(ClientVariables.swing, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
      float progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
      GL11.glRotatef(progress * 3.1F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(progress * 3.4F, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-progress * 0.2F, 1.0F, 0.0F, 0.0F);
      var10000 = this.lastTrans2 + (this.trans2 - this.lastTrans2) * par2;
      GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.0F, 0.0F, -0.08F);
   }

   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float progress;
      if (par3) {
         GL11.glTranslatef(0.0F, 0.4F, -0.15F);
         progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
         GL11.glRotatef(progress * 0.4F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(progress * 0.2F, 1.0F, 0.0F, 0.0F);
         float var10000 = this.lastTrans2 + (this.trans2 - this.lastTrans2) * par2;
      } else {
         progress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
         GL11.glTranslatef(-progress, 0.0F, progress);
         float progress1 = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
         GL11.glRotatef(progress1, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-progress1 / 2.0F, 1.0F, 0.0F, 0.0F);
      }

   }

   public void onAnimationStopped(ItemStack par1) {
   }

   public float getMaxAnimationTick() {
      return 140.0F;
   }
}
