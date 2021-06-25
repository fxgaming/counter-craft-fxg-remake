package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MarketCategoryHandler {
   private HashMap listingMapping = new HashMap();
   private int size = 0;
   private int itemID = 0;
   private int lastPriceSold = -1;
   private ArrayList lastItemsSoldFor = new ArrayList();
   private int startingPrice = -1;
   private int suggestedPrice = -1;

   public MarketCategoryHandler(int par1) {
      this.itemID = par1;
   }

   public void readBasicsFromFDS(FDSTagCompound par1) {
      this.itemID = par1.getInteger("listed");
      if (par1.hasTag("lastsold")) {
         this.lastPriceSold = par1.getInteger("lastsold");
      }

      if (par1.hasTag("startingprice")) {
         this.startingPrice = par1.getInteger("startingprice");
      }

      if (par1.hasTag("suggestedprice")) {
         this.suggestedPrice = par1.getInteger("suggestedprice");
      }

      this.size = par1.getInteger("listingSize");
   }

   public void readFromFDS(FDSTagCompound par1) {
      if (par1.hasTag("lastsold")) {
         this.lastPriceSold = par1.getInteger("lastsold");
      }

      if (par1.hasTag("startingprice")) {
         this.startingPrice = par1.getInteger("startingprice");
      }

      if (par1.hasTag("suggestedprice")) {
         this.suggestedPrice = par1.getInteger("suggestedprice");
      }

      int var1 = par1.getInteger("listingSize");
      this.listingMapping.clear();

      for(int i = 0; i < var1; ++i) {
         FDSTagCompound tag = par1.getTagCompound("listing" + i);
         PlayerListing listing = PlayerListing.readFromFDS(tag);
         this.listingMapping.put(listing.getUUID(), listing);
      }

      this.lastItemsSoldFor = par1.getIntegerArrayList("lastsolds");
   }

   public CloudItem getItem() {
      return CloudItem.itemList[this.getItemID()];
   }

   public int getItemID() {
      return this.itemID;
   }

   public int getQuanity() {
      return this.size;
   }

   public int getSuggestedPrice() {
      return this.suggestedPrice;
   }

   public PlayerListing getListing(String par1) {
      return this.listingMapping.containsKey(par1) ? (PlayerListing)this.listingMapping.get(par1) : null;
   }

   public int getLastSoldPrice() {
      return this.lastPriceSold;
   }

   public void setLastSoldPrice(int par1) {
      this.lastPriceSold = par1;
   }

   public int getStartingPrice() {
      return this.startingPrice;
   }

   public ArrayList getListings() {
      ArrayList list = new ArrayList(this.listingMapping.values());
      if (list.size() > 0) {
         try {
            Collections.sort(list);
         } catch (Exception var3) {
            System.out.println("Failed to SORT Listings for '" + this.itemID + "'");
         }
      }

      return list;
   }
}
