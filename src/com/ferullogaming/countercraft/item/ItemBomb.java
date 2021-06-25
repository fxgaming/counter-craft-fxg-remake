package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.entity.EntityBomb;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameComponentBomb;
import com.ferullogaming.countercraft.network.CCPacketBombPlanted;
import com.ferullogaming.countercraft.network.CCPacketSoundPlayerRequest;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class ItemBomb extends ItemCC {
   public static boolean triggerHeld;
   public static boolean lastTriggerHeld;
   public static int plantTime;
   public static int plantTimeMax;

   public ItemBomb(int par1) {
      super(par1);
   }

   @SideOnly(Side.CLIENT)
   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
      EntityPlayer player = (EntityPlayer)par3Entity;
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      if (!data.isSpectating()) {
         float minX = 1.0F;
         float maxX = 1.5F;
         Random rand = new Random();
         float finalX = rand.nextFloat() * (maxX - minX) + minX;
         if (par2World.isRemote && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == par1ItemStack) {
            lastTriggerHeld = triggerHeld;
            triggerHeld = Mouse.isButtonDown(Mouse.getButtonIndex("BUTTON1"));
            IGame game = GameManager.instance().currentClientGame;
            if (game != null && game instanceof IGameComponentBomb) {
               IGameComponentBomb bombGame = (IGameComponentBomb)game;
               if (!bombGame.canPlantBomb(par2World, Minecraft.getMinecraft().thePlayer, par1ItemStack)) {
                  plantTime = 0;
                  return;
               }
            }

            if (triggerHeld && par1ItemStack.stackSize > 0) {
               par3Entity.motionX *= 0.0D;
               par3Entity.motionZ *= 0.0D;
               if (plantTime % 5 == 0 && plantTime == 0) {
                  PacketDispatcher.sendPacketToServer(CCPacketSoundPlayerRequest.buildPacket("countercraft:entity.bomb.plantstart", par3Entity.posX, par3Entity.posY, par3Entity.posZ, 1.0F, finalX));
               }

               if (plantTime % 5 == 0) {
                  PacketDispatcher.sendPacketToServer(CCPacketSoundPlayerRequest.buildPacket("countercraft:entity.bomb.plant", par3Entity.posX, par3Entity.posY, par3Entity.posZ, 1.0F, finalX));
               }

               if (plantTime++ >= plantTimeMax) {
                  PacketDispatcher.sendPacketToServer(CCPacketSoundPlayerRequest.buildPacket("countercraft:entity.bomb.planted", par3Entity.posX, par3Entity.posY, par3Entity.posZ, 1.0F, finalX));
                  PacketDispatcher.sendPacketToServer(CCPacketBombPlanted.buildPacket());
                  plantTime = -10;
               }
            } else {
               plantTime = 0;
            }
         }

      }
   }

   public void onBombPlanted(World par1, EntityPlayer par2, ItemStack par3) {
      EntityBomb bomb = new EntityBomb(par1, par2);
      IGame game = GameManager.instance().getPlayerGame(par2);
      if (game != null && game instanceof IGameComponentBomb) {
         bomb = new EntityBomb(par1, game, par2);
         ((IGameComponentBomb)game).onBombPlanted(par1, par2);
      }

      if (par3 != null && par3.stackSize > 0) {
         --par3.stackSize;
         bomb.setPosition(par2.posX, par2.posY, par2.posZ);
         par1.spawnEntityInWorld(bomb);
      }

   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      return true;
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      return true;
   }

   public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
      return true;
   }

   static {
      lastTriggerHeld = triggerHeld;
      plantTime = 0;
      plantTimeMax = 100;
   }
}
