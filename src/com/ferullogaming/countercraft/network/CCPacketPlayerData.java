package com.ferullogaming.countercraft.network;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketPlayerData extends CCPacket {
   public static Packet buildPacket(PlayerData par1) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketPlayerData.class));
         data.writeUTF(par1.username);
         FDSTagCompound tag = new FDSTagCompound("tag");
         par1.writeToFDS(tag);
         tag.write(data);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
         try {
            String username = stream.readUTF();
            FDSTagCompound tag = new FDSTagCompound("tag");
            PlayerData data = PlayerDataHandler.getPlayerData(username);
            tag.load(stream);
            data.readFromFDS(tag);
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

   }
}
