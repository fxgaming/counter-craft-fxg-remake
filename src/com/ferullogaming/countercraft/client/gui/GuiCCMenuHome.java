package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.Instrumentarium;
import com.ferullogaming.countercraft.client.ModSettings;
import com.ferullogaming.countercraft.client.NewsManager;
import com.ferullogaming.countercraft.client.cloud.EnumCompRank;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScroller;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import com.ferullogaming.countercraft.utils.Utils_ForgeFixer;
import com.ferullogaming.countercraft.utils.Utils_Updater;
import com.google.common.collect.ImmutableList;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiCCMenuHome extends GuiCCMenu {
   public static PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
   private final GuiFGScroller newsContainer = new GuiFGScroller(3, 0, 84, 235, 148, this);
   String compRankSound = "countercraft:gui.comprank";
   String compRankCloseSound = "countercraft:gui.comprankclose";

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      this.newsContainer.posX = x + 115;
      this.newsContainer.shouldRenderBackground = true;
      this.newsContainer.setColor(GuiCCMenu.menuTheme3);
      GuiCCContainerBanner cont = new GuiCCContainerBanner(2, x + 114, 25, 237, 40, this);
      cont.addButtons((ArrayList)super.buttonList);
      this.addContainer(cont);
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Личный кабинет").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
      this.addContainer((new GuiFGContainerText(5, x + 114, 68, 237, 14, this)).setText("").setColor(GuiCCMenu.menuTheme3));
      super.buttonList.add((new GuiFGButton(8, x + 120, 71, 30, 8, GuiCCMenu.currentText == GuiCCMenu.NewsText.NEWS ? EnumChatFormatting.WHITE + "Новости" : EnumChatFormatting.GOLD + "Новости")).setColor("0x000000").setToolTip("Последние новости", Color.black).disableBackground());
      super.buttonList.add((new GuiFGButton(9, x + 158, 71, 50, 8, GuiCCMenu.currentText == GuiCCMenu.NewsText.UPDATES ? EnumChatFormatting.WHITE + "Обновления" : EnumChatFormatting.GOLD + "Обновления")).setColor("0x000000").setToolTip("Последние обновления", Color.black).disableBackground());
      super.buttonList.add((new GuiFGButton(10, x + 212, 71, 52, 8, GuiCCMenu.currentText == GuiCCMenu.NewsText.COMMUNITY ? EnumChatFormatting.WHITE + "Сообщество" : EnumChatFormatting.GOLD + "Сообщество")).setColor("0x000000").setToolTip("Последняя активность сообщества", Color.black).disableBackground());
      int ext = super.fontRenderer.getStringWidth("" + Instrumentarium.USERS_ONLINE);
      super.buttonList.add((new GuiFGButton(11, x + 291 - ext, 70, 58 + ext, 10, "").setColor("0x000000").setToolTip("При запуске игры", Color.black).disableBackground()));
      this.addContainer(this.newsContainer);
      this.initNews();
      
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
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 83.0D, 237.0D, 150.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 25.0D, 237.0D, 40.0D, GuiCCMenu.menuTheme, 1.0F);
      super.drawScreen(par1, par2, par3);
      this.drawButtons(par1, par2, par3);
      String playersOnline = "Общий онлайн: " + EnumChatFormatting.WHITE + Instrumentarium.USERS_ONLINE;
      CCRenderHelper.renderTextScaled(playersOnline, x + 348 - super.fontRenderer.getStringWidth(playersOnline), 71, 1.0D);
      try {
         ClientVariables.checkRank();
      } catch (FileNotFoundException var7) {
      }
      this.drawIntroNotification();
   }

   public void drawIntroNotification() {
      ScaledResolution sR = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      Minecraft mc = Minecraft.getMinecraft();
      FontRenderer fr = mc.fontRenderer;
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(mc.getSession().getUsername());
      ClientVariables.hasSeenNotification = true;
   }

   private void initNews() {
      (new Thread() {
         public void run() {
            try {
               GuiCCMenuHome.this.injectNews(NewsManager.getNewsAsStringList(GuiCCMenu.currentText));
            } catch (IOException var2) {
               var2.printStackTrace();
            }

         }
      }).start();
   }

   private void injectNews(List news) {
	  boolean falser = false;
      if (Utils_ForgeFixer.userNeedsToRestart) {
      } else if (Utils_Updater.hasUpdate()) {
      } else if (falser) {
         this.newsContainer.setTextList(ImmutableList.of("", EnumChatFormatting.RED + "Облако оффлайн!", "Пожулайста сообщите о баге!", "", "<link>&nСообщить<>https://vk.com/xzenforever<>"));
      } else if (news != null) {
         this.newsContainer.setTextList(news);
      } else {
         this.newsContainer.setTextList(ImmutableList.of("Ошибка парсинга новостей!", "Сообщите администраторам."));
      }

   }
}
