package com.ferullogaming.countercraft.item.gun;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.anim.AnimationManager;
import com.ferullogaming.countercraft.client.anim.GunAnimation;
import com.ferullogaming.countercraft.client.anim.GunAnimationInspect;
import com.ferullogaming.countercraft.client.anim.GunAnimationReload;
import com.ferullogaming.countercraft.client.anim.GunAnimationSwitch;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.particle.ParticleEffects;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.item.IItemMovementPenalty;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.network.CCPacketGunReload;
import com.ferullogaming.countercraft.network.CCPacketGunSound;
import com.ferullogaming.countercraft.network.CCPacketGunSwitch;
import com.ferullogaming.countercraft.network.CCPacketGunTrigger;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ItemGun extends ItemCC implements IItemMovementPenalty {
   public static boolean triggerHeld;
   public static boolean lastTriggerHeld;
   public static boolean reloadKey;
   public static boolean lastReloadKey;
   public static boolean switchKey;
   public static boolean lastSwitchKey;
   public static boolean inspectKey;
   public static boolean lastInspectKey;
   public static boolean aimKey;
   public static boolean lastAimKey;
   private static boolean lastIsAiming;
   private static int boltBackDelay;
   public int gunDamage;
   public int gunDamageHead;
   public float gunRecoil;
   public int gunBurstAmount;
   public float gunSpreadIncreaseFired;
   public float gunSpreadIncreaseWalking = -1.0F;
   public float gunSpreadIncreaseRunning = -1.0F;
   public float gunSpreadIncreaseJumping = -1.0F;
   public float gunSpreadDecrease = -1.0F;
   public float gunSpreadDecreaseShift = -1.0F;
   public float gunSpreadMaxWalking = -1.0F;
   public float gunSpreadMaxRunning = -1.0F;
   public int bulletDistance = 160;
   public double gunMovementPenalty = 0.0D;
   public String soundFire;
   public String soundDistant;
   public String soundFireSuppressed;
   public String soundReload;
   public String soundSecondary;
   public String soundEmpty = "gunEmpty";
   public String soundFiremode = "gunFiremode";
   public int clipSize = 0;
   public int gunMaxAmmo = 0;
   public boolean hasSecondary = false;
   public boolean isPrimary = true;
   public boolean isAimAloud = false;
   public float zoomLevel = 0.0F;
   public boolean renderCrosshairs = true;
   public boolean zoomOutOnFire = false;
   public ResourceLocation sightTexture;
   public ResourceLocation sightTextureBlur;
   public int bulletsFired = 1;
   public boolean firstBulletSpread = false;
   public EnumFiremode firemode;
   public double tickFire;
   public double delayReload;
   public double delayReloadClient;
   public double delaySwitch;
   public double delaySwitchClient;
   public double delayFire;
   public double delayBurst;
   public double delayGui;
   private Class animationWhenFired;
   private Class animationWhenReloaded;
   private Class animationWhenInspecting;
   private Class animationWhenFiremodeChanged;
   private int zoomState;
   public boolean secondaryFire;
   public static int gunid;

   public ItemGun(int givenItemID, double givenFireRate, double givenReloadTime, float givenRecoil, int id) {
      super(givenItemID);
      this.firemode = EnumFiremode.AUTO;
      this.tickFire = 0.0D;
      this.delayReload = 40.0D;
      this.delayReloadClient = 0.0D;
      this.delaySwitch = 40.0D;
      this.delaySwitchClient = 0.0D;
      this.delayBurst = 0.0D;
      this.delayGui = 0.0D;
      this.zoomState = 0;
      this.secondaryFire = false;
      this.animationWhenReloaded = GunAnimationReload.class;
      this.animationWhenInspecting = GunAnimationInspect.class;
      this.animationWhenFiremodeChanged = GunAnimationSwitch.class;
      this.delayFire = (double)((int)(60.0D / givenFireRate * 40.0D));
      this.delayReload = (double)((int)(givenReloadTime * 40.0D));
      this.gunRecoil = givenRecoil;
      this.gunid = id;
      this.setMaxStackSize(1);
   }

   public static int getLoadedAmmo(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      if (!tag.hasKey("gunAmmoLoaded")) {
         tag.setInteger("gunAmmoLoaded", ((ItemGun)par1ItemStack.getItem()).clipSize);
      }

      return tag.getInteger("gunAmmoLoaded");
   }

   public static void setLoadedAmmo(ItemStack par1ItemStack, int par2) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setInteger("gunAmmoLoaded", par2);
   }

   public static boolean hasLoadedAmmo(ItemStack par1ItemStack) {
      return getLoadedAmmo(par1ItemStack) > 0;
   }

   public static int getAmmo(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      if (!tag.hasKey("gunAmmo")) {
         tag.setInteger("gunAmmo", ((ItemGun)par1ItemStack.getItem()).gunMaxAmmo);
      }

      return tag.getInteger("gunAmmo");
   }

   public static void setAmmo(ItemStack par1ItemStack, int par2) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setInteger("gunAmmo", par2);
   }

   public static boolean hasAmmo(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      return tag.getInteger("gunAmmo") > 0;
   }

   public static void setAmmoMax(ItemStack par1ItemStack) {
      setLoadedAmmo(par1ItemStack, ((ItemGun)par1ItemStack.getItem()).clipSize);
      setAmmo(par1ItemStack, ((ItemGun)par1ItemStack.getItem()).gunMaxAmmo);
   }

   public static void cancelReloadingTick(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setDouble("gunReloadingTick", 0.0D);
   }

   public static void setReloadingTick(ItemStack par1ItemStack, double par1) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setDouble("gunReloadingTick", par1);
   }

   public static void setReloading(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setDouble("gunReloadingTick", ((ItemGun)par1ItemStack.getItem()).delayReload);
   }

   public static boolean isReloading(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      return tag.getDouble("gunReloadingTick") > 0.0D;
   }

   public static double getReloadingTick(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      return tag.getDouble("gunReloadingTick");
   }

   public static boolean isSecondaryFire(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      return tag.getBoolean("secondaryFire");
   }

   public static void switchSecondary(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setBoolean("secondaryFire", !tag.getBoolean("secondaryFire"));
   }

   public static void cancelSwitchTick(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setDouble("gunSwitchingTick", 0.0D);
   }

   public static boolean isSwitching(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      return tag.getDouble("gunSwitchingTick") > 0.0D;
   }

   public static double getSwitchingTick(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      return tag.getDouble("gunSwitchingTick");
   }

   public static void setSwitchingTick(ItemStack par1ItemStack, double par1) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setDouble("gunSwitchingTick", par1);
   }

   public static void setSwitching(ItemStack par1ItemStack) {
      NBTTagCompound tag = getItemGunNBTData(par1ItemStack);
      tag.setDouble("gunSwitchingTick", ((ItemGun)par1ItemStack.getItem()).delaySwitch);
   }

   public static void loadItemStackSkin(ItemStack itemstack, EntityPlayer player) {
      if (getGunOwner(itemstack) == null) {
         setGunOwner(itemstack, player.username);
      }

      if (getGunSkin(itemstack) == null && player.username.equalsIgnoreCase(getGunOwner(itemstack))) {
         PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(player.username);
         String texture = "none";
         String sticker1Name = "none";
         String sticker2Name = "none";
         String sticker3Name = "none";
         if (cloud != null && cloud.getItemDefault(itemstack.itemID) != null) {
            CloudItemStack stack = cloud.getItemDefault(itemstack.itemID);
            texture = stack.getCloudItem().getSuffix();
            sticker1Name = CloudItemStack.getSticker0(stack);
            sticker2Name = CloudItemStack.getSticker1(stack);
            sticker3Name = CloudItemStack.getSticker2(stack);
            if (CloudItemStack.hasNameTag(stack)) {
               setGunNameTag(itemstack, CloudItemStack.getNameTag(stack));
            }

            setGunStickerInPosition(itemstack, sticker1Name, 0);
            setGunStickerInPosition(itemstack, sticker2Name, 1);
            setGunStickerInPosition(itemstack, sticker3Name, 2);
         }
         if (CounterCraft.instance.SKINS.containsKey(player)) {
        	 if (CounterCraft.instance.sponsor.containsKey(player.username.toLowerCase())) {
        		 String[] sk = CounterCraft.instance.SKINS.get(player);
        		 String[] ski = new String[8];
        		 String skin = "";
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.M4-A4")) ski = sk[0].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.AK-47")) ski = sk[1].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Famas")) ski = sk[2].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.FN-FAL")) ski = sk[3].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Galil-AR")) ski = sk[4].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.M1911")) ski = sk[5].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.G18")) ski = sk[6].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Deagle")) ski = sk[7].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.P250")) ski = sk[8].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.TEC-9")) ski = sk[9].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.CZ75")) ski = sk[10].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Five-Seven")) ski = sk[11].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.R8Revolver")) ski = sk[12].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.P90")) ski = sk[13].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.MAC-10")) ski = sk[14].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Vector")) ski = sk[15].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.UMP-45")) ski = sk[16].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.AWP")) ski = sk[17].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Dragunov")) ski = sk[18].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Scar-20")) ski = sk[19].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.SSG-08")) ski = sk[20].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Nova")) ski = sk[21].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Sawed-off")) ski = sk[22].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Mag-7")) ski = sk[23].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.M249")) ski = sk[24].split(":");
            	 try {
            		 if (ski[1] != null) texture = ski[1];
            	 } catch (Exception e) {}
        	 }
         }
         
         if (CounterCraft.instance.STICKERS.containsKey(player)) {
        	 if (CounterCraft.instance.sponsor.containsKey(player.username.toLowerCase())) {
        		 String[] st = CounterCraft.instance.STICKERS.get(player);
        		 String[] sti = new String[8];
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.M4-A4")) sti = st[0].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.AK-47")) sti = st[1].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Famas")) sti = st[2].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.FN-FAL")) sti = st[3].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Galil-AR")) sti = st[4].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.M1911")) sti = st[5].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.G18")) sti = st[6].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Deagle")) sti = st[7].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.P250")) sti = st[8].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.TEC-9")) sti = st[9].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.CZ75")) sti = st[10].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Five-Seven")) sti = st[11].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.R8Revolver")) sti = st[12].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.P90")) sti = st[13].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.MAC-10")) sti = st[14].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Vector")) sti = st[15].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.UMP-45")) sti = st[16].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.AWP")) sti = st[17].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Dragunov")) sti = st[18].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Scar-20")) sti = st[19].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.SSG-08")) sti = st[20].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Nova")) sti = st[21].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Sawed-off")) sti = st[22].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.Mag-7")) sti = st[23].split(":");
            	 if (itemstack.getUnlocalizedName().equalsIgnoreCase("item.M249")) sti = st[24].split(":");
            	 try {
            		 if (sti[1].contains(",")) {
            			 String[] stic = sti[1].split(",");
                		 sticker1Name = "sticker" + stic[0];
                		 sticker2Name = "sticker" + stic[1];
                		 sticker3Name = "sticker" + stic[2];
                		 if (!sticker1Name.equalsIgnoreCase("sticker")) setGunStickerInPosition(itemstack, sticker1Name, 0);
                		 if (!sticker2Name.equalsIgnoreCase("sticker"))setGunStickerInPosition(itemstack, sticker2Name, 1);
                		 if (!sticker3Name.equalsIgnoreCase("sticker"))setGunStickerInPosition(itemstack, sticker3Name, 2);
            		 }
            	 } catch (Exception e) {}
        	 }
         }
         setGunSkin(itemstack, texture);
      }
   }

   public static void setGunSkin(ItemStack itemstack, String skin) {
      NBTTagCompound tag = getItemGunNBTData(itemstack);
      if (tag != null && !skin.equals("none")) {
         tag.setString("skin", skin);
      }
   }

   public static String getGunSkin(ItemStack itemstack) {
      NBTTagCompound tag = getItemGunNBTData(itemstack);
      return tag != null && tag.hasKey("skin") ? tag.getString("skin") : null;
   }

   public static void setGunStickerInPosition(ItemStack itemstack, String stickerName, int givenPosition) {
      NBTTagCompound tag = getItemGunNBTData(itemstack);
      if (tag != null && stickerName != null && !stickerName.equals("none")) {
         tag.setString("sticker" + givenPosition, stickerName);
      }

   }

   public static String getStickerInPosition(ItemStack itemstack, int givenPosition) {
      NBTTagCompound tag = getItemGunNBTData(itemstack);
      return tag != null && tag.hasKey("sticker" + givenPosition) ? tag.getString("sticker" + givenPosition) : null;
   }

   public static void setGunOwner(ItemStack par1, String par2) {
      NBTTagCompound tag = getItemGunNBTData(par1);
      if (tag != null && par2 != null && par2.length() > 0) {
         tag.setString("gunSkinOwner", par2);
      }

   }

   public static String getGunOwner(ItemStack par1) {
      NBTTagCompound tag = getItemGunNBTData(par1);
      return tag.hasKey("gunSkinOwner") ? tag.getString("gunSkinOwner") : null;
   }

   public static void setGunNameTag(ItemStack itemstack, String name) {
      NBTTagCompound tag = getItemGunNBTData(itemstack);
      if (tag != null && name != null && name.length() > 0 && !name.equals("none")) {
         tag.setString("nametag", name);
      }

   }

   public static String getGunNameTag(ItemStack itemstack) {
      NBTTagCompound tag = getItemGunNBTData(itemstack);
      return tag != null && tag.hasKey("nametag") ? tag.getString("nametag") : null;
   }

   private static NBTTagCompound getItemGunNBTData(ItemStack itemstack) {
      String var1 = "ccgun";
      if (itemstack.stackTagCompound == null) {
         itemstack.setTagCompound(new NBTTagCompound());
      }

      if (!itemstack.stackTagCompound.hasKey(var1)) {
         itemstack.stackTagCompound.setTag(var1, new NBTTagCompound(var1));
      }

      return (NBTTagCompound)itemstack.stackTagCompound.getTag(var1);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      return par1ItemStack;
   }

   public synchronized void onClientTick(World world, EntityPlayer player, ItemStack itemstack) {
      Minecraft mc = FMLClientHandler.instance().getClient();
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      if (!data.isSpectating()) {
         if (mc.currentScreen != null) {
            this.delayGui = 5.0D;
         }

         if (itemstack != null && itemstack.getItem() instanceof ItemGun && mc.currentScreen == null) {
            lastTriggerHeld = triggerHeld;
            triggerHeld = Mouse.isButtonDown(Mouse.getButtonIndex("BUTTON0"));
            lastReloadKey = reloadKey;
            reloadKey = Keyboard.isKeyDown(19);
            lastSwitchKey = switchKey;
            switchKey = Mouse.isButtonDown(1);
            lastInspectKey = inspectKey;
            inspectKey = Keyboard.isKeyDown(33);
            lastAimKey = aimKey;
            aimKey = Mouse.isButtonDown(Mouse.getButtonIndex("BUTTON1"));
            boolean reloading = isReloading(itemstack);
            boolean switching = isSwitching(itemstack);
            if (data.isFrozen) {
               triggerHeld = false;
               reloadKey = false;
               aimKey = false;
               data.isAiming = false;
            }

            if (this.delayGui-- > 0.0D) {
               return;
            }

            if (this.isAimAloud) {
               if (aimKey && !lastAimKey) {
                  if (this.zoomOutOnFire) {
                     if (boltBackDelay <= 0) {
                        data.isAiming = !data.isAiming;
                     }
                  } else {
                     if (itemstack.getItem().equals(ItemManager.awp) || itemstack.getItem().equals(ItemManager.ssg08)) {
                        ItemGun gun = (ItemGun)itemstack.getItem();
                        ++this.zoomState;
                        if (this.zoomState == 1) {
                           data.isAiming = true;
                           gun.zoomLevel = 5.0F;
                        } else if (this.zoomState == 2) {
                           gun.zoomLevel = 5.0F;
                        } else {
                           data.isAiming = false;
                           gun.zoomLevel = 1.0F;
                           this.zoomState = 0;
                        }

                        return;
                     }

                     data.isAiming = !data.isAiming;
                  }
               }
            } else {
               data.isAiming = false;
            }

            if (reloadKey && !lastReloadKey && this.delayReloadClient <= 0.0D) {
               data.isAiming = false;
               if (!reloading && !switching) {
                  this.onClientReload(player, itemstack);
               }

               return;
            }

            if (this.hasSecondary && switchKey && !lastSwitchKey && this.delaySwitchClient <= 0.0D) {
               data.isAiming = false;
               if (!switching && !reloading && this.hasSecondary) {
                  this.onClientSwitchFiremode(player, itemstack);
               }

               return;
            }

            if (inspectKey && !lastInspectKey && AnimationManager.instance().getCurrentAnimation() == null && this.animationWhenInspecting != null) {
               GunAnimation anim = this.getAnimationWhenInspecting();
               AnimationManager.instance().cancelAllAnimations();
               AnimationManager.instance().setNextGunAnimation(anim);
               return;
            }

            if (this.tickFire < this.delayFire) {
               ++this.tickFire;
            }

            if (hasLoadedAmmo(itemstack) && !reloading && !switching && this.delayReloadClient <= 0.0D) {
               if (this.firemode == EnumFiremode.AUTO) {
                  if (triggerHeld && this.tickFire >= this.delayFire) {
                     this.onClientTriggerPulled(player, itemstack);
                     this.tickFire = 0.0D;
                  }
               } else if (this.firemode == EnumFiremode.SEMI) {
                  if (this.tickFire >= this.delayFire && triggerHeld && !lastTriggerHeld) {
                     this.onClientTriggerPulled(player, itemstack);
                     this.tickFire = 0.0D;
                  }
               } else if (this.firemode == EnumFiremode.BURST) {
                  if (this.tickFire >= this.delayFire && triggerHeld && !lastTriggerHeld && this.delayBurst <= 0.0D) {
                     this.delayBurst = 9.0D;
                  }

                  if (this.delayBurst > 0.0D) {
                     if (this.delayBurst % 3.0D == 0.0D) {
                        this.onClientTriggerPulled(player, itemstack);
                     }

                     --this.delayBurst;
                  }
               }
            } else {
               if (triggerHeld && !hasLoadedAmmo(itemstack) && getAmmo(itemstack) > 0) {
                  data.isAiming = false;
                  this.onClientReload(player, itemstack);
               }

               if (triggerHeld && !lastTriggerHeld) {
                  Minecraft.getMinecraft().sndManager.playSound("countercraft:" + this.soundEmpty, (float)player.posX, (float)player.posY + 0.5F, (float)player.posZ, 1.0F, 1.0F);
               }
            }
         }

      }
   }

   public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
      EntityPlayer player = (EntityPlayer)entity;
      if (this.delayReloadClient > 0.0D) {
         --this.delayReloadClient;
      }

      if (this.delaySwitchClient > 0.0D) {
         --this.delaySwitchClient;
      }

      if (player != null && world.isRemote) {
         PlayerData data = PlayerDataHandler.getPlayerData(player);
         if (player.getCurrentEquippedItem() == null) {
            boltBackDelay = 0;
            lastIsAiming = false;
            this.zoomState = 0;
         } else if (player.getCurrentEquippedItem() == itemstack) {
            if (this.zoomOutOnFire) {
               if (boltBackDelay > 0) {
                  --boltBackDelay;
               }

               if (boltBackDelay == 0 && lastIsAiming) {
                  data.isAiming = true;
                  lastIsAiming = false;
               }
            } else if (!this.zoomOutOnFire) {
               boltBackDelay = 0;
               lastIsAiming = false;
            }
         } else {
            lastIsAiming = true;
            this.zoomState = 0;
         }
      }

      if (!world.isRemote) {
         double var1;
         if (isReloading(itemstack)) {
            var1 = getReloadingTick(itemstack);
            --var1;
            setReloadingTick(itemstack, var1 < 0.0D ? 0.0D : var1);
            if (getReloadingTick(itemstack) == 1.0D) {
               this.onReloadFinished(player, world, itemstack);
            }

            if (player.getCurrentEquippedItem() != itemstack) {
               cancelReloadingTick(itemstack);
            }
         }

         if (isSwitching(itemstack)) {
            var1 = getSwitchingTick(itemstack);
            --var1;
            setSwitchingTick(itemstack, var1 < 0.0D ? 0.0D : var1);
            if (getSwitchingTick(itemstack) == 1.0D) {
               this.onSwitchFinished(player, world, itemstack);
            }

            if (player.getCurrentEquippedItem() != itemstack) {
               cancelSwitchTick(itemstack);
            }
         }
      }

      if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == itemstack.itemID) {
         if (!world.isRemote && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            loadItemStackSkin(itemstack, player);
            return;
         }

         if (!world.isRemote && FMLCommonHandler.instance().getSide() == Side.SERVER) {
            loadItemStackSkin(itemstack, player);
         }
      }

   }

   public void onClientTriggerPulled(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
      String sound = "countercraft:" + (isSecondaryFire(par2ItemStack) ? this.soundFireSuppressed : this.soundFire);
      float var10002 = (float)par1EntityPlayer.posX;
      float var10003 = (float)par1EntityPlayer.posY + 0.5F;
      float var10004 = (float)par1EntityPlayer.posZ;
      Minecraft.getMinecraft().sndManager.playSound(sound, var10002, var10003, var10004, 1.0F, 1.0F);
      PacketDispatcher.sendPacketToServer(CCPacketGunTrigger.buildPacket());
      if ((double)getLoadedAmmo(par2ItemStack) <= (double)this.clipSize * 0.25D) {
         Minecraft.getMinecraft().sndManager.playSound("countercraft:" + this.soundEmpty, (float)par1EntityPlayer.posX, (float)par1EntityPlayer.posY + 0.5F, (float)par1EntityPlayer.posZ, 1.0F, 1.2F);
      }

      ClientTickHandler.getInstance().applyRecoil(this.gunRecoil);
      PlayerData data = PlayerDataHandler.getPlayerData(par1EntityPlayer);
      Random rand = new Random();
      double deltaX1 = (double)(-MathHelper.sin(par1EntityPlayer.rotationYaw / 180.0F * 3.1415927F));
      double deltaZ1 = (double)MathHelper.cos(par1EntityPlayer.rotationYaw / 180.0F * 3.1415927F);
      if (FMLCommonHandler.instance().getSide().isClient()) {
         ParticleEffects.spawnParticle("MuzzleSmoke", par1EntityPlayer.posX + deltaX1, par1EntityPlayer.posY + (double)par1EntityPlayer.eyeHeight - 0.5D, par1EntityPlayer.posZ + deltaZ1, (double)((rand.nextFloat() - 0.5F) * 3.0F), 0.0D, (double)((rand.nextFloat() - 0.5F) * 3.0F));
      }

      if (this.firstBulletSpread) {
         data.addSpread(this.gunSpreadIncreaseFired);
      }

      if (ClientTickHandler.getInstance() != null) {
         ClientTickHandler.muzzleTick = 1.0D;

         for(int i = 0; i < this.bulletsFired; ++i) {
            BulletSpawner spawner = new BulletSpawner();
            spawner.spawnBullet(par1EntityPlayer, this, isSecondaryFire(par2ItemStack));
         }
      }

      if (!this.firstBulletSpread) {
         data.addSpread(this.gunSpreadIncreaseFired);
      }

      if (this.zoomOutOnFire) {
         lastIsAiming = data.isAiming;
         data.isAiming = false;
         boltBackDelay = 15;
      }

      if (this.animationWhenFired != null) {
         GunAnimation anim = this.getAnimationWhenFired();
         AnimationManager.instance().cancelAllAnimations();
         AnimationManager.instance().setNextGunAnimation(anim);
      }

   }

   public void onClientSwitchFiremode(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
      if (this.delaySwitchClient <= 0.0D && !isSwitching(par2ItemStack)) {
         PacketDispatcher.sendPacketToServer(CCPacketGunSwitch.buildPacket());
         this.delaySwitchClient = 5.0D;
         if (this.animationWhenFiremodeChanged != null) {
            GunAnimation anim = this.getAnimationWhenFiremodeChanged();
            AnimationManager.instance().cancelAllAnimations();
            AnimationManager.instance().setNextGunAnimation(anim);
         }
      }

   }

   public void onClientReload(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
      if (getLoadedAmmo(par2ItemStack) != this.clipSize && this.delayReloadClient <= 0.0D && !isReloading(par2ItemStack)) {
         PacketDispatcher.sendPacketToServer(CCPacketGunReload.buildPacket());
         this.delayReloadClient = 5.0D;
         if (this.animationWhenReloaded != null) {
            GunAnimation anim = this.getAnimationWhenReloading();
            AnimationManager.instance().cancelAllAnimations();
            AnimationManager.instance().setNextGunAnimation(anim);
         }
      }

   }

   public void onTriggerPulled(ItemStack itemstack, World world, EntityPlayer player) {
      if (hasLoadedAmmo(itemstack) && !player.isDead && player.getHealth() > 0.0F) {
         ItemStack itemstack1 = this.tryToShoot(itemstack, world, player);
         player.inventory.setInventorySlotContents(player.inventory.currentItem, itemstack1);
      }

   }

   public ItemStack tryToShoot(ItemStack itemstack, World world, EntityPlayer player) {
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      if (!isSecondaryFire(itemstack)) {
         data.muzzleTimer = 2;
         data.seenTimer = 20;
      }

      Random rand = new Random();
      double deltaX1 = (double)(-MathHelper.sin(player.rotationYaw / 180.0F * 3.1415927F));
      double deltaZ1 = (double)MathHelper.cos(player.rotationYaw / 180.0F * 3.1415927F);
      if (FMLCommonHandler.instance().getSide().isClient()) {
         ParticleEffects.spawnParticle("MuzzleSmoke", player.posX + deltaX1, player.posY + (double)player.eyeHeight - 0.5D, player.posZ + deltaZ1, (double)((rand.nextFloat() - 0.5F) * 3.0F), 0.0D, (double)((rand.nextFloat() - 0.5F) * 3.0F));
      }

      if (!world.isRemote) {
         int var1 = getLoadedAmmo(itemstack);
         setLoadedAmmo(itemstack, var1 - 1);
         String sound = "countercraft:" + (isSecondaryFire(itemstack) ? this.soundFireSuppressed : this.soundFire);
         (new StringBuilder()).append("countercraft:").append(this.soundDistant).toString();
         if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
            if (ServerManager.instance().isLobby) {
               return itemstack;
            }

            IGame game = GameManager.instance().getPlayerGame(player);
            if (game != null) {
               game.getPlayerEventHandler().onGunFired(player, itemstack);
            }
         }

         Iterator i$ = world.playerEntities.iterator();

         while(i$.hasNext()) {
            Object peep = i$.next();
            if (peep instanceof EntityPlayer) {
               EntityPlayer listener = (EntityPlayer)peep;
               if (!listener.username.equalsIgnoreCase(player.username)) {
                  PacketDispatcher.sendPacketToPlayer(CCPacketGunSound.buildPacket(sound, (int)player.posX, (int)player.posY, (int)player.posZ), (Player)listener);
               }
            }
         }
      }

      return itemstack;
   }

   public void onReloadStart(EntityPlayer player, World world, ItemStack itemstack) {
      if (!world.isRemote && getAmmo(itemstack) > 0 && !isReloading(itemstack) && !isSwitching(itemstack)) {
         setReloading(itemstack);
         String sound = "countercraft:" + this.soundReload;
         world.playSoundAtEntity(player, sound, 1.0F, 1.0F);
      }

   }

   public void onSwitchStart(EntityPlayer player, World world, ItemStack itemstack) {
      if (!world.isRemote && this.hasSecondary && !isSwitching(itemstack) && !isReloading(itemstack)) {
         setSwitching(itemstack);
         String sound = "countercraft:" + this.soundSecondary;
         world.playSoundAtEntity(player, sound, 1.0F, 1.0F);
      }

   }

   public void onSwitchFinished(EntityPlayer player, World world, ItemStack itemstack) {
      switchSecondary(itemstack);
   }

   public void onReloadFinished(EntityPlayer player, World world, ItemStack itemstack) {
      if (getAmmo(itemstack) > 0) {
         int var1 = getAmmo(itemstack);
         int var2 = getLoadedAmmo(itemstack);
         int var3 = var1 + var2;
         if (var3 <= this.clipSize) {
            setLoadedAmmo(itemstack, var3);
            setAmmo(itemstack, 0);
         } else {
            int var4 = var3 - this.clipSize;
            setLoadedAmmo(itemstack, this.clipSize);
            setAmmo(itemstack, var4);
         }
      }

      if (player.capabilities.isCreativeMode) {
         setAmmo(itemstack, this.gunMaxAmmo);
      }

   }

   public ItemGun setReloadDelay(double delay) {
      this.delayReload = delay;
      return this;
   }

   public void consumeClipLoading(EntityPlayer entityplayer, ItemStack itemstack) {
      if (entityplayer != null) {
         InventoryPlayer inv = entityplayer.inventory;

         for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack1 = inv.getStackInSlot(i);
            if (itemstack1 == itemstack) {
               inv.setInventorySlotContents(i, (ItemStack)null);
            }
         }
      }

   }

   public EnumFiremode getFiremode() {
      return this.firemode;
   }

   public ItemGun setFiremode(EnumFiremode par1) {
      this.firemode = par1;
      return this;
   }

   public GunAnimation getAnimationWhenInspecting() {
      try {
         return (GunAnimation)this.animationWhenInspecting.newInstance();
      } catch (InstantiationException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      }

      return null;
   }

   public GunAnimation getAnimationWhenFired() {
      try {
         return (GunAnimation)this.animationWhenFired.newInstance();
      } catch (InstantiationException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      }

      return null;
   }

   public GunAnimationReload getAnimationWhenReloading() {
      try {
         return (GunAnimationReload)this.animationWhenReloaded.newInstance();
      } catch (InstantiationException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      }

      return null;
   }

   public GunAnimationSwitch getAnimationWhenFiremodeChanged() {
      try {
         return (GunAnimationSwitch)this.animationWhenFiremodeChanged.newInstance();
      } catch (InstantiationException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      }

      return null;
   }

   public ItemGun setSoundEffects(String par1, String par2, String par3, String par4) {
      this.soundFire = par1;
      this.soundFireSuppressed = par2;
      this.soundReload = par3;
      this.soundDistant = this.soundFire + "distant";
      this.soundSecondary = par4;
      return this;
   }

   public ItemGun setSecondaryGun() {
      this.isPrimary = false;
      return this;
   }

   public ItemGun setBulletAmountFired(int par1) {
      this.bulletsFired = par1;
      return this;
   }

   public ItemGun setBulletDistance(int par1) {
      this.bulletDistance = par1;
      return this;
   }

   public ItemGun setFirstBulletAffectedBySpray() {
      this.firstBulletSpread = true;
      return this;
   }

   public ItemGun setAimable(float par1) {
      this.zoomLevel = par1;
      this.isAimAloud = true;
      return this;
   }

   public ItemGun setRenderCrosshairs(boolean par1) {
      this.renderCrosshairs = par1;
      return this;
   }

   public ItemGun setAmmunitionClips(int par1, int par2) {
      this.clipSize = par1;
      this.gunMaxAmmo = par1 * par2;
      return this;
   }

   public ItemGun setSightTexture(String par1) {
      this.sightTexture = new ResourceLocation("countercraft:textures/misc/sights/" + par1 + ".png");
      return this;
   }

   public ItemGun setSightTextureBlur(String par1) {
      this.sightTextureBlur = new ResourceLocation("countercraft:textures/misc/sights/" + par1 + ".png");
      return this;
   }

   public ItemGun setDamage(int par1, int par2) {
      this.gunDamage = par1;
      this.gunDamageHead = par2;
      return this;
   }

   public ItemGun setAnimationFired(Class par1) {
      this.animationWhenFired = par1;
      return this;
   }

   public ItemGun setAnimationFiremode(Class par1) {
      this.animationWhenFiremodeChanged = par1;
      return this;
   }

   public ItemGun setAnimationReload(Class par1) {
      this.animationWhenReloaded = par1;
      return this;
   }

   public ItemGun setAnimationInspecting(Class par1) {
      this.animationWhenInspecting = par1;
      return this;
   }

   public ItemGun setSpreadFired(float par1) {
      this.gunSpreadIncreaseFired = par1;
      return this;
   }

   public ItemGun setHasSecondary() {
      this.hasSecondary = true;
      return this;
   }

   public ItemGun setSpreadIncreaseVariables(float par1, float par2, float par3) {
      this.gunSpreadIncreaseWalking = par1;
      this.gunSpreadIncreaseRunning = par2;
      this.gunSpreadIncreaseJumping = par3;
      return this;
   }

   public ItemGun setSpreadDecreaseVariables(float par1, float par2) {
      this.gunSpreadDecrease = par1;
      this.gunSpreadDecreaseShift = par2;
      return this;
   }

   public ItemGun setSpreadMaxIncreaseVariables(float par1, float par2) {
      this.gunSpreadMaxWalking = par1;
      this.gunSpreadMaxRunning = par2;
      return this;
   }

   public ItemGun setZoomOutOnFired() {
      this.zoomOutOnFire = true;
      return this;
   }

   public ItemGun setMovementPenalty(double par1) {
      this.gunMovementPenalty = par1 > 1.0D ? 1.0D : (par1 < 0.0D ? 0.0D : par1);
      return this;
   }

   public String getItemStackDisplayName(ItemStack par1) {
      String gunName = super.displayName;
      String owner;
      if (getGunSkin(par1) != null) {
         owner = getGunSkin(par1);
         gunName = gunName + " " + owner;
      }

      if (getGunNameTag(par1) != null) {
         owner = getGunNameTag(par1);
         gunName = owner;
      }

      if (getGunOwner(par1) != null) {
         owner = getGunOwner(par1);
         gunName = owner + "'s " + gunName;
      }

      if (getGunOwner(par1) != null) {
         PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(getGunOwner(par1));
         if (cloud != null) {
            CloudItemStack stack = cloud.getItemDefault(par1.itemID);
            if (stack != null && stack.getCloudItem() != null && stack.getCloudItem().getValue() != null && stack.getCloudItem().getValue().chatColor != null) {
               gunName = stack.getCloudItem().getValue().chatColor + gunName;
            }
         }
      }
      gunName = gunName.replaceAll("M3-A3", "M4-A4").replaceAll("AK-36", "AK-47").replaceAll("M0800", "M1911").replaceAll("G07", "G18").replaceAll("P149", "P250").replaceAll("TEC-8", "TEC-9").replaceAll("CZ64", "CZ75").replaceAll("R7", "R8").replaceAll("P80", "P90").replaceAll("MAC-00", "MAC-10").replaceAll("UMP-34", "UMP-45").replaceAll("SCAR-10", "SCAR-20").replaceAll("SSG-07", "SSG-08").replaceAll("Mag-6", "MAG-7").replaceAll("M138", "M249");
      
      return EnumChatFormatting.ITALIC + gunName;
   }

   public String getItemDisplayName(ItemStack par1) {
      return FMLCommonHandler.instance().getSide() == Side.CLIENT && Minecraft.getMinecraft().thePlayer == null ? super.displayName : this.getItemStackDisplayName(par1);
   }

   public void getSlotDisplayInformation(ItemStack par1, ArrayList par2List) {
      String var1 = ItemManager.instance().getBuyableManager().getItemSide(par1.getItem());
      if (var1.equals("Red")) {
         var1 = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + var1;
      } else if (var1.equals("Blue")) {
         var1 = EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + var1;
      }

      par2List.add("Команда: " + var1);
      par2List.add("Тип: " + this.getBuyType().toString());
      par2List.add("");
      par2List.add("Урон " + this.gunDamage + " ХШ " + this.gunDamageHead);
      par2List.add("Патроны " + getLoadedAmmo(par1) + "/" + getAmmo(par1));
   }

   public boolean getShareTag() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      String var1;
      if (Keyboard.isKeyDown(19)) {
         var1 = "Патроны " + EnumChatFormatting.BLUE + getLoadedAmmo(par1ItemStack) + "/" + getAmmo(par1ItemStack);
         par3List.add(var1);
         var1 = "Время перезагрузки " + EnumChatFormatting.BLUE + (float)(this.delayReload / 40.0D) + "s";
         par3List.add(var1);
         var1 = "Решим стрельбы " + EnumChatFormatting.BLUE + this.getFiremode().displayName;
         par3List.add(var1);
         var1 = "Урон " + EnumChatFormatting.BLUE + this.gunDamage;
         par3List.add(var1);
         var1 = "ХэдШот " + EnumChatFormatting.BLUE + this.gunDamageHead;
         par3List.add(var1);
         var1 = "Разброс " + EnumChatFormatting.BLUE + this.gunSpreadIncreaseFired;
         par3List.add(var1);
         var1 = "Отдача " + EnumChatFormatting.BLUE + this.gunRecoil;
         par3List.add(var1);
         var1 = "Движение " + EnumChatFormatting.BLUE + (100.0D - 100.0D * this.gunMovementPenalty) + "%";
         par3List.add(var1);
      } else {
         var1 = "Нажмите 'R' для информации!";
         par3List.add(var1);
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

   public boolean isMovementAffected(EntityPlayer par1, ItemStack par2) {
      return true;
   }

   public double getMovementPenalty(ItemStack par1) {
      return this.gunMovementPenalty;
   }

   static {
      lastTriggerHeld = triggerHeld;
      lastReloadKey = !reloadKey;
      lastSwitchKey = !switchKey;
      lastInspectKey = !inspectKey;
      lastAimKey = aimKey;
      lastIsAiming = false;
      boltBackDelay = 0;
   }
}
