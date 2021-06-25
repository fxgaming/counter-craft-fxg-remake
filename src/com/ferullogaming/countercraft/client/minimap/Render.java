package com.ferullogaming.countercraft.client.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class Render {
   public static final double circleSteps = 30.0D;
   public static double zDepth = 0.0D;

   public static void setColourWithAlphaPercent(int colour, int alphaPercent) {
      setColour((alphaPercent * 255 / 100 & 255) << 24 | colour & 16777215);
   }

   public static void setColour(int colour) {
      GL11.glColor4f((float)(colour >> 16 & 255) / 255.0F, (float)(colour >> 8 & 255) / 255.0F, (float)(colour & 255) / 255.0F, (float)(colour >> 24 & 255) / 255.0F);
   }

   public static void resetColour() {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static int multiplyColours(int c1, int c2) {
      float c1A = (float)(c1 >> 24 & 255);
      float c1R = (float)(c1 >> 16 & 255);
      float c1G = (float)(c1 >> 8 & 255);
      float c1B = (float)(c1 >> 0 & 255);
      float c2A = (float)(c2 >> 24 & 255);
      float c2R = (float)(c2 >> 16 & 255);
      float c2G = (float)(c2 >> 8 & 255);
      float c2B = (float)(c2 >> 0 & 255);
      int r = (int)(c1R * c2R / 255.0F) & 255;
      int g = (int)(c1G * c2G / 255.0F) & 255;
      int b = (int)(c1B * c2B / 255.0F) & 255;
      int a = (int)(c1A * c2A / 255.0F) & 255;
      return a << 24 | r << 16 | g << 8 | b;
   }

   public static int getAverageOfPixelQuad(int[] pixels, int offset, int scanSize) {
      int p00 = pixels[offset];
      int p01 = pixels[offset + 1];
      int p10 = pixels[offset + scanSize];
      int p11 = pixels[offset + scanSize + 1];
      int r = (p00 >> 16 & 255) + (p01 >> 16 & 255) + (p10 >> 16 & 255) + (p11 >> 16 & 255);
      r >>= 2;
      int g = (p00 >> 8 & 255) + (p01 >> 8 & 255) + (p10 >> 8 & 255) + (p11 >> 8 & 255);
      g >>= 2;
      int b = (p00 & 255) + (p01 & 255) + (p10 & 255) + (p11 & 255);
      b >>= 2;
      return -16777216 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
   }

   public static int getAverageColourOfArray(int[] pixels) {
      int count = 0;
      double totalA = 0.0D;
      double totalR = 0.0D;
      double totalG = 0.0D;
      double totalB = 0.0D;
      int[] arr$ = pixels;
      int len$ = pixels.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int pixel = arr$[i$];
         double a = (double)(pixel >> 24 & 255);
         double r = (double)(pixel >> 16 & 255);
         double g = (double)(pixel >> 8 & 255);
         double b = (double)(pixel >> 0 & 255);
         totalA += a;
         totalR += r * a / 255.0D;
         totalG += g * a / 255.0D;
         totalB += b * a / 255.0D;
         ++count;
      }

      totalR = totalR * 255.0D / totalA;
      totalG = totalG * 255.0D / totalA;
      totalB = totalB * 255.0D / totalA;
      totalA /= (double)count;
      return ((int)totalA & 255) << 24 | ((int)totalR & 255) << 16 | ((int)totalG & 255) << 8 | (int)totalB & 255;
   }

   public static int adjustPixelBrightness(int colour, int brightness) {
      int r = colour >> 16 & 255;
      int g = colour >> 8 & 255;
      int b = colour >> 0 & 255;
      r = Math.min(Math.max(0, r + brightness), 255);
      g = Math.min(Math.max(0, g + brightness), 255);
      b = Math.min(Math.max(0, b + brightness), 255);
      return colour & -16777216 | r << 16 | g << 8 | b;
   }

   public static int getTextureWidth() {
      return GL11.glGetTexLevelParameteri(3553, 0, 4096);
   }

   public static int getTextureHeight() {
      return GL11.glGetTexLevelParameteri(3553, 0, 4097);
   }

   public static int getBoundTextureId() {
      return GL11.glGetInteger(32873);
   }

   public static void printBoundTextureInfo(int texture) {
      int w = getTextureWidth();
      int h = getTextureHeight();
      int depth = GL11.glGetTexLevelParameteri(3553, 0, 32881);
      int format = GL11.glGetTexLevelParameteri(3553, 0, 4099);
      MinimapUtils.log("texture %d parameters: width=%d, height=%d, depth=%d, format=%08x", texture, w, h, depth, format);
   }

   public static int getMaxTextureSize() {
      return GL11.glGetInteger(3379);
   }

   public static void drawTexturedRect(double x, double y, double w, double h) {
      drawTexturedRect(x, y, w, h, 0.0D, 0.0D, 1.0D, 1.0D);
   }

   public static void drawTexturedRect(double x, double y, double w, double h, double u1, double v1, double u2, double v2) {
      try {
         GL11.glEnable(3553);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         Tessellator tes = Tessellator.instance;
         tes.startDrawingQuads();
         tes.addVertexWithUV(x + w, y, zDepth, u2, v1);
         tes.addVertexWithUV(x, y, zDepth, u1, v1);
         tes.addVertexWithUV(x, y + h, zDepth, u1, v2);
         tes.addVertexWithUV(x + w, y + h, zDepth, u2, v2);
         tes.draw();
         GL11.glDisable(3042);
      } catch (NullPointerException var17) {
         MinimapUtils.log("MwRender.drawTexturedRect: null pointer exception");
      }

   }

   public static void drawArrow(double x, double y, double angle, double length) {
      double arrowBackAngle = 2.356194490192345D;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      Tessellator tes = Tessellator.instance;
      tes.startDrawing(6);
      tes.addVertex(x + length * Math.cos(angle), y + length * Math.sin(angle), zDepth);
      tes.addVertex(x + length * 0.5D * Math.cos(angle - arrowBackAngle), y + length * 0.5D * Math.sin(angle - arrowBackAngle), zDepth);
      tes.addVertex(x + length * 0.3D * Math.cos(angle + 3.141592653589793D), y + length * 0.3D * Math.sin(angle + 3.141592653589793D), zDepth);
      tes.addVertex(x + length * 0.5D * Math.cos(angle + arrowBackAngle), y + length * 0.5D * Math.sin(angle + arrowBackAngle), zDepth);
      tes.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      Tessellator tes = Tessellator.instance;
      tes.startDrawing(4);
      tes.addVertex(x1, y1, zDepth);
      tes.addVertex(x2, y2, zDepth);
      tes.addVertex(x3, y3, zDepth);
      tes.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawRect(double x, double y, double w, double h) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      Tessellator tes = Tessellator.instance;
      tes.startDrawingQuads();
      tes.addVertex(x + w, y, zDepth);
      tes.addVertex(x, y, zDepth);
      tes.addVertex(x, y + h, zDepth);
      tes.addVertex(x + w, y + h, zDepth);
      tes.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawCircle(double x, double y, double r) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      Tessellator tes = Tessellator.instance;
      tes.startDrawing(6);
      tes.addVertex(x, y, zDepth);
      double end = 6.283185307179586D;
      double incr = end / 30.0D;

      for(double theta = -incr; theta < end; theta += incr) {
         tes.addVertex(x + r * Math.cos(-theta), y + r * Math.sin(-theta), zDepth);
      }

      tes.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawCircleBorder(double x, double y, double r, double width) {
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      Tessellator tes = Tessellator.instance;
      tes.startDrawing(5);
      double end = 6.283185307179586D;
      double incr = end / 30.0D;
      double r2 = r + width;

      for(double theta = -incr; theta < end; theta += incr) {
         tes.addVertex(x + r * Math.cos(-theta), y + r * Math.sin(-theta), zDepth);
         tes.addVertex(x + r2 * Math.cos(-theta), y + r2 * Math.sin(-theta), zDepth);
      }

      tes.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawRectBorder(double x, double y, double w, double h, double bw) {
      drawRect(x - bw, y - bw, w + bw + bw, bw);
      drawRect(x - bw, y + h, w + bw + bw, bw);
      drawRect(x - bw, y, bw, h);
      drawRect(x + w, y, bw, h);
   }

   public static void drawString(int x, int y, int colour, String formatString, Object... args) {
      Minecraft mc = Minecraft.getMinecraft();
      FontRenderer fr = mc.fontRenderer;
      String s = String.format(formatString, args);
      fr.drawStringWithShadow(s, x, y, colour);
   }

   public static void drawCentredString(int x, int y, int colour, String formatString, Object... args) {
      Minecraft mc = Minecraft.getMinecraft();
      FontRenderer fr = mc.fontRenderer;
      String s = String.format(formatString, args);
      int w = fr.getStringWidth(s);
      fr.drawStringWithShadow(s, x - w / 2, y, colour);
   }

   public static void setCircularStencil(double x, double y, double r) {
      GL11.glEnable(2929);
      GL11.glColorMask(false, false, false, false);
      GL11.glDepthMask(true);
      GL11.glDepthFunc(519);
      setColour(-1);
      zDepth = 1000.0D;
      drawCircle(x, y, r);
      zDepth = 0.0D;
      GL11.glColorMask(true, true, true, true);
      GL11.glDepthMask(false);
      GL11.glDepthFunc(516);
   }

   public static void disableStencil() {
      GL11.glDepthMask(true);
      GL11.glDepthFunc(515);
      GL11.glDisable(2929);
   }
}
