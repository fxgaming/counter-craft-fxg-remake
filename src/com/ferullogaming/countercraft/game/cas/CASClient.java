package com.ferullogaming.countercraft.game.cas;

import com.ferullogaming.countercraft.CCUtils;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.game.GuiVote;
import com.ferullogaming.countercraft.client.gui.game.GuiWSMain;
import com.ferullogaming.countercraft.game.BombPoint;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.IGameComponentEconomy;
import com.ferullogaming.countercraft.game.Team;
import com.ferullogaming.countercraft.game.Vote;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class CASClient extends IGameClient {
   private int tempScoreRed = 0;
   private int tempScoreBlue = 0;
   private int tempTime = 0;
   private CasualBombDefusal casGame;
   private ArrayList tempTeamRed = new ArrayList();
   private ArrayList tempTeamBlue = new ArrayList();
   private int refreshDelay = 0;
   private int refreshCloudDataDelay = 0;
   private int bombPointParticleDelay = 0;

   public CASClient(IGame par1) {
      super(par1);
      this.casGame = (CasualBombDefusal)par1;
   }

   @SideOnly(Side.CLIENT)
   public void onClientUpdate(Minecraft mc) {
      if (this.refreshDelay++ > 2) {
         this.tempScoreRed = this.getGame().getPlayerEventHandler().getTeam("red").getTeamObjectInteger("score").intValue();
         this.tempScoreBlue = this.getGame().getPlayerEventHandler().getTeam("blue").getTeamObjectInteger("score").intValue();
         this.tempTime = this.getCurrentTimeTick();
         this.tempTeamRed.clear();
         this.tempTeamRed.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("red").getPlayers(), "score"));
         this.tempTeamBlue.clear();
         this.tempTeamBlue.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("blue").getPlayers(), "score"));
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

      int i;
      for(i = 0; i < this.casGame.bombPoints.size(); ++i) {
         ((BombPoint)this.casGame.bombPoints.get(i)).onClientUpdate();
      }

      if (this.bombPointParticleDelay++ > 20) {
         for(i = 0; i < this.casGame.bombPoints.size(); ++i) {
            ((BombPoint)this.casGame.bombPoints.get(i)).doParticles();
         }

         this.bombPointParticleDelay = 0;
      }

      PlayerData data = PlayerDataHandler.getPlayerData(mc.thePlayer.username);
      if (data.isGhost && data.isSpectating()) {
         ;
      }

   }

   @SideOnly(Side.CLIENT)
   public void onRenderWorld(RenderGlobal par1, float par2) {
      for(int i = 0; i < this.casGame.bombPoints.size(); ++i) {
         ((BombPoint)this.casGame.bombPoints.get(i)).doRender(par2);
      }

   }

   @SideOnly(Side.CLIENT)
   public void onRenderHUD(Minecraft mc, int par1, int par2) {
      int mwidth = par1 / 2;
      int mheight = par2 / 2;
      float alpha = 0.6F;
      if (this.casGame.getClientSide().getCurrentVote() != null) {
         Vote vote = this.casGame.getClientSide().getCurrentVote();
         GuiVote.renderVote(vote);
      }

      String redScore = this.getGame().getPlayerEventHandler().getTeam("Red").teamColor + "" + this.tempScoreRed + "";
      String blueScore = this.getGame().getPlayerEventHandler().getTeam("Blue").teamColor + "" + this.tempScoreBlue + "";
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      if (this.casGame.getStatus() == GameStatus.PREGAME && this.casGame.timerPreGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.casGame.getStatus() == GameStatus.GAME) {
         if (this.casGame.timerPreRound.getTimeRemaining() > 0) {
            matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
         }

         if (this.casGame.timerRound.getTimeRemaining() > 0 && this.casGame.timerRound.getTimeRemainingSeconds() <= 11) {
            matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
         }

         if (this.casGame.isBombPlanted) {
            matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
         }
      }

      if (this.casGame.isBombPlanted) {
         CCRenderHelper.drawImage((double)(mwidth - 12), 24.0D, new ResourceLocation("countercraft:textures/misc/bombplanted.png"), 24.0D, 20.0D);
      }

      if (this.casGame.getStatus() == GameStatus.POSTGAME && this.casGame.timerPostGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      CCRenderHelper.drawRect((double)(mwidth - 19), 15.0D, 18.0D, 11.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(redScore, mwidth - 9, 17);
      CCRenderHelper.drawRect((double)(mwidth + 1), 15.0D, 18.0D, 11.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(blueScore, mwidth + 10, 17);
      CCRenderHelper.drawRect((double)(mwidth - 19), 1.0D, 38.0D, 13.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "" + matchTime, mwidth + 1, 4);
      boolean y1;
      if (this.casGame.getStatus() == GameStatus.PREGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.WHITE + "Ожидание игроков " + (this.tempTeamBlue.size() + this.tempTeamRed.size()) + "/4" + " игроков.", mwidth, mheight / 2 - 32, 2.0D);
      }

      if (this.casGame.getStatus() == GameStatus.POSTGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.GOLD + "Игра окончена", mwidth, mheight / 2 - 5, 2.0D);
      }

      String username;
      PlayerData data;
      int i;
      for(i = 0; i < this.tempTeamRed.size(); ++i) {
         username = (String)this.tempTeamRed.get(i);
         data = PlayerDataHandler.getPlayerData(username);
         CCRenderHelper.renderPlayerHead(username, mwidth - 39 - i * 21, 2);
         if (data.isGhost) {
            CCRenderHelper.drawRect((double)(mwidth - 40 - i * 21), 1.0D, 20.0D, 21.0D, "0x000000", 1.0F);
            CCRenderHelper.drawImage((double)(mwidth - 42 - i * 21), -1.0D, new ResourceLocation("countercraft:textures/misc/dead.png"), 24.0D, 24.0D);
         }
      }

      for(i = 0; i < this.tempTeamBlue.size(); ++i) {
         username = (String)this.tempTeamBlue.get(i);
         data = PlayerDataHandler.getPlayerData(username);
         CCRenderHelper.renderPlayerHead(username, mwidth + 21 + i * 21, 2);
         if (data.isGhost) {
            CCRenderHelper.drawRect((double)(mwidth + 20 + i * 21), 1.0D, 20.0D, 21.0D, "0x000000", 1.0F);
            CCRenderHelper.drawImage((double)(mwidth + 18 + i * 21), -1.0D, new ResourceLocation("countercraft:textures/misc/dead.png"), 24.0D, 24.0D);
         }
      }

      i = par1 - 115;
      int ey = par2 - 158;
      CCRenderHelper.drawRect((double)i, (double)ey, 60.0D, 13.0D, "0x000000", 0.4F);
      CCRenderHelper.renderText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "$" + this.getGame().getPlayerGameData(mc.thePlayer.username).getInteger("economy"), i + 2, ey + 3);
   }

   @SideOnly(Side.CLIENT)
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
      CCRenderHelper.renderTextRight(EnumChatFormatting.WHITE + "Map: " + this.getGame().getGameMapType().toUpperCase(), sbx + sbwidth - 3, sby + 20);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Round: " + EnumChatFormatting.BOLD + this.casGame.currentRound, sbx + 4, sby + 25);
      CCRenderHelper.drawImage((double)(sbx + sbwidth / 2 - 8), (double)(sby + 5), new ResourceLocation("countercraft:textures/misc/stopwatch.png"), 16.0D, 16.0D);
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + matchTime, sbx + sbwidth / 2, sby + 24);
      CCRenderHelper.drawRect((double)sbx, (double)(sby + 41), (double)sbwidth, 171.0D, "0x000000", 0.5F);
      CCRenderHelper.renderPlayerScoreboard(sbx, sby + 41, sbwidth, 13, 3, "", EnumChatFormatting.WHITE, this.getGame(), "ПИНГ", "ДЕНЕГ", "У", "С", "СЧ");
      CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.BLUE + "" + this.tempScoreBlue, sbx + 14, sby + 113, 2.0D);

      int i;
      String username;
      PlayerData data;
      String money;
      String kills;
      String deaths;
      String score;
      for(i = 0; i < this.tempTeamBlue.size(); ++i) {
         username = (String)this.tempTeamBlue.get(i);
         data = PlayerDataHandler.getPlayerData(username);
         money = "$" + this.getGame().getPlayerGameData(username).getInteger("economy");
         if (!this.tempTeamBlue.contains(mc.thePlayer.username)) {
            money = "-";
         }

         kills = "" + this.getGame().getPlayerGameData(username).getInteger("kills");
         deaths = "" + this.getGame().getPlayerGameData(username).getInteger("deaths");
         score = "" + this.getGame().getPlayerGameData(username).getInteger("score");
         CCRenderHelper.renderPlayerScoreboard(sbx, sby + 55 + i * 11, sbwidth, 10, 1, username, this.getGame().getPlayerEventHandler().getTeam("Blue").teamColor, this.getGame(), this.getPlayerInfoByUsername(username).responseTime + "", money, kills, deaths, score);
         if (data.isGhost) {
            CCRenderHelper.drawRect((double)sbx, (double)(sby + 55 + i * 11), (double)sbwidth, 10.0D, "0x000000", 0.3F);
         }
      }

      CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.RED + "" + this.tempScoreRed, sbx + 14, sby + 139, 2.0D);

      for(i = 0; i < this.tempTeamRed.size(); ++i) {
         username = (String)this.tempTeamRed.get(i);
         data = PlayerDataHandler.getPlayerData(username);
         money = "$" + this.getGame().getPlayerGameData(username).getInteger("economy");
         if (!this.tempTeamRed.contains(mc.thePlayer.username)) {
            money = "-";
         }

         kills = "" + this.getGame().getPlayerGameData(username).getInteger("kills");
         deaths = "" + this.getGame().getPlayerGameData(username).getInteger("deaths");
         score = "" + this.getGame().getPlayerGameData(username).getInteger("score");
         CCRenderHelper.renderPlayerScoreboard(sbx, sby + 147 + i * 11 + 11, sbwidth, 10, 1, username, this.getGame().getPlayerEventHandler().getTeam("Red").teamColor, this.getGame(), this.getPlayerInfoByUsername(username).responseTime + "", money, kills, deaths, score);
         if (data.isGhost) {
            CCRenderHelper.drawRect((double)sbx, (double)(sby + 147 + i * 11 + 11), (double)sbwidth, 10.0D, "0x000000", 0.3F);
         }
      }

      CCRenderHelper.drawRect((double)(sbx + 4), (double)(sby + 133), (double)(sbwidth - 8), 1.0D, "0xffffff", 0.5F);
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

            Minecraft.getMinecraft().displayGuiScreen((new GuiWSMain((GuiScreen)null, team.teamName)).setPriced((IGameComponentEconomy)this.getGame()));
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

   public String getNextSpectatingUsername(int par1, String username) {
      ArrayList teamList = super.gameHandler.getPlayerEventHandler().getPlayers();
      if (par1 == 0) {
         ++super.spectateIndex;
      } else if (par1 == 1) {
         --super.spectateIndex;
      }

      if (super.spectateIndex >= teamList.size()) {
         super.spectateIndex = 0;
      } else if (super.spectateIndex < 0) {
         super.spectateIndex = teamList.size() - 1;
      }

      return (String)teamList.get(super.spectateIndex);
   }

   public int getCurrentTimeTick() {
      GameStatus status = this.casGame.getStatus();
      if (status == GameStatus.IDLE) {
         return 0;
      } else if (status == GameStatus.PREGAME) {
         return this.casGame.timerPreGame.getTimeRemaining();
      } else {
         if (status == GameStatus.GAME) {
            if (this.casGame.timerPreRound.getTimeRemaining() > 0) {
               return this.casGame.timerPreRound.getTimeRemaining();
            }

            if (this.casGame.timerRound.getTimeRemaining() > 0) {
               return this.casGame.timerRound.getTimeRemaining();
            }

            if (this.casGame.timerPostRound.getTimeRemaining() > 0) {
               return this.casGame.timerPostRound.getTimeRemaining();
            }
         } else if (status == GameStatus.POSTGAME) {
            return this.casGame.timerPostGame.getTimeRemaining();
         }

         return 0;
      }
   }
}
