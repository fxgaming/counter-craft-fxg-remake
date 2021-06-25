package com.f3rullo14.fds;

import java.util.ArrayList;

public class StringListHelper {
   public static ArrayList convertListToLimitedWidth(ArrayList par1, int par2Width) {
      return getListLimitWidth(convertListToString(par1), par2Width);
   }

   public static String convertListToString(ArrayList par1) {
      String var1 = "";

      for(int i = 0; i < par1.size(); ++i) {
         var1 = var1 + " " + ((String)par1.get(i)).trim();
      }

      return var1;
   }

   public static ArrayList getListLimitWidth(String par1, int maxWidth) {
      ArrayList list = new ArrayList();
      String[] split = par1.split(" ");
      String line = "";
      int maxLineWidth = maxWidth;

      for(int i = 0; i < split.length; ++i) {
         String var1 = split[i];
         if (line.length() + var1.length() <= maxLineWidth) {
            line = line + var1 + " ";
         } else {
            list.add(new String(line));
            line = var1 + " ";
         }
      }

      if (line.length() > 0) {
         list.add(line);
      }

      return list;
   }
}
