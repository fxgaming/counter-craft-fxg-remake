package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.LargeMapMode;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.SmallMapMode;
import java.util.ArrayList;
import java.util.List;

public class MiniMap {
   public static final String catSmallMap = "smallMap";
   public static final String catLargeMap = "largeMap";
   public static final String catUndergroundMap = "undergroundMap";
   public MapMode smallMapMode;
   public MapMode largeMapMode;
   public MapMode guiMapMode;
   public MapView view;
   public MapRenderer smallMap;
   public MapRenderer largeMap;
   public int modeIndex = 0;
   private Minimap mw;
   private List mapList;
   private MapRenderer currentMap = null;

   public MiniMap(Minimap mw) {
      this.mw = mw;
      this.modeIndex = mw.config.getOrSetInt("options", "overlayModeIndex", this.modeIndex, 0, 1000);
      int zoomLevel = mw.config.getOrSetInt("options", "overlayZoomLevel", 0, mw.minZoom, mw.maxZoom);
      this.view = new MapView(mw);
      this.view.setZoomLevel(zoomLevel);
      this.smallMapMode = new SmallMapMode(this.mw.config);
      this.smallMap = new MapRenderer(mw, this.smallMapMode, this.view);
      this.largeMapMode = new LargeMapMode(this.mw.config);
      this.largeMap = new MapRenderer(mw, this.largeMapMode, this.view);
      this.mapList = new ArrayList();
      if (this.smallMapMode.enabled) {
         this.mapList.add(this.smallMap);
      }

      if (this.largeMapMode.enabled) {
         this.mapList.add(this.largeMap);
      }

      this.mapList.add((Object)null);
      this.nextOverlayMode(0);
      this.currentMap = (MapRenderer)this.mapList.get(this.modeIndex);
   }

   public void close() {
      this.mapList.clear();
      this.currentMap = null;
      this.smallMapMode.close();
      this.largeMapMode.close();
      this.mw.config.setInt("options", "overlayModeIndex", this.modeIndex);
      this.mw.config.setInt("options", "overlayZoomLevel", this.view.getZoomLevel());
   }

   public MapRenderer nextOverlayMode(int increment) {
      int size = this.mapList.size();
      this.modeIndex = (this.modeIndex + size + increment) % size;
      this.currentMap = (MapRenderer)this.mapList.get(this.modeIndex);
      return this.currentMap;
   }

   public void drawCurrentMap() {
      if (this.currentMap != null) {
         this.currentMap.draw();
      }

   }
}
