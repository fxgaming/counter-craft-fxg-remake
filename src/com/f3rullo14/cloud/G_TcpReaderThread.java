package com.f3rullo14.cloud;

class G_TcpReaderThread extends Thread {
   final G_TcpConnection theTcpConnection;

   G_TcpReaderThread(G_TcpConnection par1TcpConnection, String par2Str) {
      super(par2Str);
      this.theTcpConnection = par1TcpConnection;
   }

   public void run() {
      G_TcpConnection.threadReaderAI.getAndIncrement();

      try {
         while(G_TcpConnection.isRunning(this.theTcpConnection) && !G_TcpConnection.isServerTerminating(this.theTcpConnection)) {
            if (!G_TcpConnection.readNetworkPacket(this.theTcpConnection)) {
               try {
                  sleep(2L);
               } catch (InterruptedException var5) {
                  ;
               }
            }
         }
      } finally {
         G_TcpConnection.threadReaderAI.getAndDecrement();
      }

   }
}
