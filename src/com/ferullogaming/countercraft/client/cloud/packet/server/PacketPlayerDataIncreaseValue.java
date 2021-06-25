package com.ferullogaming.countercraft.client.cloud.packet.server;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketPlayerDataIncreaseValue extends G_TcpPacketCustomPayload {
   private String username;
   private String value;
   private int amount;

   public PacketPlayerDataIncreaseValue(String par1, String par2, int par3) {
      this.username = par1;
      this.value = par2;
      this.amount = par3;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.username);
      writeString(par1, this.value);
      par1.writeInt(this.amount);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
