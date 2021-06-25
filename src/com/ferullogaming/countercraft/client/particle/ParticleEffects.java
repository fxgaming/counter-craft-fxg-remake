package com.ferullogaming.countercraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ParticleEffects {
   private static Minecraft mc = Minecraft.getMinecraft();
   private static World theWorld;
   private static TextureManager renderEngine;

   public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12) {
      return spawnParticle(particleName, par2, par4, par6, par8, par10, par12, 40.0D);
   }

   public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12, double dis) {
      if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
         int var14 = mc.gameSettings.particleSetting;
         if (var14 == 1 && theWorld.rand.nextInt(3) == 0) {
            var14 = 2;
         }

         double var15 = mc.renderViewEntity.posX - par2;
         double var17 = mc.renderViewEntity.posY - par4;
         double var19 = mc.renderViewEntity.posZ - par6;
         EntityFX var21 = null;
         double dist = var15 * var15 + var17 * var17 + var19 * var19;
         if (dist > dis * dis) {
            return null;
         } else {
            byte var28 = -1;
            switch(particleName.hashCode()) {
            case -1702490258:
               if (particleName.equals("MuzzleSmoke")) {
                  var28 = 1;
               }
               break;
            case 64280026:
               if (particleName.equals("Blood")) {
                  var28 = 0;
               }
               break;
            case 1092518035:
               if (particleName.equals("GroundBlood")) {
                  var28 = 2;
               }
            }

            switch(var28) {
            case 0:
               if (var14 > 1) {
                  return null;
               }

               var21 = new ParticleBlood(theWorld, par2, par4, par6);
               break;
            case 1:
               if (var14 > 1) {
                  return null;
               }

               var21 = new ParticleMuzzleSmoke(theWorld, par2, par4, par6);
               break;
            case 2:
               if (var14 > 1) {
                  return null;
               }

               var21 = new ParticleGroundBlood(theWorld, par2, par4, par6);
            }

            if (var21 != null) {
               mc.effectRenderer.addEffect((EntityFX)var21);
            }

            return (EntityFX)var21;
         }
      } else {
         return null;
      }
   }

   static {
      theWorld = mc.theWorld;
      renderEngine = mc.renderEngine;
   }
}
