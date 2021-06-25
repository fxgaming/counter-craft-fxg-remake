package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeRequestUser;
import com.ferullogaming.countercraft.client.cloud.packet.friend.PacketFriendJoinGame;
import com.ferullogaming.countercraft.client.cloud.packet.friend.PacketFriendRemove;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyInvite;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyJoin;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;

public class GuiCCDDMFriend extends GuiFGDropDownMenu {
   private String username;

   public GuiCCDDMFriend(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.username = par6;
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      PlayerDataCloud data1 = PlayerDataHandler.getPlayerCloudData(this.username);
      this.addOption("Poke");
      this.addOption("Message");
      this.addOption("Profile");
      this.addOption("Invite to Party", data.serverOn == null && data.getParty() != null && data1.getParty() == null);
      this.addOption("Join Party", data.getParty() == null && data1.getParty() != null && data1.serverOn == null);
      this.addOption("Join Game", data1.serverOn != null);
      this.addOption("Trade Invite");
      this.addOption("Remove");
   }

   public void onOptionClicked(int par1) {
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      PlayerDataCloud data1 = PlayerDataHandler.getPlayerCloudData(this.username);
      boolean sameParty = false;
      if (data.getParty() != null && data1.getParty() != null && data.getParty().getHost().equals(data1.getParty().getHost())) {
         sameParty = true;
      }

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
         ClientCloudManager.sendPacket(new PacketPartyInvite(this.username));
      }

      if (par1 == 5) {
         if (data.getParty() == null) {
            if (data1.getParty() != null) {
               if (super.mc.theWorld != null) {
                  super.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
                  super.mc.theWorld.sendQuittingDisconnectingPacket();
                  super.mc.loadWorld((WorldClient)null);
               }

               ClientCloudManager.sendPacket(new PacketPartyJoin(this.username));
            } else {
               ClientTickHandler.addClientNotification(new ClientNotification("This player isn't in a Party!"));
            }
         } else {
            ClientTickHandler.addClientNotification(new ClientNotification("You're already in a Party!"));
         }
      }

      if (par1 == 6) {
         if (data.getParty() == null) {
            if (data1.serverOn != null) {
               if (super.mc.theWorld == null) {
                  ClientCloudManager.sendPacket(new PacketFriendJoinGame(this.username));
               } else if (super.mc.isSingleplayer()) {
                  if (super.mc.theWorld != null) {
                     super.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
                     super.mc.theWorld.sendQuittingDisconnectingPacket();
                     super.mc.loadWorld((WorldClient)null);
                  }

                  ClientCloudManager.sendPacket(new PacketFriendJoinGame(this.username));
               }
            }
         } else {
            ClientTickHandler.addClientNotification(new ClientNotification("In a Party. Please leave first."));
         }
      }

      if (par1 == 7) {
         if (GameManager.instance().currentClientGame == null) {
            ClientCloudManager.sendPacket(new PacketTradeRequestUser(this.username));
         } else {
            ClientTickHandler.addClientNotification(new ClientNotification("Can not trade from in game."));
         }
      }

      if (par1 == 8) {
         ClientCloudManager.sendPacket(new PacketFriendRemove(this.username));
      }

   }
}
