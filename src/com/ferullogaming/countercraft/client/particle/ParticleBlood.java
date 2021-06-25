package com.ferullogaming.countercraft.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ParticleBlood extends EntityFX {
   private float lavaParticleScale;

   public ParticleBlood(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
      super.motionX *= 0.800000011920929D;
      super.motionY *= 0.800000011920929D;
      super.motionZ *= 0.800000011920929D;
      super.motionY = (double)(super.rand.nextFloat() * 0.6F + 0.05F);
      super.particleRed = super.particleGreen = super.particleBlue = 1.0F;
      super.particleScale *= super.rand.nextFloat() * 2.0F + 0.2F;
      this.lavaParticleScale = super.particleScale;
      super.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      super.noClip = false;
   }

   public int getFXLayer() {
      return 3;
   }

   public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("countercraft", "textures/particle/blood.png"));
      float ff = ((float)super.particleAge + par2) / (float)super.particleMaxAge;
      super.particleScale = this.lavaParticleScale * (1.0F - ff * ff);
      float f6 = (float)super.particleTextureIndexX / 16.0F;
      float f7 = f6 + 0.0624375F;
      float f8 = (float)super.particleTextureIndexY / 16.0F;
      float f9 = f8 + 0.0624375F;
      float f10 = 0.1F * super.particleScale / 3.0F;
      float f11 = (float)(super.prevPosX + (super.posX - super.prevPosX) * (double)par2 - EntityFX.interpPosX);
      float f12 = (float)(super.prevPosY + (super.posY - super.prevPosY) * (double)par2 - EntityFX.interpPosY);
      float f13 = (float)(super.prevPosZ + (super.posZ - super.prevPosZ) * (double)par2 - EntityFX.interpPosZ);
      float f14 = 1.0F;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      par1Tessellator.startDrawingQuads();
      if (super.particleIcon != null) {
         f6 = super.particleIcon.getMinU();
         f7 = super.particleIcon.getMaxU();
         f8 = super.particleIcon.getMinV();
         f9 = super.particleIcon.getMaxV();
      }

      par1Tessellator.setColorRGBA_F(super.particleRed * f14, super.particleGreen * f14, super.particleBlue * f14, super.particleAlpha);
      par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 - par5 * f10 - par7 * f10), (double)f7, (double)f9);
      par1Tessellator.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 - par5 * f10 + par7 * f10), (double)f7, (double)f8);
      par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * f10), (double)(f13 + par5 * f10 + par7 * f10), (double)f6, (double)f8);
      par1Tessellator.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * f10), (double)(f13 + par5 * f10 - par7 * f10), (double)f6, (double)f9);
      par1Tessellator.draw();
      GL11.glPolygonOffset(0.0F, 0.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png"));
   }

   public void onUpdate() {
      if (super.worldObj != null) {
         super.prevPosX = super.posX;
         super.prevPosY = super.posY;
         super.prevPosZ = super.posZ;
         if (super.particleAge++ >= super.particleMaxAge) {
            this.setDead();
         }

         float f = (float)super.particleAge / (float)super.particleMaxAge;
         super.motionY -= 0.06D;
         this.moveEntity(super.motionX, super.motionY, super.motionZ);
         super.motionX *= 0.9990000128746033D;
         super.motionY *= 0.9990000128746033D;
         super.motionZ *= 0.9990000128746033D;
         if (super.onGround) {
            super.motionX *= 0.699999988079071D;
            super.motionZ *= 0.699999988079071D;
         }
      }

   }
}
