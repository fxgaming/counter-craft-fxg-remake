package com.ferullogaming.countercraft.game.cbd;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.ChatSymbols;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketCompetitiveAbandon;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataIncreaseValue;
import com.ferullogaming.countercraft.entity.EntityBomb;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.BombPoint;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameHelperEconomy;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.IGameCommandHandler;
import com.ferullogaming.countercraft.game.IGameComponentBomb;
import com.ferullogaming.countercraft.game.IGameComponentEconomy;
import com.ferullogaming.countercraft.game.IGamePlayerHandler;
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.game.Timer;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class CompetitiveBombDefusal extends IGame implements IGameComponentBomb, IGameComponentEconomy {
   public CBDPlayerHandler playerHandler = new CBDPlayerHandler(this);
   public ArrayList bombPoints = new ArrayList();
   public Timer timerPreGame;
   public Timer timerPostGame;
   public Timer timerPreRound;
   public Timer timerRound;
   public Timer timerPostRound;
   public int maxRounds = 30;
   public int maxRoundTime = 3;
   public int maxRoundWins = 16;
   public int currentRound = 0;
   public String roundWinners = "";
   public String roundMVP = "";
   public boolean isBombPlanted = false;
   public int bombPlantedTime = 1200;
   public int economyStartingAmount = 700;
   public boolean isSideSwitched = false;
   public boolean isMatchEnded = false;
   public boolean isRoundEnded = false;
   public ArrayList originalTeamBlue = new ArrayList();
   public ArrayList originalTeamRed = new ArrayList();
   public ArrayList abandonMatch = new ArrayList();
   private CBDCommands commandHandler = new CBDCommands(this);
   private ArrayList leftUsers = new ArrayList();
   private CBDClient clientSide = new CBDClient(this);
   private boolean isRound = false;

   public CompetitiveBombDefusal() {
      super("cbd");
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
            this.playerHandler.getTeam("Red").clearTeamObjects();
            this.playerHandler.getTeam("Blue").clearTeamObjects();
            this.setStatus(GameStatus.PREGAME);
         }

      } else if (this.getStatus() == GameStatus.PREGAME) {
         if (this.getPlayerEventHandler().getPlayers().size() >= 10) {
            this.timerPreGame.updateTimer();
         } else {
            this.timerPreGame.setTimeRemainingSeconds(15);
         }

         if (this.timerPreGame.isTicking() && this.timerPreGame.getTimeRemainingSeconds() < 15 && this.timerPreGame.hasSecondPast()) {
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.countdown");
         }

         if (!this.timerPreGame.isTicking()) {
            GameHelper.unfreezeAllPlayers(this);
            this.getPlayerEventHandler().getTeam("red").setTeamsInventorys(this.getPlayerEventHandler().getTeam("red").teamDefaultLoadout);
            this.getPlayerEventHandler().getTeam("blue").setTeamsInventorys(this.getPlayerEventHandler().getTeam("blue").teamDefaultLoadout);
            GameHelper.refreshPlayersMCLevels(this.playerHandler.getPlayers());
            GameHelperEconomy.setPlayersEconomy(this, this.getPlayerEventHandler().getPlayers(), this.economyStartingAmount);
            this.originalTeamBlue = new ArrayList(this.getPlayerEventHandler().getTeam("blue").getPlayers());
            this.originalTeamRed = new ArrayList(this.getPlayerEventHandler().getTeam("red").getPlayers());
            this.setStatus(GameStatus.GAME);
            this.setupRound(1);
            GameHelper.playerMusicAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.startmusic");
         }

      } else if (this.getStatus() != GameStatus.GAME) {
         if (this.getStatus() == GameStatus.POSTGAME) {
            this.timerPostGame.updateTimer();
            GameHelper.freezeAllPlayers(this);
            this.playerHandler.temporaryData.clear();
            if (!this.timerPostGame.isTicking()) {
               GameHelper.unfreezeAllPlayers(this);
               GameHelper.kickPlayers(this.playerHandler.getPlayers(), "Перезагрузка матча. Перезайдите.");
               this.restartGame();
            }
         }

      } else {
         Team teamRed = this.getPlayerEventHandler().getTeam("red");
         Team teamBlue = this.getPlayerEventHandler().getTeam("blue");
         int teamRedScore = teamRed.getTeamObjectInteger("score").intValue();
         int teamBlueScore = teamBlue.getTeamObjectInteger("score").intValue();
         if (!this.isMatchEnded) {
            if (teamRed.getPlayers().size() < 1 || teamBlue.getPlayers().size() < 1) {
               this.endMatch(teamRed.getPlayers().size() == 0 ? teamBlue : teamRed, true);
               return;
            }

            if (teamBlueScore >= this.maxRoundWins) {
               this.endMatch(teamBlue, true);
               return;
            }

            if (teamRedScore >= this.maxRoundWins) {
               this.endMatch(teamRed, true);
               return;
            }

            if (teamBlueScore == teamRedScore && teamBlueScore == this.maxRoundWins - 1) {
               this.endMatch((Team)null, true);
               return;
            }

            ArrayList list = new ArrayList();
            list.addAll(this.originalTeamBlue);
            list.addAll(this.originalTeamRed);

            for(int i = 0; i < list.size(); ++i) {
               String username = (String)list.get(i);
               int var2 = this.getPlayerGameData(username).getInteger("compRejoin");
               if (var2 > 0) {
                  if (var2 == 1 && !this.abandonMatch.contains(username)) {
                     ServerCloudManager.sendPacket(new PacketCompetitiveAbandon(username));
                     this.abandonMatch.add(username);
                  }
                  this.getPlayerGameData(username).setInteger("compRejoin", var2 - 1);
               }
            }
         }

         if (this.timerPreRound.getTimeRemaining() > 0) {
            this.timerPreRound.updateTimer();
            if (this.timerPreRound.getTimeElapsed() == 2) {
               this.playerHandler.getTeam("Red").tpTeamSetSpawns();
               this.playerHandler.getTeam("Blue").tpTeamSetSpawns();
               this.playerHandler.assignBombKitsToPlayers();
               this.playerHandler.assignBombToPlayers();
               GameHelper.freezeAllPlayers(this);
            }

            if (this.timerPreRound.getTimeRemaining() == 0) {
               GameHelper.unfreezeAllPlayers(this);
            }

         } else if (this.timerRound.getTimeRemaining() > 0) {
            this.timerRound.updateTimer();
            if (this.timerRound.getTimeRemaining() == 0 && !this.isBombPlanted) {
               this.endRound("Blue");
               GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("blue").getPlayers(), 1900);
               GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("red").getPlayers(), 1400);
            } else if (this.playerHandler.getPlayerDeadOnTeam("red") >= this.playerHandler.getTeam("red").getPlayers().size() && !this.isBombPlanted) {
               this.endRound("Blue");
               GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("blue").getPlayers(), 1900);
               GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("red").getPlayers(), 1400);
            } else if (this.playerHandler.getPlayerDeadOnTeam("blue") >= this.playerHandler.getTeam("blue").getPlayers().size()) {
               this.endRound("Red");
               GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("red").getPlayers(), 1900);
               GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("blue").getPlayers(), 1400);
            }
         } else if (this.timerPostRound.getTimeRemaining() > 0) {
            this.timerPostRound.updateTimer();
            if (this.timerPostRound.hasSecondPast()) {
               GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "random.click");
            }

         } else {
            this.setupRound(this.currentRound + 1);
            GameHelper.playerMusicAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "countercraft:match.startmusic");
         }
      }
   }

   public void endMatch(Team par1, boolean par2) {
      if (!this.isMatchEnded) {
         String var1 = "Матч сыгран!";
         Team teamRed;
         if (par1 != null) {
            var1 = par1.teamColor + par1.teamName + " выиграли матч!";
            teamRed = this.getPlayerEventHandler().getTeam("red");
            Team teamBlue = this.getPlayerEventHandler().getTeam("blue");
            if (par1 == teamBlue) {
               this.getPlayerEventHandler().getTeam("Blue").playDefeatGameMusicPerPlayer("red");
               this.getPlayerEventHandler().getTeam("Red").playVictoryGameMusicPerPlayer("blue");
            } else if (par1 == teamRed) {
               this.getPlayerEventHandler().getTeam("Red").playDefeatGameMusicPerPlayer("blue");
               this.getPlayerEventHandler().getTeam("Blue").playVictoryGameMusicPerPlayer("red");
            }
         }

         if (!par2) {
            GameHelper.sendGameNotification(this.playerHandler.getPlayers(), "Invald Match Conditions");
            var1 = "Матч аннулирован.";
         }

         GameHelper.clearDrops(this);
         this.clearBombPlanted();
         this.clearBombPointsData();
         GameHelper.clearPlayersInventory(this.playerHandler.getPlayers());
         GameHelper.refreshPlayersMCLevels(this.playerHandler.getPlayers());
         ((CBDPlayerHandler)this.getPlayerEventHandler()).onRoundRefreshed(this.playerHandler.getPlayers());
         this.playerHandler.getTeam("Red").tpTeamSetSpawns();
         this.playerHandler.getTeam("Blue").tpTeamSetSpawns();
         this.getPlayerEventHandler().getTeam("Red").playSoundFireworkPerPlayer();
         this.getPlayerEventHandler().getTeam("Blue").playSoundFireworkPerPlayer();
         GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), var1);
         teamRed = this.playerHandler.getTeam("Red");
         int scoreRed = teamRed.getTeamObjectInteger("score").intValue();
         Team teamBlue = this.playerHandler.getTeam("Blue");
         int scoreBlue = teamBlue.getTeamObjectInteger("score").intValue();
         GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
         GameHelper.sendChatToAll(this, "Игра", "");
         GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "Соревновательный");

         String name;
         int var2;
         for(int i = 0; i < this.getPlayerEventHandler().getPlayers().size(); ++i) {
            name = (String)this.getPlayerEventHandler().getPlayers().get(i);
            if (this.playerHandler.getPlayerTeam(name) == par1) {
               if (GameHelper.getPlayerGameValue(this, name, "score") > 1) {
                  ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(name, "compRankAddXP", GameHelper.getPlayerGameValue(this, name, "score") / 2));
               }
            } else if (GameHelper.getPlayerGameValue(this, name, "score") > 1) {
               PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(name);
               var2 = data.compRankRequiredXP / 2 - GameHelper.getPlayerGameValue(this, name, "score");
               if (var2 < 0) {
                  var2 = 0;
               }

               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(name, "compRankSubtractXP", var2));
            }
         }

         if (par1 == null) {
            GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.AQUA + "" + EnumChatFormatting.BOLD + "ничья!");
         } else {
            GameHelper.sendChatToAll(this, "Игра", "/c " + par1.teamColor + "" + EnumChatFormatting.BOLD + "" + par1.teamName + " выиграли!");
            GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), par1.teamColor + "" + EnumChatFormatting.BOLD + par1.teamName.toUpperCase() + " выиграли!");
         }

         GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.ITALIC + "Террористы: " + scoreRed + "    Контр-террористы: " + scoreBlue);
         if (FMLCommonHandler.instance().getSide().isServer()) {
            this.handleParticipationReward(10, 6);
            this.handleTopPlayers(3, "kills", 4, true, par1 == null ? this.getPlayerEventHandler().getPlayers() : par1.getPlayers());
            this.handleDropChances(4);
            if (ServerManager.instance().isMatchMakingAccepted) {
               if (par1 != null) {
                  Iterator i$ = par1.getPlayers().iterator();

                  while(i$.hasNext()) {
                     name = (String)i$.next();
                     ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(name, "trophies", 1));
                  }
               }

               ArrayList list = new ArrayList();
               list.addAll(this.originalTeamBlue);
               list.addAll(this.originalTeamRed);

               for(int i = 0; i < list.size(); ++i) {
                  String username = (String)list.get(i);
                  var2 = this.getPlayerGameData(username).getInteger("compRejoin");
                  if (var2 > 1) {
                     ServerCloudManager.sendPacket(new PacketCompetitiveAbandon(username));
                  } else if (!this.abandonMatch.contains(username)) {
                     ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(username, "abandons", 0));
                  }

                  ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(username, "comp", 0));
               }
            }
         }

         GameHelper.sendChatToAll(this, "Игра", "");
         GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "- Спасибо за игру! Вы должны отключится...");
         GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "- Перезагрузка игры...");
         GameHelper.sendChatToAll(this, "Игра", "");
         GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
         this.setStatus(GameStatus.POSTGAME);
         this.isMatchEnded = true;
      }

   }

   public void endRound(String par1) {
      if (!this.isRoundEnded) {
         if (this.timerRound.getTimeRemaining() > 0) {
            this.timerRound.setTimeRemainingSeconds(0);
         }

         System.out.println("Clearing Dead Players for New Round!");
         MinecraftServer.getServer().executeCommand("/lagg clear");
         MinecraftServer.getServer().executeCommand("lagg clear");
         this.leftUsers.clear();
         this.leftUsers = new ArrayList();
         int var1 = this.getPlayerEventHandler().getTeam(par1).getTeamObjectInteger("score").intValue();
         this.getPlayerEventHandler().getTeam(par1).setTeamObject("score", var1 + 1);
         this.roundWinners = par1;
         this.getPlayerEventHandler().getTeam("Blue").playVictoryGameMusicPerPlayer("red");
         this.getPlayerEventHandler().getTeam("Red").playDefeatGameMusicPerPlayer("blue");
         GameHelper.sendGameNotification(this.playerHandler.getPlayers(), this.playerHandler.getTeam(par1).teamColor + par1 + " выиграли раунд!");
         this.isRoundEnded = true;
      }

   }

   public void setupRound(int par1) {
      this.isRoundEnded = false;
      this.currentRound = par1;
      this.timerPreRound = new Timer(0, 15);
      this.timerRound = new Timer(this.maxRoundTime, 0);
      this.timerPostRound = new Timer(0, 10);
      if (this.currentRound == this.maxRounds / 2) {
         GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), "Последний раунд 1 половины");
      }

      if (this.currentRound == this.maxRounds / 2 + 1) {
         this.timerPreRound = new Timer(0, 20);
         this.switchTeams();
      }

      GameHelper.clearDrops(this);
      this.clearBombPlanted();
      this.clearBombPointsData();
      GameHelper.refreshPlayersMCLevels(this.playerHandler.getPlayers());
      ((CBDPlayerHandler)this.getPlayerEventHandler()).onRoundRefreshed(this.playerHandler.getPlayers());
   }

   public boolean handlePlayerJoining(EntityPlayer par1, String... par2) {
      if (!this.isGameSetup()) {
         return false;
      } else if (this.getStatus() == GameStatus.POSTGAME) {
         return false;
      } else {
         String assignedTeam = "red";
         if (par2 != null && par2.length == 1 && par2[0].length() > 0 && (par2[0].equalsIgnoreCase("red") || par2[0].equalsIgnoreCase("blue"))) {
            assignedTeam = par2[0].toLowerCase();
         }

         if (this.getStatus() == GameStatus.GAME) {
            assignedTeam = "red";
            if (!this.originalTeamBlue.contains(par1.username) && !this.originalTeamRed.contains(par1.username)) {
               return false;
            }

            if (this.originalTeamBlue.contains(par1.username)) {
               assignedTeam = "blue";
            }

            if (this.isSideSwitched) {
               if (assignedTeam.equals("blue")) {
                  assignedTeam = "red";
               } else {
                  assignedTeam = "blue";
               }
            }
         }

         if (assignedTeam != null && this.getPlayerEventHandler().getTeam(assignedTeam) != null) {
            Team team = this.getPlayerEventHandler().getTeam(assignedTeam);
            if (this.playerHandler.addPlayer(par1, team.teamName)) {
               GameHelper.sendChatToAll(this, "Игра", par1.username + " присоеденился");
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public void switchTeams() {
      ArrayList tempRedPlayers = new ArrayList(this.getPlayerEventHandler().getTeam("red").getPlayers());
      ArrayList tempBluePlayers = new ArrayList(this.getPlayerEventHandler().getTeam("blue").getPlayers());
      int tempRedScore = this.getPlayerEventHandler().getTeam("red").getTeamObjectInteger("score").intValue();
      int tempBlueScore = this.getPlayerEventHandler().getTeam("blue").getTeamObjectInteger("score").intValue();
      this.getPlayerEventHandler().getTeam("red").getPlayers().clear();
      this.getPlayerEventHandler().getTeam("blue").getPlayers().clear();
      this.getPlayerEventHandler().getTeam("red").setTeamObject("score", tempBlueScore);
      this.getPlayerEventHandler().getTeam("blue").setTeamObject("score", tempRedScore);
      this.getPlayerEventHandler().getTeam("red").getPlayers().addAll(tempBluePlayers);
      this.getPlayerEventHandler().getTeam("blue").getPlayers().addAll(tempRedPlayers);
      this.getPlayerEventHandler().getTeam("red").setTeamsInventorys(this.getPlayerEventHandler().getTeam("red").teamDefaultLoadout);
      this.getPlayerEventHandler().getTeam("blue").setTeamsInventorys(this.getPlayerEventHandler().getTeam("blue").teamDefaultLoadout);
      GameHelperEconomy.setPlayersEconomy(this, this.getPlayerEventHandler().getPlayers(), this.economyStartingAmount);
      GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), "Команды поменялись местами.");
      GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), "Осталась половина времени.");
      GameHelper.sendChatToAll(this, "Игра", "Добавляем 20 секунд.");
      this.isSideSwitched = true;
   }

   public void restartGame() {
      this.setStatus(GameStatus.IDLE);
      this.timerPreGame = new Timer(0, 15);
      this.timerPostGame = new Timer(0, 30);
      this.timerPreRound = new Timer(0, 15);
      this.timerRound = new Timer(this.maxRoundTime, 0);
      this.timerPostRound = new Timer(0, 10);
      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.playerHandler.getTeam("Red").clearTeamObjects();
      this.playerHandler.getTeam("Blue").clearTeamObjects();
      this.clearBombPointsData();
      this.clearBombPlanted();
      this.roundWinners = "";
      this.roundMVP = "";
      this.isMatchEnded = false;
      this.setupRound(0);
      GameManager.instance().onGameRestarted(this);
   }

   public void forceStop() {
      if (this.getPlayerEventHandler().getPlayers().size() > 0) {
         GameHelper.kickPlayers(this.getPlayerEventHandler().getPlayers(), "Игра окончена, Форс-рестарт...");
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
      par1.add("Max Round Wins: " + this.maxRoundWins);
      par1.add("Max Round Time: " + this.maxRoundTime + "m");
      par1.add("Bomb Locations: " + this.bombPoints.size());
   }

   public void sendToClient(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      this.sendPlayerGameDataToClient(par1);
      this.timerPreGame.writeToFDS("pregame", par1);
      this.timerPreRound.writeToFDS("preround", par1);
      this.timerRound.writeToFDS("round", par1);
      this.timerPostRound.writeToFDS("postround", par1);
      this.timerPostGame.writeToFDS("postgame", par1);
      par1.setInteger("round", this.currentRound);
      par1.setBoolean("bombplanted", this.isBombPlanted);
      par1.setInteger("status", this.getStatus().id);
      this.playerHandler.getTeam("Red").writeTeamToCompound(par1);
      this.playerHandler.getTeam("Blue").writeTeamToCompound(par1);
      par1.setString("roundWinners", this.roundWinners);
      par1.setString("roundMVP", this.roundMVP);

      for(int i = 0; i < this.bombPoints.size(); ++i) {
         ((BombPoint)this.bombPoints.get(i)).saveToFDS("cpoint" + i, par1);
         ((BombPoint)this.bombPoints.get(i)).writeToFDS(par1);
      }

   }

   public void loadFromServer(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.loadPlayerGameDataFromServer(par1);
      this.timerPreGame.readFromFDS("pregame", par1);
      this.timerPreRound.readFromFDS("preround", par1);
      this.timerRound.readFromFDS("round", par1);
      this.timerPostRound.readFromFDS("postround", par1);
      this.timerPostGame.readFromFDS("postgame", par1);
      this.currentRound = par1.getInteger("round");
      this.isBombPlanted = par1.getBoolean("bombplanted");
      this.setStatus(GameStatus.get(par1.getInteger("status")));
      this.playerHandler.getTeam("Red").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Blue").readTeamFromFDS(par1);
      this.roundWinners = par1.getString("roundWinners");
      this.roundMVP = par1.getString("roundMVP");
      int i;
      if (this.bombPoints.size() <= 0) {
         for(i = 0; i < 10; ++i) {
            if (par1.hasTag("blcpoint" + i)) {
               BombPoint cp = BombPoint.createBlockLocationFromFDS("cpoint" + i, par1);
               this.bombPoints.add(cp.copy());
            }
         }
      }

      for(i = 0; i < this.bombPoints.size(); ++i) {
         ((BombPoint)this.bombPoints.get(i)).readFromFDS(par1);
      }

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

      for(int i = 0; i < this.bombPoints.size(); ++i) {
         BombPoint cp = (BombPoint)this.bombPoints.get(i);
         cp.saveToFDS("cpoint" + i, par1);
      }

   }

   public void onDataLoaded(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.playerHandler.lobby = BlockLocation.createBlockLocationFromFDS("lobby", par1);
      this.playerHandler.getTeam("Red").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Blue").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Red").removeAllPlayers();
      this.playerHandler.getTeam("Blue").removeAllPlayers();
      this.playerHandler.getTeam("Red").clearTeamObjects();
      this.playerHandler.getTeam("Blue").clearTeamObjects();
      this.clearAllPlayerData();
      this.bombPoints.clear();

      for(int i = 0; i < 10; ++i) {
         if (par1.hasTag("blcpoint" + i)) {
            BombPoint cp = BombPoint.createBlockLocationFromFDS("cpoint" + i, par1);
            this.bombPoints.add(cp);
         }
      }

   }

   public void clearBombPointsData() {
      for(int i = 0; i < this.bombPoints.size(); ++i) {
         ((BombPoint)this.bombPoints.get(i)).isBombPlaneted = false;
      }

   }

   public BombPoint getBombPoint(EntityPlayer par1) {
      for(int i = 0; i < this.bombPoints.size(); ++i) {
         if (((BombPoint)this.bombPoints.get(i)).isPlayerNear(par1, 5)) {
            return (BombPoint)this.bombPoints.get(i);
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
      return "Соревновательный режим";
   }

   public boolean isGameSetup() {
      if (this.getPlayerEventHandler().getTeam("red").teamSpawns.size() > 0 && this.getPlayerEventHandler().getTeam("blue").teamSpawns.size() > 0) {
         return this.getGameMapType() != null;
      } else {
         return false;
      }
   }

   public boolean canPlantBomb(World par1, EntityPlayer par2, ItemStack par3) {
      BombPoint point = this.getBombPoint(par2);
      if (!this.isBombPlanted && point != null && this.timerRound.isTicking() && !this.timerPreRound.isTicking()) {
         return !point.isBombPlaneted;
      } else {
         return false;
      }
   }

   public void onBombPlanted(World par1, EntityPlayer par2) {
      BombPoint point = this.getBombPoint(par2);
      this.isBombPlanted = true;
      if (point != null) {
         point.isBombPlaneted = true;
      }

      GameHelper.increasePlayerGameValue(this, par2.username, "score", 2);
      this.timerRound.setTimeRemainingSeconds(this.getBombFuse() / 20);
      GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), EnumChatFormatting.RED + "Бомба установлена!");
      this.addPlayerEconomy(par2.username, 200);
   }

   public void onBombDefused(World par1, String par2) {
      this.isBombPlanted = false;
      this.endRound("Blue");
      GameHelper.increasePlayerGameValue(this, par2, "score", 2);
      GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), EnumChatFormatting.BLUE + "Бомба установлена!");
      GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("blue").getPlayers(), 2000);
      GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("red").getPlayers(), 1500);
      this.addPlayerEconomy(par2, 200);
      Iterator i$ = this.getPlayerEventHandler().getTeam("blue").getPlayers().iterator();

      while(i$.hasNext()) {
         String name = (String)i$.next();
         PlayerData data = PlayerDataHandler.getPlayerData(name);
         if (!data.isGhost && !par2.equalsIgnoreCase(name)) {
            GameHelper.increasePlayerGameValue(this, name, "score", 1);
         }
      }

   }

   public void onBombExplodes(World par1, EntityPlayer par2) {
      this.isBombPlanted = false;
      this.endRound("Red");
      GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("red").getPlayers(), 2300);
      GameHelperEconomy.addPlayersEconomy(this, this.getPlayerEventHandler().getTeam("blue").getPlayers(), 1600);
      GameHelper.increasePlayerGameValue(this, par2.username, "score", 2);
      Iterator i$ = this.getPlayerEventHandler().getTeam("red").getPlayers().iterator();

      while(i$.hasNext()) {
         String name = (String)i$.next();
         PlayerData data = PlayerDataHandler.getPlayerData(name);
         if (!data.isGhost && !par2.username.equalsIgnoreCase(name)) {
            GameHelper.increasePlayerGameValue(this, name, "score", 1);
         }
      }

   }

   public int getBombFuse() {
      return this.bombPlantedTime;
   }

   public boolean isBombPlanted() {
      return this.isBombPlanted;
   }

   public void clearBombPlanted() {
      if (this.getPlayerEventHandler() != null && this.getPlayerEventHandler().getLobby() != null) {
         BlockLocation lobby = this.getPlayerEventHandler().getLobby();
         World world = MinecraftServer.getServer().getEntityWorld();
         if (world != null) {
            int radius = 500;
            ArrayList list = (ArrayList)world.getEntitiesWithinAABB(EntityBomb.class, AxisAlignedBB.getBoundingBox(lobby.posX - (double)radius, lobby.posY - (double)radius, lobby.posZ - (double)radius, lobby.posX + (double)radius, lobby.posY + (double)radius, lobby.posZ + (double)radius));
            if (!list.isEmpty()) {
               for(int i = 0; i < list.size(); ++i) {
                  ((Entity)list.get(i)).setDead();
               }
            }
         }
      }
      this.isBombPlanted = false;
   }

   public int getItemPrice(ItemStack par1, int par2) {
      if (par2 == ItemManager.m4a1.itemID) {
         return 3200;
      } else if (par2 == ItemManager.ak47.itemID) {
         return 2700;
      } else if (par2 == ItemManager.famas.itemID) {
         return 2350;
      } else if (par2 == ItemManager.fnfal.itemID) {
         return 2200;
      } else if (par2 == ItemManager.galil.itemID) {
         return 2000;
      } else if (par2 == ItemManager.m1911.itemID) {
         return 200;
      } else if (par2 == ItemManager.g18.itemID) {
         return 200;
      } else if (par2 == ItemManager.deagle.itemID) {
         return 850;
      } else if (par2 == ItemManager.tec9.itemID) {
         return 500;
      } else if (par2 == ItemManager.p250.itemID) {
         return 300;
      } else if (par2 == ItemManager.cz75.itemID) {
         return 500;
      } else if (par2 == ItemManager.fiveSeven.itemID) {
         return 600;
      } else if (par2 == ItemManager.r8.itemID) {
         return 900;
      } else if (par2 == ItemManager.p90.itemID) {
         return 2250;
      } else if (par2 == ItemManager.vector.itemID) {
         return 1200;
      } else if (par2 == ItemManager.mac10.itemID) {
         return 1050;
      } else if (par2 == ItemManager.ump45.itemID) {
         return 2200;
      } else if (par2 == ItemManager.awp.itemID) {
         return 4750;
      } else if (par2 == ItemManager.dragunov.itemID) {
         return 5000;
      } else if (par2 == ItemManager.scar20.itemID) {
         return 5000;
      } else if (par2 == ItemManager.ssg08.itemID) {
         return 2200;
      } else if (par2 == ItemManager.nova.itemID) {
         return 1350;
      } else if (par2 == ItemManager.sawedoff.itemID) {
         return 1200;
      } else if (par2 == ItemManager.mag7.itemID) {
         return 1600;
      } else if (par2 == ItemManager.m249.itemID) {
         return 5750;
      } else if (par2 == ItemManager.grenadeFrag.itemID) {
         return 300;
      } else if (par2 == ItemManager.grenadeFire.itemID) {
         return 500;
      } else if (par2 == ItemManager.grenadeFlash.itemID) {
         return 200;
      } else if (par2 == ItemManager.grenadeSmoke.itemID) {
         return 300;
      } else if (par2 == ItemManager.grenadeDecoy.itemID) {
         return 50;
      } else if (par2 == ItemManager.armorKevlar.itemID) {
         return 650;
      } else {
         return par2 == ItemManager.armorHelmet.itemID ? 350 : 5000;
      }
   }

   public int getItemReward(ItemStack par1, int par2) {
      if (par2 == ItemManager.m4a1.itemID) {
         return 250;
      } else if (par2 == ItemManager.ak47.itemID) {
         return 250;
      } else if (par2 == ItemManager.famas.itemID) {
         return 250;
      } else if (par2 == ItemManager.fnfal.itemID) {
         return 250;
      } else if (par2 == ItemManager.galil.itemID) {
         return 230;
      } else if (par2 == ItemManager.m1911.itemID) {
         return 200;
      } else if (par2 == ItemManager.g18.itemID) {
         return 200;
      } else if (par2 == ItemManager.deagle.itemID) {
         return 200;
      } else if (par2 == ItemManager.tec9.itemID) {
         return 200;
      } else if (par2 == ItemManager.p250.itemID) {
         return 200;
      } else if (par2 == ItemManager.cz75.itemID) {
         return 200;
      } else if (par2 == ItemManager.fiveSeven.itemID) {
         return 200;
      } else if (par2 == ItemManager.r8.itemID) {
         return 220;
      } else if (par2 == ItemManager.p90.itemID) {
         return 250;
      } else if (par2 == ItemManager.vector.itemID) {
         return 300;
      } else if (par2 == ItemManager.mac10.itemID) {
         return 300;
      } else if (par2 == ItemManager.ump45.itemID) {
         return 300;
      } else if (par2 == ItemManager.awp.itemID) {
         return 100;
      } else if (par2 == ItemManager.dragunov.itemID) {
         return 300;
      } else if (par2 == ItemManager.scar20.itemID) {
         return 300;
      } else if (par2 == ItemManager.ssg08.itemID) {
         return 300;
      } else if (par2 == ItemManager.nova.itemID) {
         return 400;
      } else if (par2 == ItemManager.sawedoff.itemID) {
         return 400;
      } else if (par2 == ItemManager.mag7.itemID) {
         return 800;
      } else if (par2 == ItemManager.grenadeFrag.itemID) {
         return 300;
      } else if (par2 == ItemManager.grenadeFire.itemID) {
         return 300;
      } else if (par2 == ItemManager.grenadeFlash.itemID) {
         return 0;
      } else if (par2 == ItemManager.grenadeSmoke.itemID) {
         return 0;
      } else {
         return par2 == ItemManager.grenadeDecoy.itemID ? 0 : 300;
      }
   }

   public boolean isBuyMenuAdjusted() {
      return true;
   }

   public void setPlayerEconomy(String par1, int par2) {
      GameHelper.setPlayerGameValue(this, par1, "economy", par2);
   }

   public void addPlayerEconomy(String par1, int par2) {
      GameHelper.increasePlayerGameValue(this, par1, "economy", par2);
      if (GameHelper.getPlayerGameValue(this, par1, "economy") > 10000) {
         this.setPlayerEconomy(par1, 10000);
      }

   }

   public void removePlayerEconomy(String par1, int par2) {
      GameHelper.setPlayerGameValue(this, par1, "economy", GameHelper.getPlayerGameValue(this, par1, "economy") - par2);
   }

   public int getPlayerEconomy(String par1) {
      return GameHelper.getPlayerGameValue(this, par1, "economy");
   }

   public boolean hasPlayerEconomy(String par1, int par2) {
      return GameHelper.getPlayerGameValue(this, par1, "economy") >= par2;
   }

   public void loadIronSightWeapons() {
   }

   public ArrayList getLeftList() {
      return this.leftUsers;
   }
}
