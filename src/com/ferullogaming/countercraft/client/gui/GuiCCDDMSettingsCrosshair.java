package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.Crosshair;
import com.ferullogaming.countercraft.client.ModSettings;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCDDMSettingsCrosshair extends GuiFGDropDownMenu {
   public GuiCCDDMSettingsCrosshair(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption(EnumChatFormatting.BOLD + "Обновить");
      Iterator i$ = ModSettings.instance().CROSSHAIR_LIST.iterator();

      while(i$.hasNext()) {
         Crosshair var1 = (Crosshair)i$.next();
         this.addOption(var1.name);
      }

   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         ModSettings.instance().loadCrosshairs();
      } else {
         Crosshair selected = (Crosshair)ModSettings.instance().CROSSHAIR_LIST.get(par1 - 2);
         ModSettings.instance().CROSSHAIR_DEFAULT = selected.name;
         ModSettings.instance().saveSettings();
      }
   }
}
