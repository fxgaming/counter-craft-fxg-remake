package com.ferullogaming.countercraft.client.minimap.overlay;

import com.ferullogaming.countercraft.client.minimap.api.IMwChunkOverlay;
import com.ferullogaming.countercraft.client.minimap.api.IMwDataProvider;
import com.ferullogaming.countercraft.client.minimap.map.MapView;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import java.awt.Point;
import java.util.ArrayList;
import net.minecraft.util.MathHelper;

public class OverlayGrid implements IMwDataProvider {
   public ArrayList getChunksOverlay(int dim, double centerX, double centerZ, double minX, double minZ, double maxX, double maxZ) {
      int minChunkX = (MathHelper.ceiling_double_int(minX) >> 4) - 1;
      int minChunkZ = (MathHelper.ceiling_double_int(minZ) >> 4) - 1;
      int maxChunkX = (MathHelper.ceiling_double_int(maxX) >> 4) + 1;
      int maxChunkZ = (MathHelper.ceiling_double_int(maxZ) >> 4) + 1;
      int cX = (MathHelper.ceiling_double_int(centerX) >> 4) + 1;
      int cZ = (MathHelper.ceiling_double_int(centerZ) >> 4) + 1;
      int limitMinX = Math.max(minChunkX, cX - 100);
      int limitMaxX = Math.min(maxChunkX, cX + 100);
      int limitMinZ = Math.max(minChunkZ, cZ - 100);
      int limitMaxZ = Math.min(maxChunkZ, cZ + 100);
      ArrayList chunks = new ArrayList();

      for(int x = limitMinX; x <= limitMaxX; ++x) {
         for(int z = limitMinZ; z <= limitMaxZ; ++z) {
            chunks.add(new OverlayGrid.ChunkOverlay(x, z));
         }
      }

      return chunks;
   }

   public String getStatusString(int dim, int bX, int bY, int bZ) {
      return "";
   }

   public void onMiddleClick(int dim, int bX, int bZ, MapView mapview) {
   }

   public void onDimensionChanged(int dimension, MapView mapview) {
   }

   public void onMapCenterChanged(double vX, double vZ, MapView mapview) {
   }

   public void onZoomChanged(int level, MapView mapview) {
   }

   public void onOverlayActivated(MapView mapview) {
   }

   public void onOverlayDeactivated(MapView mapview) {
   }

   public void onDraw(MapView mapview, MapMode mapmode) {
   }

   public boolean onMouseInput(MapView mapview, MapMode mapmode) {
      return false;
   }

   public class ChunkOverlay implements IMwChunkOverlay {
      Point coord;

      public ChunkOverlay(int x, int z) {
         this.coord = new Point(x, z);
      }

      public Point getCoordinates() {
         return this.coord;
      }

      public int getColor() {
         return 16777215;
      }

      public float getFilling() {
         return 1.0F;
      }

      public boolean hasBorder() {
         return true;
      }

      public float getBorderWidth() {
         return 0.5F;
      }

      public int getBorderColor() {
         return -16777216;
      }
   }
}
