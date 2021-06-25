package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.PlayerListing;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PacketRequestedMarketMyListings extends G_TcpPacketCustomPayload {
   private static ArrayList tempListings = new ArrayList();
   private FDSTagCompound tag = new FDSTagCompound("listings");

   public static ArrayList getMyMarketListings() {
      return tempListings;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.tag.load(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      int listingSize = this.tag.getInteger("size");
      tempListings.clear();

      for(int i = 0; i < listingSize; ++i) {
         FDSTagCompound tag1 = this.tag.getTagCompound("listing" + i);
         tempListings.add(PlayerListing.readFromFDS(tag1));
      }

   }
}
