package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import java.awt.Rectangle;
import net.minecraft.client.gui.GuiButton;

public class GuiFGContainer {
   public int containerID;
   public float colorOpacity = 1.0F;
   public int posX;
   public int posY;
   public int width;
   public int height;
   public GuiFGScreen parentGUI;
   public boolean shouldRenderBackground = true;
   protected String color;

   public GuiFGContainer(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      this.color = GuiCCMenu.menuTheme;
      this.containerID = par1;
      this.posX = par2;
      this.posY = par3;
      this.width = par4;
      this.height = par5;
      this.parentGUI = par6;
   }

   public GuiFGContainer setColor(String par1) {
      this.color = par1;
      return this;
   }

   public void initGui() {
   }

   public void actionPerformed(GuiButton par1GuiButton) {
   }

   public void mouseClicked(int par1, int par2, int par3) {
   }

   public void updateScreen() {
   }

   public void handleScroll(int mouseX, int mouseY, int dWheel) {
   }

   public void mouseReleased(int mouseX, int mouseY) {
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground();
   }

   public void drawBackground() {
      if (this.shouldRenderBackground) {
         CCRenderHelper.drawRectWithShadow((double)this.posX, (double)this.posY, (double)this.width, (double)this.height, this.color, 1.0F);
      }

   }

   public Rectangle getRectange() {
      return new Rectangle(this.posX, this.posY, this.width, this.height);
   }

   public boolean isMouseOver(int par1, int par2) {
      return this.getRectange().contains(par1, par2);
   }

   public void onClose() {
   }
}
