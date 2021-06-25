package com.ferullogaming.countercraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.ferullogaming.countercraft.acloud.ControlProcessor;
import com.ferullogaming.countercraft.damagesource.DamageSourceBullet;
import com.ferullogaming.countercraft.damagesource.DamageSourceGrenade;
import com.ferullogaming.countercraft.entity.EntityManager;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.network.PacketSkinsToUser;
import com.ferullogaming.countercraft.network.cloud.SCLoginPacket;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class CommonEvents {
   public CommonEvents() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   @ForgeSubscribe
   public void onServerChat(ServerChatEvent event) {
      if (event.component.toString().contains("moved too quickly!")) {
         event.setCanceled(true);
      }

   }

   @ForgeSubscribe
   public void playSound(PlaySoundAtEntityEvent event) {
      if (event.entity instanceof EntityPlayer) {
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)event.entity);
         if (playerData != null && (playerData.isSpectating() || playerData.isGhost)) {
            event.setCanceled(true);
         } else if (event.name.equals("step.grass")) {
            event.name = "countercraft:entity.human.step.grass";
         } else if (event.name.equals("step.sand")) {
            event.name = "countercraft:entity.human.step.sand";
         } else if (event.name.equals("step.snow")) {
            event.name = "countercraft:entity.human.step.snow";
         } else if (event.name.equals("step.stone")) {
            event.name = "countercraft:entity.human.step.stone";
         } else if (!event.name.equals("step.metal") && !event.name.equals("dig.metal")) {
            if (event.name.equals("step.wood")) {
               event.name = "countercraft:entity.human.step.wood";
            } else if (event.name.equals("step.gravel")) {
               event.name = "countercraft:entity.human.step.gravel";
            }
         } else {
            event.name = "countercraft:entity.human.step.metal";
         }
      }

   }
   
   @ForgeSubscribe
   public void a(EntityJoinWorldEvent e) {
	   if (e.entity instanceof EntityPlayer) {
		   ControlProcessor.unit_00();
		   ControlProcessor.unit_11(e.entity.getEntityName().toLowerCase());
		   PacketDispatcher.sendPacketToPlayer(new PacketSkinsToUser().buildPacket((EntityPlayer)e.entity), (Player)e.entity);
		   PacketDispatcher.sendPacketToPlayer(new SCLoginPacket().buildPacket((EntityPlayer)e.entity), (Player)e.entity);
		   PacketDispatcher.sendPacketToServer(new SCLoginPacket().buildPacket((EntityPlayer)e.entity));
	   }
   }

   @ForgeSubscribe
   public void onBlockInteract(PlayerInteractEvent event) {
      if (!event.entityPlayer.capabilities.isCreativeMode && Action.RIGHT_CLICK_BLOCK != null && (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 58 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 145 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 61 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 54 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 146 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 130 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 23 || event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == 116)) {
         event.setCanceled(true);
      }

   }

   @ForgeSubscribe
   public void onEntityDamaged(LivingAttackEvent event) {
      DamageSource source = event.source;
      if (event.source != null) {
         EntityPlayer player1;
         PlayerData data1;
         if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
            player1 = (EntityPlayer)event.source.getEntity();
            data1 = PlayerDataHandler.getPlayerData(player1);
            if (data1.isGhost || player1.entityId == event.entity.entityId) {
               event.setCanceled(true);
               return;
            }
         }

         if (event.entityLiving != null && event.entityLiving instanceof EntityPlayer) {
            player1 = (EntityPlayer)event.entityLiving;
            data1 = PlayerDataHandler.getPlayerData(player1);
            if (data1.isGhost) {
               event.setCanceled(true);
               return;
            }

            if (event.source.damageType.equals("explosion") || event.source.damageType.equals("explosion.player")) {
               source = new DamageSourceGrenade(event.source.getEntity(), new ItemStack(ItemManager.grenadeFrag));
               data1.grenadeHit = true;
            }

            if (GameManager.instance().getPlayerGame(player1) != null) {
               IGame game = GameManager.instance().getPlayerGame(player1);
               if (game.getPlayerEventHandler().onPlayerDamaged(player1, (DamageSource)source)) {
                  event.setCanceled(true);
                  return;
               }
            }
         }
      }

   }

   @ForgeSubscribe
   public void onItemPicked(EntityItemPickupEvent event) {
      if (event.entityPlayer != null && event.item.getEntityItem() != null) {
         EntityPlayer player = event.entityPlayer;
         PlayerData data = PlayerDataHandler.getPlayerData(player);
         if (data.isGhost) {
            event.setCanceled(true);
         }

         if (!player.worldObj.isRemote && GameManager.instance().getPlayerGame(player) != null) {
            IGame game = GameManager.instance().getPlayerGame(player);
            if (!game.getPlayerEventHandler().allowItemPickup(player, event.item.getEntityItem(), event.item)) {
               event.setCanceled(true);
            }
         }
      }

   }

   @ForgeSubscribe
   public void onItemDropped(ItemTossEvent event) {
      EntityPlayer player = event.player;
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      if (data.isGhost) {
         event.setCanceled(true);
      }

      if (!player.worldObj.isRemote && GameManager.instance().isPlayerInGame(player)) {
         IGame game = GameManager.instance().getPlayerGame(player);
         if (!game.getPlayerEventHandler().allowItemTossed(player, event.entityItem.getEntityItem())) {
            event.setCanceled(true);
         }
      }

   }

   @ForgeSubscribe
   public void onItemsDroppedWhenKilled(PlayerDropsEvent event) {
      EntityPlayer player = event.entityPlayer;
      if (!player.worldObj.isRemote && GameManager.instance().isPlayerInGame(player)) {
         IGame game = GameManager.instance().getPlayerGame(player);
         ArrayList list = game.getPlayerEventHandler().onPlayerDeathItemsDropped(player, new ArrayList(event.drops));
         if (list == null) {
            event.setCanceled(true);
         } else {
            event.drops.clear();
            event.drops.addAll(list);
         }
      }

   }

   @ForgeSubscribe
   public void onEntityKilled(LivingDeathEvent event) {
      DamageSource source = null;
      if (event.entityLiving instanceof EntityPlayer) {
         if (!event.source.damageType.equals("explosion") && !event.source.damageType.equals("explosion.player")) {
            source = event.source;
         } else {
            source = new DamageSourceGrenade(event.source.getEntity(), new ItemStack(ItemManager.grenadeFrag));
         }

         Entity damageSource = ((DamageSource)source).getEntity();
         EntityPlayer killed = (EntityPlayer)event.entityLiving;
         PlayerData data = PlayerDataHandler.getPlayerData(killed);
         data.isAiming = false;
         data.wearingKevlar = false;
         data.wearingHelmet = false;
         if (GameManager.instance().getPlayerGame(killed) != null) {
            IGame game = GameManager.instance().getPlayerGame(killed);
            data.isGhost = true;
            data.ghostDelay = 100;
            if (damageSource != null && damageSource instanceof EntityPlayer) {
               EntityPlayer killer = (EntityPlayer)damageSource;
               if (GameManager.instance().isSameGamePresences(killer, killed)) {
                  game.getPlayerEventHandler().onPlayerDeath(killed, (DamageSource)source);
               }
            } else {
               game.getPlayerEventHandler().onPlayerDeath(killed, (DamageSource)source);
            }

            if (data.isGhost) {
               Random rand = new Random();

               for(int i = 0; i < 20; ++i) {
                  double d0 = rand.nextGaussian() * 0.02D;
                  double d1 = rand.nextGaussian() * 0.02D;
                  double d2 = rand.nextGaussian() * 0.02D;
                  EntityManager.spawnParticle("explode", killed.posX + (double)(rand.nextFloat() * killed.width * 2.0F) - (double)killed.width, killed.posY + (double)(rand.nextFloat() * killed.height), killed.posZ + (double)(rand.nextFloat() * killed.width * 2.0F) - (double)killed.width, d0, d1, d2);
               }

               ArrayList items = new ArrayList();

               for(int i = 0; i < killed.inventory.mainInventory.length; ++i) {
                  if (killed.inventory.mainInventory[i] != null) {
                     items.add(new EntityItem(killed.worldObj, killed.posX, killed.posY, killed.posZ, killed.inventory.mainInventory[i].copy()));
                  }
               }

               killed.inventory.clearInventory(-1, -1);
               event.entityLiving.setHealth(20.0F);
               event.setCanceled(true);
               PlayerDropsEvent event1 = new PlayerDropsEvent(killed, (DamageSource)source, items, false);
               if (!MinecraftForge.EVENT_BUS.post(event1)) {
                  Iterator i$ = items.iterator();

                  while(i$.hasNext()) {
                     EntityItem item1 = (EntityItem)i$.next();
                     killed.worldObj.spawnEntityInWorld(item1);
                  }
               }
            }
         }
      }

   }

   public void onBulletCollisionEntity(EntityPlayer par1, ItemStack par2, EntityLivingBase par3, boolean par4, int par5) {
      DamageSourceBullet damageSource = (new DamageSourceBullet(par1, par2)).setHeadshot(par4).setWallbang(par5 > 0, par5);
      int damage = ((ItemGun)par2.getItem()).gunDamage;
      if (par3 instanceof EntityPlayer) {
         PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)par3);
         if (!data.isGhost) {
            this.onBulletCollisionOtherPlayer(par1, (EntityPlayer)par3, damageSource);
         }

      } else {
         if (par5 > 0) {
            damage -= damage * par5 / 100;
         }

         if (damage > 0) {
            par3.hurtResistantTime = 0;
            par3.attackEntityFrom(damageSource, (float)damage);
            par3.motionX = par3.motionY = par3.motionZ = 0.0D;
         }

      }
   }

   public void onBulletCollisionOtherPlayer(EntityPlayer par1, EntityPlayer par3, DamageSourceBullet source) {
      if (par1.getHealth() > 0.0F && !par1.isDead) {
         if (GameManager.instance().isSameGamePresences(par1, par3)) {
            IGame game = GameManager.instance().getPlayerGame(par1);
            if (game.getPlayerEventHandler().onPlayerDamaged(par3, source)) {
               return;
            }
         }

         this.damagePlayerFromShot(par1, par3, source.getWeaponUsed(), source);
      }

   }

   public void damagePlayerFromShot(EntityPlayer par1, EntityPlayer par2, ItemStack par3, DamageSourceBullet par4) {
      int damage = ((ItemGun)par3.getItem()).gunDamage;
      PlayerData attackedPlayerData = PlayerDataHandler.getPlayerData(par2);
      if (par4.isHeadshot()) {
         damage = ((ItemGun)par3.getItem()).gunDamageHead;
         if (attackedPlayerData != null && attackedPlayerData.isWearingHelmet()) {
            damage -= damage / 4;
            --attackedPlayerData.helmetHealth;
         }
      } else if (attackedPlayerData != null && attackedPlayerData.isWearingKevlar()) {
         damage -= damage / 4;
         --attackedPlayerData.kevlarHealth;
      }

      if (par4.isWallbang()) {
         damage -= damage * par4.getWallbangReduction() / 100;
      }

      if (damage > 0) {
         if (FMLCommonHandler.instance().getSide() == Side.SERVER && ServerManager.instance().isLobby) {
            return;
         }

         if (!par4.isHeadshot()) {
            if (attackedPlayerData != null && attackedPlayerData.isWearingKevlar()) {
               par2.worldObj.playSoundAtEntity(par2, "countercraft:entity.human.kevlarshot", 2.0F, 1.0F);
            }
         } else {
            par1.worldObj.playSoundAtEntity(par1, "random.break", 2.0F, 1.5F);
            par2.worldObj.playSoundAtEntity(par2, attackedPlayerData != null && attackedPlayerData.isWearingHelmet() ? "countercraft:entity.human.headshothelmet" : "countercraft:entity.human.headshot", 2.0F, 1.0F);
         }

         par2.hurtTime = 0;
         par2.hurtResistantTime = 0;
         par2.attackEntityFrom(par4, (float)damage);
         par2.motionX = par2.motionY = par2.motionZ = 0.0D;
      }

   }

   public void onBulletCollisionBlock(EntityPlayer par1, ItemStack itemstack, World par2, double par3, double par4, double par5, int par6, int par7, Vec3 hitVec) {
   }
}
