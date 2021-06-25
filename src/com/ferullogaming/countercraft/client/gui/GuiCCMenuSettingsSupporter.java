package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.supporter.PacketToggleSupporter_Cape;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuSettingsSupporter extends GuiCCMenu {
   GuiFGButton toggleCape = new GuiFGButton(0, 0, 0, 0, 0, "");

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int middleX = super.width / 2;
      int buttonWidth = 120;
      this.toggleCape = new GuiFGButton(12, middleX - buttonWidth - 2, 54, buttonWidth, 20, "In-game Cape");
      super.buttonList.add(this.toggleCape);
      super.buttonList.add(new GuiFGButton(13, middleX - buttonWidth - 2, 78, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(11, middleX - buttonWidth - 2, 102, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(14, middleX - buttonWidth - 2, 126, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(21, middleX + 2, 54, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(22, middleX + 2, 78, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(23, middleX + 2, 102, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(24, middleX + 2, 126, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(30, middleX - buttonWidth / 2, 150, buttonWidth, 20, "Назад"));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      int middleX = super.width / 2;
      int buttonWidth = 1;
      if (par1GuiButton.id == 30) {
         super.mc.displayGuiScreen(new GuiCCMenuSettings());
      }

      if (par1GuiButton.id == 12) {
         ClientCloudManager.sendPacket(new PacketToggleSupporter_Cape());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsSupporter());
      }

   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      this.drawButtons(par1, par2, par3);
      super.drawScreen(par1, par2, par3);
      String username = Minecraft.getMinecraft().getSession().getUsername();
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(username);
      this.toggleCape.displayString = "In-game Cape (" + cloud.enableCape + ")";
      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Настройки саппорта", super.width / 2, 35);
   }
}
