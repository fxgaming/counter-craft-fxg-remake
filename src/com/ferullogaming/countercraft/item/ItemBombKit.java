package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.entity.EntityBomb;
import com.ferullogaming.countercraft.network.CCPacketBombDefuse;
import com.ferullogaming.countercraft.network.CCPacketSoundPlayerRequest;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class ItemBombKit extends ItemCC {
   public static boolean triggerHeld;
   public static boolean lastTriggerHeld;
   public static int defuseTime;
   public static int defuseTimeMax;
   public static boolean isDefusing;

   public ItemBombKit(int par1) {
      super(par1);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      return par1ItemStack;
   }

   @SideOnly(Side.CLIENT)
   public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
      if (par2World.isRemote && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == par1ItemStack) {
         lastTriggerHeld = triggerHeld;
         triggerHeld = Mouse.isButtonDown(Mouse.getButtonIndex("BUTTON1"));
         isDefusing = false;
         if (triggerHeld) {
            Minecraft.getMinecraft().entityRenderer.getMouseOver(1.0F);
            MovingObjectPosition object = Minecraft.getMinecraft().objectMouseOver;
            PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)Minecraft.getMinecraft().thePlayer);
            if (data.isGhost) {
               if (defuseTime > 0) {
                  PacketDispatcher.sendPacketToServer(CCPacketBombDefuse.buildPacket((EntityBomb)null, false));
                  defuseTime = 0;
               }

               return;
            }

            if (object != null && object.entityHit != null && object.entityHit instanceof EntityBomb) {
               EntityBomb bomb = (EntityBomb)object.entityHit;
               if (bomb.isBeingDefused() && !bomb.getDefuser().equals(Minecraft.getMinecraft().thePlayer.username)) {
                  defuseTime = 0;
               } else {
                  PacketDispatcher.sendPacketToServer(CCPacketBombDefuse.buildPacket(bomb, false));
                  par3Entity.motionX *= 0.0D;
                  par3Entity.motionZ *= 0.0D;
                  if (defuseTime == 0) {
                     PacketDispatcher.sendPacketToServer(CCPacketSoundPlayerRequest.buildPacket("countercraft:entity.defusekit.use", par3Entity.posX, par3Entity.posY, par3Entity.posZ, 1.0F, 2.0F));
                  }

                  if (defuseTime % 10 == 0) {
                     float var10002 = (float)par3Entity.posX;
                     float var10003 = (float)par3Entity.posY + 0.5F;
                     float var10004 = (float)par3Entity.posZ;
                     Minecraft.getMinecraft().sndManager.playSound("random.click", var10002, var10003, var10004, 1.0F, 1.0F);
                  }

                  if (defuseTime++ >= defuseTimeMax) {
                     if (defuseTime > defuseTimeMax) {
                        defuseTime = defuseTimeMax;
                     }

                     PacketDispatcher.sendPacketToServer(CCPacketBombDefuse.buildPacket(bomb, true));
                  }
               }
            } else {
               defuseTime = 0;
            }
         } else {
            defuseTime = 0;
         }

         if (triggerHeld != lastTriggerHeld && !triggerHeld) {
            PacketDispatcher.sendPacketToServer(CCPacketBombDefuse.buildPacket((EntityBomb)null, false));
         }
      }

   }

   public void onBombClicked(World par1, EntityPlayer par2, ItemStack par3, EntityBomb par4, boolean par5) {
      if (par4 != null) {
         if (!par4.isBeingDefused()) {
            par4.setDefusing(true, par2.username);
         }

         if (!par4.isDefused() && par5) {
            par4.setDefused();
         }
      } else {
         ArrayList list = (ArrayList)par1.getEntitiesWithinAABB(EntityBomb.class, par2.boundingBox.expand(10.0D, 10.0D, 10.0D));
         if (!list.isEmpty()) {
            for(int i = 0; i < list.size(); ++i) {
               EntityBomb bomb = (EntityBomb)list.get(i);
               if (bomb.getDefuser().equalsIgnoreCase(par2.username)) {
                  bomb.setDefusing(false, "");
               }
            }
         }
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
      defuseTime = 0;
      defuseTimeMax = 100;
      isDefusing = false;
   }
}
