package com.ferullogaming.countercraft.client.particle;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ParticleMuzzleSmoke extends EntityFX {
   private int index = 1;
   private float alpha = 1.0F;

   public ParticleMuzzleSmoke(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
      Random rand = new Random();
      super.motionX *= 0.800000011920929D;
      super.motionY = 0.0D;
      super.motionZ *= 0.800000011920929D;
      super.motionY = (double)(super.rand.nextFloat() * 0.4F + 0.05F);
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleScale *= super.rand.nextFloat() * 2.0F + 0.2F;
      super.noClip = true;
      super.particleMaxAge = 150;
      this.index = 1 + rand.nextInt(4);
      this.alpha = 0.0F;
   }

   public int getBrightnessForRender(float par1) {
      float f1 = ((float)super.particleAge + par1) / (float)super.particleMaxAge;
      if (f1 < 0.0F) {
         f1 = 0.0F;
      }

      if (f1 > 1.0F) {
         f1 = 1.0F;
      }

      int i = super.worldObj != null ? super.getBrightnessForRender(par1) : 0;
      short short1 = 240;
      int j = i >> 16 & 255;
      return short1 | j << 16;
   }

   public int getFXLayer() {
      return 3;
   }

   public float getBrightness(float par1) {
      return 1.0F;
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      Minecraft mc = Minecraft.getMinecraft();
      if (mc.gameSettings.fancyGraphics) {
         GL11.glPushMatrix();
         GL11.glPushAttrib(8192);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         if (super.particleAge < 5) {
            this.alpha += 0.012F;
         } else {
            this.alpha -= 0.014F;
         }

         GL11.glDepthMask(false);
         CCRenderHelper.drawPositionedImageInViewWithDepth(new ResourceLocation("countercraft", "textures/particle/muzzlesmoke" + this.index + ".png"), super.posX, super.posY + 0.5D, super.posZ, 1.0F, 50.0F, 50.0F, this.alpha);
         GL11.glDepthMask(true);
         GL11.glPopAttrib();
         GL11.glPopMatrix();
      }
   }

   public void onUpdate() {
      if (super.worldObj != null) {
         super.prevPosX = super.posX;
         super.prevPosY = super.posY;
         super.prevPosZ = super.posZ;
         if (super.particleAge++ >= super.particleMaxAge) {
            this.setDead();
         }

         super.motionX *= 0.9990000128746033D;
         super.motionY *= 0.9990000128746033D;
         super.motionZ *= 0.9990000128746033D;
         float f = (float)super.particleAge / (float)super.particleMaxAge;
         super.motionY = 0.03D;
         this.moveEntity(super.motionX / 40.0D, super.motionY, super.motionZ / 40.0D);
         if (super.onGround) {
            super.motionX *= 0.699999988079071D;
            super.motionZ *= 0.699999988079071D;
         }
      }

   }
}
