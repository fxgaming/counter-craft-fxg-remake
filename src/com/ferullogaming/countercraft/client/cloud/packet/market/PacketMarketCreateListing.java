package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketMarketCreateListing extends G_TcpPacketCustomPayload {
   private String stackUUID;
   private int price;

   public PacketMarketCreateListing(String par1, int par2) {
      this.stackUUID = par1;
      this.price = par2;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, Minecraft.getMinecraft().getSession().getUsername());
      writeString(par1, this.stackUUID);
      par1.writeInt(this.price);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
