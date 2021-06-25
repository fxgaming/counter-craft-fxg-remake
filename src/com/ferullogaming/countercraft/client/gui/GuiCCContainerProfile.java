package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.EnumCompRank;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PlayerRank;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCCContainerProfile extends GuiFGContainer {
   private static int dotDelay = 0;
   private double eNow = 0.0D;
   private double eAim = 0.0D;
   private int normalH = 0;
   private int normalY = 0;
   public GuiCCContainerProfile(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
      this.normalH = this.height;
      this.normalY = this.posY;
   }

   public void updateScreen() {
      super.updateScreen();
      String username = Minecraft.getMinecraft().getSession().getUsername();
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(username);
      if (GuiCCMenuFindMatch.isSearching) this.eAim = 48.0D;
      else this.eAim = 0.0D;
      if (this.eNow < this.eAim) this.eNow += 2D;
      else if (this.eNow > this.eAim) this.eNow -= 2D;
      this.height = this.normalH + (int)this.eNow;
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground();
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)super.posX, (double)(super.posY + 26), 110.0D, 14.0D, GuiCCMenu.menuTheme3, super.colorOpacity);
      String username = Minecraft.getMinecraft().getSession().getUsername();
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(username);
      CCRenderHelper.renderPlayerHead(username, super.posX + 3, super.posY + 3);
      CCRenderHelper.renderText(CounterCraft.instance.sponsor.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase()) ? "§6" + '★' + " " + username + " " + '★' : "§f" + username, super.posX + 25, super.posY + 3);
      CCRenderHelper.renderPlayerHead(username, super.posX + 3, super.posY + 3);
      CCRenderHelper.renderText(CounterCraft.instance.sponsor.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase()) ? "§6" + '★' + " " + username + " " + '★' : "§f" + username, super.posX + 25, super.posY + 3);
      if (CounterCraft.instance.sponsor.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase()) && !CounterCraft.instance.ban.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase())) {
    	  CCRenderHelper.renderText("§6Премиум пользователь", super.posX + 2, super.posY + 29);
      } else if (CounterCraft.instance.sponsor.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase())) {
    	  CCRenderHelper.renderText("§4Заблокирован", super.posX + 2, super.posY + 29);
      } else {
    	  CCRenderHelper.renderText("§fОбычный пользователь", super.posX + 2, super.posY + 29);
      }
      if (GuiCCMenuFindMatch.isSearching && GuiCCMenuFindMatch.var1) {
    	  GL11.glPushMatrix();
    	  CCRenderHelper.drawImage(x + 4.0D, 78.0D, new ResourceLocation("countercraft:textures/gui/find/planet" + GuiCCMenuFindMatch.currentImage + ".png"), 64, 24);
    	  GL11.glPopMatrix();
    	  CCRenderHelper.renderCenteredTextScaledWithOutline("Ожидание: " + (GuiCCMenuFindMatch.timeFromStart / 20) + " сек.", x + 56, 68, 1.0);
    	  if (GuiCCMenuFindMatch.timeToStop != 60) CCRenderHelper.renderCenteredTextScaledWithOutline("Осталось: " + ((GuiCCMenuFindMatch.timeToStop + 20) / 20) + " сек.", x + 56, 104, 1.0);
      }
   }
}
