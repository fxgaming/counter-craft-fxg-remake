package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketBuyMenuItem extends CCPacket {
   public static Packet buildPacket(int par1) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketBuyMenuItem.class));
         data.writeInt(par1);
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
      try {
         int var1 = stream.readInt();
         IGame game = GameManager.instance().getPlayerGame(player);
         if (game != null && !player.worldObj.isRemote) {
            ItemStack itemstack = new ItemStack(var1, 1, 0);
            game.getPlayerEventHandler().onBuyMenuPurchased(player, itemstack);
         }
      } catch (IOException var8) {
         var8.printStackTrace();
      }

   }
}
