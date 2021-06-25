package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMMarketBrowseFilter2 extends GuiFGDropDownMenu {
   public GuiCCDDMMarketBrowseFilter2(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      CloudItem[] arr$ = CloudItem.itemList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         CloudItem item = arr$[i$];
         if (item != null && item.getMarketType().equalsIgnoreCase(par6) && !super.optionsList.contains(item.getName())) {
            this.addOption(item.getName());
         }
      }

   }

   public void onOptionClicked(int par1) {
      String type = (String)super.optionsList.get(par1 - 1);
      GuiCCMenuMarketBrowse.filter2 = type;
      GuiCCMenuMarketBrowse.filter3 = "";
      super.mc.displayGuiScreen(super.parentGui);
   }
}
