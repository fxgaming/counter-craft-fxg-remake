package com.ferullogaming.countercraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable {
   public float damage;
   public int fuse;
   public int fuseLength;
   public float lastRotationTick;
   public float rotationTick;
   public float bounceFactor;
   public boolean exploded;
   public boolean firstBounce;
   public boolean firstStopped;
   public boolean lockPosition;
   public EntityPlayer field_70192_c;
   String soundExplode;

   public EntityGrenade(World par1World, EntityLivingBase par2EntityLiving, double force, int fuseLength) {
      this(par1World);
      this.field_70192_c = (EntityPlayer)par2EntityLiving;
      double forceUp = force;
      int playerY = (int)(par2EntityLiving.motionY * 100.0D) + 7;
      if (playerY < 0) {
         forceUp = force + 0.3D;
      }

      this.applyForces(par1World, par2EntityLiving.posX, par2EntityLiving.posY + 1.5D, par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch, force, forceUp);
      this.fuseLength = fuseLength;
   }

   public EntityGrenade(World world) {
      super(world);
      this.fuse = 0;
      this.fuseLength = 28;
      this.lastRotationTick = 0.0F;
      this.rotationTick = 0.0F;
      this.bounceFactor = 0.25F;
      this.firstBounce = true;
      this.firstStopped = false;
      this.lockPosition = true;
      this.soundExplode = "countercraft:grenadeExplode";
      this.setSize(0.18F, 0.18F);
      super.yOffset = super.height / 2.0F;
      this.fuse = 0;
      this.fuseLength = 28;
   }

   public EntityGrenade(World par1World, double par2, double par4, double par6) {
      super(par1World, par2, par4, par6);
      this.fuse = 0;
      this.fuseLength = 28;
      this.lastRotationTick = 0.0F;
      this.rotationTick = 0.0F;
      this.bounceFactor = 0.25F;
      this.firstBounce = true;
      this.firstStopped = false;
      this.lockPosition = true;
      this.soundExplode = "countercraft:grenadeExplode";
   }

   public void applyForces(World world, double x, double y, double z, float yaw, float pitch, double force, double forceUp) {
      this.setRotation(yaw, 0.0F);
      double xHeading = (double)(-MathHelper.sin(yaw * 3.141593F / 180.0F));
      double zHeading = (double)MathHelper.cos(yaw * 3.141593F / 180.0F);
      if (forceUp == 0.0D && force > 0.0D) {
         forceUp = force;
      }

      super.motionX += force * xHeading * (double)MathHelper.cos(pitch / 180.0F * 3.141593F);
      super.motionY += -forceUp * (double)MathHelper.sin(pitch / 180.0F * 3.141593F);
      super.motionZ += force * zHeading * (double)MathHelper.cos(pitch / 180.0F * 3.141593F);
      double yincrease = (double)(-((int)pitch));
      yincrease = yincrease * 2.0D / 90.0D;
      this.setPosition(x + xHeading * 0.8D, y + yincrease, z + zHeading * 0.8D);
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      double prevVelX = super.motionX;
      double prevVelY = super.motionY;
      double prevVelZ = super.motionZ;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      if (super.motionX != prevVelX) {
         this.onBounce();
         super.motionX = (double)(-this.bounceFactor) * prevVelX;
      }

      if (super.motionY != prevVelY) {
         this.onBounce();
         super.motionY = (double)(-this.bounceFactor) * prevVelY;
      }

      if (super.motionZ != prevVelZ) {
         this.onBounce();
         super.motionZ = (double)(-this.bounceFactor) * prevVelZ;
      } else {
         super.motionY -= 0.04D;
      }

      super.motionX *= 0.9800000190734863D;
      super.motionY *= 0.9800000190734863D;
      super.motionZ *= 0.9800000190734863D;
      if (super.onGround) {
         super.motionX *= 0.699999988079071D;
         super.motionZ *= 0.699999988079071D;
         super.motionY *= 1.699999988079071D;
      }

      if (super.onGround && super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ < 0.02D) {
         super.motionX = 0.0D;
         super.motionY = 0.0D;
         super.motionZ = 0.0D;
      }

      if (super.onGround && super.motionX == 0.0D && super.motionY == 0.0D && super.motionZ == 0.0D) {
         this.firstStopped = true;
         this.onMotionStopped();
      }

      if (this.firstStopped && this.lockPosition) {
         super.motionX = 0.0D;
         super.motionY = 0.0D;
         super.motionZ = 0.0D;
      }

      if (super.worldObj.isRemote && !super.onGround && super.motionX != 0.0D && super.motionY != 0.0D && super.motionZ != 0.0D) {
         int rotationTicks = 60;

         for(int i = 0; i < rotationTicks; ++i) {
            this.lastRotationTick = this.rotationTick;
            this.rotationTick += 0.5F;
         }
      }

      if (this.fuseLength != -1 && this.fuse++ >= this.fuseLength) {
         this.explode();
      }

   }

   public void onFirstBounce() {
   }

   public void onMotionStopped() {
   }

   public void explode() {
      if (!this.exploded) {
         super.worldObj.playSoundAtEntity(this, this.soundExplode, 4.0F, 1.0F);
         this.exploded = true;
         this.createExplosion(this.field_70192_c, super.posX, super.posY, super.posZ, 2.75F);
      }

      this.setDead();
   }

   public void onBounce() {
      if (this.firstBounce) {
         this.onFirstBounce();
         this.firstBounce = false;
      }

      if (!super.worldObj.isRemote && (super.motionX != 0.0D || super.motionY != 0.0D || super.motionZ != 0.0D) && !super.isInWeb) {
         super.worldObj.playSoundAtEntity(this, "random.break", 1.0F, 1.5F);
      }

   }

   public void createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8) {
      if (par1Entity != null && par1Entity.worldObj != null) {
         GrenadeExplosion explosion = new GrenadeExplosion(par1Entity.worldObj, par1Entity, par2, par4, par6, par8);
         explosion.field_77286_a = false;
         explosion.field_82755_b = true;
         explosion.doExplosionA();
         explosion.doExplosionB(true);
      }

   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      super.writeEntityToNBT(nbttagcompound);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      super.readEntityFromNBT(nbttagcompound);
   }

   public void onCollideWithPlayer(EntityPlayer entityplayer) {
   }

   protected void onImpact(MovingObjectPosition var1) {
   }
}
