package com.ferullogaming.countercraft.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityGrenadeSmoke extends EntityGrenade {
   public boolean initSmoke = false;
   public boolean hasBurst = false;
   public int smokeFuse = 300;
   public double radius = 0.2D;
   public double maxRadius = 2.25D;
   String soundBurst = "countercraft:grenadeSmokeBurst";
   private Random rand = new Random();

   public EntityGrenadeSmoke(World world) {
      super(world);
      super.fuseLength = -1;
   }

   public EntityGrenadeSmoke(World par1World, EntityLivingBase par2EntityLiving, double force, int fuseLength) {
      super(par1World, par2EntityLiving, force, fuseLength);
      super.fuseLength = -1;
   }

   public void onUpdate() {
      super.onUpdate();
      super.worldObj.spawnParticle("explode", super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D);
      if (this.initSmoke) {
         if (this.smokeFuse % 10 == 0 && super.worldObj.isRemote && this.radius < this.maxRadius) {
            this.radius += 0.25D;
         }

         if (super.worldObj.isRemote && this.smokeFuse % 4 == 0) {
            for(double x = -this.radius; x < this.radius; ++x) {
               for(double y = -this.radius; y < this.radius; ++y) {
                  for(double z = -this.radius; z < this.radius; ++z) {
                     double mx = (-0.5D + this.rand.nextDouble()) / 4.0D;
                     double my = (-0.5D + this.rand.nextDouble()) / 4.0D;
                     double mz = (-0.5D + this.rand.nextDouble()) / 4.0D;
                     if (!this.hasBurst) {
                        float var10002 = (float)super.posX;
                        float var10003 = (float)super.posY + 0.5F;
                        float var10004 = (float)super.posZ;
                        Minecraft.getMinecraft().sndManager.playSound(this.soundBurst, var10002, var10003, var10004, 3.0F, 1.0F);
                        this.hasBurst = true;
                     }
                  }
               }
            }
         }

         if (this.smokeFuse-- <= 0) {
            this.setDead();
         }
      }

   }

   public void onBounce() {
      super.onBounce();
      boolean fired = false;
      int radius = 3;
      AxisAlignedBB axisalignedbb = super.boundingBox.expand((double)radius, (double)radius, (double)radius);
      List list1 = super.worldObj.getEntitiesWithinAABB(EntityGrenadeFire.class, axisalignedbb);
      if (list1 != null && !list1.isEmpty()) {
         Iterator iterator = list1.iterator();

         while(iterator.hasNext()) {
            EntityGrenadeFire fire = (EntityGrenadeFire)iterator.next();
            if (!fire.isDead) {
               fire.fireFuse = 0;
               fired = true;
            }
         }

         if (fired) {
            super.lockPosition = true;
            super.motionX = super.motionZ = 0.0D;
            super.motionY = 0.0D;
            this.initSmoke = true;
            this.radius = 1.5D;
         }
      }

   }

   public void onMotionStopped() {
      this.initSmoke = true;
   }

   public void explode() {
   }
}
