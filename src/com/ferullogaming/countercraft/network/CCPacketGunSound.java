package com.ferullogaming.countercraft.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketGunSound extends CCPacket {
   public static Packet buildPacket(String par1, int x, int y, int z) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketGunSound.class));
         data.writeUTF(par1);
         data.writeInt(x);
         data.writeInt(y);
         data.writeInt(z);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var8) {
         var8.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String sound = stream.readUTF();
         int x = stream.readInt();
         int y = stream.readInt();
         int z = stream.readInt();
         float distance = (float)player.getDistance((double)x, (double)y, (double)z);
         if (distance <= 30.0F) {
            player.getEntityWorld().playSound((double)x, (double)y, (double)z, sound, 1.5F, 1.0F, false);
         }

         if (distance >= 10.0F && !sound.endsWith("suppressed")) {
            player.getEntityWorld().playSound((double)x, (double)y, (double)z, sound + "distant", 6.0F, 1.0F, false);
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }
}
