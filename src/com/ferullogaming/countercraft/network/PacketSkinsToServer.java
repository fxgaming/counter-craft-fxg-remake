package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CounterCraft;
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

public class PacketSkinsToServer extends CCPacket {
   public static Packet buildPacket(EntityPlayer e, String[] arr, String[] arr2, String[] arr3) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(PacketSkinsToServer.class));
         for (int i = 0; i != 25; i++) {
        	 data.writeUTF(arr[i]);
         }
         for (int i = 0; i != 25; i++) {
        	 data.writeUTF(arr2[i]);
         }
         for (int i = 0; i != 2; i++) {
        	 data.writeUTF(arr3[i]);
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

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
    	  String[] arr = new String[25];
    	  String[] arr2 = new String[25];
    	  String[] arr3 = new String[2];
    	  for (int i = 0; i != 25; i++) {
    		  arr[i] = stream.readUTF();
    	  }
    	  for (int i = 0; i != 25; i++) {
    		  arr2[i] = stream.readUTF();
    	  }
    	  for (int i = 0; i != 2; i++) {
    		  arr3[i] = stream.readUTF();
    	  }
    	  if (CounterCraft.instance.SKINS.containsKey(player)) {
    		  CounterCraft.instance.SKINS.remove(player);
    		  CounterCraft.instance.SKINS.put(player, arr);
    	  } else {
    		  CounterCraft.instance.SKINS.put(player, arr);
    	  }
    	  if (CounterCraft.instance.STICKERS.containsKey(player)) {
    		  CounterCraft.instance.STICKERS.remove(player);
    		  CounterCraft.instance.STICKERS.put(player, arr2);
    	  } else {
    		  CounterCraft.instance.STICKERS.put(player, arr2);
    	  }
    	  if (CounterCraft.instance.KNIFE.containsKey(player)) {
    		  CounterCraft.instance.KNIFE.remove(player);
    		  CounterCraft.instance.KNIFE.put(player, arr3);
    	  } else {
    		  CounterCraft.instance.KNIFE.put(player, arr3);
    	  }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }
}
