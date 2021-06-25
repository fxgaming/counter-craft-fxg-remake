package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import com.ferullogaming.countercraft.network.CCPacketVote_KickPlayer;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCTPVoteKick extends GuiFGTextPrompt {
   public GuiCCTPVoteKick(GuiScreen par1) {
      super(par1);
      this.addInformation(new String[]{EnumChatFormatting.WHITE + "Введите ник", EnumChatFormatting.WHITE + "", EnumChatFormatting.WHITE + "Введите ник игрока.", EnumChatFormatting.WHITE + "ЧуВсТвИтЕлЬнО к регистру."});
   }

   public void onPromptEntered() {
      String var1 = this.getTextField();
      System.out.println("Sending vote pack to kick player " + var1);
      PacketDispatcher.sendPacketToServer(CCPacketVote_KickPlayer.buildPacket(var1));
      super.mc.displayGuiScreen(super.parentGui);
   }
}
