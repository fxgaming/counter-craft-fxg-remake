package com.ferullogaming.countercraft.game.inf;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.ChatSymbols;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataIncreaseValue;
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
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class Infected extends IGame {
   public int maxTime = 8;
   public int maxTimePre = 30;
   public BlockLocation lobby;
   public Timer timerPreGame;
   public Timer timerGame;
   public Timer timerPostGame;
   public int infectedReleaseTime = 5;
   public ArrayList itemSpawns = new ArrayList();
   public boolean isEnviormentSet = false;
   public boolean isNight = false;
   public boolean isRaining = false;
   public boolean isThunder = false;
   public boolean toggleNight = true;
   public boolean toggleRain = true;
   public ArrayList lastAlive = new ArrayList();
   private INFCommands commandHandler = new INFCommands(this);
   private INFPlayerHandler playerHandler = new INFPlayerHandler(this);
   private INFClient clientSide = new INFClient(this);

   public Infected() {
      super("inf");
      this.restartGame();
   }

   public void onUpdate(World par1) {
      this.updateVote();
      if (this.getPlayerEventHandler().getPlayers().size() > 0) {
         if (!this.isEnviormentSet) {
            Random rand = new Random();
            this.isNight = rand.nextInt(3) == 0;
            this.isRaining = rand.nextInt(3) == 0;
            this.isThunder = rand.nextInt(3) == 0;
            this.isEnviormentSet = true;
         }

         par1.setWorldTime(this.isNight ? 14000L : 2000L);
         par1.getWorldInfo().setRaining(false);
         if (this.isRaining) {
            par1.getWorldInfo().setRaining(true);
            par1.getWorldInfo().setRainTime(18000);
            if (this.isThunder) {
               par1.getWorldInfo().setThundering(true);
               par1.getWorldInfo().setThunderTime(18000);
            }
         }
      }

      if (this.getStatus() != GameStatus.IDLE && this.getPlayerEventHandler().getPlayers().size() == 0) {
         this.restartGame();
         par1.setWorldTime(2000L);
         par1.getWorldInfo().setRaining(false);
         par1.getWorldInfo().setThundering(false);
      } else if (this.getStatus() == GameStatus.IDLE) {
         if (this.getPlayerEventHandler().getPlayers().size() > 0) {
            this.setStatus(GameStatus.PREGAME);
            GameHelper.clearDrops(this);
         }

      } else if (this.getStatus() == GameStatus.PREGAME) {
         if (this.getPlayerEventHandler().getPlayers().size() >= this.playerHandler.minPlayersToStart) {
            this.timerPreGame.updateTimer();
         } else {
            this.timerPreGame.setTimeRemainingSeconds(this.maxTimePre);
         }

         if (this.timerPreGame.isTicking() && this.timerPreGame.getTimeRemainingSeconds() <= 10 && this.timerPreGame.hasSecondPast()) {
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.harp");
            GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.hat");
         }

         if (!this.timerPreGame.isTicking()) {
            this.setStatus(GameStatus.GAME);
            GameHelper.clearDrops(this);
            GameHelper.refreshPlayersMCLevels(this.playerHandler.getPlayers());
            this.playerHandler.setPatientZero();
            this.playerHandler.getTeam("Living").tpTeamRandomSpawns();
            GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Пациент ЗЕРО выбран!");
            GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Запуск через " + this.infectedReleaseTime + " секунд!");
            this.playerHandler.getTeam("Living").setTeamsInventorys(this.playerHandler.getTeam("Living").teamDefaultLoadout.copy());
            this.playerHandler.getTeam("Dead").setTeamsInventorys(this.playerHandler.getTeam("Dead").teamDefaultLoadout.copy());
            GameHelper.sendGameNotification(this.playerHandler.getTeam("Living").getPlayers(), "Собирайте предметы и убивайте!");
            GameHelper.sendGameNotification(this.playerHandler.getTeam("Dead").getPlayers(), "Собирайте бустеры и заражайте других!");
            this.updateItemGunSpawns(par1);
         }

      } else if (this.getStatus() == GameStatus.GAME) {
         this.timerGame.updateTimer();
         if (!par1.isRemote) {
            this.updateItemSpawns(par1);
         }

         if (this.playerHandler.getPatientZero() == null && this.playerHandler.getTeam("Dead").getPlayers().size() <= 0) {
            this.playerHandler.setPatientZero();
            GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "Новый пациент ЗЕРО выбран!");
         } else {
            if (this.timerGame.getTimeElapsedSeconds() < this.infectedReleaseTime) {
               GameHelper.freezePlayer(this.playerHandler.getPatientZero());
               if (this.timerGame.hasSecondPast()) {
                  GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.bd");
                  GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "запусе через " + (this.infectedReleaseTime - this.timerGame.getTimeElapsedSeconds()));
               }
            }

            if (this.timerGame.getTimeElapsedSeconds() == this.infectedReleaseTime && this.timerGame.hasSecondPast()) {
               GameHelper.unfreezePlayer(this.playerHandler.getPatientZero());
               GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Пациент ЗЕРО вышел на охоту!");
               GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "mob.enderdragon.growl");
               GameHelper.sendGameNotification(this.playerHandler.getTeam("Dead").getPlayers(), "Вы свободны! Распространяйте инфекцию!");
            }

            if (this.timerGame.isTicking() && this.timerGame.getTimeRemainingSeconds() <= 30 && this.timerGame.hasSecondPast()) {
               GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.harp");
               GameHelper.playerSoundAtPlayers(par1, this.getPlayerEventHandler().getPlayers(), "note.hat");
            }

            if (this.timerGame.isTicking() && this.playerHandler.getTeam("Living").getPlayers().size() <= 0) {
               this.handleWinningTeam(this.playerHandler.getTeam("Dead"));
               this.setStatus(GameStatus.POSTGAME);
               GameHelper.freezeAllPlayers(this);
               GameHelper.clearDrops(this);
            } else {
               if (!this.timerGame.isTicking()) {
                  this.handleWinningTeam(this.playerHandler.getTeam("Living"));
                  this.setStatus(GameStatus.POSTGAME);
                  GameHelper.freezeAllPlayers(this);
                  GameHelper.clearDrops(this);
               }

            }
         }
      } else {
         if (this.getStatus() == GameStatus.POSTGAME) {
            this.timerPostGame.updateTimer();
            if (this.timerPostGame.getTimeRemainingSeconds() > 6 && this.playerHandler.getPlayers().size() <= 1) {
               this.timerPostGame.setTimeRemainingSeconds(5);
            }

            if (!this.timerPostGame.isTicking()) {
               GameHelper.unfreezeAllPlayers(this);
               GameHelper.kickPlayers(this.playerHandler.getPlayers(), "Перезапуск игры.");
               this.restartGame();
            }
         }

      }
   }

   public void updateItemSpawns(World par1) {
      for(int i = 0; i < this.itemSpawns.size(); ++i) {
         ItemSpawn itemSpawn = (ItemSpawn)this.itemSpawns.get(i);
         itemSpawn.onUpdate(par1, this);
      }

   }

   public void updateItemGunSpawns(World par1) {
      for(int i = 0; i < this.itemSpawns.size(); ++i) {
         ItemSpawn itemSpawn = (ItemSpawn)this.itemSpawns.get(i);
         itemSpawn.forceSpawn(par1);
      }

   }

   public void handleWinningTeam(Team teamWon) {
      GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", "/c " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + "Заражение");
      GameHelper.sendChatToAll(this, "Игра", "/c " + teamWon.teamColor + "" + EnumChatFormatting.BOLD + "" + teamWon.teamName + " выиграли!");
      GameHelper.sendGameNotification(this.getPlayerEventHandler().getPlayers(), teamWon.teamColor + "" + EnumChatFormatting.BOLD + teamWon.teamName.toUpperCase() + " выиграли!");
      if (FMLCommonHandler.instance().getSide().isServer()) {
         this.handleParticipationReward(5, 5);
         if (FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted && this.getPlayerEventHandler().getPlayers().size() >= 5) {
            PlayerDataCloud clouddata = PlayerDataHandler.getPlayerCloudData(this.playerHandler.patientZero);
            if (this.playerHandler.getPlayers().contains(this.playerHandler.patientZero) && clouddata != null) {
               int reward = 5;
               GameHelper.sendChatToAll(this, "Игра", "");
               GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "награда Пациента ЗЕРО");
               int amount = reward;
               String boosterTag = "";
               if (clouddata.boostersActive.size() > 0) {
                  boosterTag = " (x2 Изумрудный(е) бустер(ы) активен(ы))";

                  for(int i1 = 0; i1 < clouddata.boostersActive.size(); ++i1) {
                     Booster boost = (Booster)clouddata.boostersActive.get(i1);
                     if (boost.isEmeralds()) {
                        amount += reward;
                     }
                  }
               }

               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(this.playerHandler.patientZero, "emeralds", amount));
               GameHelper.sendChatToAll(this, "Игра", "  " + EnumChatFormatting.GREEN + "" + "+" + reward + " Изумрудов" + boosterTag);
            }
         }

         Collections.reverse(this.lastAlive);
         this.handleTopPlayers(3, (String)null, 0, true, this.lastAlive, 4);
         this.handleDropChances(3);
      }

      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "- Спасибо за игру! Вы должны отключится.");
      GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.RED + "- Перезагрузка матча...");
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
   }

   public boolean handlePlayerJoining(EntityPlayer par1, String... par2) {
      Team team = this.playerHandler.getTeam("Living");
      if (!this.isGameSetup()) {
         return false;
      } else {
         if (this.getStatus() == GameStatus.GAME) {
            team = this.playerHandler.getTeam("Dead");
         }

         GameHelper.sendChatToAll(this, "Игра", par1.username + " присоеденился");
         return this.playerHandler.addPlayer(par1, team.teamName);
      }
   }

   public boolean isGameSetup() {
      if (this.getPlayerEventHandler().getTeam("Dead").teamSpawns.size() > 0 && this.getPlayerEventHandler().getTeam("Living").teamSpawns.size() > 0) {
         return this.getGameMapType() != null;
      } else {
         return false;
      }
   }

   public void restartGame() {
      this.setStatus(GameStatus.IDLE);
      this.timerPreGame = new Timer(0, this.maxTimePre);
      this.timerGame = new Timer(this.maxTime, 0);
      this.timerPostGame = new Timer(0, 30);
      this.clearAllPlayerData();
      this.isEnviormentSet = false;
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
      par1.add("Lobby: " + this.lobby);
      par1.add("Red Team: " + this.getPlayerEventHandler().getTeam("Dead"));
      par1.add("Blue Team: " + this.getPlayerEventHandler().getTeam("Living"));
      par1.add("Max Time: " + this.maxTime + "m");
   }

   public void sendToClient(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      this.sendPlayerGameDataToClient(par1);
      this.timerPreGame.writeToFDS("pregame", par1);
      this.timerGame.writeToFDS("game", par1);
      this.timerPostGame.writeToFDS("postgame", par1);
      par1.setInteger("status", this.getStatus().id);
      this.playerHandler.getTeam("Dead").writeTeamToCompound(par1);
      this.playerHandler.getTeam("Living").writeTeamToCompound(par1);

      for(int i = 0; i < this.itemSpawns.size(); ++i) {
         ItemSpawn spawn = (ItemSpawn)this.itemSpawns.get(i);
         spawn.saveToFDS("ispawn" + i, par1);
         spawn.writeToFDS("ispawn" + i, par1);
      }

   }

   public void loadFromServer(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.loadPlayerGameDataFromServer(par1);
      this.timerPreGame.readFromFDS("pregame", par1);
      this.timerGame.readFromFDS("game", par1);
      this.timerPostGame.readFromFDS("postgame", par1);
      this.setStatus(GameStatus.get(par1.getInteger("status")));
      this.playerHandler.getTeam("Dead").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Living").readTeamFromFDS(par1);
      int i;
      if (this.itemSpawns.size() <= 0) {
         for(i = 0; i < 50; ++i) {
            if (par1.hasTag("blispawn" + i)) {
               ItemSpawn cp = ItemSpawn.createBlockLocationFromFDS("ispawn" + i, par1);
               this.itemSpawns.add(cp);
            }
         }
      }

      for(i = 0; i < this.itemSpawns.size(); ++i) {
         ((ItemSpawn)this.itemSpawns.get(i)).readFromFDS("ispawn" + i, par1);
      }

   }

   public void onDataSaved(FDSTagCompound par1) {
      this.writeBasicToFDS(par1);
      par1.setInteger("maxtime", this.maxTime);
      par1.setBoolean("togglenight", this.toggleNight);
      par1.setBoolean("togglerain", this.toggleRain);
      if (this.lobby != null) {
         this.lobby.saveToFDS("lobby", par1);
      }

      this.playerHandler.getTeam("Dead").removeAllPlayers();
      this.playerHandler.getTeam("Living").removeAllPlayers();
      this.playerHandler.getTeam("Dead").writeTeamToCompound(par1);
      this.playerHandler.getTeam("Living").writeTeamToCompound(par1);

      for(int i = 0; i < this.itemSpawns.size(); ++i) {
         ItemSpawn spawn = (ItemSpawn)this.itemSpawns.get(i);
         spawn.saveToFDS("ispawn" + i, par1);
      }

   }

   public void onDataLoaded(FDSTagCompound par1) {
      this.readBasicFromFDS(par1);
      this.maxTime = par1.getInteger("maxtime");
      this.toggleNight = !par1.hasTag("togglenight") || par1.getBoolean("togglenight");
      this.toggleRain = !par1.hasTag("togglerain") || par1.getBoolean("togglerain");
      this.lobby = BlockLocation.createBlockLocationFromFDS("lobby", par1);
      this.playerHandler.getTeam("Dead").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Living").readTeamFromFDS(par1);
      this.playerHandler.getTeam("Dead").removeAllPlayers();
      this.playerHandler.getTeam("Living").removeAllPlayers();
      this.clearAllPlayerData();
      this.itemSpawns.clear();

      for(int i = 0; i < 50; ++i) {
         if (par1.hasTag("blispawn" + i)) {
            ItemSpawn cp = ItemSpawn.createBlockLocationFromFDS("ispawn" + i, par1);
            this.itemSpawns.add(cp);
         }
      }

   }

   public ItemSpawn getClosestItremSpawn(Entity par1, int par2) {
      for(int i = 0; i < this.itemSpawns.size(); ++i) {
         ItemSpawn var1 = (ItemSpawn)this.itemSpawns.get(i);
         if (var1.isPlayerNear(par1, par2)) {
            return var1;
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
      return "Infected";
   }

   public void loadIronSightWeapons() {
   }
}
