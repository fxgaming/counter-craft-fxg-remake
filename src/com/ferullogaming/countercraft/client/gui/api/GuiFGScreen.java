package com.ferullogaming.countercraft.client.gui.api;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

public class GuiFGScreen extends GuiScreen {
   protected ArrayList guiContainers = new ArrayList();

   public void initGui() {
      this.guiContainers.clear();
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      this.addContainerAction(par1GuiButton);
   }

   public void updateScreen() {
      this.addContainerUpdate();
   }

   public void onGuiClosed() {
   }

   public void addContainer(GuiFGContainer par1) {
      par1.initGui();
      this.guiContainers.add(par1);
   }

   public void addContainerUpdate() {
      Iterator i$ = this.guiContainers.iterator();

      while(i$.hasNext()) {
         GuiFGContainer gui = (GuiFGContainer)i$.next();
         gui.updateScreen();
      }

   }

   public void addContainerAction(GuiButton par1GuiButton) {
      Iterator i$ = this.guiContainers.iterator();

      while(i$.hasNext()) {
         GuiFGContainer gui = (GuiFGContainer)i$.next();
         gui.actionPerformed(par1GuiButton);
      }

   }

   public void addContainerDrawing(int par1, int par2, float par3) {
      Iterator i$ = this.guiContainers.iterator();

      while(i$.hasNext()) {
         GuiFGContainer gui = (GuiFGContainer)i$.next();
         gui.drawScreen(par1, par2, par3);
      }

   }

   public GuiFGContainer getContainer(int par1) {
      Iterator i$ = this.guiContainers.iterator();

      GuiFGContainer cont;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         cont = (GuiFGContainer)i$.next();
      } while(cont.containerID != par1);

      return cont;
   }

   public boolean doesGuiPauseGame() {
      return true;
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = this.guiContainers.iterator();

      while(i$.hasNext()) {
         GuiFGContainer gui = (GuiFGContainer)i$.next();
         gui.mouseClicked(par1, par2, par3);
      }

   }

   protected void mouseMovedOrUp(int par1, int par2, int par3) {
      super.mouseMovedOrUp(par1, par2, par3);
      Iterator i$ = this.guiContainers.iterator();

      while(i$.hasNext()) {
         GuiFGContainer container = (GuiFGContainer)i$.next();
         container.mouseReleased(par1, par2);
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      this.addContainerDrawing(par1, par2, par3);
   }

   public void handleMouseInput() {
      super.handleMouseInput();
      int dWheel = Mouse.getEventDWheel();
      if (dWheel != 0) {
         int mouseX = Mouse.getEventX() * super.width / super.mc.displayWidth;
         int mouseY = super.height - Mouse.getEventY() * super.height / super.mc.displayHeight - 1;
         Iterator i$ = this.guiContainers.iterator();

         while(i$.hasNext()) {
            GuiFGContainer container = (GuiFGContainer)i$.next();
            container.handleScroll(mouseX, mouseY, dWheel);
         }
      }

   }
}
