package com.ferullogaming.countercraft.client.minimap.region;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class RegionFileCache {
   private RegionFileCache.LruCache regionFileCache = new RegionFileCache.LruCache();
   private File worldDir;

   public RegionFileCache(File worldDir) {
      this.worldDir = worldDir;
   }

   public void close() {
      Iterator i$ = this.regionFileCache.values().iterator();

      while(i$.hasNext()) {
         RegionFile regionFile = (RegionFile)i$.next();
         regionFile.close();
      }

      this.regionFileCache.clear();
   }

   public File getRegionFilePath(int x, int z, int dimension) {
      File dir = this.worldDir;
      if (dimension != 0) {
         dir = new File(dir, "DIM" + dimension);
      }

      dir = new File(dir, "region");
      String filename = String.format("r.%d.%d.mca", x >> 9, z >> 9);
      return new File(dir, filename);
   }

   public boolean regionFileExists(int x, int z, int dimension) {
      File regionFilePath = this.getRegionFilePath(x, z, dimension);
      return regionFilePath.isFile();
   }

   public RegionFile getRegionFile(int x, int z, int dimension) {
      File regionFilePath = this.getRegionFilePath(x, z, dimension);
      String key = regionFilePath.toString();
      RegionFile regionFile = (RegionFile)this.regionFileCache.get(key);
      if (regionFile == null) {
         regionFile = new RegionFile(regionFilePath);
         this.regionFileCache.put(key, regionFile);
      }

      return regionFile;
   }

   class LruCache extends LinkedHashMap {
      static final int MAX_REGION_FILES_OPEN = 8;
      private static final long serialVersionUID = 1L;

      public LruCache() {
         super(16, 0.5F, true);
      }

      protected boolean removeEldestEntry(Entry entry) {
         boolean ret = false;
         if (this.size() > 8) {
            RegionFile regionFile = (RegionFile)entry.getValue();
            regionFile.close();
            ret = true;
         }

         return ret;
      }
   }
}
