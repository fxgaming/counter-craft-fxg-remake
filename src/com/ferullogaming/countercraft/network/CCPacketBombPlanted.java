package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameComponentBomb;
import com.ferullogaming.countercraft.item.ItemBomb;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

public class CCPacketBombPlanted extends CCPacket {
   public static Packet buildPacket() {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketBombPlanted.class));
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      if (player instanceof EntityPlayerMP) {
         World world = player.worldObj;
         ItemStack itemstack = player.inventory.getCurrentItem();
         if (itemstack != null && itemstack.getItem() != null && itemstack.getItem() instanceof ItemBomb) {
            IGame game = GameManager.instance().getPlayerGame(player.username);
            if (game != null) {
               if (game instanceof IGameComponentBomb) {
                  IGameComponentBomb bombGame = (IGameComponentBomb)game;
                  if (bombGame.getBombPoint(player) != null && bombGame.canPlantBomb(world, player, itemstack)) {
                     ((ItemBomb)itemstack.getItem()).onBombPlanted(world, player, itemstack);
                  }
               }
            } else {
               ((ItemBomb)itemstack.getItem()).onBombPlanted(world, player, itemstack);
            }
         }
      }

   }
}
