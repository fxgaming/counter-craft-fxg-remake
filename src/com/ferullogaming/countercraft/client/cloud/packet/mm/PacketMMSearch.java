package com.ferullogaming.countercraft.client.cloud.packet.mm;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketMMSearch extends G_TcpPacketCustomPayload {
   public String game;
   public String region;
   public ArrayList maps = new ArrayList();

   public PacketMMSearch(String par1, String par2, ArrayList par3) {
      this.game = par1;
      this.region = par2;
      this.maps = par3;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.game);
      writeString(par1, this.region);
      par1.writeInt(this.maps.size());

      for(int i = 0; i < this.maps.size(); ++i) {
         writeString(par1, (String)this.maps.get(i));
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
