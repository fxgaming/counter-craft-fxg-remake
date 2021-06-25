package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CounterCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CCPacketBulletCollisionClient extends CCPacket {
   public static Packet buildPacket(boolean par1, Object... extraData) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketBulletCollisionClient.class));
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
            double var5 = ((Double)extraData[5]).doubleValue();
            double var6 = ((Double)extraData[6]).doubleValue();
            double var7 = ((Double)extraData[7]).doubleValue();
            data.writeInt(blockid);
            data.writeDouble(var1);
            data.writeDouble(var2);
            data.writeDouble(var3);
            data.writeInt(var4);
            data.writeDouble(var5);
            data.writeDouble(var6);
            data.writeDouble(var7);
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

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         boolean var1 = stream.readBoolean();
         World world = player.worldObj;
         if (world.isRemote) {
            int blockid;
            if (var1) {
               blockid = stream.readInt();
               boolean var3 = stream.readBoolean();
               int var4 = stream.readInt();
               Entity entityHit = world.getEntityByID(blockid);
               if (entityHit != null && !entityHit.isDead && entityHit instanceof EntityLivingBase) {
                  CounterCraft.getClientEvents().onBulletCollisionEntity((EntityLivingBase)entityHit, var3, var4);
               }
            } else {
               blockid = stream.readInt();
               double x = stream.readDouble();
               double y = stream.readDouble();
               double z = stream.readDouble();
               int sidehit = stream.readInt();
               double vx = stream.readDouble();
               double vy = stream.readDouble();
               double vz = stream.readDouble();
               Vec3 vec3 = Vec3.createVectorHelper(vx, vy, vz);
               int checkID = world.getBlockId((int)x, (int)y, (int)z);
               if (blockid == checkID && blockid != 0) {
                  CounterCraft.getClientEvents().onBulletCollisionBlock(world, x, y, z, blockid, sidehit, vec3, world.getBlockMetadata((int)x, (int)y, (int)z));
               }
            }
         }
      } catch (IOException var23) {
         var23.printStackTrace();
      }

   }
}
