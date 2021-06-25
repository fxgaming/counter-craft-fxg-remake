package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSettings extends GuiFGDropDownMenu {
   public GuiCCDDMSettings(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Консоль");
      this.addOption("Настройка");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCMenuConsole(super.parentGui));
      }

      if (par1 == 2) {
    	  super.mc.displayGuiScreen(new GuiOptions(super.parentGui, super.mc.gameSettings));
      }
   }
}
