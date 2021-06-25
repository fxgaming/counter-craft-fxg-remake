package com.f3rullo14.cloud.example;

import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.client.G_ClientTcpConnectionHandler;
import com.f3rullo14.cloud.client.IClientCloudConnection;
import com.f3rullo14.fds.tag.FDSTagCompound;

public class Client implements IClientCloudConnection {
   private boolean isRunning = true;
   private G_ClientTcpConnectionHandler connectionHandler = new G_ClientTcpConnectionHandler(this);

   public void onUpdate() {
      this.connectionHandler.onUpdate();
   }

   public void onShutdown() {
      this.connectionHandler.disconnectClientFromCloud("Logging Out");
   }

   public String getConnectionName() {
      return "LGC_CLIENT";
   }

   public String getCloudIPAddress() {
      return "149.202.89.26";
   }

   public int getCloudPort() {
      return 15000;
   }

   public void handleDisconnectFromCloud(String par1) {
      System.out.println(par1);
   }

   public boolean attemptReconnect() {
      return true;
   }

   public void handlePacket(G_TcpPacket par1) {
   }

   public static void main(String[] args) {
      System.out.println("[Client] Starting...");
      Client client = new Client();
      System.out.println("[Client] Running Updates to the Program...");

      while(client.isRunning) {
         client.onUpdate();

         try {
            Thread.sleep(50L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      }

      System.out.println("[Client] Shutting Down...");
      client.onShutdown();
      System.exit(0);
   }

   public void onConnectionStateChanged(boolean par1) {
   }

   public void handleReconnect() {
   }

   public String getConnectionChannel() {
      return "user";
   }

   public String getConnectionVersion() {
      return "1.0.0";
   }

   public boolean canConnect() {
      return false;
   }

   public void sendLoginInformation(FDSTagCompound par1) {
   }
}
