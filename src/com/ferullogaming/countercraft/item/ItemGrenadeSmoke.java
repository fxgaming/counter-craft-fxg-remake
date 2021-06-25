package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.entity.EntityGrenadeSmoke;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.network.CCPacketCalloutSound;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenadeSmoke extends ItemGrenade {
   public ItemGrenadeSmoke(int par1) {
      super(par1);
   }

   public void onGrenadeThrown(ItemStack itemstack, World world, EntityPlayer player, double force) {
      double timeSeconds = 2.0D;
      world.spawnEntityInWorld(new EntityGrenadeSmoke(world, player, force, (int)(timeSeconds * 20.0D)));
      world.playSoundAtEntity(player, super.soundThrow, 1.0F, 1.0F);
      if (!player.capabilities.isCreativeMode) {
         player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
      }

      if (GameManager.instance().isPlayerInGame(player)) {
         String sound = "smoke";
         String team = GameManager.instance().getPlayerGame(player).getPlayerEventHandler().getPlayerTeam(player).teamName;
         PacketDispatcher.sendPacketToAllPlayers(CCPacketCalloutSound.buildPacket(team, sound, player.username));
      }

   }
}
