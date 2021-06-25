package com.ferullogaming.countercraft.client.render;

import com.ferullogaming.countercraft.entity.EntityPlayerHead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntityPlayerHead extends Render {
   public void renderBullet(EntityPlayerHead par1EntityBullet, double par2, double par4, double par6, float par8, float par9) {
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderBullet((EntityPlayerHead)par1Entity, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return null;
   }
}
