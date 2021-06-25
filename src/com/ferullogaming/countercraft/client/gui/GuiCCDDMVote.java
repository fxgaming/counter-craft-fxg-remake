package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.game.GuiCCTPVoteKick;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMVote extends GuiFGDropDownMenu {
   public GuiCCDDMVote(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      this.addOption("Голосование кика...");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         GuiCCTPVoteKick gui = new GuiCCTPVoteKick(this);
         super.mc.displayGuiScreen(gui);
      }
   }
}
