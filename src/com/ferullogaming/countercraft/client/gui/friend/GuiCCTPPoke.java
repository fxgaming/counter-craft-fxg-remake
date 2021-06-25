package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.FriendsManager;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.friend.PacketFriendPoke;
import com.ferullogaming.countercraft.client.gui.api.GuiFGTextPrompt;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCTPPoke extends GuiFGTextPrompt {
   private String userPoking;

   public GuiCCTPPoke(GuiScreen par1, String par2) {
      super(par1);
      this.userPoking = par2;
      super.maxCharacters = 50;
      this.addInformation(new String[]{EnumChatFormatting.WHITE + "Poke A Friend", EnumChatFormatting.WHITE + "", EnumChatFormatting.WHITE + "'" + this.userPoking + "'", EnumChatFormatting.WHITE + "Max 50 Characters"});
   }

   public void onPromptEntered() {
      String var1 = this.getTextField();
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      FriendsManager friendManager = FriendsManager.instance();
      if (friendManager.isFriend(this.userPoking)) {
         ClientCloudManager.sendPacket(new PacketFriendPoke(this.userPoking, var1));
      }

      super.mc.displayGuiScreen(super.parentGui);
   }
}
