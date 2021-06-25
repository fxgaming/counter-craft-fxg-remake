package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.Trade;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCSlotTrade {
   public Trade trade;
   public int x;
   public int y;
   public int width;
   public int height;
   public ArrayList trader1Offer = new ArrayList();
   public ArrayList trader2Offer = new ArrayList();

   public GuiCCSlotTrade(Trade par1, int par2, int par3, int par4, int par5) {
      this.trade = par1;
      this.x = par2;
      this.y = par3;
      this.width = par4;
      this.height = par5;
      this.loadOffer(this.trade.offer1, this.trader1Offer, this.x + this.width - 124, this.y + 2);
      this.loadOffer(this.trade.offer2, this.trader2Offer, this.x + this.width - 124, this.y + this.height - 42);
   }

   public void doRender(int par1, int par2, float par3, boolean par4) {
      if (this.isMouseOver(par1, par2)) {
         CCRenderHelper.drawRect((double)(this.x - 1), (double)(this.y - 1), (double)(this.width + 2), (double)(this.height + 2), "0xffba00", 1.0F);
      }

      CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.GOLD + this.trade.trader1Username, this.x + 2, this.y + 4);
      CCRenderHelper.renderTextScaled("Создатель трейда", this.x + 2, this.y + 14, 0.7D);
      CCRenderHelper.renderTextScaled(EnumChatFormatting.AQUA + "Trade ID", this.x + 2, this.y + 24, 0.7D);
      CCRenderHelper.renderTextScaled(EnumChatFormatting.AQUA + "" + this.trade.tradeUUID, this.x + 2, this.y + 31, 0.7D);
      Iterator i$ = this.trader1Offer.iterator();

      GuiCCSlotInventory slot;
      while(i$.hasNext()) {
         slot = (GuiCCSlotInventory)i$.next();
         slot.doRender(par1, par2, par3);
      }

      CCRenderHelper.renderTextScaled("Создано:", this.x + 4, this.y + 44, 0.7D);
      if (this.trade.dateMade != null && this.trade.dateMade.length() > 0) {
         CCRenderHelper.renderTextScaled(this.trade.dateMade, this.x + 40, this.y + 44, 0.7D);
      }

      CCRenderHelper.drawRect((double)(this.x + 4), (double)(this.y + this.height / 2), (double)(this.width - 8), 1.0D, "0xffffff", 1.0F);
      CCRenderHelper.renderText(this.trade.trader2Username, this.x + 2, this.y + 60);
      i$ = this.trader2Offer.iterator();

      while(i$.hasNext()) {
         slot = (GuiCCSlotInventory)i$.next();
         slot.doRender(par1, par2, par3);
      }

      CCRenderHelper.renderTextScaled("Закрыто:", this.x + 4, this.y + 52, 0.7D);
      if (this.trade.dateEnded != null && this.trade.dateEnded.length() > 0) {
         CCRenderHelper.renderTextScaled(this.trade.dateEnded, this.x + 40, this.y + 52, 0.7D);
      }

      if (this.trade.STATUS == 2) {
         CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, GuiCCMenu.menuTheme, 0.3F);
         CCRenderHelper.renderTextScaled("ЗАКРЫТО", this.x + 2, this.y + 74, 2.0D);
         if (this.trade.closedState != 0) {
            String state = "Принято";
            if (this.trade.closedState == 2) {
               state = "Отказано";
            }

            if (this.trade.closedState == 3) {
               state = "Ошибка процесса";
            }

            CCRenderHelper.renderText(state, this.x + 2, this.y + 90);
         }
      }

   }

   public boolean isMouseOver(int x, int y) {
      Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
      return rect.contains(x, y);
   }

   public void loadOffer(ArrayList par1, ArrayList par2, int x, int y) {
      par2.clear();
      int x1 = 41;

      for(int i = 0; i < par1.size(); ++i) {
         GuiCCSlotInventory slot = new GuiCCSlotInventory(x + i * x1, y, (CloudItemStack)par1.get(i), 40, 40, false);
         par2.add(slot);
      }

   }
}
