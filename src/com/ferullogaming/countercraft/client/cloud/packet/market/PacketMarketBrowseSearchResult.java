package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.MarketCategoryHandler;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketBrowse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketMarketBrowseSearchResult extends G_TcpPacketCustomPayload {
   private static ArrayList lastResults = new ArrayList();

   public static ArrayList getLastSearchResults() {
      return lastResults;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      int var1 = par1.readInt();
      lastResults.clear();

      for(int i = 0; i < var1; ++i) {
         FDSTagCompound tag = new FDSTagCompound("result" + i);
         tag.load(par1);
         MarketCategoryHandler cat = new MarketCategoryHandler(tag.getInteger("listed"));
         cat.readBasicsFromFDS(tag);
         lastResults.add(cat);
      }

   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      GuiCCMenuMarketBrowse.isSearching = false;
   }
}
