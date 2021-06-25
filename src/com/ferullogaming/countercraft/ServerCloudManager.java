package com.ferullogaming.countercraft;

import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.client.G_ClientTcpConnectionHandler;
import com.f3rullo14.cloud.client.IClientCloudConnection;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.packet.PacketRegistry;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketServerMatchMaking;
import net.minecraft.server.MinecraftServer;

public class ServerCloudManager implements IClientCloudConnection {
   private G_ClientTcpConnectionHandler connectionHandler;
   private ServerManager manager;
   private int port;
   private int delayMatchMaking = 2;

   public ServerCloudManager(ServerManager par1Manager) {
      this.manager = par1Manager;
      this.connectionHandler = new G_ClientTcpConnectionHandler(this);
      PacketRegistry.registerPackets();
      this.port = Integer.valueOf(References.PORT_CLOUD);
   }

   public static void sendPacket(G_TcpPacket par1) {
      CounterCraft.getServerManager().getCloudManager().connectionHandler.sendPacket(par1);
   }

   public void onUpdate() {
      this.connectionHandler.onUpdate();
      if (this.connectionHandler.isConnected() && this.delayMatchMaking-- <= 0) {
         sendPacket(new PacketServerMatchMaking(this.manager));
         this.delayMatchMaking = 600;
      }
   }

   public void onShutdown() {
      this.connectionHandler.disconnectClientFromCloud("Logging Out");
   }

   public String getConnectionName() {
      return this.manager.serverUUID;
   }

   public String getCloudIPAddress() {
      return this.manager.cloudIP;
   }

   public int getCloudPort() {
      return this.port;
   }

   public void sendLoginInformation(FDSTagCompound par1) {
      par1.setInteger("port", MinecraftServer.getServer().getServerPort());
   }

   public void onConnectionStateChanged(boolean b) {
   }

   public void handleDisconnectFromCloud(String par1) {
      System.out.println(par1);
   }

   public void handlePacket(G_TcpPacket par1) {
   }

   public boolean attemptReconnect() {
      return true;
   }

   public void handleReconnect() {
      this.connectionHandler.attemptCloudConnectionThread();
   }

   public String getConnectionChannel() {
      return "server";
   }

   public String getConnectionVersion() {
      return "1.2.5a";
   }

   public boolean canConnect() {
      return true;
   }
}
