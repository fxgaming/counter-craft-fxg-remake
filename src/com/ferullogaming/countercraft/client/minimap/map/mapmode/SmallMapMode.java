package com.ferullogaming.countercraft.client.minimap.map.mapmode;

import com.ferullogaming.countercraft.client.minimap.forge.MinimapConfig;

public class SmallMapMode extends MapMode {
   public SmallMapMode(MinimapConfig config) {
      super(config, "smallMap");
      super.heightPercent = 30;
      super.marginTop = 10;
      super.marginBottom = -1;
      super.marginLeft = -1;
      super.marginRight = 10;
      super.playerArrowSize = 4;
      super.markerSize = 3;
      super.coordsEnabled = true;
      this.loadConfig();
   }
}
