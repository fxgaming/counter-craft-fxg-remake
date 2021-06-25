package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketInventoryItemRemoveSticker extends G_TcpPacketCustomPayload {
   private String stackRenaming;
   private String stickerPosition;

   public PacketInventoryItemRemoveSticker(String par1, String givenStickerPosition) {
      this.stackRenaming = par1;
      this.stickerPosition = givenStickerPosition;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.stackRenaming);
      writeString(par1, this.stickerPosition);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
