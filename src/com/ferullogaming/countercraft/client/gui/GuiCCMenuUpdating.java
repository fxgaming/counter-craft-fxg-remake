package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.cloud.EnumCompRank;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import com.ferullogaming.countercraft.utils.Utils_Updater;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class GuiCCMenuUpdating extends GuiCCMenu {
   public static PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
   String compRankSound = "countercraft:gui.comprank";
   String compRankCloseSound = "countercraft:gui.comprankclose";

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      GuiCCContainerBanner cont = new GuiCCContainerBanner(2, x + 114, 25, 237, 40, this);
      cont.addButtons((ArrayList)super.buttonList);
      this.addContainer(cont);
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Мой профиль").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
      this.addContainer((new GuiFGContainerText(5, x + 114, 68, 237, 14, this)).setText(EnumChatFormatting.WHITE + "Обновление").setColor(GuiCCMenu.menuTheme3));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      int spacing = super.width / 6;
      int barSize = spacing * 2;
      int yChange = 1;
      int percentage = Utils_Updater.updatePercentageInt;
      int properSize = barSize / 100 * percentage;
      String downloadingParagraph = "Загрузка (" + Utils_Updater.updatePercentage + ")";
      String downloadingFileSize = EnumChatFormatting.GRAY + "" + Utils_Updater.updateSizeDownloaded + "МБ" + EnumChatFormatting.GOLD + "/" + EnumChatFormatting.GRAY + "" + Utils_Updater.updateSize + "МБ";
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 83.0D, 237.0D, 150.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 25.0D, 237.0D, 40.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.renderCenteredTextScaled(downloadingParagraph, x + 114 + 118, 123, 1.0D);
      CCRenderHelper.renderCenteredTextScaled(downloadingFileSize, x + 114 + 118, 133, 1.0D);
      super.drawScreen(par1, par2, par3);
      this.drawButtons(par1, par2, par3);

      try {
         ClientVariables.checkRank();
      } catch (FileNotFoundException var13) {
         var13.printStackTrace();
      }

      this.drawIntroNotification();
   }

   public void drawIntroNotification() {
      ScaledResolution sR = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      Minecraft mc = Minecraft.getMinecraft();
      FontRenderer fr = mc.fontRenderer;
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(mc.getSession().getUsername());
      if (data != null && data.compRank != null) {
         CCRenderHelper.drawRectWithShadow2(0, 0, super.width, super.height, Color.BLACK, ClientVariables.notification_fade);
         String title = EnumChatFormatting.BLUE + "Competitive Rank Changed!";
         String text = EnumChatFormatting.GRAY + "Rank has changed to " + data.compRank.getRankFormatting() + data.compRank.getAsName() + EnumChatFormatting.GRAY + " in Competitive!";
         String close = EnumChatFormatting.GRAY + "<-- Press " + Keyboard.getKeyName(1) + " to Close! -->";
         if (!ClientVariables.hasSeenNotification) {
            if (!ClientVariables.hasPlayedNotificationOpenSound) {
               Minecraft.getMinecraft().sndManager.playSoundFX(this.compRankSound, 1.0F, 1.0F);
               ClientVariables.hasPlayedNotificationOpenSound = true;
            }

            CCRenderHelper.drawRectWithShadow2(super.width / 8 + super.width / 16, super.height / 2 - 60, super.width / 8 * 5, 100, Color.BLACK, 150);
            CCRenderHelper.renderTextScaled(title, super.width / 2 - fr.getStringWidth(title) / 2, super.height / 2 - 50, 1.0D, 0);
            CCRenderHelper.renderTextScaled(text, super.width / 2 - fr.getStringWidth(text) / 2, super.height / 2 - 35, 1.0D, 0);
            CCRenderHelper.renderTextScaled(close, super.width / 2 - fr.getStringWidth(close) / 2, super.height / 2 + 45, 1.0D, 0);
            if (data.compRank != null) {
               EnumCompRank playerCompRank = data.getCompRank();
               String rankResLoc = playerCompRank.getResourceName();
               CCRenderHelper.drawImage((double)(sR.getScaledWidth() / 2 - 45), (double)(sR.getScaledHeight() / 2 - 16), new ResourceLocation("countercraft:textures/misc/compranks/" + rankResLoc + ".png"), 90.0D, 45.0D);
            }

            if (Keyboard.isKeyDown(1)) {
               ClientVariables.hasSeenNotification = true;
            }

            if (ClientVariables.notification_fade < 200) {
               ClientVariables.notification_fade += 20;
            }

            if (ClientVariables.notification_top_y < 0) {
               ClientVariables.notification_top_y += 5;
            }

            if (ClientVariables.notification_bottom_y > sR.getScaledHeight() - 40) {
               ClientVariables.notification_bottom_y -= 5;
            }
         } else {
            if (!ClientVariables.hasPlayedNotificationCloseSound) {
               Minecraft.getMinecraft().sndManager.playSoundFX(this.compRankCloseSound, 1.0F, 1.0F);
               ClientVariables.hasPlayedNotificationCloseSound = true;
            }

            if (ClientVariables.notification_fade > 0) {
               ClientVariables.notification_fade -= 20;
            }

            if (ClientVariables.notification_top_y > -41) {
               ClientVariables.notification_top_y -= 5;
            }

            if (ClientVariables.notification_bottom_y < super.height) {
               ClientVariables.notification_bottom_y += 5;
            }
         }
      } else {
         ClientVariables.hasSeenNotification = true;
      }

   }
}
