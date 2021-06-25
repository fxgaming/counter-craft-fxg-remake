package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerListing;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketRequestedMarketValue;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import java.awt.Rectangle;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCSlotMyListing {
   public PlayerListing listing;
   public int x;
   public int y;
   public int width;
   public int height;
   private GuiCCSlotInventory stackSlot;

   public GuiCCSlotMyListing(PlayerListing par1, int par2, int par3, int par4, int par5) {
      this.listing = par1;
      this.x = par2;
      this.y = par3;
      this.width = par4;
      this.height = par5;
      this.stackSlot = new GuiCCSlotInventory(this.x + 2, this.y + 2, par1.getStack(), 44, 45, false);
      ClientCloudManager.sendPacket(new PacketClientRequest(RequestType.MARKETVALUE, new String[]{"" + par1.getStack().getCloudItem().getID()}));
   }

   public void doRender(int par1, int par2, float par3) {
      CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, "0x555555", 1.0F);
      CCRenderHelper.drawRect((double)(this.x + 1), (double)(this.y + 1), (double)(this.width - 2), (double)(this.height - 2), GuiCCMenu.menuTheme, 1.0F);
      this.stackSlot.doRender(par1, par2, par3);
      int lastColor = (new Integer(CCRenderHelper.gameColor)).intValue();
      CCRenderHelper.gameColor = this.stackSlot.stack.getCloudItem().getValue().getColorInteger();
      CCRenderHelper.renderText(this.stackSlot.stack.getDisplayName(), this.x + 50, this.y + 4);
      CCRenderHelper.gameColor = lastColor;
      CCRenderHelper.drawImage((double)(this.x + 95), (double)(this.y + 13), CCRenderHelper.RES_EMERALD_LOW, 10.0D, 10.0D);
      CCRenderHelper.renderText("My Price:   " + EnumChatFormatting.GREEN + "" + this.listing.getPrice(), this.x + 50, this.y + 14);
      int var1 = PacketRequestedMarketValue.getStartingValue(this.listing.getStack().getCloudItem().getID());
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Starting Cost: " + (var1 == -1 ? "No Data" : "" + var1), this.x + 50, this.y + 24);
      var1 = PacketRequestedMarketValue.getSuggestedValue(this.listing.getStack().getCloudItem().getID());
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Suggested Cost: " + (var1 == -1 ? "No Data" : "" + var1), this.x + 50, this.y + 34);
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
