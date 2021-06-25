package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.Vote;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketVoteEnd extends CCPacket {
   public static Packet buildPacket() {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketVoteEnd.class));
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
	   try {
	      if (GameManager.instance().isPlayerInGame(player)) {
	         IGameClient gameClient = GameManager.instance().getPlayerGame(player).getClientSide();
	         gameClient.setCurrentVote((Vote)null);
	      }
	   } catch (Exception e) {}
   }
}
