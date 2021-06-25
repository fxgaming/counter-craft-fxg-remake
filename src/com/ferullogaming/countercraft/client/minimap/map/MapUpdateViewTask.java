package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.minimap.region.RegionManager;
import com.ferullogaming.countercraft.client.minimap.tasks.Task;

public class MapUpdateViewTask extends Task {
   final MapViewRequest req;
   RegionManager regionManager;
   MapTexture mapTexture;

   public MapUpdateViewTask(MapTexture mapTexture, RegionManager regionManager, MapViewRequest req) {
      this.mapTexture = mapTexture;
      this.regionManager = regionManager;
      this.req = req;
   }

   public void run() {
      this.mapTexture.loadRegions(this.regionManager, this.req);
   }

   public void onComplete() {
      this.mapTexture.setLoaded(this.req);
   }
}
