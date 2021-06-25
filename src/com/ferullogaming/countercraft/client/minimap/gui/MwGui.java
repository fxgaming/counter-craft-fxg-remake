package com.ferullogaming.countercraft.client.minimap.gui;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.MinimapUtils;
import com.ferullogaming.countercraft.client.minimap.api.IMwDataProvider;
import com.ferullogaming.countercraft.client.minimap.api.MwAPI;
import com.ferullogaming.countercraft.client.minimap.map.MapRenderer;
import com.ferullogaming.countercraft.client.minimap.map.MapView;
import com.ferullogaming.countercraft.client.minimap.map.Marker;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import com.ferullogaming.countercraft.client.minimap.tasks.MergeTask;
import com.ferullogaming.countercraft.client.minimap.tasks.RebuildRegionsTask;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Point;
import java.awt.geom.Point2D.Double;
import java.util.Iterator;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@SideOnly(Side.CLIENT)
public class MwGui extends GuiScreen {
   private static final double PAN_FACTOR = 0.3D;
   private static final int menuY = 5;
   private static final int menuX = 5;
   private Minimap mw;
   private MapMode mapMode;
   private MapView mapView;
   private MapRenderer map;
   private int mouseLeftHeld;
   private int mouseLeftDragStartX;
   private int mouseLeftDragStartY;
   private double viewXStart;
   private double viewZStart;
   private Marker movingMarker;
   private int movingMarkerXStart;
   private int movingMarkerZStart;
   private int mouseBlockX;
   private int mouseBlockY;
   private int mouseBlockZ;
   private int exit;
   private MwGui.Label helpLabel;
   private MwGui.Label optionsLabel;
   private MwGui.Label dimensionLabel;
   private MwGui.Label groupLabel;
   private MwGui.Label overlayLabel;

   public MwGui(Minimap mw) {
      this.mouseLeftHeld = 0;
      this.mouseLeftDragStartX = 0;
      this.mouseLeftDragStartY = 0;
      this.movingMarker = null;
      this.movingMarkerXStart = 0;
      this.movingMarkerZStart = 0;
      this.mouseBlockX = 0;
      this.mouseBlockY = 0;
      this.mouseBlockZ = 0;
      this.exit = 0;
      this.mw = mw;
      this.mapView = new MapView(this.mw);
      this.map = new MapRenderer(this.mw, this.mapMode, this.mapView);
      this.mapView.setDimension(this.mw.miniMap.view.getDimension());
      this.mapView.setViewCentreScaled(this.mw.playerX, this.mw.playerZ, this.mw.playerDimension);
      this.mapView.setZoomLevel(0);
      this.helpLabel = new MwGui.Label();
      this.optionsLabel = new MwGui.Label();
      this.dimensionLabel = new MwGui.Label();
      this.groupLabel = new MwGui.Label();
      this.overlayLabel = new MwGui.Label();
   }

   public MwGui(Minimap mw, int dim, int x, int z) {
      this(mw);
      this.mapView.setDimension(dim);
      this.mapView.setViewCentreScaled((double)x, (double)z, dim);
      this.mapView.setZoomLevel(0);
   }

   public void initGui() {
   }

   protected void actionPerformed(GuiButton button) {
   }

   public void exitGui() {
      this.mapMode.close();
      Keyboard.enableRepeatEvents(false);
      super.mc.displayGuiScreen((GuiScreen)null);
      super.mc.setIngameFocus();
      super.mc.sndManager.resumeAllSounds();
   }

   public Marker getMarkerNearScreenPos(int x, int y) {
      Marker nearMarker = null;
      Iterator i$ = this.mw.markerManager.visibleMarkerList.iterator();

      while(i$.hasNext()) {
         Marker marker = (Marker)i$.next();
         if (marker.screenPos != null && marker.screenPos.distanceSq((double)x, (double)y) < 6.0D) {
            nearMarker = marker;
         }
      }

      return nearMarker;
   }

   public int getHeightAtBlockPos(int bX, int bZ) {
      int bY = 0;
      if (this.mw.mc.theWorld.provider.dimensionId == this.mapView.getDimension()) {
         bY = this.mw.mc.theWorld.getChunkFromBlockCoords(bX, bZ).getHeightValue(bX & 15, bZ & 15);
      }

      return bY;
   }

