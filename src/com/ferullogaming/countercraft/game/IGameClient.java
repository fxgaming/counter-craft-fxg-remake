package com.ferullogaming.countercraft.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.renderer.RenderGlobal;

public abstract class IGameClient {
   public IGame gameHandler;
   public int spectateIndex = 0;
   private Vote currentVote;

   public IGameClient(IGame par1) {
      this.gameHandler = par1;
   }

   public abstract void onClientUpdate(Minecraft var1);

   public abstract void onRenderHUD(Minecraft var1, int var2, int var3);

   public abstract void onRenderTAB(Minecraft var1, int var2, int var3);

   public void onRenderWorld(RenderGlobal par1, float par2) {
   }

   public abstract void onKeyPressed(int var1);

   public boolean allowCustomScroll() {
      return false;
   }

   public boolean allowGuiInventory() {
      return false;
   }

   public boolean allowNameTagRendering(String par1) {
      return true;
   }

   public String getNextSpectatingUsername(int par1, String username) {
      ArrayList teamList = this.gameHandler.getPlayerEventHandler().getPlayerTeam(username).getPlayers();
      if (par1 == 0) {
         ++this.spectateIndex;
      } else if (par1 == 1) {
         --this.spectateIndex;
      }

      if (this.spectateIndex >= teamList.size()) {
         this.spectateIndex = 0;
      } else if (this.spectateIndex < 0) {
         this.spectateIndex = teamList.size() - 1;
      }

      return (String)teamList.get(this.spectateIndex);
   }

   protected GuiPlayerInfo getPlayerInfoByUsername(String username) {
      NetClientHandler netclienthandler = Minecraft.getMinecraft().thePlayer.sendQueue;
      List list = netclienthandler.playerInfoList;
      Iterator i$ = list.iterator();

      GuiPlayerInfo info;
      do {
         if (!i$.hasNext()) {
            return new GuiPlayerInfo(username);
         }

         Object object = i$.next();
         info = (GuiPlayerInfo)object;
      } while(!info.name.equals(username));

      return info;
   }

   public Vote getCurrentVote() {
      return this.currentVote;
   }

   public void setCurrentVote(Vote currentVote) {
      this.currentVote = currentVote;
   }

   public boolean allowCustomHUD() {
      return true;
   }

   public IGame getGame() {
      return this.gameHandler;
   }
}
