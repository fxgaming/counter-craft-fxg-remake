package com.ferullogaming.countercraft.client.minimap;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.world.chunk.Chunk;

public class MinimapUtils {
   public static final Pattern patternInvalidChars = Pattern.compile("[^a-zA-Z0-9_]");

   public static void logInfo(String s, Object... args) {
   }

   public static void log(String s, Object... args) {
      logInfo(String.format(s, args));
   }

   public static String mungeString(String s) {
      s = s.replace('.', '_');
      s = s.replace('-', '_');
      s = s.replace(' ', '_');
      s = s.replace('/', '_');
      s = s.replace('\\', '_');
      return patternInvalidChars.matcher(s).replaceAll("");
   }

   public static File getFreeFilename(File dir, String baseName, String ext) {
      int i = 0;
      File outputFile;
      if (dir != null) {
         outputFile = new File(dir, baseName + "." + ext);
      } else {
         outputFile = new File(baseName + "." + ext);
      }

      for(; outputFile.exists() && i < 1000; ++i) {
         if (dir != null) {
            outputFile = new File(dir, baseName + "." + i + "." + ext);
         } else {
            outputFile = new File(baseName + "." + i + "." + ext);
         }
      }

      return i < 1000 ? outputFile : null;
   }

   public static void printBoth(String msg) {
      EntityClientPlayerMP thePlayer = Minecraft.getMinecraft().thePlayer;
      if (thePlayer != null) {
         thePlayer.addChatMessage(msg);
      }

      log("%s", msg);
   }

   public static File getDimensionDir(File worldDir, int dimension) {
      File dimDir;
      if (dimension != 0) {
         dimDir = new File(worldDir, "DIM" + dimension);
      } else {
         dimDir = worldDir;
      }

      return dimDir;
   }

   public static IntBuffer allocateDirectIntBuffer(int size) {
      return ByteBuffer.allocateDirect(size * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
   }

   public static int nextHighestPowerOf2(int v) {
      --v;
      v |= v >> 1;
      v |= v >> 2;
      v |= v >> 4;
      v |= v >> 8;
      v |= v >> 16;
      return v + 1;
   }

   public static String getCurrentDateString() {
      DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
      return dateFormat.format(new Date());
   }

   public static int distToChunkSq(int x, int z, Chunk chunk) {
      int dx = (chunk.xPosition << 4) + 8 - x;
      int dz = (chunk.zPosition << 4) + 8 - z;
      return dx * dx + dz * dz;
   }
}
