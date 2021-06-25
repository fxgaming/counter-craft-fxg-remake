package com.ferullogaming.countercraft.client.minimap.gui;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.map.MapView;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class MwGuiDimensionDialog extends MwGuiTextDialog {
   final Minimap mw;
   final MapView mapView;
   final int dimension;

   public MwGuiDimensionDialog(GuiScreen parentScreen, Minimap mw, MapView mapView, int dimension) {
      super(parentScreen, "Set dimension to:", "" + dimension, "invalid dimension");
      this.mw = mw;
      this.mapView = mapView;
      this.dimension = dimension;
   }

   public boolean submit() {
      boolean done = false;
      int dimension = this.getInputAsInt();
      if (super.inputValid) {
         this.mapView.setDimensionAndAdjustZoom(dimension);
         this.mw.miniMap.view.setDimension(dimension);
         this.mw.addDimension(dimension);
         done = true;
      }

      return done;
   }
}
