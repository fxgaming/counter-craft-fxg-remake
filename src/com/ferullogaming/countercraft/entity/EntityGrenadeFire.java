package com.ferullogaming.countercraft.entity;

import com.ferullogaming.countercraft.damagesource.DamageSourceCCFire;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityGrenadeFire extends EntityGrenade {
   public boolean initFire = false;
   public int fireFuse = 200;
   public int failedFire = 0;
   public boolean fireSpawned = false;
   private Random rand = new Random();
   private double fireSpread = 0.1D;

   public EntityGrenadeFire(World world) {
      super(world);
      super.fuseLength = -1;
   }

   public EntityGrenadeFire(World par1World, EntityLivingBase par2EntityLiving, double force, int fuseLength) {
      super(par1World, par2EntityLiving, force, fuseLength);
      super.fuseLength = -1;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.initFire) {
         super.firstStopped = true;
         super.lockPosition = true;
         Random rand = new Random();
         if (this.isInsideOfMaterial(Material.water)) {
            this.setDead();
            super.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.0F);
            return;
         }

         if (this.fireFuse % 3 == 0 && !super.worldObj.isRemote) {
            double radius = this.fireSpread;
            AxisAlignedBB axisalignedbb = super.boundingBox.expand(radius, radius, radius);
            List list1 = super.worldObj.getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
            if (!list1.isEmpty()) {
               Iterator i$ = list1.iterator();

               label107:
               while(true) {
                  EntityPlayer player;
                  do {
                     do {
                        do {
                           do {
                              do {
                                 Object obj;
                                 do {
                                    if (!i$.hasNext()) {
                                       break label107;
                                    }

                                    obj = i$.next();
                                 } while(!(obj instanceof EntityPlayer));

                                 player = (EntityPlayer)obj;
                              } while(player.isDead);
                           } while(player.getHealth() <= 0.0F);
                        } while(player.capabilities.isCreativeMode);
                     } while(player.hurtResistantTime > 0);
                  } while(!player.onGround && (double)this.getDistanceToEntity(player) > 1.5D);

                  if (player.canEntityBeSeen(this)) {
                     player.attackEntityFrom(new DamageSourceCCFire(super.field_70192_c), 2.0F);
                     player.hurtResistantTime = 10;
                     player.motionX = player.motionY = player.motionZ = 0.0D;
                  }
               }
            }
         }

         if (this.fireFuse % 2 == 0) {
            this.fireSpread += 0.08D;
            if (this.fireSpread > 3.0D) {
               this.fireSpread = 3.0D;
            }

            if (this.fireFuse % 20 == 0) {
               super.worldObj.playSoundAtEntity(this, "fire.fire", 1.0F, 1.0F);
            }

            for(int k1 = 0; k1 < 20; ++k1) {
               double d7 = rand.nextDouble() * this.fireSpread;
               double d3 = rand.nextDouble() * 3.141592653589793D * 2.0D;
               double d4 = Math.cos(d3) * d7;
               double d5 = rand.nextDouble() * 0.07D;
               double d6 = Math.sin(d3) * d7;
               double px = super.posX + d4;
               double py = super.posY - 0.1D;
               double pz = super.posZ + d6;
               double dyd = 0.0D;

               for(int i = 0; i < 2; ++i) {
                  if (i != 0 && super.worldObj.getBlockId((int)px, (int)(py - (double)i), (int)pz) == 0) {
                     --dyd;
                  }
               }

               String partical = rand.nextInt(3) == 0 ? "smoke" : "flame";
               EntityManager.spawnParticle(partical, px, py + dyd, pz, 0.0D, d5, 0.0D);
            }
         }

         if (this.fireFuse-- <= 0) {
            this.setDead();
            super.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.0F);
            return;
         }
      } else {
         ++this.failedFire;
         if (this.failedFire >= 240) {
            this.setDead();
         }
      }

   }

   public void spawnFire() {
   }

   public void spawnFireParticles() {
      for(int k1 = 0; k1 < 150; ++k1) {
         double d7 = this.rand.nextDouble() * 0.2D;
         double d3 = this.rand.nextDouble() * 3.141592653589793D * 2.0D;
         double d4 = Math.cos(d3) * d7;
         double d5 = 0.01D + this.rand.nextDouble() * 0.2D;
         double d6 = Math.sin(d3) * d7;
         EntityManager.spawnParticle("flame", super.posX + d4 * 0.1D, super.posY + 0.1D, super.posZ + d6 * 0.1D, d4, d5, d6);
      }

   }

   public void onBounce() {
      super.onBounce();
      if (super.motionY == 0.0D && super.onGround && super.worldObj.getBlockId((int)super.posX, (int)super.posY - 1, (int)super.posZ) != 0) {
         super.worldObj.playSoundAtEntity(this, "random.glass", 1.0F, 0.9F);
         this.spawnFireParticles();
         if (!super.worldObj.isRemote && !this.fireSpawned) {
            int radius = 3;
            AxisAlignedBB axisalignedbb = super.boundingBox.expand((double)radius, (double)radius, (double)radius);
            List list1 = super.worldObj.getEntitiesWithinAABB(EntityGrenadeSmoke.class, axisalignedbb);
            if (list1 != null && !list1.isEmpty()) {
               this.fireFuse = 1;
               super.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.0F);
               this.setDead();
            } else {
               FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[0].getGameRules().setOrCreateGameRule("doFireTick", "false");
               this.spawnFire();
               this.fireSpawned = true;
            }
         }

         this.initFire = true;
      }

   }

   public void onFirstBounce() {
   }

   public void explode() {
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
   }
}
