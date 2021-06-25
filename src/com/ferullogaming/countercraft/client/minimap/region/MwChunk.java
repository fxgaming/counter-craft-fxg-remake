package com.ferullogaming.countercraft.client.minimap.region;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MwChunk implements IChunk {
   public static final int SIZE = 16;
   public final int x;
   public final int z;
   public final int dimension;
   public final byte[][] msbArray;
   public final byte[][] lsbArray;
   public final byte[][] metaArray;
   public final byte[][] lightingArray;
   public final byte[] biomeArray;
   public final int maxY;

   public MwChunk(int x, int z, int dimension, byte[][] msbArray, byte[][] lsbArray, byte[][] metaArray, byte[][] lightingArray, byte[] biomeArray) {
      this.x = x;
      this.z = z;
      this.dimension = dimension;
      this.msbArray = msbArray;
      this.lsbArray = lsbArray;
      this.metaArray = metaArray;
      this.biomeArray = biomeArray;
      this.lightingArray = lightingArray;
      int maxY = 0;

      for(int y = 0; y < 16; ++y) {
         if (lsbArray[y] != null) {
            maxY = (y << 4) + 15;
         }
      }

      this.maxY = maxY;
   }

   public static MwChunk read(int x, int z, int dimension, RegionFileCache regionFileCache) {
      byte[] biomeArray = null;
      byte[][] msbArray = new byte[16][];
      byte[][] lsbArray = new byte[16][];
      byte[][] metaArray = new byte[16][];
      byte[][] lightingArray = new byte[16][];
      DataInputStream dis = null;
      RegionFile regionFile = regionFileCache.getRegionFile(x << 4, z << 4, dimension);
      if (!regionFile.isOpen() && regionFile.exists()) {
         regionFile.open();
      }

      if (regionFile.isOpen()) {
         dis = regionFile.getChunkDataInputStream(x & 31, z & 31);
      }

      if (dis != null) {
         try {
            Nbt root = Nbt.readNextElement(dis);
            Nbt level = root.getChild("Level");
            int xNbt = level.getChild("xPos").getInt();
            int zNbt = level.getChild("zPos").getInt();
            Nbt sections = level.getChild("Sections");

            for(int i = 0; i < sections.size(); ++i) {
               Nbt section = sections.getChild(i);
               if (!section.isNull()) {
                  int y = section.getChild("Y").getByte();
                  lsbArray[y & 15] = section.getChild("Blocks").getByteArray();
                  msbArray[y & 15] = section.getChild("Add").getByteArray();
                  metaArray[y & 15] = section.getChild("Data").getByteArray();
               }
            }

            biomeArray = level.getChild("Biomes").getByteArray();
         } catch (IOException var27) {
            ;
         } finally {
            try {
               dis.close();
            } catch (IOException var26) {
               ;
            }

         }
      }

      return new MwChunk(x, z, dimension, msbArray, lsbArray, metaArray, lightingArray, biomeArray);
   }

   public String toString() {
      return String.format("(%d, %d) dim%d", this.x, this.z, this.dimension);
   }

   public boolean isEmpty() {
      return this.maxY <= 0;
   }

   public int getBiome(int x, int z) {
      return this.biomeArray != null ? this.biomeArray[(z & 15) << 4 | x & 15] & 255 : 0;
   }

   public int getLightValue(int x, int y, int z) {
      return 15;
   }

   public int getMaxY() {
      return this.maxY;
   }

   public int getBlockAndMetadata(int x, int y, int z) {
      int yi = y >> 4 & 15;
      int offset = (y & 15) << 8 | (z & 15) << 4 | x & 15;
      int lsb = this.lsbArray != null && this.lsbArray[yi] != null ? this.lsbArray[yi][offset] : 0;
      int msb = this.msbArray != null && this.msbArray[yi] != null ? this.msbArray[yi][offset >> 1] : 0;
      int meta = this.metaArray != null && this.metaArray[yi] != null ? this.metaArray[yi][offset >> 1] : 0;
      return (offset & 1) == 1 ? (msb & 240) << 8 | (lsb & 255) << 4 | (meta & 240) >> 4 : (msb & 15) << 12 | (lsb & 255) << 4 | meta & 15;
   }

   public Nbt getNbt() {
      Nbt sections = new Nbt((byte)9, "Sections", (Object)null);

      Nbt section;
      for(int y = 0; y < 16; ++y) {
         section = new Nbt((byte)10, "", (Object)null);
         section.addChild(new Nbt((byte)1, "Y", (byte)y));
         if (this.lsbArray != null && this.lsbArray[y] != null) {
            section.addChild(new Nbt((byte)7, "Blocks", this.lsbArray[y]));
         }

         if (this.msbArray != null && this.msbArray[y] != null) {
            section.addChild(new Nbt((byte)7, "Add", this.msbArray[y]));
         }

         if (this.metaArray != null && this.metaArray[y] != null) {
            section.addChild(new Nbt((byte)7, "Data", this.metaArray[y]));
         }

         sections.addChild(section);
      }

      Nbt level = new Nbt((byte)10, "Level", (Object)null);
      level.addChild(new Nbt((byte)3, "xPos", this.x));
      level.addChild(new Nbt((byte)3, "zPos", this.z));
      level.addChild(sections);
      if (this.biomeArray != null) {
         level.addChild(new Nbt((byte)7, "Biomes", this.biomeArray));
      }

      section = new Nbt((byte)10, "", (Object)null);
      section.addChild(level);
      return section;
   }

   public synchronized boolean write(RegionFileCache regionFileCache) {
      boolean error = false;
      RegionFile regionFile = regionFileCache.getRegionFile(this.x << 4, this.z << 4, this.dimension);
      if (!regionFile.isOpen()) {
         error = regionFile.open();
      }

      if (!error) {
         DataOutputStream dos = regionFile.getChunkDataOutputStream(this.x & 31, this.z & 31);
         if (dos != null) {
            Nbt chunkNbt = this.getNbt();

            try {
               chunkNbt.writeElement(dos);
            } catch (IOException var15) {
               error = true;
            } finally {
               try {
                  dos.close();
               } catch (IOException var14) {
                  ;
               }

            }
         }
      }

      return error;
   }
}
