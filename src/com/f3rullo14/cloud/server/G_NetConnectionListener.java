package com.f3rullo14.cloud.server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class G_NetConnectionListener extends Thread {
   private ServerSocket listener = null;
   public G_CloudNetworkManager netManager;
   private final List pendingConnections = Collections.synchronizedList(new ArrayList());
   private int serverPort = 2100;
   private boolean serverRunning = true;

   public G_NetConnectionListener(int par1Port, G_CloudNetworkManager par2Manager) {
      //this.serverPort = par1Port;
      this.netManager = par2Manager;
   }

   public void processPendingConnections() {
      List list = this.pendingConnections;
      List var2 = this.pendingConnections;
      synchronized(this.pendingConnections) {
         for(int i = 0; i < this.pendingConnections.size(); ++i) {
            try {
               G_CloudTcpConnectionHandler clientConnectionHandler = (G_CloudTcpConnectionHandler)this.pendingConnections.get(i);
               if (clientConnectionHandler != null && clientConnectionHandler.tcpConnection != null) {
                  if (this.netManager.getParentManager().getParent().onConnectionAddedToThread(clientConnectionHandler)) {
                     if (G_CloudNetworkManager.LOG_LOGIN_PROCESS) {
                        System.out.println("[LOGIN] 3. Adding: " + clientConnectionHandler.toString2());
                     }

                     this.netManager.addTcpConnectionHandler(clientConnectionHandler.channel, clientConnectionHandler);
                     this.pendingConnections.remove(i);
                  } else {
                     ++clientConnectionHandler.pendingConnectionProcessing;
                     if (clientConnectionHandler.pendingConnectionProcessing > 200) {
                        System.out.println("Failed to Verify Connection: " + clientConnectionHandler.getConnectionUsername() + "");
                        clientConnectionHandler.kickClientConnection("Connection Closed");
                        this.pendingConnections.remove(i);
                     }
                  }
               } else {
                  System.out.println("[G_NetConnectionListener] Error! Invalid player connection hanlder trying to be initalized!");
               }
            } catch (Exception var6) {
               this.pendingConnections.remove(i);
               var6.printStackTrace();
            }
         }

      }
   }

   public void run() {
      System.out.println("[G_NetConnectionListener] Now Accepting Pending Connections...");

      while(this.serverRunning) {
         this.setSleep();
         this.setUpServerSocket();
         try {
            Socket socket = this.listener.accept();
            socket.setSoTimeout(3000);
            DataInputStream socketInputStream = new DataInputStream(socket.getInputStream());
            String channel = socketInputStream.readUTF();
            String name = socketInputStream.readUTF();
            String version = socketInputStream.readUTF();
            boolean close = false;
            if (channel != null && name != null && version != null && this.netManager.getParentManager().potentialChannels.contains(channel)) {
               G_CloudTcpConnectionHandler playerConnectionHandler = new G_CloudTcpConnectionHandler(channel, socket, name, version);
               if (this.netManager.getParentManager().getParent().onConnectionReached(playerConnectionHandler)) {
                  if (G_CloudNetworkManager.LOG_LOGIN_PROCESS) {
                     System.out.println("[LOGIN] 1. Reached: " + playerConnectionHandler.toString2());
                  }

                  this.addPendingConnection(playerConnectionHandler);
               }
            }
         } catch (SocketException var8) {
            ;
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      System.out.println("[WARNING] [G_NetConnectionListener] Closing Connection Listener Thread!");
   }

   public void addPendingConnection(G_CloudTcpConnectionHandler par1PlayerConnectionHandler) {
      if (par1PlayerConnectionHandler != null) {
         List list = this.pendingConnections;
         List var3 = this.pendingConnections;
         synchronized(this.pendingConnections) {
            if (G_CloudNetworkManager.LOG_LOGIN_PROCESS) {
               System.out.println("[LOGIN] 2. Syncing: " + par1PlayerConnectionHandler.toString2());
            }

            this.pendingConnections.add(par1PlayerConnectionHandler);
         }
      }

   }

   public void setUpServerSocket() {
      try {
         int port = this.serverPort;
         if (port < 18000 && port != 0 && this.listener == null) {
            this.listener = new ServerSocket(this.serverPort);
            this.listener.setPerformancePreferences(0, 2, 1);
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void setSleep() {
      try {
         sleep(2L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }
}
