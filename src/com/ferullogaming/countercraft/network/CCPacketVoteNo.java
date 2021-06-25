package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketVoteNo extends CCPacket {
   public static Packet buildPacket() {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketVoteNo.class));
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
	      PlayerData playerData = PlayerDataHandler.getPlayerData(player);
	      if (playerData != null && player != null && GameManager.instance().isPlayerInGame(player)) {
	         IGame gameClient = GameManager.instance().getPlayerGame(player);
	         if (gameClient != null && gameClient.getPlayerEventHandler() != null && gameClient.getPlayerEventHandler().getPlayerTeam(player) != null && gameClient.getCurrentVote() != null && gameClient.getPlayerEventHandler().getPlayerTeam(player).teamName.equalsIgnoreCase(gameClient.getCurrentVote().teamVoting)) {
	            if (!playerData.hasVoted() && gameClient.getCurrentVote() != null) {
	               playerData.hasVoted = true;
	               ++gameClient.getCurrentVote().noVotes;
	               PacketDispatcher.sendPacketToAllPlayers(CCPacketSoundEffectPlayer.buildPacket("countercraft:gui.vote.voteNo", 20.0F, 1.0F));
	            }
	         } else if (gameClient != null) {
	            GameHelper.sendChatToPlayer(gameClient, player.username, "Vote", "You must be in the " + gameClient.getCurrentVote().teamVoting + " team to vote!");
	         }
	      }
	   } catch (Exception e) {}
   }
}
