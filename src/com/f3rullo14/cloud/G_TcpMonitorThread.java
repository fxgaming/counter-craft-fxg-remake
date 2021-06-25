package com.f3rullo14.cloud;

class G_TcpMonitorThread extends Thread {
   final G_TcpConnection theTcpConnection;

   G_TcpMonitorThread(G_TcpConnection par1TcpConnection) {
      this.theTcpConnection = par1TcpConnection;
   }

   public void run() {
      try {
         Thread.sleep(2000L);
         if (G_TcpConnection.isRunning(this.theTcpConnection)) {
            G_TcpConnection.getWriteThread(this.theTcpConnection).interrupt();
            this.theTcpConnection.networkShutdown("disconnect.closed");
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
}
