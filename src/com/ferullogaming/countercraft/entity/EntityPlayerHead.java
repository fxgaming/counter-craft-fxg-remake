package com.ferullogaming.countercraft.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityPlayerHead extends Entity {
   public EntityPlayer player;

   public EntityPlayerHead(World par1World) {
      super(par1World);
      this.setSize(0.4F, 0.4F);
   }

   protected void entityInit() {
      super.dataWatcher.addObject(21, Integer.valueOf(0));
   }

   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
      return this.player == null ? false : this.player.attackEntityFrom(par1DamageSource, par2);
   }

   public void onUpdate() {
      double distance = 1.45D;
      if (this.player != null && this.player.isSneaking()) {
         distance = 1.3D;
      }

      if (!super.worldObj.isRemote) {
         if (this.player != null) {
            if (this.player.isDead) {
               this.setDead();
            }

            super.dataWatcher.updateObject(21, this.player.entityId);
            super.prevPosX = super.posX;
            super.prevPosY = super.posY;
            super.prevPosZ = super.posZ;
            this.setLocationAndAngles(this.player.posX, this.player.posY + distance, this.player.posZ, this.player.rotationYaw, this.player.rotationPitch);
         } else {
            this.setDead();
         }
      } else if (this.player == null) {
         Entity entity = super.worldObj.getEntityByID(super.dataWatcher.getWatchableObjectInt(21));
         if (entity != null && entity instanceof EntityPlayer) {
            this.player = (EntityPlayer)entity;
         }
      } else if (!this.player.username.equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.username)) {
         this.setLocationAndAngles(this.player.posX, this.player.posY + distance, this.player.posZ, this.player.rotationYaw, this.player.rotationPitch);
      } else {
         this.setDead();
      }

   }

   public boolean canBeCollidedWith() {
      if (this.player == null) {
         return false;
      } else if (super.worldObj.isRemote && Minecraft.getMinecraft().thePlayer.username == this.player.username) {
         return false;
      } else {
         return super.worldObj.isRemote;
      }
   }

   protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
   }

   protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
   }
}
