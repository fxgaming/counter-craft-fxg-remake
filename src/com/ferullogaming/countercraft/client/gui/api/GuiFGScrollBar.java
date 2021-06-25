package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import java.awt.Rectangle;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiFGScrollBar {
   private int posX;
   private int posY;
   private int width;
   private int height;
   private int mouseX;
   private int mouseY;
   private Rectangle scroller = new Rectangle();
   private boolean isClicked = false;
   private int isMouseScrolling = 0;
   private int maxY = 0;
   private int minY = 0;
   private int minMaxDistance = 0;
   private int posYOnClickedBox = 0;
   private int scrollPercentage = 0;
   private int scrollIncrements = 0;
   private int currentIncrement = 0;
   private int scrollYAdjustment = 0;
   private ResourceLocation scrollerTexture = new ResourceLocation("countercraft:textures/gui/scroller.png");

   public GuiFGScrollBar(int par1, int par2, int par3, int par4, int par5Adjustment) {
      this.posX = par1;
      this.posY = par2;
      this.width = par3;
      this.height = par4;
      this.maxY = par2 + 2;
      this.minY = par2 + par4 - 34;
      this.minMaxDistance = this.minY - this.maxY;
      this.scroller = new Rectangle(par1 + 2, par2 + 2, par3 - 4, 32);
      this.scrollYAdjustment = par5Adjustment;
   }

   public void setIncrements(int par1) {
      this.scrollIncrements = par1;
   }

   public void resetScroller() {
      this.scroller.y = this.posY + 2;
   }

   public void update() {
      if (this.isMouseScrolling > 0) {
         --this.isMouseScrolling;
      }

      if (Mouse.isButtonDown(0)) {
         if (this.scroller.contains(this.mouseX, this.mouseY)) {
            this.isClicked = true;
         }
      } else {
         this.isClicked = false;
      }

      int yPer;
      if (this.isClicked) {
         yPer = this.mouseY - 16;
         if (yPer < this.maxY) {
            yPer = this.maxY;
         }

         if (yPer > this.minY) {
            yPer = this.minY;
         }

         this.scroller = new Rectangle(this.scroller.x, yPer, this.scroller.width, this.scroller.height);
      }

      yPer = this.scroller.y * 100 / this.minMaxDistance - (this.posY - 14);
      yPer -= this.scrollYAdjustment;
      this.scrollPercentage = yPer;
      if (this.scrollIncrements > 0) {
         if (yPer > 98) {
            yPer = 100;
         }

         this.currentIncrement = Math.round((float)(this.scrollIncrements * yPer / 100));
      }

   }

   public void updateMouseScroll() {
      if (!Mouse.isButtonDown(0)) {
         if (!Mouse.isButtonDown(1)) {
            int l2;
            while(Mouse.next() && (l2 = Mouse.getEventDWheel()) != 0) {
               this.isMouseScrolling = 5;
               if (l2 > 0) {
                  l2 = -1;
               } else if (l2 < 0) {
                  l2 = 1;
               }

               int newY = this.scroller.y + l2 * 10;
               if (newY < this.maxY) {
                  newY = this.maxY;
               }

               if (newY > this.minY) {
                  newY = this.minY;
               }

               this.scroller = new Rectangle(this.scroller.x, newY, this.scroller.width, this.scroller.height);
               int yPer = this.scroller.y * 100 / this.minMaxDistance - (this.posY - 14);
               yPer -= this.scrollYAdjustment;
               this.scrollPercentage = yPer;
               if (this.scrollIncrements > 0) {
                  if (yPer > 98) {
                     yPer = 100;
                  }

                  this.currentIncrement = Math.round((float)(this.scrollIncrements * yPer / 100));
               }
            }

         }
      }
   }

   public void setScrollerMaxed() {
      this.scroller.y = this.minY;
   }

   public int getScrollPercentage() {
      return this.scrollPercentage;
   }

   public int getTopSlot() {
      return this.currentIncrement;
   }

   public boolean isClicked() {
      return this.isClicked;
   }

   public boolean isMouseScrolling() {
      return this.isMouseScrolling > 0;
   }

   public void doRender(int par1, int par2, float par3) {
      this.mouseX = par1;
      this.mouseY = par2;
      this.updateMouseScroll();
      CCRenderHelper.drawRect((double)this.posX, (double)this.posY, (double)this.width, (double)this.height, "0x00000", 1.0F);
      CCRenderHelper.drawRect((double)this.scroller.x, (double)this.scroller.y, (double)this.scroller.width, (double)this.scroller.height, "0xffffff", 1.0F);
   }
}
