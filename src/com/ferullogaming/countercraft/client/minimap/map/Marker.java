package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.minimap.Render;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import java.awt.geom.Point2D.Double;
import net.minecraft.util.EnumChatFormatting;

public class Marker {
   private static int[] colours = new int[]{16711680, 65280, 255, 16776960, 16711935, 65535, 16744448, 8388863};
   private static int colourIndex = 0;
   public final String name;
   public final String groupName;
   public boolean isBombZone = false;
   public boolean isBombPlanted = false;
   public int x;
   public int y;
   public int z;
   public int dimension;
   public int colour;
   public Double screenPos = new Double(0.0D, 0.0D);

   public Marker(String name, String groupName, int x, int y, int z, int dimension, int colour) {
      this.name = name;
      this.x = x;
      this.y = y;
      this.z = z;
      this.dimension = dimension;
      this.colour = colour;
      this.groupName = groupName;
   }

   public static int getCurrentColour() {
      return -16777216 | colours[colourIndex];
   }

   public Marker setBombZone(boolean isBombPlanted) {
      this.isBombZone = true;
      this.isBombPlanted = isBombPlanted;
      return this;
   }

   public String getString() {
      return String.format("%s %s (%d, %d, %d) %d %06x", this.name, this.groupName, this.x, this.y, this.z, this.dimension, this.colour & 16777215);
   }

   public void colourNext() {
      colourIndex = (colourIndex + 1) % colours.length;
      this.colour = getCurrentColour();
   }

   public void colourPrev() {
      colourIndex = (colourIndex + colours.length - 1) % colours.length;
      this.colour = getCurrentColour();
   }

   public void draw(MapMode mapMode, MapView mapView, int borderColour, boolean isBombZone) {
      double scale = mapView.getDimensionScaling(this.dimension);
      Double p = mapMode.getClampedScreenXY(mapView, (double)this.x * scale, (double)this.z * scale);
      this.screenPos.setLocation(p.x + (double)mapMode.xTranslation, p.y + (double)mapMode.yTranslation);
      double mSize = (double)mapMode.markerSize;
      double halfMSize = (double)mapMode.markerSize / 2.0D;
      if (isBombZone) {
         CCRenderHelper.renderCenteredTextScaled((this.isBombPlanted ? EnumChatFormatting.RED : EnumChatFormatting.GOLD) + this.name.toUpperCase(), (int)(p.x - halfMSize + 2.0D), (int)(p.y - halfMSize - 1.0D), 1.0D);
      } else {
         Render.setColour(borderColour);
         Render.drawRect(p.x - halfMSize, p.y - halfMSize, mSize, mSize);
         Render.setColour(this.colour);
         Render.drawRect(p.x - halfMSize + 0.5D, p.y - halfMSize + 0.5D, mSize - 1.0D, mSize - 1.0D);
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Marker)) {
         return false;
      } else {
         Marker m = (Marker)o;
         return this.name == m.name && this.groupName == m.groupName && this.x == m.x && this.y == m.y && this.z == m.z && this.dimension == m.dimension;
      }
   }
}
