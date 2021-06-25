package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.item.ItemBomb;
import com.ferullogaming.countercraft.item.ItemBombKit;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.network.CCPacketGameNotification;
import com.ferullogaming.countercraft.network.CCPacketKillFeed;
import com.ferullogaming.countercraft.network.CCPacketKilledMessage;
import com.ferullogaming.countercraft.network.CCPacketUpdateGameTP;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class GameHelper {
   public static void clearDrops(IGame par1) {
      if (par1 != null && par1.getPlayerEventHandler() != null && par1.getPlayerEventHandler().getLobby() != null) {
         BlockLocation lobby = par1.getPlayerEventHandler().getLobby();
         World world = MinecraftServer.getServer().getEntityWorld();
         if (world != null) {
            int radius = 500;
            ArrayList list = (ArrayList)world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(lobby.posX - (double)radius, lobby.posY - (double)radius, lobby.posZ - (double)radius, lobby.posX + (double)radius, lobby.posY + (double)radius, lobby.posZ + (double)radius));
            if (!list.isEmpty()) {
               for(int i = 0; i < list.size(); ++i) {
                  ((Entity)list.get(i)).setDead();
               }
            }
         }
      }

   }

   public static void clearPlayersInventory(ArrayList par1) {
      Iterator i$ = par1.iterator();

      while(i$.hasNext()) {
         String name = (String)i$.next();
         getPlayerFromUsername(name).inventory.clearInventory(-1, -1);
         getPlayerFromUsername(name).inventoryContainer.detectAndSendChanges();
      }

   }

   public static void clearSpecificPlayerInventory(String givenUsername) {
      getPlayerFromUsername(givenUsername).inventory.clearInventory(-1, -1);
      getPlayerFromUsername(givenUsername).inventoryContainer.detectAndSendChanges();
   }

   public static void playSoundFireworks(World par1, double x, double y, double z) {
      par1.playSoundEffect(x, y, z, "fireworks.launch", 1.0F, 1.0F);
      par1.playSoundEffect(x, y, z, "fireworks.largeBlast", 1.0F, 1.0F);
      par1.playSoundEffect(x, y, z, "fireworks.launch", 1.0F, 1.0F);
      par1.playSoundEffect(x, y, z, "fireworks.twinkle", 1.0F, 1.0F);
   }

   public static void playMusic(World par1, double x, double y, double z, String string) {
      par1.playSoundEffect(x, y, z, "countercraft:" + string, 0.7F, 1.0F);
   }

   public static void playerMusicAtPlayers(World par1, ArrayList par2, String par3) {
      for(int i = 0; i < par2.size(); ++i) {
         playMusicAtPlayer(par1, (String)par2.get(i), par3);
      }

   }

   public static void playerSoundAtPlayers(World par1, ArrayList par2, String par3) {
      for(int i = 0; i < par2.size(); ++i) {
         playSoundAtPlayer(par1, (String)par2.get(i), par3);
      }

   }

   public static void playMusicAtPlayer(World par1, String par2, String par3) {
      EntityPlayer player = getPlayerFromUsername(par2);
      if (player != null) {
         playerMusicAt(par1, player.posX, player.posY, player.posZ, par3);
      }

   }

   public static void playSoundAtPlayer(World par1, String par2, String par3) {
      EntityPlayer player = getPlayerFromUsername(par2);
      if (player != null) {
         playerSoundAt(par1, player.posX, player.posY, player.posZ, par3);
      }

   }

   public static void playerMusicAt(World par1, double x, double y, double z, String par3) {
      par1.playSoundEffect(x, y, z, par3, 0.7F, 1.0F);
   }

   public static void playerSoundAt(World par1, double x, double y, double z, String par3) {
      par1.playSoundEffect(x, y, z, par3, 0.7F, 1.0F);
   }

   public static void sendKilledMessage(String par1, KilledMessage par2, boolean bomb) {
      PacketDispatcher.sendPacketToPlayer(CCPacketKilledMessage.buildPacket(par2, bomb), (Player)getPlayerFromUsername(par1));
   }

   public static void sendKillFeedMessage(IGame par1, KillFeedMessage par2) {
      for(int i = 0; i < par1.getPlayerEventHandler().getPlayers().size(); ++i) {
         String player = (String)par1.getPlayerEventHandler().getPlayers().get(i);
         PacketDispatcher.sendPacketToPlayer(CCPacketKillFeed.buildPacket(par2), (Player)getPlayerFromUsername(player));
      }

   }

   public static void sendGameNotification(ArrayList par1, String par2) {
      Iterator i$ = par1.iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         sendGameNotification(var1, par2);
      }

   }

   public static void sendGameNotification(String par1, String par2) {
      GameNotification not = new GameNotification(par2);
      if (getPlayerFromUsername(par1) != null) {
         if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            PacketDispatcher.sendPacketToPlayer(CCPacketGameNotification.buildPacket(not), (Player)getPlayerFromUsername(par1));
         } else if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            ClientTickHandler.addGameNotification(not);
         }
      }

   }

   public static void refreshPlayerGuns(String par1) {
      EntityPlayer player = getPlayerFromUsername(par1);
      if (player != null) {
         for(int i = 0; i < player.inventory.mainInventory.length; ++i) {
            ItemStack stack = player.inventory.mainInventory[i];
            if (stack != null && stack.getItem() instanceof ItemGun) {
               ItemGun.setAmmoMax(stack);
               player.inventoryContainer.detectAndSendChanges();
            }
         }
      }

   }

   public static void refreshPlayersMCLevels(ArrayList par1) {
      Iterator i$ = par1.iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         refreshPlayerMCLevels(getPlayerFromUsername(var1));
      }

   }

   public static void refreshPlayerMCLevels(EntityPlayer par1) {
      if (par1 != null && !par1.worldObj.isRemote) {
         par1.setHealth(20.0F);
         par1.getFoodStats().addStats(20, 20.0F);
      }

   }

   public static ArrayList orderPlayersByValue(IGame par1, ArrayList par2, String par3) {
      if (par3 != null && par2.size() > 1) {
         ArrayList copyOf = new ArrayList(par2);
         ArrayList returnList = new ArrayList();

         while(copyOf.size() > 0) {
            String username = (String)copyOf.get(0);

            for(int i = 0; i < copyOf.size(); ++i) {
               if (par1.getPlayerGameData((String)copyOf.get(i)).getInteger(par3) > par1.getPlayerGameData(username).getInteger(par3)) {
                  username = (String)copyOf.get(i);
               }
            }

            returnList.add(username);
            copyOf.remove(username);
         }

         ArrayList returnList2 = new ArrayList();
         Iterator i$ = returnList.iterator();

         while(i$.hasNext()) {
            String var1 = (String)i$.next();
            if (!returnList2.contains(var1)) {
               returnList2.add(var1);
            }
         }

         return returnList2;
      } else {
         return par2;
      }
   }

   public static ArrayList getTopPlayers(IGame par1, int par2, String par3, ArrayList par4Players) {
      ArrayList list = orderPlayersByValue(par1, par4Players, par3);
      return par2 >= list.size() ? list : new ArrayList(list.subList(0, par2));
   }

   public static ArrayList getRandomPlayers(IGame par1, int par2) {
      ArrayList copyOf = new ArrayList(par1.getPlayerEventHandler().getPlayers());
      ArrayList returnList = new ArrayList();
      Random rand = new Random();

      for(int i = 0; i < par2; ++i) {
         if (copyOf.size() > 0) {
            int var1 = rand.nextInt(copyOf.size());
            returnList.add(new String((String)copyOf.get(var1)));
            copyOf.remove(var1);
         }
      }

      return returnList;
   }

   public static void freezeAllPlayers(IGame par1) {
      for(int i = 0; i < par1.getPlayerEventHandler().getPlayers().size(); ++i) {
         String player = (String)par1.getPlayerEventHandler().getPlayers().get(i);
         freezePlayer(player);
      }

   }

   public static void unfreezeAllPlayers(IGame par1) {
      for(int i = 0; i < par1.getPlayerEventHandler().getPlayers().size(); ++i) {
         String player = (String)par1.getPlayerEventHandler().getPlayers().get(i);
         unfreezePlayer(player);
      }

   }

   public static void freezePlayer(String par1) {
      PlayerData data = PlayerDataHandler.getPlayerData(par1);
      data.isFrozen = true;
   }

   public static void unfreezePlayer(String par1) {
      PlayerData data = PlayerDataHandler.getPlayerData(par1);
      data.isFrozen = false;
   }

   public static void kickPlayers(ArrayList par1, String par2Reason) {
      for(int i = 0; i < par1.size(); ++i) {
         kickPlayer((String)par1.get(i), par2Reason);
      }

   }

   public static void kickPlayer(String par1, String par2Reason) {
      if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
         EntityPlayer player = getPlayerFromUsername(par1);
         if (player != null && player instanceof EntityPlayerMP) {
            ((EntityPlayerMP)player).playerNetServerHandler.kickPlayerFromServer(par2Reason);
         }
      }

   }

   public static EntityPlayer getPlayerFromUsername(String par1) {
      return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(par1);
   }

   public static void updatePlayerLocation(EntityPlayer par1) {
      ArrayList list = (ArrayList)FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
      World world = par1.worldObj;
      if (world instanceof WorldServer) {
         WorldServer sw = (WorldServer)world;
         sw.getPlayerManager().updatePlayerInstances();
         sw.getEntityTracker().updateTrackedEntities();
      }

      BlockLocation loc = new BlockLocation(par1);
      PacketDispatcher.sendPacketToAllPlayers(CCPacketUpdateGameTP.buildPacket(par1.entityId, loc));
   }

   public static void teleportPlayer(String par1, BlockLocation par2) {
      teleportPlayer(getPlayerFromUsername(par1), par2);
      getPlayerFromUsername(par1).inventoryContainer.detectAndSendChanges();
   }

   public static void teleportPlayer(EntityPlayer par1, BlockLocation par2) {
      if (par1 != null && par2 != null && !par1.worldObj.isRemote && par1 instanceof EntityLivingBase) {
         PlayerData data = PlayerDataHandler.getPlayerData(par1);
         if (data != null) {
            data.teleportPlayer(par2);
            par1.setPositionAndUpdate(par2.posX, par2.posY, par2.posZ);
            par1.fallDistance = 0.0F;
            updatePlayerLocation(par1);
         }
      }

   }

   public static void sendChatToPlayer(IGame par1, String par2, String par3, String par4) {
      EntityPlayer player = getPlayerFromUsername(par2);
      if (par1.getPlayerEventHandler().isPlayerPresent(par2) && player != null) {
         String prefix = "";
         if (par2 != null && par2.length() > 0) {
            prefix = "" + EnumChatFormatting.GRAY + "" + par3 + "" + EnumChatFormatting.RESET + chatSeperator();
         }

         String spacing = "";
         String spacingMax = "                                                                 ";
         if (par4.startsWith("/c")) {
            int var1 = spacingMax.length();
            int var2 = spacingMax.length() * 4;
            par4 = par4.replaceAll("/c", "").trim();
            int var3 = par4.length() * 4;
            int var4 = var2 / 2 - var3 / 2;
            int var5 = var4 / 4;

            for(int i = 0; i < var5; ++i) {
               spacing = spacing + " ";
            }
         }

         player.sendChatToPlayer(ChatMessageComponent.createFromText(prefix + spacing + par4));
      }

   }

   public static void sendChatToAll(IGame par1, String par2, String par3) {
      Iterator i$ = par1.getPlayerEventHandler().getPlayers().iterator();

      while(i$.hasNext()) {
         String player = (String)i$.next();
         sendChatToPlayer(par1, player, par2, par3);
      }

   }

   public static void sendChatToTeam(IGame par1, Team par2, String par3, String par4) {
      Iterator i$ = par2.getPlayers().iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         sendChatToPlayer(par1, var1, par3, par4);
      }

   }

   public static String chatSeperator() {
      return EnumChatFormatting.RESET + "" + EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + " >> " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY;
   }

   public static int getPlayerGameValue(IGame par1, String par2, String par3) {
      return par1.getPlayerEventHandler().isPlayerPresent(par2) ? par1.getPlayerGameData(par2).getInteger(par3) : 0;
   }

   public static void setPlayerGameValue(IGame par1, String par2, String par3, int par4) {
      if (par1.getPlayerEventHandler().isPlayerPresent(par2)) {
         par1.getPlayerGameData(par2).setInteger(par3, par4);
      }

   }

   public static void increasePlayerGameValue(IGame par1, String par2, String par3) {
      int var1 = getPlayerGameValue(par1, par2, par3);
      setPlayerGameValue(par1, par2, par3, var1 + 1);
   }

   public static void increasePlayerGameValue(IGame par1, String par2, String par3, int par4) {
      int var1 = getPlayerGameValue(par1, par2, par3);
      setPlayerGameValue(par1, par2, par3, var1 + par4);
   }

   public static void decreasePlayerGameValue(IGame par1, String par2, String par3) {
      int var1 = getPlayerGameValue(par1, par2, par3);
      setPlayerGameValue(par1, par2, par3, var1 - 1);
   }

   public static void decreasePlayerGameValue(IGame givenGame, String par2, String par3, int par4) {
      int var1 = getPlayerGameValue(givenGame, par2, par3);
      setPlayerGameValue(givenGame, par2, par3, var1 - par4);
   }

   public static void setPlayerInventoryOrganized(EntityPlayer givenPlayer) {
      ArrayList invlist = new ArrayList(Arrays.asList((Object[])givenPlayer.inventory.mainInventory.clone()));
      givenPlayer.inventory.clearInventory(-1, -1);
      int var1 = 3;

      for(int i = 0; i < invlist.size(); ++i) {
         ItemStack itemstack = (ItemStack)invlist.get(i);
         if (itemstack != null) {
            if (itemstack.getItem() instanceof ItemGun) {
               if (((ItemGun)itemstack.getItem()).isPrimary) {
                  givenPlayer.inventory.setInventorySlotContents(0, itemstack);
               } else {
                  givenPlayer.inventory.setInventorySlotContents(1, itemstack);
               }
            } else if (itemstack.getItem() instanceof ItemKnife) {
               givenPlayer.inventory.setInventorySlotContents(2, itemstack);
            } else if (!(itemstack.getItem() instanceof ItemBomb) && !(itemstack.getItem() instanceof ItemBombKit)) {
               givenPlayer.inventory.setInventorySlotContents(var1++, itemstack);
            } else {
               givenPlayer.inventory.setInventorySlotContents(6, itemstack);
            }
         }
      }

      givenPlayer.inventoryContainer.detectAndSendChanges();
   }

   public static void setPlayerInventory(EntityPlayer givenPlayer, Loadout givenLoadout) {
      setPlayerInventory(givenPlayer, givenLoadout, false);
   }

   public static void setPlayerInventory(EntityPlayer givenPlayer, Loadout givenLoadout, boolean givenShouldDrop) {
      if (givenLoadout != null && !givenPlayer.worldObj.isRemote) {
         if (!givenShouldDrop) {
            givenPlayer.inventory.clearInventory(-1, -1);
         }

         int var1 = 3;

         for(int i = 0; i < givenLoadout.getInventoryList().size(); ++i) {
            ItemStack itemstack = ((ItemStack)givenLoadout.getInventoryList().get(i)).copy();
            if (itemstack.getItem() instanceof ItemGun) {
               if (!givenPlayer.worldObj.isRemote) {
                  ItemGun.loadItemStackSkin(itemstack, givenPlayer);
               }

               EntityItem entity;
               if (((ItemGun)itemstack.getItem()).isPrimary) {
                  if (givenShouldDrop && givenPlayer.inventory.getStackInSlot(0) != null && givenPlayer.inventory.getStackInSlot(0).itemID != itemstack.itemID) {
                     entity = new EntityItem(givenPlayer.worldObj, givenPlayer.posX, givenPlayer.posY + 1.0D, givenPlayer.posZ, givenPlayer.inventory.getStackInSlot(0).copy());
                     givenPlayer.worldObj.spawnEntityInWorld(entity);
                  }

                  givenPlayer.inventory.setInventorySlotContents(0, itemstack);
               } else {
                  if (givenShouldDrop && givenPlayer.inventory.getStackInSlot(1) != null && givenPlayer.inventory.getStackInSlot(1).itemID != itemstack.itemID) {
                     entity = new EntityItem(givenPlayer.worldObj, givenPlayer.posX, givenPlayer.posY + 1.0D, givenPlayer.posZ, givenPlayer.inventory.getStackInSlot(1).copy());
                     givenPlayer.worldObj.spawnEntityInWorld(entity);
                  }

                  givenPlayer.inventory.setInventorySlotContents(1, itemstack);
               }
            } else if (itemstack.getItem() instanceof ItemKnife) {
               givenPlayer.inventory.setInventorySlotContents(2, itemstack);
               ItemKnife.loadItemStackSkin(givenPlayer, 2, itemstack);
            } else if (itemstack.getItem() instanceof ItemGrenade) {
               givenPlayer.inventory.setInventorySlotContents(var1++, itemstack);
            } else if (itemstack.getItem() instanceof ItemBomb || itemstack.getItem() instanceof ItemBombKit) {
               givenPlayer.inventory.setInventorySlotContents(6, itemstack);
            }
         }

         givenPlayer.inventoryContainer.detectAndSendChanges();
      }

   }

   public static void refreshPlayerConnectionInfo(EntityPlayer givenPlayer) {
      EntityPlayerMP entityPlayerMP = (EntityPlayerMP)givenPlayer;
      MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet201PlayerInfo(entityPlayerMP.getCommandSenderName(), true, entityPlayerMP.ping));
   }
}
