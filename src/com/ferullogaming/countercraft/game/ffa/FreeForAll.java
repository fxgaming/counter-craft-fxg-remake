package com.ferullogaming.countercraft.game.ffa;

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
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FreeForAll extends IGame {
   public int maxKills = 30;
   public int maxTime = 10;
   public Timer timerPreGame;
   public Timer timerGame;
   public Timer timerPostGame;
   public String firstBlood = null;
   private FFACommands commandHandler = new FFACommands(this);
   private FFAPlayerHandler playerHandler = new FFAPlayerHandler(this);
   private FFAClient clientSide = new FFAClient(this);

   public FreeForAll() {
      super("ffa");
      this.loadIronSightWeapons();
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
            this.playerHandler.getTeam("players").tpTeamRandomSpawns();
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
               GameHelper.kickPlayers(this.playerHandler.getPlayers(), "Restarting. Goodgame! Well played.");
               this.restartGame();
            }
         }

      } else {
         this.timerGame.updateTimer();
         GameHelper.unfreezeAllPlayers(this);
         Team playerTeam = this.getPlayerEventHandler().getTeam("players");
         Iterator i$ = playerTeam.getPlayers().iterator();

         while(i$.hasNext()) {
            String username = (String)i$.next();
            if (GameHelper.getPlayerGameValue(this, username, "kills") >= this.maxKills) {
               this.handleWinningPlayer(username);
               this.setStatus(GameStatus.POSTGAME);
               GameHelper.freezeAllPlayers(this);
               break;
            }
         }

         if (this.timerGame.isTicking() && this.timerGame.getTimeRemainingSeconds() <= 20 && this.timerGame.hasSecondPast()) {
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.countdown");
         }

         if (!this.timerGame.isTicking()) {
            this.handleWinningPlayer((String)GameHelper.orderPlayersByValue(this, this.playerHandler.getPlayers(), "kills").get(0));
            this.setStatus(GameStatus.POSTGAME);
            GameHelper.freezeAllPlayers(this);
         }

      }
   }

   public void handleWinningPlayer(String winner) {
      GameHelper.sendChatToAll(this, "Game", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToAll(this, "Game", "");
      GameHelper.sendChatToAll(this, "Game", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "Free for All");
      GameHelper.sendChatToAll(this, "Game", "/c" + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + winner + " won!");
      if (FMLCommonHandler.instance().getSide().isServer()) {
         this.handleParticipationReward(3, 6);
         this.handleTopPlayers(3, "kills", 6, true, this.getPlayerEventHandler().getPlayers(), 4);
         this.handleDropChances(4);
      }

      GameHelper.sendChatToAll(this, "Game", "");
      GameHelper.sendChatToAll(this, "Game", EnumChatFormatting.RED + "- Thanks for Playing! You may Disconnect.");
      GameHelper.sendChatToAll(this, "Game", EnumChatFormatting.RED + "- Restarting Server...");
      GameHelper.sendChatToAll(this, "Game", "");
      GameHelper.sendChatToAll(this, "Game", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
   }

   public boolean handlePlayerJoining(EntityPlayer par1, String... par2) {
      Team team = this.getPlayerEventHandler().getTeam("players");
      if (!this.isGameSetup()) {
         return false;
      } else {
         if (par2 != null && par2.length == 1 && this.getPlayerEventHandler().getTeam(par2[0]) != null) {
            team = this.getPlayerEventHandler().getTeam(par2[0]);
         }

         GameHelper.sendChatToAll(this, "Game", par1.username + " joined");
         return this.playerHandler.addPlayer(par1, team.teamName);
      }
   }

   public boolean isGameSetup() {
      if (this.getPlayerEventHandler().getTeam("players").teamSpawns.size() > 0) {
         return this.getGameMapType() != null;
      } else {
         return false;
      }
   }

   public void restartGame() {
      this.setStatus(GameStatus.IDLE);
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
         GameHelper.kickPlayers(this.getPlayerEventHandler().getPlayers(), "Game Over, Forced Restart...");
      }

   }

   public void getInformation(ArrayList par1) {
      par1.add("Game ID: " + this.getGameName());
      par1.add("Type: " + this.getGameType());
      par1.add("Map ID: " + this.getGameMapType());
      par1.add("Status: " + this.getStatus());
      par1.add("Lobby: " + this.playerHandler.lobby);
      par1.add("Player Team: " + this.getPlayerEventHandler().getTeam("players"));
      par1.add("Max Kills: " + this.maxKills);
      par1.add("Max Time: " + this.maxTime + "m");
   }

   public void sendToClient(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      this.sendPlayerGameDataToClient(par1);
      this.timerPreGame.writeToFDS("pregame", par1);
      this.timerGame.writeToFDS("game", par1);
      this.timerPostGame.writeToFDS("postgame", par1);
      par1.setInteger("status", this.getStatus().id);
      this.playerHandler.getTeam("players").writeTeamToCompound(par1);
   }

   public void loadFromServer(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.loadPlayerGameDataFromServer(par1);
      this.timerPreGame.readFromFDS("pregame", par1);
      this.timerGame.readFromFDS("game", par1);
      this.timerPostGame.readFromFDS("postgame", par1);
      this.setStatus(GameStatus.get(par1.getInteger("status")));
      this.playerHandler.getTeam("players").readTeamFromFDS(par1);
   }

   public void onDataSaved(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      par1.setInteger("maxscore", this.maxKills);
      par1.setInteger("maxtime", this.maxTime);
      if (this.playerHandler.lobby != null) {
         this.playerHandler.lobby.saveToFDS("lobby", par1);
      }

      this.playerHandler.getTeam("players").removeAllPlayers();
      this.playerHandler.getTeam("players").writeTeamToCompound(par1);
      par1.setInteger("randomSpawnsSize", this.playerHandler.randomSpawns.size());

      for(int i = 0; i < this.playerHandler.randomSpawns.size(); ++i) {
         ((BlockLocation)this.playerHandler.randomSpawns.get(i)).saveToFDS("randomSpawns" + i, par1);
      }

   }

   public void onDataLoaded(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.maxKills = par1.getInteger("maxscore");
      this.maxTime = par1.getInteger("maxtime");
      this.playerHandler.lobby = BlockLocation.createBlockLocationFromFDS("lobby", par1);
      this.playerHandler.getTeam("players").readTeamFromFDS(par1);
      this.playerHandler.getTeam("players").removeAllPlayers();
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

   public void loadIronSightWeapons() {
   }

   public String getDisplayName() {
      return "Free for All";
   }
}
