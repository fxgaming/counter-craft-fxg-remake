package com.ferullogaming.countercraft.client.gui.api;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class GuiFGContainerListSlot {
   public int posX;
   public int posY;

   public void onUpdate() {
   }

   public void onDoubleClick() {
   }

   public boolean canSelect() {
      return true;
   }

   public void doRender(int x, int y, boolean selected, int width, int height) {
   }

   protected void openURL(String par1) {
      try {
         if (Desktop.isDesktopSupported()) {
            try {
               Desktop.getDesktop().browse(new URI(par1));
            } catch (URISyntaxException var3) {
               var3.printStackTrace();
            }
         }
      } catch (MalformedURLException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
