package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestedInventory extends G_TcpPacketCustomPayload {
   private String username;
   private int section;
   private boolean hasInv;
   private FDSTagCompound tagSplit = new FDSTagCompound("invSplit");

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.username = readString(par1);
      this.section = par1.readInt();
      this.hasInv = par1.readBoolean();
      if (this.hasInv) {
         this.tagSplit.load(par1);
      }

   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      if (this.hasInv) {
         PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(this.username);
         cloudData.getInventory().readSectionFromFDS(this.section, this.tagSplit);
      }

   }
}
