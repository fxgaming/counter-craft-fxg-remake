package com.ferullogaming.countercraft.client.minimap.tasks;

import com.ferullogaming.countercraft.client.minimap.region.RegionManager;

public class CloseRegionManagerTask extends Task {
   private final RegionManager regionManager;

   public CloseRegionManagerTask(RegionManager regionManager) {
      this.regionManager = regionManager;
   }

   public void run() {
      this.regionManager.close();
   }

   public void onComplete() {
   }
}
