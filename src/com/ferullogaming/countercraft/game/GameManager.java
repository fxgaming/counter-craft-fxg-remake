package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSHelper;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketServerGameStatus;
import com.ferullogaming.countercraft.game.cas.CasualBombDefusal;
import com.ferullogaming.countercraft.game.cbd.CompetitiveBombDefusal;
import com.ferullogaming.countercraft.game.dom.Domination;
import com.ferullogaming.countercraft.game.ffa.FreeForAll;
import com.ferullogaming.countercraft.game.inf.Infected;
import com.ferullogaming.countercraft.game.tdm.TeamDeathMatch;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class GameManager {
   public HashMap gamesHosted = new HashMap();
   public IGame matchMakingGame = null;
   @SideOnly(Side.CLIENT)
   public IGame currentClientGame;
   public String serverOnUUID;
   public String saveLocation = "";
   private int delayRestartMMGame = 0;
   private int delayPacket = 0;

   public GameManager() {
      this.saveLocation = CounterCraft.instance.folderLocation + "/cgames/";
      if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
         this.saveLocation = CounterCraft.instance.folderLocation + "/sgames/";
      }

   }

   public static GameManager instance() {
      return CounterCraft.getGameManager();
   }

   public void onServerUpdate(MinecraftServer par1) {
      if (this.gamesHosted.size() > 0) {
         if (FMLCommonHandler.instance().getSide().isServer()) {
            if (Calendar.getInstance().getTime().getTime() % 500L == 0L) {
               Iterator i$ = par1.getEntityWorld().playerEntities.iterator();

               while(i$.hasNext()) {
                  Object currentPlayer = i$.next();
                  GameHelper.refreshPlayerConnectionInfo(MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(((EntityPlayer)currentPlayer).username));
               }
            }

            if (ServerManager.instance().isMatchMakingAccepted) {
               if (this.delayPacket++ > 2) {
                  ServerCloudManager.sendPacket(new PacketServerGameStatus());
                  this.delayPacket = 0;
               }

               if (this.matchMakingGame != null) {
                  this.matchMakingGame.onUpdate(par1.worldServers[0]);
                  this.matchMakingGame.setVoteCooldown(this.matchMakingGame.getVoteCooldown() - 1);
                  if (this.matchMakingGame.getPlayerEventHandler().getPlayers().size() <= 0) {
                     ++this.delayRestartMMGame;
                  }

                  if (!ServerManager.instance().isMatchRestartedShutdown) {
                     if (this.matchMakingGame.getStatus() == GameStatus.POSTGAME) {
                        ++this.delayRestartMMGame;
                        if (this.matchMakingGame.getPlayerEventHandler().getPlayers().size() > 0) {
                           this.delayRestartMMGame = 0;
                        }
                     }

                     if (this.delayRestartMMGame > 10) {
                        System.out.println("Переназначение ММ игры. Ожидание переназначения...");
                        this.matchMakingGame = null;
                        this.delayRestartMMGame = 0;
                     }
                  }

                  return;
               }
            }
         }

         ArrayList games = new ArrayList(this.gamesHosted.values());
         Iterator i$ = games.iterator();

         while(i$.hasNext()) {
            IGame game = (IGame)i$.next();
            game.onUpdate(par1.worldServers[0]);
            if (game.getVoteCooldown() > 0) {
               game.setVoteCooldown(game.getVoteCooldown() - 1);
            }

            if (FMLCommonHandler.instance().getSide() == Side.SERVER && ServerManager.instance().isMatchMakingAccepted && game.getPlayerEventHandler().getPlayers().size() > 0 && this.matchMakingGame == null) {
               this.matchMakingGame = game;
               System.out.println("Match Making Game Assigned. [game=" + game.getGameType() + ", map=" + game.getGameMapType() + "]");
               break;
            }
         }
      }

   }

   public void onGameRestarted(IGame game) {
      if (FMLCommonHandler.instance().getSide() == Side.SERVER && ServerManager.instance().isMatchMakingAccepted && game != null && game == this.matchMakingGame) {
         if (ServerManager.instance().isMatchRestartedShutdown) {
            ServerManager.instance().shutdownServer("Игра окончена.");
         } else {
            this.delayRestartMMGame = 100;
         }
      }
      MinecraftServer.getServer().executeCommand("/lagg clear");
      MinecraftServer.getServer().executeCommand("lagg clear");
   }

   public void onServerStarted() {
      System.out.println("Loading Potential Games...");
      File f1 = new File(this.saveLocation);
      f1.mkdirs();
      ArrayList fileNames = new ArrayList(Arrays.asList(f1.list()));

      for(int i = 0; i < fileNames.size(); ++i) {
         File gameFile = new File(this.saveLocation + (String)fileNames.get(i));
         FDSTagCompound tag = FDSHelper.loadFDSTagCompound(gameFile);
         String gameName = tag.getString("name");
         String gameType = tag.getString("type");
         String gameMap = tag.getString("map");
         EnumMapTime gameTime = EnumMapTime.getById(tag.getInteger("time"));
         IGame potentalGame = this.getGameObject(gameType);
         if (potentalGame != null) {
            potentalGame.setGameName(gameName);
            potentalGame.onDataLoaded(tag);
            potentalGame.setGameMapType(gameMap);
            potentalGame.setMapTime(gameTime);
            this.gamesHosted.put(gameName, potentalGame);
            System.out.println("Loaded Game '" + gameName + "'.");
         }
      }

      Block.vine.setTickRandomly(false);
      System.out.println("Potential Games Loaded!");
   }

   public void onServerStopped() {
      this.saveGameData();
      this.gamesHosted.clear();
   }

   public void saveGameData() {
      ArrayList games = new ArrayList(this.gamesHosted.values());

      IGame game;
      FDSTagCompound tag;
      for(Iterator i$ = games.iterator(); i$.hasNext(); FDSHelper.saveFDSTagCompound(this.saveLocation + game.getGameName(), tag)) {
         game = (IGame)i$.next();
         game.forceStop();
         tag = new FDSTagCompound(game.getGameName());

         try {
            game.onDataSaved(tag);
         } catch (Exception var6) {
            System.out.println("Failed to save game '" + game.getGameName() + "' All data lost...");
         }
      }

   }

   public void onPlayerRespawn(EntityPlayer player) {
      IGame game = this.getPlayerGame(player);
      PlayerData playerData = PlayerDataHandler.getPlayerData(player);
      if (game != null) {
         game.getPlayerEventHandler().onPlayerRespawn(player);
      }

      if (playerData != null) {
         playerData.setRandomSkindex();
      }
   }

   public boolean isPlayerInGame(EntityPlayer par1) {
      if (par1.worldObj.isRemote) {
         return this.currentClientGame != null;
      } else {
         return this.getPlayerGame(par1) != null;
      }
   }

   public boolean isSameGamePresences(EntityPlayer par1, EntityPlayer par2) {
      return this.getPlayerGame(par1) != null && this.getPlayerGame(par2) != null ? this.getPlayerGame(par1).getGameName().equals(this.getPlayerGame(par2).getGameName()) : false;
   }

   public IGame getPlayerGame(EntityPlayer par1) {
      return par1.worldObj.isRemote && FMLCommonHandler.instance().getSide() == Side.CLIENT ? this.currentClientGame : this.getPlayerGame(par1.username);
   }

   public IGame getPlayerGame(String par1) {
      ArrayList games = new ArrayList(this.gamesHosted.values());
      Iterator i$ = games.iterator();

      IGame game;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         game = (IGame)i$.next();
      } while(!game.getPlayerEventHandler().isPlayerPresent(par1));

      return game;
   }

   public boolean assignPlayerToGame(EntityPlayer player, String par2, String... par3) {
      IGame game = this.getGameFromName(par2);
      return game != null ? game.handlePlayerJoining(player, par3) : false;
   }

   public boolean assignPlayerToGame(EntityPlayer player, IGame par2, String... par3) {
      if (par2 != null) {
         return par2.handlePlayerJoining(player, par3);
      } else {
         System.out.println("Player " + player.username + " tried to join (E)");
         return false;
      }
   }

   public IGame getGameFromTypeAndMap(String par1, String par2) {
      ArrayList games = new ArrayList(this.gamesHosted.values());
      Iterator i$ = games.iterator();

      IGame game;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         game = (IGame)i$.next();
      } while(!game.getGameType().equalsIgnoreCase(par1) || !game.getGameMapType().equalsIgnoreCase(par2));

      return game;
   }

   public IGame getGameFromName(String par1) {
      ArrayList games = new ArrayList(this.gamesHosted.values());
      Iterator i$ = games.iterator();

      IGame game;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         game = (IGame)i$.next();
      } while(!game.getGameName().equals(par1));

      return game;
   }

   public IGame getGameObject(String par1) {
      try {
         return (IGame)this.getGameReference(par1).newInstance();
      } catch (Exception var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public Class getGameReference(String par1) {
      if (par1.equals("tdm")) {
         return TeamDeathMatch.class;
      } else if (par1.equals("dom")) {
         return Domination.class;
      } else if (par1.equals("cbd")) {
         return CompetitiveBombDefusal.class;
      } else if (par1.equals("cas")) {
         return CasualBombDefusal.class;
      } else if (par1.equals("inf")) {
         return Infected.class;
      } else {
         return par1.equals("ffa") ? FreeForAll.class : null;
      }
   }
}
