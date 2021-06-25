package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.entity.EntityGrenadeDecoy;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.network.CCPacketCalloutSound;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenadeDecoy extends ItemGrenade {
   public ItemGrenadeDecoy(int par1) {
      super(par1);
   }

   public void onGrenadeThrown(ItemStack itemstack, World world, EntityPlayer player, double force) {
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      double timeSeconds = 2.0D;
      world.spawnEntityInWorld(new EntityGrenadeDecoy(world, player, force, (int)(timeSeconds * 20.0D)));
      world.playSoundAtEntity(player, super.soundThrow, 1.0F, 1.0F);
      if (!player.capabilities.isCreativeMode) {
         player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
      }

      if (GameManager.instance().isPlayerInGame(player)) {
         String sound = "decoy";
         String team = GameManager.instance().getPlayerGame(player).getPlayerEventHandler().getPlayerTeam(player).teamName;
         PacketDispatcher.sendPacketToAllPlayers(CCPacketCalloutSound.buildPacket(team, sound, player.username));
         data.calloutTimer = 100;
      }

   }
}
