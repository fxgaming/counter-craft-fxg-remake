package com.ferullogaming.countercraft.client.gui;

import java.util.ArrayList;

import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;

import net.minecraft.client.gui.GuiButton;

public class GuiMenuHomes extends GuiMenus {
   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      this.addContainer(new GuiFGContainer(1, x + 1, 25, 100, 40, this));
      this.addContainer(new GuiCBanner(2, x + 104, 25, 247, 40, this));
      this.addContainer(new GuiFGContainer(4, x + 1, 68, 100, 157, this));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par1, par2, par3);
      this.drawButtons(par1, par2, par3);
      super.drawScreen(par1, par2, par3);
   }
}
