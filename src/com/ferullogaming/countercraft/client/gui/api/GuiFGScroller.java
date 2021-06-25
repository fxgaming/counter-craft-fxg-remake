package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.GuiCCScrollerTextSlot;
import com.ferullogaming.countercraft.client.gui.GuiCCSlotImage;
import com.ferullogaming.countercraft.client.gui.GuiCCSlotLiveEvent;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class GuiFGScroller extends GuiFGContainer {
   public static final int SCROLLBAR_HEIGHT = 20;
   public static final int SCROLLBAR_WIDTH = 6;
   public static final int SCROLLBAR_Y_PAD = 2;
   public ArrayList slots = new ArrayList();
   private int scrollbarY;
   private int scrollbarMinY;
   private int scrollbarMaxY;
   private int scrollbarX;
   private int smoothScrollTargetY = -1;
   private int selectedSlot = -1;
   private int totalHeight;
   private boolean scrollerClicked = false;
   private int scrollerClickMouseOffset;

   public GuiFGScroller(int containerID, int posX, int posY, int width, int height, GuiFGScreen parentGUI) {
      super(containerID, posX, posY, width, height, parentGUI);
   }

   public void initGui() {
      super.initGui();
      this.scrollbarMinY = super.posY + 4;
      this.scrollbarMaxY = this.scrollbarMinY;
      this.scrollbarY = this.scrollbarMinY;
      this.scrollbarX = super.posX + super.width - 10;
      this.onSlotHeightChanged();
   }

   public void onSlotHeightChanged() {
      int currentHeight = 0;
      int i = 0;

      for(int n = this.slots.size(); i < n; ++i) {
         GuiFGScrollerSlot slot = (GuiFGScrollerSlot)this.slots.get(i);
         slot.init(this, i, super.posX, super.posY + currentHeight);
         currentHeight += slot.height();
      }

      this.totalHeight = currentHeight;
      if (this.totalHeight > super.height) {
         this.scrollbarMaxY = super.posY + super.height - 4 - 20;
      } else {
         this.scrollbarMaxY = this.scrollbarMinY;
      }

   }

   public void scrollTo(int y) {
      int maxTranslate = this.totalHeight - super.height;
      float translate = (float)Math.min(maxTranslate, y - super.posY);
      this.smoothScrollTargetY = this.clampToScrollbar(this.scrollbarMinY + (int)(translate / (float)maxTranslate * (float)(this.scrollbarMaxY - this.scrollbarMinY)));
   }

   public void scrollIntoView(GuiFGScrollerSlot slot) {
      this.scrollTo(slot.posY);
   }

   private void updateScrollPosition(int newPos) {
      this.scrollbarY = this.clampToScrollbar(newPos);
   }

   private int clampToScrollbar(int newPos) {
      return MathHelper.clamp_int(newPos, this.scrollbarMinY, this.scrollbarMaxY);
   }

   private int getActualScrollbarPos() {
      return this.smoothScrollTargetY == -1 ? this.scrollbarY : this.smoothScrollTargetY;
   }

   public void handleScroll(int mouseX, int mouseY, int dWheel) {
      super.handleScroll(mouseX, mouseY, dWheel);
      if (!this.scrollerClicked && !this.slots.isEmpty() && CCRenderHelper.isInBox(super.posX, super.posY, super.width, super.height, mouseX, mouseY)) {
         this.smoothScrollTargetY = this.clampToScrollbar((int)((float)this.getActualScrollbarPos() - (float)dWheel / (float)this.totalHeight * 30.0F));
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      int translate = this.getSlotYTranslation();
      int i = 0;

      for(int n = this.slots.size(); i < n; ++i) {
         GuiFGScrollerSlot slot = (GuiFGScrollerSlot)this.slots.get(i);
         if (CCRenderHelper.isInBox(slot.posX, slot.posY - translate, super.width, slot.height(), mouseX, mouseY)) {
            slot.clicked(mouseX, mouseY + translate);
         }
      }

      if (CCRenderHelper.isInBox(this.scrollbarX, this.scrollbarY, 6, 20, mouseX, mouseY)) {
         this.smoothScrollTargetY = -1;
         this.scrollerClickMouseOffset = mouseY - this.scrollbarY;
         this.scrollerClicked = true;
      }

   }

   public void mouseReleased(int mouseX, int mouseY) {
      super.mouseReleased(mouseX, mouseY);
      this.scrollerClicked = false;
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.scrollerClicked) {
         this.updateScrollPosition(mouseY - this.scrollerClickMouseOffset);
      } else if (this.smoothScrollTargetY != -1) {
         double amnt = (double)(this.smoothScrollTargetY - this.scrollbarY) / 6.0D;
         this.updateScrollPosition((int)((double)this.scrollbarY + (amnt <= 0.0D ? Math.floor(amnt) : Math.ceil(amnt))));
         if (this.scrollbarY == this.smoothScrollTargetY) {
            this.smoothScrollTargetY = -1;
         }
      }

      try {
         Iterator i$ = this.slots.iterator();

         while(i$.hasNext()) {
            GuiFGScrollerSlot slot = (GuiFGScrollerSlot)i$.next();
            slot.preRenderCallback(mouseX, mouseY);
         }
      } catch (ConcurrentModificationException var8) {
         System.out.println("There was an error launching the Gui Scroller!");
      }

      this.drawBackground();
      this.renderScrollbar();
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      int factor = sr.getScaleFactor();
      GL11.glPushMatrix();
      int translation = this.getSlotYTranslation();
      GL11.glTranslatef(0.0F, (float)(-translation), 0.0F);
      GL11.glEnable(3089);
      GL11.glScissor(super.posX * factor, (super.parentGUI.height - (super.posY + super.height - 4)) * factor - 8, super.width * factor - 30, (super.height - 8) * factor + 15);

      int i;
      for(i = 0; i < this.slots.size(); ++i) {
         ((GuiFGScrollerSlot)this.slots.get(i)).doRender(mouseX, mouseY + translation);
      }

      GL11.glDisable(3089);

      for(i = 0; i < this.slots.size(); ++i) {
         ((GuiFGScrollerSlot)this.slots.get(i)).renderNoClip(((GuiFGScrollerSlot)this.slots.get(i)).posX, ((GuiFGScrollerSlot)this.slots.get(i)).posY, mouseX, mouseY);
      }

      GL11.glPopMatrix();
   }

   private int getSlotYTranslation() {
      int maxTranslate = this.totalHeight - super.height;
      return (int)((float)(this.scrollbarY - this.scrollbarMinY) / (float)(this.scrollbarMaxY - this.scrollbarMinY) * (float)maxTranslate);
   }

   private void renderScrollbar() {
      if (this.totalHeight > super.height) {
         CCRenderHelper.drawRect((double)(this.scrollbarX - 2), (double)(super.posY + 2), 10.0D, (double)(super.height - 4), "0x000000", 0.5F);
         CCRenderHelper.drawRect((double)this.scrollbarX, (double)this.scrollbarY, 6.0D, 20.0D, "0xFFFFFF", 0.3F);
      }

   }

   boolean isSelected(GuiFGScrollerSlot slot) {
      return slot.index == this.selectedSlot;
   }

   public void setSlotList(Iterator newSlots) {
      this.slots.clear();

      try {
         Iterators.addAll(this.slots, newSlots);
         this.onSlotHeightChanged();
      } catch (ConcurrentModificationException var3) {
         throw var3;
      }
   }

   public void onClose() {
      Iterator i$ = this.slots.iterator();

      while(i$.hasNext()) {
         GuiFGScrollerSlot slot = (GuiFGScrollerSlot)i$.next();
         slot.onClose();
      }

   }

   public void setTextList(List list) {
      this.slots.clear();
      Iterator i$ = list.iterator();

      while(true) {
         while(true) {
            String text;
            do {
               if (!i$.hasNext()) {
                  return;
               }

               text = (String)i$.next();
            } while(text.startsWith("//"));

            String[] split;
            String userName;
            if (text.contains("<image>")) {
               split = text.replace("<image>", "").split("<>");
               userName = split[0];
               int imageWidth = Integer.parseInt(split[1]);
               int imageHeight = Integer.parseInt(split[2]);
               GuiCCSlotImage imageSlot = new GuiCCSlotImage(userName, imageWidth, imageHeight);
               imageSlot.scroller = this;
               this.slots.add(imageSlot);
               this.onSlotHeightChanged();
            } else {
               GuiCCScrollerTextSlot newSlot;
               if (text.contains("<ad>")) {
                  PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
                  if (cloudData == null || !cloudData.isSupporter) {
                     text = text.replace("<ad>", "");
                     newSlot = new GuiCCScrollerTextSlot(text);
                     newSlot.scroller = this;
                     this.slots.add(newSlot);
                     this.onSlotHeightChanged();
                  }
               } else {
                  String streamDesc;
                  if (text.contains("<link>")) {
                     text = text.replace("<link>", "");
                     split = text.split("<>");
                     userName = split[0];
                     streamDesc = split[1];
                     GuiCCScrollerTextSlot newSlot1 = (new GuiCCScrollerTextSlot(userName)).setUrl(streamDesc);
                     newSlot1.scroller = this;
                     this.slots.add(newSlot1);
                     this.onSlotHeightChanged();
                  } else if (text.contains("<live>")) {
                     split = text.replace("<live>", "").split("<>");
                     userName = split[0];
                     streamDesc = split[1];
                     String liveUrl = split[2];
                     int imageWidth = Integer.parseInt(split[3]);
                     int imageHeight = Integer.parseInt(split[4]);
                     GuiCCSlotLiveEvent imageSlot = new GuiCCSlotLiveEvent(userName, streamDesc, liveUrl, imageWidth, imageHeight);
                     imageSlot.scroller = this;
                     this.slots.add(imageSlot);
                     this.onSlotHeightChanged();
                  } else {
                     boolean renderSmall = false;
                     if (text.startsWith("<s>")) {
                        renderSmall = true;
                        text = text.replace("<s>", "");
                     }

                     newSlot = new GuiCCScrollerTextSlot(text);
                     if (renderSmall) {
                        newSlot.renderSmall();
                     }

                     newSlot.scroller = this;
                     this.slots.add(newSlot);
                     this.onSlotHeightChanged();
                  }
               }
            }
         }
      }
   }
}
