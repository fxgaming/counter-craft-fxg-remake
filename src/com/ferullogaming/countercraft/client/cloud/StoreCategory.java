package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StoreCategory {
   public HashMap catItems = new HashMap();
   private int id;
   private String name;
   private String description = "";

   public StoreCategory() {
   }

   public StoreCategory(int par1, String par2) {
      this.id = par1;
      this.name = par2;
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.id = par1.getInteger("id");
      this.name = par1.getString("name");
      this.description = par1.getString("desc");
      int var1 = par1.getInteger("size");

      for(int i = 0; i < var1; ++i) {
         StoreItem item = new StoreItem();
         FDSTagCompound tag1 = par1.getTagCompound("item" + i);
         item.readFromFDS(tag1);
         this.catItems.put(item.getID(), item);
      }

   }

   public StoreItem getStoreItemFromCloudItemID(int par1) {
      Iterator i$ = this.getItemsListed().iterator();

      StoreItem item;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         item = (StoreItem)i$.next();
      } while(item.getCloudItemID() != par1);

      return item;
   }

   public boolean hasItem(int par1) {
      return this.getStoreItemFromCloudItemID(par1) != null;
   }

   public void addStoreItem(StoreItem par1) {
      this.catItems.put(par1.getID(), par1);
   }

   public ArrayList getItemsListed() {
      return new ArrayList(this.catItems.values());
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }
}
