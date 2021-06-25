package com.ferullogaming.countercraft.client.cloud.packet.friend;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketFriendRemove extends G_TcpPacketCustomPayload {
   private String user;

   public PacketFriendRemove(String par1) {
      this.user = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, Minecraft.getMinecraft().getSession().getUsername());
      writeString(par1, this.user);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
