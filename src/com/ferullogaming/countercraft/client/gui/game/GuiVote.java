package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.game.Vote;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiVote {
   public static void renderVote(Vote givenVote) {
      int votePosX = 5;
      int votePosY = 70;
      int voteWidth = 100;
      int voteHeight = 45;
      String voteMessage = "Неизвестное голосование";
      String timeLeft = "0:" + givenVote.voteTime / 20L;
      switch(givenVote.voteType) {
      case KICK:
         voteMessage = "Кикнуть игрока " + givenVote.voteValue + "?";
      default:
         CCRenderHelper.drawRectWithShadow((double)votePosX, (double)(votePosY + 11), (double)voteWidth, (double)(voteHeight - 11), GuiCCMenu.menuTheme2, 255.0F);
         CCRenderHelper.drawRectWithShadow((double)votePosX, (double)votePosY, (double)voteWidth, 10.0D, GuiCCMenu.menuTheme3, 255.0F);
         CCRenderHelper.renderText("Команда " + givenVote.teamVoting.toUpperCase() + " голосует - " + timeLeft, votePosX + 2, votePosY + 2);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.WHITE + voteMessage, votePosX + 2, votePosY + 13, 0.6D);
         CCRenderHelper.drawRectWithShadow((double)(votePosX + voteWidth - 31), (double)(votePosY + 19), 28.0D, 10.0D, GuiCCMenu.menuTheme3, 255.0F);
         CCRenderHelper.drawRectWithShadow((double)(votePosX + voteWidth - 31), (double)(votePosY + 32), 28.0D, 10.0D, GuiCCMenu.menuTheme3, 255.0F);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.GREEN + "Да", votePosX + voteWidth - 30, votePosY + 20, 1.0D);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.GREEN + "" + givenVote.yesVotes, votePosX + 5, votePosY + 20, 1.0D);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.RED + "Нет", votePosX + voteWidth - 30, votePosY + 33, 1.0D);
         CCRenderHelper.renderTextScaled(EnumChatFormatting.RED + "" + givenVote.noVotes, votePosX + 5, votePosY + 33, 1.0D);
         CCRenderHelper.drawImageTransparent((double)(votePosX + voteWidth - 14), (double)(votePosY + 18), new ResourceLocation("countercraft", "textures/gui/voteyes.png"), 12.0D, 12.0D, 255.0D);
         CCRenderHelper.drawImageTransparent((double)(votePosX + voteWidth - 14), (double)(votePosY + 31), new ResourceLocation("countercraft", "textures/gui/voteno.png"), 12.0D, 12.0D, 255.0D);
         CCRenderHelper.drawRectWithShadow((double)votePosX, (double)(votePosY + voteHeight + 1), (double)voteWidth, 10.0D, GuiCCMenu.menuTheme3, 255.0F);
         String tip = "Нажмите F4 для голоса НЕТ, F5 для голоса ДА";
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)Minecraft.getMinecraft().thePlayer);
         if (playerData != null && playerData.hasVoted) {
            tip = "Вы проголосовали!";
         }

         CCRenderHelper.renderTextScaled(EnumChatFormatting.WHITE + tip, votePosX + 2, votePosY + 49, 0.6D);
      }
   }
}
