package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollerSlot;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCFriendListSlot extends GuiFGScrollerSlot {
   public String username;
   public boolean pending = false;
   private PlayerDataCloud playerData;
   private GuiScreen parentGUI;

   public GuiCCFriendListSlot(String par1, boolean par6, GuiScreen par7) {
      this.username = par1;
      this.playerData = PlayerDataHandler.getPlayerCloudData(this.username);
      this.pending = par6;
      this.parentGUI = par7;
   }

   protected void init() {
      super.init();
   }

   public boolean canSelect() {
      return this.playerData.isOnline;
   }

   public void doRender(int mouseX, int mouseY) {
      CCRenderHelper.drawRectWithShadow((double)(super.posX + 4), (double)(super.posY + 4), 20.0D, 20.0D, this.playerData.isOnline ? "0x55FF55" : "0x71787D", 1.0F);
      CCRenderHelper.renderPlayerHead(this.username, (double)(super.posX + 5), (double)(super.posY + 5), 1.0D, false);
      CCRenderHelper.renderTextScaled((this.playerData.isOnline ? EnumChatFormatting.GREEN : EnumChatFormatting.DARK_GRAY) + this.username, super.posX + 27, super.posY + 6, 1.0D);
   }

   public void clicked(int mouseX, int mouseY) {
      super.clicked(mouseX, mouseY);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
   }

   protected int height() {
      return 31;
   }
}
