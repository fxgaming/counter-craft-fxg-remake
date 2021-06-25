package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.BlockLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketUpdateGameTP extends CCPacket {
   public static Packet buildPacket(int entityID, BlockLocation loc) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketUpdateGameTP.class));
         data.writeInt(entityID);
         data.writeDouble(loc.posX);
         data.writeDouble(loc.posY);
         data.writeDouble(loc.posZ);
         data.writeDouble(loc.rotPitch);
         data.writeDouble(loc.rotYaw);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         int entityID = stream.readInt();
         double posX = stream.readDouble();
         double posY = stream.readDouble();
         double posZ = stream.readDouble();
         double rotPitch = stream.readDouble();
         double rotYaw = stream.readDouble();
         if (player.worldObj.getEntityByID(entityID) != null && player.worldObj.getEntityByID(entityID) instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP updatingPlayer = (EntityOtherPlayerMP)player.worldObj.getEntityByID(entityID);
            boolean flag = false;
            int var1 = 0;

            while(!flag) {
               ++var1;
               if (!updatingPlayer.username.equals(Minecraft.getMinecraft().thePlayer.username) && updatingPlayer.getHealth() > 0.0F) {
                  updatingPlayer.setPosition(posX, posY, posZ);
                  updatingPlayer.setPositionAndRotation(posX, posY, posZ, (float)rotYaw, (float)rotPitch);
                  player.worldObj.updateEntity(updatingPlayer);
                  flag = true;
               }

               if (var1 >= 1000) {
                  flag = true;
               }
            }
         }
      } catch (IOException var19) {
         var19.printStackTrace();
      }

   }
}
