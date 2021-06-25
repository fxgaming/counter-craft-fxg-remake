package com.ferullogaming.countercraft.client.minimap.region;

public interface IChunk {
   int getBlockAndMetadata(int var1, int var2, int var3);

   int getBiome(int var1, int var2);

   int getLightValue(int var1, int var2, int var3);

   int getMaxY();
}
