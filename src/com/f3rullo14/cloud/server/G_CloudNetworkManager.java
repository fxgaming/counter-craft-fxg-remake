package com.f3rullo14.cloud.server;

import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.packet.PacketClientLogin;
import com.f3rullo14.fds.NanoTimeHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class G_CloudNetworkManager {
   public G_NetConnectionListener netListener;
   private volatile HashMap netChannels = new HashMap();
   public ArrayList packetHandlers = new ArrayList();
   private G_CloudManager manager;
   public static boolean LOG_PACKETS_PROCESSED = false;
   public static boolean LOG_LOGIN_PROCESS = false;

   public G_CloudNetworkManager(G_CloudManager par1, int port) {
      this.manager = par1;
      this.netChannels = new HashMap();
      this.netListener = new G_NetConnectionListener(port, this);
      this.netListener.start();
   }

   public void onNetworkTick() {
      this.netListener.processPendingConnections();
      G_CloudTcpConnectionHandler.packetsProcessed = 0;
      Iterator i$ = this.netChannels.keySet().iterator();
      while(i$.hasNext()) {
    	  System.out.println("[GC] has");
         String channelname = (String)i$.next();
         ArrayList list1 = (ArrayList)this.netChannels.get(channelname);

         for(int i = 0; i < list1.size(); ++i) {
            G_CloudTcpConnectionHandler connectionHandler = (G_CloudTcpConnectionHandler)list1.get(i);
            if (connectionHandler != null) {
               try {
                  NanoTimeHelper nano = NanoTimeHelper.createNanoTimer("Processing Packets: " + connectionHandler.getConnectionUsername() + "");
                  connectionHandler.tryLogin();
                  if (nano.getElapsed() > 5.0D) {
                     nano.updateAndPrintResult();
                  }
               } catch (Exception var8) {
                  connectionHandler.kickClientConnection("Failed to process a packet.");
                  var8.printStackTrace();
               }

               try {
                  if (connectionHandler != null && connectionHandler.isConnectionClosed) {
                     this.manager.getParent().onConnectionDisconnected(connectionHandler);
                     list1.remove(i);
                  }
               } catch (Exception var7) {
                  System.out.println("Failed to process disconnect: " + connectionHandler);
                  var7.printStackTrace();
                  list1.remove(i);
               }
            } else {
               list1.remove(i);
            }
         }
      }

      if (LOG_PACKETS_PROCESSED) {
         System.out.println("Packets Processed: " + G_CloudTcpConnectionHandler.packetsProcessed);
      }

   }

   public void onShutdown() {
      Iterator i$ = this.netChannels.keySet().iterator();

      while(i$.hasNext()) {
         String channelname = (String)i$.next();
         ArrayList list1 = (ArrayList)this.netChannels.get(channelname);

         for(int i = 0; i < list1.size(); ++i) {
            G_CloudTcpConnectionHandler connectionHandler = (G_CloudTcpConnectionHandler)list1.get(i);
            connectionHandler.kickClientConnection("Cloud Restarting");
            if (connectionHandler.isConnectionClosed) {
               this.manager.getParent().onConnectionDisconnected(connectionHandler);
               list1.remove(i);
            }

            connectionHandler.tcpConnection.wakeThreads();
         }
      }

   }

   public void onUserLoginSuccessful(G_CloudTcpConnectionHandler par1, PacketClientLogin par2) {
      this.manager.getParent().onConnectionEstablished(par1, par2.loginData);
   }

   public boolean isUserConnected(String par1, String par2) {
      return this.getClientConnectionHandler(par1, par2) != null;
   }

   public G_CloudTcpConnectionHandler getClientConnectionHandler(String par1, String par2) {
      ArrayList list = this.getNetChannel(par1);

      for(int i = 0; i < list.size(); ++i) {
         if (list.get(i) != null && ((G_CloudTcpConnectionHandler)list.get(i)).getConnectionUsername() != null && ((G_CloudTcpConnectionHandler)list.get(i)).getConnectionUsername().equals(par2)) {
            return (G_CloudTcpConnectionHandler)list.get(i);
         }
      }

      return null;
   }

   public G_CloudTcpConnectionHandler getClientConnectionHandlerIgnoreCase(String par1, String par2) {
      ArrayList list = this.getNetChannel(par1);

      for(int i = 0; i < list.size(); ++i) {
         if (list.get(i) != null && ((G_CloudTcpConnectionHandler)list.get(i)).getConnectionUsername() != null && ((G_CloudTcpConnectionHandler)list.get(i)).getConnectionUsername().equalsIgnoreCase(par2)) {
            return (G_CloudTcpConnectionHandler)list.get(i);
         }
      }

      return null;
   }

   public void addTcpConnectionHandler(String par1, G_CloudTcpConnectionHandler par2) {
      ArrayList list = this.getNetChannel(par1);
      if (!list.contains(par2)) {
         par2.networkManager = this;
         list.add(par2);
         if (LOG_LOGIN_PROCESS) {
            System.out.println("[LOGIN] 4. Added: " + par2.toString2());
         }
      }

   }

   public void sendPacketToClient(String par1Channel, String par2Name, G_TcpPacket packet) {
      G_CloudTcpConnectionHandler con = this.getClientConnectionHandler(par1Channel, par2Name);
      if (con != null && packet != null) {
         con.sendPacket(packet);
      }

   }

   public ArrayList getNetChannel(String par1) {
      if (!this.netChannels.containsKey(par1)) {
         this.netChannels.put(par1, new ArrayList());
      }

      return (ArrayList)this.netChannels.get(par1);
   }

   public ArrayList getChannels() {
      return new ArrayList(this.netChannels.keySet());
   }

   public void registerPacketHandler(ICloudPacketHandler par1) {
      this.packetHandlers.add(par1);
   }

   public G_CloudManager getParentManager() {
      return this.manager;
   }

   public int getTotalConnections() {
      int var1 = 0;

      String channelname;
      for(Iterator i$ = this.netChannels.keySet().iterator(); i$.hasNext(); var1 += ((ArrayList)this.netChannels.get(channelname)).size()) {
         channelname = (String)i$.next();
      }

      return var1;
   }

   public static void log(String par1) {
      System.out.println("[CNM] " + par1);
   }
}
