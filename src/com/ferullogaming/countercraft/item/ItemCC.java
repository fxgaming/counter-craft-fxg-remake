package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemRarity;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.ArrayList;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemCC extends Item {
   protected String textureName = "";
   protected String displayName = "";
   private EnumBuyType buyableType;

   public ItemCC(int par1) {
      super(par1);
      this.setCreativeTab(ItemManager.tabCounterCraft);
   }

   public static String getCCDisplayName(ItemStack par1) {
      return par1 != null && par1.getItem() instanceof ItemCC ? ((ItemCC)par1.getItem()).getDisplayName(par1) : "";
   }

   public static void setCCRarity(ItemStack itemstack, CloudItemRarity par2) {
      NBTTagCompound tag = getItemCCNBTData(itemstack);
      tag.setInteger("rare", par2.rarityID);
   }

   public static CloudItemRarity getCCRarity(ItemStack itemstack) {
      NBTTagCompound tag = getItemCCNBTData(itemstack);
      return tag.hasKey("rare") ? CloudItemRarity.getEnumFromID(tag.getInteger("rare")) : null;
   }

   private static NBTTagCompound getItemCCNBTData(ItemStack itemstack) {
      String var1 = "ccitem";
      if (itemstack.stackTagCompound == null) {
         itemstack.setTagCompound(new NBTTagCompound());
      }

      if (!itemstack.stackTagCompound.hasKey(var1)) {
         itemstack.stackTagCompound.setTag(var1, new NBTTagCompound(var1));
      }

      return (NBTTagCompound)itemstack.stackTagCompound.getTag(var1);
   }

   public ItemCC setCCItemDisplayName(String par1) {
      this.displayName = par1;
      this.setUnlocalizedName(par1.replace(" ", ""));
      LanguageRegistry.addName(this, par1);
      return this;
   }

   public ItemCC setCCItemTextureName(String par2) {
      this.textureName = par2;
      return this;
   }

   public ItemCC setCCBuyType(EnumBuyType par2) {
      this.buyableType = par2;
      return this;
   }

   public EnumBuyType getBuyType() {
      return this.buyableType;
   }

   public String getDisplayName(ItemStack par1) {
      return this.displayName;
   }

   public String getCCTextureName() {
      return this.textureName;
   }

   public void getSlotDisplayInformation(ItemStack par1, ArrayList par2List) {
   }

   public void registerIcons(IconRegister iconRegister) {
      super.itemIcon = iconRegister.registerIcon("countercraft:" + this.textureName);
   }
}
