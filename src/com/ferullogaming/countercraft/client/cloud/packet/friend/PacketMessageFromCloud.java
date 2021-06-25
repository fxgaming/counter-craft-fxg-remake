package com.ferullogaming.countercraft.client.cloud.packet.friend;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.Conversation;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCConversationMenu;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketMessageFromCloud extends G_TcpPacketCustomPayload {
   private String userFrom;
   private String message;

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.userFrom = readString(par1);
      this.message = readString(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      Conversation con = ClientManager.instance().getConversationHandler().getConversation(this.userFrom);
      con.addMessage(this.userFrom, this.message);
      if (!con.hasNotification) {
         if (Minecraft.getMinecraft().currentScreen instanceof GuiCCConversationMenu && this.userFrom.equals(GuiCCConversationMenu.playerViewing)) {
            return;
         }

         ClientNotification not = new ClientNotification("New Message from " + this.userFrom + "!");
         ClientTickHandler.addClientNotification(not);
         con.hasNotification = true;
      }

   }
}
