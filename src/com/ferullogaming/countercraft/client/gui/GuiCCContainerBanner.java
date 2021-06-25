package com.ferullogaming.countercraft.client.gui;

import java.util.ArrayList;
import java.util.Date;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.Punishment;
import com.ferullogaming.countercraft.client.cloud.PunishmentType;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketCompetitiveAbandon;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketCompetitiveRejoin;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketBrowse;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.player.PlayerDataHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCContainerBanner extends GuiFGContainer {
   private GuiFGButton button1;
   private GuiFGButton button2;
   private GuiFGButton showcaseButton;
   private GuiFGButton showcaseButton2;
   private int count = 0;
   private boolean allow$ = false;
   private ResourceLocation menuBanner = new ResourceLocation("countercraft", "textures/gui/banner.png");

   public GuiCCContainerBanner(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
   }

   public void addButtons(ArrayList par1) {
	   /*
      this.button1 = new GuiFGButton(25, super.posX + super.width / 2 - 75, super.posY + 25, 50, 10, EnumChatFormatting.BOLD + "Rejoin");
      this.button1.drawBackground = false;
      par1.add(this.button1);
      this.button2 = new GuiFGButton(26, super.posX + super.width / 2 + 25, super.posY + 25, 50, 10, EnumChatFormatting.BOLD + "Abandon");
      this.button2.drawBackground = false;
      par1.add(this.button2);
      this.showcaseButton = (new GuiFGButton(27, super.posX + 4, super.posY + 31, 34, 6, EnumChatFormatting.WHITE + "View on Market")).setColor("0x689727");
      this.showcaseButton.drawBackground = true;
      this.showcaseButton.centeredText = true;
      this.showcaseButton.setScale(0.5F);
      this.showcaseButton.disableShadow();
      par1.add(this.showcaseButton);
      this.showcaseButton2 = (new GuiFGButton(28, super.posX + 122, super.posY + 31, 34, 6, EnumChatFormatting.WHITE + "Purchase Now")).setColor("0x689727").setToolTip("Only $1 or more!", Color.black);
      this.showcaseButton2.drawBackground = true;
      this.showcaseButton2.centeredText = true;
      this.showcaseButton2.setScale(0.5F);
      this.showcaseButton2.disableShadow();
      par1.add(this.showcaseButton2);
      */
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      if (par1GuiButton.id == 25) {
         ClientCloudManager.sendPacket(new PacketCompetitiveRejoin());
      }

      if (par1GuiButton.id == 26) {
         ClientCloudManager.sendPacket(new PacketCompetitiveAbandon(Minecraft.getMinecraft().getSession().getUsername()));
      }

      if (par1GuiButton.id == 27) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuMarketBrowse());
      } else if (par1GuiButton.id == 28) {
         //GuiCCMenu.openURL(References.URL_BUYCRAFT);
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
	  /*
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      //if (data.playerPunishments.size() <= 0) {
         this.drawBackground();
	      double var10000 = (double)(super.posX + 1);
	      double var10001 = (double)(super.posY + 1);
	      double var10003 = (double)(super.width - 2);
	      Date d = new Date();
	      FontRenderer f = Minecraft.getMinecraft().fontRenderer;
	      String cdadata0 = (String)CounterCraft.Data[0];
	      String cdadata1 = (String)CounterCraft.Data[3];
	      f.drawStringWithShadow("Версия мода: " + CounterCraft.MOD_VERSION, (int)var10000+1, (int)var10001+1, 123123);
	      f.drawStringWithShadow("Следующая версия мода: " + cdadata0, (int)var10000+1, (int)var10001+10, 123123);
	      f.drawStringWithShadow("Обновление в: " + cdadata1, (int)var10000+1, (int)var10001+19, 123123);
	      f.drawStringWithShadow("Время сейчас: " + d, (int)var10000+1, (int)var10001+28, 123123);
	      if (this.count <= 39) {
	    	  ++this.count;
	    	  if (this.allow$) { 
	    		  this.allow$ = false;
	    	  }
	      }
	      else if (this.count >= 40){
	    	  this.allow$ = true;
	    	  this.count = 0;
	      }
	      if (this.allow$) {
	    	  d = CounterCraft.upd.getDateGMT();
	      }
	      
      //}
      /*
      ResourceLocation res = this.menuBanner;
      CCRenderHelper.renderShowcase("The Alpha Case 2", "The second in-game case that contains 10 community made skins!", super.posX + 1, super.posY + 1, 117, 38, CCRenderHelper.EnumShowcaseColor.ALPHA2, new ItemStack(ItemManager.caseAlpha2, 1), true, par3);
      CCRenderHelper.renderShowcase("Become a " + EnumChatFormatting.AQUA + "Supporter" + EnumChatFormatting.WHITE + "!", "Show your support and donate for a free in-game supporter rank & cape!", super.posX + 119, super.posY + 1, 117, 38, CCRenderHelper.EnumShowcaseColor.BLUE, new ItemStack(ItemManager.coinSupporter, 1), true, par3);
      this.button1.drawButton = this.button1.enabled = false;
      this.button2.drawButton = this.button2.enabled = false;
      if (data.hasCompetitiveRejoinButton && !data.hasPunishmentType(PunishmentType.BAN_COMP)) {
         this.button1.drawButton = this.button1.enabled = true;
         this.button2.drawButton = this.button2.enabled = true;
         CCRenderHelper.drawRectWithShadow((double)(super.posX + 1), (double)(super.posY + 1), (double)(super.width - 2), (double)(super.height - 2), "0xffde00", 0.75F);
         CCRenderHelper.renderCenteredText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Pending Competitive Match", super.posX + super.width / 2, super.posY + 8);
      } else {
         if (data.playerPunishments.size() > 0) {
            this.showcaseButton.drawButton = this.showcaseButton.enabled = false;
            this.showcaseButton2.drawButton = this.showcaseButton2.enabled = false;
            CCRenderHelper.drawRectWithShadow((double)(super.posX + 1), (double)(super.posY + 1), (double)(super.width - 2), (double)(super.height - 2), GuiCCMenu.menuTheme3, 1.0F);
            CCRenderHelper.renderText(EnumChatFormatting.RED + "Active Punishments", super.posX + 2, super.posY + 2);

            for(int i = 0; i < data.playerPunishments.size(); ++i) {
               Punishment pun = (Punishment)data.playerPunishments.get(i);
               CCRenderHelper.renderTextScaled("- " + pun.type.displayName + ": " + pun.getTimeRemainingString() + " - '" + pun.reason + "'", super.posX + 2, super.posY + 12 + i * 7, 0.7D);
            }
         } else {
            this.showcaseButton.drawButton = this.showcaseButton.enabled = true;
            this.showcaseButton.drawButton = this.showcaseButton.enabled = true;
         }

      }*/
   }
   
}
