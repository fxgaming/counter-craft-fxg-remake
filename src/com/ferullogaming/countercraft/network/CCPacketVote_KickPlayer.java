package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CCUtils;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.Vote;
import com.ferullogaming.countercraft.game.VoteKick;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class CCPacketVote_KickPlayer extends CCPacket {
   public static Packet buildPacket(String givenPlayerNameToKick) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketVote_KickPlayer.class));
         data.writeUTF(givenPlayerNameToKick);
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
         String usernameToKick = stream.readUTF();
         if (!usernameToKick.equals(player.getCommandSenderName())) {
            if (player instanceof EntityPlayerMP && MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(usernameToKick) != null) {
               IGame gameData = GameManager.instance().getPlayerGame(player);
               if (gameData != null) {
                  if (gameData.getVoteCooldown() <= 0) {
                     if (gameData.getPlayerEventHandler().getPlayerTeam(player).teamName.equals(gameData.getPlayerEventHandler().getPlayerTeam(usernameToKick).teamName)) {
                        if (gameData.getCurrentVote() == null) {
                           PlayerData playerData = PlayerDataHandler.getPlayerData(player);
                           VoteKick newVote = new VoteKick(gameData.getPlayerEventHandler().getPlayerTeam(player).teamName, Vote.VoteType.KICK, usernameToKick);
                           newVote.voteTime = 300L;
                           gameData.setCurrentVote(newVote);
                           playerData.hasVoted = true;
                           ++newVote.yesVotes;
                           PacketDispatcher.sendPacketToAllPlayers(CCPacketSoundEffectPlayer.buildPacket("countercraft:gui.vote.voteStarted", 20.0F, 1.0F));
                           GameHelper.sendChatToAll(gameData, "Vote Kick!", player.getCommandSenderName() + " has started a vote on team " + gameData.getPlayerEventHandler().getPlayerTeam(player).teamName + " to kick player " + usernameToKick + "!");
                        } else {
                           player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "There is already a vote happening!")));
                        }
                     } else {
                        player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "The given player is not on your team!")));
                     }
                  } else {
                     player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "Vote is too recent! Please wait a while! (" + CCUtils.getTickAsTime(gameData.getVoteCooldown()) + ")")));
                  }
               } else {
                  player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "There was an issue with casting a vote! (Try again later)")));
               }
            } else {
               player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "The given player username doesn't exist!")));
            }
         } else {
            player.sendChatToPlayer(new ChatMessageComponent(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "You can't vote to kick yourself!")));
         }
      } catch (IOException var9) {
      }
   }
}
