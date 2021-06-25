package com.ferullogaming.countercraft.client.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.client.gui.supportskin.GuiSupportSkins;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import com.ferullogaming.countercraft.utils.Utils_ForgeFixer;
import com.ferullogaming.countercraft.utils.Utils_Updater;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenu extends GuiFGScreen {
   private static final ResourceLocation MENU_MUSIC = new ResourceLocation("countercraft", "gui.menu.music");
   public static ResourceLocation MENU_BACKGROUND = new ResourceLocation("countercraft:textures/gui/background.png");
   public static String menuTheme = "0x353A3D";
   public static String menuTheme2 = "0x2A3035";
   public static String menuTheme3 = "0x202325";
   public static GuiCCMenu.NewsText currentText;
   private static double smokeX;
   private static double lastSmokeX;
   private static double smoke2X;
   private static double lastSmoke2X;
   private static int delayNewItem;
   public ResourceLocation iconHome = new ResourceLocation("countercraft:textures/gui/home.png");
   public ResourceLocation iconSettings = new ResourceLocation("countercraft:textures/gui/settings.png");
   public ResourceLocation iconPower = new ResourceLocation("countercraft:textures/gui/power.png");
   ResourceLocation smoke1 = new ResourceLocation("countercraft:textures/gui/smoke.png");
   ResourceLocation smoke2 = new ResourceLocation("countercraft:textures/gui/smoke.png");
   public static GuiFGButton fgb;

   public static void openURL(String par1) {
      try {
         if (Desktop.isDesktopSupported()) {
            try {
               Desktop.getDesktop().browse(new URI(par1));
            } catch (URISyntaxException var2) {
               var2.printStackTrace();
            }
         }
      } catch (UnsupportedOperationException var3) {
         var3.printStackTrace();
      } catch (MalformedURLException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public void initGui() {
      super.initGui();
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      Iterator i$ = ((ArrayList)super.buttonList).iterator();
      if (smokeX > (double)(-super.width)) {
         lastSmokeX = smokeX;
         smokeX -= 0.5D;
      } else {
         lastSmokeX = 0.0D;
         smokeX = 0.0D;
      }

      if (smoke2X < (double)super.width) {
         lastSmoke2X = smoke2X;
         smoke2X += 0.2D;
      } else {
         lastSmoke2X = 0.0D;
         smoke2X = 0.0D;
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.addContainerDrawing(par1, par2, par3);
      this.drawButtons(par1, par2, par3);
      ClientVariables.menuRotation = (float)((super.width - par1) / 8 - 80);
      ClientVariables.menuRotation2 = (float)((super.height - par2) / 8 - 10);
   }

   public void addOverheadMenuActionPerformed(GuiButton par1GuiButton) {
      int x = super.width / 2 - 173;
      if (par1GuiButton.id == 1) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
      }

      if (par1GuiButton.id == 2) {
         GuiLG dropDown = new GuiLG(this, x + 32, 23, 85, 20);
         super.mc.displayGuiScreen(dropDown);
      }

      if (par1GuiButton.id == 3) {
         super.mc.displayGuiScreen(new GuiCCMenuFindMatch());
      }

      if (par1GuiButton.id == 4) {
         super.mc.displayGuiScreen(new GuiSupportSkins(this));
      }

      if (par1GuiButton.id == 5) {
         GuiCCDDMSettings dropDown = new GuiCCDDMSettings(this, x + 290, 23, 85, 20);
         super.mc.displayGuiScreen(dropDown);
      }

      if (par1GuiButton.id == 6) {
         if (super.mc.theWorld != null) {
            super.mc.displayGuiScreen((GuiScreen)null);
         } else {
            super.mc.shutdown();
         }
      }

      if (par1GuiButton.id == 7 && !Utils_Updater.isUpdating) {
         Utils_Updater.initializeUpdateClient();
      }
      
      if (par1GuiButton.id == 8) {
         currentText = GuiCCMenu.NewsText.NEWS;
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
      }

      if (par1GuiButton.id == 9) {
         currentText = GuiCCMenu.NewsText.UPDATES;
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
      }

      if (par1GuiButton.id == 10) {
         currentText = GuiCCMenu.NewsText.COMMUNITY;
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
      }
	
   }

   public void drawIntendedBackground(float par1) {
      if (super.mc != null && super.mc.theWorld != null) {
         CCRenderHelper.drawRect(0.0D, 0.0D, (double)super.width, (double)super.height, "0x000000", 0.8F);
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)super.mc.thePlayer);
         int mwidth = super.width / 2;
         int mheight = super.height / 2;
         if (playerData != null && playerData.ghostViewing != null && PlayerDataHandler.getPlayerData(playerData.ghostViewing).isGhost) {
            CCRenderHelper.drawRect(0.0D, 0.0D, (double)super.width, (double)super.height, "0x000000", 255.0F);
         }
      } else {
         GL11.glPushMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         CCRenderHelper.drawImage(0.0D, 0.0D, MENU_BACKGROUND, (double)super.width, (double)super.height);
         GL11.glPushMatrix();
         GL11.glScalef(1.5F, 1.5F, 1.5F);
         GL11.glEnable(3042);
         double smoothSmokeX = lastSmokeX + (smokeX - lastSmokeX) * (double)par1;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.21F);
         CCRenderHelper.drawImage(smoothSmokeX, 0.0D, this.smoke1, (double)super.width, (double)super.height);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.21F);
         CCRenderHelper.drawImage(smoothSmokeX + (double)super.width, 0.0D, this.smoke2, (double)super.width, (double)super.height);
         GL11.glEnable(3042);
         double smoothSmoke2X = lastSmoke2X + (smoke2X - lastSmoke2X) * (double)par1;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.21F);
         CCRenderHelper.drawImage(smoothSmoke2X, 0.0D, this.smoke1, (double)super.width, (double)super.height);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.21F);
         CCRenderHelper.drawImage(smoothSmoke2X - (double)super.width, 0.0D, this.smoke2, (double)super.width, (double)super.height);
         GL11.glPopMatrix();
         GL11.glPopMatrix();
         CCRenderHelper.drawRectWithShadow(0.0D, 0.0D, (double)super.width, 22.0D, menuTheme, 1.0F);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.GRAY + "MUI v" + CounterCraft.MOD_VERSION, 2, 2, 0.5D, 1);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.GRAY + "FPS: " + Minecraft.getMinecraft().debug.split(",")[0], 2, 8, 0.5D, 1);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.GRAY + "©ARR. Using by FXG since 2018.", 3, super.height - 5, 0.5D, 1);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.GRAY + References.URL_WEBSITE.replace("https://", "").replace("http://", "").replace("/", ""), super.width - 55, super.height - 5, 0.5D, 1);
      }

   }

   public void drawBackground(float par1) {
	   PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
       this.drawIntendedBackground(par1);
   }

   public void drawButtons(int par1, int par2, float par3) {
      for(int k = 0; k < super.buttonList.size(); ++k) {
         GuiButton guibutton = (GuiButton)super.buttonList.get(k);
         guibutton.drawButton(super.mc, par1, par2);
      }
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void initOverheadMenu() {
      int x = super.width / 2 - 173;
      int y = 1;
      int buttonWidth = 85;
      super.buttonList.add(new GuiFGButton(2, x + 205, y, buttonWidth, 20, "Играть"));
      super.buttonList.add(new GuiFGButton(1, x + 1, y, 30, 20, this.iconHome));
      super.buttonList.add(new GuiFGButton(3, x + 32, y, buttonWidth, 20, "Подбор игр")); 
      super.buttonList.add(fgb = new GuiFGButton(4, x + 33 + buttonWidth, y, buttonWidth, 20, "Скины и Стикеры"));
      super.buttonList.add(new GuiFGButton(5, x + 35 + 3 * buttonWidth, y, 30, 20, this.iconSettings));
      if (super.mc.theWorld == null) {
         super.buttonList.add(new GuiFGButton(6, x + 66 + 3 * buttonWidth, y, 30, 20, this.iconPower));
      } else {
         super.buttonList.add(new GuiFGButton(6, x + 66 + 3 * buttonWidth, y, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      }
   }

   static {
      currentText = GuiCCMenu.NewsText.NEWS;
      smokeX = 0.0D;
      smoke2X = 0.0D;
      delayNewItem = 0;
   }

   public static enum NewsText {
      NEWS,
      UPDATES,
      COMMUNITY;
   }
}
