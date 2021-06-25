package com.ferullogaming.countercraft;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PunishmentType;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.entity.EntityManager;
import com.ferullogaming.countercraft.entity.EntityPlayerHead;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.item.ItemBomb;
import com.ferullogaming.countercraft.network.CCPacketFrozen;
import com.ferullogaming.countercraft.network.CCPacketGame;
import com.ferullogaming.countercraft.network.CCPacketPlayerData;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

public class CommonTickHandler implements ITickHandler {
   public ArrayList playerHeads = new ArrayList();

   public CommonTickHandler(FMLInitializationEvent event) {
      TickRegistry.registerTickHandler(this, Side.SERVER);
   }

   public void tickStart(EnumSet type, Object... tickData) {
   }

   public void tickEnd(EnumSet type, Object... tickData) {
      if (type.equals(EnumSet.of(TickType.PLAYER))) {
         this.onPlayerTick((EntityPlayer)tickData[0]);
      } else if (type.equals(EnumSet.of(TickType.SERVER))) {
         this.onServerTick(MinecraftServer.getServer());
      }

   }

   private void onServerTick(MinecraftServer server) {
      if (CounterCraft.getGameManager() != null) {
         GameManager.instance().onServerUpdate(server);
      }

      if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
         ServerManager.instance().onUpdate();
      }

   }

   private void onPlayerTick(EntityPlayer player) {
      PlayerData playerData = PlayerDataHandler.getPlayerData(player);
      if (player instanceof EntityPlayerMP) {
         EntityPlayerMP playermp = (EntityPlayerMP)player;
         playerData.isInvisible = playermp.isPotionActive(Potion.invisibility);
         PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(playerData.username);
         if (cloudData != null && cloudData.hasPunishmentType(PunishmentType.BAN_CLOUD) || cloudData.hasPunishmentType(PunishmentType.BAN_MM)) {
            if (GameManager.instance().getPlayerGame(playermp.username) != null) {
               GameHelper.sendChatToAll(GameManager.instance().getPlayerGame(playermp.username), "Cloud", EnumChatFormatting.RED + "Kicking " + playermp.username + ". You have been banned by CounterCraft AC.");
            }

            playermp.playerNetServerHandler.kickPlayerFromServer("Active AC Ban");
         }

         if (playerData.getHelmetHealth() <= 0 && playerData.wearingHelmet) {
            playerData.wearingHelmet = false;
         }

         if (playerData.getKevlarHealth() <= 0 && playerData.wearingKevlar) {
            playerData.wearingKevlar = false;
         }

         if (playerData.muzzleTimer > 0) {
            --playerData.muzzleTimer;
         }

         if (playerData.seenTimer > 0) {
            --playerData.seenTimer;
         }

         if (playerData.calloutTimer > 0) {
            --playerData.calloutTimer;
         }

         if (playerData.isGhost) {
            player.setSprinting(false);
         }

         IGame game;
         if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            playerData.hatWearing = -1;
            playerData.featuredCoin = -1;
            playerData.hasBomb = playermp.inventory.getStackInSlot(6) != null && playermp.inventory.getStackInSlot(6).getItem() instanceof ItemBomb;
            if (cloudData.requestDataDelay++ > 60) {
               PacketClientRequest.sendRequestServer(RequestType.PLAYER_DATA, playerData.username);
               cloudData.requestDataDelay = 0;
            }

            if (cloudData.requestDataDelayInventory++ > 180) {
               PacketClientRequest.sendRequestServer(RequestType.PLAYER_INVENTORY, playerData.username);
               PacketClientRequest.sendRequestServer(RequestType.PLAYER_INVENTORY_DEFAULTS, playerData.username);
               cloudData.requestDataDelayInventory = 0;
            }

            if (cloudData.getItemDefault("hat", -1) != null) {
               playerData.hatWearing = cloudData.getItemDefault("hat", -1).getMCItemID();
            }

            if (cloudData.getItemDefault("coin", -1) != null) {
               playerData.featuredCoin = cloudData.getItemDefault("coin", -1).getMCItemID();
            }

            game = GameManager.instance().getPlayerGame((EntityPlayer)playermp);
            if (game != null) {
               if (playerData.isGhost) {
                  playermp.setInvisible(true);
                  EntityPlayer playerViewing;
                  if ((game.getGameType().equals("cbd") || game.getGameType().equals("cas")) && playerData.ghostViewing == null) {
                     playerData.ghostViewing = game.getClientSide().getNextSpectatingUsername(0, playerData.username);
                     playerViewing = player.worldObj.getPlayerEntityByName(playerData.ghostViewing);
                     if (playerViewing != null) {
                        player.setPositionAndUpdate(playerViewing.posX, playerViewing.posY, playerViewing.posZ);
                     }
                  }

                  if (playerData.ghostViewing != null) {
                     playerViewing = playermp.worldObj.getPlayerEntityByName(playerData.ghostViewing);
                     if (playerViewing != null) {
                        PlayerData spectatingPlayerData = PlayerDataHandler.getPlayerData(playerViewing);
                        if (spectatingPlayerData != null) {
                           playermp.swingProgress = playerViewing.swingProgress;
                           playermp.setPositionAndUpdate(playerViewing.posX, playerViewing.posY + 4.0D, playerViewing.posZ);
                           playerData.flashTime = spectatingPlayerData.flashTime;
                           playerData.muzzleTimer = spectatingPlayerData.muzzleTimer;
                        }
                     }
                  }

                  if (playerData.ghostDelay > 0) {
                     --playerData.ghostDelay;
                  } else {
                     playermp.setInvisible(false);
                     playermp.setHealth(20.0F);
                     playermp.getFoodStats().addStats(20, 20.0F);
                     playerData.isGhost = false;
                     GameManager.instance().onPlayerRespawn(playermp);
                     Random rand = new Random();

                     for(int i = 0; i < 20; ++i) {
                        double d0 = rand.nextGaussian() * 0.02D;
                        double d1 = rand.nextGaussian() * 0.02D;
                        double d2 = rand.nextGaussian() * 0.02D;
                        EntityManager.spawnParticle("explode", playermp.posX + (double)(rand.nextFloat() * playermp.width * 2.0F) - (double)playermp.width, playermp.posY + (double)(rand.nextFloat() * playermp.height), playermp.posZ + (double)(rand.nextFloat() * playermp.width * 2.0F) - (double)playermp.width, d0, d1, d2);
                     }
                  }
               } else {
                  playermp.setInvisible(false);
               }
            }

            PacketDispatcher.sendPacketToAllPlayers(CCPacketPlayerData.buildPacket(playerData));
            if (ServerManager.instance().isMatchMakingAccepted) {
               if (game != null) {
                  if (cloudData.hasPunishmentType(PunishmentType.BAN_MM) || cloudData.hasPunishmentType(PunishmentType.BAN_CLOUD)) {
                     GameHelper.sendChatToAll(game, "Cloud", EnumChatFormatting.RED + "Kicking " + playermp.username + ". You have been banned by CounterCraft AC.");
                     playermp.playerNetServerHandler.kickPlayerFromServer("Counter Craft AC Ban Active.");
                  }

                  if (game.getGameType().equals("cbd") && cloudData.hasPunishmentType(PunishmentType.BAN_COMP)) {
                     GameHelper.sendChatToAll(game, "Cloud", EnumChatFormatting.RED + "Kicking " + playermp.username + ". You have been banned by CounterCraft AC.");
                     playermp.playerNetServerHandler.kickPlayerFromServer("Counter Craft AC Ban Active.");
                  }
               }

               if (game == null || game != GameManager.instance().matchMakingGame) {
                  ++playerData.mmNoGameDelay;
               }

               if (MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(playerData.username)) {
                  playerData.mmNoGameDelay = 0;
               }

               if (playerData.mmNoGameDelay > 40) {
                  playermp.playerNetServerHandler.kickPlayerFromServer("Match Making Error. Please Research for a Game.");
                  playerData.mmNoGameDelay = 0;
               }
            }
         }

         if (playerData.grenadeHit) {
            playermp.motionX = playermp.motionY = playermp.motionZ = 0.0D;
            playerData.grenadeHit = false;
         }

         if (playerData.isTeleporting && playermp.getHealth() > 0.0F) {
            playermp.setPositionAndUpdate(playerData.tpLocation.posX, playerData.tpLocation.posY, playerData.tpLocation.posZ);
            playermp.setPositionAndUpdate(playerData.tpLocation.posX, playerData.tpLocation.posY, playerData.tpLocation.posZ);
            playermp.playerNetServerHandler.setPlayerLocation(playerData.tpLocation.posX, playerData.tpLocation.posY, playerData.tpLocation.posZ, playermp.rotationYaw, playermp.rotationPitch);
            playerData.tpLocation = null;
            playerData.isTeleporting = false;
         }

         if (!this.hasHeadRegistered(playermp)) {
            EntityPlayerHead head = new EntityPlayerHead(playermp.worldObj);
            head.player = playermp;
            head.setPosition(playermp.posX, playermp.posY + 1.45D, playermp.posZ);
            playermp.worldObj.spawnEntityInWorld(head);
            this.playerHeads.add(head);
         }

         for(int i = 0; i < this.playerHeads.size(); ++i) {
            if (((EntityPlayerHead)this.playerHeads.get(i)).isDead || ((EntityPlayerHead)this.playerHeads.get(i)).player == null || ((EntityPlayerHead)this.playerHeads.get(i)).player.isDead) {
               this.playerHeads.remove(i);
            }
         }

         game = GameManager.instance().getPlayerGame((EntityPlayer)playermp);
         if (playerData.gameToClientDelay++ > 2) {
            PacketDispatcher.sendPacketToPlayer(CCPacketGame.buildPacket(game), (Player)playermp);
            playerData.gameToClientDelay = 0;
         }

         if (game != null) {
            game.getPlayerEventHandler().onPlayerUpdate(playermp);
         }

         if (playerData.isFrozen) {
            if (playermp.getHealth() > 0.0F) {
               if (playerData.frozenLocation == null) {
                  playerData.frozenLocation = new BlockLocation(playermp);
               }
            } else {
               playerData.frozenLocation = null;
            }
         } else {
            playerData.frozenLocation = null;
         }

         PacketDispatcher.sendPacketToPlayer(CCPacketFrozen.buildPacket(playerData.frozenLocation), (Player)playermp);
      }

   }

   public boolean hasHeadRegistered(EntityPlayer par1) {
      for(int i = 0; i < this.playerHeads.size(); ++i) {
         if (((EntityPlayerHead)this.playerHeads.get(i)).player == par1) {
            return true;
         }
      }

      return false;
   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.PLAYER, TickType.SERVER);
   }

   public String getLabel() {
      return "commontick";
   }
}
