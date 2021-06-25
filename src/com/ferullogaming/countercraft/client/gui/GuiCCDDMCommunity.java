package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCConversationMenu;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCFriendMenu;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCProfile;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMCommunity extends GuiFGDropDownMenu {
   public GuiCCDDMCommunity(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Мой Профиль");
      this.addOption("Мои Друзья");
      this.addOption("Кланы");
      this.addOption("Наш сервер Discord");
      this.addOption("Форум");
      this.addOption("Сообщения");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCProfile(super.parentGui, super.mc.getSession().getUsername()));
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen(new GuiCCFriendMenu(super.parentGui));
      }

      if (par1 == 3) {
         GuiCCDDMClans gui = new GuiCCDDMClans(super.parentGui, super.buttonX + super.buttonWidth + 1, super.buttonY + 42, super.buttonWidth, super.buttonHeight);
         super.mc.displayGuiScreen(gui);
      }

      if (par1 == 4) {
         //GuiCCMenu.openURL(References.URL_DISCORD);
      }

      if (par1 == 5) {
         //GuiCCMenu.openURL(References.URL_FORUMS);
      }

      if (par1 == 6) {
         super.mc.displayGuiScreen(new GuiCCConversationMenu(this));
      }

   }
}
