package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.api.MwAPI;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import java.util.List;

public class MapView {
   private int zoomLevel = 1;
   private int dimension = 0;
   private int textureSize = 2048;
   private double x = 0.0D;
   private double z = 0.0D;
   private int mapW = 0;
   private int mapH = 0;
   private int baseW = 1;
   private int baseH = 1;
   private double w = 1.0D;
   private double h = 1.0D;
   private int minZoom;
   private int maxZoom;
   private boolean undergroundMode;

   public MapView(Minimap mw) {
      this.minZoom = mw.minZoom;
      this.maxZoom = mw.maxZoom;
      this.undergroundMode = mw.undergroundMode;
      this.setZoomLevel(0);
      this.setViewCentre(mw.playerX, mw.playerZ);
   }

   public void setViewCentre(double vX, double vZ) {
      this.x = vX;
      this.z = vZ;
      if (MwAPI.getCurrentDataProvider() != null) {
         MwAPI.getCurrentDataProvider().onMapCenterChanged(vX, vZ, this);
      }

   }

   public double getX() {
      return this.x;
   }

   public double getZ() {
      return this.z;
   }

   public double getWidth() {
      return this.w;
   }

   public double getHeight() {
      return this.h;
   }

   public void panView(double relX, double relZ) {
      this.setViewCentre(this.x + relX * this.w, this.z + relZ * this.h);
   }

   public int setZoomLevel(int zoomLevel) {
      int prevZoomLevel = this.zoomLevel;
      if (this.undergroundMode) {
         this.zoomLevel = Math.min(Math.max(this.minZoom, zoomLevel), 0);
      } else {
         this.zoomLevel = Math.min(Math.max(this.minZoom, zoomLevel), this.maxZoom);
      }

      if (prevZoomLevel != this.zoomLevel) {
         this.updateZoom();
      }

      return this.zoomLevel;
   }

   private void updateZoom() {
      if (this.zoomLevel >= 0) {
         this.w = (double)(this.baseW << this.zoomLevel);
         this.h = (double)(this.baseH << this.zoomLevel);
      } else {
         this.w = (double)(this.baseW >> -this.zoomLevel);
         this.h = (double)(this.baseH >> -this.zoomLevel);
      }

      if (MwAPI.getCurrentDataProvider() != null) {
         MwAPI.getCurrentDataProvider().onZoomChanged(this.getZoomLevel(), this);
      }

   }

   public int adjustZoomLevel(int n) {
      return this.setZoomLevel(this.zoomLevel + n);
   }

   public int getZoomLevel() {
      return this.zoomLevel;
   }

   public int getRegionZoomLevel() {
      return Math.max(0, this.zoomLevel);
   }

   public void zoomToPoint(int newZoomLevel, double bX, double bZ) {
      int prevZoomLevel = this.zoomLevel;
      newZoomLevel = this.setZoomLevel(newZoomLevel);
      double zF = Math.pow(2.0D, (double)(newZoomLevel - prevZoomLevel));
      this.setViewCentre(bX - (bX - this.x) * zF, bZ - (bZ - this.z) * zF);
   }

   public void setDimensionAndAdjustZoom(int dimension) {
      int zoomLevelChange = 0;
      if (this.dimension != -1 && dimension == -1) {
         zoomLevelChange = -3;
      } else if (this.dimension == -1 && dimension != -1) {
         zoomLevelChange = 3;
      }

      this.setZoomLevel(this.getZoomLevel() + zoomLevelChange);
      this.setDimension(dimension);
   }

   public void nextDimension(List dimensionList, int n) {
      int i = dimensionList.indexOf(this.dimension);
      i = Math.max(0, i);
      int size = dimensionList.size();
      int dimension = ((Integer)dimensionList.get((i + size + n) % size)).intValue();
      this.setDimensionAndAdjustZoom(dimension);
   }

   public int getDimension() {
      return this.dimension;
   }

   public void setDimension(int dimension) {
      double scale = 1.0D;
      if (dimension != this.dimension) {
         if (this.dimension != -1 && dimension == -1) {
            scale = 0.125D;
         } else if (this.dimension == -1 && dimension != -1) {
            scale = 8.0D;
         }

         this.dimension = dimension;
         this.setViewCentre(this.x * scale, this.z * scale);
      }

      if (MwAPI.getCurrentDataProvider() != null) {
         MwAPI.getCurrentDataProvider().onDimensionChanged(this.dimension, this);
      }

   }

   public void setMapWH(int w, int h) {
      if (this.mapW != w || this.mapH != h) {
         this.mapW = w;
         this.mapH = h;
         this.updateBaseWH();
      }

   }

   public void setMapWH(MapMode mapMode) {
      this.setMapWH(mapMode.wPixels, mapMode.hPixels);
   }

   public double getMinX() {
      return this.x - this.w / 2.0D;
   }

   public double getMaxX() {
      return this.x + this.w / 2.0D;
   }

   public double getMinZ() {
      return this.z - this.h / 2.0D;
   }

   public double getMaxZ() {
      return this.z + this.h / 2.0D;
   }

   public double getDimensionScaling(int playerDimension) {
      double scale;
      if (this.dimension != -1 && playerDimension == -1) {
         scale = 8.0D;
      } else if (this.dimension == -1 && playerDimension != -1) {
         scale = 0.125D;
      } else {
         scale = 1.0D;
      }

      return scale;
   }

   public void setViewCentreScaled(double vX, double vZ, int playerDimension) {
      double scale = this.getDimensionScaling(playerDimension);
      this.setViewCentre(vX * scale, vZ * scale);
   }

   public void setTextureSize(int n) {
      if (this.textureSize != n) {
         this.textureSize = n;
         this.updateBaseWH();
      }

   }

   private void updateBaseWH() {
      int w = this.mapW;
      int h = this.mapH;

      for(int halfTextureSize = this.textureSize / 2; w > halfTextureSize || h > halfTextureSize; h /= 2) {
         w /= 2;
      }

      this.baseW = w;
      this.baseH = h;
      this.updateZoom();
   }

   public int getPixelsPerBlock() {
      return this.mapW / this.baseW;
   }

   public boolean isBlockWithinView(double bX, double bZ) {
      boolean inside = bX > this.getMinX() || bX < this.getMaxX() || bZ > this.getMinZ() || bZ < this.getMaxZ();
      return inside;
   }

   public boolean getUndergroundMode() {
      return this.undergroundMode;
   }

   public void setUndergroundMode(boolean enabled) {
      if (enabled && this.zoomLevel >= 0) {
         this.setZoomLevel(-1);
      }

      this.undergroundMode = enabled;
   }
}
