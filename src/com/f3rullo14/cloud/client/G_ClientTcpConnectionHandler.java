package com.f3rullo14.cloud.client;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpConnection;
import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.packet.PacketClientHeartBeat;
import com.f3rullo14.cloud.packet.PacketClientLogin;
import com.f3rullo14.cloud.packet.PacketClientLogout;
import com.f3rullo14.cloud.packet.PacketCloudDisconnected;
import java.io.DataOutputStream;
import java.net.Socket;

public class G_ClientTcpConnectionHandler extends G_ITcpConnectionHandler {
   private boolean disconnected;
   private String cloudIP = "192.168.100.14";
   private int cloudPORT = 2100;
   private G_TcpConnection tcpConnection;
   private volatile int cloudConnectionPulse = 600;
   private IClientCloudConnection connectionBase;
   private int connectionHeartBeat = 0;
   private boolean isLoggedIn = false;
   private int reconnectTick = 0;
   private static boolean attemptingConnection = false;

   public G_ClientTcpConnectionHandler(IClientCloudConnection par1) {
      this.connectionBase = par1;
      //his.cloudIP = par1.getCloudIPAddress();
      //this.cloudPORT = par1.getCloudPort();
      this.attemptCloudConnection();
   }

   public void attemptCloudConnectionThread() {
      ConnectThread thread = new ConnectThread(this);
      thread.start();
   }

   public void attemptCloudConnection() {
      if (!this.isConnected() && !attemptingConnection) {
         attemptingConnection = true;

         try {
            Socket socket = new Socket(this.cloudIP, this.cloudPORT);
            this.tcpConnection = new G_TcpConnection(socket, this.getConnectionUsername(), this);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(this.connectionBase.getConnectionChannel());
            out.writeUTF(this.connectionBase.getConnectionName());
            out.writeUTF(this.connectionBase.getConnectionVersion());
            System.out.println("[G_ClientTcpConnectionHandler] Connection Established to cloud!");
            this.isLoggedIn = false;
         } catch (Exception var3) {
         }
         attemptingConnection = false;
      }

   }

   public void onUpdate() {
      if (this.isConnected()) {
         if (!this.isLoggedIn) {
            System.out.println("[G_ClientTcpConnectionHandler] Sending Login Information");
            PacketClientLogin packet = new PacketClientLogin();
            packet.username = this.getConnectionUsername();
            this.connectionBase.sendLoginInformation(packet.loginData);
            this.sendPacket(packet);
            this.isLoggedIn = true;
         }

         if (this.connectionHeartBeat++ > 20) {
            this.sendPacket(new PacketClientHeartBeat());
            this.connectionHeartBeat = 0;
         }

         this.processReadPackets();
      } else {
         this.isLoggedIn = false;
         if (this.connectionBase.attemptReconnect() && this.reconnectTick++ > 600) {
            this.connectionBase.handleReconnect();
            this.reconnectTick = 0;
         }
      }

   }

   public void processReadPackets() {
      if (this.cloudConnectionPulse <= 0) {
         this.disconnect("No message from cloud.");
      }

      if (!this.disconnected && this.tcpConnection != null) {
         this.tcpConnection.processReadPackets();
      }

      if (this.tcpConnection != null) {
         this.tcpConnection.wakeThreads();
      }

   }

   public void sendPacket(G_TcpPacket par1Packet) {
      if (!this.disconnected && this.tcpConnection != null) {
         this.addToSendQueue(par1Packet);
      }

   }

   private void addToSendQueue(G_TcpPacket par1Packet) {
      this.tcpConnection.addToSendQueue(par1Packet);
   }

   public void disconnectClientFromCloud(String par1) {
      this.disconnected = true;
      if (this.tcpConnection != null) {
         this.tcpConnection.addToSendQueue(new PacketClientLogout());
         this.tcpConnection.wakeThreads();
         this.tcpConnection.networkShutdown("disconnect.closed");
         this.tcpConnection = null;
      }

   }

   private void disconnect(String par1) {
      this.disconnected = true;
      if (this.tcpConnection != null) {
         this.handleDisconnectedFromCloud(par1);
         this.tcpConnection.wakeThreads();
         this.tcpConnection.networkShutdown("disconnect.closed");
         this.tcpConnection = null;
      }

   }

   public boolean isConnected() {
      return G_TcpConnection.isConnectionAlive(this.tcpConnection);
   }

   public boolean processesUnexpectedPacket(G_TcpPacket payload) {
      return true;
   }

   public boolean handleUnexpectedPacket(G_TcpPacket packet) {
      if (packet instanceof PacketCloudDisconnected) {
         this.disconnect(((PacketCloudDisconnected)packet).getReason());
         return true;
      } else {
         this.connectionBase.handlePacket(packet);
         this.cloudConnectionPulse = 600;
         return true;
      }
   }

   private void handleDisconnectedFromCloud(String par1) {
      this.connectionBase.handleDisconnectFromCloud(par1);
   }

   public String getConnectionUsername() {
      return this.connectionBase.getConnectionName();
   }

   public void handleErrorMessage(String par1) {
      if (!this.disconnected) {
         this.disconnected = true;
         System.out.println("[G_ClientTcpConnectionHandler] Error Message");
         System.out.println("[G_ClientTcpConnectionHandler] " + par1);
      }

   }

   public String getConnectionVersion() {
      return this.connectionBase.getConnectionVersion();
   }
}
