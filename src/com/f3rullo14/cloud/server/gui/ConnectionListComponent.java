package com.f3rullo14.cloud.server.gui;

import com.f3rullo14.cloud.server.G_CloudManager;
import com.f3rullo14.cloud.server.G_CloudTcpConnectionHandler;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JList;

public class ConnectionListComponent extends JList {
   private G_CloudManager dedicatedServer;
   private int field_120014_b;

   public ConnectionListComponent(G_CloudManager par1DedicatedServer) {
      this.dedicatedServer = par1DedicatedServer;
      par1DedicatedServer.setPlayerListComponent(this);
   }

   public void onUpdate() {
      try {
         if (this.field_120014_b++ > 5) {
            Vector vector = new Vector();
            if (this.dedicatedServer != null && this.dedicatedServer.getNetwork() != null) {
               ArrayList list = this.dedicatedServer.getNetwork().getNetChannel("player");

               for(int i = 0; i < list.size(); ++i) {
                  try {
                     vector.add(((G_CloudTcpConnectionHandler)list.get(i)).getConnectionUsername());
                  } catch (Exception var5) {
                     ;
                  }
               }
            }

            this.setListData(vector);
            this.field_120014_b = 0;
         }
      } catch (Exception var6) {
         ;
      }

   }
}
