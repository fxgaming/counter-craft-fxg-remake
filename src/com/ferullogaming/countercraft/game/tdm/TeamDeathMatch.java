package com.ferullogaming.countercraft.game.tdm;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.ChatSymbols;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.IGameCommandHandler;
import com.ferullogaming.countercraft.game.IGamePlayerHandler;
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.game.Timer;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TeamDeathMatch extends IGame {
   public int maxScore = 100;
   public int maxTime = 10;
   public Timer timerPreGame;
   public Timer timerGame;
   public Timer timerPostGame;
   public String firstBlood = null;
   private TDMCommands commandHandler = new TDMCommands(this);
   private TDMPlayerHandler playerHandler = new TDMPlayerHandler(this);
   private TDMClient clientSide;

   public TeamDeathMatch() {
      super("tdm");
      if (FMLCommonHandler.instance().getSide().isClient()) {
         this.clientSide = new TDMClient(this);
      }

      this.restartGame();
   }

   public void onUpdate(World par1) {
      this.updateVote();
      if (this.getPlayerEventHandler().getPlayers().size() > 0) {
         par1.setWorldTime(2000L);
         par1.getWorldInfo().setRaining(false);
      }

      if (this.getStatus() != GameStatus.IDLE && this.getPlayerEventHandler().getPlayers().size() == 0) {
         this.restartGame();
      } else if (this.getStatus() == GameStatus.IDLE) {
         if (this.getPlayerEventHandler().getPlayers().size() > 0) {
            this.setStatus(GameStatus.PREGAME);
         }

      } else if (this.getStatus() == GameStatus.PREGAME) {
         if (this.getPlayerEventHandler().getPlayers().size() > 1) {
            this.timerPreGame.updateTimer();
         }

         if (this.getPlayerEventHandler().getPlayers().size() >= 6 && this.timerPreGame.getTimeRemainingSeconds() > 16) {
            this.timerPreGame.setTimeRemainingSeconds(15);
         }

         if (this.timerPreGame.isTicking() && this.timerPreGame.getTimeRemainingSeconds() <= 15 && this.timerPreGame.hasSecondPast()) {
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.countdown");
         }

         if (this.timerPreGame.isTicking() && this.timerPreGame.getTimeRemainingSeconds() <= 5) {
            GameHelper.freezeAllPlayers(this);
         }

         if (!this.timerPreGame.isTicking()) {
            GameHelper.unfreezeAllPlayers(this);
            this.setStatus(GameStatus.GAME);
            this.playerHandler.getTeam("Red").tpTeamRandomSpawns();
            this.playerHandler.getTeam("Blue").tpTeamRandomSpawns();
            GameHelper.refreshPlayersMCLevels(this.playerHandler.getPlayers());
            this.playerHandler.resetAllBuyMenuTimer();
            this.playerHandler.clearPlayerLoadouts();
            GameHelper.playerMusicAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.startmusic");
         }

      } else if (this.getStatus() != GameStatus.GAME) {
         if (this.getStatus() == GameStatus.POSTGAME) {
            this.timerPostGame.updateTimer();
            GameHelper.freezeAllPlayers(this);
            if (this.timerPostGame.getTimeRemainingSeconds() > 6 && this.playerHandler.getPlayers().size() <= 1) {
               this.timerPostGame.setTimeRemainingSeconds(5);
            }

            if (!this.timerPostGame.isTicking()) {
               GameHelper.unfreezeAllPlayers(this);
               GameHelper.kickPlayers(this.playerHandler.getPlayers(), "Перезапуск игры.");
               this.restartGame();
            }
         }

      } else {
         this.timerGame.updateTimer();
         GameHelper.unfreezeAllPlayers(this);
         Team red = this.getPlayerEventHandler().getTeam("red");
         int redscore = red.getTeamObjectInteger("score").intValue();
         Team blue = this.getPlayerEventHandler().getTeam("blue");
         int bluescore = blue.getTeamObjectInteger("score").intValue();
         if (redscore >= this.maxScore || bluescore >= this.maxScore) {
            Team team = this.playerHandler.getWinningTeam();
            if (team != null) {
               this.handleWinningTeam(team);
               this.setStatus(GameStatus.POSTGAME);
               GameHelper.freezeAllPlayers(this);
            }
         }

         if (this.timerGame.isTicking() && this.timerGame.getTimeRemainingSeconds() <= 20 && this.timerGame.hasSecondPast()) {
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.countdown");
         }

         if (!this.timerGame.isTicking()) {
            this.handleWinningTeam(this.playerHandler.getWinningTeam());
            this.setStatus(GameStatus.POSTGAME);
            GameHelper.freezeAllPlayers(this);
         }

      }
   }

   public void handleWinningTeam(Team teamWon) {
      Team teamRed = this.playerHandler.getTeam("Red");
      int scoreRed = teamRed.getTeamObjectInteger("score").intValue();
      Team teamBlue = this.playerHandler.getTeam("Blue");
      int scoreBlue = teamBlue.getTeamObjectInteger("score").intValue();
      GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "Командный бой");
      if (teamWon == null) {
         GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "ничья!");
         this.getPlayerEventHandler().getTeam("Red").playVictoryGameMusicPerPlayer("red");
         this.getPlayerEventHandler().getTeam("Blue").playVictoryGameMusicPerPlayer("blue");
      } else {
         GameHelper.sendChatToAll(this, "Игра", "/c " + teamWon.teamColor + "" + EnumChatFormatting.BOLD + "" + teamWon.teamName + " выиграли!");
         GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), teamWon.teamColor + "" + EnumChatFormatting.BOLD + teamWon.teamName.toUpperCase() + " выиграли!");
         if (teamWon.teamName == "Blue") {
            this.getPlayerEventHandler().getTeam("Red").playDefeatGameMusicPerPlayer("red");
            this.getPlayerEventHandler().getTeam("Blue").playVictoryGameMusicPerPlayer("blue");
         } else if (teamWon.teamName == "Red") {
            this.getPlayerEventHandler().getTeam("Blue").playDefeatGameMusicPerPlayer("blue");
            this.getPlayerEventHandler().getTeam("Red").playVictoryGameMusicPerPlayer("red");
         }
      }

      GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.ITALIC + "Террористы: " + scoreRed + "    Контр-террористы: " + scoreBlue);
      if (FMLCommonHandler.instance().getSide().isServer()) {
         this.handleParticipationReward(5, 6);
         this.handleTopPlayers(5, "kills", 6, true, this.getPlayerEventHandler().getPlayers(), 4);
         this.handleDropChances(4);
      }

      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "- Спасибо за игру! Вы должны отключится.");
      GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "- Перезагрузка матча...");
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
   }

   public boolean handlePlayerJoining(EntityPlayer par1, String... par2) {
      Team team = this.getPlayerEventHandler().getNextBalancedOpenTeam();
      if (!this.isGameSetup()) {
         return false;
      } else {
         if (par2 != null && par2.length == 1 && this.getPlayerEventHandler().getTeam(par2[0]) != null) {
            team = this.getPlayerEventHandler().getTeam(par2[0]);
         }

         GameHelper.sendChatToAll(this, "Игра", par1.username + " присоеденился");
         return this.playerHandler.addPlayer(par1, team.teamName);
      }
   }

   public boolean isGameSetup() {
      if (this.getPlayerEventHandler().getTeam("red").teamSpawns.size() > 0 && this.getPlayerEventHandler().getTeam("blue").teamSpawns.size() > 0) {
         return this.getGameMapType() != null;
      } else {
         return false;
      }
   }

   public void restartGame() {
      this.setStatus(GameStatus.IDLE);
      this.playerHandler.getTeam("Blue").setTeamObject("score", Integer.valueOf(0));
      this.playerHandler.getTeam("Red").setTeamObject("score", Integer.valueOf(0));
      this.timerGame = new Timer(this.maxTime, 0);
      this.timerPreGame = new Timer(1, 0);
      this.timerPostGame = new Timer(0, 30);
      this.clearAllPlayerData();
      this.playerHandler.playerLoadouts.clear();
      this.firstBlood = null;
      GameManager.instance().onGameRestarted(this);
   }

   public void forceStop() {
      if (this.getPlayerEventHandler().getPlayers().size() > 0) {
         GameHelper.kickPlayers(this.getPlayerEventHandler().getPlayers(), "Игра окончена. Рестарт.");
      }
   }

   public void getInformation(ArrayList par1) {
      par1.add("Game ID: " + this.getGameName());
      par1.add("Type: " + this.getGameType());
      par1.add("Map ID: " + this.getGameMapType());
      par1.add("Status: " + this.getStatus());
      par1.add("Lobby: " + this.playerHandler.lobby);
      par1.add("Red Team: " + this.getPlayerEventHandler().getTeam("Red"));
      par1.add("Blue Team: " + this.getPlayerEventHandler().getTeam("Blue"));
      par1.add("Max Score: " + this.maxScore);
      par1.add("Max Time: " + this.maxTime + "m");
   }

   public void sendToClient(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      this.sendPlayerGameDataToClient(par1);
      this.timerPreGame.writeToFDS("pregame", par1);
      this.timerGame.writeToFDS("game", par1);
      this.timerPostGame.writeToFDS("postgame", par1);
      par1.setInteger("status", this.getStatus().id);
      this.playerHandler.getTeam("Red").writeTeamToCompound(par1);
      this.playerHandler.getTeam("Blue").writeTeamToCompound(par1);
   }

   public void loadFromServer(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.loadPlayerGameDataFromServer(par1);
      this.timerPreGame.readFromFDS("pregame", par1);
      this.timerGame.readFromFDS("game", par1);
      this.timerPostGame.readFromFDS("postgame", par1);
      this.setStatus(GameStatus.get(par1.getInteger("status")));
      this.playerHandler.getTeam("Red").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Blue").readTeamFromFDS(par1);
   }

   public void onDataSaved(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      if (this.playerHandler.lobby != null) {
         this.playerHandler.lobby.saveToFDS("lobby", par1);
      }

      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.playerHandler.getTeam("Red").writeTeamToCompound(par1);
      this.playerHandler.getTeam("Blue").writeTeamToCompound(par1);
      par1.setInteger("randomSpawnsSize", this.playerHandler.randomSpawns.size());

      for(int i = 0; i < this.playerHandler.randomSpawns.size(); ++i) {
         ((BlockLocation)this.playerHandler.randomSpawns.get(i)).saveToFDS("randomSpawns" + i, par1);
      }

   }

   public void onDataLoaded(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.playerHandler.lobby = BlockLocation.createBlockLocationFromFDS("lobby", par1);
      this.playerHandler.getTeam("Red").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Blue").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.clearAllPlayerData();
      int var1 = par1.getInteger("randomSpawnsSize");
      this.playerHandler.randomSpawns.clear();

      for(int i = 0; i < var1; ++i) {
         BlockLocation spawn = BlockLocation.createBlockLocationFromFDS("randomSpawns" + i, par1);
         this.playerHandler.randomSpawns.add(spawn.copy());
      }

   }

   public IGameClient getClientSide() {
      return this.clientSide;
   }

   public IGamePlayerHandler getPlayerEventHandler() {
      return this.playerHandler;
   }

   public IGameCommandHandler getCommandHandler() {
      return this.commandHandler;
   }

   public String getDisplayName() {
      return "Team Deathmatch";
   }

   public void loadIronSightWeapons() {
   }
}
