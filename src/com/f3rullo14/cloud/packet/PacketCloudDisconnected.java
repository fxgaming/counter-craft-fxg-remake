package com.f3rullo14.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketCloudDisconnected extends G_TcpPacketCustomPayload {
   private String disconnectReason = "Disconnected.";

   public PacketCloudDisconnected() {
   }

   public PacketCloudDisconnected(String par1) {
      this.disconnectReason = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      par1.writeUTF(this.disconnectReason);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.disconnectReason = par1.readUTF();
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }

   public String getReason() {
      return this.disconnectReason;
   }
}
