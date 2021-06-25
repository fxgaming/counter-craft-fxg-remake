package com.ferullogaming.countercraft.entity;

import com.ferullogaming.countercraft.network.CCPacketGrenadeFlash;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityGrenadeFlash extends EntityGrenade {
   String soundFlash = "countercraft:grenadeFlashBurst";

   public EntityGrenadeFlash(World world) {
      super(world);
      super.yOffset = super.height / 2.0F;
      super.fuse = 0;
   }

   public EntityGrenadeFlash(World par1World, EntityLivingBase par2EntityLiving, double force, int fuseLength) {
      super(par1World, par2EntityLiving, force, fuseLength);
   }

   public void explode() {
      double flashRadius = 40.0D;
      super.worldObj.playSoundAtEntity(this, this.soundFlash, 4.0F, 1.0F);
      AxisAlignedBB axisalignedbb = super.boundingBox.expand(flashRadius, flashRadius, flashRadius);
      List list1 = super.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
      if (list1 != null && !list1.isEmpty()) {
         Iterator iterator = list1.iterator();

         while(iterator.hasNext()) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
            double dist = (double)this.getDistanceToEntity(entitylivingbase);
            if (entitylivingbase instanceof EntityPlayer && dist < flashRadius) {
               EntityPlayer player = (EntityPlayer)entitylivingbase;
               PlayerData data = PlayerDataHandler.getPlayerData(player);
               PacketDispatcher.sendPacketToPlayer(CCPacketGrenadeFlash.buildPacket(dist, super.entityId), (Player)player);
            }
         }
      }

      this.setDead();
   }
}
