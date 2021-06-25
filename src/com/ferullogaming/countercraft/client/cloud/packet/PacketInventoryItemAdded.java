package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;

public class PacketInventoryItemAdded extends G_TcpPacketCustomPayload {
   FDSTagCompound tag = new FDSTagCompound("stack");

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.tag.load(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      CloudItemStack stack = CloudItemStack.readFromFDS(this.tag);
      PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername()).getInventory().addItemStackLocal(stack);
   }
}
