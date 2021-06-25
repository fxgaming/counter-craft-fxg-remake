package com.f3rullo14.cloud;

import java.io.IOException;

class G_TcpWriterThread extends Thread {
   final G_TcpConnection theTcpConnection;

   G_TcpWriterThread(G_TcpConnection par1TcpConnection, String par2Str) {
      super(par2Str);
      this.theTcpConnection = par1TcpConnection;
   }

   public void run() {
      G_TcpConnection.threadWriterAI.getAndIncrement();

      try {
         while(G_TcpConnection.isRunning(this.theTcpConnection)) {
            boolean flag;
            for(flag = false; G_TcpConnection.sendNetworkPacket(this.theTcpConnection); flag = true) {
               ;
            }

            try {
               if (flag && G_TcpConnection.getOutputStream(this.theTcpConnection) != null) {
                  G_TcpConnection.getOutputStream(this.theTcpConnection).flush();
               }
            } catch (IOException var8) {
               if (!G_TcpConnection.isTerminating(this.theTcpConnection)) {
                  G_TcpConnection.sendError(this.theTcpConnection, var8);
               }

               var8.printStackTrace();
            }

            try {
               sleep(2L);
            } catch (InterruptedException var7) {
               ;
            }
         }
      } finally {
         G_TcpConnection.threadWriterAI.getAndDecrement();
      }

   }
}
