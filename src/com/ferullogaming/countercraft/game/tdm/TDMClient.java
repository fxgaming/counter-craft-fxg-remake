package com.ferullogaming.countercraft.game.tdm;

import com.ferullogaming.countercraft.CCUtils;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.game.GuiVote;
import com.ferullogaming.countercraft.client.gui.game.GuiWSMain;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.game.Vote;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class TDMClient extends IGameClient {
   private int tempScoreRed = 0;
   private int tempScoreBlue = 0;
   private int tempTime = 0;
   private TeamDeathMatch tdmGame;
   private ArrayList tempTeamRed = new ArrayList();
   private ArrayList tempTeamBlue = new ArrayList();
   private int refreshDelay = 0;
   private int refreshCloudDataDelay = 0;

   public TDMClient(IGame par1) {
      super(par1);
      this.tdmGame = (TeamDeathMatch)par1;
   }

   public void onClientUpdate(Minecraft mc) {
      if (this.refreshDelay++ > 2) {
         this.tempScoreRed = this.getGame().getPlayerEventHandler().getTeam("red").getTeamObjectInteger("score").intValue();
         this.tempScoreBlue = this.getGame().getPlayerEventHandler().getTeam("blue").getTeamObjectInteger("score").intValue();
         this.tempTime = this.getCurrentTimeTick();
         this.tempTeamRed.clear();
         this.tempTeamRed.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("red").getPlayers(), "kills"));
         this.tempTeamBlue.clear();
         this.tempTeamBlue.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("blue").getPlayers(), "kills"));
         this.refreshDelay = 0;
      }

      if (this.refreshCloudDataDelay++ > 80) {
         Iterator i$ = this.getGame().getPlayerEventHandler().getPlayers().iterator();

         while(i$.hasNext()) {
            String username = (String)i$.next();
            PacketClientRequest.sendRequest(RequestType.PLAYER_DATA, username);
         }

         this.refreshCloudDataDelay = 0;
      }

   }

   public void onRenderHUD(Minecraft mc, int par1, int par2) {
      int mwidth = par1 / 2;
      int mheight = par2 / 2;
      float alpha = 0.6F;
      if (this.tdmGame.getClientSide().getCurrentVote() != null) {
         Vote vote = this.tdmGame.getClientSide().getCurrentVote();
         GuiVote.renderVote(vote);
      }

      String redScore = this.getGame().getPlayerEventHandler().getTeam("Red").teamColor + "" + this.tempScoreRed + "";
      String blueScore = this.getGame().getPlayerEventHandler().getTeam("Blue").teamColor + "" + this.tempScoreBlue + "";
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      if (this.tdmGame.getStatus() == GameStatus.PREGAME && this.tdmGame.timerPreGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.tdmGame.getStatus() == GameStatus.GAME && this.tdmGame.timerGame.getTimeRemainingSeconds() < 16) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.tdmGame.getStatus() == GameStatus.POSTGAME && this.tdmGame.timerPostGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      CCRenderHelper.drawRect((double)(mwidth - 19), 15.0D, 18.0D, 11.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(redScore, mwidth - 9, 17);
      CCRenderHelper.drawRect((double)(mwidth + 1), 15.0D, 18.0D, 11.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(blueScore, mwidth + 10, 17);
      CCRenderHelper.drawRect((double)(mwidth - 19), 1.0D, 38.0D, 13.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "" + matchTime, mwidth + 1, 4);
      boolean y1;
      if (this.tdmGame.getStatus() == GameStatus.PREGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.WHITE + "Разминка " + matchTime, mwidth, mheight / 2 - 5, 2.0D);
      }

      if (this.tdmGame.getStatus() == GameStatus.POSTGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.GOLD + "ИГРА ОКОНЧЕНА", mwidth, mheight / 2 - 5, 2.0D);
      }

      String username;
      int i;
      for(i = 0; i < this.tempTeamRed.size(); ++i) {
         username = (String)this.tempTeamRed.get(i);
         CCRenderHelper.renderPlayerHead(username, mwidth - 39 - i * 21, 2);
      }

      for(i = 0; i < this.tempTeamBlue.size(); ++i) {
         username = (String)this.tempTeamBlue.get(i);
         CCRenderHelper.renderPlayerHead(username, mwidth + 21 + i * 21, 2);
      }

   }

   public void onRenderTAB(Minecraft mc, int par1, int par2) {
      int mwidth = par1 / 2;
      int mheight = par2 / 2;
      int sbwidth = 330;
      int sbheight = 200;
      int sbx = mwidth - sbwidth / 2;
      int sby = mheight - sbheight / 2 - 18;
      CCRenderHelper.drawRect((double)sbx, (double)sby, (double)sbwidth, 40.0D, "0x000000", 0.8F);
      CCRenderHelper.drawRect((double)sbx, (double)(sby + 3), (double)sbwidth, 34.0D, "0xffffff", 0.2F);
      CCRenderHelper.drawImage((double)(sbx + 2), (double)(sby - 10), new ResourceLocation("countercraft:textures/gui/loading_gametitle.png"), 100.0D, 55.0D);
      CCRenderHelper.renderTextRight(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + this.getGame().getDisplayName(), sbx + sbwidth - 3, sby + 9);
      CCRenderHelper.renderTextRight(EnumChatFormatting.WHITE + "Карта: " + this.getGame().getGameMapType().toUpperCase(), sbx + sbwidth - 3, sby + 20);
      CCRenderHelper.drawImage((double)(sbx + sbwidth / 2 - 8), (double)(sby + 5), new ResourceLocation("countercraft:textures/misc/stopwatch.png"), 16.0D, 16.0D);
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + matchTime, sbx + sbwidth / 2, sby + 24);
      CCRenderHelper.drawRect((double)sbx, (double)(sby + 41), (double)sbwidth, 193.0D, "0x000000", 0.5F);
      CCRenderHelper.renderPlayerScoreboard(sbx, sby + 41, sbwidth, 13, 3, "", EnumChatFormatting.WHITE, this.getGame(), "Пинг", "У", "П", "О");

      int i;
      String username;
      String kills;
      String assists;
      String deaths;
      for(i = 0; i < this.tempTeamBlue.size(); ++i) {
         username = (String)this.tempTeamBlue.get(i);
         kills = "" + this.getGame().getPlayerGameData(username).getInteger("kills");
         assists = "" + this.getGame().getPlayerGameData(username).getInteger("assists");
         deaths = "" + this.getGame().getPlayerGameData(username).getInteger("deaths");
         CCRenderHelper.renderPlayerScoreboard(sbx, sby + 55 + i * 11, sbwidth, 10, 1, username, this.getGame().getPlayerEventHandler().getTeam("Blue").teamColor, this.getGame(), this.getPlayerInfoByUsername(username).responseTime + "", kills, assists, deaths);
      }

      for(i = 0; i < this.tempTeamRed.size(); ++i) {
         username = (String)this.tempTeamRed.get(i);
         kills = "" + this.getGame().getPlayerGameData(username).getInteger("kills");
         assists = "" + this.getGame().getPlayerGameData(username).getInteger("assists");
         deaths = "" + this.getGame().getPlayerGameData(username).getInteger("deaths");
         CCRenderHelper.renderPlayerScoreboard(sbx, sby + 147 + i * 11, sbwidth, 10, 1, username, this.getGame().getPlayerEventHandler().getTeam("Red").teamColor, this.getGame(), this.getPlayerInfoByUsername(username).responseTime + "", kills, assists, deaths);
      }

      CCRenderHelper.drawRect((double)(sbx + 4), (double)(sby + 144), (double)(sbwidth - 8), 1.0D, "0xffffff", 0.5F);
   }

   @SideOnly(Side.CLIENT)
   public void onKeyPressed(int par1) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      Team team = this.getGame().getPlayerEventHandler().getPlayerTeam((EntityPlayer)player);
      if (par1 == 48 || par1 == 18) {
         int var1 = this.getGame().getPlayerGameData(player.username).getInteger("buyTime");
         if (team != null && (var1 == -1 || var1 > 0)) {
            if (Minecraft.getMinecraft().currentScreen instanceof GuiWSMain) {
               Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
               return;
            }

            Minecraft.getMinecraft().displayGuiScreen(new GuiWSMain((GuiScreen)null, team.teamName));
         }
      }

   }

   public boolean allowNameTagRendering(String par1) {
      String var1 = this.getGame().getPlayerEventHandler().getPlayerTeam(Minecraft.getMinecraft().thePlayer.username).teamName;
      String var2 = this.getGame().getPlayerEventHandler().getPlayerTeam(par1).teamName;
      return var1.equalsIgnoreCase(var2);
   }

   public boolean allowCustomScroll() {
      return true;
   }

   public int getCurrentTimeTick() {
      GameStatus status = this.tdmGame.getStatus();
      if (status == GameStatus.IDLE) {
         return 0;
      } else if (status == GameStatus.PREGAME) {
         return this.tdmGame.timerPreGame.getTimeRemaining();
      } else if (status == GameStatus.GAME) {
         return this.tdmGame.timerGame.getTimeRemaining();
      } else {
         return status == GameStatus.POSTGAME ? this.tdmGame.timerPostGame.getTimeRemaining() : 0;
      }
   }
}
