package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.MarketCategoryHandler;
import com.ferullogaming.countercraft.client.cloud.PlayerListing;
import com.ferullogaming.countercraft.client.cloud.StoreManager;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketRequestCategory;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuInspect;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import com.ferullogaming.countercraft.client.gui.store.GuiCCMenuStore;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuMarketCategory extends GuiCCMenu {
   public static MarketCategoryHandler lastCategory = null;
   public static boolean isRequesting = false;
   private static ArrayList slots = new ArrayList();
   private static int topSlot = 0;
   private static int refreshDelay = 0;
   private static GuiCCSlotInventory displayStack;
   private static GuiFGScrollBar scrollBar;
   private GuiScreen parentGui;
   private int totalToShow = 0;
   private int showingPerPage = 6;

   public GuiCCMenuMarketCategory(GuiScreen par1, MarketCategoryHandler par2) {
      this.parentGui = par1;
      topSlot = 0;
      lastCategory = new MarketCategoryHandler(par2.getItemID());
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      super.buttonList.add(new GuiFGButton(20, x + 330, 27, 18, 19, new ResourceLocation("countercraft:textures/gui/refresh.png")));
      scrollBar = new GuiFGScrollBar(x + 330, 50, 18, 180, 0);
      topSlot = 0;
      super.buttonList.add(new GuiFGButton(40, x + 5, 205, 100, 20, "Back"));
      if (StoreManager.instance().isStoreItemFromCloudID(lastCategory.getItemID())) {
         GuiFGButton button = new GuiFGButton(42, x + 122, 30, 50, 12, EnumChatFormatting.ITALIC + "Buy Now");
         button.drawBackground = true;
         super.buttonList.add(button);
      }

      if (lastCategory != null && !isRequesting) {
         PacketMarketRequestCategory packet = new PacketMarketRequestCategory(lastCategory.getItemID());
         ClientCloudManager.sendPacket(packet);
         isRequesting = true;
      }

   }

   public void initSlots() {
      int x = super.width / 2 - 171;
      displayStack = new GuiCCSlotInventory(x + 33, 70, new CloudItemStack("fake", lastCategory.getItemID()), 44, 44, false);
      slots.clear();
      if (lastCategory != null && lastCategory.getListings().size() > 0) {
         int tx = x + 118;
         int ty = 47;
         int tw = 209;
         int th = 30;
         int tym = 31;

         for(int i = 0; i < 6; ++i) {
            int i1 = i + topSlot;
            if (i1 < lastCategory.getListings().size()) {
               GuiCCSlotListing slot = new GuiCCSlotListing((PlayerListing)lastCategory.getListings().get(i1), tx, ty + i * tym, tw, th);
               slots.add(slot);
            }
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      int x = super.width / 2 - 172;
      if (par1GuiButton.id == 40) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      if (par1GuiButton.id == 41) {
         super.mc.displayGuiScreen(new GuiCCMenuInspect(this, displayStack.stack));
      }

      if (par1GuiButton.id == 42) {
         super.mc.displayGuiScreen((new GuiCCMenuStore()).setViewing(displayStack.stack));
      }

      if (par1GuiButton.id == 20 && lastCategory != null && !isRequesting) {
         PacketMarketRequestCategory packet = new PacketMarketRequestCategory(lastCategory.getItemID());
         ClientCloudManager.sendPacket(packet);
         isRequesting = true;
      }

   }

   public void updateScreen() {
      super.updateScreen();
      scrollBar.update();
      if (scrollBar.isClicked() || scrollBar.isMouseScrolling()) {
         topSlot = scrollBar.getTopSlot();
         this.initSlots();
      }

      if (refreshDelay++ > 5) {
         this.initSlots();
         this.totalToShow = lastCategory.getListings().size();
         if (this.totalToShow - this.showingPerPage > 0) {
            this.totalToShow -= this.showingPerPage;
         } else {
            this.totalToShow = 0;
         }

         scrollBar.setIncrements(this.totalToShow);
         refreshDelay = 0;
      }

      if (topSlot > lastCategory.getListings().size()) {
         topSlot = 0;
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotListing slot = (GuiCCSlotListing)i$.next();
         if (slot.isMouseOver(par1, par2) && par3 == 1) {
            GuiCCDDMMarketListing gui = new GuiCCDDMMarketListing(this, par1 + 2, par2 + 2, 80, 12, slot);
            super.mc.displayGuiScreen(gui);
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
      CloudItem item = lastCategory.getItem();
      CCRenderHelper.drawRectWithShadow((double)x, 25.0D, 110.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRect((double)(x + 1), 27.0D, 108.0D, 18.0D, GuiCCMenu.menuTheme2, 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.GREEN + "Emeralds: ", x + 4, 33);
      CCRenderHelper.drawImage((double)(x + 51), 32.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText(EnumChatFormatting.GREEN + "" + PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername()).emeralds, x + 62, 33);
      int lastColor = (new Integer(CCRenderHelper.gameColor)).intValue();
      CCRenderHelper.gameColor = item.getValue().getColorInteger();
      CCRenderHelper.renderCenteredText(item.getDisplayName(), x + 55, 50);
      CCRenderHelper.gameColor = lastColor;
      CCRenderHelper.drawRect((double)(x + 10), 63.0D, 90.0D, 1.0D, "0xffffff", 1.0F);
      if (displayStack != null) {
         displayStack.doRender(par1, par2, par3);
         CCRenderHelper.renderText(EnumChatFormatting.BOLD + "Type " + EnumChatFormatting.RESET + item.getMarketType().toUpperCase(), x + 10, 120);
         CCRenderHelper.renderText(EnumChatFormatting.BOLD + "Rarity " + EnumChatFormatting.RESET + item.getValue().rarityName.toUpperCase(), x + 10, 130);
         CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Market Stats", x + 55, 155);
         CCRenderHelper.drawRect((double)(x + 10), 143.0D, 90.0D, 1.0D, "0xffffff", 1.0F);
         String var1 = lastCategory.getStartingPrice() == -1 ? "-" : "" + lastCategory.getStartingPrice();
         CCRenderHelper.drawImage((double)(x + 45), 164.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
         CCRenderHelper.renderText("Starting:   " + EnumChatFormatting.GREEN + var1, x + 10, 165);
         String var2 = lastCategory.getLastSoldPrice() == -1 ? "-" : "" + lastCategory.getLastSoldPrice();
         CCRenderHelper.drawImage((double)(x + 55), 174.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
         CCRenderHelper.renderText("Last Sold:   " + EnumChatFormatting.GREEN + var2, x + 10, 175);
         String var3 = "" + lastCategory.getListings().size();
         CCRenderHelper.renderText("Quantity: " + EnumChatFormatting.GREEN + var3, x + 10, 185);
      }

      CCRenderHelper.drawRectWithShadow((double)(x + 113), 25.0D, 237.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      scrollBar.doRender(par1, par2, par3);
      CCRenderHelper.drawRect((double)(x + 119), 27.0D, 209.0D, 18.0D, GuiCCMenu.menuTheme2, 1.0F);
      if (StoreManager.instance().isStoreItemFromCloudID(lastCategory.getItemID())) {
         CCRenderHelper.renderText("Item", x + 176, 33);
      }

      CCRenderHelper.renderText("Item", x + 124, 33);
      CCRenderHelper.drawRect((double)(x + 290), 27.0D, 1.0D, 18.0D, "0xffffff", 1.0F);
      CCRenderHelper.drawImage((double)(x + 302), 29.0D, CCRenderHelper.RES_EMERALD_LOW, 16.0D, 16.0D);
      if (isRequesting) {
         CCRenderHelper.renderCenteredText("Requesting Listings...", x + 230, 50);
      }

      if (lastCategory.getListings().size() == 0 && !isRequesting) {
         CCRenderHelper.renderCenteredText("No Results Found", x + 230, 50);
      }

      if (!isRequesting) {
         for(int i = 0; i < slots.size(); ++i) {
            ((GuiCCSlotListing)slots.get(i)).doRender(par1, par2, par3);
            if (((GuiCCSlotListing)slots.get(i)).isMouseOver(par1, par2)) {
               ((GuiCCSlotListing)slots.get(i)).doRenderHighlight(1);
            }
         }
      }

      this.drawButtons(par1, par2, par3);
   }
}
