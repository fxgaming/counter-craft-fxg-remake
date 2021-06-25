package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Rectangle;
import net.minecraft.client.Minecraft;

public class GuiCCSlotParty {
   public String username;
   public int x;
   public int y;
   public int width;
   public int height;
   private PlayerDataCloud playerData;

   public GuiCCSlotParty(String par1, int par2, int par3, int par4, int par5) {
      this.username = par1;
      this.playerData = PlayerDataHandler.getPlayerCloudData(this.username);
      this.x = par2;
      this.y = par3;
      this.width = par4;
      this.height = par5;
   }

   public void doRender(int par1, int par2, float par3) {
      CCRenderHelper.drawRectWithShadow((double)this.x, (double)this.y, (double)this.width, (double)this.height, GuiCCMenu.menuTheme3, 1.0F);
      CCRenderHelper.renderPlayerHead(this.username, (double)(this.x + 12), (double)(this.y + 2), 1.15D, false);
      int length = (int)((double)Minecraft.getMinecraft().fontRenderer.getStringWidth(this.username) * 0.65D);
      CCRenderHelper.renderTextScaled(this.username, this.x + 22 - length / 2, this.y + 25, 0.65D);
      if (this.playerData.group != null) {
         CCRenderHelper.drawRect((double)(this.x + 1), (double)(this.y + 32), (double)(this.width - 2), 11.0D, GuiCCMenu.menuTheme, 1.0F);
         String var1 = "" + this.playerData.group.getDisplayName() + "";
         length = (int)((double)Minecraft.getMinecraft().fontRenderer.getStringWidth(var1) * 0.65D);
         CCRenderHelper.renderTextScaled(var1, this.x + 22 - length / 2, this.y + 35, 0.65D);
      }

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
