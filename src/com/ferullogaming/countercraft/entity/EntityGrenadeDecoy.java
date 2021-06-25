package com.ferullogaming.countercraft.entity;

import com.ferullogaming.countercraft.item.gun.ItemGun;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityGrenadeDecoy extends EntityGrenade {
   public boolean initDecoy = false;
   public String gunsound = "ak47shoot";
   public int decoyFuse = 300;
   private Random rand = new Random();

   public EntityGrenadeDecoy(World world) {
      super(world);
   }

   public EntityGrenadeDecoy(World par1World, EntityLivingBase par2EntityLiving, double force, int fuseLength) {
      super(par1World, par2EntityLiving, force, fuseLength);
      if (par2EntityLiving instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)par2EntityLiving;
         ItemGun gun;
         if (player.inventory.getStackInSlot(1) != null && player.inventory.getStackInSlot(1).getItem() instanceof ItemGun) {
            gun = (ItemGun)player.inventory.getStackInSlot(1).getItem();
            this.gunsound = gun.soundFire;
         }

         if (player.inventory.getStackInSlot(0) != null && player.inventory.getStackInSlot(0).getItem() instanceof ItemGun) {
            gun = (ItemGun)player.inventory.getStackInSlot(0).getItem();
            this.gunsound = gun.soundFire;
         }
      }

   }

   public EntityGrenadeDecoy setDecoySound(String par1) {
      this.gunsound = par1;
      return this;
   }

   public void onUpdate() {
      super.onUpdate();
      if (this.initDecoy) {
         if (this.rand.nextInt(20) == 0) {
            super.worldObj.playSoundAtEntity(this, "countercraft:" + this.gunsound, 1.0F, 1.0F);

            for(int i = 0; i < 6; ++i) {
               EntityManager.spawnParticle("smoke", super.posX, super.posY + 0.1D, super.posZ, 0.0D, 0.0D, 0.0D);
            }
         }

         if (this.decoyFuse-- <= 0) {
            this.createExplosion(super.field_70192_c, super.posX, super.posY, super.posZ, 0.2F);
            this.setDead();
         }
      }

   }

   public void onMotionStopped() {
      this.initDecoy = true;
   }

   public void explode() {
   }
}
