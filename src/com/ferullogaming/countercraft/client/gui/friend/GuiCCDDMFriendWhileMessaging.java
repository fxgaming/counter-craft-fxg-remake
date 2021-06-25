package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeRequestUser;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMFriendWhileMessaging extends GuiFGDropDownMenu {
   private String username;

   public GuiCCDDMFriendWhileMessaging(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.username = par6;
      this.addOption("Poke");
      this.addOption("Profile");
      this.addOption("Trade Invite");
      this.addOption("Clear Chat");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCTPPoke(super.parentGui, this.username));
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen(new GuiCCProfile(super.parentGui, this.username));
      }

      if (par1 == 3) {
         if (super.mc.theWorld == null) {
            ClientCloudManager.sendPacket(new PacketTradeRequestUser(this.username));
            ClientTickHandler.addClientNotification(new ClientNotification("Requesting a Trade with " + this.username + ", please wait."));
         } else {
            ClientTickHandler.addClientNotification(new ClientNotification("Can not trade from in game."));
         }
      }

      if (par1 == 4) {
         GuiCCConversationMenu menu = (GuiCCConversationMenu)super.parentGui;
         if (GuiCCConversationMenu.playerViewing.length() > 0) {
            ClientManager.instance().getConversationHandler().getConversation(GuiCCConversationMenu.playerViewing).messages.clear();
         }
      }

   }
}
