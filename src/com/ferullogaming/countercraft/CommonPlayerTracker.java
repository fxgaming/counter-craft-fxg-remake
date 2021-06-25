package com.ferullogaming.countercraft;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketMMSearchHostCanceled;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyLeave;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonPlayerTracker implements IPlayerTracker {
   public void onPlayerLogin(EntityPlayer player) {
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      data.isGhost = false;
      data.ghostDelay = 0;
      player.setInvisible(false);
      if (CounterCraft.getServerManager() != null) {
         if (FMLCommonHandler.instance().getSide() == Side.SERVER && GameManager.instance().gamesHosted.size() > 0) {
            World world = player.worldObj;
            if (world.getSpawnPoint() != null) {
               player.setPosition((double)world.getSpawnPoint().posX, (double)world.getSpawnPoint().posY, (double)world.getSpawnPoint().posZ);
               player.setInvisible(false);
            }
         }

         CounterCraft.getServerManager().onPlayerLogin(player.username);
      }

   }

   public void onPlayerLogout(EntityPlayer player) {
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(player.username);
      if (GameManager.instance().getPlayerGame(player) != null) {
         IGame game = GameManager.instance().getPlayerGame(player);
         game.getPlayerEventHandler().removePlayer(player);
      }

      if (CounterCraft.getServerManager() != null) {
         CounterCraft.getServerManager().onPlayerLogout(player.username);
      }

      if (FMLCommonHandler.instance().getSide().isClient() && data != null) {
         if (data.getParty() != null && data.getParty().isHost(data.getUsername()) && data.getParty().isSearching) {
            ClientCloudManager.sendPacket(new PacketMMSearchHostCanceled());
         }

         ClientCloudManager.sendPacket(new PacketPartyLeave());
      }

   }

   public void onPlayerChangedDimension(EntityPlayer player) {
   }

   public void onPlayerRespawn(EntityPlayer player) {
      if (!player.worldObj.isRemote) {
         GameManager.instance().onPlayerRespawn(player);
      }

   }
}
