package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketInventoryContract extends G_TcpPacketCustomPayload {
   private int tier;
   private ArrayList uuids = new ArrayList();

   public PacketInventoryContract(int par1, ArrayList par2) {
      this.tier = par1;
      this.uuids = par2;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      par1.writeInt(this.tier);

      for(int i = 0; i < this.uuids.size(); ++i) {
         writeString(par1, (String)this.uuids.get(i));
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
