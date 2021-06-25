package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMMarketBrowseFilter extends GuiFGDropDownMenu {
   public GuiCCDDMMarketBrowseFilter(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Rifle");
      this.addOption("Pistol");
      this.addOption("SMG");
      this.addOption("Sniper");
      this.addOption("Knife");
      this.addOption("Case");
      this.addOption("Misc");
      this.addOption("Heavy");
   }

   public void onOptionClicked(int par1) {
      String type = (String)super.optionsList.get(par1 - 1);
      if (GuiCCMenuMarketBrowse.filter1.equals(type)) {
         GuiCCMenuMarketBrowse.filter1 = "";
      } else {
         GuiCCMenuMarketBrowse.filter1 = type;
      }

      GuiCCMenuMarketBrowse.filter2 = "";
      GuiCCMenuMarketBrowse.filter3 = "";
      super.mc.displayGuiScreen(super.parentGui);
   }
}
