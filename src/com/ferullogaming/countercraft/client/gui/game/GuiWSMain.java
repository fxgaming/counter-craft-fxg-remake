package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.game.GameManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiWSMain extends GuiWeaponSelection {
   private String team = "";

   public GuiWSMain(GuiScreen par1, String par2) {
      super(par1);
      this.team = par2;
      this.addOption("Винтовки", new String[]{"Выбор штурмовых", "винтовок."});
      this.addOption("ПП", new String[]{"Выбор ПП."});
      this.addOption("Тяжелое", new String[]{"Выбор тяжелого", "оружия."});
      this.addOption("Снайперское", new String[]{"Выбор снайперских", "винтовок."});
      this.addOption("Пистолеты", new String[]{"Выбор пистолетов и т.д."});
      this.addOption("Гранаты", new String[]{"Гранаты"});
      this.addOption("Экипировка", new String[]{"Экипировка"});
   }

   public void updateScreen() {
      super.updateScreen();
      if (GameManager.instance().currentClientGame != null && GameManager.instance().currentClientGame.getPlayerEventHandler() != null && GameManager.instance().currentClientGame.getPlayerEventHandler().getPlayerTeam((EntityPlayer)super.mc.thePlayer) != null && !this.team.equalsIgnoreCase(GameManager.instance().currentClientGame.getPlayerEventHandler().getPlayerTeam((EntityPlayer)super.mc.thePlayer).teamName)) {
         super.mc.displayGuiScreen((GuiScreen)null);
      }

   }

   public void onOptionClicked(String par1, int par2) {
      if (par2 == 1) {
         super.mc.displayGuiScreen((new GuiWSRifles(this, this.team)).setPriced(super.theEconomy));
      }

      if (par2 == 2) {
         super.mc.displayGuiScreen((new GuiWSSMG(this, this.team)).setPriced(super.theEconomy));
      }

      if (par2 == 3) {
         super.mc.displayGuiScreen((new GuiWSHeavy(this, this.team)).setPriced(super.theEconomy));
      }

      if (par2 == 4) {
         super.mc.displayGuiScreen((new GuiWSSniper(this, this.team)).setPriced(super.theEconomy));
      }

      if (par2 == 5) {
         super.mc.displayGuiScreen((new GuiWSPistols(this, this.team)).setPriced(super.theEconomy));
      }

      if (par2 == 6) {
         super.mc.displayGuiScreen((new GuiWSGrenade(this, this.team)).setPriced(super.theEconomy));
      }

      if (par2 == 7) {
         super.mc.displayGuiScreen((new GuiWSEquipment(this, this.team)).setPriced(super.theEconomy));
      }

   }
}
