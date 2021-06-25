package com.ferullogaming.countercraft.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class GrenadeExplosion extends Explosion {
   public boolean field_77286_a;
   public boolean field_82755_b = true;
   public double field_77284_b;
   public double field_77285_c;
   public double field_77282_d;
   public Entity field_77283_e;
   public float field_77280_f;
   private int field_77289_h = 16;
   private Random explosionRNG = new Random();
   private World worldObj;

   public GrenadeExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9) {
      super(par1World, par2Entity, par3, par5, par7, par9);
      this.worldObj = par1World;
      this.field_77283_e = par2Entity;
      this.field_77280_f = par9;
      this.field_77284_b = par3;
      this.field_77285_c = par5;
      this.field_77282_d = par7;
   }

   public void doExplosionA() {
      if (!this.worldObj.isRemote) {
         float f = this.field_77280_f;
         new HashSet();
         this.field_77280_f *= 2.0F;
         int i = MathHelper.floor_double(this.field_77284_b - (double)this.field_77280_f - 1.0D);
         int j = MathHelper.floor_double(this.field_77284_b + (double)this.field_77280_f + 1.0D);
         int k = MathHelper.floor_double(this.field_77285_c - (double)this.field_77280_f - 1.0D);
         int l1 = MathHelper.floor_double(this.field_77285_c + (double)this.field_77280_f + 1.0D);
         int i2 = MathHelper.floor_double(this.field_77282_d - (double)this.field_77280_f - 1.0D);
         int j2 = MathHelper.floor_double(this.field_77282_d + (double)this.field_77280_f + 1.0D);
         List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.field_77283_e, AxisAlignedBB.getAABBPool().getAABB((double)i, (double)k, (double)i2, (double)j, (double)l1, (double)j2));
         Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.field_77284_b, this.field_77285_c, this.field_77282_d);

         for(int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = (Entity)list.get(k2);
            double d7 = entity.getDistance(this.field_77284_b, this.field_77285_c, this.field_77282_d) / (double)this.field_77280_f;
            if (d7 <= 1.0D) {
               double d0 = entity.posX - this.field_77284_b;
               double d1 = entity.posY + (double)entity.getEyeHeight() - this.field_77285_c;
               double d2 = entity.posZ - this.field_77282_d;
               double d8 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
               if (d8 != 0.0D) {
                  double var10000 = d0 / d8;
                  var10000 = d1 / d8;
                  var10000 = d2 / d8;
                  double d9 = (double)this.worldObj.getBlockDensity(vec3, entity.boundingBox);
                  double d10 = (1.0D - d7) * d9;
                  if (!(entity instanceof EntityItem)) {
                     entity.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((d10 * d10 + d10) / 2.0D * 8.0D * (double)this.field_77280_f + 1.0D)));
                     entity.motionX = 0.0D;
                     entity.motionY = 0.0D;
                     entity.motionZ = 0.0D;
                  }
               }
            }
         }

         this.field_77280_f = f;
      }
   }

   public void doExplosionB(boolean par1) {
      this.worldObj.playSoundEffect(this.field_77284_b, this.field_77285_c, this.field_77282_d, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      if (this.field_77280_f >= 2.0F && this.field_82755_b) {
         EntityManager.spawnParticle("hugeexplosion", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
      } else {
         EntityManager.spawnParticle("largeexplode", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
      }

      if (this.field_82755_b) {
         Iterator iterator = super.affectedBlockPositions.iterator();

         while(iterator.hasNext()) {
            ChunkPosition chunkposition = (ChunkPosition)iterator.next();
            int i = chunkposition.x;
            int j = chunkposition.y;
            int k = chunkposition.z;
            if (par1) {
               double d0 = (double)((float)i + this.worldObj.rand.nextFloat());
               double d1 = (double)((float)j + this.worldObj.rand.nextFloat());
               double d2 = (double)((float)k + this.worldObj.rand.nextFloat());
               double d3 = d0 - this.field_77284_b;
               double d4 = d1 - this.field_77285_c;
               double d5 = d2 - this.field_77282_d;
               double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
               d3 /= d6;
               d4 /= d6;
               d5 /= d6;
               double d7 = 0.5D / (d6 / (double)this.field_77280_f + 0.1D);
               d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
               d3 *= d7;
               d4 *= d7;
               d5 *= d7;
               EntityManager.spawnParticle("explode", (d0 + this.field_77284_b * 1.0D) / 2.0D, (d1 + this.field_77285_c * 1.0D) / 2.0D, (d2 + this.field_77282_d * 1.0D) / 2.0D, d3, d4, d5);
               EntityManager.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
            }
         }
      }

   }
}
