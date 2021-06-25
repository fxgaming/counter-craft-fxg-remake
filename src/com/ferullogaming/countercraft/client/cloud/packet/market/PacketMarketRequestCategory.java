package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMarketRequestCategory extends G_TcpPacketCustomPayload {
   private int cloudItemID;

   public PacketMarketRequestCategory(int par1) {
      this.cloudItemID = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      par1.writeInt(this.cloudItemID);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
