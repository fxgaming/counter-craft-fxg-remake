package com.ferullogaming.countercraft.game.cbd;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.ServerCloudManager;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.ChatSymbols;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketCompetitiveLeave;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataIncreaseValue;
import com.ferullogaming.countercraft.damagesource.DamageSourceBomb;
import com.ferullogaming.countercraft.damagesource.DamageSourceBullet;
import com.ferullogaming.countercraft.damagesource.DamageSourceCC;
import com.ferullogaming.countercraft.damagesource.DamageSourceCCFire;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.BombKilledMessage;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameComponentEconomy;
import com.ferullogaming.countercraft.game.IGamePlayerHandler;
import com.ferullogaming.countercraft.game.KillFeedMessage;
import com.ferullogaming.countercraft.game.KilledMessage;
import com.ferullogaming.countercraft.game.Loadout;
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.item.ItemArmorCC;
import com.ferullogaming.countercraft.item.ItemBomb;
import com.ferullogaming.countercraft.item.ItemBombKit;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;

public class CBDPlayerHandler extends IGamePlayerHandler {
   public static final int MAX_BUYTIME = 400;
   public BlockLocation lobby;
   public Map temporaryData = new HashMap();
   public ArrayList temporarySpectators = new ArrayList();

   public CBDPlayerHandler(IGame par1) {
      super(par1);
      this.addTeam(new Team("Red", 5, EnumChatFormatting.RED));
      this.addTeam(new Team("Blue", 5, EnumChatFormatting.BLUE));
   }

