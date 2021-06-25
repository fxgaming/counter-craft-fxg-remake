package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestCandy extends G_TcpPacketCustomPayload {
   private String userToSendCandy;

   public PacketRequestCandy(String username) {
      this.userToSendCandy = username;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.userToSendCandy);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.userToSendCandy = readString(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      ClientCloudManager.sendPacket(new PacketRequestCandy(this.userToSendCandy));
   }
}
