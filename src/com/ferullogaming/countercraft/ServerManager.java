package com.ferullogaming.countercraft;

import com.f3rullo14.fds.FCFile;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;

public class ServerManager {
   public String serverUUID = "";
   public String cloudIP;
   public boolean isMatchMaking;
   public boolean isMatchMakingAccepted;
   public String matchMakingPassword;
   public boolean isMatchRestartedShutdown;
   public String serverRegion;
   public boolean isLobby;
   private FCFile configFile;
   private ServerCloudManager cloudManager;

   public ServerManager() {
      this.cloudIP = References.IP_CLOUD;
      this.isMatchMaking = false;
      this.isMatchMakingAccepted = false;
      this.matchMakingPassword = "password";
      this.isMatchRestartedShutdown = false;
      this.serverRegion = "ALL";
      this.isLobby = false;
      this.loadConfig();
      //this.cloudManager = new ServerCloudManager(this);
      //this.setupStatus();
   }

   public static ServerManager instance() {
      return CounterCraft.getServerManager();
   }

   public void onUpdate() {
      this.cloudManager.onUpdate();
   }

   public void loadConfig() {
   }

   public void setupStatus() {
      if (this.isMatchMaking) {
         File f1 = new File(CounterCraft.instance.folderLocation + "status.txt");
         if (f1.exists()) {
            f1.delete();
         }

         FCFile config = new FCFile("status", CounterCraft.instance.folderLocation + "status.txt");
         config.getString("uuid", this.cloudManager.getConnectionName());
      }

   }

   public void shutdownServer(String par1) {
      this.log("*** Shutting Down by Counter Craft ***");
      this.log(par1);
      MinecraftServer.getServer().initiateShutdown();
   }

   public void onPlayerLogin(String par1) {
      PacketClientRequest.sendRequestServer(RequestType.PLAYER_INVENTORY, par1);
      PacketClientRequest.sendRequestServer(RequestType.PLAYER_INVENTORY_DEFAULTS, par1);
   }

   public void onPlayerLogout(String par1) {
      PlayerDataHandler.removePlayerData(par1);
      PlayerDataHandler.playerCloudDataMapping.remove(par1);
   }

   public void onServerStopped() {
      this.cloudManager.onShutdown();
   }

   public void log(String par1) {
      if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
         MinecraftServer.getServer().logInfo(par1);
      }

   }

   public ServerCloudManager getCloudManager() {
      return this.cloudManager;
   }
}
