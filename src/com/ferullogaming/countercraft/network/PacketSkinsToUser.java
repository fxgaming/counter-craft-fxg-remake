package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.game.GameNotification;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketSkinsToUser extends CCPacket {
   public static Packet buildPacket(EntityPlayer e) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(PacketSkinsToUser.class));
         data.writeUTF(e.getEntityName());
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
    	  if (side == Side.CLIENT) {
    		  PacketDispatcher.sendPacketToServer(new PacketSkinsToServer().buildPacket(player, CounterCraft.instance.getHDD().SKIN, CounterCraft.instance.getHDD().STICKER, CounterCraft.instance.getHDD().KNIFE));
    	  }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }
}
