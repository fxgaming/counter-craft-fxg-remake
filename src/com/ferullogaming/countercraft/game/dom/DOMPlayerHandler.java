package com.ferullogaming.countercraft.game.dom;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.ChatSymbols;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
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
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;

public class DOMPlayerHandler extends IGamePlayerHandler {
   public static final int MAX_BUYTIME = 400;
   public static final int MAX_SPAWNPROTECTION = 150;
   public BlockLocation lobby;
   public HashMap playerLoadouts = new HashMap();

   public DOMPlayerHandler(IGame par1) {
      super(par1);
      this.addTeam(new Team("Red", 8, EnumChatFormatting.RED));
      this.addTeam(new Team("Blue", 8, EnumChatFormatting.BLUE));
   }

   public void onPlayerJoined(EntityPlayer par1, String par2Team) {
      Team team = this.getTeam(par2Team);
      par1.inventory.clearInventory(-1, -1);
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "CS");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + EnumChatFormatting.ITALIC + "Приветствуем " + par1.username + " в Захват точек.");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + team.teamColor + "" + EnumChatFormatting.BOLD + "Вы назначены в команду " + par2Team + "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(par1.username);
      if (!CounterCraft.instance.sponsor.containsKey(par1.username.toLowerCase())) {
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.YELLOW + "Купите и станьте §6премиум игроком§e,");
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.YELLOW + "получите все скины, наклейки, ножи которые видны всем!");
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.YELLOW + "Купить здесь: " + EnumChatFormatting.WHITE + References.SPONSOR_BUY);
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
       }
      GameHelper.teleportPlayer(par1, team.getRandomSpawn());
      GameHelper.refreshPlayerMCLevels(par1);
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

         Team team = this.getPlayerTeam(par1);
         if (team != null) {
            team.removePlayerFromTeam(par1.username);
         }

         this.getGame().clearPlayerData(par1.username);
         GameHelper.sendChatToAll(this.getGame(), "Игра", par1.username + " вышел");
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

               Domination dom = (Domination)this.getGame();
               if (dom.firstBlood == null || dom.firstBlood.length() <= 0) {
                  GameHelper.sendChatToAll(this.getGame(), "Игра", EnumChatFormatting.DARK_RED + playerSource.username + " сделал первое убийство!");
                  GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "score");
                  dom.firstBlood = playerSource.username;
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
      GameHelper.teleportPlayer(par1, team.getRandomSpawn());
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
         } else if (par1 != null && playerSource != null && this.getPlayerTeam(par1) != null && this.getPlayerTeam(playerSource) != null && this.getPlayerTeam(par1).teamName.equalsIgnoreCase(this.getPlayerTeam(playerSource).teamName)) {
            return true;
         } else {
            return GameHelper.getPlayerGameValue(this.getGame(), par1.username, "spawnPro") > 0;
         }
      } else {
         return false;
      }
   }

   public Loadout getPlayerLoadout(String par1) {
      if (!this.playerLoadouts.containsKey(par1)) {
         this.playerLoadouts.put(par1, new Loadout());
      }

      return (Loadout)this.playerLoadouts.get(par1);
   }

   public void onPlayerSwitchTeam(EntityPlayer par1) {
      Domination dom = (Domination)this.getGame();
      if (dom.getStatus() == GameStatus.POSTGAME) {
         GameHelper.sendChatToPlayer(dom, par1.username, "Игра", "Нельзя сменить команду сейчас");
      } else if (dom.timerGame.getTimeElapsedSeconds() > 240) {
         GameHelper.sendChatToPlayer(dom, par1.username, "Игра", "Нельзя сменить команду сейчас");
      } else if ((double)this.getTeam("red").getTeamObjectInteger("score").intValue() > (double)dom.maxScore * 0.75D) {
         GameHelper.sendChatToPlayer(dom, par1.username, "Игра", "Нельзя сменить команду сейчас");
      } else if ((double)this.getTeam("blue").getTeamObjectInteger("score").intValue() > (double)dom.maxScore * 0.75D) {
         GameHelper.sendChatToPlayer(dom, par1.username, "Игра", "Нельзя сменить команду сейчас");
      } else {
         Team team = this.getPlayerTeam(par1);
         Team newTeam = null;
         if (team != null) {
            if (team.teamName.equalsIgnoreCase("red")) {
               newTeam = this.getTeam("blue");
            } else {
               newTeam = this.getTeam("red");
            }
         }

         if (newTeam != null && newTeam.getPlayers().size() < 8) {
            team.removePlayerFromTeam(par1.username);
            this.getGame().clearPlayerData(par1.username);
            par1.inventory.clearInventory(-1, -1);
            this.playerLoadouts.remove(par1.username);
            this.addPlayer(par1, newTeam.teamName);
            GameHelper.sendChatToAll(this.getGame(), "Игра", par1.username + " сменил команду на " + newTeam.teamName.toUpperCase());
         }

      }
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
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(par2);
      this.getPlayerTeam(par2);
      if (par3.startsWith("[Team]")) {
         String username = Minecraft.getMinecraft().getSession().getUsername();
         par1 = "" + EnumChatFormatting.GRAY + "" + EnumChatFormatting.BOLD + "Команда " + EnumChatFormatting.RESET + par1.replace("[Team] ", "").trim();
         if (!this.getPlayerTeam(username).teamName.equals(this.getPlayerTeam(par2).teamName)) {
            return null;
         }
      }

      return par1;
   }

   public void resetAllBuyMenuTimer() {
      for(int i = 0; i < this.getPlayers().size(); ++i) {
         this.getGame().getPlayerGameData((String)this.getPlayers().get(i)).setInteger("buyTime", 400);
      }

   }

   public Team getWinningTeam() {
      int red = this.getTeam("Red").getTeamObjectInteger("score").intValue();
      int blue = this.getTeam("Blue").getTeamObjectInteger("score").intValue();
      if (red == blue) {
         return null;
      } else {
         return red > blue ? this.getTeam("Red") : this.getTeam("Blue");
      }
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

   public int getMaxPlayers() {
      return 16;
   }

   public boolean acceptsPlayersFromMM() {
      if (this.getGame().getStatus() == GameStatus.POSTGAME) {
         return false;
      } else if (((Domination)this.getGame()).timerGame.getTimeRemainingSeconds() < 180) {
         return false;
      } else {
         Team red = this.getTeam("red");
         int redscore = red.getTeamObjectInteger("score").intValue();
         Team blue = this.getTeam("blue");
         int bluescore = blue.getTeamObjectInteger("score").intValue();
         Domination dom = (Domination)this.getGame();
         return (double)redscore < (double)dom.maxScore * 0.85D && (double)bluescore < (double)dom.maxScore * 0.85D;
      }
   }

   public BlockLocation getLobby() {
      return this.lobby;
   }
}
