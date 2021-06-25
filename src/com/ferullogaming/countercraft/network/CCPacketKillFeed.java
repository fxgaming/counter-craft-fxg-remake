package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.game.KillFeedMessage;
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

public class CCPacketKillFeed extends CCPacket {
   public static Packet buildPacket(KillFeedMessage par1) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketKillFeed.class));
         data.writeUTF(par1.player);
         data.writeUTF(par1.playerDead);
         data.writeInt(par1.metaType);
         data.writeBoolean(par1.itemUsed != null);
         if (par1.itemUsed != null) {
            NBTTagCompound tag = new NBTTagCompound("used");
            par1.itemUsed.writeToNBT(tag);
            NBTTagCompound.writeNamedTag(tag, data);
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
         String player1 = stream.readUTF();
         String player1Dead = stream.readUTF();
         int meta = stream.readInt();
         boolean isStack = stream.readBoolean();
         ItemStack stack = null;
         if (isStack) {
            NBTTagCompound tag = (NBTTagCompound)NBTTagCompound.readNamedTag(stream);
            stack = ItemStack.loadItemStackFromNBT(tag);
         }

         KillFeedMessage msg = new KillFeedMessage(player1, player1Dead, stack, meta);
         ClientTickHandler.addKillFeedMessage(msg);
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }
}
