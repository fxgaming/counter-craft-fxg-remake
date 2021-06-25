package com.ferullogaming.countercraft.client.minimap.map.mapmode;

import com.ferullogaming.countercraft.client.minimap.forge.MinimapConfig;

public class LargeMapMode extends MapMode {
   public LargeMapMode(MinimapConfig config) {
      super(config, "largeMap");
      super.heightPercent = -1;
      super.marginTop = 10;
      super.marginBottom = 40;
      super.marginLeft = 40;
      super.marginRight = 40;
      super.playerArrowSize = 5;
      super.markerSize = 5;
      super.coordsEnabled = true;
      this.loadConfig();
   }
}
