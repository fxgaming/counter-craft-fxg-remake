package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketEditStatus;
import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCTPEditMood extends GuiFGTextPrompt {
   public GuiCCTPEditMood(GuiScreen par1) {
      super(par1);
      super.maxCharacters = 36;
      this.addInformation(new String[]{"Edit your 'Status'", "", "Max Length is 36 Characters", ""});
   }

   public void onPromptEntered() {
      String newMood = this.getTextField();
      ClientTickHandler.addClientNotification(new ClientNotification("Updating Status..."));
      ClientCloudManager.sendPacket(new PacketEditStatus(newMood));
      super.mc.displayGuiScreen(super.parentGui);
   }
}
