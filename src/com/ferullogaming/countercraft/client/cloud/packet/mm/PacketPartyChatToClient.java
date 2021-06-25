package com.ferullogaming.countercraft.client.cloud.packet.mm;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.cloud.ConversationMessage;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCMenuCreateParty;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketPartyChatToClient extends G_TcpPacketCustomPayload {
   private String user;
   private String message;

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.user = readString(par1);
      this.message = readString(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      GuiCCMenuCreateParty.partyChat.add(new ConversationMessage(this.user, this.message));
   }
}
