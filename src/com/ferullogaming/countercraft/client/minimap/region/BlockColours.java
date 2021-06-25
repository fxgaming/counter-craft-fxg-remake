package com.ferullogaming.countercraft.client.minimap.region;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class BlockColours {
   public static final int MAX_BLOCKS = 4096;
   public static final int MAX_META = 16;
   public static final int MAX_BIOMES = 256;
   public static final String biomeSectionString = "[biomes]";
   public static final String blockSectionString = "[blocks]";
   private int[] bcArray = new int[65536];
   private int[] waterMultiplierArray = new int[256];
   private int[] grassMultiplierArray = new int[256];
   private int[] foliageMultiplierArray = new int[256];
   private BlockColours.BlockType[] blockTypeArray = new BlockColours.BlockType[65536];

   public BlockColours() {
      Arrays.fill(this.bcArray, 0);
      Arrays.fill(this.waterMultiplierArray, 16777215);
      Arrays.fill(this.grassMultiplierArray, 16777215);
      Arrays.fill(this.foliageMultiplierArray, 16777215);
      Arrays.fill(this.blockTypeArray, BlockColours.BlockType.NORMAL);
   }

   private static BlockColours.BlockType getBlockTypeFromString(String typeString) {
      BlockColours.BlockType blockType = BlockColours.BlockType.NORMAL;
      if (typeString.equalsIgnoreCase("normal")) {
         blockType = BlockColours.BlockType.NORMAL;
      } else if (typeString.equalsIgnoreCase("grass")) {
         blockType = BlockColours.BlockType.GRASS;
      } else if (typeString.equalsIgnoreCase("leaves")) {
         blockType = BlockColours.BlockType.LEAVES;
      } else if (typeString.equalsIgnoreCase("foliage")) {
         blockType = BlockColours.BlockType.FOLIAGE;
      } else if (typeString.equalsIgnoreCase("water")) {
         blockType = BlockColours.BlockType.WATER;
      } else if (typeString.equalsIgnoreCase("opaque")) {
         blockType = BlockColours.BlockType.OPAQUE;
      }

      return blockType;
   }

   private static String getBlockTypeAsString(BlockColours.BlockType blockType) {
      String s = "normal";
      switch(blockType) {
      case NORMAL:
         s = "normal";
         break;
      case GRASS:
         s = "grass";
         break;
      case LEAVES:
         s = "leaves";
         break;
      case FOLIAGE:
         s = "foliage";
         break;
      case WATER:
         s = "water";
         break;
      case OPAQUE:
         s = "opaque";
      }

      return s;
   }

   public static int getColourFromString(String s) {
      return (int)(Long.parseLong(s, 16) & 4294967295L);
   }

   private static String getMostOccurringKey(Map map, String defaultItem) {
      int maxCount = 1;
      String mostOccurringKey = defaultItem;
      Iterator i$ = map.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         String key = (String)entry.getKey();
         int count = ((Integer)entry.getValue()).intValue();
         if (count > maxCount) {
            maxCount = count;
            mostOccurringKey = key;
         }
      }

      return mostOccurringKey;
   }

   private static void writeMinimalBlockLines(Writer fout, String lineStart, String[] items, String defaultItem) throws IOException {
      Map frequencyMap = new HashMap();
      String[] arr$ = items;
      int i = items.length;

      for(int i$ = 0; i$ < i; ++i$) {
         String item = arr$[i$];
         int count = 0;
         if (frequencyMap.containsKey(item)) {
            count = ((Integer)frequencyMap.get(item)).intValue();
         }

         frequencyMap.put(item, count + 1);
      }

      String mostOccurringItem = getMostOccurringKey(frequencyMap, defaultItem);
      if (!mostOccurringItem.equals(defaultItem)) {
         fout.write(String.format("%s * %s\n", lineStart, mostOccurringItem));
      }

      for(i = 0; i < items.length; ++i) {
         if (!items[i].equals(mostOccurringItem) && !items[i].equals(defaultItem)) {
            fout.write(String.format("%s %d %s\n", lineStart, i, items[i]));
         }
      }

   }

   public static void writeOverridesFile(File f) {
      OutputStreamWriter fout = null;

      try {
         fout = new OutputStreamWriter(new FileOutputStream(f));
         fout.write("block 37 * 60ffff00      # make dandelions more yellow\nblock 38 * 60ff0000      # make roses more red\nblocktype 2 * grass      # grass block\nblocktype 8 * water      # still water block\nblocktype 9 * water      # flowing water block\nblocktype 18 * leaves    # leaves block\nblocktype 18 1 opaque    # pine leaves (not biome colorized)\nblocktype 18 2 opaque    # birch leaves (not biome colorized)\nblocktype 31 * grass     # tall grass block\nblocktype 106 * foliage  # vines block\nblocktype 169 * grass    # biomes o plenty holy grass\nblocktype 1920 * grass   # biomes o plenty plant\nblocktype 1923 * opaque  # biomes o plenty leaves 1\nblocktype 1924 * opaque  # biomes o plenty leaves 2\nblocktype 1925 * foliage # biomes o plenty foliage\nblocktype 1926 * opaque  # biomes o plenty fruit leaf block\nblocktype 1932 * foliage # biomes o plenty tree moss\nblocktype 1962 * leaves  # biomes o plenty colorized leaves\nblocktype 2164 * leaves  # twilight forest leaves\nblocktype 2177 * leaves  # twilight forest magic leaves\nblocktype 2204 * leaves  # extrabiomesXL green leaves\nblocktype 2200 * opaque  # extrabiomesXL autumn leaves\nblocktype 3257 * opaque  # natura berry bush\nblocktype 3272 * opaque  # natura darkwood leaves\nblocktype 3259 * leaves  # natura flora leaves\nblocktype 3278 * opaque  # natura rare leaves\nblocktype 3258 * opaque  # natura sakura leaves\n");
      } catch (IOException var11) {
         ;
      } finally {
         if (fout != null) {
            try {
               fout.close();
            } catch (IOException var10) {
               ;
            }
         }

      }

   }

   public int getColour(int blockAndMeta) {
      return this.bcArray[blockAndMeta & '\uffff'];
   }

   public void setColour(int blockAndMeta, int colour) {
      this.bcArray[blockAndMeta & '\uffff'] = colour;
   }

   public int getColour(int blockID, int meta) {
      return this.bcArray[(blockID & 4095) << 4 | meta & 15];
   }

   public void setColour(int blockID, int meta, int colour) {
      this.bcArray[(blockID & 4095) << 4 | meta & 15] = colour;
   }

   private int getGrassColourMultiplier(int biome) {
      return this.grassMultiplierArray != null && biome >= 0 && biome < this.grassMultiplierArray.length ? this.grassMultiplierArray[biome] : 16777215;
   }

   private int getWaterColourMultiplier(int biome) {
      return this.waterMultiplierArray != null && biome >= 0 && biome < this.waterMultiplierArray.length ? this.waterMultiplierArray[biome] : 16777215;
   }

   private int getFoliageColourMultiplier(int biome) {
      return this.foliageMultiplierArray != null && biome >= 0 && biome < this.foliageMultiplierArray.length ? this.foliageMultiplierArray[biome] : 16777215;
   }

   public int getBiomeColour(int blockAndMeta, int biome) {
      blockAndMeta &= 65535;
      int colourMultiplier;
      switch(this.blockTypeArray[blockAndMeta]) {
      case GRASS:
         colourMultiplier = this.getGrassColourMultiplier(biome);
         break;
      case LEAVES:
      case FOLIAGE:
         colourMultiplier = this.getFoliageColourMultiplier(biome);
         break;
      case WATER:
         colourMultiplier = this.getWaterColourMultiplier(biome);
         break;
      default:
         colourMultiplier = 16777215;
      }

      return colourMultiplier;
   }

   public void setBiomeWaterShading(int biomeID, int colour) {
      this.waterMultiplierArray[biomeID & 255] = colour;
   }

   public void setBiomeGrassShading(int biomeID, int colour) {
      this.grassMultiplierArray[biomeID & 255] = colour;
   }

   public void setBiomeFoliageShading(int biomeID, int colour) {
      this.foliageMultiplierArray[biomeID & 255] = colour;
   }

   public BlockColours.BlockType getBlockType(int blockAndMeta) {
      return this.blockTypeArray[blockAndMeta & '\uffff'];
   }

   public BlockColours.BlockType getBlockType(int blockId, int meta) {
      return this.blockTypeArray[(blockId & 4095) << 4 | meta & 15];
   }

   public void setBlockType(int blockId, int meta, BlockColours.BlockType type) {
      this.blockTypeArray[(blockId & 4095) << 4 | meta & 15] = type;
   }

   public void setBlockType(int blockAndMeta, BlockColours.BlockType type) {
      this.blockTypeArray[blockAndMeta & '\uffff'] = type;
   }

   private void loadBiomeLine(String[] split) {
      try {
         int startBiomeId = 0;
         int endBiomeId = 256;
         if (!split[1].equals("*")) {
            startBiomeId = Integer.parseInt(split[1]);
            endBiomeId = startBiomeId + 1;
         }

         if (startBiomeId >= 0 && startBiomeId < 256) {
            int waterMultiplier = getColourFromString(split[2]) & 16777215;
            int grassMultiplier = getColourFromString(split[3]) & 16777215;
            int foliageMultiplier = getColourFromString(split[4]) & 16777215;

            for(int biomeId = startBiomeId; biomeId < endBiomeId; ++biomeId) {
               this.setBiomeWaterShading(biomeId, waterMultiplier);
               this.setBiomeGrassShading(biomeId, grassMultiplier);
               this.setBiomeFoliageShading(biomeId, foliageMultiplier);
            }
         }
      } catch (NumberFormatException var8) {
         ;
      }

   }

   private void loadBlockLine(String[] split, boolean isBlockColourLine) {
      try {
         int startBlockId = 0;
         int endBlockId = 4096;
         if (!split[1].equals("*")) {
            startBlockId = Integer.parseInt(split[1]);
            endBlockId = startBlockId + 1;
         }

         int startBlockMeta = 0;
         int endBlockMeta = 16;
         if (!split[2].equals("*")) {
            startBlockMeta = Integer.parseInt(split[2]);
            endBlockMeta = startBlockMeta + 1;
         }

         if (startBlockId >= 0 && startBlockId < 4096 && startBlockMeta >= 0 && startBlockMeta < 16) {
            int blockId;
            int blockMeta;
            if (isBlockColourLine) {
               int colour = getColourFromString(split[3]);

               for(blockId = startBlockId; blockId < endBlockId; ++blockId) {
                  for(blockMeta = startBlockMeta; blockMeta < endBlockMeta; ++blockMeta) {
                     this.setColour(blockId, blockMeta, colour);
                  }
               }
            } else {
               BlockColours.BlockType type = getBlockTypeFromString(split[3]);

               for(blockId = startBlockId; blockId < endBlockId; ++blockId) {
                  for(blockMeta = startBlockMeta; blockMeta < endBlockMeta; ++blockMeta) {
                     this.setBlockType(blockId, blockMeta, type);
                  }
               }
            }
         }
      } catch (NumberFormatException var10) {
         ;
      }

   }

   public void loadFromFile(File f) {
      Scanner fin = null;

      try {
         fin = new Scanner(new FileReader(f));

         while(true) {
            while(true) {
               String line;
               do {
                  if (!fin.hasNextLine()) {
                     return;
                  }

                  line = fin.nextLine().split("#")[0].trim();
               } while(line.length() <= 0);

               String[] lineSplit = line.split(" ");
               if (lineSplit[0].equals("biome") && lineSplit.length == 5) {
                  this.loadBiomeLine(lineSplit);
               } else if (lineSplit[0].equals("block") && lineSplit.length == 4) {
                  this.loadBlockLine(lineSplit, true);
               } else if (lineSplit[0].equals("blocktype") && lineSplit.length == 4) {
                  this.loadBlockLine(lineSplit, false);
               }
            }
         }
      } catch (IOException var8) {
         ;
      } finally {
         if (fin != null) {
            fin.close();
         }

      }

   }

   public void saveBiomes(Writer fout) throws IOException {
      fout.write("biome * ffffff ffffff ffffff\n");

      for(int biomeId = 0; biomeId < 256; ++biomeId) {
         int waterMultiplier = this.getWaterColourMultiplier(biomeId) & 16777215;
         int grassMultiplier = this.getGrassColourMultiplier(biomeId) & 16777215;
         int foliageMultiplier = this.getFoliageColourMultiplier(biomeId) & 16777215;
         if (waterMultiplier != 16777215 || grassMultiplier != 16777215 || foliageMultiplier != 16777215) {
            fout.write(String.format("biome %d %06x %06x %06x\n", biomeId, waterMultiplier, grassMultiplier, foliageMultiplier));
         }
      }

   }

   public void saveBlocks(Writer fout) throws IOException {
      fout.write("block * * 00000000\n");
      String[] colours = new String[16];

      for(int blockId = 0; blockId < 4096; ++blockId) {
         for(int meta = 0; meta < 16; ++meta) {
            colours[meta] = String.format("%08x", this.getColour(blockId, meta));
         }

         String lineStart = String.format("block %d", blockId);
         writeMinimalBlockLines(fout, lineStart, colours, "00000000");
      }

   }

   public void saveBlockTypes(Writer fout) throws IOException {
      fout.write("blocktype * * normal\n");
      String[] blockTypes = new String[16];

      for(int blockId = 0; blockId < 4096; ++blockId) {
         for(int meta = 0; meta < 16; ++meta) {
            BlockColours.BlockType bt = this.getBlockType(blockId, meta);
            blockTypes[meta] = getBlockTypeAsString(bt);
         }

         String lineStart = String.format("blocktype %d", blockId);
         writeMinimalBlockLines(fout, lineStart, blockTypes, "normal");
      }

   }

   public void saveToFile(File f) {
      OutputStreamWriter fout = null;

      try {
         fout = new OutputStreamWriter(new FileOutputStream(f));
         this.saveBiomes(fout);
         this.saveBlockTypes(fout);
         this.saveBlocks(fout);
      } catch (IOException var12) {
         ;
      } finally {
         if (fout != null) {
            try {
               fout.close();
            } catch (IOException var11) {
               ;
            }
         }

      }

   }

   public static enum BlockType {
      NORMAL,
      GRASS,
      LEAVES,
      FOLIAGE,
      WATER,
      OPAQUE;
   }
}
