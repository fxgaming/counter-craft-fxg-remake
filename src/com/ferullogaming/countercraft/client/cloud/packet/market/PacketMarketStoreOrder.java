package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.gui.store.GuiCCMenuStore;
import com.ferullogaming.countercraft.client.gui.store.StoreOrder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMarketStoreOrder extends G_TcpPacketCustomPayload {
   public void writePacketToStream(DataOutputStream par1) throws IOException {
      FDSTagCompound tagOrder = new FDSTagCompound("order");

      for(int i = 0; i < GuiCCMenuStore.cart.size(); ++i) {
         StoreOrder order = (StoreOrder)GuiCCMenuStore.cart.get(i);
         FDSTagCompound tag = new FDSTagCompound("orderItem" + i);
         tag.setInteger("cat", order.catID);
         tag.setInteger("package", order.packageID);
         tag.setInteger("amount", order.amount);
         tagOrder.setTagCompound("orderItem" + i, tag);
      }

      tagOrder.write(par1);
      GuiCCMenuStore.cart.clear();
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
