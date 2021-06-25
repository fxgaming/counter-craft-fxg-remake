package com.f3rullo14.cloud;

class G_TcpMasterThread extends Thread {
   final G_TcpConnection theTcpConnection;

   G_TcpMasterThread(G_TcpConnection par1TcpConnection) {
      this.theTcpConnection = par1TcpConnection;
   }

   public void run() {
      try {
         Thread.sleep(5000L);
         if (this.theTcpConnection != null) {
            if (G_TcpConnection.getReadThread(this.theTcpConnection) != null && G_TcpConnection.getReadThread(this.theTcpConnection).isAlive()) {
               try {
                  G_TcpConnection.getReadThread(this.theTcpConnection).stop();
               } catch (Throwable var3) {
                  ;
               }
            }

            if (G_TcpConnection.getWriteThread(this.theTcpConnection) != null && G_TcpConnection.getWriteThread(this.theTcpConnection).isAlive()) {
               try {
                  G_TcpConnection.getWriteThread(this.theTcpConnection).stop();
               } catch (Throwable var2) {
                  ;
               }
            }
         }
      } catch (InterruptedException var4) {
         var4.printStackTrace();
      } catch (Exception var5) {
         ;
      }

   }
}
