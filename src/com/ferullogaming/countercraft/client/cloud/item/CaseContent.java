package com.ferullogaming.countercraft.client.cloud.item;

public class CaseContent {
   private int cloudItemID;

   public CaseContent(int var1) {
      this.cloudItemID = var1;
   }

   public CloudItem getCloudItem() {
      return CloudItem.itemList[this.cloudItemID];
   }
}
