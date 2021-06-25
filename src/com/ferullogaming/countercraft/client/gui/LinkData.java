package com.ferullogaming.countercraft.client.gui;

import java.awt.Rectangle;
import net.minecraft.client.Minecraft;

public class LinkData {
   public String message;
   public String linkURL;
   public Rectangle hitBox;
   public boolean isMosueOver = false;
   private Minecraft mc = Minecraft.getMinecraft();

   public LinkData(String par1Mes, String par1, int x, int y) {
      this.message = par1Mes;
      this.linkURL = par1;
      this.hitBox = new Rectangle(x + 1, y + 2, 40, 6);
   }
}
