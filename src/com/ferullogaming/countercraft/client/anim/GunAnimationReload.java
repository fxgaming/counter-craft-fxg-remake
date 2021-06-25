package com.ferullogaming.countercraft.client.anim;

import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GunAnimationReload extends GunAnimation {
   public float rotation1 = 0.0F;
   public float lastRotation1 = 0.0F;
   public float maxRotation1 = 55.0F;
   public float trans1 = -1.0F;
   public float lastTrans1 = 0.0F;
   public float maxTrans1 = 0.3F;
   public boolean up = true;

   public GunAnimationReload() {
      this.trans1 = -1.0F;
   }

   public void onUpdate(Minecraft par1, EntityPlayer par2, ItemStack par3) {
      this.lastRotation1 = this.rotation1;
      this.lastTrans1 = this.trans1;
      float gunReloadDelay = (float)((ItemGun)par3.getItem()).delayReload / 2.0F;
      float newSpeed = 13.5F - gunReloadDelay;
      if (super.animationTicker == 20) {
         this.up = false;
      }

      float roation1Speed = 13.0F + newSpeed / 2.0F;
      float transSpeed = 0.13F + newSpeed / 100.0F / 2.0F;
      if (this.rotation1 >= this.maxRotation1) {
         this.trans1 += transSpeed;
         if (this.trans1 > 0.0F) {
            this.trans1 = 0.0F;
         }
      }

      if (this.up) {
         this.rotation1 += roation1Speed;
      } else {
         this.rotation1 -= roation1Speed;
      }

      if (this.rotation1 > this.maxRotation1) {
         this.rotation1 = this.maxRotation1;
      }

      if (this.rotation1 < 0.0F) {
         this.rotation1 = 0.0F;
      }

   }

   public void doRender(ItemStack par1, float par2) {
      float progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
      GL11.glRotatef(-progress, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(progress, 1.0F, 0.0F, 0.0F);
   }

   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float progress;
      if (par3) {
         progress = this.lastRotation1 + (this.rotation1 - this.lastRotation1) * par2;
         GL11.glRotatef(-progress * 0.4F, 0.0F, 0.0F, 1.0F);
      } else {
         progress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
         GL11.glTranslatef(-progress, progress, progress);
      }

   }

   public void doRenderAmmo(ItemStack par1, float par2) {
      float transprogress = this.lastTrans1 + (this.trans1 - this.lastTrans1) * par2;
      GL11.glTranslatef(transprogress, -transprogress, 0.0F);
   }

   public void onAnimationStopped(ItemStack par1) {
   }

   public float getMaxAnimationTick() {
      return 40.0F;
   }
}
