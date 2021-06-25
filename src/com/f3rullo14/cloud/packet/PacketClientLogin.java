package com.f3rullo14.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketClientLogin extends G_TcpPacketCustomPayload {
   public String username;
   public FDSTagCompound loginData = new FDSTagCompound("login");

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      if (G_TcpPacket.isClient) {
         par1.writeUTF(this.username);
         if (this.loginData != null) {
            this.loginData.write(par1);
         }
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      if (!G_TcpPacket.isClient) {
         this.username = par1.readUTF();
         this.loginData.load(par1);
      }

   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
