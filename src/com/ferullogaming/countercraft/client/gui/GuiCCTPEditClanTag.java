package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketEditClanTag;
import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCTPEditClanTag extends GuiFGTextPrompt {
   public GuiCCTPEditClanTag(GuiScreen par1) {
      super(par1);
      super.maxCharacters = 4;
      this.addInformation(new String[]{"Отредактируйте свое «Название Клана»", "Максимальная длина " + super.maxCharacters + " символов", ""});
   }

   public void onPromptEntered() {
      String newClanTag = this.getTextField();
      ClientCloudManager.sendPacket(new PacketEditClanTag(newClanTag));
      super.mc.displayGuiScreen(new GuiCCMenuHome());
   }
}
