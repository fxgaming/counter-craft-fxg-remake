package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.network.cloud.CPacket;
import com.ferullogaming.countercraft.network.cloud.CloudNetwork;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketHandlerClient implements IPacketHandler {
   public static final void onRecieve(Packet250CustomPayload packet, EntityPlayer player, INetworkManager manager) {
      if (packet.channel.equals("ccNetworking")) {
         NetworkManager netManager = CounterCraft.getNetworkManager();

         try {
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(packet.data));
            int incomingPacketID = stream.read();
            if (incomingPacketID == 0) {
               CounterCraft.log("Incoming Packet processed by client with 0 ID!");
               stream.close();
               return;
            }

            if (netManager.getPacketFromID(incomingPacketID) != null) {
               CCPacket ccpacket = (CCPacket)netManager.getPacketFromID(incomingPacketID).getClass().newInstance();
               ccpacket.execute(stream, player, new Object[0], Side.CLIENT);
            }

            stream.close();
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }
      
      if (packet.channel.equals("clop")) {
    	  CloudNetwork netManager = CounterCraft.getCloudNetwork();

          try {
             DataInputStream stream = new DataInputStream(new ByteArrayInputStream(packet.data));
             int incomingPacketID = stream.read();
             if (incomingPacketID == 0) {
                CounterCraft.log("Incoming Packet processed by client with 0 ID!");
                stream.close();
                return;
             }

             if (netManager.getPacketFromID(incomingPacketID) != null) {
                CPacket ccpacket = (CPacket)netManager.getPacketFromID(incomingPacketID).getClass().newInstance();
                ccpacket.execute(stream, player, new Object[0], Side.CLIENT);
             }

             stream.close();
          } catch (Exception var7) {
             var7.printStackTrace();
          }
       }

   }

   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
      String name = ((EntityPlayer)player).username;
      EntityPlayer playerEntity = FMLClientHandler.instance().getClient().thePlayer;
      onRecieve(packet, playerEntity, manager);
   }
}
