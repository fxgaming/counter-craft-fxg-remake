package com.ferullogaming.countercraft.client.cloud.packet.server;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.ServerManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketServerMatchMaking extends G_TcpPacketCustomPayload {
   private ServerManager serverManager;

   public PacketServerMatchMaking(ServerManager par1) {
      this.serverManager = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      par1.writeBoolean(this.serverManager.isMatchMaking);
      writeString(par1, this.serverManager.matchMakingPassword);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
