package com.f3rullo14.cloud.client;

public class ConnectThread extends Thread {
   private G_ClientTcpConnectionHandler handler;

   public ConnectThread(G_ClientTcpConnectionHandler par1) {
      this.handler = par1;
   }

   public void run() {
      this.handler.attemptCloudConnection();
   }
}
