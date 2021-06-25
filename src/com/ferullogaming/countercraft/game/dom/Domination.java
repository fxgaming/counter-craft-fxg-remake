package com.ferullogaming.countercraft.game.dom;

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
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class Domination extends IGame {
   public ArrayList capturePoints = new ArrayList();
   public Timer timerPreGame;
   public Timer timerGame;
   public Timer timerPostGame;
   public int maxScore = 250;
   public int maxTime = 10;
   public int capturePointScoreDelay = 0;
   public String firstBlood = null;
   private DOMCommands commandHandler = new DOMCommands(this);
   private DOMPlayerHandler playerHandler = new DOMPlayerHandler(this);
   private DOMClient clientSide = new DOMClient(this);

   public Domination() {
      super("dom");
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
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.harp");
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.hat");
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
            this.playerHandler.clearPlayerLoadouts();
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
         this.updateCatpurePoints(par1);
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
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.harp");
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.hat");
         }

         if (!this.timerGame.isTicking()) {
            this.handleWinningTeam(this.playerHandler.getWinningTeam());
            this.setStatus(GameStatus.POSTGAME);
            GameHelper.freezeAllPlayers(this);
         }

      }
   }

   public void updateCatpurePoints(World par1) {
      int i;
      CapturePoint cpoint;
      for(i = 0; i < this.capturePoints.size(); ++i) {
         cpoint = (CapturePoint)this.capturePoints.get(i);
         ArrayList playersNear = cpoint.getPlayersAround(this);
         cpoint.flicker = false;
         Team cappingTeam = null;
         ArrayList reds = new ArrayList();
         ArrayList blues = new ArrayList();
         int capPlayers = 0;
         int notCapPlayers = 0;
         int capRate = 0;
         int i1;
         if (playersNear.size() > 0) {
            for(i1 = 0; i1 < playersNear.size(); ++i1) {
               EntityPlayer player = (EntityPlayer)playersNear.get(i1);
               PlayerData data = PlayerDataHandler.getPlayerData(player);
               Team team = this.getPlayerEventHandler().getPlayerTeam(player);
               if (!player.isDead && player.getHealth() > 0.0F && !data.isGhost) {
                  if (team.teamName.equalsIgnoreCase("red")) {
                     reds.add(player.username);
                  }

                  if (team.teamName.equalsIgnoreCase("blue")) {
                     blues.add(player.username);
                  }
               }
            }
         }

         if (reds.size() > blues.size()) {
            cappingTeam = this.getPlayerEventHandler().getTeam("red");
            capPlayers = reds.size();
            notCapPlayers = blues.size();
         } else if (blues.size() > reds.size()) {
            cappingTeam = this.getPlayerEventHandler().getTeam("blue");
            capPlayers = blues.size();
            notCapPlayers = reds.size();
         }

         if (cappingTeam != null && !cpoint.capturedBy.equalsIgnoreCase(cappingTeam.teamName)) {
            int capRate1 = capPlayers - notCapPlayers;

            for(i1 = 0; i1 < capRate1; ++i1) {
               if (cpoint.captureTick < 240) {
                  cpoint.capturing = cappingTeam.teamName;
                  cpoint.flicker = true;
                  ++cpoint.captureTick;
                  if (cpoint.captureTick % 20 == 0) {
                     GameHelper.playerSoundAt(par1, cpoint.posX, cpoint.posY, cpoint.posZ, "random.click");
                  }
               }

               if (cpoint.captureTick >= 240) {
                  cpoint.capturedBy = cappingTeam.teamName;
                  cpoint.captureTick = 0;
                  cpoint.onCaptured(par1, this, cappingTeam.teamName);
                  break;
               }
            }
         } else {
            cpoint.captureTick = 0;
            cpoint.capturing = "";
         }

         if (cpoint.captureTick > 40) {
            if (cpoint.lastCapturing != cpoint.capturing) {
               if (cpoint.capturedBy != null && cpoint.capturedBy.length() > 0) {
                  GameHelper.sendChatToTeam(this, this.getPlayerEventHandler().getTeam(cpoint.capturedBy), "Цель", "Теряем цель " + cpoint.title.toUpperCase() + "!!");
               }

               cpoint.lastCapturing = cpoint.capturing;
            }
         } else {
            cpoint.lastCapturing = "";
         }
      }

      if (this.capturePointScoreDelay++ > 60) {
         for(i = 0; i < this.capturePoints.size(); ++i) {
            cpoint = (CapturePoint)this.capturePoints.get(i);
            if (cpoint.capturedBy != null && cpoint.capturedBy.length() > 0) {
               Team team = this.playerHandler.getTeam(cpoint.capturedBy);
               int score = team.getTeamObjectInteger("score").intValue();
               team.setTeamObject("score", score + 1);
            }
         }

         this.capturePointScoreDelay = 0;
      }

   }

   public void handleWinningTeam(Team teamWon) {
      this.getPlayerEventHandler().getTeam("Red").playSoundFireworkPerPlayer();
      this.getPlayerEventHandler().getTeam("Blue").playSoundFireworkPerPlayer();
      Team teamRed = this.playerHandler.getTeam("Red");
      int scoreRed = teamRed.getTeamObjectInteger("score").intValue();
      Team teamBlue = this.playerHandler.getTeam("Blue");
      int scoreBlue = teamBlue.getTeamObjectInteger("score").intValue();
      GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "Захват точек");
      if (teamWon == null) {
         GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "ничья!");
      } else {
         GameHelper.sendChatToAll(this, "Игра", "/c " + teamWon.teamColor + "" + EnumChatFormatting.BOLD + "" + teamWon.teamName + " выиграли!");
         GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), teamWon.teamColor + "" + EnumChatFormatting.BOLD + teamWon.teamName.toUpperCase() + " выиграли!");
      }

      GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.ITALIC + "Террористы: " + scoreRed + "    Контр-террористы: " + scoreBlue);
      if (FMLCommonHandler.instance().getSide().isServer()) {
         this.handleParticipationReward(5, 6);
         this.handleTopPlayers(5, "score", 6, true, this.getPlayerEventHandler().getPlayers());
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
      if (par2 != null && par2.length == 1 && this.getPlayerEventHandler().getTeam(par2[0]) != null) {
         team = this.getPlayerEventHandler().getTeam(par2[0]);
      }

      GameHelper.sendChatToAll(this, "Игра", par1.username + " Присоеденился");
      return this.playerHandler.addPlayer(par1, team.teamName);
   }

   public void restartGame() {
      this.setStatus(GameStatus.IDLE);
      this.timerGame = new Timer(this.maxTime, 0);
      this.timerPreGame = new Timer(1, 0);
      this.timerPostGame = new Timer(0, 30);
      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.playerHandler.getTeam("Red").clearTeamObjects();
      this.playerHandler.getTeam("Blue").clearTeamObjects();
      this.clearCapturePoints();
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
      par1.add("Capture Points: " + this.capturePoints.size());
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

      for(int i = 0; i < this.capturePoints.size(); ++i) {
         ((CapturePoint)this.capturePoints.get(i)).saveToFDS("cpoint" + i, par1);
         ((CapturePoint)this.capturePoints.get(i)).writeToFDS(par1);
      }

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
      int i;
      if (this.capturePoints.size() <= 0) {
         for(i = 0; i < 10; ++i) {
            if (par1.hasTag("blcpoint" + i)) {
               CapturePoint cp = CapturePoint.createBlockLocationFromFDS("cpoint" + i, par1);
               this.capturePoints.add(cp.copy());
            }
         }
      }

      for(i = 0; i < this.capturePoints.size(); ++i) {
         ((CapturePoint)this.capturePoints.get(i)).readFromFDS(par1);
      }

   }

   public void onDataSaved(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      par1.setInteger("maxscore", this.maxScore);
      par1.setInteger("maxtime", this.maxTime);
      if (this.playerHandler.lobby != null) {
         this.playerHandler.lobby.saveToFDS("lobby", par1);
      }

      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.playerHandler.getTeam("Red").writeTeamToCompound(par1);
      this.playerHandler.getTeam("Blue").writeTeamToCompound(par1);

      for(int i = 0; i < this.capturePoints.size(); ++i) {
         CapturePoint cp = (CapturePoint)this.capturePoints.get(i);
         cp.saveToFDS("cpoint" + i, par1);
      }

   }

   public void onDataLoaded(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.maxScore = par1.getInteger("maxscore");
      this.maxTime = par1.getInteger("maxtime");
      this.playerHandler.getTeam("Red").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Blue").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.playerHandler.getTeam("Red").clearTeamObjects();
      this.playerHandler.getTeam("Blue").clearTeamObjects();
      this.clearAllPlayerData();
      this.playerHandler.lobby = BlockLocation.createBlockLocationFromFDS("lobby", par1);
      this.capturePoints.clear();

      for(int i = 0; i < 10; ++i) {
         if (par1.hasTag("blcpoint" + i)) {
            CapturePoint cp = CapturePoint.createBlockLocationFromFDS("cpoint" + i, par1);
            this.capturePoints.add(cp);
         }
      }

   }

   public void clearCapturePoints() {
      for(int i = 0; i < this.capturePoints.size(); ++i) {
         ((CapturePoint)this.capturePoints.get(i)).capturedBy = "";
         ((CapturePoint)this.capturePoints.get(i)).captureTick = 0;
      }

   }

   public CapturePoint getCatpurePoint(EntityPlayer par1) {
      for(int i = 0; i < this.capturePoints.size(); ++i) {
         if (((CapturePoint)this.capturePoints.get(i)).isPlayerNear(par1, 5)) {
            return (CapturePoint)this.capturePoints.get(i);
         }
      }

      return null;
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
      return "Domination";
   }

   public void loadIronSightWeapons() {
   }
}
