package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.ItemDefault;
import com.ferullogaming.countercraft.client.cloud.ItemDefaultGun;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestedItemDefault extends G_TcpPacketCustomPayload {
   private String username;
   private String cat;
   private boolean var2;
   private FDSTagCompound tag = new FDSTagCompound("itemDefault");

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      this.username = readString(par1);
      this.var2 = par1.readBoolean();
      if (this.var2) {
         this.cat = readString(par1);
         this.tag.load(par1);
      }

   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      if (this.var2) {
         ItemDefault def = new ItemDefault(this.cat);
         if (this.cat.equals("gun")) {
            def = new ItemDefaultGun(this.cat);
         }

         ((ItemDefault)def).readFromFDSExtra(this.tag);
         if (def != null) {
            PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(this.username);
            data.itemDefaults.put(((ItemDefault)def).getItemCategory(), def);
         }
      }

   }
}
