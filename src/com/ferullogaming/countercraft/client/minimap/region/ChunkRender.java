package com.ferullogaming.countercraft.client.minimap.region;

public class ChunkRender {
   public static final byte FLAG_UNPROCESSED = 0;
   public static final byte FLAG_NON_OPAQUE = 1;
   public static final byte FLAG_OPAQUE = 2;
   public static final double brightenExponent = 0.35D;
   public static final double darkenExponent = 0.35D;
   public static final double brightenAmplitude = 0.7D;
   public static final double darkenAmplitude = 1.4D;

   public static double getHeightShading(int height, int heightW, int heightN) {
      int samples = 0;
      int heightDiff = 0;
      if (heightW > 0 && heightW < 255) {
         heightDiff += height - heightW;
         ++samples;
      }

      if (heightN > 0 && heightN < 255) {
         heightDiff += height - heightN;
         ++samples;
      }

      double heightDiffFactor = 0.0D;
      if (samples > 0) {
         heightDiffFactor = (double)heightDiff / ((double)samples * 255.0D);
      }

      return heightDiffFactor >= 0.0D ? Math.pow(heightDiffFactor, 0.35D) * 0.7D : -Math.pow(-heightDiffFactor, 0.35D) * 1.4D;
   }

   public static int getColumnColour(BlockColours bc, IChunk chunk, int x, int y, int z, int heightW, int heightN) {
      double a = 1.0D;
      double r = 0.0D;
      double g = 0.0D;

      double b;
      int alpha;
      double c1A;
      for(b = 0.0D; y > 0; --y) {
         int blockAndMeta = chunk.getBlockAndMetadata(x, y, z);
         int c1 = bc.getColour(blockAndMeta);
         alpha = c1 >> 24 & 255;
         if (alpha > 0) {
            int biome = chunk.getBiome(x, z);
            int c2 = bc.getBiomeColour(blockAndMeta, biome);
            c1A = (double)alpha / 255.0D;
            double c1R = (double)(c1 >> 16 & 255) / 255.0D;
            double c1G = (double)(c1 >> 8 & 255) / 255.0D;
            double c1B = (double)(c1 >> 0 & 255) / 255.0D;
            double c2R = (double)(c2 >> 16 & 255) / 255.0D;
            double c2G = (double)(c2 >> 8 & 255) / 255.0D;
            double c2B = (double)(c2 >> 0 & 255) / 255.0D;
            r += a * c1A * c1R * c2R;
            g += a * c1A * c1G * c2G;
            b += a * c1A * c1B * c2B;
            a *= 1.0D - c1A;
         }

         if (alpha == 255) {
            break;
         }
      }

      double heightShading = getHeightShading(y, heightW, heightN);
      alpha = chunk.getLightValue(x, y + 1, z);
      double lightShading = (double)alpha / 15.0D;
      c1A = (heightShading + 1.0D) * lightShading;
      r = Math.min(Math.max(0.0D, r * c1A), 1.0D);
      g = Math.min(Math.max(0.0D, g * c1A), 1.0D);
      b = Math.min(Math.max(0.0D, b * c1A), 1.0D);
      return (y & 255) << 24 | ((int)(r * 255.0D) & 255) << 16 | ((int)(g * 255.0D) & 255) << 8 | (int)(b * 255.0D) & 255;
   }

   static int getPixelHeightN(int[] pixels, int offset, int scanSize) {
      return offset >= scanSize ? pixels[offset - scanSize] >> 24 & 255 : -1;
   }

   static int getPixelHeightW(int[] pixels, int offset, int scanSize) {
      return (offset & scanSize - 1) >= 1 ? pixels[offset - 1] >> 24 & 255 : -1;
   }

   public static void renderSurface(BlockColours bc, IChunk chunk, int[] pixels, int offset, int scanSize, boolean dimensionHasCeiling) {
      int startY = chunk.getMaxY();

      for(int z = 0; z < 16; ++z) {
         for(int x = 0; x < 16; ++x) {
            int y = startY;
            int blockAndMeta;
            if (dimensionHasCeiling) {
               while(y >= 0) {
                  blockAndMeta = chunk.getBlockAndMetadata(x, y, z);
                  int alpha = bc.getColour(blockAndMeta) >> 24 & 255;
                  if (alpha != 255) {
                     break;
                  }

                  --y;
               }
            }

            blockAndMeta = offset + z * scanSize + x;
            pixels[blockAndMeta] = getColumnColour(bc, chunk, x, y, z, getPixelHeightW(pixels, blockAndMeta, scanSize), getPixelHeightN(pixels, blockAndMeta, scanSize));
         }
      }

   }

   public static void renderUnderground(BlockColours bc, IChunk chunk, int[] pixels, int offset, int scanSize, int startY, byte[] mask) {
      startY = Math.min(Math.max(0, startY), 255);

      for(int z = 0; z < 16; ++z) {
         for(int x = 0; x < 16; ++x) {
            if (mask == null || mask[z * 16 + x] == 1) {
               int lastNonTransparentY = startY;

               int y;
               for(y = startY; y < chunk.getMaxY(); ++y) {
                  int blockAndMeta = chunk.getBlockAndMetadata(x, y, z);
                  int alpha = bc.getColour(blockAndMeta) >> 24 & 255;
                  if (alpha == 255) {
                     break;
                  }

                  if (alpha > 0) {
                     lastNonTransparentY = y;
                  }
               }

               y = offset + z * scanSize + x;
               pixels[y] = getColumnColour(bc, chunk, x, lastNonTransparentY, z, getPixelHeightW(pixels, y, scanSize), getPixelHeightN(pixels, y, scanSize));
            }
         }
      }

   }
}
