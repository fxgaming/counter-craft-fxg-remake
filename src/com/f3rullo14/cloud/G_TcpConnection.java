package com.f3rullo14.cloud;

import com.f3rullo14.fds.NanoTimeHelper;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class G_TcpConnection implements G_ITcpConnection {
   public static AtomicInteger threadReaderAI = new AtomicInteger();
   public static AtomicInteger threadWriterAI = new AtomicInteger();
   public final String connectionName;
   private final Object sendQueueLock;
   private Socket networkSocket;
   private final SocketAddress remoteSocketAddress;
   private volatile DataInputStream socketInputStream;
   private volatile DataOutputStream socketOutputStream;
   private volatile boolean isRunning;
   private volatile boolean isTerminating;
   private Queue readPackets;
   private List dataPackets;
   private G_ITcpConnectionHandler theConnectionHandler;
   private boolean isConnectionAlive = false;
   private boolean isServerTerminating;
   private Thread writeThread;
   private Thread readThread;
   private String terminationReason;
   private int timeoutTick;
   private int sendQueueByteLength;
   public static int[] readingPacketSizes = new int[256];
   public static int[] sendingPacketSizes = new int[256];
   public int sendingDelay;

   public G_TcpConnection(Socket par2Socket, String par3Str, G_ITcpConnectionHandler par4ConnectionHandler) throws IOException {
      this.connectionName = par3Str;
      this.sendQueueLock = new Object();
      this.isRunning = true;
      this.readPackets = new ConcurrentLinkedQueue();
      this.dataPackets = Collections.synchronizedList(new ArrayList());
      this.terminationReason = "";
      this.networkSocket = par2Socket;
      this.remoteSocketAddress = par2Socket.getRemoteSocketAddress();
      this.theConnectionHandler = par4ConnectionHandler;

      try {
         par2Socket.setSoTimeout(30000);
         par2Socket.setTrafficClass(25);
      } catch (SocketException var5) {
         System.err.println(var5.getMessage());
      }

      this.socketInputStream = new DataInputStream(par2Socket.getInputStream());
      this.socketOutputStream = new DataOutputStream(new BufferedOutputStream(par2Socket.getOutputStream(), 5120));
      this.readThread = new G_TcpReaderThread(this, par3Str + " read thread");
      this.writeThread = new G_TcpWriterThread(this, par3Str + " write thread");
      this.readThread.start();
      this.writeThread.start();
      this.isConnectionAlive = true;
   }

   public void setNetHandler(G_ITcpConnectionHandler par1Handler) {
      this.theConnectionHandler = par1Handler;
   }

   public void addToSendQueue(G_TcpPacket par1Packet) {
      if (!this.isServerTerminating) {
         Object object = this.sendQueueLock;
         Object var3 = this.sendQueueLock;
         synchronized(this.sendQueueLock) {
            this.sendQueueByteLength += par1Packet.getPacketSize() + 1;
            this.dataPackets.add(par1Packet);
         }
      }

   }

   private boolean sendPacket() {
      boolean flag = false;

      try {
         if (this.sendingDelay == 0) {
            G_TcpPacket packet = this.getPacketToSend();
            if (packet != null) {
               G_TcpPacket.writePacket(packet, this.socketOutputStream);
               int[] aint = sendingPacketSizes;
               int i = packet.getPacketId();
               aint[i] = packet.getPacketSize() + 1;
               flag = true;
            }
         }

         return flag;
      } catch (Exception var5) {
         if (!this.isTerminating) {
            this.onNetworkError(var5);
         }

         return false;
      }
   }

   private G_TcpPacket getPacketToSend() {
      G_TcpPacket packet = null;
      List list = this.dataPackets;
      Object object = this.sendQueueLock;
      Object var4 = this.sendQueueLock;
      synchronized(this.sendQueueLock) {
         while(!list.isEmpty() && packet == null) {
            packet = (G_TcpPacket)list.remove(0);
            this.sendQueueByteLength -= packet.getPacketSize() + 1;
         }

         return packet;
      }
   }

   public void wakeThreads() {
      if (this.readThread != null) {
         this.readThread.interrupt();
      }

      if (this.writeThread != null) {
         this.writeThread.interrupt();
      }

   }

   private boolean readPacket() {
      boolean flag = false;

      try {
         G_TcpPacket packet = G_TcpPacket.readPacket(this.socketInputStream, this.networkSocket);
         if (packet != null) {
            int[] aint = readingPacketSizes;
            int i = packet.getPacketId();
            aint[i] = packet.getPacketSize() + 1;
            if (!this.isServerTerminating) {
               this.readPackets.add(packet);
            }

            flag = true;
         } else {
            this.networkShutdown("disconnect.endOfStream");
         }

         return flag;
      } catch (Exception var5) {
         if (!this.isTerminating) {
            this.onNetworkError(var5);
         }

         return false;
      }
   }

   public void processReadPackets() {
      if (this.sendQueueByteLength > 2097152) {
         this.networkShutdown("disconnect.overflow");
      } else {
         if (this.readPackets.isEmpty()) {
            if (this.timeoutTick++ >= 1200) {
               this.networkShutdown("disconnect.timeout");
               return;
            }
         } else {
            this.timeoutTick = 0;
         }

         int i = 20;
         ArrayList packetIDs = new ArrayList();
         NanoTimeHelper nano = NanoTimeHelper.createNanoTimer("Process Packets Load");
         if (!this.readPackets.isEmpty()) {
            while(i-- >= 0) {
               G_TcpPacket packet = (G_TcpPacket)this.readPackets.poll();
               if (packet != null) {
                  if (this.theConnectionHandler.handleUnexpectedPacket(packet)) {
                     packet.processPacket(this.theConnectionHandler);
                     packetIDs.add(packet.getPacketId());
                  } else {
                     this.readPackets.add(packet);
                  }
               }
            }
         }

         if (nano.getElapsed() > 10.0D) {
            nano.updateAndPrintResult();
            System.out.println("Bad Packets Processed: " + packetIDs.toString());
         }

         this.wakeThreads();
         if (this.isTerminating && this.readPackets.isEmpty()) {
            this.theConnectionHandler.handleErrorMessage(this.terminationReason);
         }

      }
   }

   public SocketAddress getSocketAddress() {
      return this.remoteSocketAddress;
   }

   public void serverShutdown() {
      if (!this.isServerTerminating) {
         this.wakeThreads();
         this.isServerTerminating = true;
         this.readThread.interrupt();
         (new G_TcpMonitorThread(this)).start();
      }

   }

   public int packetSize() {
      return 0;
   }

   private void onNetworkError(Exception par1Exception) {
      par1Exception.printStackTrace();
      this.networkShutdown("disconnect.genericReason");
   }

   public void networkShutdown(String par1Str) {
      if (this.isRunning) {
         NanoTimeHelper nano = NanoTimeHelper.createNanoTimer("Connection Shutdown");
         this.isConnectionAlive = false;
         this.isTerminating = true;
         this.terminationReason = par1Str;
         this.isRunning = false;
         (new G_TcpMasterThread(this)).start();

         try {
            this.socketInputStream.close();
         } catch (Throwable var6) {
            ;
         }

         try {
            this.socketOutputStream.close();
         } catch (Throwable var5) {
            ;
         }

         try {
            this.networkSocket.close();
         } catch (Throwable var4) {
            ;
         }

         this.socketInputStream = null;
         this.socketOutputStream = null;
         this.networkSocket = null;
         nano.updateAndPrintResult();
      }

   }

   public void closeConnections() {
      this.wakeThreads();
      this.writeThread = null;
      this.readThread = null;
   }

   static boolean isRunning(G_TcpConnection par1) {
      return par1.isRunning;
   }

   static boolean isServerTerminating(G_TcpConnection par1) {
      return par1.isServerTerminating;
   }

   static boolean readNetworkPacket(G_TcpConnection par1) {
      return par1.readPacket();
   }

   static boolean sendNetworkPacket(G_TcpConnection par1) {
      return par1.sendPacket();
   }

   static DataOutputStream getOutputStream(G_TcpConnection par1) {
      return par1.socketOutputStream;
   }

   static boolean isTerminating(G_TcpConnection par1) {
      return par1.isTerminating;
   }

   static void sendError(G_TcpConnection par1, Exception par1Exception) {
      par1.onNetworkError(par1Exception);
   }

   static Thread getReadThread(G_TcpConnection par1) {
      return par1.readThread;
   }

   static Thread getWriteThread(G_TcpConnection par1) {
      return par1.writeThread;
   }

   public static boolean isConnectionAlive(G_TcpConnection par1) {
      return par1 == null ? false : par1.isConnectionAlive;
   }
}
