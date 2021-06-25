package com.ferullogaming.countercraft.client.anim;

import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GunAnimationReloadM249 extends GunAnimationReload {
   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float progress;
      if (par3) {
         progress = super.lastRotation1 + (super.rotation1 - super.lastRotation1) * par2;
         GL11.glRotatef(-progress * 0.4F, 0.0F, 0.0F, 1.0F);
      } else {
         progress = super.lastTrans1 + (super.trans1 - super.lastTrans1) * par2;
         GL11.glTranslatef(-progress, progress, progress);
      }

   }
}
