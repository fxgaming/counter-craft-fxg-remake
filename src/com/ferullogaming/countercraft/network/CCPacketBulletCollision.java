package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet55BlockDestroy;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CCPacketBulletCollision extends CCPacket {
   public static Packet buildPacket(boolean par1, Object... extraData) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketBulletCollision.class));
         data.writeBoolean(par1);
         if (par1) {
            Entity entityHit = (Entity)extraData[0];
            int var1 = entityHit.entityId;
            data.writeInt(var1);
            data.writeBoolean(((Boolean)extraData[1]).booleanValue());
            data.writeInt(((Integer)extraData[2]).intValue());
         } else {
            int blockid = ((Integer)extraData[0]).intValue();
            double var1 = ((Double)extraData[1]).doubleValue();
            double var2 = ((Double)extraData[2]).doubleValue();
            double var3 = ((Double)extraData[3]).doubleValue();
            int var4 = ((Integer)extraData[4]).intValue();
            double var5vecx = ((Double)extraData[5]).doubleValue();
            double var6vecy = ((Double)extraData[6]).doubleValue();
            double var7vecz = ((Double)extraData[7]).doubleValue();
            data.writeInt(blockid);
            data.writeDouble(var1);
            data.writeDouble(var2);
            data.writeDouble(var3);
            data.writeInt(var4);
            data.writeDouble(var5vecx);
            data.writeDouble(var6vecy);
            data.writeDouble(var7vecz);
         }

         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var19) {
         var19.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         boolean var1 = stream.readBoolean();
         World world = player.worldObj;
         ItemStack itemstack = player.getCurrentEquippedItem();
         if (itemstack != null && itemstack.getItem() instanceof ItemGun && ItemGun.hasLoadedAmmo(itemstack)) {
            int var2;
            if (var1) {
               if (!world.isRemote) {
                  var2 = stream.readInt();
                  boolean var3 = stream.readBoolean();
                  int var4 = stream.readInt();
                  Entity entityHit = world.getEntityByID(var2);
                  if (entityHit != null && !entityHit.isDead && entityHit instanceof EntityLivingBase) {
                     CounterCraft.getCommonEvents().onBulletCollisionEntity(player, itemstack, (EntityLivingBase)entityHit, var3, var4);
                     double var10000 = entityHit.posX;
                     double var10001 = entityHit.posY;
                     double var10002 = entityHit.posZ;
                     new CCPacketBulletCollisionClient();
                     PacketDispatcher.sendPacketToAllAround(var10000, var10001, var10002, 50.0D, 0, CCPacketBulletCollisionClient.buildPacket(true, entityHit, var3, var4));
                  }
               }
            } else {
               var2 = stream.readInt();
               double x = stream.readDouble();
               double y = stream.readDouble();
               double z = stream.readDouble();
               int sidehit = stream.readInt();
               double vecx = stream.readDouble();
               double vecy = stream.readDouble();
               double vecz = stream.readDouble();
               Vec3 vec3 = Vec3.createVectorHelper(vecx, vecy, vecz);
               int checkID = world.getBlockId((int)x, (int)y, (int)z);
               if (var2 == checkID && var2 != 0) {
                  CounterCraft.getCommonEvents().onBulletCollisionBlock(player, itemstack, world, x, y, z, var2, sidehit, vec3);
                  int max = 9;
                  int min = 1;
                  Random randomNum = new Random();
                  int randomBlockCrack = min + randomNum.nextInt(max);
                  PacketDispatcher.sendPacketToAllPlayers(new Packet55BlockDestroy(var2, (int)x, (int)y, (int)z, randomBlockCrack));
                  new CCPacketBulletCollisionClient();
                  PacketDispatcher.sendPacketToAllAround(x, y, z, 50.0D, 0, CCPacketBulletCollisionClient.buildPacket(false, var2, x, y, z, sidehit, vecx, vecy, vecz));
               }
            }
         }
      } catch (IOException var28) {
         var28.printStackTrace();
      }

   }
}
