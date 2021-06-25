package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketMarketMyListingAction extends G_TcpPacketCustomPayload {
   private String listingUUID;
   private int action;

   public PacketMarketMyListingAction(String par1, int par2) {
      this.listingUUID = par1;
      this.action = par2;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, Minecraft.getMinecraft().getSession().getUsername());
      writeString(par1, this.listingUUID);
      par1.writeInt(this.action);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
