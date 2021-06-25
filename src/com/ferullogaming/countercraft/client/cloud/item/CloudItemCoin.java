package com.ferullogaming.countercraft.client.cloud.item;

import java.util.ArrayList;

public class CloudItemCoin extends CloudItem {
   public CloudItemCoin(int par1, int par2, String par3) {
      super(par1, par2, par3);
      this.setFilterIT("coin");
   }

   public void addInformation(CloudItemStack par1, ArrayList par2) {
      super.addInformation(par1, par2);
   }
}
