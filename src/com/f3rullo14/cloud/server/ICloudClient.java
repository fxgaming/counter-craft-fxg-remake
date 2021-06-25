package com.f3rullo14.cloud.server;

import java.util.ArrayList;

public interface ICloudClient {
   String getCloudUsername();

   String getCloudVersion();

   int getCloudPort();

   void getDisplayInformation(ArrayList var1);

   boolean onConnectionReached(G_CloudTcpConnectionHandler var1);

   void onConnectionEstablished(G_CloudTcpConnectionHandler var1);

   void onConnectionDisconnected(G_CloudTcpConnectionHandler var1);

   void handleCommand(String var1);
}
