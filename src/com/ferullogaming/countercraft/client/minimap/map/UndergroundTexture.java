package com.ferullogaming.countercraft.client.minimap.map;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.Texture;
import com.ferullogaming.countercraft.client.minimap.region.ChunkRender;
import com.ferullogaming.countercraft.client.minimap.region.IChunk;
import java.awt.Point;
import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.chunk.Chunk;

public class UndergroundTexture extends Texture {
   private Minimap mw;
   private int px = 0;
   private int py = 0;
   private int pz = 0;
   private int updateX;
   private int updateZ;
   private byte[][] updateFlags = new byte[9][256];
   private Point[] loadedChunkArray;
   private int textureSize;
   private int textureChunks;
   private int[] pixels;

   public UndergroundTexture(Minimap mw, int textureSize, boolean linearScaling) {
      super(textureSize, textureSize, 0, 9728, 9728, 10497);
      this.setLinearScaling(false);
      this.textureSize = textureSize;
      this.textureChunks = textureSize >> 4;
      this.loadedChunkArray = new Point[this.textureChunks * this.textureChunks];
      this.pixels = new int[textureSize * textureSize];
      Arrays.fill(this.pixels, -16777216);
      this.mw = mw;
   }

   public void clearChunkPixels(int cx, int cz) {
      int tx = cx << 4 & this.textureSize - 1;
      int tz = cz << 4 & this.textureSize - 1;

      for(int j = 0; j < 16; ++j) {
         int offset = (tz + j) * this.textureSize + tx;
         Arrays.fill(this.pixels, offset, offset + 16, -16777216);
      }

      this.updateTextureArea(tx, tz, 16, 16);
   }

   void renderToTexture(int y) {
      this.setPixelBufPosition(0);

      for(int i = 0; i < this.pixels.length; ++i) {
         int colour = this.pixels[i];
         int height = colour >> 24 & 255;
         int alpha = y >= height ? 255 - (y - height) * 8 : 0;
         if (alpha < 0) {
            alpha = 0;
         }

         this.pixelBufPut(alpha << 24 & -16777216 | colour & 16777215);
      }

      this.updateTexture();
   }

   public int getLoadedChunkOffset(int cx, int cz) {
      int cxOffset = cx & this.textureChunks - 1;
      int czOffset = cz & this.textureChunks - 1;
      return czOffset * this.textureChunks + cxOffset;
   }

   public void requestView(MapView view) {
      int cxMin = (int)view.getMinX() >> 4;
      int czMin = (int)view.getMinZ() >> 4;
      int cxMax = (int)view.getMaxX() >> 4;
      int czMax = (int)view.getMaxZ() >> 4;

      for(int cz = czMin; cz <= czMax; ++cz) {
         for(int cx = cxMin; cx <= cxMax; ++cx) {
            Point requestedChunk = new Point(cx, cz);
            int offset = this.getLoadedChunkOffset(cx, cz);
            Point currentChunk = this.loadedChunkArray[offset];
            if (currentChunk == null || !currentChunk.equals(requestedChunk)) {
               this.clearChunkPixels(cx, cz);
               this.loadedChunkArray[offset] = requestedChunk;
            }
         }
      }

   }

   public boolean isChunkInTexture(int cx, int cz) {
      Point requestedChunk = new Point(cx, cz);
      int offset = this.getLoadedChunkOffset(cx, cz);
      Point chunk = this.loadedChunkArray[offset];
      return chunk != null && chunk.equals(requestedChunk);
   }

   public void update() {
      this.clearFlags();
      this.px = this.mw.playerXInt;
      this.py = this.mw.playerYInt;
      this.pz = this.mw.playerZInt;
      this.updateX = (this.px >> 4) - 1;
      this.updateZ = (this.pz >> 4) - 1;
      this.processBlock(this.px - (this.updateX << 4), this.py, this.pz - (this.updateZ << 4));
      int cxMax = this.updateX + 2;
      int czMax = this.updateZ + 2;
      WorldClient world = this.mw.mc.theWorld;
      int flagOffset = 0;

      for(int cz = this.updateZ; cz <= czMax; ++cz) {
         for(int cx = this.updateX; cx <= cxMax; ++cx) {
            if (this.isChunkInTexture(cx, cz)) {
               Chunk chunk = world.getChunkFromChunkCoords(cx, cz);
               int tx = cx << 4 & this.textureSize - 1;
               int tz = cz << 4 & this.textureSize - 1;
               int pixelOffset = tz * this.textureSize + tx;
               byte[] mask = this.updateFlags[flagOffset];
               ChunkRender.renderUnderground(this.mw.blockColours, new UndergroundTexture.RenderChunk(chunk), this.pixels, pixelOffset, this.textureSize, this.py, mask);
            }

            ++flagOffset;
         }
      }

      this.renderToTexture(this.py + 1);
   }

   private void clearFlags() {
      byte[][] arr$ = this.updateFlags;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         byte[] chunkFlags = arr$[i$];
         Arrays.fill(chunkFlags, (byte)0);
      }

   }

   private void processBlock(int xi, int y, int zi) {
      int x = (this.updateX << 4) + xi;
      int z = (this.updateZ << 4) + zi;
      int xDist = this.px - x;
      int zDist = this.pz - z;
      if (xDist * xDist + zDist * zDist <= 256 && this.isChunkInTexture(x >> 4, z >> 4)) {
         int chunkOffset = (zi >> 4) * 3 + (xi >> 4);
         int columnXi = xi & 15;
         int columnZi = zi & 15;
         int columnOffset = (columnZi << 4) + columnXi;
         byte columnFlag = this.updateFlags[chunkOffset][columnOffset];
         if (columnFlag == 0) {
            WorldClient world = this.mw.mc.theWorld;
            int blockID = world.getBlockId(x, y, z);
            Block block = Block.blocksList[blockID];
            if (block != null && block.isOpaqueCube()) {
               this.updateFlags[chunkOffset][columnOffset] = 2;
            } else {
               this.updateFlags[chunkOffset][columnOffset] = 1;
               this.processBlock(xi + 1, y, zi);
               this.processBlock(xi - 1, y, zi);
               this.processBlock(xi, y, zi + 1);
               this.processBlock(xi, y, zi - 1);
            }
         }
      }

   }

   class RenderChunk implements IChunk {
      Chunk chunk;

      public RenderChunk(Chunk chunk) {
         this.chunk = chunk;
      }

      public int getMaxY() {
         return this.chunk.getTopFilledSegment() + 15;
      }

      public int getBlockAndMetadata(int x, int y, int z) {
         int blockID = this.chunk.getBlockID(x, y, z);
         int meta = this.chunk.getBlockMetadata(x, y, z);
         return (blockID & 4095) << 4 | meta & 15;
      }

      public int getBiome(int x, int z) {
         return this.chunk.getBiomeArray()[z * 16 + x];
      }

      public int getLightValue(int x, int y, int z) {
         return this.chunk.getBlockLightValue(x, y, z, 0);
      }
   }
}
