package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.MarketCategoryHandler;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketBrowseSearch;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketBrowseSearchResult;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuMarketBrowse extends GuiCCMenu {
   public static String filter1 = "";
   public static String filter2 = "";
   public static String filter3 = "";
   public static boolean isSearching = false;
   private static ArrayList slots = new ArrayList();
   private static ArrayList tempList = new ArrayList();
   private static int topSlot = 0;
   private static int refreshDelay = 0;
   private static GuiFGButton priceFilter;
   private static int searchTime = 0;
   private static GuiFGScrollBar scrollBar;
   private int totalToShow = 0;
   private int showingPerPage = 6;
   private int lastClickY;

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      this.performSearch();
      super.buttonList.add(new GuiFGButton(20, x + 330, 27, 18, 19, new ResourceLocation("countercraft:textures/gui/refresh.png")));
      scrollBar = new GuiFGScrollBar(x + 330, 50, 18, 180, 0);
      topSlot = 0;
      int dy = 40;
      super.buttonList.add(new GuiFGButton(30, x + 75, 72 + dy, 30, 15, "Edit"));
      super.buttonList.add(new GuiFGButton(31, x + 75, 98 + dy, 30, 15, "Edit"));
      super.buttonList.add(new GuiFGButton(32, x + 75, 124 + dy, 30, 15, "Edit"));
      super.buttonList.add(new GuiFGButton(33, x + 5, 65, 100, 20, "My Listings"));
      super.buttonList.add(new GuiFGButton(34, x + 5, 187, 100, 20, "Clear Search"));
      super.buttonList.add(new GuiFGButton(35, x + 5, 210, 100, 20, "Search"));
      this.initSlots();
   }

   public void initSlots() {
      int x = super.width / 2 - 171;
      tempList.clear();
      tempList.addAll(PacketMarketBrowseSearchResult.getLastSearchResults());
      int tx = x + 118;
      int ty = 47;
      int tw = 209;
      int th = 30;
      int tym = 31;
      slots.clear();

      for(int i = 0; i < 6; ++i) {
         int i1 = i + topSlot;
         if (i1 < tempList.size()) {
            GuiCCSlotMarketResult slot = new GuiCCSlotMarketResult((MarketCategoryHandler)tempList.get(i1), tx, ty + i * tym, tw, th);
            slots.add(slot);
         }
      }

   }

   public void performSearch() {
      if (!isSearching) {
         PacketMarketBrowseSearch packet = new PacketMarketBrowseSearch(new String[]{filter1, filter2, filter3});
         ClientCloudManager.sendPacket(packet);
         isSearching = true;
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      int x = super.width / 2 - 172;
      if (par1GuiButton.id == 30) {
         super.mc.displayGuiScreen(new GuiCCDDMMarketBrowseFilter(this, x + 114, 25, 90, 15));
      }

      if (par1GuiButton.id == 31 && filter1.length() > 0) {
         super.mc.displayGuiScreen(new GuiCCDDMMarketBrowseFilter2(this, x + 114, 25, 90, 15, filter1));
      }

      if (par1GuiButton.id == 32 && filter2.length() > 0) {
         super.mc.displayGuiScreen(new GuiCCDDMMarketBrowseFilter3(this, x + 114, 25, 90, 15, filter2));
      }

      if (par1GuiButton.id == 33) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketMyListings());
      }

      if (par1GuiButton.id == 34) {
         filter1 = "";
         filter2 = "";
         filter3 = "";
         this.performSearch();
         this.initSlots();
      }

      if (par1GuiButton.id == 35) {
         this.performSearch();
         topSlot = 0;
         scrollBar.resetScroller();
         this.initSlots();
      }

      if (par1GuiButton.id == 20) {
         this.performSearch();
         this.initSlots();
      }

   }

   public void updateScreen() {
      super.updateScreen();
      scrollBar.update();
      if (topSlot > tempList.size()) {
         topSlot = 0;
         scrollBar.resetScroller();
         this.initSlots();
      }

      if (scrollBar.isClicked() || scrollBar.isMouseScrolling()) {
         topSlot = scrollBar.getTopSlot();
         this.initSlots();
      }

      if (refreshDelay++ > 5) {
         this.totalToShow = tempList.size();
         if (this.totalToShow - this.showingPerPage > 0) {
            this.totalToShow -= this.showingPerPage;
         } else {
            this.totalToShow = 0;
         }

         scrollBar.setIncrements(this.totalToShow);
         this.initSlots();
         refreshDelay = 0;
      }

      if (isSearching) {
         ++searchTime;
         if (searchTime > 60) {
            isSearching = false;
            searchTime = 0;
         }
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotMarketResult slot = (GuiCCSlotMarketResult)i$.next();
         if (slot.isMouseOver(par1, par2) && par3 == 0) {
            GuiCCMenuMarketCategory gui = new GuiCCMenuMarketCategory(this, slot.listing);
            super.mc.displayGuiScreen(gui);
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
         }
      }

   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 172;
      CCRenderHelper.drawRectWithShadow((double)x, 25.0D, 110.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      int ey = 16;
      CCRenderHelper.drawRect((double)(x + 1), (double)(26 + ey), 108.0D, 17.0D, GuiCCMenu.menuTheme2, 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.GREEN + "Emeralds: ", x + 4, 31 + ey);
      CCRenderHelper.drawImage((double)(x + 50), (double)(29 + ey), CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText(EnumChatFormatting.GREEN + "" + PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername()).emeralds, x + 62, 31 + ey);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Market", x + 55, 30);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Filters", x + 55, 95);
      int dy = 40;
      int var1 = 68 + dy;
      CCRenderHelper.drawRectWithShadow((double)(x + 2), (double)var1, 106.0D, 23.0D, GuiCCMenu.menuTheme2, 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "Type Filter", x + 5, var1 + 2);
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + (filter1.length() > 0 ? filter1 : "None"), x + 5, var1 + 2 + 10);
      var1 = 94 + dy;
      CCRenderHelper.drawRectWithShadow((double)(x + 2), (double)var1, 106.0D, 23.0D, GuiCCMenu.menuTheme2, 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "Item Filter", x + 5, var1 + 2);
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + (filter2.length() > 0 ? filter2 : "None"), x + 5, var1 + 2 + 10);
      var1 = 120 + dy;
      CCRenderHelper.drawRectWithShadow((double)(x + 2), (double)var1, 106.0D, 23.0D, GuiCCMenu.menuTheme2, 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "Suffix Filter", x + 5, var1 + 2);
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + (filter3.length() > 0 ? filter3 : "None"), x + 5, var1 + 2 + 10);
      CCRenderHelper.drawRectWithShadow((double)(x + 113), 25.0D, 237.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      scrollBar.doRender(par1, par2, par3);
      CCRenderHelper.drawRect((double)(x + 119), 27.0D, 209.0D, 18.0D, GuiCCMenu.menuTheme2, 1.0F);
      CCRenderHelper.renderText("Item Category", x + 124, 33);
      CCRenderHelper.drawRect((double)(x + 250), 27.0D, 1.0D, 18.0D, "0xffffff", 1.0F);
      CCRenderHelper.renderText("Stock", x + 257, 33);
      CCRenderHelper.drawRect((double)(x + 290), 27.0D, 1.0D, 18.0D, "0xffffff", 1.0F);
      CCRenderHelper.drawImage((double)(x + 302), 28.0D, CCRenderHelper.RES_EMERALD_LOW, 16.0D, 16.0D);
      if (isSearching) {
         CCRenderHelper.renderCenteredText("Updating Search...", x + 230, 50);
      }

      if (tempList.size() == 0 && !isSearching) {
         CCRenderHelper.renderCenteredText("No Results Found", x + 230, 50);
      }

      if (!isSearching) {
         for(int i = 0; i < slots.size(); ++i) {
            ((GuiCCSlotMarketResult)slots.get(i)).doRender(par1, par2, par3);
            if (((GuiCCSlotMarketResult)slots.get(i)).isMouseOver(par1, par2)) {
               ((GuiCCSlotMarketResult)slots.get(i)).doRenderHighlight(1);
            }
         }
      }

      this.drawButtons(par1, par2, par3);
   }
}
