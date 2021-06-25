package com.ferullogaming.countercraft.client.cloud.item;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.item.ItemStack;

public class CloudItemStack {
   private String uuid = "";
   private int itemID = 0;
   private FDSTagCompound stackData;

   public CloudItemStack(String par1, int par2) {
      this.uuid = par1;
      this.itemID = par2;
   }

   public static CloudItemStack readFromFDS(FDSTagCompound par1) {
      int var1 = par1.getInteger("itemid");
      String var2 = par1.getString("uuid");
      CloudItemStack stack = new CloudItemStack(var2, var1);
      stack.readDataFromFDS(par1);
      return stack;
   }

   public static boolean hasNameTag(CloudItemStack par1) {
      return getNameTag(par1) != null && getNameTag(par1).length() > 0;
   }

   public static String getNameTag(CloudItemStack par1) {
      if (par1.hasMetaData()) {
         FDSTagCompound tag = par1.getMetaData();
         if (tag.hasTag("nametag")) {
            return tag.getString("nametag");
         }
      }

      return null;
   }

   public static String getSticker0(CloudItemStack par1) {
      if (par1.hasMetaData()) {
         FDSTagCompound tag = par1.getMetaData();
         return tag.hasTag("sticker0") ? tag.getString("sticker0") : "none";
      } else {
         return null;
      }
   }

   public static String getSticker1(CloudItemStack par1) {
      if (par1.hasMetaData()) {
         FDSTagCompound tag = par1.getMetaData();
         return tag.hasTag("sticker1") ? tag.getString("sticker1") : "none";
      } else {
         return null;
      }
   }

   public static String getSticker2(CloudItemStack par1) {
      if (par1.hasMetaData()) {
         FDSTagCompound tag = par1.getMetaData();
         return tag.hasTag("sticker2") ? tag.getString("sticker2") : "none";
      } else {
         return null;
      }
   }

   public static CloudItemStack copyStack(CloudItemStack par1) {
      return par1.copy();
   }

   public String getUUID() {
      return this.uuid;
   }

   public CloudItem getCloudItem() {
      return CloudItem.itemList[this.itemID];
   }

   public int getMCItemID() {
      return this.getCloudItem().getMCItemID();
   }

   public String getDisplayName() {
      String name = this.getCloudItem().getName();
      String suffix = this.getCloudItem().getSuffix();
      return !suffix.equals("none") ? name + " " + suffix : name;
   }

   private void readDataFromFDS(FDSTagCompound par1) {
      if (par1.hasTag("metadata")) {
         this.stackData = par1.getTagCompound("metadata").copy();
      }

   }

   public ItemStack getItemStack() {
      CloudItem item = this.getCloudItem();
      ItemStack stack = new ItemStack(this.getMCItemID(), 1, 0);
      ItemCC.setCCRarity(stack, item.getValue());
      if (stack.getItem() instanceof ItemGun) {
         ItemGun.setGunSkin(stack, this.getCloudItem().getSuffix());
         ItemGun.setGunStickerInPosition(stack, getSticker0(this), 0);
         ItemGun.setGunStickerInPosition(stack, getSticker1(this), 1);
         ItemGun.setGunStickerInPosition(stack, getSticker2(this), 2);
      }

      return stack;
   }

   public boolean hasMetaData() {
      return this.stackData != null;
   }

   public FDSTagCompound getMetaData() {
      return this.stackData;
   }

   public CloudItemStack copy() {
      CloudItemStack stack = new CloudItemStack(this.uuid, this.itemID);
      if (this.stackData != null) {
         stack.stackData = this.stackData.copy();
      }

      return stack;
   }

   public boolean equals(CloudItemStack par1) {
      return this.uuid.equals(par1.uuid);
   }
}