   public boolean isPlayerNearScreenPos(int x, int y) {
      Double p = this.map.playerArrowScreenPos;
      return p.distanceSq((double)x, (double)y) < 9.0D;
   }

   public void deleteSelectedMarker() {
      if (this.mw.markerManager.selectedMarker != null) {
         this.mw.markerManager.delMarker(this.mw.markerManager.selectedMarker);
         this.mw.markerManager.update();
         this.mw.markerManager.selectedMarker = null;
      }

   }

   public void mergeMapViewToImage() {
      this.mw.chunkManager.saveChunks();
      this.mw.executor.addTask(new MergeTask(this.mw.regionManager, (int)this.mapView.getX(), (int)this.mapView.getZ(), (int)this.mapView.getWidth(), (int)this.mapView.getHeight(), this.mapView.getDimension(), this.mw.worldDir, this.mw.worldDir.getName()));
      MinimapUtils.printBoth("merging to '" + this.mw.worldDir.getAbsolutePath() + "'");
   }

   public void regenerateView() {
      MinimapUtils.printBoth(String.format("regenerating %dx%d blocks starting from (%d, %d)", (int)this.mapView.getWidth(), (int)this.mapView.getHeight(), (int)this.mapView.getMinX(), (int)this.mapView.getMinZ()));
      this.mw.reloadBlockColours();
      this.mw.executor.addTask(new RebuildRegionsTask(this.mw, (int)this.mapView.getMinX(), (int)this.mapView.getMinZ(), (int)this.mapView.getWidth(), (int)this.mapView.getHeight(), this.mapView.getDimension()));
   }

   public void handleMouseInput() {
      if (MwAPI.getCurrentDataProvider() == null || !MwAPI.getCurrentDataProvider().onMouseInput(this.mapView, this.mapMode)) {
         int x = Mouse.getEventX() * super.width / super.mc.displayWidth;
         int y = super.height - Mouse.getEventY() * super.height / super.mc.displayHeight - 1;
         int direction = Mouse.getEventDWheel();
         if (direction != 0) {
            this.mouseDWheelScrolled(x, y, direction);
         }

         super.handleMouseInput();
      }
   }

   protected void mouseClicked(int x, int y, int button) {
      Marker marker = this.getMarkerNearScreenPos(x, y);
      Marker prevMarker = this.mw.markerManager.selectedMarker;
      if (button == 0) {
         if (this.dimensionLabel.posWithin(x, y)) {
            super.mc.displayGuiScreen(new MwGuiDimensionDialog(this, this.mw, this.mapView, this.mapView.getDimension()));
         } else {
            this.mouseLeftHeld = 1;
            this.mouseLeftDragStartX = x;
            this.mouseLeftDragStartY = y;
            this.mw.markerManager.selectedMarker = marker;
            if (marker != null && prevMarker == marker) {
               this.movingMarker = marker;
               this.movingMarkerXStart = marker.x;
               this.movingMarkerZStart = marker.z;
            }
         }
      } else if (button == 1) {
         if (marker != null && prevMarker == marker) {
            super.mc.displayGuiScreen(new MwGuiMarkerDialog(this, this.mw.markerManager, marker));
         } else if (marker == null) {
            String group = this.mw.markerManager.getVisibleGroupName();
            if (group.equals("none")) {
               group = "group";
            }

            int mx;
            int my;
            int mz;
            if (this.isPlayerNearScreenPos(x, y)) {
               mx = this.mw.playerXInt;
               my = this.mw.playerYInt;
               mz = this.mw.playerZInt;
            } else {
               mx = this.mouseBlockX;
               my = this.mouseBlockY > 0 ? this.mouseBlockY : this.mw.defaultTeleportHeight;
               mz = this.mouseBlockZ;
            }

            super.mc.displayGuiScreen(new MwGuiMarkerDialog(this, this.mw.markerManager, "", group, mx, my, mz, this.mapView.getDimension()));
         }
      } else if (button == 2) {
         Point blockPoint = this.mapMode.screenXYtoBlockXZ(this.mapView, x, y);
         IMwDataProvider provider = MwAPI.getCurrentDataProvider();
         if (provider != null) {
            provider.onMiddleClick(this.mapView.getDimension(), blockPoint.x, blockPoint.y, this.mapView);
         }
      }

      this.viewXStart = this.mapView.getX();
      this.viewZStart = this.mapView.getZ();
   }

