package com.ferullogaming.countercraft.entity;

import com.ferullogaming.countercraft.damagesource.DamageSourceBomb;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameComponentBomb;
import com.ferullogaming.countercraft.network.CCPacketSoundPlayer;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBomb extends Entity {
   public int bombFuse = 900;
   public boolean exploded;
   public IGame theGame;
   public EntityPlayer planter;
   private float bombVolume = 1.0F;

   public EntityBomb(World par1World) {
      super(par1World);
      this.setSize(0.5F, 0.2F);
   }

   public EntityBomb(World par1, IGame par2, EntityPlayer par3) {
      super(par1);
      if (par2 instanceof IGameComponentBomb) {
         this.theGame = par2;
         this.bombFuse = ((IGameComponentBomb)par2).getBombFuse();
         this.planter = par3;
      }

   }

   public EntityBomb(World par1, EntityPlayer par2) {
      super(par1);
      this.planter = par2;
   }

   protected void entityInit() {
      super.dataWatcher.addObject(21, Integer.valueOf(0));
      super.dataWatcher.addObject(22, "");
   }

   public void onUpdate() {
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      this.moveEntity(super.motionX, super.motionY, super.motionZ);
      super.motionY -= 0.04D;
      if (!super.worldObj.isRemote) {
         if (this.bombFuse == -10) {
            this.setDead();
            return;
         }

         if (this.bombFuse > 0) {
            --this.bombFuse;
         }

         if (this.bombFuse == 20) {
            super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.ignite", this.bombVolume, 1.0F);
            this.bombVolume = (float)((double)this.bombVolume + 0.1D);
         } else if (this.bombFuse < 60) {
            if (this.bombFuse % 3 == 0) {
               super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.tick", this.bombVolume, 1.9F);
               this.bombVolume = (float)((double)this.bombVolume + 0.1D);
            }
         } else if (this.bombFuse < 100) {
            if (this.bombFuse % 5 == 0) {
               super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.tick", this.bombVolume, 1.9F);
               this.bombVolume = (float)((double)this.bombVolume + 0.1D);
            }
         } else if (this.bombFuse < 200) {
            if (this.bombFuse % 10 == 0) {
               super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.tick", this.bombVolume, 1.8F);
               this.bombVolume = (float)((double)this.bombVolume + 0.1D);
            }
         } else if (this.bombFuse < 300) {
            if (this.bombFuse % 15 == 0) {
               super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.tick", this.bombVolume, 1.7F);
               this.bombVolume = (float)((double)this.bombVolume + 0.1D);
            }
         } else if (this.bombFuse % 20 == 0 && !super.worldObj.isRemote) {
            super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.tick", this.bombVolume, 1.5F);
            this.bombVolume = (float)((double)this.bombVolume + 0.1D);
         }

         if (this.bombFuse <= 0 && this.bombFuse != -10) {
            super.worldObj.playSoundAtEntity(this, "countercraft:entity.bomb.explode", 20.0F, 0.8F);
            List worldEntities = super.worldObj.playerEntities;

            for(int i = 0; i < worldEntities.size(); ++i) {
               EntityPlayer thePlayer = (EntityPlayer)worldEntities.get(i);
               if (thePlayer.getDistanceToEntity(this) <= 15.0F) {
                  thePlayer.attackEntityFrom(new DamageSourceBomb(), 20.0F);
                  System.out.println("damaged with 20");
               } else if (thePlayer.getDistanceToEntity(this) <= 25.0F) {
                  thePlayer.attackEntityFrom(new DamageSourceBomb(), 15.0F);
                  System.out.println("damaged with 15");
               } else if (thePlayer.getDistanceToEntity(this) <= 30.0F) {
                  thePlayer.attackEntityFrom(new DamageSourceBomb(), 10.0F);
                  System.out.println("damaged with 10");
               } else if (thePlayer.getDistanceToEntity(this) <= 40.0F) {
                  thePlayer.attackEntityFrom(new DamageSourceBomb(), 5.0F);
                  System.out.println("damaged with 5");
               }
            }

            this.explode();
         }
      }

   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public void setDefusing(boolean par1, String par2) {
      super.dataWatcher.updateObject(21, par1 ? 1 : 0);
      super.dataWatcher.updateObject(22, par2);
   }

   public boolean isBeingDefused() {
      return super.dataWatcher.getWatchableObjectInt(21) == 1;
   }

   public String getDefuser() {
      return super.dataWatcher.getWatchableObjectString(22);
   }

   public void explode() {
      if (!this.exploded) {
         this.exploded = true;
         if (this.theGame != null) {
            ((IGameComponentBomb)this.theGame).onBombExplodes(super.worldObj, this.planter);
         }

         for(int i = 0; i < 50; ++i) {
            EntityManager.spawnParticle("largeexplode", super.posX, super.posY, super.posZ, 0.0D, 0.0D, 0.0D);
            EntityManager.spawnParticle("largeexplode", super.posX + 2.0D, super.posY + 1.0D, super.posZ, 0.0D, 0.0D, 0.0D);
            EntityManager.spawnParticle("largeexplode", super.posX + 2.0D, super.posY - 2.0D, super.posZ, 0.0D, 0.0D, 0.0D);
            EntityManager.spawnParticle("largeexplode", super.posX - 2.0D, super.posY + 2.0D, super.posZ, 0.0D, 0.0D, 0.0D);
            EntityManager.spawnParticle("largeexplode", super.posX - 2.0D, super.posY - 2.0D, super.posZ, 0.0D, 0.0D, 0.0D);
         }

         this.createExplosion(this.planter, super.posX, super.posY, super.posZ, 20.0F);
      }

      this.setDead();
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

   protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.setDead();
   }

   protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
   }

   public boolean isDefused() {
      return this.bombFuse == -10;
   }

   public void setDefused() {
      if (this.bombFuse != -10) {
         PacketDispatcher.sendPacketToAllPlayers(CCPacketSoundPlayer.buildPacket("countercraft:entity.defusekit.defuse", super.posX, super.posY, super.posZ, 1.0F, 1.0F));
         if (this.theGame != null) {
            ((IGameComponentBomb)this.theGame).onBombDefused(super.worldObj, this.getDefuser());
         }

         this.bombFuse = -10;
      }

   }
}
