package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.gui.GuiCCDDMTrade;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.market.GuiCCDDMMarket;
import com.ferullogaming.countercraft.client.gui.workshop.GuiCCDDMWorkskhop;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMInventory extends GuiFGDropDownMenu {
   public GuiCCDDMInventory(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("My Items");
      this.addOption("Trading");
      this.addOption("Shopping");
      this.addOption("Workshop");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuInventory());
      }

      if (par1 == 2) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCDDMTrade(this, super.buttonX + super.buttonWidth + 1, super.buttonY + 21, super.buttonWidth, super.buttonHeight));
      }

      if (par1 == 3) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCDDMMarket(this, super.buttonX + super.buttonWidth + 1, super.buttonY + 42, super.buttonWidth, super.buttonHeight));
      }

      if (par1 == 4) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCDDMWorkskhop(super.parentGui, super.buttonX + super.buttonWidth + 1, super.buttonY + 63, super.buttonWidth, super.buttonHeight));
      }

   }
}
