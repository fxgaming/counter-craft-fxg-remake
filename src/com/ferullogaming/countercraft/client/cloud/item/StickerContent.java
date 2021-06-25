package com.ferullogaming.countercraft.client.cloud.item;

public class StickerContent {
   private int cloudItemID;

   public StickerContent(int var1) {
      this.cloudItemID = var1;
   }

   public CloudItem getCloudItem() {
      return CloudItem.itemList[this.cloudItemID];
   }
}
