package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.entity.EntityManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketParticles extends CCPacket {
   public static Packet buildPacket(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketParticles.class));
         data.writeUTF(par1Str);
         data.writeDouble(par2);
         data.writeDouble(par4);
         data.writeDouble(par6);
         data.writeDouble(par8);
         data.writeDouble(par10);
         data.writeDouble(par12);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var17) {
         var17.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String var1 = stream.readUTF();
         double var2 = stream.readDouble();
         double var3 = stream.readDouble();
         double var4 = stream.readDouble();
         double var5 = stream.readDouble();
         double var6 = stream.readDouble();
         double var7 = stream.readDouble();
         EntityManager.spawnParticle(var1, var2, var3, var4, var5, var6, var7);
      } catch (IOException var18) {
         var18.printStackTrace();
      }

   }
}
