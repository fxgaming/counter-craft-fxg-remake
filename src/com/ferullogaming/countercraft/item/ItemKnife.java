package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.anim.AnimationManager;
import com.ferullogaming.countercraft.client.anim.GunAnimation;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.damagesource.DamageSourceMelee;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.inf.INFPlayerHandler;
import com.ferullogaming.countercraft.game.inf.Infected;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class ItemKnife extends ItemCC implements IItemMovementPenalty {
   public static boolean inspectKey;
   public static boolean lastInspectKey;
   String soundWhoosh = "countercraft:knifewhoosh";
   String soundSlash = "countercraft:impact";
   private int weaponDamage = 12;
   private Class animationWhenInspecting;

   public ItemKnife(int par1) {
      super(par1);
      this.setCreativeTab(ItemManager.tabCounterCraft);
      this.setMaxStackSize(1);
   }

   public static void loadItemStackSkin(EntityPlayer par1, int par2, ItemStack par3) {
	   try {
	      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(par1.username);
	      if (cloud.getItemDefault("knife", -1) != null) {
	         CloudItemStack cstack = cloud.getItemDefault("knife", -1);
	         if (cstack.getItemStack().itemID != ItemManager.knifeTactical.itemID) {
	            par1.inventory.setInventorySlotContents(par2, cstack.getItemStack());
	         }
	      }
	      
	      if (!par1.worldObj.isRemote)
	      if (par3.getItem().itemID == ItemManager.knifeTactical.itemID) {
	    	  if (CounterCraft.instance.KNIFE.containsKey(par1)) {
	    		  if (CounterCraft.instance.sponsor.get(par1.username.toLowerCase())) {
	    			  par1.inventory.setInventorySlotContents(par2, new ItemStack(ItemManager.instance().getItemFromName(CounterCraft.instance.KNIFE.get(par1)[0].split(":")[1]), 1));
	    		  }
	    	  }
	      }
	      
	      if (getKnifeSkin(par3) != null) {
	    	  String skin = "none";
	    	  if (CounterCraft.instance.KNIFE.containsKey(par1)) {
	    		  if (CounterCraft.instance.sponsor.get(par1.username.toLowerCase())) {
	    			  skin = CounterCraft.instance.KNIFE.get(par1)[1].split(":")[1];
	    		  }
	    	  }
	    	  setKnifeSkin(par3, skin);
	      }
	   } catch (Exception e) {}
   }
   
   private static NBTTagCompound getItemKnifeNBTData(ItemStack itemstack) {
      String var1 = "ccknife";
      if (itemstack.stackTagCompound == null) {
         itemstack.setTagCompound(new NBTTagCompound());
      }

      if (!itemstack.stackTagCompound.hasKey(var1)) {
         itemstack.stackTagCompound.setTag(var1, new NBTTagCompound(var1));
      }

      return (NBTTagCompound)itemstack.stackTagCompound.getTag(var1);
   }
   
   public static void setKnifeSkin(ItemStack itemstack, String skin) {
	   NBTTagCompound tag = getItemKnifeNBTData(itemstack);
	   if (tag != null && !skin.equals("none")) {
		   tag.setString("skin", skin);
	   }
   }

   public static String getKnifeSkin(ItemStack itemstack) {
      NBTTagCompound tag = getItemKnifeNBTData(itemstack);
      return tag != null && tag.hasKey("skin") ? tag.getString("skin") : "none";
   }


   public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
      EntityPlayer player = (EntityPlayer)entity;
      if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == itemstack.itemID) {
         if (world.isRemote && FMLCommonHandler.instance().getSide().isClient() && Minecraft.getMinecraft().currentScreen == null) {
            PlayerData data = PlayerDataHandler.getPlayerData(player);
            if (data.isSpectating()) {
               return;
            }

            lastInspectKey = inspectKey;
            inspectKey = Keyboard.isKeyDown(33);
            if (inspectKey && !lastInspectKey) {
               GunAnimation anim = this.getAnimationWhenInspecting();
               AnimationManager.instance().cancelAllAnimations();
               AnimationManager.instance().setNextGunAnimation(anim);
            }

            if (player.isSwingInProgress) {
               AnimationManager.instance().cancelAllAnimations();
            }
         }
         loadItemStackSkin(player, player.inventory.currentItem, itemstack);
      }
      
      if (!world.isRemote)
    	  if (getKnifeSkin(itemstack).equalsIgnoreCase("none")) {
    		  loadItemStackSkin(player, player.inventory.currentItem, itemstack);
    	  }
   }

   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
      if (!player.worldObj.isRemote && player != null && entity != null && entity instanceof EntityPlayer) {
         PlayerData playerData = PlayerDataHandler.getPlayerData(player.username);
         if (playerData != null && !playerData.isSpectating()) {
            player.worldObj.playSoundAtEntity(entity, this.soundSlash, 1.0F, 1.0F);
         }

         EntityPlayer killed = (EntityPlayer)entity;
         if (GameManager.instance().isSameGamePresences(player, killed)) {
            IGame game = GameManager.instance().getPlayerGame(player);
            DamageSourceMelee source = new DamageSourceMelee(player, stack);
            return game.getPlayerEventHandler().onPlayerDamaged(killed, source);
         }
      }

      return false;
   }

   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
      entityLiving.worldObj.playSoundAtEntity(entityLiving, this.soundWhoosh, 1.0F, 1.0F);
      return false;
   }

   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
      if (!par3EntityLivingBase.worldObj.isRemote) {
         int damage = this.weaponDamage;
         IGame game = GameManager.instance().getPlayerGame(par3EntityLivingBase.getEntityName());
         if (par2EntityLivingBase instanceof EntityPlayer) {
            PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)par2EntityLivingBase);
            if (playerData.isWearingKevlar()) {
               damage -= damage / 4;
            }
         }

         if (game != null && game instanceof Infected) {
            Infected infected = (Infected)game;
            INFPlayerHandler playerHandler = (INFPlayerHandler)infected.getPlayerEventHandler();
            if (playerHandler.getPlayerTeam(par3EntityLivingBase.getEntityName()).teamName.equals("Dead") && par3EntityLivingBase.isPotionActive(Potion.damageBoost)) {
               damage += 10;
            }
         }

         Vec3 look = par3EntityLivingBase.getLookVec().normalize();
         double knockback = -0.5D;
         par2EntityLivingBase.addVelocity(look.xCoord * knockback, look.yCoord * knockback, look.zCoord * knockback);
         par2EntityLivingBase.attackEntityFrom(new DamageSourceMelee(par3EntityLivingBase, par1ItemStack), (float)damage);
      }

      return true;
   }

   public boolean isFull3D() {
      return true;
   }

   public EnumAction getItemUseAction(ItemStack par1ItemStack) {
      return EnumAction.block;
   }

   public int getMaxItemUseDuration(ItemStack par1ItemStack) {
      return 72000;
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
      return par1ItemStack;
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      String var1;
      if (Keyboard.isKeyDown(19)) {
         var1 = "Урон " + EnumChatFormatting.BLUE + this.weaponDamage;
         par3List.add(var1);
         var1 = "Движение " + EnumChatFormatting.BLUE + 100 + "%";
         par3List.add(var1);
      } else {
         var1 = "Нажмите 'R' для информации!";
         par3List.add(var1);
      }

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

   public ItemKnife setAnimationInspecting(Class par1) {
      this.animationWhenInspecting = par1;
      return this;
   }

   public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
      return true;
   }

   public boolean isMovementAffected(EntityPlayer par1, ItemStack par2) {
      return false;
   }

   public double getMovementPenalty(ItemStack par1) {
      return 0.0D;
   }

   static {
      lastInspectKey = !inspectKey;
   }
}
