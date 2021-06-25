package com.ferullogaming.countercraft.client.minimap.tasks;

import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.map.MapTexture;
import com.ferullogaming.countercraft.client.minimap.region.MwChunk;
import com.ferullogaming.countercraft.client.minimap.region.RegionManager;
import com.ferullogaming.countercraft.game.GameManager;

public class UpdateSurfaceChunksTask extends Task {
   MwChunk[] chunkArray;
   RegionManager regionManager;
   MapTexture mapTexture;

   public UpdateSurfaceChunksTask(Minimap mw, MwChunk[] chunkArray) {
      this.mapTexture = mw.mapTexture;
      this.regionManager = mw.regionManager;
      this.chunkArray = chunkArray;
   }

   public void run() {
      if (GameManager.instance().currentClientGame != null && ClientVariables.enableMinimap) {
         MwChunk[] arr$ = this.chunkArray;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            MwChunk chunk = arr$[i$];
            if (chunk != null) {
               this.regionManager.updateChunk(chunk);
               this.mapTexture.updateArea(this.regionManager, chunk.x << 4, chunk.z << 4, 16, 16, chunk.dimension);
            }
         }
      }

   }

   public void onComplete() {
   }
}