   protected void mouseMovedOrUp(int x, int y, int button) {
      if (button == 0) {
         this.mouseLeftHeld = 0;
         this.movingMarker = null;
      } else if (button == 1) {
         ;
      }

   }

   public void mouseDWheelScrolled(int x, int y, int direction) {
      Marker marker = this.getMarkerNearScreenPos(x, y);
      if (marker != null && marker == this.mw.markerManager.selectedMarker) {
         if (direction > 0) {
            marker.colourNext();
         } else {
            marker.colourPrev();
         }
      } else {
         int n;
         if (this.dimensionLabel.posWithin(x, y)) {
            n = direction > 0 ? 1 : -1;
            this.mapView.nextDimension(this.mw.dimensionList, n);
         } else if (this.groupLabel.posWithin(x, y)) {
            n = direction > 0 ? 1 : -1;
            this.mw.markerManager.nextGroup(n);
            this.mw.markerManager.update();
         } else if (this.overlayLabel.posWithin(x, y)) {
            n = direction > 0 ? 1 : -1;
            if (MwAPI.getCurrentDataProvider() != null) {
               MwAPI.getCurrentDataProvider().onOverlayDeactivated(this.mapView);
            }

            if (n == 1) {
               MwAPI.setNextProvider();
            } else {
               MwAPI.setPrevProvider();
            }

            if (MwAPI.getCurrentDataProvider() != null) {
               MwAPI.getCurrentDataProvider().onOverlayActivated(this.mapView);
            }
         } else {
            n = direction > 0 ? -1 : 1;
            this.mapView.zoomToPoint(this.mapView.getZoomLevel() + n, (double)this.mouseBlockX, (double)this.mouseBlockZ);
         }
      }

   }

   public void updateScreen() {
      if (this.exit > 0) {
         ++this.exit;
      }

      if (this.exit > 2) {
         this.exitGui();
      }

      super.updateScreen();
   }

   public void drawStatus(int bX, int bY, int bZ) {
      String s;
      if (bY != 0) {
         s = String.format("cursor: (%d, %d, %d)", bX, bY, bZ);
      } else {
         s = String.format("cursor: (%d, ?, %d)", bX, bZ);
      }

      if (super.mc.theWorld != null && !super.mc.theWorld.getChunkFromBlockCoords(bX, bZ).isEmpty()) {
         s = s + String.format(", biome: %s", super.mc.theWorld.getBiomeGenForCoords(bX, bZ).biomeName);
      }

      IMwDataProvider provider = MwAPI.getCurrentDataProvider();
      if (provider != null) {
         s = s + provider.getStatusString(this.mapView.getDimension(), bX, bY, bZ);
      }

      drawRect(10, super.height - 21, super.width - 20, super.height - 6, Integer.MIN_VALUE);
      this.drawCenteredString(super.fontRenderer, s, super.width / 2, super.height - 18, 16777215);
   }

   public void drawHelp() {
      drawRect(10, 20, super.width - 20, super.height - 30, Integer.MIN_VALUE);
      super.fontRenderer.drawSplitString("Keys:\n\n  Space\n  Delete\n  C\n  Home\n  End\n  N\n  T\n  P\n  R\n  U\n\nLeft click drag or arrow keys pan the map.\nMouse wheel or Page Up/Down zooms map.\nRight click map to create a new marker.\nLeft click drag a selected marker to move it.\nMouse wheel over selected marker to cycle colour.\nMouse wheel over dimension or group box to cycle.\n", 15, 24, super.width - 30, 16777215);
      super.fontRenderer.drawSplitString("| Next marker group\n| Delete selected marker\n| Cycle selected marker colour\n| Centre map on player\n| Centre map on selected marker\n| Select next marker\n| Teleport to cursor or selected marker\n| Save PNG of visible map area\n| Regenerate visible map area from region files\n| Underground map mode\n", 75, 42, super.width - 90, 16777215);
   }

