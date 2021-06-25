package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCMenuCreateParty;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;

public class GuiCCDDMPlay extends GuiFGDropDownMenu {
   public GuiCCDDMPlay(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (GuiCCMenuFindMatch.isSearching) {
         this.addOption("Найти игру");
      } else if (data.getParty() != null) {
         this.addOption("Групповая Игра");
      } else if (Minecraft.getMinecraft().theWorld != null) {
         this.addOption("Найти игру");
         this.addOption("Создать группу");
      } else {
         this.addOption("Найти игру");
         this.addOption("Создать группу");
         this.addOption("Сервера сообщества");
         this.addOption("Одиночная игра");
      }
   }

   public void onOptionClicked(int par1) {
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (data.getParty() != null) {
         if (par1 == 1) {
            super.mc.displayGuiScreen(new GuiCCMenuCreateParty(false));
         }

      } else if (GuiCCMenuFindMatch.isSearching) {
         if (par1 == 1) {
            super.mc.displayGuiScreen(new GuiCCMenuFindMatch());
         }

      } else if (Minecraft.getMinecraft().theWorld != null) {
         if (par1 == 1) {
            super.mc.displayGuiScreen(new GuiCCMenuFindMatch());
         }

         if (par1 == 2) {
            super.mc.displayGuiScreen(new GuiCCMenuCreateParty(true));
         }

      } else {
         if (par1 == 1) {
            super.mc.displayGuiScreen(new GuiCCMenuFindMatch());
         }

         if (par1 == 2) {
            super.mc.displayGuiScreen(new GuiCCMenuCreateParty(true));
         }

         if (par1 == 3) {
            super.mc.displayGuiScreen(new GuiMultiplayer(new GuiCCMenuHome()));
         }

         if (par1 == 4) {
            super.mc.displayGuiScreen(new GuiSelectWorld((GuiScreen)null));
         }

      }
   }
}
