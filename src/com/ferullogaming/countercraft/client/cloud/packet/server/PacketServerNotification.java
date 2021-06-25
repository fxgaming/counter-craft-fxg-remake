package com.ferullogaming.countercraft.client.cloud.packet.server;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketServerNotification extends G_TcpPacketCustomPayload {
   private String message;

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.message = readString(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      ServerManager.instance().log("Cloud: " + this.message);
      if (ServerManager.instance().isMatchMakingAccepted && GameManager.instance().matchMakingGame != null) {
         GameHelper.sendChatToAll(GameManager.instance().matchMakingGame, "Cloud", this.message);
      }

   }
}
