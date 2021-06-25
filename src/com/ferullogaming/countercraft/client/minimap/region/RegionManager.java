package com.ferullogaming.countercraft.client.minimap.region;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class RegionManager {
   public final File worldDir;
   public final File imageDir;
   public final RegionFileCache regionFileCache;
   private final RegionManager.LruCache regionMap;
   public BlockColours blockColours;
   public int maxZoom;
   public int minZoom;

   public RegionManager(File worldDir, File imageDir, BlockColours blockColours, int minZoom, int maxZoom) {
      this.worldDir = worldDir;
      this.imageDir = imageDir;
      this.blockColours = blockColours;
      this.regionMap = new RegionManager.LruCache();
      this.regionFileCache = new RegionFileCache(worldDir);
      this.minZoom = minZoom;
      this.maxZoom = maxZoom;
   }

   public void close() {
      Iterator i$ = this.regionMap.values().iterator();

      while(i$.hasNext()) {
         Region region = (Region)i$.next();
         if (region != null) {
            region.close();
         }
      }

      this.regionMap.clear();
      this.regionFileCache.close();
   }

   public Region getRegion(int x, int z, int zoomLevel, int dimension) {
      Region region = (Region)this.regionMap.get(Region.getKey(x, z, zoomLevel, dimension));
      if (region == null) {
         region = new Region(this, x, z, zoomLevel, dimension);
         this.regionMap.put(region.key, region);
      }

      return region;
   }

   public void updateChunk(MwChunk chunk) {
      Region region = this.getRegion(chunk.x << 4, chunk.z << 4, 0, chunk.dimension);
      region.updateChunk(chunk);
   }

   public void rebuildRegions(int xStart, int zStart, int w, int h, int dimension) {
      xStart &= -512;
      zStart &= -512;
      w = w + 512 & -512;
      h = h + 512 & -512;

      for(int rX = xStart; rX < xStart + w; rX += 512) {
         for(int rZ = zStart; rZ < zStart + h; rZ += 512) {
            Region region = this.getRegion(rX, rZ, 0, dimension);
            if (this.regionFileCache.regionFileExists(rX, rZ, dimension)) {
               region.clear();

               for(int cz = 0; cz < 32; ++cz) {
                  for(int cx = 0; cx < 32; ++cx) {
                     MwChunk chunk = MwChunk.read((region.x >> 4) + cx, (region.z >> 4) + cz, region.dimension, this.regionFileCache);
                     region.updateChunk(chunk);
                  }
               }
            }

            region.updateZoomLevels();
         }
      }

   }

   class LruCache extends LinkedHashMap {
      private static final long serialVersionUID = 1L;
      private static final int MAX_LOADED_REGIONS = 64;

      public LruCache() {
         super(128, 0.5F, true);
      }

      protected boolean removeEldestEntry(Entry entry) {
         boolean ret = false;
         if (this.size() > 64) {
            Region region = (Region)entry.getValue();
            region.close();
            ret = true;
         }

         return ret;
      }
   }
}
