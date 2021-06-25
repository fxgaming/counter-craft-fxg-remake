package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.entity.EntityGrenadeFire;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.network.CCPacketCalloutSound;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenadeFire extends ItemGrenade {
   private boolean hasCalled = false;

   public ItemGrenadeFire(int par1) {
      super(par1);
   }

   public void onGrenadeThrown(ItemStack itemstack, World world, EntityPlayer player, double force) {
      double timeSeconds = 1.4D;
      world.spawnEntityInWorld(new EntityGrenadeFire(world, player, force, (int)(timeSeconds * 20.0D)));
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      world.playSoundAtEntity(player, super.soundThrow, 1.0F, 1.0F);
      if (!player.capabilities.isCreativeMode) {
         player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
      }

      if (GameManager.instance().isPlayerInGame(player)) {
         String sound = "firebomb";
         String team = GameManager.instance().getPlayerGame(player).getPlayerEventHandler().getPlayerTeam(player).teamName;
         PacketDispatcher.sendPacketToAllPlayers(CCPacketCalloutSound.buildPacket(team, sound, player.username));
         data.calloutTimer = 100;
      }

   }
}
