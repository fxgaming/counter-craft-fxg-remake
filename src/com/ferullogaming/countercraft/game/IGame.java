package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataIncreaseValue;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataRandomDrop;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.network.CCPacketVoteData;
import com.ferullogaming.countercraft.network.CCPacketVoteEnd;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public abstract class IGame {
   private static int gameIDCount = 100;
   public boolean isEditing = false;
   private int uniqueGameID;
   private String gameName = "";
   private String gameMap = "";
   private String gameType = "";
   private EnumMapTime mapTime;
   private int voteCooldown;
   private HashMap playerGameData;
   private GameStatus gameStatus;
   private Vote currentVote;

   public IGame(String par1) {
      this.mapTime = EnumMapTime.DAY;
      this.voteCooldown = 0;
      this.playerGameData = new HashMap();
      this.gameType = par1;
   }

   public abstract void onUpdate(World var1);

   public void updateVote() {
      if (this.getCurrentVote() != null) {
         this.getCurrentVote().tick();
         if (this.getCurrentVote().hasVoteEnded) {
            this.getCurrentVote().finalizeVote();
            this.endVote();
         } else if (this.getCurrentVote().voteTime % 5L == 0L) {
            this.sendVoteData(this.getCurrentVote());
         }
      }

   }

   public abstract boolean handlePlayerJoining(EntityPlayer var1, String... var2);

   public abstract void restartGame();

   public abstract void forceStop();

   public abstract void getInformation(ArrayList var1);

   public abstract void sendToClient(FDSTagCompound var1);

   public abstract void loadFromServer(FDSTagCompound var1);

   public abstract void onDataSaved(FDSTagCompound var1);

   public abstract void onDataLoaded(FDSTagCompound var1);

   public void writeBasicToFDS(FDSTagCompound par1) {
      par1.setString("name", this.gameName);
      par1.setString("map", this.gameMap);
      par1.setString("type", this.gameType);
      par1.setInteger("time", this.mapTime.getTimeID());
   }

   public void readBasicFromFDS(FDSTagCompound par1) {
      if (par1.hasTag("name")) {
         this.gameName = par1.getString("name");
      }

      if (par1.hasTag("map")) {
         this.gameMap = par1.getString("map");
      }

      if (par1.hasTag("type")) {
         this.gameType = par1.getString("type");
      }

      if (par1.hasTag("time")) {
         EnumMapTime.getById(par1.getInteger("time"));
      }

   }

   public Object getPotentialPlayerGameDataValue(String par1, String par2) {
      return this.hasPlayerGameData(par1) && this.getPlayerGameData(par1).hasTag(par2) ? this.getPlayerGameData(par1).getObject() : null;
   }

   public FDSTagCompound getPlayerGameData(String par1) {
      if (!this.playerGameData.containsKey(par1)) {
         this.playerGameData.put(par1, new FDSTagCompound(par1));
      }

      return (FDSTagCompound)this.playerGameData.get(par1);
   }

   public boolean hasPlayerGameData(String par1) {
      return this.playerGameData.containsKey(par1);
   }

   public void sendPlayerGameDataToClient(FDSTagCompound par1) {
      if (!this.playerGameData.isEmpty()) {
         FDSTagCompound tag = new FDSTagCompound("playerdata");
         ArrayList usernames = new ArrayList(this.playerGameData.keySet());
         ArrayList players = new ArrayList(this.playerGameData.values());
         tag.setStringArrayList("usernames", usernames);
         Iterator i$ = players.iterator();

         while(i$.hasNext()) {
            FDSTagCompound tag1 = (FDSTagCompound)i$.next();
            tag.setTagCompound(tag1.getTag(), tag1);
         }

         par1.setTagCompound("playerdata", tag);
      }

   }

   public void loadPlayerGameDataFromServer(FDSTagCompound par1) {
      this.clearAllPlayerData();
      if (par1.hasTag("playerdata")) {
         FDSTagCompound tag = par1.getTagCompound("playerdata");
         ArrayList usernames = tag.getStringArrayList("usernames");
         Iterator i$ = usernames.iterator();

         while(i$.hasNext()) {
            String username = (String)i$.next();
            FDSTagCompound tag1 = tag.getTagCompound(username).setTag(username);
            this.playerGameData.put(username, tag1);
         }
      }

   }

   public void clearPlayerData(String par1) {
      this.playerGameData.remove(par1);
   }

   public void clearAllPlayerData() {
      this.playerGameData = new HashMap();
   }

   public boolean isMMAccepted() {
      return FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted;
   }

   public void handleParticipationReward(int par1, int par2Min) {
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "Награда");
      ArrayList players = this.getPlayerEventHandler().getPlayers();
      if (players.size() < par2Min) {
         GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.ITALIC + "  (Не хватает игроков для получения награды)");
      } else {
         GameHelper.sendChatToAll(this, "Игра", "  " + EnumChatFormatting.GREEN + "" + "+" + par1 + " изумрудов");

         for(int i = 0; i < players.size(); ++i) {
            String player = (String)players.get(i);
            if (FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted) {
               PlayerDataCloud clouddata = PlayerDataHandler.getPlayerCloudData(player);
               if (clouddata.boostersActive.size() > 0) {
                  for(int i1 = 0; i1 < clouddata.boostersActive.size(); ++i1) {
                     Booster boost = (Booster)clouddata.boostersActive.get(i1);
                     if (boost.isEmeralds()) {
                        GameHelper.sendChatToAll(this, "Игра", "  " + clouddata.getUsernameFormatted() + EnumChatFormatting.GREEN + " (+" + par1 + " изумрудов) x2 Бустер");
                     }
                  }
               }

               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(player, "emeralds", par1));
            }
         }

      }
   }

   public void handleTopPlayers(int par1, String par2, int par3Min, boolean par4Trophy, ArrayList par5Players) {
      this.handleTopPlayers(par1, par2, par3Min, par4Trophy, par5Players, 5);
   }

   public void handleTopPlayers(int par1, String par2, int par3Min, boolean par4Trophy, ArrayList par5Players, int par6Multiplier) {
      int multiplier = par6Multiplier;
      GameHelper.sendChatToAll(this, "Игра", "");
      GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "Топ " + par1 + " игроков");
      if (par5Players.size() < par3Min) {
         GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.ITALIC + "  (Не хватает игроков для получения награды)");
      }

      ArrayList topPlayers = GameHelper.getTopPlayers(this, par1, par2, par5Players);
      int coins = par6Multiplier * par1;

      for(int i = 0; i < topPlayers.size(); ++i) {
         PlayerDataCloud clouddata = PlayerDataHandler.getPlayerCloudData((String)topPlayers.get(i));
         String var2 = "" + (i + 1) + ". " + clouddata.getUsernameFormatted();
         if (FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted) {
            if (this.getPlayerEventHandler().getPlayers().size() >= par3Min) {
               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue((String)topPlayers.get(i), "emeralds", coins));
               int boosts = 0;
               if (clouddata.boostersActive.size() > 0) {
                  for(int i1 = 0; i1 < clouddata.boostersActive.size(); ++i1) {
                     Booster boost = (Booster)clouddata.boostersActive.get(i1);
                     if (boost.isEmeralds()) {
                        ++boosts;
                     }
                  }
               }

               if (boosts > 0) {
                  var2 = var2 + EnumChatFormatting.GREEN + "" + EnumChatFormatting.ITALIC + " (+" + coins * (boosts + 1) + " изумрудов) " + boosts + " Бустер(ов) активно";
               } else {
                  var2 = var2 + EnumChatFormatting.GREEN + "" + EnumChatFormatting.ITALIC + " (+" + coins + " изумрудов)";
               }
            }

            if (par4Trophy) {
               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue((String)topPlayers.get(i), "trophies", 1));
            }
         }

         GameHelper.sendChatToAll(this, "Игра", "  " + var2);
         coins -= multiplier;
      }

   }

   public void handleDropChances(int par1) {
      if (FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted) {
         int players = this.getPlayerEventHandler().getPlayers().size();
         if (players >= 4) {
            int var1 = (new Random()).nextInt(par1);
            if (players >= 10) {
               var1 += (new Random()).nextInt(par1);
            }

            if (var1 > 0) {
               GameHelper.sendChatToAll(this, "Игра", "");
               GameHelper.sendChatToAll(this, "Игра", EnumChatFormatting.AQUA + "Запрашиваем дроп для " + var1 + " игроков(а)");
               ArrayList drops = GameHelper.getRandomPlayers(this, var1);

               for(int i = 0; i < drops.size(); ++i) {
                  String name = (String)drops.get(i);
                  GameHelper.sendChatToAll(this, "Игра", "  " + EnumChatFormatting.GOLD + name);
                  ServerCloudManager.sendPacket(new PacketPlayerDataRandomDrop(name));
               }
            }
         }
      }

   }

   public abstract IGameClient getClientSide();

   public abstract IGamePlayerHandler getPlayerEventHandler();

   public abstract IGameCommandHandler getCommandHandler();

   public abstract String getDisplayName();

   public abstract void loadIronSightWeapons();

   public String getGameName() {
      return this.gameName;
   }

   public void setGameName(String par1) {
      this.gameName = par1;
   }

   public String getGameMapType() {
      return this.gameMap;
   }

   public void setGameMapType(String par1) {
      this.gameMap = par1;
   }

   public EnumMapTime getMapTime() {
      return this.mapTime;
   }

   public void setMapTime(EnumMapTime mapTime) {
      this.mapTime = mapTime;
   }

   public String getGameType() {
      return this.gameType;
   }

   public GameStatus getStatus() {
      return this.gameStatus;
   }

   public void setStatus(GameStatus par1) {
      this.gameStatus = par1;
   }

   public void enableIronSight(Item item, int var) {
      if (item instanceof ItemGun) {
         ItemGun var3 = (ItemGun)item;
      }

   }

   public Vote getCurrentVote() {
      return this.currentVote;
   }

   public void setCurrentVote(Vote currentVote) {
      this.setVoteCooldown(3600);
      this.currentVote = currentVote;
      this.sendVoteData(this.currentVote);
   }

   public void endVote() {
      this.currentVote = null;
      PacketDispatcher.sendPacketToAllPlayers(CCPacketVoteEnd.buildPacket());

      PlayerData playerData;
      for(Iterator i$ = this.getPlayerEventHandler().getPlayers().iterator(); i$.hasNext(); playerData.hasVoted = false) {
         String username = (String)i$.next();
         playerData = PlayerDataHandler.getPlayerData(username);
      }

   }

   public void sendVoteData(Vote givenVote) {
      Iterator i$ = this.getPlayerEventHandler().getPlayers().iterator();

      while(i$.hasNext()) {
         String username = (String)i$.next();
         PacketDispatcher.sendPacketToPlayer(CCPacketVoteData.buildPacket(givenVote.teamVoting, givenVote.yesVotes, givenVote.noVotes, givenVote.voteType.voteID, givenVote.voteValue, givenVote.voteTime), (Player)GameHelper.getPlayerFromUsername(username));
      }

   }

   public int getVoteCooldown() {
      return this.voteCooldown;
   }

   public void setVoteCooldown(int givenCooldown) {
      this.voteCooldown = givenCooldown;
   }
}
