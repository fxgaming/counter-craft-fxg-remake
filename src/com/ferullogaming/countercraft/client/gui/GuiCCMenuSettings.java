package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.ModSettings;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import com.ferullogaming.countercraft.utils.Utils_Updater;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiCCMenuSettings extends GuiCCMenu {
   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int middleX = super.width / 2;
      int buttonWidth = 120;
      super.buttonList.add((new GuiFGButton(10, middleX - buttonWidth - 2, 30, buttonWidth, 20, "Автопроизводительность")).setToolTip("Автоматическая настройка параметров видео", Color.black));
      super.buttonList.add((new GuiFGButton(11, middleX - buttonWidth - 2, 54, buttonWidth, 20, "Уведомления")).setToolTip("Управление настройками уведомлений", Color.black));
      super.buttonList.add((new GuiFGButton(12, middleX - buttonWidth - 2, 78, buttonWidth, 20, "Конфиденциальность")).setToolTip("Управление настройками конфиденциальности", Color.black));
      super.buttonList.add(new GuiFGButton(13, middleX - buttonWidth - 2, 102, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(14, middleX - buttonWidth - 2, 126, buttonWidth, 20, "---"));
      if (ModSettings.instance().getDefaultCrosshair() == null) {
         super.buttonList.add((new GuiFGButton(20, middleX + 2, 30, buttonWidth, 20, "Перекрестие: нет")).setToolTip("У вас пока нет перекрестия!", Color.black));
      } else {
         super.buttonList.add((new GuiFGButton(20, middleX + 2, 30, buttonWidth, 20, "Crosshair: " + ModSettings.instance().getDefaultCrosshair().name)).setToolTip("Пользовательский перекрестье (" + ModSettings.instance().getDefaultCrosshair().name + ")", Color.black));
      }

      super.buttonList.add((new GuiFGButton(21, middleX + 2, 54, buttonWidth, 20, "Обновление")).setToolTip("Принудительное обновление до последнего обновления версии", Color.black));
      super.buttonList.add((new GuiFGButton(22, middleX + 2, 78, buttonWidth, 20, "Supporter")).setToolTip("Управление настройками саппорта", Color.black));
      super.buttonList.add(new GuiFGButton(23, middleX + 2, 102, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(24, middleX + 2, 126, buttonWidth, 20, "---"));
      super.buttonList.add(new GuiFGButton(30, middleX - buttonWidth / 2, 174, buttonWidth, 20, "Назад"));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      PlayerDataCloud playerDataCloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      int middleX = super.width / 2;
      int buttonWidth = 1;
      if (par1GuiButton.id == 11) {
         super.mc.displayGuiScreen(new GuiCCMenuSettingsNotifications());
      }

      if (par1GuiButton.id == 12) {
         super.mc.displayGuiScreen(new GuiCCMenuSettingsPrivacy());
      }

      if (par1GuiButton.id == 20) {
         super.mc.displayGuiScreen(new GuiCCDDMSettingsCrosshair(this, middleX + 2 + 10, 49, 100, 15));
      }

      if (par1GuiButton.id == 21 && !Utils_Updater.isUpdating) {
         Utils_Updater.initializeUpdateClient();
      }

      if (par1GuiButton.id == 22) {
         if (playerDataCloud.isSupporter) {
            super.mc.displayGuiScreen(new GuiCCMenuSettingsSupporter());
         } else {
            ClientNotification not = new ClientNotification("You must be a Supporter to access these options!");
            ClientTickHandler.addClientNotification(not);
         }
      }

      if (par1GuiButton.id == 30) {
         super.mc.displayGuiScreen(new GuiCCMenuHome());
      }

   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      this.drawButtons(par1, par2, par3);
      super.drawScreen(par1, par2, par3);
   }
}
