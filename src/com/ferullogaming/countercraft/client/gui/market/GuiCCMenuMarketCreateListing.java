package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketCreateListing;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketRequestedMarketValue;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuInventory;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuMarketCreateListing extends GuiCCMenu implements IGuiFGPromptYesNo {
   private CloudItemStack stackListing;
   private GuiCCSlotInventory slot;
   private int startingPrice = 0;
   private int suggestedPrice = 0;
   private int lastSold = 0;
   private int delay = 20;
   private GuiTextField promptInputText;
   private int listingPrice = 1;

   public GuiCCMenuMarketCreateListing(CloudItemStack par1) {
      this.stackListing = par1;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      int mx = x + 175;
      int width = 1;
      this.slot = (new GuiCCSlotInventory(x + 31, 30, this.stackListing, 50, 50, false)).setHighlight(true);
      super.buttonList.add(new GuiFGButton(17, mx + 10, 100, 60, 20, "Create"));
      super.buttonList.add(new GuiFGButton(18, mx + 80, 100, 60, 20, "Cancel"));
      this.promptInputText = new GuiTextField(super.fontRenderer, mx + 11, 75, 100, 20);
      this.promptInputText.setMaxStringLength(10);
      this.promptInputText.setText("1");
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      if (par1GuiButton.id == 17) {
         super.mc.displayGuiScreen((new GuiFGYesNoPrompt(1, this)).addInformation("Continue to create the listing?", "You may NEVER see this item again."));
      }

      if (par1GuiButton.id == 18) {
         super.mc.displayGuiScreen(new GuiCCMenuInventory());
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.promptInputText.mouseClicked(par1, par2, par3);
   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      this.promptInputText.textboxKeyTyped(par1, par2);
   }

   public void updateScreen() {
      super.updateScreen();
      this.promptInputText.updateCursorCounter();
      if (this.delay++ > 20) {
         ClientCloudManager.sendPacket(new PacketClientRequest(RequestType.MARKETVALUE, new String[]{"" + this.stackListing.getCloudItem().getID()}));
         this.startingPrice = PacketRequestedMarketValue.getStartingValue(this.stackListing.getCloudItem().getID());
         this.suggestedPrice = PacketRequestedMarketValue.getSuggestedValue(this.stackListing.getCloudItem().getID());
         this.lastSold = PacketRequestedMarketValue.getLastSoldValue(this.stackListing.getCloudItem().getID());
         this.delay = 0;
      }

   }

   public int getSettingPrice() {
      String var1 = this.promptInputText.getText();

      try {
         int var2 = Integer.parseInt(var1);
         if (var2 >= 50000) {
            return 50000;
         } else {
            return var2 <= 1 ? 1 : var2;
         }
      } catch (Exception var3) {
         return 1;
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      int x = super.width / 2 - 172;
      int width = 350;
      int mx = x + width / 2;
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      CCRenderHelper.drawRectWithShadow((double)(x + 25), 25.0D, 300.0D, 100.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRect((double)(x + width / 2), 30.0D, 1.0D, 90.0D, "0xffffff", 1.0F);
      String var1 = this.startingPrice == -1 ? "-" : "" + this.startingPrice;
      CCRenderHelper.drawImage((double)(x + 83), 40.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "Starting", x + 85, 31);
      CCRenderHelper.renderText(EnumChatFormatting.YELLOW + "" + var1, x + 95, 41);
      String var2 = this.suggestedPrice == -1 ? "-" : "" + this.suggestedPrice;
      CCRenderHelper.drawImage((double)(x + 83), 60.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "Suggested", x + 85, 51);
      CCRenderHelper.renderText(EnumChatFormatting.YELLOW + "" + var2, x + 95, 61);
      String var3 = this.lastSold == -1 ? "-" : "" + this.lastSold;
      CCRenderHelper.drawImage((double)(x + 83), 80.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "Last Sold", x + 85, 71);
      CCRenderHelper.renderText(EnumChatFormatting.YELLOW + "" + var3, x + 95, 81);
      this.slot.doRender(par1, par2, par3);
      this.promptInputText.drawTextBox();
      CCRenderHelper.renderText("Your Price", mx + 10, 30);
      CCRenderHelper.drawImage((double)(mx + 10), 39.0D, CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText(EnumChatFormatting.YELLOW + "" + this.getSettingPrice(), mx + 22, 40);
      double scale = 0.7D;
      int margin = 7;
      int bx = x + 32;
      int by = 98;
      CCRenderHelper.renderTextScaled("Listing your Item will remove it from", bx, by, scale);
      CCRenderHelper.renderTextScaled("your inventory. Max 5 listings per user.", bx, by + margin * 1, scale);
      CCRenderHelper.renderTextScaled("Max is 50000e, Min is 1e", bx, by + margin * 2, scale);
      this.drawButtons(par1, par2, par3);
   }

   public void onResult(int par1, boolean par2) {
      if (par2) {
         ClientCloudManager.sendPacket(new PacketMarketCreateListing(this.stackListing.getUUID(), this.getSettingPrice()));
         super.mc.displayGuiScreen(new GuiCCMenuInventory());
      }

   }
}
