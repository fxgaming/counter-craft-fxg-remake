package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeRequestUser;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyKick;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMFriendWhileParty extends GuiFGDropDownMenu {
   private String username;

   public GuiCCDDMFriendWhileParty(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.username = par6;
      this.addOption("Poke");
      this.addOption("Message");
      this.addOption("Profile");
      this.addOption("Trade Invite");
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (data.getParty() != null && data.getParty().isHost(data.getUsername())) {
         this.addOption("Kick");
      }

   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCTPPoke(super.parentGui, this.username));
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen((new GuiCCConversationMenu(super.parentGui)).setViewing(this.username));
      }

      if (par1 == 3) {
         super.mc.displayGuiScreen(new GuiCCProfile(super.parentGui, this.username));
      }

      if (par1 == 4) {
         if (super.mc.theWorld == null) {
            ClientCloudManager.sendPacket(new PacketTradeRequestUser(this.username));
            ClientTickHandler.addClientNotification(new ClientNotification("Requesting a Trade with " + this.username + ", please wait."));
         } else {
            ClientTickHandler.addClientNotification(new ClientNotification("Can not trade from in game."));
         }
      }

      if (par1 == 5) {
         ClientCloudManager.sendPacket(new PacketPartyKick(this.username));
      }

   }
}
