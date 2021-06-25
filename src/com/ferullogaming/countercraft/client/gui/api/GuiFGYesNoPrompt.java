package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import java.awt.Rectangle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiFGYesNoPrompt extends GuiFGScreen {
   public String[] promptInformation;
   public int promptWidth = 200;
   public int promptHeight = 70;
   public int promptID = 0;
   protected GuiScreen parentGui;
   private boolean isButtonClicked = false;
   private boolean isOpened = false;
   private int promptX;
   private int promptY;
   private String opt1Text = "Нет";
   private String opt2Text = "Да";

   public GuiFGYesNoPrompt(int par1, GuiScreen par2) {
      this.promptID = par1;
      this.parentGui = par2;
   }

   public GuiFGYesNoPrompt addInformation(String... par1) {
      this.promptInformation = par1;
      return this;
   }

   public GuiFGYesNoPrompt setEnableDisable() {
      this.opt1Text = "Выкл.";
      this.opt2Text = "Вкл.";
      return this;
   }

   public void initGui() {
      this.promptX = super.width / 2 - this.promptWidth / 2;
      this.promptY = super.height / 2 - this.promptHeight / 2 - 40;
      super.buttonList.add(new GuiFGButton(10, this.promptX + 2, this.promptY + this.promptHeight - 22, 80, 20, this.opt1Text));
      super.buttonList.add(new GuiFGButton(11, this.promptX + this.promptWidth - 80 - 2, this.promptY + this.promptHeight - 22, 80, 20, this.opt2Text));
      if (this.isOpened) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      this.isOpened = true;
   }

   public void actionPerformed(GuiButton guibutton) {
      if (guibutton.id == 11 && this.parentGui instanceof IGuiFGPromptYesNo) {
         ((IGuiFGPromptYesNo)this.parentGui).onResult(this.promptID, true);
      }

      if (guibutton.id == 10 && this.parentGui instanceof IGuiFGPromptYesNo) {
         ((IGuiFGPromptYesNo)this.parentGui).onResult(this.promptID, false);
      }

      if (super.mc.currentScreen == this) {
         super.mc.displayGuiScreen(this.parentGui);
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Rectangle rect = new Rectangle(this.promptX, this.promptY, this.promptWidth, this.promptHeight);
      if (!rect.contains(par1, par2)) {
         if (this.isButtonClicked) {
            this.isButtonClicked = false;
         } else {
            if (super.mc.currentScreen == this) {
               super.mc.displayGuiScreen(this.parentGui);
            }

         }
      }
   }

   public void drawScreen(int i, int j, float f) {
      this.parentGui.drawScreen(i, j, f);
      int margin = 1;
      CCRenderHelper.drawRectWithShadow((double)this.promptX, (double)this.promptY, (double)this.promptWidth, (double)this.promptHeight, GuiCCMenu.menuTheme, 1.0F);

      int i1;
      for(i1 = 0; i1 < super.buttonList.size(); ++i1) {
         GuiButton guibutton = (GuiButton)super.buttonList.get(i1);
         guibutton.drawButton(super.mc, i, j);
      }

      if (this.promptInformation != null) {
         for(i1 = 0; i1 < this.promptInformation.length; ++i1) {
            String var1 = this.promptInformation[i1];
            CCRenderHelper.renderCenteredText(var1, this.promptX + this.promptWidth / 2, this.promptY + 5 + i1 * 11);
         }
      }

   }

   public void setPromptAnswers(String givenYes, String givenNo) {
      this.opt1Text = givenYes;
      this.opt2Text = givenNo;
   }
}
