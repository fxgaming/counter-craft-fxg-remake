package com.ferullogaming.countercraft.client.gui.friend;

import com.f3rullo14.fds.MathHelper;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.EnumCompRank;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PlayerRank;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketCheckPrestige;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.PacketEditClanTag;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.client.gui.GuiCCTPEditClanTag;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class GuiCCProfileBase extends GuiCCMenu implements IGuiFGPromptYesNo {
   public GuiScreen parentGui;
   public String username;
   public PlayerDataCloud data;
   private int refreshDelay = 15;
   private int tabSelected = 0;
   private String[] tabs = new String[]{"Showcase", "Details", "Records"};
   private boolean[] tabPublicView = new boolean[]{true, true, false};

   public GuiCCProfileBase(GuiScreen par1, String par2) {
      this.parentGui = par1;
      this.username = par2;
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      super.buttonList.add(new GuiFGButton(1, x + 1, 1, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      this.data = PlayerDataHandler.getPlayerCloudData(this.username);
      if (this.username.equals(super.mc.getSession().getUsername())) {
         GuiFGButton button = new GuiFGButton(10, x + 290, 44, 40, 10, EnumChatFormatting.ITALIC + "Edit");
         button.drawBackground = false;
         super.buttonList.add(button);
         GuiFGButton clanTag = new GuiFGButton(30, x + 88, 32, 40, 10, EnumChatFormatting.YELLOW + "[" + this.data.clanTag + "]");
         clanTag.drawBackground = false;
         super.buttonList.add(clanTag);
         GuiFGButton clanTagReset = new GuiFGButton(31, x + 255, 82, 40, 10, EnumChatFormatting.RED.toString() + EnumChatFormatting.ITALIC + "Reset Clan Tag");
         clanTagReset.drawBackground = false;
         super.buttonList.add(clanTagReset);
      }

      int tabW = 60;
      int tabH = 12;
      int npc = 0;

      for(int i = 0; i < this.tabs.length; ++i) {
         if (!this.tabPublicView[i] && !this.username.equalsIgnoreCase(super.mc.getSession().getUsername())) {
            --npc;
         } else {
            GuiFGButton button = new GuiFGButton(20 + (i - npc), x + 25 + (tabW + 4) * (i - npc) + 1, 99, tabW - 2, tabH, this.tabs[i]);
            button.drawBackground = false;
            super.buttonList.add(button);
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      if (par1GuiButton.id == 1) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      if (par1GuiButton.id == 10) {
         super.mc.displayGuiScreen(new GuiCCTPEditMood(this));
      }

      if (par1GuiButton.id == 20) {
         GuiCCProfileBase gui = new GuiCCProfile(this.parentGui, this.username);
         gui.tabSelected = 0;
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 21) {
         GuiCCProfileBase gui = new GuiCCProfileDetails(this.parentGui, this.username);
         gui.tabSelected = 1;
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 22) {
         GuiCCProfileBase gui = new GuiCCProfilePunishments(this.parentGui, this.username);
         gui.tabSelected = 2;
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 30) {
         super.mc.displayGuiScreen(new GuiCCTPEditClanTag(this));
      }

      if (par1GuiButton.id == 31) {
         ClientTickHandler.addClientNotification(new ClientNotification("Your clantag has been reset!"));
         ClientCloudManager.sendPacket(new PacketEditClanTag("NONE"));
         super.mc.displayGuiScreen(new GuiCCMenuHome());
      }

      if (par1GuiButton.id == 32) {
         if (this.data.discordUserID.length() > 0) {
            ClientTickHandler.addClientNotification(new ClientNotification("Woops!", "Your Discord is already linked!"));
            super.mc.displayGuiScreen(new GuiCCMenuHome());
         } else {
            ClientTickHandler.addClientNotification((new ClientNotification("Link Copied!", "Paste in " + EnumChatFormatting.BLUE + "#link_profile" + EnumChatFormatting.WHITE + "!")).setSubMessage("Discord Integration"));
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("!link " + this.data.playerUUID + " " + this.data.discordAuthCode), (ClipboardOwner)null);
         }
      }

      if (par1GuiButton.id == 33) {
         GuiFGYesNoPrompt gui = new GuiFGYesNoPrompt(1, this);
         gui.setPromptAnswers("Cancel", "Prestige");
         gui.addInformation("Are you sure you want to increase your", "prestige level to " + (this.data.prestigeLevel + 1) + "?", "(This will set you back to" + EnumChatFormatting.WHITE + " Chicken " + EnumChatFormatting.RESET + "rank)");
         super.mc.displayGuiScreen(gui);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      if (this.refreshDelay++ > 30) {
         if (this.username == null) {
            super.mc.displayGuiScreen(this.parentGui);
            return;
         }

         PacketClientRequest.sendRequest(RequestType.PLAYER_DATA, this.username);
         this.data = PlayerDataHandler.getPlayerCloudData(this.username);
         this.refreshDelay = 0;
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawIntendedBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      CCRenderHelper.drawRectWithShadow((double)(x + 25), 25.0D, 300.0D, 70.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "Player Profile", super.width / 2, 7);
      byte tabH;
      if (this.data != null) {
         int x1 = x + 25;
         tabH = 25;
         String username = this.data.getUsername();
         if (this.data.group != null) {
            username = "" + this.data.group.getDisplayName() + " " + username;
         }

         CCRenderHelper.renderTextScaled("" + username, x1 + 102, tabH + 6, 1.5D);
         CCRenderHelper.drawRectWithShadow2(x1 + 5, tabH + 5, 58, 60, Color.black, 255);
         CCRenderHelper.renderPlayerHead(this.data.getUsername(), (double)(x1 + 5), (double)(tabH + 5), 3.2D, false);
         CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "" + this.data.mood, x1 + 67, tabH + 20);
         int x2 = 10;
         PlayerRank rank = PlayerRank.getRankFromEXP(this.data.exp);
         CCRenderHelper.renderText("Rank: " + rank.getTitle(), x1 + 100 + x2, tabH + 35);
         CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Time Played: " + MathHelper.round((double)this.data.getMinutesPlayed() / 60.0D, 1) + "h", x1 + 100 + x2, tabH + 45);
         CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Games Played: " + this.data.totalGames, x1 + 100 + x2, tabH + 55);
         CCRenderHelper.renderText("Trophies: " + this.data.trophies, x1 + 202 + x2, tabH + 35);
         double kd = (double)this.data.totalKills / (double)(this.data.totalDeaths == 0 ? 1 : this.data.totalDeaths);
         CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "K/D: " + MathHelper.round(kd, 1), x1 + 205 + x2, tabH + 45);
         if (rank != null) {
            ResourceLocation icon = new ResourceLocation("countercraft:textures/misc/ranks/rank_" + rank.getTexture() + ".png");
            ResourceLocation icon1 = new ResourceLocation("countercraft:textures/misc/ranks/rankbackground_" + rank.getTextureBackground() + ".png");
            CCRenderHelper.drawImage((double)(x1 + 79), 67.0D, icon, 14.0D, 14.0D);
            CCRenderHelper.drawImage((double)(x1 + 70), 58.0D, icon1, 32.0D, 32.0D);
            if (this.data.boostersActive.size() > 0) {
               for(int i = 0; i < this.data.boostersActive.size(); ++i) {
                  Booster boost = (Booster)this.data.boostersActive.get(i);
                  CCRenderHelper.renderText(EnumChatFormatting.GOLD + "" + (boost.isEmeralds() ? EnumChatFormatting.GREEN : "") + "" + EnumChatFormatting.BOLD + "x" + boost.multiplier, x1 + 57 + x2, tabH + 32 + i * 8);
               }
            }
         }

         if (this.data.coinDisplayed > 0) {
            CloudItemStack coinStack = new CloudItemStack("-fake-", this.data.coinDisplayed);
            if (coinStack != null && coinStack.getItemStack() != null) {
               GL11.glPushMatrix();
               GL11.glTranslated((double)(x + 23), 23.0D, 0.0D);
               double scale = 1.25D;
               GL11.glScaled(scale, scale, scale);
               CCRenderHelper.renderItemStackIntoGUI(coinStack.getItemStack(), 0, 0);
               GL11.glPopMatrix();
            }
         }

         if (this.data.getCompRank() != null) {
            EnumCompRank playerCompRank = this.data.getCompRank();
            String rankResLoc = playerCompRank.getResourceName();
            CCRenderHelper.drawImage((double)(x1 + 265), (double)(tabH + 1), new ResourceLocation("countercraft:textures/misc/compranks/" + rankResLoc + ".png"), 35.0D, 16.0D);
         }
      }

      int tabW = 60;
      tabH = 12;
      int npc = 0;

      int i;
      for(i = 0; i < this.tabs.length; ++i) {
         if (i != this.tabSelected) {
            if (!this.tabPublicView[i] && !this.username.equalsIgnoreCase(super.mc.getSession().getUsername())) {
               --npc;
            } else {
               CCRenderHelper.drawRectWithShadow((double)(x + 25 + (tabW + 4) * (i - npc)), 98.0D, (double)tabW, (double)tabH, "0x222222", 1.0F);
               CCRenderHelper.drawRect((double)(x + 25 + (tabW + 4) * (i - npc)), 98.0D, (double)tabW, (double)(tabH + 1), "0x222222", 1.0F);
            }
         }
      }

      CCRenderHelper.drawRectWithShadow((double)(x + 25), 112.0D, 300.0D, 120.0D, GuiCCMenu.menuTheme, 1.0F);

      for(i = 0; i < this.tabs.length; ++i) {
         if (i == this.tabSelected) {
            CCRenderHelper.drawRectWithShadow((double)(x + 25 + (tabW + 4) * i), 98.0D, (double)tabW, (double)tabH, GuiCCMenu.menuTheme, 1.0F);
            CCRenderHelper.drawRect((double)(x + 25 + (tabW + 4) * i), 98.0D, (double)tabW, (double)(tabH + 1), GuiCCMenu.menuTheme, 1.0F);
            int marg = 4;
            CCRenderHelper.drawRectWithShadow((double)(x + 25 + marg), (double)(112 + marg), (double)(300 - marg * 2), (double)(120 - marg * 2), "0x222222", 1.0F);
            this.drawTab(par1, par2, par3);
         }
      }

      this.drawButtons(par1, par2, par3);
   }

   public void drawTab(int par1, int par2, float par3) {
   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2) {
         ClientCloudManager.sendPacket(new PacketCheckPrestige());
      }

   }
}
