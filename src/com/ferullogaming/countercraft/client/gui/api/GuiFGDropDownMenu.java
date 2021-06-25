package com.ferullogaming.countercraft.client.gui.api;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public abstract class GuiFGDropDownMenu extends GuiFGScreen {
   protected GuiScreen parentGui;
   protected ArrayList optionsList = new ArrayList();
   protected ArrayList optionsListEnabled = new ArrayList();
   protected int buttonX;
   protected int buttonY;
   protected int buttonWidth = 85;
   protected int buttonHeight = 20;
   private boolean isButtonClicked = false;
   private boolean isOpened = false;

   public GuiFGDropDownMenu(GuiScreen par1, int par2, int par3, int par4, int par5) {
      this.parentGui = par1;
      this.buttonX = par2;
      this.buttonY = par3;
      this.buttonWidth = par4;
      this.buttonHeight = par5;
      this.optionsList.clear();
   }

   public GuiFGDropDownMenu addOption(String par1) {
      this.optionsList.add(par1);
      this.optionsListEnabled.add(true);
      return this;
   }

   public GuiFGDropDownMenu addOption(String par1, boolean par2) {
      this.optionsList.add(par1);
      this.optionsListEnabled.add(par2);
      return this;
   }

   public void initGui() {
      if (this.optionsList.size() <= 0) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      for(int i = 0; i < this.optionsList.size(); ++i) {
         int y = i * (this.buttonHeight + 1);
         GuiFGButton button = new GuiFGButton(i + 1, this.buttonX, this.buttonY + y, this.buttonWidth, this.buttonHeight, (String)this.optionsList.get(i));
         boolean enabled = ((Boolean)this.optionsListEnabled.get(i)).booleanValue();
         button.enabled = enabled;
         super.buttonList.add(button);
      }

      if (this.isOpened) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      this.isOpened = true;
   }

   public void actionPerformed(GuiButton guibutton) {
      this.onOptionClicked(guibutton.id);
   }

   public abstract void onOptionClicked(int var1);

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      if (!this.isButtonClicked && super.mc.currentScreen == this) {
         super.mc.displayGuiScreen(this.parentGui);
      }

   }

   public void drawScreen(int i, int j, float f) {
      if (super.mc.theWorld != null) {
         this.parentGui.drawScreen(i, j, f);
      }

      for(int k = 0; k < super.buttonList.size(); ++k) {
         GuiButton guibutton = (GuiButton)super.buttonList.get(k);
         guibutton.drawButton(super.mc, i, j);
      }

   }
}
