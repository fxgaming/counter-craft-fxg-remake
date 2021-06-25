package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketKickedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class VoteKick extends Vote {
   public VoteKick(String givenTeamVoting, Vote.VoteType givenVoteType, String givenVoteValue) {
      super(givenTeamVoting, givenVoteType, givenVoteValue);
   }

   public void execute(IGame givenGame, boolean wasSuccessfull) {
      if (givenGame != null && super.voteValue != null) {
         if (wasSuccessfull) {
            GameHelper.sendChatToAll(givenGame, "Решение принято!", super.voteValue + " успешно выгнан из игры!");
            EntityPlayer thePlayer = GameHelper.getPlayerFromUsername(super.voteValue);
            if (thePlayer != null) {
               EntityPlayerMP playermp = (EntityPlayerMP)thePlayer;
               playermp.playerNetServerHandler.kickPlayerFromServer("Вы были выгнаны из матча.");
            }

            ServerCloudManager.sendPacket(new PacketKickedPlayer(super.voteValue));
         } else {
            GameHelper.sendChatToAll(givenGame, "Решение отклонено!", super.voteValue + " не будет выгнан! (Недостаточно голосов)");
         }
      }

   }
}
