package com.ferullogaming.countercraft.network;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketGame extends CCPacket {
   public static Packet buildPacket(IGame par1) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketGame.class));
         data.writeBoolean(par1 != null);
         if (par1 != null) {
            data.writeUTF(ServerManager.instance() != null ? ServerManager.instance().serverUUID : "-");
            FDSTagCompound tag = new FDSTagCompound("toclient");
            data.writeUTF(par1.getGameName());
            data.writeUTF(par1.getGameType());
            par1.sendToClient(tag);
            tag.write(data);
         }

         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         boolean hasGame = stream.readBoolean();
         GameManager.instance().serverOnUUID = null;
         if (hasGame) {
            String serverUUID = stream.readUTF();
            String gameName = stream.readUTF();
            String gameType = stream.readUTF();
            if (!serverUUID.equals("-")) {
               GameManager.instance().serverOnUUID = serverUUID;
            }

            if (GameManager.instance().currentClientGame == null) {
               GameManager.instance().currentClientGame = GameManager.instance().getGameObject(gameType);
            }

            FDSTagCompound tag = new FDSTagCompound("toclient");
            tag.load(stream);
            GameManager.instance().currentClientGame.loadFromServer(tag);
         } else if (GameManager.instance().currentClientGame != null) {
            GameManager.instance().currentClientGame = null;
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }
}
