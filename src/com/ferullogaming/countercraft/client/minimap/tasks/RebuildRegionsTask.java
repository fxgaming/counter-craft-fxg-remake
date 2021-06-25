package com.ferullogaming.countercraft.client.minimap.tasks;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.MinimapUtils;
import com.ferullogaming.countercraft.client.minimap.region.BlockColours;
import com.ferullogaming.countercraft.client.minimap.region.RegionManager;

public class RebuildRegionsTask extends Task {
   final RegionManager regionManager;
   final BlockColours blockColours;
   final int x;
   final int z;
   final int w;
   final int h;
   final int dimension;
   String msg = "";

   public RebuildRegionsTask(Minimap mw, int x, int z, int w, int h, int dimension) {
      this.regionManager = mw.regionManager;
      this.blockColours = mw.blockColours;
      this.x = x;
      this.z = z;
      this.w = w;
      this.h = h;
      this.dimension = dimension;
   }

   public void run() {
      this.regionManager.blockColours = this.blockColours;
      this.regionManager.rebuildRegions(this.x, this.z, this.w, this.h, this.dimension);
   }

   public void onComplete() {
      MinimapUtils.printBoth("rebuild task complete");
   }
}
