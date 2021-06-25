package com.ferullogaming.countercraft.client.cloud.item;

import java.util.ArrayList;

public class CloudItemStickerCapsule extends CloudItem {
   public ArrayList content = new ArrayList();
   public ArrayList contentDisplay = new ArrayList();
   public int costToOpen = 150;

   public CloudItemStickerCapsule(int par1, int par2, String par3) {
      super(par1, par2, par3);
      this.setValue(CloudItemRarity.TIER4);
   }

   public void addInformation(CloudItemStack par1, ArrayList par2) {
      super.addInformation(par1, par2);
      par2.add("");
      par2.add("Try your luck, open");
      par2.add("to obtain new stickers!");
   }

   public CloudItemStickerCapsule setCostToOpen(int par1) {
      this.costToOpen = par1;
      return this;
   }

   public CloudItemStickerCapsule addContent(CloudItem par1, int par2Weight) {
      this.contentDisplay.add(new CaseContent(par1.getID()));

      for(int i = 0; i < par2Weight; ++i) {
         this.content.add(new CaseContent(par1.getID()));
      }

      return this;
   }

   public CloudItemStickerCapsule addContent(int par1ID, int par2Weight) {
      this.contentDisplay.add(new CaseContent(par1ID));

      for(int i = 0; i < par2Weight; ++i) {
         this.content.add(new CaseContent(par1ID));
      }

      return this;
   }
}
