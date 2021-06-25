package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;

public class StoreItem {
   private int id;
   private String name;
   private int cloudItemID;
   private int price;
   private String description = "";
   private int maxQuantity = 1;

   public StoreItem() {
   }

   public StoreItem(int par1, String par2, int par3, int par4) {
      this.id = par1;
      this.name = par2;
      this.cloudItemID = par3;
      this.price = par4;
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.id = par1.getInteger("id");
      this.name = par1.getString("name");
      this.cloudItemID = par1.getInteger("cloudid");
      this.price = par1.getInteger("price");
      this.description = par1.getString("desc");
      this.maxQuantity = par1.getInteger("maxq");
   }

   public int getID() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public int getCloudItemID() {
      return this.cloudItemID;
   }

   public CloudItem getCloudItem() {
      return CloudItem.itemList[this.cloudItemID];
   }

   public int getPrice() {
      return this.price;
   }

   public String getDescription() {
      return this.description;
   }

   public int getMaxQuantity() {
      return this.maxQuantity;
   }
}
