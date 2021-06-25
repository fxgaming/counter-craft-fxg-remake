package com.f3rullo14.cloud.server.gui;

import com.f3rullo14.cloud.server.G_CloudManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

final class ServerGuiINNER1 extends WindowAdapter {
   final G_CloudManager field_120023_a;

   ServerGuiINNER1(G_CloudManager par1DedicatedServer) {
      this.field_120023_a = par1DedicatedServer;
   }

   public void windowClosing(WindowEvent par1WindowEvent) {
      this.field_120023_a.initiateShutdown();

      while(!this.field_120023_a.isCloudStopped) {
         try {
            Thread.sleep(100L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      }

      System.exit(0);
   }
}
