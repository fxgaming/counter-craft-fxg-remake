package com.ferullogaming.countercraft.client.minimap;

import com.ferullogaming.countercraft.client.minimap.region.BlockColours;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockColourGen {
   private static int getIconMapColour(Icon icon, Texture terrainTexture) {
      int iconX = Math.round((float)terrainTexture.w * Math.min(icon.getMinU(), icon.getMaxU()));
      int iconY = Math.round((float)terrainTexture.h * Math.min(icon.getMinV(), icon.getMaxV()));
      int iconWidth = Math.round((float)terrainTexture.w * Math.abs(icon.getMaxU() - icon.getMinU()));
      int iconHeight = Math.round((float)terrainTexture.h * Math.abs(icon.getMaxV() - icon.getMinV()));
      int[] pixels = new int[iconWidth * iconHeight];
      terrainTexture.getRGB(iconX, iconY, iconWidth, iconHeight, pixels, 0, iconWidth);
      return Render.getAverageColourOfArray(pixels);
   }

   private static int adjustBlockColourFromType(BlockColours bc, int blockAndMeta, int blockColour) {
      Block block = Block.blocksList[blockAndMeta >> 4];
      BlockColours.BlockType blockType = bc.getBlockType(blockAndMeta);
      switch(blockType) {
      case OPAQUE:
         blockColour |= -16777216;
      case NORMAL:
         try {
            int renderColour = block.getRenderColor(blockAndMeta & 15);
            if (renderColour != 16777215) {
               blockColour = Render.multiplyColours(blockColour, -16777216 | renderColour);
            }
         } catch (RuntimeException var6) {
            ;
         }
         break;
      case LEAVES:
         blockColour |= -16777216;
      }

      return blockColour;
   }

   private static void genBiomeColours(BlockColours bc) {
      for(int i = 0; i < BiomeGenBase.biomeList.length; ++i) {
         if (BiomeGenBase.biomeList[i] != null) {
            bc.setBiomeWaterShading(i, BiomeGenBase.biomeList[i].getWaterColorMultiplier() & 16777215);
            bc.setBiomeGrassShading(i, BiomeGenBase.biomeList[i].getBiomeGrassColor() & 16777215);
            bc.setBiomeFoliageShading(i, BiomeGenBase.biomeList[i].getBiomeFoliageColor() & 16777215);
         } else {
            bc.setBiomeWaterShading(i, 16777215);
            bc.setBiomeGrassShading(i, 16777215);
            bc.setBiomeFoliageShading(i, 16777215);
         }
      }

   }

   public static void genBlockColours(BlockColours bc) {
      MinimapUtils.log("generating block map colours from textures");
      int terrainTextureId = Minecraft.getMinecraft().renderEngine.getTexture(TextureMap.locationBlocksTexture).getGlTextureId();
      if (terrainTextureId == 0) {
         MinimapUtils.log("error: could get terrain texture ID");
      } else {
         Texture terrainTexture = new Texture(terrainTextureId);
         double u1Last = 0.0D;
         double u2Last = 0.0D;
         double v1Last = 0.0D;
         double v2Last = 0.0D;
         int blockColourLast = 0;
         int e_count = 0;
         int b_count = 0;
         int s_count = 0;

         for(int blockID = 0; blockID < Block.blocksList.length; ++blockID) {
            for(int dv = 0; dv < 16; ++dv) {
               int blockAndMeta = (blockID & 4095) << 4 | dv & 15;
               Block block = Block.blocksList[blockID];
               int blockColour = 0;
               if (block != null) {
                  Icon icon = null;

                  try {
                     icon = block.getIcon(1, dv);
                  } catch (Exception var29) {
                     ++e_count;
                  }

                  if (icon != null) {
                     double u1 = (double)icon.getMinU();
                     double u2 = (double)icon.getMaxU();
                     double v1 = (double)icon.getMinV();
                     double v2 = (double)icon.getMaxV();
                     if (u1 == u1Last && u2 == u2Last && v1 == v1Last && v2 == v2Last) {
                        blockColour = blockColourLast;
                        ++s_count;
                     } else {
                        blockColour = getIconMapColour(icon, terrainTexture);
                        u1Last = u1;
                        u2Last = u2;
                        v1Last = v1;
                        v2Last = v2;
                        blockColourLast = blockColour;
                        ++b_count;
                     }
                  }

                  blockColour = adjustBlockColourFromType(bc, blockAndMeta, blockColour);
               }

               bc.setColour(blockAndMeta, blockColour);
            }
         }

         MinimapUtils.log("processed %d block textures, %d skipped, %d exceptions", b_count, s_count, e_count);
         genBiomeColours(bc);
      }
   }
}
