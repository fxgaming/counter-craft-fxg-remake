package com.ferullogaming.countercraft.client.cloud.packet.mm;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.ClientManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketMMJoinServer extends G_TcpPacketCustomPayload {
   private FDSTagCompound tag = new FDSTagCompound("values");

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.tag.load(par1);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      ClientManager.instance().getCloudManager().onMMServerDataReceived(this.tag);
   }
}
