package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.client.ModVariablesClient;
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

public class CCPacketHitMarker extends CCPacket {
   public static Packet buildPacket(String par1) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketHitMarker.class));
         data.writeUTF(par1);
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
         String playerName = stream.readUTF();
         if (Minecraft.getMinecraft().thePlayer.getDisplayName().equals(playerName)) {
            ModVariablesClient.hmTimer = 10;
            player.playSound("countercraft:misc.hitmarker", 0.8F, 1.0F);
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }
}
