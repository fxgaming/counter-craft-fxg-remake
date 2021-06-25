package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMarketListingAction extends G_TcpPacketCustomPayload {
   private int itemID;
   private String listingUUID;
   private int action = 0;

   public PacketMarketListingAction(int par1, String par2, int par3) {
      this.itemID = par1;
      this.listingUUID = par2;
      this.action = par3;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      par1.writeInt(this.itemID);
      writeString(par1, this.listingUUID);
      par1.writeInt(this.action);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
