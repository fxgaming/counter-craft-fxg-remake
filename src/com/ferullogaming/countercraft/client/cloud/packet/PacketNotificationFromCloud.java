package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketNotificationFromCloud extends G_TcpPacketCustomPayload {
   private String var1;
   private String var2;
   private int var3;

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.var1 = readString(par1);
      this.var2 = readString(par1);
      this.var3 = Integer.parseInt(readString(par1));
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      ClientNotification not = (new ClientNotification(this.var2)).setSubMessage(this.var1).setType(this.var3);
      ClientTickHandler.addClientNotification(not);
   }
}
