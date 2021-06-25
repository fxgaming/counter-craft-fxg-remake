package com.ferullogaming.countercraft.game.ffa;

import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.ChatSymbols;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataIncreaseValue;
import com.ferullogaming.countercraft.damagesource.DamageSourceBullet;
import com.ferullogaming.countercraft.damagesource.DamageSourceCC;
import com.ferullogaming.countercraft.damagesource.DamageSourceCCFire;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGamePlayerHandler;
import com.ferullogaming.countercraft.game.KillFeedMessage;
import com.ferullogaming.countercraft.game.KilledMessage;
import com.ferullogaming.countercraft.game.Loadout;
import com.ferullogaming.countercraft.game.Team;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FFAPlayerHandler extends IGamePlayerHandler {
   public static final int MAX_BUYTIME = 400;
   public static final int MAX_SPAWNPROTECTION = 150;
   public BlockLocation lobby;
   public HashMap playerLoadouts = new HashMap();
   public ArrayList randomSpawns = new ArrayList();

   public FFAPlayerHandler(IGame par1) {
      super(par1);
      this.addTeam(new Team("Players", 8, EnumChatFormatting.BLUE));
   }

   public void onPlayerJoined(EntityPlayer par1, String par2Team) {
      Team team = this.getTeam(par2Team);
      par1.inventory.clearInventory(-1, -1);
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "Counter Craft");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "/c " + EnumChatFormatting.ITALIC + "Welcome " + par1.username + " to Free for All");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "/c " + team.teamColor + "" + EnumChatFormatting.BOLD + "Assigned to team " + par2Team + "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.teleportPlayer(par1, team.getRandomSpawn());
      GameHelper.refreshPlayerMCLevels(par1);
      GameHelper.sendGameNotification(par1.username, "Welcome to Free for All");
      if (team.teamDefaultLoadout != null) {
         GameHelper.setPlayerInventory(par1, team.teamDefaultLoadout.copy());
         this.playerLoadouts.put(par1.username, team.teamDefaultLoadout.copy());
      }

      GameHelper.setPlayerGameValue(this.getGame(), par1.username, "spawnPro", 150);
      GameHelper.setPlayerGameValue(this.getGame(), par1.username, "buyTime", 400);
   }

   public void onPlayerExit(EntityPlayer par1) {
      if (par1 != null) {
         if (this.getGame().getPlayerGameData(par1.username).getInteger("playTime") > 600 && FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted) {
            ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(par1.username, "games", 1));
         }

         this.getGame().clearPlayerData(par1.username);
         GameHelper.sendChatToAll(this.getGame(), "Game", par1.username + " left");
         GameHelper.teleportPlayer(par1, this.lobby);
         par1.inventory.clearInventory(-1, -1);
         this.playerLoadouts.remove(par1.username);
      }

   }

   public void onPlayerUpdate(EntityPlayer par1) {
      if (this.getGame().getStatus() == GameStatus.GAME) {
         GameHelper.increasePlayerGameValue(this.getGame(), par1.username, "playTime");
         if (GameHelper.getPlayerGameValue(this.getGame(), par1.username, "buyTime") > 0) {
            GameHelper.decreasePlayerGameValue(this.getGame(), par1.username, "buyTime");
         }
      }

      int lastSpawnProtection = GameHelper.getPlayerGameValue(this.getGame(), par1.username, "spawnPro");
      if (lastSpawnProtection > 0) {
         GameHelper.decreasePlayerGameValue(this.getGame(), par1.username, "spawnPro");
         if (par1.lastTickPosX != par1.posX || par1.lastTickPosZ != par1.posZ || par1.isSneaking() || par1.posY > par1.lastTickPosY) {
            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "spawnPro", 0);
         }
      }

   }

   public void onPlayerDeath(EntityPlayer par1, DamageSource par2) {
      if (!par1.worldObj.isRemote) {
         this.getPlayerTeam(par1);
         Team killerTeam = null;
         Entity entitySource = par2.getEntity();
         if (entitySource != null && entitySource instanceof EntityPlayer) {
            EntityPlayer playerSource = (EntityPlayer)entitySource;
            this.getPlayerTeam(playerSource);
            if (this.getGame().getStatus() == GameStatus.GAME && !playerSource.username.equalsIgnoreCase(par1.username)) {
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "kills");
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "killsCL");
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "score");
               if (this.getGame().isMMAccepted()) {
                  ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(playerSource.username, "kills", 1));
               }

               FreeForAll ffa = (FreeForAll)this.getGame();
               if (ffa.firstBlood == null || ffa.firstBlood.length() <= 0) {
                  GameHelper.sendChatToAll(this.getGame(), "Game", EnumChatFormatting.DARK_RED + playerSource.username + " drew First Blood!");
                  GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "score");
                  ffa.firstBlood = playerSource.username;
                  if (this.getGame().isMMAccepted()) {
                     ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(playerSource.username, "firstblood", 1));
                  }
               }
            }

            if (par2 instanceof DamageSourceCC) {
               DamageSourceCC csource = (DamageSourceCC)par2;
               int meta = 0;
               if (par2 instanceof DamageSourceBullet) {
                  DamageSourceBullet bsource = (DamageSourceBullet)par2;
                  if (bsource.isHeadshot()) {
                     meta = 1;
                  }

                  if (bsource.isWallbang()) {
                     meta = 2;
                  }

                  if (bsource.isHeadshot() && bsource.isWallbang()) {
                     meta = 3;
                  }
               }

               if (par2 instanceof DamageSourceCCFire) {
                  meta = 4;
               }

               KillFeedMessage msg = new KillFeedMessage(this.getPlayerTeam(playerSource).teamColor + playerSource.username, this.getPlayerTeam(par1).teamColor + par1.username, csource.getWeaponUsed(), meta);
               GameHelper.sendKillFeedMessage(this.getGame(), msg);
               KilledMessage msg1 = new KilledMessage(playerSource.username, csource.getWeaponUsed(), (int)playerSource.getHealth());
               GameHelper.sendKilledMessage(par1.username, msg1, false);
            }
         }

         if (this.getGame().getStatus() == GameStatus.GAME) {
            GameHelper.increasePlayerGameValue(this.getGame(), par1.username, "deaths");
            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "killsCL", 0);
            if (this.getGame().isMMAccepted()) {
               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(par1.username, "deaths", 1));
            }
         }
      }

   }

   public void onPlayerRespawn(EntityPlayer par1) {
      Team team = this.getPlayerTeam(par1);
      GameHelper.teleportPlayer(par1, this.randomSpawns.size() > 0 ? this.getSafestRandomSpawn(par1.worldObj) : team.getRandomSpawn());
      GameHelper.setPlayerGameValue(this.getGame(), par1.username, "spawnPro", 150);
      GameHelper.setPlayerGameValue(this.getGame(), par1.username, "buyTime", 400);
      if (this.getPlayerLoadout(par1.username).hasItems()) {
         GameHelper.setPlayerInventory(par1, this.getPlayerLoadout(par1.username).copy());
      }

   }

   public boolean onPlayerDamaged(EntityPlayer par1, DamageSource par2) {
      Entity entitySource = par2.getEntity();
      if (entitySource != null && entitySource instanceof EntityPlayer) {
         EntityPlayer playerSource = (EntityPlayer)entitySource;
         if (GameHelper.getPlayerGameValue(this.getGame(), playerSource.username, "spawnPro") > 0) {
            GameHelper.setPlayerGameValue(this.getGame(), playerSource.username, "spawnPro", 0);
         }

         if (par1.username.equalsIgnoreCase(playerSource.username)) {
            return false;
         } else {
            return GameHelper.getPlayerGameValue(this.getGame(), par1.username, "spawnPro") > 0;
         }
      } else {
         return false;
      }
   }

   public int getMaxPlayers() {
      return 8;
   }

   public void onPlayerSwitchTeam(EntityPlayer par1) {
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Game", "You can't switch teams in Free for All.");
   }

   public ArrayList onPlayerDeathItemsDropped(EntityPlayer par1, ArrayList par2) {
      return null;
   }

   public boolean allowItemTossed(EntityPlayer par1, ItemStack par2) {
      par1.inventory.addItemStackToInventory(par2);
      GameHelper.setPlayerInventoryOrganized(par1);
      return false;
   }

   public boolean allowItemPickup(EntityPlayer par1, ItemStack par2, EntityItem par3) {
      return false;
   }

   public void onBuyMenuPurchased(EntityPlayer par1, ItemStack par2) {
      GameHelper.setPlayerInventory(par1, this.getPlayerLoadout(par1.username).copy());
      if (this.getPlayerTeam(par1) != null && !par1.inventory.hasItem(par2.itemID)) {
         this.getPlayerLoadout(par1.username).addItemStack(par2);
         GameHelper.setPlayerInventory(par1, this.getPlayerLoadout(par1.username).copy());
         String sound = "countercraft:gunPulled";
         par1.worldObj.playSoundAtEntity(par1, sound, 0.8F, 1.0F);
      }

   }

   public void onGunFired(EntityPlayer par1, ItemStack par2) {
      if (GameHelper.getPlayerGameValue(this.getGame(), par1.username, "spawnPro") > 0) {
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "spawnPro", 0);
      }

   }

   public String onClientMessageReceived(String par1, String par2, String par3) {
      return par1;
   }

   public void resetAllBuyMenuTimer() {
      for(int i = 0; i < this.getPlayers().size(); ++i) {
         this.getGame().getPlayerGameData((String)this.getPlayers().get(i)).setInteger("buyTime", 400);
      }

   }

   public Loadout getPlayerLoadout(String par1) {
      if (!this.playerLoadouts.containsKey(par1)) {
         this.playerLoadouts.put(par1, new Loadout());
      }

      return (Loadout)this.playerLoadouts.get(par1);
   }

   public void clearPlayerLoadouts() {
      for(int i = 0; i < this.getPlayers().size(); ++i) {
         String username = (String)this.getPlayers().get(i);
         EntityPlayer player = GameHelper.getPlayerFromUsername(username);
         if (player != null) {
            player.inventory.clearInventory(-1, -1);
         }

         Team team = this.getPlayerTeam(username);
         if (this.playerLoadouts.containsKey(username)) {
            this.playerLoadouts.put(username, team.teamDefaultLoadout.copy());
         }

         GameHelper.setPlayerInventory(player, team.teamDefaultLoadout.copy());
      }

   }

   public BlockLocation getSafestRandomSpawn(World par1) {
      BlockLocation loc = null;
      int blockDistance = 50;
      ArrayList spawns = new ArrayList(this.randomSpawns);
      Collections.shuffle(spawns);

      while(loc == null) {
         for(int i = 0; i < spawns.size(); ++i) {
            BlockLocation temp = (BlockLocation)spawns.get(i);
            if (temp.getClosestPlayer(par1, blockDistance) == null) {
               loc = temp.copy();
            }
         }

         if (loc == null) {
            if (blockDistance > 0) {
               blockDistance -= 10;
            } else {
               Random rand = new Random();
               loc = (BlockLocation)spawns.get(rand.nextInt(spawns.size()));
            }
         }
      }

      return loc;
   }

   public boolean acceptsPlayersFromMM() {
      if (this.getGame().getStatus() == GameStatus.POSTGAME) {
         return false;
      } else if (((FreeForAll)this.getGame()).timerGame.getTimeRemainingSeconds() < 180) {
         return false;
      } else {
         Team players = this.getTeam("players");
         return players.getPlayers().size() <= this.getMaxPlayers();
      }
   }

   public BlockLocation getLobby() {
      return this.lobby;
   }
}
