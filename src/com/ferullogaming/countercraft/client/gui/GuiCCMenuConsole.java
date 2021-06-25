package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuConsole extends GuiCCMenu {
   public static ArrayList consoleChat = new ArrayList();
   public static ArrayList lastCommands = new ArrayList();
   public static int lastCommandCount = 0;
   public GuiScreen parentGui;
   private GuiTextField promptInputText;
   private GuiFGScrollBar scrollBar;

   public GuiCCMenuConsole(GuiScreen par1) {
      this.parentGui = par1;
   }

   public static void addText(String par1) {
      if (consoleChat.size() > 60) {
         consoleChat.remove(0);
      }

      consoleChat.add(par1);
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      super.buttonList.clear();
      super.buttonList.add(new GuiFGButton(1, x + 1, 1, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      this.promptInputText = new GuiTextField(super.fontRenderer, super.width / 2 - 125, 184, 250, 20);
      this.promptInputText.setMaxStringLength(76);
      this.scrollBar = new GuiFGScrollBar(super.width / 2 + 125 + 3, 29, 18, 152, 13);
      this.scrollBar.setIncrements(40);
      this.scrollBar.setScrollerMaxed();
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      if (par1GuiButton.id == 1) {
         super.mc.displayGuiScreen(this.parentGui);
      }

   }

   public void sendCommand() {
      String text = this.promptInputText.getText();
      if (text != null && text.length() > 0) {
         addText(EnumChatFormatting.GRAY + text);
         ClientManager.instance().getCloudManager().getLocalCommandHandler().handleCommandPresend(PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername()), text);
         lastCommands.add(text);
         this.promptInputText.setText("");
         this.promptInputText.setFocused(true);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.promptInputText.updateCursorCounter();
      this.scrollBar.update();
   }

   public void onGuiClosed() {
      super.onGuiClosed();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.promptInputText.mouseClicked(par1, par2, par3);
   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      this.promptInputText.textboxKeyTyped(par1, par2);
      if (par2 == 28) {
         this.sendCommand();
      } else {
         if (par2 == 200) {
            if (lastCommands.size() - lastCommandCount > 0) {
               this.promptInputText.setText((String)lastCommands.get(lastCommands.size() - 1 - lastCommandCount));
               ++lastCommandCount;
            }
         } else {
            lastCommandCount = 0;
         }

      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawIntendedBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      CCRenderHelper.renderCenteredText("Консоль клиента", super.width / 2, 7);
      int cw = 250;
      int ch = 150;
      int cx = super.width / 2 - cw / 2;
      int cy = 30;
      int cboader = 3;
      CCRenderHelper.drawRectWithShadow((double)cx, (double)cy, (double)cw, (double)ch, "0x999999", 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(cx + cboader), (double)(cy + cboader), (double)(cw - cboader * 2), (double)(ch - cboader * 2), "0x111111", 1.0F);

      for(int i = 0; i < consoleChat.size(); ++i) {
         String var1 = (String)consoleChat.get(i);
         int ty = cy + cboader + 2 + i * 7;
         int dty = consoleChat.size() - 59 + this.scrollBar.getTopSlot();
         ty -= dty > 0 ? 7 * dty : 0;
         if (ty < cy + ch - 7 && ty > cy) {
            CCRenderHelper.renderTextScaled(var1, cx + cboader + 2, ty, 0.7D);
         }
      }

      this.promptInputText.drawTextBox();
      this.scrollBar.doRender(par1, par2, par3);
   }
}
