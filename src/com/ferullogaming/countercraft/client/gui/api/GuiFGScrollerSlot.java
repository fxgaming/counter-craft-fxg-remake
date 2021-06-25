package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public abstract class GuiFGScrollerSlot {
   protected final List buttons = new ArrayList();
   public GuiFGScroller scroller;
   public int posX;
   public int posY;
   int index;

   final void init(GuiFGScroller scroller, int index, int posX, int posY) {
      this.scroller = scroller;
      this.index = index;
      this.posX = posX;
      this.posY = posY;
      this.init();
   }

   protected void init() {
      this.buttons.clear();
   }

   public GuiButton addButton(GuiButton button) {
      this.buttons.add(button);
      return button;
   }

   public abstract boolean canSelect();

   protected void actionPerformed(GuiButton button) {
   }

   public void clicked(int mouseX, int mouseY) {
      Buttons.click(this.buttons, mouseX, mouseY, this.scroller.parentGUI);
   }

   public final boolean isSelected() {
      return this.scroller.isSelected(this);
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return CCRenderHelper.isInBox(this.posX, this.posY, this.scroller.width, this.height(), mouseX, mouseY);
   }

   protected void onClose() {
   }

   public void preRenderCallback(int mouseX, int mouseY) {
   }

   public void doRender(int mouseX, int mouseY) {
      this.drawBackground(mouseX, mouseY);
      Buttons.draw(this.buttons, mouseX, mouseY);
   }

   public void renderNoClip(int x, int y, int mouseX, int mouseY) {
   }

   protected void drawBackground(int mouseX, int mouseY) {
      int color;
      if (this.isDisabled()) {
         color = -12320768;
      } else if (this.isSelected()) {
         color = 436272896;
      } else if (this.isHovered(mouseX, mouseY)) {
         color = 570425343;
      } else {
         color = 1862270976;
      }

      Gui.drawRect(this.posX + this.paddingLeft(), this.posY + this.paddingTop(), this.posX + this.scroller.width - this.paddingRight(), this.posY + this.height() - this.paddingBottom(), color);
   }

   protected int paddingLeft() {
      return 2;
   }

   protected int paddingRight() {
      return 20;
   }

   protected int paddingTop() {
      return 2;
   }

   protected int paddingBottom() {
      return 2;
   }

   public boolean isDisabled() {
      return false;
   }

   protected abstract int height();
}
