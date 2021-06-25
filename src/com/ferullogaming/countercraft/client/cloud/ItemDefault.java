package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;

public class ItemDefault {
   private String itemCategory;
   private String cloudStackUUID;

   public ItemDefault(String par1) {
      this.itemCategory = par1;
   }

   public String getItemCategory() {
      return this.itemCategory;
   }

   public String getCloudStackUUID(int par1) {
      return this.cloudStackUUID;
   }

   public void setCloudStackUUID(String par1, int par2) {
      this.cloudStackUUID = par1;
   }

   public void writeToFDS(FDSTagCompound par1) {
      par1.setString("cat", this.itemCategory);
      par1.setString("uuid", this.cloudStackUUID);
   }

   public void readFromFDSExtra(FDSTagCompound par1) {
      this.cloudStackUUID = par1.getString("uuid");
   }
}
