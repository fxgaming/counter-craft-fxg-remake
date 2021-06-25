package com.ferullogaming.countercraft.client.minimap.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class MwGuiTextDialog extends GuiScreen {
   static final int textDialogWidthPercent = 50;
   static final int textDialogTitleY = 80;
   static final int textDialogY = 92;
   static final int textDialogErrorY = 108;
   private final GuiScreen parentScreen;
   String title;
   String text;
   String error;
   GuiTextField textField = null;
   boolean inputValid = false;
   boolean showError = false;
   boolean backToGameOnSubmit = false;

   public MwGuiTextDialog(GuiScreen parentScreen, String title, String text, String error) {
      this.parentScreen = parentScreen;
      this.title = title;
      this.text = text;
      this.error = error;
   }

   private void newTextField() {
      if (this.textField != null) {
         this.text = this.textField.getText();
      }

      int w = super.width * 50 / 100;
      this.textField = new GuiTextField(super.fontRenderer, (super.width - w) / 2 + 5, 92, w - 10, 12);
      this.textField.setMaxStringLength(32);
      this.textField.setFocused(true);
      this.textField.setCanLoseFocus(false);
      this.textField.setText(this.text);
   }

   public void setText(String s) {
      this.textField.setText(s);
      this.text = s;
   }

   public String getInputAsString() {
      String s = this.textField.getText().trim();
      this.inputValid = s.length() > 0;
      this.showError = !this.inputValid;
      return s;
   }

   public int getInputAsInt() {
      String s = this.getInputAsString();
      int value = 0;

      try {
         value = Integer.parseInt(s);
         this.inputValid = true;
         this.showError = false;
      } catch (NumberFormatException var4) {
         this.inputValid = false;
         this.showError = true;
      }

      return value;
   }

   public int getInputAsHexInt() {
      String s = this.getInputAsString();
      int value = 0;

      try {
         value = Integer.parseInt(s, 16);
         this.inputValid = true;
         this.showError = false;
      } catch (NumberFormatException var4) {
         this.inputValid = false;
         this.showError = true;
      }

      return value;
   }

   public boolean submit() {
      return false;
   }

   public void initGui() {
      this.newTextField();
   }

   public void drawScreen(int mouseX, int mouseY, float f) {
      if (this.parentScreen != null) {
         this.parentScreen.drawScreen(mouseX, mouseY, f);
      } else {
         this.drawDefaultBackground();
      }

      int w = super.width * 50 / 100;
      drawRect((super.width - w) / 2, 76, (super.width - w) / 2 + w, 122, Integer.MIN_VALUE);
      this.drawCenteredString(super.fontRenderer, this.title, super.width / 2, 80, 16777215);
      this.textField.drawTextBox();
      if (this.showError) {
         this.drawCenteredString(super.fontRenderer, this.error, super.width / 2, 108, 16777215);
      }

      super.drawScreen(mouseX, mouseY, f);
   }

   protected void mouseClicked(int x, int y, int button) {
      super.mouseClicked(x, y, button);
   }

   protected void keyTyped(char c, int key) {
      switch(key) {
      case 1:
         super.mc.displayGuiScreen(this.parentScreen);
         break;
      case 28:
         if (this.submit()) {
            if (!this.backToGameOnSubmit) {
               super.mc.displayGuiScreen(this.parentScreen);
            } else {
               super.mc.displayGuiScreen((GuiScreen)null);
            }
         }
         break;
      default:
         this.textField.textboxKeyTyped(c, key);
         this.text = this.textField.getText();
      }

   }
}
