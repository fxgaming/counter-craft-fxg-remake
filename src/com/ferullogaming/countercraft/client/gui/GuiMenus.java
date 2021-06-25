package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiMenus extends GuiFGScreen {
   public static ResourceLocation background = null;
   public ResourceLocation iconHome = new ResourceLocation("craftingdead:textures/gui/menu/home.png");
   public ResourceLocation iconSettings = new ResourceLocation("craftingdead:textures/gui/menu/settings.png");
   public ResourceLocation iconPower = new ResourceLocation("craftingdead:textures/gui/menu/power.png");

   public void initGui() {
      super.initGui();
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
   }

   public void addOverheadMenuActionPerformed(GuiButton par1GuiButton) {
      int x = super.width / 2 - 173;
      if (par1GuiButton.id == 1) {
         super.mc.displayGuiScreen(new GuiMenuHomes());
      }

      if (par1GuiButton.id == 2) {
         GuiLG dropDown = new GuiLG(this, x + 32, 23, 85, 20);
         super.mc.displayGuiScreen(dropDown);
      }

      if (par1GuiButton.id == 3) {
    	  this.openURL(ClientManager.instance().WEB_VK);
      }

      if (par1GuiButton.id == 4) {
         this.openURL(ClientManager.instance().WEB_FORUMS);
      }

      if (par1GuiButton.id == 5) {
         GuiCCDDMSettings dropDown = new GuiCCDDMSettings(this, x + 35 + 255, 23, 85, 20);
         super.mc.displayGuiScreen(dropDown);
      }

      if (par1GuiButton.id == 6) {
         super.mc.shutdown();
      }

   }

   public void drawBackground(int par1, int par2, float par3) {
      if (background == null) {
         Random rand = new Random();
         background = new ResourceLocation("craftingdead:textures/gui/menu/background_" + rand.nextInt(4) + ".png");
      }
      CCRenderHelper.drawImage(0.0D, 0.0D, background, (double)super.width, (double)super.height);
      CCRenderHelper.drawRectWithShadow(0, 0, super.width, 22, "0x333333", 1.0F);
      Integer cml = Integer.valueOf((String)CounterCraft.Data[1]);
      String[] cmt = (String[])CounterCraft.Data[2];
      Integer var1 = Integer.valueOf((String)CounterCraft.Data[1]);
      String[] var3 = (String[])CounterCraft.Data[2];

      CCRenderHelper.drawRectWithShadow(super.width / 2 - 69, 68, 247, 157, "0x333333", 1.0F);
      try {
        	for (int var2 = 0; var2 < (var1+1); ++var2) {
        		super.mc.fontRenderer.drawStringWithShadow(var3[var2], this.width / 2 - 66, 70 + (10*(var2)), 123400);	
        	}
        }
      catch(Exception e) {}
   }

   public void drawButtons(int par1, int par2, float par3) {
      for(int k = 0; k < super.buttonList.size(); ++k) {
         GuiButton guibutton = (GuiButton)super.buttonList.get(k);
         guibutton.drawButton(super.mc, par1, par2);
      }

   }

   public void initOverheadMenu() {
      int x = super.width / 2 - 173;
      int y = 1;
      int buttonWidth = 85;
      super.buttonList.add(new GuiFGButton(1, x + 1, y, 30, 20, this.iconHome));
      super.buttonList.add(new GuiFGButton(2, x + 32, y, buttonWidth, 20, EnumChatFormatting.BOLD + "Играть"));
      super.buttonList.add(new GuiFGButton(3, x + 33 + buttonWidth, y, buttonWidth, 20, EnumChatFormatting.BOLD + "Группа ВК"));
      super.buttonList.add(new GuiFGButton(4, x + 34 + 2 * buttonWidth, y, buttonWidth, 20, EnumChatFormatting.BOLD + "Сайт"));
      super.buttonList.add(new GuiFGButton(5, x + 35 + 3 * buttonWidth, y, 30, 20, this.iconSettings));
      super.buttonList.add(new GuiFGButton(6, x + 66 + 3 * buttonWidth, y, 30, 20, this.iconPower));
   }

   protected void openURL(String par1) {
      if (!par1.startsWith("http://")) {
         par1 = "http://" + par1;
      }

      try {
         if (Desktop.isDesktopSupported()) {
            try {
               Desktop.getDesktop().browse(new URI(par1));
            } catch (URISyntaxException var3) {
               var3.printStackTrace();
            }
         }
      } catch (MalformedURLException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
