package com.f3rullo14.cloud;

import java.net.SocketAddress;

public interface G_ITcpConnection {
   void setNetHandler(G_ITcpConnectionHandler var1);

   void addToSendQueue(G_TcpPacket var1);

   void wakeThreads();

   void processReadPackets();

   SocketAddress getSocketAddress();

   void serverShutdown();

   int packetSize();

   void networkShutdown(String var1);

   void closeConnections();
}
