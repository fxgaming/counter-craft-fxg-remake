package com.ferullogaming.countercraft.client.minimap.api;

import com.ferullogaming.countercraft.client.minimap.map.MapView;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import java.util.ArrayList;

public interface IMwDataProvider {
   ArrayList getChunksOverlay(int var1, double var2, double var4, double var6, double var8, double var10, double var12);

   String getStatusString(int var1, int var2, int var3, int var4);

   void onMiddleClick(int var1, int var2, int var3, MapView var4);

   void onDimensionChanged(int var1, MapView var2);

   void onMapCenterChanged(double var1, double var3, MapView var5);

   void onZoomChanged(int var1, MapView var2);

   void onOverlayActivated(MapView var1);

   void onOverlayDeactivated(MapView var1);

   void onDraw(MapView var1, MapMode var2);

   boolean onMouseInput(MapView var1, MapMode var2);
}
