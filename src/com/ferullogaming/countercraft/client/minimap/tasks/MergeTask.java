package com.ferullogaming.countercraft.client.minimap.tasks;

import com.ferullogaming.countercraft.client.minimap.MinimapUtils;
import com.ferullogaming.countercraft.client.minimap.region.MergeToImage;
import com.ferullogaming.countercraft.client.minimap.region.RegionManager;
import java.io.File;

public class MergeTask extends Task {
   final RegionManager regionManager;
   final File outputDir;
   final String basename;
   final int x;
   final int z;
   final int w;
   final int h;
   final int dimension;
   String msg = "";

   public MergeTask(RegionManager regionManager, int x, int z, int w, int h, int dimension, File outputDir, String basename) {
      this.regionManager = regionManager;
      this.x = x;
      this.z = z;
      this.w = w;
      this.h = h;
      this.dimension = dimension;
      this.outputDir = outputDir;
      this.basename = basename;
   }

   public void run() {
      int count = MergeToImage.merge(this.regionManager, this.x, this.z, this.w, this.h, this.dimension, this.outputDir, this.basename);
      if (count > 0) {
         this.msg = String.format("successfully wrote merged images to %s/%s.*.*.png", this.outputDir, this.basename);
      } else {
         this.msg = String.format("merge error: could not write images to %s", this.outputDir);
      }

   }

   public void onComplete() {
      MinimapUtils.printBoth(this.msg);
   }
}
