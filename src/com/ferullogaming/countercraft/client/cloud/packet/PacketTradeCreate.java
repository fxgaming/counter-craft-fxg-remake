package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuTradeCreate;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketTradeCreate extends G_TcpPacketCustomPayload {
   private GuiCCMenuTradeCreate gui;

   public PacketTradeCreate(GuiCCMenuTradeCreate par1) {
      this.gui = par1;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, Minecraft.getMinecraft().getSession().getUsername());
      writeString(par1, this.gui.userTradingWith);
      par1.writeInt(this.gui.myItemsOffering.size());

      int i;
      GuiCCSlotInventory slot;
      for(i = 0; i < this.gui.myItemsOffering.size(); ++i) {
         slot = (GuiCCSlotInventory)this.gui.myItemsOffering.get(i);
         writeString(par1, slot.stack.getUUID());
      }

      par1.writeInt(this.gui.otherItemsReturn.size());

      for(i = 0; i < this.gui.otherItemsReturn.size(); ++i) {
         slot = (GuiCCSlotInventory)this.gui.otherItemsReturn.get(i);
         writeString(par1, slot.stack.getUUID());
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
