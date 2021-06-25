package com.ferullogaming.countercraft.client.minimap.forge;

import com.ferullogaming.countercraft.client.minimap.MinimapUtils;
import java.io.File;
import java.util.List;
import net.minecraftforge.common.Configuration;

public class MinimapConfig extends Configuration {
   public MinimapConfig(File file) {
      super(file, true);
   }

   public boolean getOrSetBoolean(String category, String key, boolean defaultValue) {
      return this.get(category, key, defaultValue ? 1 : 0).getInt() != 0;
   }

   public void setBoolean(String category, String key, boolean value) {
      this.get(category, key, value).set(value ? 1 : 0);
   }

   public int getOrSetInt(String category, String key, int defaultValue, int minValue, int maxValue) {
      int value = this.get(category, key, defaultValue).getInt();
      return Math.min(Math.max(minValue, value), maxValue);
   }

   public void setInt(String category, String key, int value) {
      this.get(category, key, value).set(value);
   }

   public long getColour(String category, String key) {
      long value = -1L;
      if (this.hasKey(category, key)) {
         try {
            String valueString = this.get(category, key, "").getString();
            if (valueString.length() > 0) {
               value = Long.parseLong(valueString, 16);
               value &= 4294967295L;
            }
         } catch (NumberFormatException var6) {
            MinimapUtils.log("error: could not read colour from config file %s:%s", category, key);
            value = -1L;
         }
      }

      return value;
   }

   public int getColour(String category, String key, int value) {
      long valueLong = this.getColour(category, key);
      if (valueLong >= 0L) {
         value = (int)(valueLong & 4294967295L);
      }

      return value;
   }

   public int getOrSetColour(String category, String key, int value) {
      long valueLong = this.getColour(category, key);
      if (valueLong >= 0L) {
         value = (int)(valueLong & 4294967295L);
      } else {
         this.setColour(category, key, value);
      }

      return value;
   }

   public void setColour(String category, String key, int n) {
      this.get(category, key, "00000000").set(String.format("%08x", n));
   }

   public void setColour(String category, String key, int n, String comment) {
      this.get(category, key, "00000000", comment).set(String.format("%08x", n));
   }

   public String getSingleWord(String category, String key) {
      String value = "";
      if (this.hasKey(category, key)) {
         value = this.get(category, key, value).getString().trim();
         int firstSpace = value.indexOf(32);
         if (firstSpace >= 0) {
            value = value.substring(0, firstSpace);
         }
      }

      return value;
   }

   public void setSingleWord(String category, String key, String value, String comment) {
      if (comment != null && comment.length() > 0) {
         value = value + " # " + comment;
      }

      this.get(category, key, value).set(value);
   }

   public void getIntList(String category, String key, List list) {
      int size = list.size();
      int[] array = new int[size];

      for(int i = 0; i < size; ++i) {
         array[i] = ((Integer)list.get(i)).intValue();
      }

      Object var9 = null;

      int[] arrayFromConfig;
      try {
         arrayFromConfig = this.get(category, key, array).getIntList();
      } catch (Exception var8) {
         var8.printStackTrace();
         arrayFromConfig = null;
      }

      if (arrayFromConfig != null) {
         array = arrayFromConfig;
      }

      list.clear();

      for(int i = 0; i < array.length; ++i) {
         list.add(array[i]);
      }

   }

   public void setIntList(String category, String key, List list) {
      int size = list.size();
      String[] array = new String[size];

      for(int i = 0; i < size; ++i) {
         array[i] = ((Integer)list.get(i)).toString();
      }

      try {
         this.get(category, key, array).set(array);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }
}
