package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.game.GameNotification;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketGameNotification extends CCPacket {
   public static Packet buildPacket(GameNotification par1) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketGameNotification.class));
         par1.writeToStream(data);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         GameNotification var1 = GameNotification.createNotificationFromStream(stream);
         ClientTickHandler.addGameNotification(var1);
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }
}
