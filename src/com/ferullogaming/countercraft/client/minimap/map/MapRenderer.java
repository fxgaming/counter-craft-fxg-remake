package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.Render;
import com.ferullogaming.countercraft.client.minimap.api.IMwChunkOverlay;
import com.ferullogaming.countercraft.client.minimap.api.IMwDataProvider;
import com.ferullogaming.countercraft.client.minimap.api.MwAPI;
import com.ferullogaming.countercraft.client.minimap.map.mapmode.MapMode;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MapRenderer {
   public Double playerArrowScreenPos = new Double(0.0D, 0.0D);
   private Minimap mw;
   private MapMode mapMode;
   private MapView mapView;
   private ResourceLocation backgroundTexture = new ResourceLocation("countercraft", "textures/gui/minimap/background.png");
   private ResourceLocation roundMapTexture = new ResourceLocation("countercraft", "textures/gui/minimap/border_round.png");
   private ResourceLocation squareMapTexture = new ResourceLocation("countercraft", "textures/gui/minimap/border_square.png");
   private ResourceLocation playerArrowTexture = new ResourceLocation("countercraft", "textures/gui/minimap/arrow_player.png");
   private ResourceLocation northArrowTexture = new ResourceLocation("countercraft", "textures/gui/minimap/arrow_north.png");

   public MapRenderer(Minimap mw, MapMode mapMode, MapView mapView) {
      this.mw = mw;
      this.mapMode = mapMode;
      this.mapView = mapView;
   }

   private static void paintChunk(MapMode mapMode, MapView mapView, IMwChunkOverlay overlay) {
      int chunkX = overlay.getCoordinates().x;
      int chunkZ = overlay.getCoordinates().y;
      float filling = overlay.getFilling();
      Double topCorner = mapMode.blockXZtoScreenXY(mapView, (double)(chunkX << 4), (double)(chunkZ << 4));
      Double botCorner = mapMode.blockXZtoScreenXY(mapView, (double)(chunkX + 1 << 4), (double)(chunkZ + 1 << 4));
      topCorner.x = Math.max((double)mapMode.x, topCorner.x);
      topCorner.x = Math.min((double)(mapMode.x + mapMode.w), topCorner.x);
      topCorner.y = Math.max((double)mapMode.y, topCorner.y);
      topCorner.y = Math.min((double)(mapMode.y + mapMode.h), topCorner.y);
      botCorner.x = Math.max((double)mapMode.x, botCorner.x);
      botCorner.x = Math.min((double)(mapMode.x + mapMode.w), botCorner.x);
      botCorner.y = Math.max((double)mapMode.y, botCorner.y);
      botCorner.y = Math.min((double)(mapMode.y + mapMode.h), botCorner.y);
      double sizeX = (botCorner.x - topCorner.x) * (double)filling;
      double sizeY = (botCorner.y - topCorner.y) * (double)filling;
      double offsetX = (botCorner.x - topCorner.x - sizeX) / 2.0D;
      double offsetY = (botCorner.y - topCorner.y - sizeY) / 2.0D;
      if (overlay.hasBorder()) {
         Render.setColour(overlay.getBorderColor());
         Render.drawRectBorder(topCorner.x + 1.0D, topCorner.y + 1.0D, botCorner.x - topCorner.x - 1.0D, botCorner.y - topCorner.y - 1.0D, (double)overlay.getBorderWidth());
      }

      Render.setColour(overlay.getColor());
      Render.drawRect(topCorner.x + offsetX + 1.0D, topCorner.y + offsetY + 1.0D, sizeX - 1.0D, sizeY - 1.0D);
   }

   private void drawMapTexture() {
      int regionZoomLevel = Math.max(0, this.mapView.getZoomLevel());
      double tSize = (double)this.mw.textureSize;
      double zoomScale = (double)(1 << regionZoomLevel);
      double u = (double)Math.round(this.mapView.getMinX() / zoomScale) / tSize % 1.0D;
      double v = (double)Math.round(this.mapView.getMinZ() / zoomScale) / tSize % 1.0D;
      double w = (double)Math.round(this.mapView.getWidth() / zoomScale) / tSize;
      double h = (double)Math.round(this.mapView.getHeight() / zoomScale) / tSize;
      GL11.glPushMatrix();
      if (this.mapView.getUndergroundMode() && regionZoomLevel == 0) {
         this.mw.undergroundMapTexture.requestView(this.mapView);
         Render.setColourWithAlphaPercent(16777215, this.mapMode.alphaPercent);
         this.mw.undergroundMapTexture.bind();
         Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, u, v, u + w, v + h);
      } else {
         MapViewRequest req = new MapViewRequest(this.mapView);
         this.mw.mapTexture.requestView(req, this.mw.executor, this.mw.regionManager);
         if (this.mw.backgroundTextureMode > 0) {
            double bu1 = 0.0D;
            double bu2 = 1.0D;
            double bv1 = 0.0D;
            double bv2 = 1.0D;
            if (this.mw.backgroundTextureMode == 2) {
               double bSize = tSize / 256.0D;
               bu1 = u * bSize;
               bu2 = (u + w) * bSize;
               bv1 = v * bSize;
               bv2 = (v + h) * bSize;
            }

            this.mw.mc.renderEngine.bindTexture(this.backgroundTexture);
            Render.setColourWithAlphaPercent(16777215, this.mapMode.alphaPercent);
            Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, bu1, bv1, bu2, bv2);
         } else {
            Render.setColourWithAlphaPercent(0, this.mapMode.alphaPercent);
            Render.drawRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h);
         }

         if (this.mw.mapTexture.isLoaded(req)) {
            this.mw.mapTexture.bind();
            Render.setColourWithAlphaPercent(16777215, this.mapMode.alphaPercent);
            Render.drawTexturedRect((double)this.mapMode.x, (double)this.mapMode.y, (double)this.mapMode.w, (double)this.mapMode.h, u, v, u + w, v + h);
         }
      }

      GL11.glPopMatrix();
   }

   private void drawPlayerArrow() {
      GL11.glPushMatrix();
      double scale = this.mapView.getDimensionScaling(this.mw.playerDimension);
      Double p = this.mapMode.getClampedScreenXY(this.mapView, this.mw.playerX * scale, this.mw.playerZ * scale);
      this.playerArrowScreenPos.setLocation(p.x + (double)this.mapMode.xTranslation, p.y + (double)this.mapMode.yTranslation);
      GL11.glTranslated(p.x, p.y, 0.0D);
      GL11.glRotated(-this.mw.mapRotationDegrees, 0.0D, 0.0D, 1.0D);
      double arrowSize = (double)this.mapMode.playerArrowSize;
      Render.setColour(-1);
      this.mw.mc.renderEngine.bindTexture(this.playerArrowTexture);
      Render.drawTexturedRect(-arrowSize, -arrowSize, arrowSize * 2.0D, arrowSize * 2.0D, 0.0D, 0.0D, 1.0D, 1.0D);
      GL11.glPopMatrix();
   }

   private void drawIcons() {
      GL11.glPushMatrix();
      this.mw.markerManager.drawMarkers(this.mapMode, this.mapView);
      GL11.glPopMatrix();
      this.drawPlayerArrow();
   }

   private void drawCoords() {
      if (this.mapMode.coordsEnabled) {
         GL11.glPushMatrix();
         GL11.glTranslatef((float)this.mapMode.textX, (float)this.mapMode.textY, 0.0F);
         if (this.mw.coordsMode != 2) {
            GL11.glScalef(0.5F, 0.5F, 1.0F);
         }

         int offset = 0;
         if (this.mw.coordsMode > 0) {
            Render.drawCentredString(0, 0, this.mapMode.textColour, "%d, %d, %d", this.mw.playerXInt, this.mw.playerYInt, this.mw.playerZInt);
         }

         GL11.glPopMatrix();
      }

   }

   private IMwDataProvider drawOverlay() {
      IMwDataProvider provider = MwAPI.getCurrentDataProvider();
      if (provider != null) {
         ArrayList overlays = provider.getChunksOverlay(this.mapView.getDimension(), this.mapView.getX(), this.mapView.getZ(), this.mapView.getMinX(), this.mapView.getMinZ(), this.mapView.getMaxX(), this.mapView.getMaxZ());
         if (overlays != null) {
            Iterator i$ = overlays.iterator();

            while(i$.hasNext()) {
               IMwChunkOverlay overlay = (IMwChunkOverlay)i$.next();
               paintChunk(this.mapMode, this.mapView, overlay);
            }
         }
      }

      return provider;
   }

   public void draw() {
      this.mapMode.setScreenRes();
      this.mapView.setMapWH(this.mapMode);
      this.mapView.setTextureSize(this.mw.textureSize);
      GL11.glPushMatrix();
      GL11.glLoadIdentity();
      GL11.glTranslated((double)(this.mapMode.xTranslation + 5), (double)(this.mapMode.yTranslation - 5), -2000.0D);
      CCRenderHelper.drawRectWithShadow((double)(this.mapMode.x - 1), (double)(this.mapMode.y - 1), (double)(this.mapMode.w + 2), (double)(this.mapMode.h + 2), "0x000000", 1.0F);
      CCRenderHelper.drawImageTransparent((double)(this.mapMode.x - 1), (double)(this.mapMode.y - 1), this.backgroundTexture, (double)(this.mapMode.w + 2), (double)(this.mapMode.h + 2), 1.0D);
      this.drawMapTexture();
      this.drawIcons();
      IMwDataProvider provider = this.drawOverlay();
      this.drawCoords();
      GL11.glEnable(2929);
      GL11.glPopMatrix();
      if (provider != null) {
         GL11.glPushMatrix();
         provider.onDraw(this.mapView, this.mapMode);
         GL11.glPopMatrix();
      }

   }
}
