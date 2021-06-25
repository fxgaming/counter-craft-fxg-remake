package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuConsole;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.util.EnumChatFormatting;

public class PacketConsoleMessage extends G_TcpPacketCustomPayload {
   private String message;

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.message = readString(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      if (this.message != null && this.message.length() > 0) {
         GuiCCMenuConsole.addText(EnumChatFormatting.RED + this.message);
      }

   }
}
