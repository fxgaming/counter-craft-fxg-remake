package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.Punishment;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestedPunishments extends G_TcpPacketCustomPayload {
   private String username;
   private FDSTagCompound tag = new FDSTagCompound("playerdata");

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.username = readString(par1);
      this.tag.load(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(this.username);
      cloudData.setUsername(this.username);
      if (this.tag.hasTag("punishSize")) {
         int var1 = this.tag.getInteger("punishSize");

         for(int i = 0; i < var1; ++i) {
            if (this.tag.hasTag("pun" + i)) {
               FDSTagCompound tag1 = this.tag.getTagCompound("pun" + i);
               Punishment punish = Punishment.readFromFDS(tag1);
               cloudData.addPunishment(punish);
            }
         }
      }

   }
}
