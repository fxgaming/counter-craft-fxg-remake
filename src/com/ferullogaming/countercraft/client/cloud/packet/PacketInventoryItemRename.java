package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketInventoryItemRename extends G_TcpPacketCustomPayload {
   private String stackTag;
   private String stackRenaming;
   private String newName;

   public PacketInventoryItemRename(String par1, String par2, String par3) {
      this.stackTag = par1;
      this.stackRenaming = par2;
      this.newName = par3;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.stackTag);
      writeString(par1, this.stackRenaming);
      writeString(par1, this.newName);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
