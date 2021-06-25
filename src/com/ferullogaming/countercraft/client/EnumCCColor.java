package com.ferullogaming.countercraft.client;

import java.util.ArrayList;

public enum EnumCCColor {
   DEFAULT(0, 15188107),
   RED(1, 14174020),
   ORANGE(2, 16757525),
   YELLOW(3, 16776960),
   GREEN(4, 1617219),
   BLUE(5, 33514),
   PURPLE(6, 12189951),
   PINK(7, 16731091),
   WHITE(8, 16777215);

   public int colorID = 0;
   public int colorStr = 15188107;

   private EnumCCColor(int par1, int par2) {
      this.colorID = par1;
      this.colorStr = par2;
   }

   public static EnumCCColor getColorFromID(int par1) {
      for(int i = 0; i < values().length; ++i) {
         if (values()[i].colorID == par1) {
            return values()[i];
         }
      }

      return null;
   }

   public static ArrayList getColorList() {
      ArrayList list = new ArrayList();

      for(int i = 0; i < values().length; ++i) {
         list.add(values()[i]);
      }

      return list;
   }
}
