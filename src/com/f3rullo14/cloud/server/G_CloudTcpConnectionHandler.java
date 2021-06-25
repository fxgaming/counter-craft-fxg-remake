package com.f3rullo14.cloud.server;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpConnection;
import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.packet.PacketClientHeartBeat;
import com.f3rullo14.cloud.packet.PacketClientLogin;
import com.f3rullo14.cloud.packet.PacketClientLogout;
import com.f3rullo14.cloud.packet.PacketCloudDisconnected;
import com.f3rullo14.cloud.packet.PacketCloudHeartBeat;
import com.f3rullo14.fds.NanoTimeHelper;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

public class G_CloudTcpConnectionHandler extends G_ITcpConnectionHandler {
   public G_TcpConnection tcpConnection;
   private String clientUsername = "[connecting]";
   private String clientVersion = "1.0.0";
   private int clientResponseTime = 0;
   public boolean isLoginSuccessful = false;
   public int loginTimer = 0;
   public boolean isConnectionClosed = false;
   public int clientPulseTick = 0;
   public int versionDelay = 0;
   public String channel;
   public G_CloudNetworkManager networkManager;
   public static int packetsProcessed = 0;
   public boolean allowPacketProcessing = true;
   public int pendingConnectionProcessing = 0;

   public G_CloudTcpConnectionHandler(String par1, Socket par2, String par3, String par4) throws IOException {
      this.channel = par1;
      this.clientUsername = par3;
      this.clientVersion = par4;
      this.tcpConnection = new G_TcpConnection(par2, "CloudClientConnection", this);
      this.loginTimer = 0;
   }

   public void tryLogin() {
      if (!this.isConnectionClosed) {
         this.onNetworkTick();
         NanoTimeHelper nano = NanoTimeHelper.createNanoTimer("Post Packet Process");
         if (this.isLoginSuccessful) {
            if (this.clientPulseTick++ >= 8000) {
               this.disconnectClient();
               this.clientPulseTick = 0;
            }
         } else if (this.loginTimer++ >= 8000) {
            this.disconnectClient();
         }

         if (!G_TcpConnection.isConnectionAlive(this.tcpConnection)) {
            this.disconnectClient();
         }

         if (nano.getElapsed() > 10.0D) {
            nano.updateAndPrintResult();
         }
      }

   }

   public void onNetworkTick() {
      try {
         if (this.tcpConnection != null && !this.isConnectionClosed) {
            G_TcpConnection var10000 = this.tcpConnection;
            if (G_TcpConnection.isConnectionAlive(this.tcpConnection)) {
               this.tcpConnection.processReadPackets();
               NanoTimeHelper nano = NanoTimeHelper.createNanoTimer("Waking Threads");
               this.tcpConnection.wakeThreads();
               if (nano.getElapsed() > 10.0D) {
                  nano.updateAndPrintResult();
               }
            }
         }
      } catch (Exception var2) {
         this.disconnectClient();
         var2.printStackTrace();
      }

   }

   public void kickClientConnection(String par1) {
      this.networkManager.getParentManager().log("[Connection Handler] Kicking " + (this.clientUsername != null ? this.clientUsername : "Unknown") + " " + par1);
      this.tcpConnection.addToSendQueue(new PacketCloudDisconnected(par1));
      this.tcpConnection.wakeThreads();
      this.disconnectClient();
   }

   private void disconnectClient() {
      try {
         if (this.tcpConnection != null) {
            this.tcpConnection.wakeThreads();
            this.tcpConnection.serverShutdown();
            this.tcpConnection.networkShutdown("Disconnecting Player");
         }

         this.isConnectionClosed = true;
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public boolean processesUnexpectedPacket(G_TcpPacket payload) {
      return !this.isLoginSuccessful ? payload instanceof PacketClientLogin : true;
   }

   public boolean handleUnexpectedPacket(G_TcpPacket packet) {
      this.clientPulseTick = 0;
      ++packetsProcessed;
      if (!this.isLoginSuccessful) {
         if (packet instanceof PacketClientLogin) {
            this.handlePacketLogin((PacketClientLogin)packet);
            return true;
         } else {
            return false;
         }
      } else if (!this.allowPacketProcessing) {
         return false;
      } else if (packet instanceof PacketClientHeartBeat) {
         this.handlePacketClientHeartBeat();
         return true;
      } else if (packet instanceof PacketClientLogout) {
         this.disconnectClient();
         return true;
      } else {
         if (this.networkManager.packetHandlers.size() > 0) {
            Iterator i$ = this.networkManager.packetHandlers.iterator();

            while(i$.hasNext()) {
               ICloudPacketHandler handler = (ICloudPacketHandler)i$.next();
               handler.handlePacket(this, packet);
            }
         }

         return true;
      }
   }

   public void handlePacketClientHeartBeat() {
      this.sendPacket(new PacketCloudHeartBeat());
   }

   public void handlePacketLogin(PacketClientLogin packet) {
      if (this.clientUsername.equals(packet.username) && this.clientUsername != null && this.clientUsername.length() > 0 && this.isClientUsernameClean(this.clientUsername)) {
         this.isLoginSuccessful = true;

         try {
            this.networkManager.onUserLoginSuccessful(this, packet);
         } catch (Exception var3) {
            System.out.println("[G_CloudTcpConnectionHandler] Failed to handle successful login.");
            var3.printStackTrace();
         }
      } else {
         this.disconnectClient();
      }

   }

   private boolean isClientUsernameClean(String par1) {
      if (par1.contains(" ")) {
         return false;
      } else {
         for(int i = 0; i < par1.length(); ++i) {
            if (!Character.isLetter(par1.charAt(i)) && !Character.isDigit(par1.charAt(i)) && !this.isValidCharacter(par1.charAt(i))) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isValidCharacter(char par1) {
      char[] var1 = new char[]{'_', '-'};

      for(int i = 0; i < var1.length; ++i) {
         if (par1 == var1[i]) {
            return true;
         }
      }

      return false;
   }

   public void handleErrorMessage(String par1) {
      if (!this.isConnectionClosed) {
         this.isConnectionClosed = true;
         if (par1.equals("disconnect.endOfStream")) {
            return;
         }

         System.out.println("[G_CloudTcpConnectionHandler] " + par1);
      }

   }

   public String getConnectionUsername() {
      return this.clientUsername;
   }

   public void sendPacket(G_TcpPacket par1) {
      this.tcpConnection.addToSendQueue(par1);
   }

   public String getConnectionVersion() {
      return this.clientVersion;
   }

   public String toString() {
      return "G_CloudTcpConnectionHandler [username=" + this.clientUsername + ",version=" + this.clientVersion + ",channel=" + this.channel + "]";
   }

   public String toString2() {
      return "[name=" + this.clientUsername + ",v=" + this.clientVersion + ",ch=" + this.channel + "]";
   }
}
