package com.ferullogaming.countercraft.client.minimap.tasks;

import com.ferullogaming.countercraft.client.minimap.region.MwChunk;
import com.ferullogaming.countercraft.client.minimap.region.RegionManager;

public class SaveChunkTask extends Task {
   private final MwChunk chunk;
   private final RegionManager regionManager;

   public SaveChunkTask(MwChunk chunk, RegionManager regionManager) {
      this.chunk = chunk;
      this.regionManager = regionManager;
   }

   public void run() {
      this.chunk.write(this.regionManager.regionFileCache);
   }

   public void onComplete() {
   }
}
