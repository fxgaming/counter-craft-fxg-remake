package com.ferullogaming.countercraft.client.gui;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

public class StringListHelperCC {
   public static ArrayList convertListToLimitedWidth(ArrayList par1, int par2Width) {
      return getListLimitWidth(convertListToString(new ArrayList(par1)), par2Width);
   }

   public static String convertListToString(ArrayList par1) {
      String var1 = "";

      for(int i = 0; i < par1.size(); ++i) {
         var1 = var1 + " " + ((String)par1.get(i)).trim();
      }

      return var1.trim();
   }

   public static ArrayList getListLimitWidth(String par1, int maxWidth) {
      ArrayList list = new ArrayList();
      String theText = colorizeString(par1);
      String[] split = theText.split(" ");
      String line = "";
      int maxLineWidth = maxWidth;
      FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

      for(int i = 0; i < split.length; ++i) {
         String var1 = split[i];
         if (var1.equals("[br]")) {
            if (fr.getStringWidth(line) > 0) {
               list.add(new String(line));
            }

            line = "";
         } else if (!var1.equals("[nl]") && !var1.equals("++")) {
            if (fr.getStringWidth(line) + fr.getStringWidth(var1) <= maxLineWidth) {
               line = line + var1 + " ";
            } else {
               list.add(new String(line));
               line = var1 + " ";
            }
         } else {
            if (fr.getStringWidth(line) > 0) {
               list.add(new String(line));
            }

            list.add("");
            line = "";
         }
      }

      if (fr.getStringWidth(line) > 0) {
         list.add(line);
      }

      return list;
   }
   
   public static ArrayList getListLimitWidthRus(String par1, int maxWidth) {
	      ArrayList list = new ArrayList();
	      String theText = colorizeString(par1);
	      String[] split = theText.split(" ");
	      String line = "";
	      int maxLineWidth = maxWidth+(maxWidth/4);
	      FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

	      for(int i = 0; i < split.length; ++i) {
	         String var1 = split[i];
	         if (var1.equals("[br]")) {
	            if (fr.getStringWidth(line) > 0) {
	               list.add(new String(line));
	            }

	            line = "";
	         } else if (!var1.equals("[nl]") && !var1.equals("++")) {
	            if (fr.getStringWidth(line) + fr.getStringWidth(var1) <= maxLineWidth) {
	               line = line + var1 + " ";
	            } else {
	               list.add(new String(line));
	               line = var1 + " ";
	            }
	         } else {
	            if (fr.getStringWidth(line) > 0) {
	               list.add(new String(line));
	            }

	            list.add("");
	            line = "";
	         }
	      }

	      if (fr.getStringWidth(line) > 0) {
	         list.add(line);
	      }

	      return list;
	   }

   public static String colorizeString(String par1) {
      String var1 = "";
      boolean skipNext = false;

      for(int i = 0; i < par1.length(); ++i) {
         char var2 = par1.charAt(i);
         if (skipNext) {
            skipNext = false;
         } else {
            if (var2 == '&' && i != par1.length() - 1) {
               char var3 = par1.charAt(i + 1);
               boolean found = false;
               EnumChatFormatting[] arr$ = EnumChatFormatting.values();
               int len$ = arr$.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  EnumChatFormatting color = arr$[i$];
                  if (color.func_96298_a() == var3) {
                     var1 = var1 + color;
                     skipNext = true;
                     found = true;
                     break;
                  }
               }

               if (found) {
                  continue;
               }
            }

            var1 = var1 + var2;
         }
      }

      return var1;
   }
}
