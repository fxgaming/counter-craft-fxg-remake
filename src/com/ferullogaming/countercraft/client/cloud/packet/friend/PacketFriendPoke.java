package com.ferullogaming.countercraft.client.cloud.packet.friend;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketFriendPoke extends G_TcpPacketCustomPayload {
   private String user;
   private String message;

   public PacketFriendPoke(String par1, String par2) {
      this.user = par1;
      this.message = par2;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.user);
      writeString(par1, this.message);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
