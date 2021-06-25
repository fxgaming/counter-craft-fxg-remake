package com.ferullogaming.countercraft.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketSoundPlayer extends CCPacket {
   public static Packet buildPacket(String givenSoundName, double givenPosX, double givenPosY, double givenPosZ, float givenVolume, float givenPitch) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketSoundPlayer.class));
         data.writeUTF(givenSoundName);
         data.writeDouble(givenPosX);
         data.writeDouble(givenPosY);
         data.writeDouble(givenPosZ);
         data.writeFloat(givenVolume);
         data.writeFloat(givenPitch);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var13) {
         var13.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String soundName = stream.readUTF();
         double posX = stream.readDouble();
         double posY = stream.readDouble();
         double posZ = stream.readDouble();
         float volume = stream.readFloat();
         float pitch = stream.readFloat();
         Minecraft.getMinecraft().thePlayer.worldObj.playSound(posX, posY, posZ, soundName, volume, pitch, false);
      } catch (IOException var14) {
         var14.printStackTrace();
      }

   }
}
