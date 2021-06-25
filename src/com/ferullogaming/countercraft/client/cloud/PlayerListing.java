package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import java.util.UUID;

public class PlayerListing implements Comparable {
   private String listingUUID;
   private String playerUUID;
   private String playerUsername;
   private CloudItemStack theStack;
   private int thePrice;

   public PlayerListing() {
   }

   public PlayerListing(String par1, String par2, CloudItemStack par3, int par4) {
      this.listingUUID = UUID.randomUUID().toString();
      this.playerUUID = par1;
      this.playerUsername = par2;
      this.theStack = par3;
      this.thePrice = par4;
   }

   public static PlayerListing readFromFDS(FDSTagCompound par1) {
      PlayerListing listing = new PlayerListing();
      listing.readFromFDS2(par1);
      return listing;
   }

   public void readFromFDS2(FDSTagCompound par1) {
      this.listingUUID = par1.getString("uuid");
      this.playerUUID = par1.getString("playeruuid");
      this.playerUsername = par1.getString("playerusername");
      this.theStack = CloudItemStack.readFromFDS(par1.getTagCompound("stack"));
      this.thePrice = par1.getInteger("price");
   }

   public String getUUID() {
      return this.listingUUID;
   }

   public String getPlayerUUID() {
      return this.playerUUID;
   }

   public String getPlayerUsername() {
      return this.playerUsername;
   }

   public CloudItemStack getStack() {
      return this.theStack;
   }

   public int getPrice() {
      return this.thePrice;
   }

   public void setPrice(int par1) {
      this.thePrice = par1;
   }

   public String getCategory() {
      return this.getStack().getCloudItem().getName();
   }

   public String getSubCaterogy() {
      String var1 = this.getStack().getCloudItem().getSuffix();
      return var1.equals("none") ? null : var1;
   }

   public boolean equals(Object par1) {
      return par1 instanceof PlayerListing ? ((PlayerListing)par1).getUUID().equals(this.getUUID()) : false;
   }
   
   public int compareTo(PlayerListing o) {
      return this.thePrice - o.thePrice;
   }
   
   public int compareTo(Object o) {
	  return 0;
	}

   public String toString() {
      return "PlayerListing[uuid=" + this.listingUUID + ",player=" + this.playerUsername + ",price=" + this.thePrice + "e,item=" + this.getStack().getCloudItem().getDisplayName() + "]";
   }

}


