package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemDefaultGun extends ItemDefault {
   public HashMap gunDefaults = new HashMap();

   public ItemDefaultGun(String par1) {
      super(par1);
   }

   public String getCloudStackUUID(int par1) {
      return (String)this.gunDefaults.get(par1);
   }

   public void setCloudStackUUID(String par1, int par2) {
      this.gunDefaults.put(par2, par1);
   }

   public void writeToFDS(FDSTagCompound par1) {
      par1.setString("cat", this.getItemCategory());
      par1.setIntegerArrayList("gunids", new ArrayList(this.gunDefaults.keySet()));
      par1.setStringArrayList("gunuuids", new ArrayList(this.gunDefaults.values()));
   }

   public void readFromFDSExtra(FDSTagCompound par1) {
      if (par1.hasTag("gunids")) {
         ArrayList gunids = par1.getIntegerArrayList("gunids");
         ArrayList gunuuids = par1.getStringArrayList("gunuuids");
         this.gunDefaults.clear();

         for(int i = 0; i < gunids.size(); ++i) {
            this.gunDefaults.put(gunids.get(i), gunuuids.get(i));
         }
      }

   }
}
