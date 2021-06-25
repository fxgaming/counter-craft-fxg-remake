package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCSlotFriend {
   public String username;
   public int x;
   public int y;
   public int width;
   public int height;
   public boolean pending = false;
   private PlayerDataCloud playerData;
   private GuiScreen parentGUI;

   public GuiCCSlotFriend(String par1, int par2, int par3, int par4, int par5, boolean par6, GuiScreen par7) {
      this.username = par1;
      this.playerData = PlayerDataHandler.getPlayerCloudData(this.username);
      this.x = par2;
      this.y = par3;
      this.width = par4;
      this.height = par5;
      this.pending = par6;
      this.parentGUI = par7;
   }

   public void onClicked(int mx, int my) {
      if (this.pending) {
         GuiCCDDMFriendAccept gui = new GuiCCDDMFriendAccept(this.parentGUI, mx, my, 100, 15, this.username);
         Minecraft.getMinecraft().displayGuiScreen(gui);
      } else {
         GuiCCDDMFriend gui = new GuiCCDDMFriend(this.parentGUI, mx, my, 100, 15, this.username);
         Minecraft.getMinecraft().displayGuiScreen(gui);
      }

   }

   public void doRender(boolean par1) {
      CCRenderHelper.drawRectWithShadow((double)this.x, (double)this.y, (double)this.width, (double)this.height, "0x000000", 0.5F);
      CCRenderHelper.drawRect((double)(this.x + 26), (double)(this.y + 14), 29.0D, 30.0D, "0x888888", 1.0F);
      CCRenderHelper.renderPlayerHead(this.username, (double)(this.x + 27), (double)(this.y + 15), 1.5D, false);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + this.username, this.x + this.width / 2, this.y + 4);
      String color;
      if (this.pending) {
         CCRenderHelper.renderCenteredText(EnumChatFormatting.RED + "Pending", this.x + this.width / 2, this.y + 47);
         CCRenderHelper.renderCenteredText(EnumChatFormatting.RED + "Request", this.x + this.width / 2, this.y + 57);
      } else {
         int y1 = this.y + 47;
         if (this.playerData.group != null) {
            CCRenderHelper.renderCenteredText("" + this.playerData.group.getDisplayName() + "", this.x + this.width / 2, y1);
            y1 += 10;
         }

         color = this.playerData.isOnline ? EnumChatFormatting.GREEN + "Online" : EnumChatFormatting.RED + "Offline";
         CCRenderHelper.renderCenteredText(color, this.x + this.width / 2, y1);
         y1 += 10;
         if (this.playerData.isOnline) {
            String status = this.playerData.status;
            CCRenderHelper.renderCenteredText(status, this.x + this.width / 2, y1);
         }
      }

      if (par1) {
         int size = 1;
         color = "0xffba00";
         CCRenderHelper.drawRect((double)(this.x - size), (double)this.y, (double)size, (double)this.height, color, 1.0F);
         CCRenderHelper.drawRect((double)(this.x + this.width), (double)this.y, (double)size, (double)this.height, color, 1.0F);
         CCRenderHelper.drawRect((double)(this.x - size), (double)(this.y - size), (double)(this.width + size * 2), (double)size, color, 1.0F);
         CCRenderHelper.drawRect((double)(this.x - size), (double)(this.y + this.height), (double)(this.width + size * 2), (double)size, color, 1.0F);
      }

   }
}
