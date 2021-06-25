package com.f3rullo14.cloud.server;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacket;

public interface ICloudPacketHandler {
   void handlePacket(G_ITcpConnectionHandler var1, G_TcpPacket var2);
}
