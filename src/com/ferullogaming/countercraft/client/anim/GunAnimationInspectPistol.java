package com.ferullogaming.countercraft.client.anim;

import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GunAnimationInspectPistol extends GunAnimationInspect {
   public void doRenderHand(ItemStack par1, float par2, boolean par3) {
      float transprogress;
      float progress;
      if (par3) {
         transprogress = super.lastTrans1 + (super.trans1 - super.lastTrans1) * par2;
         GL11.glTranslatef(transprogress * 0.2F, transprogress * 0.2F, -transprogress * 0.6F);
         progress = super.lastRotation1 + (super.rotation1 - super.lastRotation1) * par2;
         GL11.glRotatef(-progress * 0.1F, 0.0F, 0.0F, 1.0F);
      } else {
         transprogress = super.lastTrans1 + (super.trans1 - super.lastTrans1) * par2;
         GL11.glTranslatef(-transprogress, 0.0F, -transprogress);
         progress = super.lastRotation1 + (super.rotation1 - super.lastRotation1) * par2;
         GL11.glRotatef(progress, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-progress / 2.0F, 1.0F, 0.0F, 0.0F);
      }

   }
}
