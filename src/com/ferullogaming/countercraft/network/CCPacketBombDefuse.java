package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.entity.EntityBomb;
import com.ferullogaming.countercraft.item.ItemBombKit;
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

public class CCPacketBombDefuse extends CCPacket {
   public static Packet buildPacket(EntityBomb par1, boolean par2) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketBombDefuse.class));
         data.writeInt(par1 == null ? -1 : par1.entityId);
         data.writeBoolean(par2);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return packet;
   }

   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      if (player instanceof EntityPlayerMP) {
         World world = player.worldObj;
         ItemStack itemstack = player.inventory.getCurrentItem();

         try {
            int var1 = stream.readInt();
            boolean var2 = stream.readBoolean();
            EntityBomb entity = var1 == -1 ? null : (EntityBomb)world.getEntityByID(var1);
            if (!player.worldObj.isRemote && entity != null && itemstack != null && itemstack.getItem() instanceof ItemBombKit) {
               ItemBombKit kit = (ItemBombKit)itemstack.getItem();
               kit.onBombClicked(world, player, itemstack, entity, var2);
            }
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }

   }
}
