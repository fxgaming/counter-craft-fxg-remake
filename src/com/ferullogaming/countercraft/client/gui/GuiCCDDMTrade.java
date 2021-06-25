package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMTrade extends GuiFGDropDownMenu {
   public GuiCCDDMTrade(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Создать");
      this.addOption("Мои обмены");
      this.addOption("Моя история");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCTPTradeRequest(super.parentGui));
      }

      if (par1 == 2) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuTrades());
      }

      if (par1 == 3) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuTrades(true));
      }

   }
}
