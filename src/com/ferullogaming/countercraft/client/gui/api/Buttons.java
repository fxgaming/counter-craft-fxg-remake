package com.ferullogaming.countercraft.client.gui.api;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Buttons {
   public static void draw(List buttons, int mouseX, int mouseY) {
      Minecraft mc = Minecraft.getMinecraft();
      Iterator i$ = buttons.iterator();

      while(i$.hasNext()) {
         GuiButton button = (GuiButton)i$.next();
         button.drawButton(mc, mouseX, mouseY);
      }

   }

   public static void click(List buttons, int mouseX, int mouseY, GuiFGScreen parentGUI) {
      Minecraft mc = Minecraft.getMinecraft();
      Iterator i$ = buttons.iterator();

      GuiButton button;
      do {
         if (!i$.hasNext()) {
            return;
         }

         button = (GuiButton)i$.next();
      } while(!button.mousePressed(mc, mouseX, mouseY));

      parentGUI.actionPerformed(button);
   }
}
