package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketCreateListing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMItemClickedStickerCapsule extends GuiFGDropDownMenu {
   private CloudItemStack stack;

   public GuiCCDDMItemClickedStickerCapsule(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      this.stack = stack;
      this.addOption("Open");
      this.addOption("Sell on Market");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuOpenCapsule(this.stack));
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketCreateListing(this.stack));
      }

   }
}
