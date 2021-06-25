package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.network.CCPacketSoundEffectPlayer;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;

public class Vote {
   public String teamVoting;
   public int yesVotes = 0;
   public int noVotes = 0;
   public Vote.VoteType voteType;
   public String voteValue;
   public long voteTime = 0L;
   public boolean hasVoteEnded = false;

   public Vote(String givenTeamVoting, Vote.VoteType givenVoteType, String givenVoteValue) {
      this.teamVoting = givenTeamVoting;
      this.voteType = givenVoteType;
      this.voteValue = givenVoteValue;
   }

   public Vote(String givenTeamVoting, int givenYesVotes, int givenNoVotes, Vote.VoteType givenVoteType, String givenVoteValue) {
      this.teamVoting = givenTeamVoting;
      this.voteType = givenVoteType;
      this.voteValue = givenVoteValue;
      this.yesVotes = givenYesVotes;
      this.noVotes = givenNoVotes;
   }

   public void tick() {
      if (this.voteTime > 0L) {
         --this.voteTime;
      } else {
         this.hasVoteEnded = true;
      }

   }

   public void finalizeVote() {
      EntityPlayer thePlayer = GameHelper.getPlayerFromUsername(this.voteValue);
      if (thePlayer != null) {
         IGame gameData = GameManager.instance().getPlayerGame(thePlayer);
         if (gameData != null) {
            int teamSize = gameData.getPlayerEventHandler().getPlayerTeam(thePlayer).getPlayers().size() + this.noVotes;
            boolean wasSuccessful = this.yesVotes > teamSize / 2;
            if (wasSuccessful) {
               PacketDispatcher.sendPacketToAllPlayers(CCPacketSoundEffectPlayer.buildPacket("countercraft:gui.vote.voteSuccess", 20.0F, 1.0F));
            } else {
               PacketDispatcher.sendPacketToAllPlayers(CCPacketSoundEffectPlayer.buildPacket("countercraft:gui.vote.voteFailure", 20.0F, 1.0F));
            }

            this.execute(gameData, this.yesVotes > teamSize / 2);
         }
      }

   }

   public void execute(IGame givenGame, boolean wasSuccessfull) {
   }

   public static enum VoteType {
      UNKNOWN(0),
      KICK(1),
      SCRAMBLE_TEAMS(2),
      REMATCH(3);

      int voteID = 0;

      private VoteType(int givenVoteID) {
         this.voteID = givenVoteID;
      }

      public static Vote.VoteType getById(int id) {
         Vote.VoteType[] arr$ = values();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Vote.VoteType e = arr$[i$];
            if (e.voteID == id) {
               return e;
            }
         }

         return UNKNOWN;
      }
   }
}
