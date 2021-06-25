package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketInventoryOpenStickerCapsule extends G_TcpPacketCustomPayload {
   private String uuid;

   public PacketInventoryOpenStickerCapsule(String par1) {
      this.uuid = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.uuid);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
