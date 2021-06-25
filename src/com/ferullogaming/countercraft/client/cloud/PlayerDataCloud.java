package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.cloud.command.ICommandSender;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuConsole;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class PlayerDataCloud implements ICommandSender {
   public boolean isOnline = false;
   public int trophies = 0;
   public int exp = 0;
   public int emeralds = 0;
   public int totalKills = 0;
   public int totalDeaths = 0;
   public int totalGames = 0;
   public int statHeadshots = 0;
   public int statFirstBloods = 0;
   public int statFireKills = 0;
   public int maxInvSlots = 0;
   public int maxFriendSlots = 0;
   public int timePlayed = 0;
   public int halloweenCasesUnboxed = 0;
   public boolean isPartyCreated = false;
   public HashMap itemDefaults = new HashMap();
   public String mood = "Не думаю ниочем.";
   public String status = "Main Menu";
   public String clanTag = "NONE";
   public ArrayList itemsShowcasing = new ArrayList();
   public Group group = null;
   public EnumCompRank compRank = null;
   public int compRankXP = 0;
   public int compRankRequiredXP = 0;
   public String discordAuthCode = "";
   public String discordUserID = "";
   public String playerUUID = "";
   public boolean enableNotification_FriendRequest = true;
   public boolean enableNotification_FriendPoke = true;
   public boolean enableNotification_TradeRequest = true;
   public boolean enableProfileViewing = true;
   public boolean enableTradeRequests = true;
   public boolean enableFriendRequests = true;
   public String serverOn = null;
   public int coinDisplayed = -1;
   public ArrayList boostersActive = new ArrayList();
   public ArrayList playerPunishments = new ArrayList();
   public HashMap playerPunishmentCounts = new HashMap();
   public int requestDataDelay = 0;
   public int requestDataDelayInventory = 0;
   public boolean hasCompetitiveRejoinButton = false;
   public boolean isSupporter = false;;
   public boolean enableCape = false;
   public int prestigeLevel = 1;
   private String username;
   private CloudInventory inventory;
   private MatchParty party = null;

   public PlayerDataCloud(String givenUsername) {
      this.username = givenUsername;
      this.inventory = new CloudInventory();

   }

   public void readShowcaseFromFDS(FDSTagCompound par1) {
      int showcaseSize = par1.getInteger("showcaseSize");
      this.itemsShowcasing.clear();

      for(int i = 0; i < showcaseSize; ++i) {
         CloudItemStack stack = CloudItemStack.readFromFDS(par1.getTagCompound("showcase" + i));
         this.itemsShowcasing.add(stack);
      }

   }

   public boolean isShowcasingItem(CloudItemStack par1) {
      for(int i = 0; i < this.itemsShowcasing.size(); ++i) {
         if (((CloudItemStack)this.itemsShowcasing.get(i)).getUUID().equals(par1.getUUID())) {
            return true;
         }
      }

      return false;
   }

   public int getMinutesPlayed() {
      return this.timePlayed / 60;
   }

   public CloudInventory getInventory() {
      return this.inventory;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String par1) {
      this.username = par1;
   }

   public String getUsernameFormatted() {
      boolean isInTeam = false;
      String prefix = "";
      String username = this.getUsername();
      if (this.group != null) {
         prefix = this.group.getEnumColor() + "" + this.group.getName() + " ";
      }

      if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && GameManager.instance().currentClientGame != null) {
         IGame game = GameManager.instance().currentClientGame;
         if (game != null && game.getPlayerEventHandler().getPlayerTeam(username) != null && game.getPlayerEventHandler().getPlayerTeam(username).teamColor != null) {
            isInTeam = true;
            EnumChatFormatting teamColor = game.getPlayerEventHandler().getPlayerTeam(username).teamColor;
            username = teamColor + this.getUsername();
         }
      }

      if (!isInTeam) {
         username = EnumChatFormatting.GRAY + this.getUsername();
      }

      PlayerDataCloud playerDataCloud = PlayerDataHandler.getPlayerCloudData(this.getUsername());
      if (playerDataCloud != null && playerDataCloud.isSupporter) {
         username = EnumChatFormatting.YELLOW + Character.toString('★') + " " + username;
      }

      return prefix + username;
   }

   public MatchParty getParty() {
      return this.party;
   }

   public void setParty(MatchParty par1) {
      if (this.party == null && par1 != null) {
         this.isPartyCreated = true;
      }

      this.party = par1;
   }

   public boolean isDefaultItem(CloudItemStack par1) {
      CloudItemStack stack = this.getItemDefault(par1.getMCItemID());
      return stack != null ? stack.equals(par1) : false;
   }

   public CloudItemStack getItemDefault(String par1, int par2) {
      if (this.itemDefaults.containsKey(par1)) {
         String uuid = ((ItemDefault)this.itemDefaults.get(par1)).getCloudStackUUID(par2);
         return this.inventory.getStackFromUUID(uuid);
      } else {
         return null;
      }
   }

   public CloudItemStack getItemDefault(int par1) {
      CloudItem cloudItem = null;
      CloudItem[] arr$ = CloudItem.itemList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         CloudItem item = arr$[i$];
         if (item != null && item.getMCItemID() == par1) {
            cloudItem = item;
            break;
         }
      }

      if (cloudItem != null) {
         String type = cloudItem.getInventoryType();
         if (this.itemDefaults.containsKey(type)) {
            String uuid = ((ItemDefault)this.itemDefaults.get(type)).getCloudStackUUID(par1);
            return this.inventory.getStackFromUUID(uuid);
         }
      }

      return null;
   }

   public boolean hasPermission(String par1) {
      if (par1 != null && par1.length() > 0) {
         if (this.group == null) {
            return false;
         } else {
            return this.group.hasPermission(par1) || this.group.hasPermission("*");
         }
      } else {
         return true;
      }
   }

   public void addPunishment(Punishment par1) {
      for(int i = 0; i < this.playerPunishments.size(); ++i) {
         Punishment var1 = (Punishment)this.playerPunishments.get(i);
         if (var1.id.equals(par1.id)) {
            this.playerPunishments.remove(i);
            break;
         }
      }

      this.playerPunishments.add(par1);
   }

   public Punishment getFirstPunishmentType(PunishmentType par1) {
      Iterator i$ = this.playerPunishments.iterator();

      Punishment var1;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         var1 = (Punishment)i$.next();
      } while(var1.type != par1 || !var1.isActive());

      return var1;
   }

   public boolean hasPunishmentType(PunishmentType par1) {
      return this.getFirstPunishmentType(par1) != null;
   }

   public ArrayList getActivePunishments() {
      ArrayList var1 = new ArrayList();

      for(int i = 0; i < this.playerPunishments.size(); ++i) {
         if (((Punishment)this.playerPunishments.get(i)).isActive()) {
            var1.add(this.playerPunishments.get(i));
         }
      }

      return var1;
   }

   public EnumCompRank getCompRank() {
      return this.compRank;
   }

   public int getCompXP() {
      return this.compRankXP;
   }

   public int getCompRequiredXP() {
      return this.compRankRequiredXP;
   }

   private int getEnumCompRankRequiredXP(EnumCompRank currentRank) {
      int requiredXP = 0;
      switch(currentRank) {
      case IRON_1:
         requiredXP = 20;
         break;
      case IRON_2:
         requiredXP = 50;
         break;
      case IRON_3:
         requiredXP = 100;
         break;
      case IRON_MASTER:
         requiredXP = 200;
         break;
      case GOLD_1:
         requiredXP = 400;
         break;
      case GOLD_2:
         requiredXP = 600;
         break;
      case GOLD_3:
         requiredXP = 1000;
         break;
      case GOLD_MASTER:
         requiredXP = 1200;
         break;
      case DIAMOND_1:
         requiredXP = 1400;
         break;
      case DIAMOND_2:
         requiredXP = 1800;
         break;
      case DIAMOND_3:
         requiredXP = 2000;
         break;
      case DIAMOND_MASTER:
         requiredXP = 2400;
         break;
      case OBSIDIAN_1:
         requiredXP = 2800;
         break;
      case OBSIDIAN_2:
         requiredXP = 3000;
         break;
      case OBSIDIAN_3:
         requiredXP = 3500;
         break;
      case OBSIDIAN_MASTER:
         requiredXP = 4500;
         break;
      case ENDER_ELITE:
         requiredXP = 4500;
      }

      return requiredXP;
   }

   public String toString() {
      return "[Cloud Player Data] {username=" + this.username + ",invsize=" + this.getInventory().getListed().size() + ",emeralds(coins)=" + this.emeralds + "}";
   }

   public String getSender() {
      return this.username;
   }

   public void sendChat(String par1) {
      GuiCCMenuConsole.addText(EnumChatFormatting.RED + par1);
   }

   public String getDiscordAuthCode() {
      return this.discordAuthCode;
   }

   public String getDiscordUserID() {
      return this.discordUserID;
   }

   public String getStatus() {
      return this.isOnline ? this.status : "Offline";
   }
}
