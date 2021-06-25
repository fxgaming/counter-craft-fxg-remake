package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketMMDataToServer extends CCPacket {
   public static Packet buildPacket(String par1, String par2, String par3) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketMMDataToServer.class));
         data.writeUTF(par1);
         data.writeUTF(par2);
         data.writeUTF(par3);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String game = stream.readUTF();
         String map = stream.readUTF();
         String team = stream.readUTF();
         if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            IGame game1 = GameManager.instance().getGameFromTypeAndMap(game, map);
            if (game1 != null && !GameManager.instance().assignPlayerToGame(player, game1, team)) {
               ((EntityPlayerMP)player).playerNetServerHandler.kickPlayerFromServer("Failed to join the game assigned by MM.");
            }
         }
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }
}
