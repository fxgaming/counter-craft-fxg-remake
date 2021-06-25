package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMClans extends GuiFGDropDownMenu {
   public GuiCCDDMClans(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Мой клан");
      this.addOption("Настройки");
      this.addOption("Таблица Лидеров");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         ;
      }

      if (par1 == 4) {
         ;
      }

   }
}
