package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMPlayerLounge extends GuiFGDropDownMenu {
   public GuiCCDDMPlayerLounge(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Chat Lounge");
      this.addOption("Server Lounge");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         ;
      }

      if (par1 == 2) {
         ;
      }

   }
}
