package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTogglePrivacy_FriendRequests;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTogglePrivacy_Profile;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTogglePrivacy_TradeRequests;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuSettingsPrivacy extends GuiCCMenu {
   GuiFGButton buttonProfile = new GuiFGButton(0, 0, 0, 0, 0, "");
   GuiFGButton buttonTradeRequests = new GuiFGButton(0, 0, 0, 0, 0, "");
   GuiFGButton buttonFriendRequests = new GuiFGButton(0, 0, 0, 0, 0, "");

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int middleX = super.width / 2;
      int buttonWidth = 120;
      this.buttonProfile = (new GuiFGButton(11, middleX - buttonWidth - 2, 54, buttonWidth, 20, "Профиль")).setToolTip("Let users view your profile", Color.black);
      this.buttonTradeRequests = (new GuiFGButton(12, middleX - buttonWidth - 2, 78, buttonWidth, 20, "Запросы на торговлю")).setToolTip("Let users send trade requests", Color.black);
      this.buttonFriendRequests = (new GuiFGButton(13, middleX - buttonWidth - 2, 102, buttonWidth, 20, "Запросы на дружбу")).setToolTip("Let users send friend requests", Color.black);
      super.buttonList.add(this.buttonProfile);
      super.buttonList.add(this.buttonTradeRequests);
      super.buttonList.add(this.buttonFriendRequests);
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
      if (par1GuiButton.id == 11) {
         ClientCloudManager.sendPacket(new PacketTogglePrivacy_Profile());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsPrivacy());
      }

      if (par1GuiButton.id == 12) {
         ClientCloudManager.sendPacket(new PacketTogglePrivacy_TradeRequests());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsPrivacy());
      }

      if (par1GuiButton.id == 13) {
         ClientCloudManager.sendPacket(new PacketTogglePrivacy_FriendRequests());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsPrivacy());
      }

      if (par1GuiButton.id == 30) {
         super.mc.displayGuiScreen(new GuiCCMenuSettings());
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
      this.buttonProfile.displayString = "Профиль (" + cloud.enableProfileViewing + ")";
      this.buttonTradeRequests.displayString = "Запросы на торговлю (" + cloud.enableTradeRequests + ")";
      this.buttonFriendRequests.displayString = "Запросы на држбу (" + cloud.enableFriendRequests + ")";
      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Настройки конфидициальности", super.width / 2, 35);
   }
}
