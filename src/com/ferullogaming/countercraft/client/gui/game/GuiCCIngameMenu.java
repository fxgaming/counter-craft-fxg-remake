package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.network.CCPacketGameSwitchTeam;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Color;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCIngameMenu extends GuiFGScreen implements IGuiFGPromptYesNo {
   public static int switchTeamCooldown = 0;

   public void initGui() {
      int mx = super.width / 2;
      int my = super.height / 2;
      int by = my - 60;
      int bw = 150;
      int bh = 15;
      GuiFGButton button = new GuiFGButton(1, mx - bw / 2, by, bw, bh, "Продолжить игру");
      button.centeredText = false;
      button.drawBackground = false;
      super.buttonList.add(button);
      button = (new GuiFGButton(2, mx - bw / 2, by + bh * 1, bw, bh, "Начать голосование кика..")).setToolTip("Голосование на кик игрока", Color.black);
      button.centeredText = false;
      button.drawBackground = false;
      super.buttonList.add(button);
      button = (new GuiFGButton(3, mx - bw / 2, by + bh * 2, bw, bh, "Сменить команду")).setToolTip("Меняйте команду в игре", Color.black);
      button.centeredText = false;
      button.drawBackground = false;
      if (switchTeamCooldown > 0) {
         button.enabled = false;
      }

      super.buttonList.add(button);
      button = (new GuiFGButton(4, mx - bw / 2, by + bh * 3, bw, bh, "Настройки...")).setToolTip("Настроить игру", Color.black);
      button.centeredText = false;
      button.drawBackground = false;
      super.buttonList.add(button);
      button = new GuiFGButton(5, mx - bw / 2, by + bh * 5, bw, bh, EnumChatFormatting.RED + "Выйти в меню.");
      button.centeredText = false;
      button.drawBackground = false;
      super.buttonList.add(button);
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      if (par1GuiButton.id == 1) {
         super.mc.displayGuiScreen((GuiScreen)null);
         super.mc.setIngameFocus();
         super.mc.sndManager.resumeAllSounds();
      }

      if (par1GuiButton.id == 2) {
         GuiCCTPVoteKick gui = new GuiCCTPVoteKick(this);
         super.mc.displayGuiScreen(gui);
      }

      GuiFGYesNoPrompt gui;
      if (par1GuiButton.id == 3) {
         gui = new GuiFGYesNoPrompt(1, this);
         gui.addInformation("Продолжить смену команд?");
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 4) {
         super.mc.displayGuiScreen(new GuiOptions(this, super.mc.gameSettings));
      }

      if (par1GuiButton.id == 5) {
         gui = new GuiFGYesNoPrompt(2, this);
         gui.addInformation("Вы точно хотите покинуть игру?");
         super.mc.displayGuiScreen(gui);
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      int mx = super.width / 2;
      int my = super.height / 2;
      int by = my - 60;
      int bw = 150;
      int bh = 1;
      CCRenderHelper.drawRectWithShadow((double)(mx - bw / 2 - 5), (double)(by - 25), (double)(bw + 10), 20.0D, "0x000000", 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(mx - bw / 2 - 5), (double)(by - 5), (double)(bw + 10), 115.0D, "0x000000", 0.5F);
      CCRenderHelper.renderText("Игровое меню", mx - bw / 2, by - 19);
      super.drawScreen(par1, par2, par3);
   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2 && switchTeamCooldown <= 0) {
         PacketDispatcher.sendPacketToServer(CCPacketGameSwitchTeam.buildPacket());
         switchTeamCooldown = 2400;
         super.mc.displayGuiScreen((GuiScreen)null);
         super.mc.setIngameFocus();
         super.mc.sndManager.resumeAllSounds();
      }

      if (par1 == 2 && par2) {
         super.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
         super.mc.theWorld.sendQuittingDisconnectingPacket();
         super.mc.loadWorld((WorldClient)null);
         super.mc.displayGuiScreen(new GuiMainMenu());
      }

   }
}
