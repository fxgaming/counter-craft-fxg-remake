package com.ferullogaming.countercraft.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.HDD;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketMMSearchHostCanceled;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCMenuCreateParty;
import com.ferullogaming.countercraft.game.references.GameType;
import com.ferullogaming.countercraft.game.references.MapType;
import com.ferullogaming.countercraft.player.PlayerDataHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuFindMatch extends GuiCCMenu {
   public static int selectedGamemode = -1;
   public static int lastSelectedGamemode = -1;
   public static ArrayList selectedMaps = new ArrayList();
   public static boolean isSearching = false;
   public static String region = "ALL";
   public ArrayList gamemodeSlots = new ArrayList();
   public ArrayList mapSlots = new ArrayList();
   public GuiFGButton buttonUS;
   public GuiFGButton buttonEU;
   public GuiFGButton buttonALL;
   private boolean isParty;
   private GuiFGButton confirmButton;
   GuiFGContainerText OCab = (GuiFGContainerText)new GuiFGContainerText(5, 1, 68, 110, 14, this).setText("Личный кабинет").setColor(GuiCCMenu.menuTheme3);
   static int OCabX = 0;
   static double eNow = 0.0D;
   static double eAim = 0.0D;
   static int normalH = 0;
   static int normalY = 0;
   static int timeFromStart = 0;
   static int timeToStop = 60;
   static boolean var1 = false;
   static boolean var2 = false;
   static boolean var3 = false;
   static boolean var4 = false;
   static boolean var5 = false;
   static boolean var6 = false;
   static int currentImage = 0;
   
   public GuiCCMenuFindMatch() {
      this.isParty = false;
   }

   public GuiCCMenuFindMatch setParty() {
      this.isParty = true;
      return this;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      if (this.isParty) {
         x -= 55;
      }

      if (selectedGamemode == -1) {
         selectedGamemode = 1;
      }

      if (!this.isParty) {
         this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
         OCabX = x + 1;
         OCab.posX = x + 1;
         this.normalH = this.OCab.height;
         this.normalY = this.OCab.posY;
         this.addContainer(OCab);
         this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
      }

      GuiFGButton button = new GuiFGButton(21, x + 335, 25, 16, 16, EnumChatFormatting.BOLD + "x");
      button.drawBackground = false;
      super.buttonList.add(button);
      if (!this.isParty) {
         button = new GuiFGButton(23, x + 305, 26, 16, 16, EnumChatFormatting.ITALIC + "Стоп");
         button.drawBackground = false;
         super.buttonList.add(button);
      }

      boolean enableButtons = true;
      this.gamemodeSlots.clear();
      int gx = x + 121 + 9;
      int gy = 49;

      int len$;
      int i$;
      for(int i = 0; i < GameType.getList().size(); ++i) {
         GameType type = (GameType)GameType.getList().get(i);
         len$ = i * 35;
         i$ = 0;
         if (i >= 6) {
            len$ -= 175;
            i$ += 35;
         }

         GuiCCSlotGamemode slot = new GuiCCSlotGamemode(type, gx + len$ + 1, gy + i$ + 1);
         this.gamemodeSlots.add(slot);
         try {slot.enabled = CounterCraft.instance.getHDD().gitActive[i];
         } catch (Exception e) {slot.enabled = false;}
      }

      GameType game;
      if (lastSelectedGamemode != selectedGamemode) {
         selectedMaps.clear();
         game = GameType.gameTypeList[selectedGamemode];
         MapType[] arr$ = game.getMaps();
         len$ = arr$.length;

         for(i$ = 0; i$ < len$; ++i$) {
            MapType map = arr$[i$];
            selectedMaps.add(map);
         }

         lastSelectedGamemode = selectedGamemode;
      }

      if (selectedGamemode != -1) {
         game = GameType.gameTypeList[selectedGamemode];
      }

      this.confirmButton = (new GuiFGButton(22, x + 48 + 118 - 50, 217, 80, 14, "Найти матч")).setColor("0x18AD43").setToolTip("Найти матч на серверах Mixlab.pw", Color.black);
      this.confirmButton.enabled = enableButtons;
      if (this.confirmButton.enabled) this.confirmButton.enabled = CounterCraft.instance.ban.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase()) ? false : true;
      super.buttonList.add(this.confirmButton);
      region = "RU";
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = this.gamemodeSlots.iterator();
      while(i$.hasNext()) {
         GuiCCSlotGamemode slot = (GuiCCSlotGamemode)i$.next();
         if (slot.isMouseOver(par1, par2)) {
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 2.0F);
            if (isSearching) {
               ClientCloudManager.sendPacket(new PacketMMSearchHostCanceled());
               isSearching = false;
            }
            GuiCCMenuFindMatch gui = new GuiCCMenuFindMatch();
            gui.isParty = this.isParty;
            if (CounterCraft.instance.getHDD().gitActive[slot.game.getID() - 1]) selectedGamemode = slot.game.getID();
            super.mc.displayGuiScreen(gui);
            return;
         }
      }

      if (selectedGamemode > 0) {
         GameType type = GameType.gameTypeList[selectedGamemode];
         this.confirmButton.enabled = true;
         PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
         if (!type.isRankAccepted(data)) {
            this.confirmButton.enabled = false;
         }
      }
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1GuiButton.id == 21) {
         if (!this.isParty) {
            ClientCloudManager.sendPacket(new PacketMMSearchHostCanceled());
            isSearching = false;
            super.mc.displayGuiScreen(new GuiCCMenuHome());
         } else {
            super.mc.displayGuiScreen(new GuiCCMenuCreateParty(false));
         }
      }

      if (par1GuiButton.id == 22) {
    	  if (!CounterCraft.instance.ban.containsKey("" + Minecraft.getMinecraft().getSession().getUsername().toLowerCase())) this.searchForGame();
    	  else {
    		  this.confirmButton.displayString = "§4Забанен";
    	  }
      }

      if (par1GuiButton.id == 23 && isSearching) {
         ClientCloudManager.sendPacket(new PacketMMSearchHostCanceled());
         isSearching = false;
      }
      
   }

   public void updateScreen() {
      super.updateScreen();
      
      if (GuiCCMenuFindMatch.isSearching) this.eAim = 48.0D;
      else this.eAim = 0.0D;
      if (this.eNow < this.eAim) this.eNow += 2D;
      else if (this.eNow > this.eAim) this.eNow -= 2D;
      this.OCab.posY = this.normalY + (int)this.eNow;
      
      if (this.eNow == this.eAim && this.isSearching) {
    	  this.var1 = true; 
    	  if (this.currentImage == 5 && !this.var2) this.currentImage = 0;
    	  else if (this.currentImage == 5 && this.var2) this.currentImage = 6;
    	  else if (this.currentImage == 6 && this.var2) this.currentImage = 7;
    	  else if (this.currentImage == 7 && this.var2) this.currentImage = 8;
    	  else if (this.currentImage <= 4) this.currentImage++;
    	  this.timeFromStart++;
    	  if (this.timeFromStart >= 120) {
    		  this.var2 = true;
    		  this.timeToStop--;
    		  if (this.timeToStop <= 0) {
    			  this.var4 = true;
    			  this.setServerAndConnect(this.selectedGamemode);
    		  }
    	  }
      } else if (this.isSearching && this.checkTime()) {
    	  this.clearTime();
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      if (this.isParty) {
          x -= 55;
       }
      if (this.isSearching && this.var1) {
    	  GL11.glPushMatrix();
    	  CCRenderHelper.drawImage(x + 24.0D, 78.0D, new ResourceLocation("countercraft:textures/gui/find/planet" + this.currentImage + ".png"), 64, 24);
    	  GL11.glPopMatrix();
    	  CCRenderHelper.renderCenteredTextScaledWithOutline("Ожидание: " + (GuiCCMenuFindMatch.timeFromStart / 20) + " сек.", x + 56, 68, 1.0);
    	  if (this.timeToStop != 60) CCRenderHelper.renderCenteredTextScaledWithOutline("Осталось: " + ((this.timeToStop + 20) / 20) + " сек.", x + 56, 104, 1.0);
      }
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 45.0D, 237.0D, 188.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x + 114), 25.0D, 237.0D, 17.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x + 115), 46.0D, 235.0D, 71.0D, GuiCCMenu.menuTheme, 1.0F);
      int x1 = x + 121 + 9;
      int y1 = 49;

      int hx;
      int hy;
      for(int i = 0; i < 10; ++i) {
         hx = i * 35;
         hy = 0;
         if (i >= 6) {
            hx -= 175;
            hy += 35;
         }

         CCRenderHelper.drawRectWithShadow((double)(x1 + hx), (double)(y1 + hy), 30.0D, 30.0D, GuiCCMenu.menuTheme, 0.5F);
      }

      GuiCCSlotGamemode hoverslot = null;
      Iterator i$ = this.gamemodeSlots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotGamemode slot = (GuiCCSlotGamemode)i$.next();
         slot.doRender(par1, par2, par3, false);
         if (slot.isMouseOver(par1, par2)) {
            hoverslot = slot;
         }

         if (slot.game.getID() == selectedGamemode) {
            slot.doRenderHighlight(1);
         } else {
            slot.doRenderHighlightNot();
         }
      }

      if (selectedGamemode > 0) {
         GameType type = GameType.gameTypeList[selectedGamemode];
      }

      if (hoverslot != null) {
         hx = par1 + 10;
         hy = par2 - 5;
         int hw = 160;
         int hh = 109;
         int hm = 2;
         CCRenderHelper.drawRectWithShadow((double)hx, (double)hy, (double)hw, (double)hh, "0xFFFFFF", 0.5F);
         CCRenderHelper.drawRectWithShadow((double)(hx + hm), (double)(hy + hm), (double)(hw - hm * 2), (double)(hh - hm * 2), "0x111111", 1.0F);
         GameType game = hoverslot.game;
         CCRenderHelper.renderText(game.getDisplayName(), hx + 5, hy + 5);
         ArrayList desc = StringListHelperCC.getListLimitWidth(game.description, 150);

         for(int i = 0; i < desc.size(); ++i) {
            String var1 = (String)desc.get(i);
            CCRenderHelper.renderText(EnumChatFormatting.GRAY + var1, hx + 5, hy + 15 + i * 10);
         }
      }

      this.drawButtons(par1, par2, par3);
   }

   public void searchForGame() {
      if (selectedGamemode != -1) {
         GameType game = GameType.gameTypeList[selectedGamemode];
         ArrayList maps = new ArrayList();
         this.isSearching = true;
      }
   }
   
   public void clearTime() {
	   this.var1 = false;
	   this.var2 = false;
	   this.var3 = false;
	   this.var4 = false;
	   this.var5 = false;
	   this.var6 = false;
	   this.timeFromStart = 0;
	   this.timeToStop = 60;
	   this.currentImage = 0;
   }
   
   public boolean checkTime() {
	   if (this.var1) return true;
	   if (this.var2) return true;
	   if (this.var3) return true;
	   if (this.var4) return true;
	   if (this.var5) return true;
	   if (this.var6) return true;
	   if (this.timeFromStart != 0) return true;
	   if (this.timeToStop != 60) return true;
	   if (this.currentImage != 0) return true;
	   return false;
   }
   
   public void setServerAndConnect(int srvid) {
	   int id = srvid - 1;
	   HDD hdd = CounterCraft.instance.getHDD();
	   ServerData currsrv = new ServerData("MIXLAB.PW", "164.132.201.202:200" + srvid);
	   this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, currsrv));
	   this.isSearching = false;
	   this.clearTime();
   }
   
   public boolean hasSymbols(String a) {
	   if (a.contains("a")) return true;
	   if (a.contains("b")) return true;
	   if (a.contains("c")) return true;
	   if (a.contains("d")) return true;
	   if (a.contains("e")) return true;
	   if (a.contains("f")) return true;
	   if (a.contains("g")) return true;
	   if (a.contains("h")) return true;
	   if (a.contains("i")) return true;
	   if (a.contains("j")) return true;
	   if (a.contains("k")) return true;
	   if (a.contains("l")) return true;
	   if (a.contains("m")) return true;
	   if (a.contains("o")) return true;
	   if (a.contains("p")) return true;
	   if (a.contains("q")) return true;
	   if (a.contains("r")) return true;
	   if (a.contains("s")) return true;
	   if (a.contains("t")) return true;
	   if (a.contains("u")) return true;
	   if (a.contains("v")) return true;
	   if (a.contains("w")) return true;
	   if (a.contains("x")) return true;
	   if (a.contains("y")) return true;
	   if (a.contains("z")) return true;
	   return false;
   }
}
