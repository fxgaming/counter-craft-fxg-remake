package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMItemClickedTrade extends GuiFGDropDownMenu {
   private CloudItemStack stack;

   public GuiCCDDMItemClickedTrade(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      this.stack = stack;
      this.addOption("Open Contract");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuTradeContract());
      }

   }
}
