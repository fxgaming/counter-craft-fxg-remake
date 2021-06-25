package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketToggleNotification_FriendRequests;
import com.ferullogaming.countercraft.client.cloud.packet.PacketToggleNotification_Poke;
import com.ferullogaming.countercraft.client.cloud.packet.PacketToggleNotification_TradeRequests;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuSettingsNotifications extends GuiCCMenu {
   GuiFGButton togglePoke = new GuiFGButton(0, 0, 0, 0, 0, "");
   GuiFGButton toggleFriendRequest = new GuiFGButton(0, 0, 0, 0, 0, "");
   GuiFGButton toggleTraderRequest = new GuiFGButton(0, 0, 0, 0, 0, "");

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int middleX = super.width / 2;
      int buttonWidth = 120;
      this.togglePoke = new GuiFGButton(11, middleX - buttonWidth - 2, 102, buttonWidth, 20, "Friend Pokes");
      this.toggleFriendRequest = new GuiFGButton(12, middleX - buttonWidth - 2, 54, buttonWidth, 20, "Запросы на Дружбу");
      this.toggleTraderRequest = new GuiFGButton(13, middleX - buttonWidth - 2, 78, buttonWidth, 20, "Запросы на Торговлю");
      super.buttonList.add(this.toggleFriendRequest);
      super.buttonList.add(this.toggleTraderRequest);
      super.buttonList.add(this.togglePoke);
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

      if (par1GuiButton.id == 11) {
         ClientCloudManager.sendPacket(new PacketToggleNotification_Poke());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsNotifications());
      }

      if (par1GuiButton.id == 12) {
         ClientCloudManager.sendPacket(new PacketToggleNotification_FriendRequests());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsNotifications());
      }

      if (par1GuiButton.id == 13) {
         ClientCloudManager.sendPacket(new PacketToggleNotification_TradeRequests());
         super.mc.displayGuiScreen(new GuiCCMenuSettingsNotifications());
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
      this.togglePoke.displayString = "Friend Pokes (" + cloud.enableNotification_FriendPoke + ")";
      this.toggleFriendRequest.displayString = "Запрос на дружбу (" + cloud.enableNotification_FriendRequest + ")";
      this.toggleTraderRequest.displayString = "Запрос на торговлю (" + cloud.enableNotification_TradeRequest + ")";
      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Настройки уведомлений", super.width / 2, 35);
   }
}
