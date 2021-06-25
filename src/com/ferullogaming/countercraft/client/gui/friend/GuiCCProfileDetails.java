package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PunishmentType;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCProfileDetails extends GuiCCProfileBase {
   public GuiCCProfileDetails(GuiScreen par1, String par2) {
      super(par1, par2);
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (super.username.equals(Minecraft.getMinecraft().getSession().getUsername())) {
         super.buttonList.add((new GuiFGButton(32, x + 228, 215, 90, 10, "Link my Discord")).setToolTip("Link your profile to Discord", Color.black));
      }

      if (cloudData != null) {
         super.buttonList.add((new GuiFGButton(33, x + 228, 201, 90, 10, "Initiate Prestige")).setToolTip("Prestige to next Level", Color.black));
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawTab(int par1, int par2, float par3) {
      int x = super.width / 2 - 173;
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + "Profile Details", x + 35, 122);
      CCRenderHelper.renderText(super.data.isOnline ? EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Online" : EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Offline", x + 35, 135);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "" + super.data.status, x + 35, 145);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Active Boosters: " + super.data.boostersActive.size(), x + 35, 155);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Group: " + (super.data.group != null ? super.data.group.name : "None"), x + 35, 165);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "EXP: " + super.data.exp, x + 35, 185);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Kills: " + super.data.totalKills, x + 35, 195);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Deaths: " + super.data.totalDeaths, x + 35, 205);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Prestige Level: " + super.data.prestigeLevel, x + 35, 215);
      CCRenderHelper.renderText(EnumChatFormatting.RED + "" + "Punishment Records", x + 170, 135);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Warnings: " + super.data.playerPunishmentCounts.get(PunishmentType.WARNING.toString()), x + 170, 145);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Mutes: " + super.data.playerPunishmentCounts.get(PunishmentType.MUTE.toString()), x + 170, 155);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "AC Bans: " + super.data.playerPunishmentCounts.get(PunishmentType.BAN_MM.toString()), x + 170, 165);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "CC Bans: " + super.data.playerPunishmentCounts.get(PunishmentType.BAN_MM.toString()), x + 170, 175);
   }
}
