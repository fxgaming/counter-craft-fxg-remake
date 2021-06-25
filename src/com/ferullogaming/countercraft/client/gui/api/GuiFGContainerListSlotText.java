package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.EnumChatFormatting;

public class GuiFGContainerListSlotText extends GuiFGContainerListSlot {
   private String displayText;

   public GuiFGContainerListSlotText(String par1) {
      this.displayText = par1;
   }

   public static ArrayList getListFromStrings(ArrayList par1) {
      ArrayList list = new ArrayList();
      Iterator i$ = par1.iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         GuiFGContainerListSlot slot = new GuiFGContainerListSlotText(var1);
         list.add(slot);
      }

      return list;
   }

   public boolean canSelect() {
      return this.displayText != null && !this.displayText.equals("");
   }

   public void onDoubleClick() {
      String[] split = this.displayText.split(" ");
      if (split.length >= 1) {
         String[] arr$ = split;
         int len$ = split.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String var1 = arr$[i$];
            if (var1.contains(".") && (var1.contains(".com") || var1.contains(".net") || var1.contains("https://") || var1.startsWith("http://"))) {
               this.openURL(var1);
            }
         }
      }

   }

   public void doRender(int x, int y, boolean selected, int width, int height) {
      CCRenderHelper.renderText((selected ? EnumChatFormatting.GRAY : EnumChatFormatting.WHITE) + this.displayText, x + 2, y);
   }
}
