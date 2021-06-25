package com.ferullogaming.countercraft.game.inf;

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
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.ItemPowerup;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;

public class INFPlayerHandler extends IGamePlayerHandler {
   public static final String TEAM_DEAD = "Dead";
   public static final String TEAM_LIVING = "Living";
   public static final int MAX_SPAWNPROTECTION = 80;
   public String patientZero = null;
   public int minPlayersToStart = 6;
   private Infected theGame;

   public INFPlayerHandler(IGame par1) {
      super(par1);
      this.theGame = (Infected)par1;
      this.addTeam(new Team("Dead", 12, EnumChatFormatting.RED));
      this.addTeam(new Team("Living", 12, EnumChatFormatting.GREEN));
   }

   public void onPlayerJoined(EntityPlayer par1, String par2Team) {
      Team team = this.getTeam(par2Team);
      par1.inventory.clearInventory(-1, -1);
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "CS");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + EnumChatFormatting.ITALIC + "Приветствуем " + par1.username + " в Инфицирование.");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + team.teamColor + "" + EnumChatFormatting.BOLD + "Назначены в команду " + par2Team + "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      if (!CounterCraft.instance.sponsor.containsKey(par1.username.toLowerCase())) {
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.YELLOW + "Купите и станьте §6премиум игроком§e,");
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.YELLOW + "получите все скины, наклейки, ножи которые видны всем!");
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.YELLOW + "Купить здесь: " + EnumChatFormatting.WHITE + References.SPONSOR_BUY);
          GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
       }
      GameHelper.refreshPlayerMCLevels(par1);
      GameHelper.sendGameNotification(par1.username, team.teamColor + "присоеденился к " + team.teamName + ".");
      GameHelper.setPlayerInventory(par1, team.teamDefaultLoadout.copy());
      GameHelper.teleportPlayer(par1, team.getRandomSpawn());
      par1.removePotionEffect(Potion.moveSpeed.id);
      par1.removePotionEffect(Potion.invisibility.id);
      par1.removePotionEffect(Potion.damageBoost.id);
      par1.removePotionEffect(Potion.heal.id);
   }

   public void onPlayerExit(EntityPlayer par1) {
      if (par1 != null) {
         if (this.getGame().getPlayerGameData(par1.username).getInteger("playTime") > 1200 && FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted) {
            ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(par1.username, "games", 1));
         }

         this.getGame().clearPlayerData(par1.username);
         GameHelper.sendChatToAll(this.getGame(), "Игра", par1.username + " вышел");
         GameHelper.teleportPlayer(par1, this.theGame.lobby);
         par1.inventory.clearInventory(-1, -1);
         par1.removePotionEffect(Potion.moveSpeed.id);
         par1.removePotionEffect(Potion.invisibility.id);
         par1.removePotionEffect(Potion.damageBoost.id);
         par1.removePotionEffect(Potion.heal.id);
      }

   }

   public void onPlayerUpdate(EntityPlayer par1) {
      if (this.getGame().getStatus() == GameStatus.GAME) {
         GameHelper.increasePlayerGameValue(this.getGame(), par1.username, "playTime");
         if (GameHelper.getPlayerGameValue(this.getGame(), par1.username, "buyTime") > 0) {
            GameHelper.decreasePlayerGameValue(this.getGame(), par1.username, "buyTime");
         }
      }

      int var1 = GameHelper.getPlayerGameValue(this.getGame(), par1.username, "itempickedup");
      if (var1 == 1) {
         GameHelper.setPlayerInventoryOrganized(par1);
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 0);
      }

      int lastSpawnProtection = GameHelper.getPlayerGameValue(this.getGame(), par1.username, "spawnPro");
      if (lastSpawnProtection > 0) {
         GameHelper.decreasePlayerGameValue(this.getGame(), par1.username, "spawnPro");
      }

   }

   public void onPlayerDeath(EntityPlayer par1, DamageSource par2) {
      if (!par1.worldObj.isRemote) {
         Team killedTeam = this.getPlayerTeam(par1);
         Team killerTeam = null;
         Entity entitySource = par2.getEntity();
         if (entitySource != null && entitySource instanceof EntityPlayer) {
            EntityPlayer playerSource = (EntityPlayer)entitySource;
            killerTeam = this.getPlayerTeam(playerSource);
            if (this.getGame().getStatus() == GameStatus.GAME && !playerSource.username.equalsIgnoreCase(par1.username)) {
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "kills");
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "killsCL");
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "score");
               if (killerTeam.teamName.equals("Dead")) {
                  GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "score", 3);
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
            if (killedTeam.teamName.equals("Living")) {
               if (killedTeam.getPlayers().size() <= 3 && !this.theGame.lastAlive.contains(par1.username)) {
                  this.theGame.lastAlive.add(par1.username);
               }

               killedTeam.removePlayerFromTeam(par1.username);
               Team deadTeam = this.getTeam("Dead");
               deadTeam.addPlayerToTeam(par1.username);
               GameHelper.sendGameNotification(par1.username, EnumChatFormatting.RED + "Вы инфицированы!");
               GameHelper.sendGameNotification(par1.username, EnumChatFormatting.RED + "Бегите распространять инфекцию!");
            }
         }
      }

   }

   public void onPlayerRespawn(EntityPlayer par1) {
      Team team = this.getPlayerTeam(par1);
      GameHelper.teleportPlayer(par1, team.getRandomSpawn());
      GameHelper.setPlayerInventory(par1, team.teamDefaultLoadout);
      par1.removePotionEffect(Potion.moveSpeed.id);
      par1.removePotionEffect(Potion.invisibility.id);
      par1.removePotionEffect(Potion.damageBoost.id);
      par1.removePotionEffect(Potion.heal.id);
      GameHelper.setPlayerGameValue(this.getGame(), par1.username, "spawnPro", 80);
   }

   public boolean onPlayerDamaged(EntityPlayer par1, DamageSource par2) {
      Entity entitySource = par2.getEntity();
      if (this.theGame.getStatus() == GameStatus.GAME && this.theGame.timerGame.getTimeElapsedSeconds() < 10) {
         return true;
      } else if (entitySource != null && entitySource instanceof EntityPlayer) {
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

   public ArrayList onPlayerDeathItemsDropped(EntityPlayer par1, ArrayList par2) {
      return null;
   }

   public boolean allowItemTossed(EntityPlayer par1, ItemStack par2) {
      if (!(par2.getItem() instanceof ItemKnife) && !(par2.getItem() instanceof ItemGrenade)) {
         return true;
      } else {
         par1.inventory.addItemStackToInventory(par2);
         GameHelper.setPlayerInventoryOrganized(par1);
         return false;
      }
   }

   public boolean allowItemPickup(EntityPlayer par1, ItemStack par2, EntityItem par3) {
      PlayerData data = PlayerDataHandler.getPlayerData(par1);
      if (data.isGhost) {
         return false;
      } else {
         ItemSpawn itemSpawn = this.theGame.getClosestItremSpawn(par1, 3);
         Team team = this.getPlayerTeam(par1);
         if (team == null) {
            return false;
         } else {
            if (itemSpawn != null && !itemSpawn.respawning) {
               if (par2.getItem() instanceof ItemPowerup) {
                  int itemID = par2.itemID;
                  if (team.teamName.equalsIgnoreCase("Living")) {
                     if (itemID == ItemManager.gameItemInfectedLivingHealth.itemID && par1.getHealth() < 20.0F) {
                        GameHelper.playSoundFireworks(par1.worldObj, par1.posX, par1.posY, par1.posZ);
                        itemSpawn.notifyItemPickup();
                        par1.setHealth(20.0F);
                        if (par3 != null) {
                           par3.setDead();
                        }
                     }

                     return false;
                  }

                  if (team.teamName.equalsIgnoreCase("Dead")) {
                     if (itemID == ItemManager.gameItemInfectedDeadVanish.itemID) {
                        GameHelper.playSoundFireworks(par1.worldObj, par1.posX, par1.posY, par1.posZ);
                        itemSpawn.notifyItemPickup();
                        par1.addPotionEffect(new PotionEffect(Potion.invisibility.id, 600, 0, false));
                        if (par3 != null) {
                           par3.setDead();
                        }

                        GameHelper.sendChatToPlayer(this.theGame, par1.username, "Game", "");
                     }

                     if (itemID == ItemManager.gameItemInfectedDeadSpeed.itemID) {
                        GameHelper.playSoundFireworks(par1.worldObj, par1.posX, par1.posY, par1.posZ);
                        itemSpawn.notifyItemPickup();
                        par1.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1800, 2, false));
                        if (par3 != null) {
                           par3.setDead();
                        }
                     }

                     if (itemID == ItemManager.gameItemInfectedDeadHealth.itemID) {
                        GameHelper.playSoundFireworks(par1.worldObj, par1.posX, par1.posY, par1.posZ);
                        itemSpawn.notifyItemPickup();
                        par1.addPotionEffect(new PotionEffect(Potion.heal.id, 500, 1, false));
                        if (par3 != null) {
                           par3.setDead();
                        }
                     }

                     if (itemID == ItemManager.gameItemInfectedDeadDamage.itemID) {
                        GameHelper.playSoundFireworks(par1.worldObj, par1.posX, par1.posY, par1.posZ);
                        itemSpawn.notifyItemPickup();
                        par1.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 600, 1, false));
                        if (par3 != null) {
                           par3.setDead();
                        }
                     }

                     return false;
                  }
               }

               if (team.teamName.equalsIgnoreCase("Living")) {
                  itemSpawn.notifyItemPickup();
               }
            }

            if (team.teamName.equalsIgnoreCase("Dead")) {
               return false;
            } else {
               if (par2.getItem() instanceof ItemGun) {
                  ItemGun gun = (ItemGun)par2.getItem();
                  if (gun.isPrimary) {
                     if (par1.inventory.mainInventory[0] == null) {
                        GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 1);
                        return true;
                     }
                  } else if (par1.inventory.mainInventory[1] == null) {
                     GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 1);
                     return true;
                  }
               } else if (par2.getItem() instanceof ItemGrenade && (par1.inventory.mainInventory[3] == null || par1.inventory.mainInventory[4] == null || par1.inventory.mainInventory[5] == null)) {
                  GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 1);
                  return true;
               }

               return false;
            }
         }
      }
   }

   public String getPatientZero() {
      if (this.patientZero != null && !this.isPlayerPresent(this.patientZero)) {
         this.patientZero = null;
      }

      return this.patientZero;
   }

   public boolean setPatientZero() {
      Team living = this.getTeam("Living");
      String pzero = living.getRandomPlayer();
      if (pzero != null) {
         this.patientZero = pzero;
         living.removePlayerFromTeam(pzero);
         Team dead = this.getTeam("Dead");
         dead.addPlayerToTeam(pzero);
         this.getTeam("Dead").tpTeamRandomSpawns();
         GameHelper.getPlayerFromUsername(pzero).inventory.clearInventory(-1, -1);
         return true;
      } else {
         return false;
      }
   }

   public int getMaxPlayers() {
      return 12;
   }

   public boolean acceptsPlayersFromMM() {
      return this.getGame().getStatus() != GameStatus.GAME && this.getGame().getStatus() != GameStatus.POSTGAME;
   }

   public BlockLocation getLobby() {
      return this.theGame.lobby;
   }
}
