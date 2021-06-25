package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.store.GuiCCMenuStore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCDDMMarket extends GuiFGDropDownMenu {
   public GuiCCDDMMarket(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Mod's Store");
      this.addOption("Market");
      this.addOption(EnumChatFormatting.BOLD + "Donations");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCMenuStore());
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketBrowse());
      }

      if (par1 == 3) {
         //GuiCCMenu.openURL(References.URL_BUYCRAFT);
      }

   }
}
