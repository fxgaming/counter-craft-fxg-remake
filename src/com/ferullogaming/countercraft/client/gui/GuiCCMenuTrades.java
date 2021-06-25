package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.cloud.Trade;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuTrades extends GuiCCMenu {
   private static ArrayList slots = new ArrayList();
   private static boolean isHistory = false;
   private static int topSlot = 0;
   private static GuiFGScrollBar scrollBar;
   private int lastSize = 0;
   private int refreshDelay = 0;
   private int totalToShow = 0;
   private int showingPerPage = 2;

   public GuiCCMenuTrades() {
      isHistory = false;
   }

   public GuiCCMenuTrades(boolean par1) {
      isHistory = par1;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      scrollBar = new GuiFGScrollBar(x + 330, 50, 18, 180, 0);
      topSlot = 0;
      this.addContainer(new GuiCCContainerProfile(1, x, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Мой профиль").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x, 83, 110, 150, this));
      this.initSlots();
      super.buttonList.add(new GuiFGButton(20, x + 330, 27, 18, 19, new ResourceLocation("countercraft:textures/gui/refresh.png")));
   }

   public void initSlots() {
      PacketClientRequest.sendRequest(RequestType.PLAYER_TRADES, super.mc.getSession().getUsername());
      int x = super.width / 2 - 173;
      ArrayList trades = this.getTradeList();
      int tx = x + 117;
      int ty = 28;
      int tw = 208;
      int th = 100;
      int tym = 102;
      slots.clear();

      for(int i = 0; i < 2; ++i) {
         int i1 = i + topSlot;
         if (i1 < trades.size()) {
            GuiCCSlotTrade slot = new GuiCCSlotTrade((Trade)trades.get(i1), tx, ty + i * tym, tw, th);
            slots.add(slot);
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      if (par1GuiButton.id == 20) {
         this.initSlots();
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotTrade slot = (GuiCCSlotTrade)i$.next();
         if (slot.isMouseOver(par1, par2) && par3 == 1 && slot.trade.STATUS != 2) {
            if (slot.trade.trader1Username.equals(Minecraft.getMinecraft().getSession().getUsername())) {
               GuiCCDDMTradeClose gui = new GuiCCDDMTradeClose(this, par1 + 2, par2 + 2, 80, 12, slot.trade.tradeUUID);
               super.mc.displayGuiScreen(gui);
            } else {
               GuiCCDDMTradeAccept gui = new GuiCCDDMTradeAccept(this, par1 + 2, par2 + 2, 80, 12, slot.trade.tradeUUID);
               super.mc.displayGuiScreen(gui);
            }
         }
      }

   }

   public void updateScreen() {
      super.updateScreen();
      scrollBar.update();
      if (scrollBar.isClicked() || scrollBar.isMouseScrolling()) {
         topSlot = scrollBar.getTopSlot();
         this.initSlots();
      }

      if (this.refreshDelay++ > 5) {
         this.totalToShow = this.getTradeList().size();
         if (this.totalToShow - this.showingPerPage > 0) {
            this.totalToShow -= this.showingPerPage;
         } else {
            this.totalToShow = 0;
         }

         scrollBar.setIncrements(this.totalToShow);
         this.initSlots();
         this.refreshDelay = 0;
      }

      if (this.lastSize != this.getTradeList().size()) {
         this.initSlots();
         this.lastSize = this.getTradeList().size();
      }

   }

   private ArrayList getTradeList() {
      ArrayList list = new ArrayList(ClientManager.instance().getTradeHandler().openTrades);
      if (isHistory) {
         list = new ArrayList(ClientManager.instance().getTradeHandler().history);
      }

      Collections.reverse(list);
      return list;
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 25.0D, 237.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x + 115), 26.0D, 213.0D, 206.0D, "0x222222", 1.0F);
      scrollBar.doRender(par1, par2, par3);
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotTrade slot = (GuiCCSlotTrade)i$.next();
         slot.doRender(par1, par2, par3, false);
      }

      ArrayList trades = this.getTradeList();
      if (trades == null || trades.size() <= 0) {
         CCRenderHelper.renderCenteredTextScaledWithOutline("Никаких сделок для отображения.", x + 105 + 118, 128, 1.0D);
      }

      this.drawButtons(par1, par2, par3);
   }
}
