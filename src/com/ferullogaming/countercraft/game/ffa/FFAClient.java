package com.ferullogaming.countercraft.game.ffa;

import com.ferullogaming.countercraft.CCUtils;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.game.GuiWSMain;
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
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class FFAClient extends IGameClient {
   private int tempTime = 0;
   private FreeForAll ffaGame;
   private ArrayList players = new ArrayList();
   private int refreshDelay = 0;
   private int refreshCloudDataDelay = 0;

   public FFAClient(IGame par1) {
      super(par1);
      this.ffaGame = (FreeForAll)par1;
   }

   public void onClientUpdate(Minecraft mc) {
      if (this.refreshDelay++ > 2) {
         this.tempTime = this.getCurrentTimeTick();
         this.players.clear();
         this.players.addAll(GameHelper.orderPlayersByValue(this.getGame(), this.getGame().getPlayerEventHandler().getTeam("players").getPlayers(), "kills"));
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
      String matchTime = "" + CCUtils.getTickAsTime(this.tempTime);
      if (this.ffaGame.getStatus() == GameStatus.PREGAME && this.ffaGame.timerPreGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.ffaGame.getStatus() == GameStatus.GAME && this.ffaGame.timerGame.getTimeRemainingSeconds() < 16) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      if (this.ffaGame.getStatus() == GameStatus.POSTGAME && this.ffaGame.timerPostGame.getTimeRemainingSeconds() < 11) {
         matchTime = EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + matchTime;
      }

      CCRenderHelper.drawRect((double)(mwidth - 19), 23.0D, 38.0D, 13.0D, "0x000000", alpha);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "" + matchTime, mwidth + 1, 26);
      boolean y1;
      if (this.ffaGame.getStatus() == GameStatus.PREGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.WHITE + "Warm Up " + matchTime, mwidth, mheight / 2 - 5, 2.0D);
      }

      if (this.ffaGame.getStatus() == GameStatus.POSTGAME) {
         y1 = true;
         CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.GOLD + "GAME OVER", mwidth, mheight / 2 - 5, 2.0D);
      }

      int i = 0;

      for(int counter = 1; i < this.players.size(); ++i) {
         String username = (String)this.players.get(i);
         if (i == 0) {
            CCRenderHelper.renderPlayerHead(username, mwidth - 10, 2);
         } else if (i % 2 == 1) {
            CCRenderHelper.renderPlayerHead(username, mwidth - 10 - counter * 21, 2);
         } else {
            CCRenderHelper.renderPlayerHead(username, mwidth - 10 + counter * 21, 2);
            ++counter;
         }
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
      CCRenderHelper.drawRect((double)sbx, (double)(sby + 41), (double)sbwidth, 123.0D, "0x000000", 0.5F);
      CCRenderHelper.renderPlayerScoreboard(sbx, sby + 41, sbwidth, 13, 3, "", EnumChatFormatting.WHITE, this.getGame(), "PING", "K", "A", "D");

      for(int i = 0; i < this.players.size(); ++i) {
         String username = (String)this.players.get(i);
         String kills = "" + this.getGame().getPlayerGameData(username).getInteger("kills");
         String assists = "" + this.getGame().getPlayerGameData(username).getInteger("assists");
         String deaths = "" + this.getGame().getPlayerGameData(username).getInteger("deaths");
         CCRenderHelper.renderPlayerScoreboard(sbx, sby + 55 + i * 11, sbwidth, 10, 1, username, this.getGame().getPlayerEventHandler().getTeam("players").teamColor, this.getGame(), this.getPlayerInfoByUsername(username).responseTime + "", kills, assists, deaths);
      }

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
      return false;
   }

   public boolean allowCustomScroll() {
      return true;
   }

   public int getCurrentTimeTick() {
      GameStatus status = this.ffaGame.getStatus();
      if (status == GameStatus.IDLE) {
         return 0;
      } else if (status == GameStatus.PREGAME) {
         return this.ffaGame.timerPreGame.getTimeRemaining();
      } else if (status == GameStatus.GAME) {
         return this.ffaGame.timerGame.getTimeRemaining();
      } else {
         return status == GameStatus.POSTGAME ? this.ffaGame.timerPostGame.getTimeRemaining() : 0;
      }
   }
}