   public void onPlayerJoined(EntityPlayer par1, String par2Team) {
      this.getGame().clearPlayerData(par1.username);
      PlayerData data = PlayerDataHandler.getPlayerData(par1);
      Team team = this.getTeam(par2Team);
      par1.inventory.clearInventory(-1, -1);
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "" + EnumChatFormatting.WHITE + ChatSymbols.SQUARE(30));
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + EnumChatFormatting.WHITE + EnumChatFormatting.BOLD + "CS");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + EnumChatFormatting.ITALIC + "Приветствуем " + par1.username + " в соревновательном режиме.");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c " + team.teamColor + "" + EnumChatFormatting.BOLD + "Назначен в команду " + par2Team + "");
      GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "/c Сервер доступен в регионе: " + ServerManager.instance().serverRegion);
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
      GameHelper.sendGameNotification(par1.username, team.teamColor + "" + team.teamName + " команда");
      if (this.getGame().getStatus() != GameStatus.GAME) {
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "buyTime", 400);
         ((IGameComponentEconomy)this.getGame()).addPlayerEconomy(par1.username, 10000);
      }

      if (this.temporaryData.containsKey(par1.username)) {
         List tempData = (List)this.temporaryData.get(par1.username);
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "kills", Integer.parseInt((String)tempData.get(0)));
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "deaths", Integer.parseInt((String)tempData.get(1)));
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "score", Integer.parseInt((String)tempData.get(2)));
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "economy", Integer.parseInt((String)tempData.get(3)));
         this.temporaryData.remove(par1.username);
      }

      GameHelper.setPlayerInventory(par1, team.teamDefaultLoadout.copy());
      CompetitiveBombDefusal comp = (CompetitiveBombDefusal)this.getGame();
      if (comp.getLeftList().contains(par1.username)) {
         if (comp.getStatus() == GameStatus.GAME && !comp.isRoundEnded) {
            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "isdead", 1);
            data.isGhost = true;
            data.ghostDelay = 72000;
            this.temporarySpectators.add(par1.username);
            System.out.println("Added a new spectator from relog! " + par1.username);
         }

         comp.getLeftList().remove(par1.username);
      }

   }

   public void onPlayerExit(EntityPlayer par1) {
      if (par1 != null) {
         if (this.getGame().getPlayerGameData(par1.username).getInteger("playTime") > 600 && FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted) {
            ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(par1.username, "games", 1));
         }

         CompetitiveBombDefusal comp = (CompetitiveBombDefusal)this.getGame();
         if (!comp.getLeftList().contains(par1.username)) {
            comp.getLeftList().add(par1.username);
         }

         this.temporarySpectators.remove(par1.username);
         GameHelper.sendChatToAll(this.getGame(), "Игра", par1.username + " вышел");
         GameHelper.teleportPlayer(par1, this.lobby);
         par1.inventory.clearInventory(-1, -1);
         PlayerData data = PlayerDataHandler.getPlayerData(par1);
         if (FMLCommonHandler.instance().getSide().isServer() && ServerManager.instance().isMatchMakingAccepted && this.getGame().getStatus() == GameStatus.GAME) {
            ServerCloudManager.sendPacket(new PacketCompetitiveLeave(par1.username));
            this.getGame().getPlayerGameData(par1.username).setInteger("compRejoin", 3600);
            GameHelper.sendChatToAll(this.getGame(), "Игра", EnumChatFormatting.RED + "Если " + par1.username + " не вернется в игру");
            GameHelper.sendChatToAll(this.getGame(), "Игра", EnumChatFormatting.RED + "до конца игры, он получит блокировку на 3 дня.");
            //BANSYSTEM
            //>> вышел из игры - занесен в hashmap(EntityPlayer),
            //HasmapFormat - <EntityPlayer, String[]{EntityPlayer, String(gamepersonalid, session/ не варик делать)}>
            //>> игра окончена - бан на 7д (занесен в лист банов)
            //>> nickname!dateOfUnban(берется по формату из старых модов)
         }

         this.temporaryData.put(par1.username, Arrays.asList(String.valueOf(comp.getPlayerGameData(par1.username).getInteger("kills")), String.valueOf(comp.getPlayerGameData(par1.username).getInteger("deaths")), String.valueOf(comp.getPlayerGameData(par1.username).getInteger("score")), String.valueOf(this.getGame().getPlayerGameData(par1.username).getInteger("economy"))));
      }

   }

   public void onRoundRefreshed(ArrayList par1) {
      Iterator i$ = par1.iterator();

      while(i$.hasNext()) {
         String username = (String)i$.next();
         PlayerData data = PlayerDataHandler.getPlayerData(username);
         GameHelper.getPlayerFromUsername(username).setHealth(20.0F);
         if (data != null && data.isSpectating() || data.isGhost) {
            GameHelper.clearSpecificPlayerInventory(username);
            data.ghostViewing = null;
            data.ghostDelay = 0;
            data.isGhost = false;
            GameHelper.setPlayerGameValue(this.getGame(), username, "isdead", 0);
            GameHelper.setPlayerInventory(GameHelper.getPlayerFromUsername(username), this.getPlayerTeam(username).teamDefaultLoadout.copy());
         }

         GameHelper.setPlayerGameValue(this.getGame(), username, "buyTime", 400);
         GameHelper.setPlayerGameValue(this.getGame(), username, "killsCL", 0);
         GameHelper.refreshPlayerGuns(username);
      }

   }

   public void onPlayerUpdate(EntityPlayer par1) {
      if (this.getGame().getStatus() == GameStatus.GAME) {
         GameHelper.increasePlayerGameValue(this.getGame(), par1.username, "playTime");
         if (GameHelper.getPlayerGameValue(this.getGame(), par1.username, "buyTime") > 0) {
            GameHelper.decreasePlayerGameValue(this.getGame(), par1.username, "buyTime");
         }

         CompetitiveBombDefusal comp = (CompetitiveBombDefusal)this.getGame();
         if (GameHelper.getPlayerGameValue(this.getGame(), par1.username, "isdead") == 1 && !par1.isDead) {
            if (comp.timerPreRound.getTimeRemaining() <= 0) {
               par1.attackEntityFrom(DamageSource.generic, 40.0F);
               GameHelper.setPlayerGameValue(this.getGame(), par1.username, "isdead", 0);
               return;
            }

            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "isdead", 0);
            PlayerData data = PlayerDataHandler.getPlayerData(par1);
            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "isdead", 1);
            data.ghostDelay = 20;
         }
      }

      int var1 = GameHelper.getPlayerGameValue(this.getGame(), par1.username, "itempickedup");
      if (var1 == 1) {
         GameHelper.setPlayerInventoryOrganized(par1);
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 0);
      }

   }

   public void onPlayerDeath(EntityPlayer par1, DamageSource par2) {
      if (!par1.worldObj.isRemote) {
         Entity entitySource = par2.getEntity();
         if (entitySource != null && entitySource instanceof EntityPlayer) {
            EntityPlayer playerSource = (EntityPlayer)entitySource;
            if (this.getGame().getStatus() == GameStatus.GAME && !playerSource.username.equalsIgnoreCase(par1.username)) {
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "kills");
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "killsCL");
               GameHelper.increasePlayerGameValue(this.getGame(), playerSource.username, "score", 2);
               if (this.getGame().isMMAccepted()) {
                  ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(playerSource.username, "kills", 1));
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

               ItemStack itemUsed = csource.getWeaponUsed();
               int reward = ((IGameComponentEconomy)this.getGame()).getItemReward(itemUsed, itemUsed.itemID);
               ((IGameComponentEconomy)this.getGame()).addPlayerEconomy(playerSource.username, reward);
               KillFeedMessage msg = new KillFeedMessage(this.getPlayerTeam(playerSource).teamColor + playerSource.username, this.getPlayerTeam(par1).teamColor + par1.username, csource.getWeaponUsed(), meta);
               GameHelper.sendKillFeedMessage(this.getGame(), msg);
               KilledMessage msg1 = new KilledMessage(playerSource.username, csource.getWeaponUsed(), (int)playerSource.getHealth());
               GameHelper.sendKilledMessage(par1.username, msg1, false);
            }
         } else if (par2 instanceof DamageSourceCC) {
            DamageSourceCC csource = (DamageSourceCC)par2;
            if (par2 instanceof DamageSourceBomb) {
               DamageSourceBomb gsource = (DamageSourceBomb)par2;
               BombKilledMessage msg1 = new BombKilledMessage("Бомба");
               GameHelper.sendKilledMessage(par1.username, msg1, true);
            }
         }

         if (this.getGame().getStatus() == GameStatus.GAME) {
            PlayerData data = PlayerDataHandler.getPlayerData(par1);
            GameHelper.increasePlayerGameValue(this.getGame(), par1.username, "deaths");
            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "isdead", 1);
            data.ghostDelay = 72000;
            if (this.getGame().isMMAccepted()) {
               ServerCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(par1.username, "deaths", 1));
            }
         }
      }

   }

   public void onPlayerRespawn(EntityPlayer par1) {
      Team team = this.getPlayerTeam(par1);
      par1.inventory.clearInventory(-1, -1);
      GameHelper.setPlayerInventory(par1, team.teamDefaultLoadout.copy());
      if (this.getGame().getStatus() != GameStatus.GAME) {
         GameHelper.setPlayerGameValue(this.getGame(), par1.username, "isdead", 0);
         GameHelper.teleportPlayer(par1, team.getRandomSpawn());
      }

   }

   public boolean onPlayerDamaged(EntityPlayer par1, DamageSource par2) {
      Entity entitySource = par2.getEntity();
      if (this.getGame().getStatus() == GameStatus.POSTGAME) {
         return true;
      } else {
         if (this.getGame() instanceof CompetitiveBombDefusal) {
            CompetitiveBombDefusal comp = (CompetitiveBombDefusal)this.getGame();
            if (comp.isRoundEnded) {
               return false;
            }
         }

         if (entitySource != null && entitySource instanceof EntityPlayer) {
            EntityPlayer playerSource = (EntityPlayer)entitySource;
            if (par1.username.equalsIgnoreCase(playerSource.username)) {
               return false;
            } else {
               return par1 != null && playerSource != null && this.getPlayerTeam(par1) != null && this.getPlayerTeam(playerSource) != null && this.getPlayerTeam(par1).teamName.equalsIgnoreCase(this.getPlayerTeam(playerSource).teamName);
            }
         } else {
            return false;
         }
      }
   }

   public String onClientMessageReceived(String par1, String par2, String par3) {
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(par2);
      this.getPlayerTeam(par2);
      if (par3.startsWith("[Team]")) {
         String username = Minecraft.getMinecraft().getSession().getUsername();
         (new StringBuilder()).append("").append(EnumChatFormatting.GRAY).append("").append(EnumChatFormatting.BOLD).append("Команда ").append(EnumChatFormatting.RESET).append(par1.replace("[Team] ", "").trim()).toString();
         if (!this.getPlayerTeam(username).teamName.equals(this.getPlayerTeam(par2).teamName)) {
            return null;
         }
      }

      return par1;
   }

   public ArrayList onPlayerDeathItemsDropped(EntityPlayer par1, ArrayList par2) {
      ArrayList items = new ArrayList();
      boolean flag = false;
      Iterator i$ = par2.iterator();

      EntityItem item;
      ItemGun gun;
      while(i$.hasNext()) {
         item = (EntityItem)i$.next();
         if (item.getEntityItem().getItem() instanceof ItemGun) {
            gun = (ItemGun)item.getEntityItem().getItem();
            if (gun.isPrimary) {
               items.add(item);
               flag = true;
            }
         }
      }

      i$ = par2.iterator();

      while(i$.hasNext()) {
         item = (EntityItem)i$.next();
         if (item.getEntityItem().getItem() instanceof ItemGun) {
            gun = (ItemGun)item.getEntityItem().getItem();
            if (!gun.isPrimary && !flag) {
               items.add(item);
               flag = true;
            }
         }

         if (item.getEntityItem().getItem() instanceof ItemBombKit) {
            items.add(item);
         }

         if (item.getEntityItem().getItem() instanceof ItemBomb) {
            GameHelper.sendChatToTeam(this.getGame(), this.getTeam("red"), "Игра", par1.username + " потерял бомбу!");
            items.add(item);
         }
      }

      return items;
   }

   public boolean allowItemTossed(EntityPlayer par1, ItemStack par2) {
      if (par2.getItem() instanceof ItemGun) {
         return true;
      } else if (par2.getItem() instanceof ItemBomb) {
         GameHelper.sendChatToTeam(this.getGame(), this.getTeam("red"), "Игра", par1.username + " уронил бомбу!");
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
         } else if (par2.getItem() instanceof ItemGrenade) {
            if (par1.inventory.mainInventory[3] == null || par1.inventory.mainInventory[4] == null || par1.inventory.mainInventory[5] == null) {
               GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 1);
               return true;
            }
         } else if (this.getPlayerTeam(par1).teamName.equalsIgnoreCase("blue") && par2.getItem() instanceof ItemBombKit) {
            if (par1.inventory.mainInventory[6] == null) {
               GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 1);
               return true;
            }
         } else if (this.getPlayerTeam(par1).teamName.equalsIgnoreCase("red") && par2.getItem() instanceof ItemBomb && par1.inventory.mainInventory[6] == null) {
            GameHelper.setPlayerGameValue(this.getGame(), par1.username, "itempickedup", 1);
            GameHelper.sendChatToTeam(this.getGame(), this.getTeam("red"), "Игра", par1.username + " поднял бомбу!");
            return true;
         }

         return false;
      }
   }

   public void onBuyMenuPurchased(EntityPlayer par1, ItemStack par2) {
      int var1 = ((IGameComponentEconomy)this.getGame()).getItemPrice(par2, par2.itemID);
      if (((IGameComponentEconomy)this.getGame()).hasPlayerEconomy(par1.username, var1)) {
         String sound;
         if (par2 != null && par2.getItem() != null && par2.getItem() instanceof ItemArmorCC) {
            if (!this.getGame().getStatus().equals(GameStatus.PREGAME)) {
               PlayerData playerData = PlayerDataHandler.getPlayerData(par1);
               if (playerData != null) {
                  if (par2.getItem() == ItemManager.armorHelmet) {
                     if (!playerData.isWearingHelmet() || playerData.helmetHealth < playerData.helmetMaxHealth) {
                        playerData.wearingHelmet = true;
                        playerData.setHelmetHealth(playerData.helmetMaxHealth);
                        sound = "countercraft:misc.helmet";
                        par1.worldObj.playSoundAtEntity(par1, sound, 0.8F, 1.0F);
                        ((IGameComponentEconomy)this.getGame()).removePlayerEconomy(par1.username, var1);
                     }
                  } else if (par2.getItem() == ItemManager.armorKevlar && (!playerData.isWearingKevlar() || playerData.kevlarHealth < playerData.kevlarMaxHealth)) {
                     playerData.wearingKevlar = true;
                     playerData.setKevlarHealth(playerData.kevlarMaxHealth);
                     sound = "countercraft:misc.kevlar";
                     par1.worldObj.playSoundAtEntity(par1, sound, 0.8F, 1.0F);
                     ((IGameComponentEconomy)this.getGame()).removePlayerEconomy(par1.username, var1);
                  }
               }
            } else {
               GameHelper.sendChatToPlayer(this.getGame(), par1.username, "Игра", "Вы не можете купить броню во время разминки!");
            }
         } else {
            Loadout loadout = Loadout.createLoadout(par1);
            GameHelper.setPlayerInventory(par1, loadout.copy());
            if (this.getPlayerTeam(par1) != null && !par1.inventory.hasItem(par2.itemID)) {
               loadout.addItemStack(par2);
               GameHelper.setPlayerInventory(par1, loadout.copy(), true);
               sound = "countercraft:gunPulled";
               par1.worldObj.playSoundAtEntity(par1, sound, 0.8F, 1.0F);
               ((IGameComponentEconomy)this.getGame()).removePlayerEconomy(par1.username, var1);
            }
         }
      }

   }

   public int getPlayersDead() {
      return this.getPlayerDeadOnTeam("red") + this.getPlayerDeadOnTeam("blue");
   }

   public int getPlayerDeadOnTeam(String par1) {
      int var1 = 0;
      Iterator i$ = this.getTeam(par1).getPlayers().iterator();

      while(i$.hasNext()) {
         String var2 = (String)i$.next();
         PlayerData data = PlayerDataHandler.getPlayerData(var2);
         if (data.isGhost) {
            ++var1;
         }
      }

      return var1;
   }

   public void assignBombToPlayers() {
      if (this.getPlayers().size() > 0) {
         Iterator i$ = this.getTeam("red").getPlayers().iterator();

         while(i$.hasNext()) {
            String var1 = (String)i$.next();
            EntityPlayer player = GameHelper.getPlayerFromUsername(var1);
            player.inventory.clearInventory(ItemManager.bomb.itemID, -1);
         }

         String name = this.getTeam("red").getRandomPlayer();
         EntityPlayer player = GameHelper.getPlayerFromUsername(name);
         player.inventory.setInventorySlotContents(6, new ItemStack(ItemManager.bomb));
      }

   }

   public void assignBombKitsToPlayers() {
      if (this.getPlayers().size() > 0) {
         ArrayList list = this.getTeam("blue").getPlayers();

         for(int i = 0; i < list.size(); ++i) {
            EntityPlayer player = GameHelper.getPlayerFromUsername((String)list.get(i));
            player.inventory.setInventorySlotContents(6, new ItemStack(ItemManager.bombKit));
         }
      }

   }

   public int getMaxPlayers() {
      return 10;
   }

   public boolean acceptsPlayersFromMM() {
      return this.getGame().getStatus() == GameStatus.IDLE || this.getGame().getStatus() == GameStatus.PREGAME;
   }

   public BlockLocation getLobby() {
      return this.lobby;
   }
}
