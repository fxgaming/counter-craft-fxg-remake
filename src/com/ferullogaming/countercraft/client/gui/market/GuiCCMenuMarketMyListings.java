package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerListing;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketRequestedMarketMyListings;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerStats;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuMarketMyListings extends GuiCCMenu {
   public static int resyncListings = 0;
   public static int resyncListingsDisplay = 0;
   private static ArrayList slots = new ArrayList();
   private static ArrayList tempList = new ArrayList();
   private static int topSlot = 0;
   private static int refreshDelay = 0;
   private int totalToShow = 0;
   private int showingPerPage = 4;
   private GuiFGScrollBar scrollBar;

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      ClientCloudManager.sendPacket(new PacketClientRequest(RequestType.MYLISTINGS, new String[]{Minecraft.getMinecraft().getSession().getUsername()}));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("My Profile").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x, 83, 110, 150, this));
      super.buttonList.add(new GuiFGButton(18, x + 1, 25, 108, 18, "Back"));
      super.buttonList.add(new GuiFGButton(19, x + 1, 46, 108, 18, "Options"));
      super.buttonList.add(new GuiFGButton(20, x + 330, 27, 18, 19, new ResourceLocation("countercraft:textures/gui/refresh.png")));
      this.scrollBar = new GuiFGScrollBar(x + 330, 50, 18, 180, 0);
      topSlot = 0;
      this.initSlots();
   }

   public void initSlots() {
      int x = super.width / 2 - 172;
      tempList.clear();
      tempList.addAll(PacketRequestedMarketMyListings.getMyMarketListings());
      ClientCloudManager.sendPacket(new PacketClientRequest(RequestType.MYLISTINGS, new String[]{Minecraft.getMinecraft().getSession().getUsername()}));
      int tx = x + 118;
      int ty = 27;
      int tw = 209;
      int th = 49;
      int tym = 51;
      slots.clear();

      for(int i = 0; i < 4; ++i) {
         int i1 = i + topSlot;
         if (i1 < tempList.size()) {
            GuiCCSlotMyListing slot = new GuiCCSlotMyListing((PlayerListing)tempList.get(i1), tx, ty + i * tym, tw, th);
            slots.add(slot);
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      int x = super.width / 2 - 172;
      if (par1GuiButton.id == 18) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketBrowse());
      }

      if (par1GuiButton.id == 19) {
         GuiScreen gui = new GuiCCDDMMarketMyListingOptions(this, x + 1, 65, 108, 20, tempList);
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 20) {
         this.initSlots();
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.scrollBar.update();
      if (this.scrollBar.isClicked() || this.scrollBar.isMouseScrolling()) {
         topSlot = this.scrollBar.getTopSlot();
         this.initSlots();
      }

      if (refreshDelay++ > 20) {
         this.initSlots();
         this.totalToShow = tempList.size();
         if (this.totalToShow - this.showingPerPage > 0) {
            this.totalToShow -= this.showingPerPage;
         } else {
            this.totalToShow = 0;
         }

         this.scrollBar.setIncrements(this.totalToShow);
         refreshDelay = 0;
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotMyListing slot = (GuiCCSlotMyListing)i$.next();
         if (slot.isMouseOver(par1, par2) && par3 == 1) {
            GuiScreen gui = new GuiCCDDMMarketMyListing(this, par1 + 2, par2 + 2, 80, 12, slot);
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
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 25.0D, 237.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      this.scrollBar.doRender(par1, par2, par3);
      if (resyncListingsDisplay > 0) {
         CCRenderHelper.renderText("Re-Syncning Your Listings...", x + 164, 50);
      } else {
         for(int i = 0; i < slots.size(); ++i) {
            ((GuiCCSlotMyListing)slots.get(i)).doRender(par1, par2, par3);
            if (((GuiCCSlotMyListing)slots.get(i)).isMouseOver(par1, par2)) {
               ((GuiCCSlotMyListing)slots.get(i)).doRenderHighlight(1);
            }
         }
      }

      if (slots == null || slots.size() <= 0) {
         CCRenderHelper.renderCenteredTextScaledWithOutline("No trades to display.", x + 105 + 118, 128, 1.0D);
      }

      this.drawButtons(par1, par2, par3);
   }
}
