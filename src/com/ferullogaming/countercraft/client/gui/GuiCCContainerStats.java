package com.ferullogaming.countercraft.client.gui;

import com.f3rullo14.fds.MathHelper;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.HDD;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.CCRenderHelper.EnumShowcaseColor;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerList;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerListSlotText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCContainerStats extends GuiFGContainerList {
   private int delayRefresh = 0;
   private double eNow = 0.0D;
   private double eAim = 0.0D;
   private int normalH = 0;
   private int normalY = 0;
   public GuiCCContainerStats(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
      this.normalH = this.height;
      this.normalY = this.posY;
   }

   public static double round(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         long factor = (long)Math.pow(10.0D, (double)places);
         value *= (double)factor;
         long tmp = Math.round(value);
         return (double)tmp / (double)factor;
      }
   }

   public void updateScreen() {
      super.updateScreen();
      if (this.delayRefresh++ > 20) {
         this.delayRefresh = 0;
      }
      
      if (GuiCCMenuFindMatch.isSearching) this.eAim = 48.0D;
      else this.eAim = 0.0D;
      if (this.eNow < this.eAim) this.eNow += 2D;
      else if (this.eNow > this.eAim) this.eNow -= 2D;
      this.height = this.normalH + (int)-this.eNow;
      this.posY = this.normalY + (int)this.eNow;
   }

   public void drawScreen(int par1, int par2, float par3) {
	   super.drawScreen(par1, par2, par3);
	   int yoffset = CounterCraft.instance.ban.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase()) ? 43 : 0;
	   try {
		   if (CounterCraft.instance.sponsor.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase())) {
			   CCRenderHelper.drawRect((double)super.posX, (double)(super.posY + 4 - yoffset) + 104, 110.0D, 10.0D, GuiCCMenu.menuTheme3, 255.0F);
			   CCRenderHelper.drawRect((double)super.posX, (double)(super.posY + 5 - yoffset) + 104, 110.0D, 8.0D, GuiCCMenu.menuTheme2, 255.0F);
			   CCRenderHelper.renderCenteredTextScaled("§6Y O U  A R E  S P O N S O R   ON MIXLAB.PW", super.posX + 55, super.posY + 111 - yoffset, 0.5D);
			   CCRenderHelper.renderShowcaseRus("SPONSOR", "Спасибо за материальную помощь серверу!", super.posX, super.posY + 118 - yoffset, 110, 32, EnumShowcaseColor.ALPHA2, new ItemStack(4278, 1, 0), false, 20, 0, 0, 1.0F);
		   }
		   if (CounterCraft.instance.ban.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase())) { 
			   CCRenderHelper.drawRect((double)super.posX, (double)(super.posY + 4) + 104, 110.0D, 10.0D, GuiCCMenu.menuTheme3, 255.0F);
			   CCRenderHelper.drawRect((double)super.posX, (double)(super.posY + 5) + 104, 110.0D, 8.0D, GuiCCMenu.menuTheme2, 255.0F);
			   CCRenderHelper.renderCenteredTextScaled("§4Y O U  A R E  B A N N E D      ON MIXLAB.PW", super.posX + 55, super.posY + 111, 0.5D);
			   CCRenderHelper.renderShowcaseRus("BANNED", "Вы заблокированы на серверах Mixlab.pw", super.posX, super.posY + 118, 110, 32, EnumShowcaseColor.RED, new ItemStack(4721, 1, 0), false, 20, 0, 0, 1.0F);
		   }
		   if (!GuiCCMenuFindMatch.isSearching) {
			   CCRenderHelper.renderTextScaled("Койнов: ", super.posX + 10, super.posY + 1, 1.0D);
			   CCRenderHelper.renderTextScaled("Уровень: ", super.posX + 1, super.posY + 11, 1.0D);
			   CCRenderHelper.renderTextScaled("Ранг: ", super.posX + 1, super.posY + 21, 1.0D);
			   CCRenderHelper.renderTextScaled("Уровень: ", super.posX + 1, super.posY + 31, 1.0D);
			   CCRenderHelper.renderTextScaled("ХР:", super.posX + 1, super.posY + 41, 1.0D);
			   //XXX IF SPONSOR
			   CCRenderHelper.renderTextScaled("Спонсор " + '★' + " до: ", super.posX + 1, super.posY + 51, 1.0D);
		   }
	   } catch(Exception e) {}
   }
}
