package com.ferullogaming.countercraft.client.gui.store;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.StoreCategory;
import com.ferullogaming.countercraft.client.cloud.StoreItem;
import com.ferullogaming.countercraft.client.cloud.StoreManager;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerStats;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuStore extends GuiCCMenu {
   public static int lastCategory = 1;
   public static ArrayList cart = new ArrayList();

   public static int getCartPrice() {
      int var1 = 0;

      StoreOrder cartItem;
      StoreItem item;
      for(Iterator i$ = cart.iterator(); i$.hasNext(); var1 += cartItem.amount * item.getPrice()) {
         cartItem = (StoreOrder)i$.next();
         item = (StoreItem)StoreManager.instance().getCategory(cartItem.catID).catItems.get(cartItem.packageID);
      }

      return var1;
   }

   public static int getCartQty() {
      int var1 = 0;

      StoreOrder cartItem;
      for(Iterator i$ = cart.iterator(); i$.hasNext(); var1 += cartItem.amount) {
         cartItem = (StoreOrder)i$.next();
      }

      return var1;
   }

   public GuiCCMenuStore setViewing(CloudItemStack par1) {
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

      for(int i = 0; i < store.getCategoriesListed().size(); ++i) {
         String name = EnumChatFormatting.ITALIC + ((StoreCategory)store.getCategoriesListed().get(i)).getName();
         int nameWidth = super.mc.fontRenderer.getStringWidth(name);
         GuiFGButton button = new GuiFGButton(50 + i, dx + 7 + buttonX, dy + 17, nameWidth, 10, name);
         button.drawBackground = false;
         super.buttonList.add(button);
         buttonX += super.mc.fontRenderer.getStringWidth(name) + 7;
      }

      String name = EnumChatFormatting.ITALIC + "Check Out";
      int nameWidth = super.mc.fontRenderer.getStringWidth(name);
      GuiFGButton button = new GuiFGButton(49, dx + 166, dy + 72, nameWidth, 10, name);
      button.drawBackground = false;
      super.buttonList.add(button);
      name = EnumChatFormatting.ITALIC + "Add Emeralds";
      nameWidth = super.mc.fontRenderer.getStringWidth(name);
      button = new GuiFGButton(45, dx + 160, dy + 4, nameWidth, 10, name);
      button.drawBackground = false;
      super.buttonList.add(button);
      StoreCategory category = store.getCategory(lastCategory);
      if (category != null) {
         for(int i = 0; i < category.getItemsListed().size(); ++i) {
            StoreItem var10000 = (StoreItem)category.getItemsListed().get(i);
            GuiFGButton button1 = new GuiFGButton(30 + i, dx + 125, dy + 84 + i * 19, 24, 14, "Add");
            button1.drawShadow = false;
            button1.buttonColor = "0x006cff";
            super.buttonList.add(button1);
         }
      }

      PacketClientRequest.sendRequest(RequestType.STOREITEMS);
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      StoreManager store = StoreManager.instance();

      for(int i = 0; i < store.getCategoriesListed().size(); ++i) {
         if (par1GuiButton.id == 50 + i) {
            lastCategory = ((StoreCategory)store.getCategoriesListed().get(i)).getId();
            super.mc.displayGuiScreen(new GuiCCMenuStore());
            return;
         }
      }

      StoreCategory category = store.getCategory(lastCategory);
      if (category != null) {
         for(int i = 0; i < category.getItemsListed().size(); ++i) {
            StoreItem item = (StoreItem)category.getItemsListed().get(i);
            if (par1GuiButton.id == 30 + i) {
               GuiCCTPStoreAdd gui = new GuiCCTPStoreAdd(this, category, item);
               if (cart.size() < 6) {
                  super.mc.displayGuiScreen(gui);
               } else {
                  ClientTickHandler.addClientNotification(new ClientNotification("Too many Items in 'My Cart'"));
               }
            }
         }
      }

      if (par1GuiButton.id == 49) {
         super.mc.displayGuiScreen(new GuiCCMenuStoreCheckout());
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
      CCRenderHelper.drawRectWithShadow((double)dx, (double)dy, (double)dw, (double)dh, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(dx + 5), (double)(dy + 1), (double)(dw - 10), 27.0D, GuiCCMenu.menuTheme3, 1.0F);
      CCRenderHelper.drawRect((double)(dx + 5), (double)(dy + 1), (double)(dw - 10), 15.0D, "0x006cff", 1.0F);
      CCRenderHelper.renderText(EnumChatFormatting.BOLD + "Counter Craft Store", dx + 7, dy + 5);
      if (StoreManager.instance().getCategoriesListed().size() <= 0) {
         CCRenderHelper.renderCenteredText("Loading Store Items...", dx + dw / 2, dy + 40);
      } else {
         StoreCategory category = store.getCategory(lastCategory);
         if (category != null) {
            CCRenderHelper.drawRectWithShadow((double)(dx + 5), (double)(dy + 34), 150.0D, 38.0D, GuiCCMenu.menuTheme3, 1.0F);
            CCRenderHelper.drawRect((double)(dx + 5), (double)(dy + 34), 150.0D, 15.0D, "0x006cff", 1.0F);
            CCRenderHelper.renderText(EnumChatFormatting.BOLD + category.getName(), dx + 7, dy + 38);
            ArrayList desc = StringListHelperCC.getListLimitWidth(category.getDescription(), 145);

            int i;
            for(i = 0; i < desc.size(); ++i) {
               CCRenderHelper.renderText(EnumChatFormatting.DARK_GRAY + (String)desc.get(i), dx + 7, dy + 52 + i * 9, false);
            }

            CCRenderHelper.drawRectWithShadow((double)(dx + 5), (double)(dy + 79), 150.0D, 123.0D, GuiCCMenu.menuTheme3, 1.0F);

            for(i = 0; i < category.getItemsListed().size(); ++i) {
               StoreItem item = (StoreItem)category.getItemsListed().get(i);
               CCRenderHelper.drawRect((double)(dx + 8), (double)(dy + 82 + i * 19), 144.0D, 18.0D, "0x000000", 1.0F);
               CCRenderHelper.renderText(item.getName(), dx + 11, dy + 87 + i * 19);
               CCRenderHelper.renderCenteredText("" + (item.getPrice() == 0 ? "FREE" : item.getPrice() + "c"), dx + 100, dy + 87 + i * 19);
            }
         }

         CCRenderHelper.drawRectWithShadow((double)(dx + dw - 70 - 5), (double)(dy + 34), 70.0D, 50.0D, GuiCCMenu.menuTheme3, 1.0F);
         CCRenderHelper.drawRect((double)(dx + dw - 70 - 5), (double)(dy + 34), 70.0D, 15.0D, "0x006cff", 1.0F);
         CCRenderHelper.renderText(EnumChatFormatting.BOLD + "My Cart", dx + dw - 72, dy + 38);
         CCRenderHelper.renderText(getCartQty() + " Item(s)", dx + dw - 72, dy + 51);
         CCRenderHelper.renderText("" + getCartPrice() + "c", dx + dw - 72, dy + 61);
         this.drawButtons(par1, par2, par3);
      }
   }
}
