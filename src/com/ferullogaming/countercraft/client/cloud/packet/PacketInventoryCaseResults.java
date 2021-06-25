package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuOpenCase;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class PacketInventoryCaseResults extends G_TcpPacketCustomPayload {
   private ArrayList stacks = new ArrayList();

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      int var1 = par1.readInt();
      this.stacks.clear();

      for(int i = 0; i < var1; ++i) {
         int var2 = par1.readInt();
         CloudItemStack stack = new CloudItemStack("fake", var2);
         this.stacks.add(stack);
      }

   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      GuiScreen gui = Minecraft.getMinecraft().currentScreen;
      if (gui != null && gui instanceof GuiCCMenuOpenCase) {
         ((GuiCCMenuOpenCase)gui).onResultsIn(this.stacks);
      }

   }
}
