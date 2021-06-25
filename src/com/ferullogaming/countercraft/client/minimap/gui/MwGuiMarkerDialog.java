package com.ferullogaming.countercraft.client.minimap.gui;

import com.ferullogaming.countercraft.client.minimap.map.Marker;
import com.ferullogaming.countercraft.client.minimap.map.MarkerManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class MwGuiMarkerDialog extends MwGuiTextDialog {
   private final MarkerManager markerManager;
   private Marker editingMarker;
   private String markerName = "name";
   private String markerGroup = "group";
   private int markerX = 0;
   private int markerY = 80;
   private int markerZ = 0;
   private int state = 0;
   private int dimension = 0;

   public MwGuiMarkerDialog(GuiScreen parentScreen, MarkerManager markerManager, String markerName, String markerGroup, int x, int y, int z, int dimension) {
      super(parentScreen, "Marker Name:", markerName, "marker must have a name");
      this.markerManager = markerManager;
      this.markerName = markerName;
      this.markerGroup = markerGroup;
      this.markerX = x;
      this.markerY = y;
      this.markerZ = z;
      this.editingMarker = null;
      this.dimension = dimension;
   }

   public MwGuiMarkerDialog(GuiScreen parentScreen, MarkerManager markerManager, Marker editingMarker) {
      super(parentScreen, "Edit Marker Name:", editingMarker.name, "marker must have a name");
      this.markerManager = markerManager;
      this.editingMarker = editingMarker;
      this.markerName = editingMarker.name;
      this.markerGroup = editingMarker.groupName;
      this.markerX = editingMarker.x;
      this.markerY = editingMarker.y;
      this.markerZ = editingMarker.z;
      this.dimension = editingMarker.dimension;
   }

   public boolean submit() {
      boolean done = false;
      switch(this.state) {
      case 0:
         this.markerName = this.getInputAsString();
         if (super.inputValid) {
            super.title = "Marker Group:";
            this.setText(this.markerGroup);
            super.error = "marker must have a group name";
            ++this.state;
         }
         break;
      case 1:
         this.markerGroup = this.getInputAsString();
         if (super.inputValid) {
            super.title = "Marker X:";
            this.setText("" + this.markerX);
            super.error = "invalid value";
            ++this.state;
         }
         break;
      case 2:
         this.markerX = this.getInputAsInt();
         if (super.inputValid) {
            super.title = "Marker Y:";
            this.setText("" + this.markerY);
            super.error = "invalid value";
            ++this.state;
         }
         break;
      case 3:
         this.markerY = this.getInputAsInt();
         if (super.inputValid) {
            super.title = "Marker Z:";
            this.setText("" + this.markerZ);
            super.error = "invalid value";
            ++this.state;
         }
         break;
      case 4:
         this.markerZ = this.getInputAsInt();
         if (super.inputValid) {
            done = true;
            int colour = Marker.getCurrentColour();
            if (this.editingMarker != null) {
               colour = this.editingMarker.colour;
               this.markerManager.delMarker(this.editingMarker);
               this.editingMarker = null;
            }

            this.markerManager.addMarker(this.markerName, this.markerGroup, this.markerX, this.markerY, this.markerZ, this.dimension, colour, false, false);
            this.markerManager.setVisibleGroupName(this.markerGroup);
            this.markerManager.update();
         }
      }

      return done;
   }
}
