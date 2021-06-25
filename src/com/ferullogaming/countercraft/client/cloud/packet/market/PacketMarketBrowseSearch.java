package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketMarketBrowseSearch extends G_TcpPacketCustomPayload {
   private String[] filters;

   public PacketMarketBrowseSearch(String... par1) {
      this.filters = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, Minecraft.getMinecraft().getSession().getUsername());
      par1.writeInt(this.filters.length);

      for(int i = 0; i < this.filters.length; ++i) {
         writeString(par1, this.filters[i]);
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
