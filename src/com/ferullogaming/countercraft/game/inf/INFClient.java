package com.ferullogaming.countercraft.game.inf;

import com.ferullogaming.countercraft.CCUtils;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameStatus;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.Team;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class INFClient extends IGameClient {
   private int tempTime = 0;
   private Infected theGame;
   private ArrayList tempTeamRed = new ArrayList();
   private ArrayList tempTeamBlue = new ArrayList();
   private int refreshDelay = 0;
   private int refreshCloudDataDelay = 0;

   public INFClient(IGame par1) {
      super(par1);
      this.theGame = (Infected)par1;
   }

   public void onClientUpdate(Minecraft mc) {
      if (this.refreshDelay++ > 2) {
         this.tempTime = this.getCurrentTimeTick();
         this.tempTeamRed.clear();
         this.tempTeamRed.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("Dead").getPlayers(), "score"));
         this.tempTeamBlue.clear();
         this.tempTeamBlue.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("Living").getPlayers(), "score"));
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

   @SideOnly(Side.CLIENT)
   public void onRenderHUD(Minecraft mc, int par1, int par2) {
      int mwidth = par1 / 2;
      int mheight = par2 / 2;
      float alpha = 0.6F;
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      if (this.theGame.getStatus() == GameStatus.PREGAME && this.theGame.timerPreGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.theGame.getStatus() == GameStatus.GAME && this.theGame.timerGame.getTimeRemainingSeconds() < 16) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.theGame.getStatus() == GameStatus.POSTGAME && this.theGame.timerPostGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      CCRenderHelper.drawRect((double)(mwidth - 19), 15.0D, 18.0D, 11.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(this.getGame().getPlayerEventHandler().getTeam("Dead").teamColor + "" + this.tempTeamRed.size(), mwidth - 9, 17);
      CCRenderHelper.drawRect((double)(mwidth + 1), 15.0D, 18.0D, 11.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(this.getGame().getPlayerEventHandler().getTeam("Living").teamColor + "" + this.tempTeamBlue.size(), mwidth + 10, 17);
      CCRenderHelper.drawRect((double)(mwidth - 19), 1.0D, 38.0D, 13.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "" + matchTime, mwidth + 1, 4);
      boolean y1;
      if (this.theGame.getStatus() == GameStatus.PREGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.WHITE + "Ожидаем игроков " + matchTime, mwidth, mheight / 2 - 32, 2.0D);
      }

      if (this.theGame.getStatus() == GameStatus.POSTGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.GOLD + "Игра окончена", mwidth, mheight / 2 - 5, 2.0D);
      }

      if (mc.thePlayer != null && mc.thePlayer.getActivePotionEffects().size() > 0) {
         EntityPlayer player = mc.thePlayer;
         ArrayList potions = new ArrayList(player.getActivePotionEffects());

         for(int i = 0; i < potions.size(); ++i) {
            PotionEffect effect = (PotionEffect)potions.get(i);
            int var1 = par1 - 115 + i * 25;
            ResourceLocation res = new ResourceLocation("countercraft:textures/items/infiteminv.png");
            if (effect.getPotionID() == Potion.damageBoost.id) {
               res = new ResourceLocation("countercraft:textures/items/infitemdam.png");
            }

            if (effect.getPotionID() == Potion.heal.id) {
               res = new ResourceLocation("countercraft:textures/items/infitemhealth.png");
            }

            if (effect.getPotionID() == Potion.moveSpeed.id) {
               res = new ResourceLocation("countercraft:textures/items/infitemspeed.png");
            }

            CCRenderHelper.drawImage((double)var1, (double)(par2 - 195), res, 24.0D, 24.0D);
            int var2 = effect.getDuration();
            CCRenderHelper.renderText("" + var2 / 20, var1, par2 - 180);
         }
      }

   }

   public void onRenderWorld(RenderGlobal par1, float par2) {
      for(int i = 0; i < this.theGame.itemSpawns.size(); ++i) {
         ((ItemSpawn)this.theGame.itemSpawns.get(i)).doRender(par2, this.theGame);
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
      CCRenderHelper.renderTextRight(EnumChatFormatting.WHITE + "Map: " + this.getGame().getGameMapType().toUpperCase(), sbx + sbwidth - 3, sby + 20);
      CCRenderHelper.drawImage((double)(sbx + sbwidth / 2 - 8), (double)(sby + 5), new ResourceLocation("countercraft:textures/misc/stopwatch.png"), 16.0D, 16.0D);
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + matchTime, sbx + sbwidth / 2, sby + 24);
      CCRenderHelper.drawRect((double)sbx, (double)(sby + 41), (double)sbwidth, 193.0D, "0x000000", 0.5F);
      CCRenderHelper.renderPlayerScoreboard(sbx, sby + 41, sbwidth, 13, 3, "", EnumChatFormatting.WHITE, this.getGame(), "Пинг", "У", "С", "СЧ");
      ArrayList list = new ArrayList();
      list.addAll(this.tempTeamBlue);
      list.addAll(this.tempTeamRed);

      for(int i = 0; i < list.size(); ++i) {
         String username = (String)list.get(i);
         if (username != null) {
            Team team = this.getGame().getPlayerEventHandler().getPlayerTeam(username);
            if (team != null) {
               String kills = "" + this.getGame().getPlayerGameData(username).getInteger("kills");
               String deaths = "" + this.getGame().getPlayerGameData(username).getInteger("deaths");
               String score = "" + this.getGame().getPlayerGameData(username).getInteger("score");
               CCRenderHelper.renderPlayerScoreboard(sbx, sby + 55 + i * 11, sbwidth, 10, 1, username, team.teamColor, this.getGame(), this.getPlayerInfoByUsername(username).responseTime + "", kills, deaths, score);
            }
         }
      }

   }

   public void onKeyPressed(int par1) {
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
      GameStatus status = this.theGame.getStatus();
      if (status == GameStatus.IDLE) {
         return 0;
      } else if (status == GameStatus.PREGAME) {
         return this.theGame.timerPreGame.getTimeRemaining();
      } else if (status == GameStatus.GAME) {
         return this.theGame.timerGame.getTimeRemaining();
      } else {
         return status == GameStatus.POSTGAME ? this.theGame.timerPostGame.getTimeRemaining() : 0;
      }
   }
}
