package com.f3rullo14.cloud.server;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.util.ArrayList;

public interface ICloud {
   String getCloudUsername();

   String getCloudVersion();

   int getCloudPort();

   void getDisplayInformation(ArrayList var1);

   boolean onConnectionReached(G_CloudTcpConnectionHandler var1);

   boolean onConnectionAddedToThread(G_CloudTcpConnectionHandler var1);

   void onConnectionEstablished(G_CloudTcpConnectionHandler var1, FDSTagCompound var2);

   void onConnectionDisconnected(G_CloudTcpConnectionHandler var1);

   void handleCommand(String var1);

   void getChannels(ArrayList var1);
}
