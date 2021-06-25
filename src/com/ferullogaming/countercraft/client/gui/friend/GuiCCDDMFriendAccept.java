package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.friend.PacketFriendRequestAccept;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMFriendAccept extends GuiFGDropDownMenu {
   private String username;

   public GuiCCDDMFriendAccept(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Accept");
      this.addOption("Ignore");
      this.username = par6;
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         ClientCloudManager.sendPacket(new PacketFriendRequestAccept(this.username, 1));
      }

      if (par1 == 2) {
         ClientCloudManager.sendPacket(new PacketFriendRequestAccept(this.username, 2));
      }

      super.mc.displayGuiScreen(new GuiCCFriendMenu((GuiScreen)null));
   }
}
