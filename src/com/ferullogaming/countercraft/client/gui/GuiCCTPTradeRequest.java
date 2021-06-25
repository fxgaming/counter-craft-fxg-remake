package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeRequestUser;
import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCTPTradeRequest extends GuiFGTextPrompt {
   public GuiCCTPTradeRequest(GuiScreen par1) {
      super(par1);
      this.addInformation(new String[]{"Создать торговлю", "", "Enter Username you want to trade", "with. Case Sensitive."});
   }

   public void onPromptEntered() {
      String username = this.getTextField();
      if (super.mc.getSession().getUsername().equalsIgnoreCase(username)) {
         ClientTickHandler.addClientNotification(new ClientNotification("Отказ от торговли"));
         super.mc.displayGuiScreen(super.parentGui);
      } else {
         ClientCloudManager.sendPacket(new PacketTradeRequestUser(username));
         super.mc.displayGuiScreen(super.parentGui);
      }
   }
}