   public void drawMouseOverHint(int x, int y, String title, int mX, int mY, int mZ) {
      String desc = String.format("(%d, %d, %d)", mX, mY, mZ);
      int stringW = Math.max(super.fontRenderer.getStringWidth(title), super.fontRenderer.getStringWidth(desc));
      x = Math.min(x, super.width - (stringW + 16));
      y = Math.min(Math.max(10, y), super.height - 14);
      drawRect(x + 8, y - 10, x + stringW + 16, y + 14, Integer.MIN_VALUE);
      this.drawString(super.fontRenderer, title, x + 10, y - 8, 16777215);
      this.drawString(super.fontRenderer, desc, x + 10, y + 4, 13421772);
   }

   public void drawScreen(int mouseX, int mouseY, float f) {
      this.drawDefaultBackground();
      double xOffset = 0.0D;
      double yOffset = 0.0D;
      if (this.mouseLeftHeld > 2) {
         xOffset = (double)(this.mouseLeftDragStartX - mouseX) * this.mapView.getWidth() / (double)this.mapMode.w;
         yOffset = (double)(this.mouseLeftDragStartY - mouseY) * this.mapView.getHeight() / (double)this.mapMode.h;
         if (this.movingMarker != null) {
            double scale = this.mapView.getDimensionScaling(this.movingMarker.dimension);
            this.movingMarker.x = this.movingMarkerXStart - (int)(xOffset / scale);
            this.movingMarker.z = this.movingMarkerZStart - (int)(yOffset / scale);
         } else {
            this.mapView.setViewCentre(this.viewXStart + xOffset, this.viewZStart + yOffset);
         }
      }

      if (this.mouseLeftHeld > 0) {
         ++this.mouseLeftHeld;
      }

      this.map.draw();
      Point p = this.mapMode.screenXYtoBlockXZ(this.mapView, mouseX, mouseY);
      this.mouseBlockX = p.x;
      this.mouseBlockZ = p.y;
      this.mouseBlockY = this.getHeightAtBlockPos(this.mouseBlockX, this.mouseBlockZ);
      Marker marker = this.getMarkerNearScreenPos(mouseX, mouseY);
      if (marker != null) {
         this.drawMouseOverHint(mouseX, mouseY, marker.name, marker.x, marker.y, marker.z);
      }

      if (this.isPlayerNearScreenPos(mouseX, mouseY)) {
         this.drawMouseOverHint(mouseX, mouseY, super.mc.thePlayer.getEntityName(), this.mw.playerXInt, this.mw.playerYInt, this.mw.playerZInt);
      }

      this.drawStatus(this.mouseBlockX, this.mouseBlockY, this.mouseBlockZ);
      this.helpLabel.draw(5, 5, "[help]");
      this.optionsLabel.drawToRightOf(this.helpLabel, "[options]");
      String dimString = String.format("[dimension: %d]", this.mapView.getDimension());
      this.dimensionLabel.drawToRightOf(this.optionsLabel, dimString);
      String groupString = String.format("[group: %s]", this.mw.markerManager.getVisibleGroupName());
      this.groupLabel.drawToRightOf(this.dimensionLabel, groupString);
      String overlayString = String.format("[overlay : %s]", MwAPI.getCurrentProviderName());
      this.overlayLabel.drawToRightOf(this.groupLabel, overlayString);
      if (this.helpLabel.posWithin(mouseX, mouseY)) {
         this.drawHelp();
      }

      super.drawScreen(mouseX, mouseY, f);
   }

   class Label {
      int x = 0;
      int y = 0;
      int w = 1;
      int h = 12;

      public void draw(int x, int y, String s) {
         this.x = x;
         this.y = y;
         this.w = MwGui.access$000(MwGui.this).getStringWidth(s) + 4;
         MwGui.drawRect(this.x, this.y, this.x + this.w, this.y + this.h, Integer.MIN_VALUE);
         MwGui.this.drawString(MwGui.access$100(MwGui.this), s, this.x + 2, this.y + 2, 16777215);
      }

      public void drawToRightOf(MwGui.Label label, String s) {
         this.draw(label.x + label.w + 5, label.y, s);
      }

      public boolean posWithin(int x, int y) {
         return x >= this.x && y >= this.y && x <= this.x + this.w && y <= this.y + this.h;
      }
   }

   public static FontRenderer access$100(MwGui mwGui) {
	return null;
	}

    public static FontRenderer access$000(MwGui mwGui) {
	return null;
}

}
