package com.ferullogaming.countercraft.client.gui;

import java.util.Calendar;
import java.util.Date;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiCBanner extends GuiFGContainer {
   public GuiCBanner(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground();
      double var10000 = (double)(super.posX + 1);
      double var10001 = (double)(super.posY + 1);
      double var10003 = (double)(super.width - 2);
      FontRenderer f = Minecraft.getMinecraft().fontRenderer;
      Double cdadata0 = Double.valueOf((String)CounterCraft.Data[0])/10;
      String cdadata1 = (String)CounterCraft.Data[3];
      f.drawStringWithShadow("Версия мода: " + CounterCraft.MOD_VERSION, (int)var10000+1, (int)var10001+1, 123123);
      f.drawStringWithShadow("Следующая версия мода: " + cdadata0, (int)var10000+1, (int)var10001+10, 123123);
      f.drawStringWithShadow("Следующее обновление в: " + cdadata1, (int)var10000+1, (int)var10001+19, 123123);
   }
}
