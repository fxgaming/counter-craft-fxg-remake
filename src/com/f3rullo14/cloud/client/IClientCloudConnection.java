package com.f3rullo14.cloud.client;

import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.fds.tag.FDSTagCompound;

public interface IClientCloudConnection {
   String getConnectionName();

   String getConnectionVersion();

   String getConnectionChannel();

   String getCloudIPAddress();

   int getCloudPort();

   boolean canConnect();

   void sendLoginInformation(FDSTagCompound var1);

   void onConnectionStateChanged(boolean var1);

   void handleDisconnectFromCloud(String var1);

   void handlePacket(G_TcpPacket var1);

   boolean attemptReconnect();

   void handleReconnect();
}
