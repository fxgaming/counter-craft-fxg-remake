package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.ClientManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StoreManager {
   private HashMap storeCategories = new HashMap();

   public static StoreManager instance() {
      return ClientManager.instance().getCloudManager().getCloudStoreManager();
   }

   public void readFromFDS(FDSTagCompound par1) {
      int var1 = par1.getInteger("size");

      for(int i = 0; i < var1; ++i) {
         FDSTagCompound tag1 = par1.getTagCompound("cat" + i);
         StoreCategory cat1 = new StoreCategory();
         cat1.readFromFDS(tag1);
         this.registerCategory(cat1);
      }

   }

   public StoreCategory getCategory(int par1) {
      return (StoreCategory)this.storeCategories.get(par1);
   }

   public StoreItem getStoreItemFromCloudID(int par1) {
      Iterator i$ = this.getCategoriesListed().iterator();

      StoreCategory cat;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         cat = (StoreCategory)i$.next();
      } while(!cat.hasItem(par1));

      return cat.getStoreItemFromCloudItemID(par1);
   }

   public boolean isStoreItemFromCloudID(int par1) {
      return this.getStoreItemFromCloudID(par1) != null;
   }

   public ArrayList getCategoriesListed() {
      return new ArrayList(this.storeCategories.values());
   }

   public void registerCategory(StoreCategory par1) {
      this.storeCategories.put(par1.getId(), par1);
   }
}
