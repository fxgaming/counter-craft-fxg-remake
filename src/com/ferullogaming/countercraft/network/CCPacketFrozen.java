package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketFrozen extends CCPacket {
   public static Packet buildPacket(BlockLocation loc) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketFrozen.class));
         data.writeBoolean(loc != null);
         if (loc != null) {
            data.writeDouble(loc.posX);
            data.writeDouble(loc.posY);
            data.writeDouble(loc.posZ);
            data.writeDouble(loc.rotPitch);
            data.writeDouble(loc.rotYaw);
         }

         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         boolean isFrozen = stream.readBoolean();
         if (!player.worldObj.isRemote && !Minecraft.getMinecraft().isSingleplayer()) {
            PlayerData data = PlayerDataHandler.getClientPlayerData();
            data.isFrozen = isFrozen;
         }

         if (isFrozen) {
            double posX = stream.readDouble();
            double posY = stream.readDouble();
            double posZ = stream.readDouble();
            double rotPitch = stream.readDouble();
            double rotYaw = stream.readDouble();
            if (player != null && player.getHealth() > 0.0F) {
               player.setPosition(posX, player.posY, posZ);
            }
         }
      } catch (IOException var16) {
         var16.printStackTrace();
      }

   }
}
