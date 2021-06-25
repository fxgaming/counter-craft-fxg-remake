package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.MarketCategoryHandler;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import java.awt.Rectangle;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCSlotMarketResult {
   public MarketCategoryHandler listing;
   public int x;
   public int y;
   public int width;
   public int height;
   private CloudItemStack stackSlot;

   public GuiCCSlotMarketResult(MarketCategoryHandler par1, int par2, int par3, int par4, int par5) {
      this.listing = par1;
      this.x = par2;
      this.y = par3;
      this.width = par4;
      this.height = par5;
      this.stackSlot = new CloudItemStack("fake", par1.getItemID());
   }

   public void doRender(int par1, int par2, float par3) {
      CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, "0x555555", 1.0F);
      CCRenderHelper.drawRect((double)(this.x + 1), (double)(this.y + 1), (double)(this.width - 2), (double)(this.height - 2), GuiCCMenu.menuTheme, 1.0F);
      this.doRenderItemStack(0, 0);
      int lastColor = (new Integer(CCRenderHelper.gameColor)).intValue();
      CCRenderHelper.gameColor = this.stackSlot.getCloudItem().getValue().getColorInteger();
      CCRenderHelper.renderText(this.stackSlot.getDisplayName(), this.x + 38, this.y + this.height / 2 - 5);
      CCRenderHelper.gameColor = lastColor;
      CCRenderHelper.renderCenteredText("" + this.listing.getQuanity(), this.x + 150, this.y + this.height / 2 - 5);
      String var1 = this.listing.getStartingPrice() != -1 ? "" + this.listing.getStartingPrice() : "-";
      CCRenderHelper.renderCenteredText(EnumChatFormatting.GREEN + "" + var1, this.x + 192, this.y + this.height / 2 - 5);
   }

   public void doRenderItemStack(int x1, int y1) {
      CCRenderHelper.drawRect((double)(this.x + 1), (double)(this.y + 1), 32.0D, (double)(this.height - 2), "0x000000", 0.2F);
      CCRenderHelper.drawRect((double)(this.x + 2), (double)(this.y + 2), 30.0D, (double)(this.height - 4), "0x969ba0", 1.0F);
      CCRenderHelper.drawGradientRect(this.x + 2, this.y + 2, 30, this.height - 4, 0.3F);
      CCRenderHelper.renderSpecialItemStackInventory(this.stackSlot.getItemStack(), (double)(this.x + 17), (double)(this.y + this.height / 2 + 6), 0.75D);
   }

   public void doRenderHighlight(int par1) {
      String color = "0xffba00";
      CCRenderHelper.drawRect((double)(this.x - par1), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x + this.width), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y - par1), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y + this.height), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
   }

   public boolean isMouseOver(int x, int y) {
      Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
      return rect.contains(x, y);
   }
}
