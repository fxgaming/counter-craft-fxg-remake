package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Rectangle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCSlotFriendSmall {
   public String username;
   public int x;
   public int y;
   public int width;
   public int height;
   public ResourceLocation friendSlotTexture = new ResourceLocation("countercraft", "textures/gui/friendslot.png");
   public ResourceLocation friendSlotTextureOffline = new ResourceLocation("countercraft", "textures/gui/friendslotoffline.png");
   private PlayerDataCloud playerData;

   public GuiCCSlotFriendSmall(String par1, int par2, int par3, int par4, int par5) {
      this.username = par1;
      this.playerData = PlayerDataHandler.getPlayerCloudData(this.username);
      this.x = par2;
      this.y = par3;
      this.width = par4;
      this.height = par5;
   }

   public void onClicked(int mx, int my) {
   }

   public void doRender(int par1, int par2, float par3) {
      CCRenderHelper.drawRectWithShadow((double)this.x, (double)this.y, (double)this.width, (double)this.height, "0x000000", 0.5F);
      CCRenderHelper.drawImage((double)this.x, (double)this.y, this.playerData.isOnline ? this.friendSlotTexture : this.friendSlotTextureOffline, (double)this.width, (double)this.height);
      boolean blink = ClientManager.instance().getConversationHandler().getConversation(this.username).blink;
      if (blink) {
         CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, "0xffdd00", 1.0F);
      }

      CCRenderHelper.renderPlayerHead(this.username, (double)(this.x + 2), (double)(this.y + 2), 0.85D, false);
      CCRenderHelper.renderText((blink ? EnumChatFormatting.DARK_GRAY : EnumChatFormatting.WHITE + "") + this.username, this.x + 22, this.y + 7);
   }

   public void doRenderHighlight(int par1) {
      String color = "0xffba00";
      CCRenderHelper.drawRect((double)(this.x - par1), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x + this.width), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y - par1), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y + this.height), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
   }

   public boolean isMouseOver(int x, int y) {
      Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
      return rect.contains(x, y);
   }
}
