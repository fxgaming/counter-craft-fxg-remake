package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.FriendsManager;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.friend.PacketFriendRequest;
import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCTPAddFriend extends GuiFGTextPrompt {
   public GuiCCTPAddFriend(GuiScreen par1) {
      super(par1);
      this.addInformation(new String[]{EnumChatFormatting.WHITE + "Add A Friend", EnumChatFormatting.WHITE + "", EnumChatFormatting.WHITE + "Please enter a username.", EnumChatFormatting.WHITE + "CaSe Sensitive."});
   }

   public void onPromptEntered() {
      String var1 = this.getTextField();
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      FriendsManager friendManager = FriendsManager.instance();
      if (!friendManager.isFriend(var1)) {
         ClientCloudManager.sendPacket(new PacketFriendRequest(var1));
      }

      super.mc.displayGuiScreen(super.parentGui);
   }
}
