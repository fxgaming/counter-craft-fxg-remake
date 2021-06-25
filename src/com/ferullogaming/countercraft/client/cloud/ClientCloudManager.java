package com.ferullogaming.countercraft.client.cloud;

import java.util.Random;

import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.client.G_ClientTcpConnectionHandler;
import com.f3rullo14.cloud.client.IClientCloudConnection;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.ClientSessionHandler;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.command.LocalCommandHandler;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientUpdateStatus;
import com.ferullogaming.countercraft.client.cloud.packet.PacketRegistry;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuFindMatch;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.network.CCPacketMMDataToServer;
import com.ferullogaming.countercraft.player.PlayerDataHandler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;

public class ClientCloudManager implements IClientCloudConnection {
   public String clientStatus = "Main Menu";
   public boolean joinedMMGame = false;
   public GuiScreen lastGuiScreen = null;
   private G_ClientTcpConnectionHandler connectionHandler;
   private String address;
   private int port;
   private int requestDelay = 0;
   private int requestDelayStats = 100000;
   private int requestDelayFriendData = 100000;
   private int requestDelayTrades = 100000;
   private int requestDelayPunishments = 100000;
   private ClientSessionHandler sessionHandler = new ClientSessionHandler();
   private boolean lastConnection = false;
   private String mmGame;
   private String mmMap;
   private String mmTeam;
   private StoreManager cloudStoreManager;
   private LocalCommandHandler localCommands;

   public ClientCloudManager(String par1) {
      this.connectionHandler = new G_ClientTcpConnectionHandler(this);
      PacketRegistry.registerPackets();
      this.cloudStoreManager = new StoreManager();
      this.localCommands = new LocalCommandHandler();
      this.address = References.IP_CLOUD;
      this.port = Integer.valueOf(References.PORT_CLOUD);
      System.out.println("Created new Cloud Connection instance to '" + this.address + "'...");
   }

   public static void sendPacket(G_TcpPacket par1) {
      if (CounterCraft.getClientManager().getCloudManager().getConnectionHandler().isConnected()) {
         CounterCraft.getClientManager().getCloudManager().getConnectionHandler().sendPacket(par1);
      }

   }

   public void onUpdate() {
      this.connectionHandler.onUpdate();
      if (this.requestDelay < 40) {
         ++this.requestDelay;
      } else {
         if (this.lastConnection != this.connectionHandler.isConnected()) {
            if (this.connectionHandler.isConnected()) {
               PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY, this.getConnectionName());
               PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, this.getConnectionName());
               PacketClientRequest.sendRequest(RequestType.STOREITEMS);
            }

            this.lastConnection = this.connectionHandler.isConnected();
         }

         if (this.connectionHandler.isConnected()) {
            if (this.requestDelayStats++ > 30) {
               PacketClientRequest.sendRequest(RequestType.PLAYER_DATA, this.getConnectionName());
               sendPacket(new PacketClientUpdateStatus());
               this.requestDelayStats = 0;
            }

            if (this.requestDelayFriendData++ > 100) {
               this.requestFriendsData();
               this.requestDelayFriendData = 0;
            }

            if (this.requestDelayTrades++ > 200) {
               PacketClientRequest.sendRequest(RequestType.PLAYER_TRADES, this.getConnectionName());
               this.requestDelayTrades = 0;
            }

            if (this.requestDelayPunishments++ > 200) {
               PacketClientRequest.sendRequest(RequestType.PLAYER_PUNISHMENTS, this.getConnectionName());
               this.requestDelayPunishments = 0;
            }
         }

         PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
         if (data != null) {
            boolean var1 = data.hasPunishmentType(PunishmentType.BAN_CLOUD);
            this.clientStatus = "Main Menu";
            if (data.getParty() != null) {
               this.clientStatus = "In-Party";
            }

            if (data.serverOn != null) {
               this.clientStatus = "In-Game";
            }
            if (var1) {
               this.clientStatus = EnumChatFormatting.RED + "Cloud Banned";
            }
         }

      }
   }

   public void onMMServerDataReceived(FDSTagCompound par1) {
      String address = par1.getString("address");
      this.mmGame = par1.getString("game");
      this.mmMap = par1.getString("map");
      this.mmTeam = par1.getString("team");
      Minecraft mc = Minecraft.getMinecraft();
      PlayerDataCloud playerData = PlayerDataHandler.getPlayerCloudData(mc.getSession().getUsername());
      if (this.mmGame.equals("cbd")) {
         PlayerRank rank = PlayerRank.getRankFromEXP(playerData.exp);
         if ((rank == null || rank.getID() < PlayerRank.SPIDER.getID()) && !playerData.getInventory().hasItem(CloudItem.COIN_PRESTIGE)) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
            ClientTickHandler.addClientNotification(new ClientNotification("You require Spider to join Competitive."));
            GuiCCMenuFindMatch.isSearching = false;
            return;
         }
      }

      if (mc.theWorld != null) {
         mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
         mc.theWorld.sendQuittingDisconnectingPacket();
         mc.loadWorld((WorldClient)null);
      }

      try {
         Random rand = new Random();
         Thread.sleep((long)(10 + rand.nextInt(1000)));
      } catch (Exception var6) {
         ;
      }

      if (GameManager.instance().currentClientGame == null) {
         this.lastGuiScreen = Minecraft.getMinecraft().currentScreen;
         this.joinedMMGame = true;
         Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(new GuiCCMenuHome(), Minecraft.getMinecraft(), new ServerData("mm-server", address)));
      }

      GuiCCMenuFindMatch.isSearching = false;
   }

   public void onMMServerJoined() {
      if (this.mmGame != null && this.mmGame.length() > 0 && this.mmMap != null && this.mmMap.length() > 0) {
         PacketDispatcher.sendPacketToServer(CCPacketMMDataToServer.buildPacket(this.mmGame, this.mmMap, this.mmTeam));
         this.mmGame = null;
         this.mmMap = null;
      }

   }

   public void requestFriendsData() {
      PlayerFriendsThread thread = new PlayerFriendsThread();
      thread.run();
   }

   public void onShutdown() {
      this.connectionHandler.disconnectClientFromCloud("Logging Out");
   }

   public String getConnectionName() {
      return Minecraft.getMinecraft().getSession().getUsername();
   }

   public String getCloudIPAddress() {
      return this.address;
   }

   public int getCloudPort() {
      return this.port;
   }

   public boolean canConnect() {
      try {
         return this.sessionHandler.handleClientSessionLogin();
      } catch (Exception var2) {
         return false;
      }
   }

   public void sendLoginInformation(FDSTagCompound par1) {
      this.sessionHandler.handleClientSessionLogin();
      par1.setBoolean("debug", false);
      par1.setString("key", this.sessionHandler.MOD_KEY);
   }

   public void onConnectionStateChanged(boolean par1) {
      String var1 = "Connected to the Cloud!";
      if (!par1) {
         var1 = "Connection to the Cloud Lost!";
      }
      ClientNotification.createNotification(var1);
   }

   public void handleDisconnectFromCloud(String par1) {
   }

   public void handlePacket(G_TcpPacket par1) {
   }

   public boolean attemptReconnect() {
      return true;
   }

   public void handleReconnect() {
      this.connectionHandler.attemptCloudConnectionThread();
   }

   public G_ClientTcpConnectionHandler getConnectionHandler() {
      return this.connectionHandler;
   }

   public String getConnectionChannel() {
      return "player";
   }

   public String getConnectionVersion() {
      return "1.2.5a";
   }

   public StoreManager getCloudStoreManager() {
      return this.cloudStoreManager;
   }

   public LocalCommandHandler getLocalCommandHandler() {
      return this.localCommands;
   }

   public boolean hasMods() {
      return Loader.instance().getActiveModList().size() > 4;
   }
}
