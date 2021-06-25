package com.ferullogaming.countercraft.client.render;

import com.ferullogaming.countercraft.client.model.ModelGrenade;
import com.ferullogaming.countercraft.entity.EntityGrenade;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderGrenadeFlash extends Render {
   public ModelBase grenadeModel = new ModelGrenade();
   public String grenadeTexture = "grenadeflash";

   public RenderGrenadeFlash() {
   }

   public RenderGrenadeFlash(String par1) {
      this.grenadeTexture = par1;
   }

   public void renderGrenade(EntityGrenade par1Grenade, double par2, double par4, double par6, float par8, float par9) {
      super.renderManager.renderEngine.bindTexture(this.getEntityTexture(par1Grenade));
      GL11.glPushMatrix();
      GL11.glTranslatef((float)par2 - 0.05F, (float)par4 + 0.1F, (float)par6 + 0.05F);
      double scale = 0.04D;
      GL11.glScaled(scale, scale, scale);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      float progress = par1Grenade.lastRotationTick + (par1Grenade.rotationTick - par1Grenade.lastRotationTick) * par9;
      GL11.glRotatef(progress, 1.0F, 0.0F, 1.0F);
      float par7 = 0.625F;
      this.grenadeModel.render(par1Grenade, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.625F);
      GL11.glPopMatrix();
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderGrenade((EntityGrenade)par1Entity, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return new ResourceLocation("countercraft:textures/models/" + this.grenadeTexture + ".png");
   }
}
