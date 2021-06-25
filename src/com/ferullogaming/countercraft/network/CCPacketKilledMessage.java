package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.game.BombKilledMessage;
import com.ferullogaming.countercraft.game.KilledMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketKilledMessage extends CCPacket {
   private static boolean bomb = false;

   public static Packet buildPacket(KilledMessage par1, boolean bomb) {
      bomb = bomb;
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketKilledMessage.class));
         data.writeUTF(par1.username);
         data.writeInt(par1.userHealth);
         NBTTagCompound tag = new NBTTagCompound("used");
         par1.itemUsed.writeToNBT(tag);
         NBTTagCompound.writeNamedTag(tag, data);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String player1 = stream.readUTF();
         int health = stream.readInt();
         NBTTagCompound tag = (NBTTagCompound)NBTTagCompound.readNamedTag(stream);
         ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
         if (!bomb) {
            ClientTickHandler.lastKilledMessage = new KilledMessage(player1, stack, health);
         } else {
            ClientTickHandler.lastKilledMessage = new BombKilledMessage(player1);
         }

         ClientTickHandler.lastKilledMessageTimer = 300;
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }
}
