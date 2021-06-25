package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectRegion extends GuiFGDropDownMenu {
   public GuiCCDDMSelectRegion(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      this.addOption("Все сервера");
      this.addOption("Сервера Європы");
      this.addOption("Сервера Америки");
   }

   public void onOptionClicked(int par1) {
      String region = "ALL";
      switch(par1) {
      case 0:
         region = "ALL";
         break;
      case 1:
         region = "ALL";
         break;
      case 2:
         region = "EU";
         break;
      case 3:
         region = "US";
         break;
      default:
         region = "ALL";
      }

      GuiCCMenuFindMatch.region = region;
      ((GuiCCMenuFindMatch)super.parentGui).searchForGame();
      super.mc.displayGuiScreen(super.parentGui);
   }
}
