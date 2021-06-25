package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.entity.EntityGrenade;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.network.CCPacketCalloutSound;
import com.ferullogaming.countercraft.network.CCPacketGrenadeThrowing;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class ItemGrenade extends ItemCC {
   public static boolean rightHeld;
   public static boolean lastRightHeld;
   public static boolean leftHeld;
   public static boolean lastLeftHeld;
   public boolean throwing = false;
   public boolean throwingDouble = false;
   public double throwingForce = 1.1D;
   String soundThrow = "countercraft:grenadeThrow";

   public ItemGrenade(int par1) {
      super(par1);
      this.setCreativeTab(ItemManager.tabCounterCraft);
      this.setMaxStackSize(1);
   }

   @SideOnly(Side.CLIENT)
   public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
      if (world.isRemote && Minecraft.getMinecraft().currentScreen == null) {
         double runningForce = 0.0D;
         if (entity instanceof EntityPlayerSP) {
            EntityPlayerSP playersp = (EntityPlayerSP)entity;
            PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)playersp);
            int running = Math.round(playersp.moveForward);
            runningForce = (double)((float)running) * 0.13D;
            if (data.isSpectating()) {
               return;
            }

            if (playersp.isSprinting()) {
               runningForce += 0.13D;
            }

            if (data.isFrozen) {
               return;
            }
         }

         lastRightHeld = rightHeld;
         rightHeld = Mouse.isButtonDown(Mouse.getButtonIndex("BUTTON1"));
         lastLeftHeld = leftHeld;
         leftHeld = Mouse.isButtonDown(Mouse.getButtonIndex("BUTTON0"));
         if (leftHeld) {
            this.throwingForce = 1.4D;
            this.throwing = true;
         }

         if (rightHeld) {
            this.throwingForce = 0.6D;
            this.throwing = true;
         }

         this.throwingForce += runningForce;
         if (!leftHeld && !rightHeld && this.throwing) {
            PacketDispatcher.sendPacketToServer(CCPacketGrenadeThrowing.buildPacket(this.throwingForce));
            this.throwing = false;
         }
      }

   }

   public void onGrenadeThrown(ItemStack itemstack, World world, EntityPlayer player, double force) {
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      if (data == null || !data.isFrozen) {
         world.playSoundAtEntity(player, this.soundThrow, 1.0F, 1.0F);
         double timeSeconds = 1.7D;
         world.spawnEntityInWorld(new EntityGrenade(world, player, force, (int)(timeSeconds * 20.0D)));
         if (!player.capabilities.isCreativeMode) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
         }

         if (GameManager.instance().isPlayerInGame(player)) {
            String sound = "hegrenade";
            String team = GameManager.instance().getPlayerGame(player).getPlayerEventHandler().getPlayerTeam(player).teamName;
            PacketDispatcher.sendPacketToAllPlayers(CCPacketCalloutSound.buildPacket(team, sound, player.username));
            data.calloutTimer = 100;
         }

      }
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      par3List.add("ЛКМ что-бы кинуть далеко,");
      par3List.add("ПКМ что-бы кинуть близко!");
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
      lastRightHeld = rightHeld;
      lastLeftHeld = leftHeld;
   }
}
