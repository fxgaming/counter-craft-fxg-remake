package com.f3rullo14.cloud;

public abstract class G_ITcpConnectionHandler {
   public abstract boolean processesUnexpectedPacket(G_TcpPacket var1);

   public abstract boolean handleUnexpectedPacket(G_TcpPacket var1);

   public abstract String getConnectionUsername();

   public abstract String getConnectionVersion();

   public abstract void handleErrorMessage(String var1);

   public abstract void sendPacket(G_TcpPacket var1);
}
