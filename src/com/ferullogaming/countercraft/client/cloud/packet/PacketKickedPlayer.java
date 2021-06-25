package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.ServerManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketKickedPlayer extends G_TcpPacketCustomPayload {
   private String givenUsername;
   private String cloudAuthCode;

   public PacketKickedPlayer(String par1) {
      this.givenUsername = par1;
      this.cloudAuthCode = ServerManager.instance().matchMakingPassword;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.givenUsername);
      writeString(par1, this.cloudAuthCode);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
