package com.ferullogaming.countercraft.client.gui.store;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.StoreCategory;
import com.ferullogaming.countercraft.client.cloud.StoreItem;
import com.ferullogaming.countercraft.client.cloud.StoreManager;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketStoreOrder;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerStats;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuStoreCheckout extends GuiCCMenu implements IGuiFGPromptYesNo {
   public GuiCCMenuStoreCheckout setViewing(CloudItemStack par1) {
      return this;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      int mx = x + 175;
      StoreManager store = StoreManager.instance();
      int dx = x + 114;
      int dy = 25;
      int dw = 1;
      int dh = 1;
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("My Profile").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
      int buttonX = 0;

      String name;
      int nameWidth;
      GuiFGButton button1;
      for(int i = 0; i < store.getCategoriesListed().size(); ++i) {
         name = EnumChatFormatting.ITALIC + ((StoreCategory)store.getCategoriesListed().get(i)).getName();
         nameWidth = super.mc.fontRenderer.getStringWidth(name);
         button1 = new GuiFGButton(50 + i, dx + 7 + buttonX, dy + 17, nameWidth, 10, name);
         button1.drawBackground = false;
         super.buttonList.add(button1);
         buttonX += super.mc.fontRenderer.getStringWidth(name) + 7;
      }

      GuiFGButton button = new GuiFGButton(29, dx + 215, dy + 54, 10, 10, EnumChatFormatting.BOLD + "x");
      button.drawBackground = false;
      super.buttonList.add(button);
      name = EnumChatFormatting.ITALIC + "Add Emeralds";
      nameWidth = super.mc.fontRenderer.getStringWidth(name);
      button = new GuiFGButton(45, dx + 160, dy + 4, nameWidth, 10, name);
      button.drawBackground = false;
      super.buttonList.add(button);

      for(int i = 0; i < GuiCCMenuStore.cart.size(); ++i) {
         GuiFGButton button11 = new GuiFGButton(30 + i, dx + 215, dy + 71 + i * 19, 10, 10, EnumChatFormatting.BOLD + "x");
         button11.drawBackground = false;
         super.buttonList.add(button11);
      }

      button1 = new GuiFGButton(28, dx + 128, dy + 184, 100, 18, EnumChatFormatting.BOLD + "Purchase >>");
      button1.drawShadow = false;
      button1.buttonColor = "0x006cff";
      super.buttonList.add(button1);
      PacketClientRequest.sendRequest(RequestType.STOREITEMS);
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      StoreManager store = StoreManager.instance();

      int i;
      for(i = 0; i < store.getCategoriesListed().size(); ++i) {
         if (par1GuiButton.id == 50 + i) {
            GuiCCMenuStore.lastCategory = ((StoreCategory)store.getCategoriesListed().get(i)).getId();
            super.mc.displayGuiScreen(new GuiCCMenuStore());
            return;
         }
      }

      GuiFGYesNoPrompt gui;
      if (par1GuiButton.id == 29) {
         gui = new GuiFGYesNoPrompt(1, this);
         gui.addInformation("Remove all Packages from 'My Cart'?");
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 28 && GuiCCMenuStore.cart.size() > 0) {
         gui = new GuiFGYesNoPrompt(2, this);
         gui.addInformation("Continue Purchase?", "Total is " + GuiCCMenuStore.getCartPrice() + " Emeralds.", "Non-refundable.");
         super.mc.displayGuiScreen(gui);
      }

      for(i = 0; i < GuiCCMenuStore.cart.size(); ++i) {
         StoreOrder order = (StoreOrder)GuiCCMenuStore.cart.get(i);
         StoreItem item = (StoreItem)store.getCategory(order.catID).catItems.get(order.packageID);
         if (par1GuiButton.id == 30 + i) {
            GuiFGYesNoPrompt gui1 = new GuiFGYesNoPrompt(30 + i, this);
            gui1.addInformation("Remove '" + item.getName() + "' from 'My Cart'?");
            super.mc.displayGuiScreen(gui1);
         }
      }

      if (par1GuiButton.id == 45) {
         ;
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      int x = super.width / 2 - 173;
      int width = 350;
      int var10000 = x + width / 2;
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      StoreManager store = StoreManager.instance();
      int dx = x + 114;
      int dy = 25;
      int dw = 237;
      int dh = 208;
      CCRenderHelper.drawRectWithShadow((double)dx, (double)dy, (double)dw, (double)dh, "0x333333", 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(dx + 5), (double)(dy + 1), (double)(dw - 10), 27.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRect((double)(dx + 5), (double)(dy + 1), (double)(dw - 10), 15.0D, "0x006cff", 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.BOLD + "Counter Craft Store", dx + 7, dy + 5);
      if (StoreManager.instance().getCategoriesListed().size() <= 0) {
         CCRenderHelper.renderCenteredText("Loading Store Items...", dx + dw / 2, dy + 40);
      } else {
         CCRenderHelper.drawRectWithShadow((double)(dx + 5), (double)(dy + 34), (double)(dw - 10), 170.0D, GuiCCMenu.menuTheme, 1.0F);
         CCRenderHelper.drawRect((double)(dx + 5), (double)(dy + 34), (double)(dw - 10), 15.0D, "0x006cff", 1.0F);
         CCRenderHelper.renderText(EnumChatFormatting.BOLD + "Check Out", dx + 7, dy + 38);
         CCRenderHelper.renderTextRight(GuiCCMenuStore.getCartPrice() + " Emeralds", dx + dw - 7, dy + 38);
         CCRenderHelper.renderText("Name", dx + 12, dy + 55);
         CCRenderHelper.renderText("Qty", dx + 110, dy + 55);
         CCRenderHelper.renderText("Price", dx + 160, dy + 55);

         for(int i = 0; i < GuiCCMenuStore.cart.size(); ++i) {
            StoreOrder order = (StoreOrder)GuiCCMenuStore.cart.get(i);
            StoreItem item = (StoreItem)store.getCategory(order.catID).catItems.get(order.packageID);
            CCRenderHelper.drawRect((double)(dx + 8), (double)(dy + 68 + i * 19), 220.0D, 18.0D, "0x000000", 1.0F);
            CCRenderHelper.renderText(item.getName(), dx + 12, dy + 73 + i * 19);
            CCRenderHelper.renderCenteredText("" + order.amount, dx + 118, dy + 73 + i * 19);
            CCRenderHelper.renderCenteredText("" + order.amount * item.getPrice(), dx + 173, dy + 73 + i * 19);
         }

         this.drawButtons(par1, par2, par3);
      }
   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2) {
         GuiCCMenuStore.cart.clear();
      }

      if (par1 == 2 && par2) {
         ClientCloudManager.sendPacket(new PacketMarketStoreOrder());
         super.mc.displayGuiScreen(new GuiCCMenuStore());
      }

      if (par1 >= 30 && par1 < 40 && par2) {
         int var1 = par1 - 30;
         GuiCCMenuStore.cart.remove(var1);
      }

   }
}
