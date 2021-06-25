package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.FriendsManager;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.ConversationMessage;
import com.ferullogaming.countercraft.client.cloud.MatchParty;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketMMSearch;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketMMSearchHostCanceled;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyChatToCloud;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyCreate;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyEditSettings;
import com.ferullogaming.countercraft.client.cloud.packet.mm.PacketPartyLeave;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuFindMatch;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuCreateParty extends GuiCCMenu {
   public static ArrayList partyChat = new ArrayList();
   private static MatchParty lastParty;
   public PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
   public String tempGamemode;
   public ArrayList tempMaps = new ArrayList();
   public String tempRegion = "All";
   public GuiFGButton editButton = new GuiFGButton(0, 0, 0, 0, 0, "");
   public GuiFGButton findMatchButton = new GuiFGButton(0, 0, 0, 0, 0, "");
   private int refreshDelay = 0;
   private ArrayList slots = new ArrayList();
   private GuiTextField promptInputText;
   private int refreshPlayerData = 0;

   public GuiCCMenuCreateParty(boolean par1) {
      if (this.data.getParty() == null) {
         partyChat.clear();
         if (par1) {
            ClientCloudManager.sendPacket(new PacketPartyCreate());
         }
      }

   }

   public void updatePartySettings(String par1, String par2) {
      if (par1 != null) {
         this.tempGamemode = par1;
         this.tempMaps.clear();
      }

      if (par2 != null) {
         if (this.tempMaps.contains(par2)) {
            this.tempMaps.remove(par2);
         } else {
            this.tempMaps.add(par2);
         }
      }

      ClientCloudManager.sendPacket(new PacketPartyEditSettings(this.tempGamemode, this.tempMaps, this.tempRegion));
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      this.initOverheadMenu();
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.promptInputText = new GuiTextField(super.fontRenderer, x + 117, 210, 231, 20);
      this.promptInputText.setMaxStringLength(76);
      GuiFGButton editButton = new GuiFGButton(10, x + 80, 97, 40, 10, EnumChatFormatting.ITALIC + "Edit");
      editButton.drawBackground = false;
      super.buttonList.add(editButton);
      GuiFGButton button2 = new GuiFGButton(12, x + 335, 32, 10, 10, EnumChatFormatting.BOLD + "x");
      button2.drawBackground = false;
      super.buttonList.add(button2);
      button2 = new GuiFGButton(14, x + 134, 32, 40, 10, EnumChatFormatting.ITALIC + "Stop Search");
      button2.drawBackground = false;
      super.buttonList.add(button2);
      this.findMatchButton = (new GuiFGButton(13, x + 6, 211, 100, 16, EnumChatFormatting.BOLD + "Find Match")).setColor("0x18AD43").setToolTip("Find a match the party...", Color.black);
      super.buttonList.add(this.findMatchButton);
      this.initSlots();
   }

   public void initSlots() {
      this.data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      this.slots.clear();
      int x = super.width / 2 - 173;
      if (this.data.getParty() != null) {
         for(int i = 0; i < this.data.getParty().getPartyUsernames().size(); ++i) {
            GuiCCSlotParty slot = new GuiCCSlotParty((String)this.data.getParty().getPartyUsernames().get(i), x + 118 + i * 46, 57, 44, 44);
            this.slots.add(slot);
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      if (par1GuiButton.id == 10 && this.data.getParty().isHost(this.data.getUsername())) {
         GuiCCMenuFindMatch gui = (new GuiCCMenuFindMatch()).setParty();
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 11 && this.data.getParty().isHost(this.data.getUsername())) {
         int x = super.width / 2 - 173;
         GuiCCDDMPartyMaps gui = new GuiCCDDMPartyMaps(this, x - 5, 138, 80, 15, this.data.getParty().gameType);
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 12) {
         if (this.data.getParty() != null && this.data.getParty().isHost(this.data.getUsername()) && this.data.getParty().isSearching) {
            ClientCloudManager.sendPacket(new PacketMMSearchHostCanceled());
         }

         ClientCloudManager.sendPacket(new PacketPartyLeave());
      }

      if (par1GuiButton.id == 13 && this.data.getParty() != null && this.data.getParty().isHost(this.data.getUsername()) && !this.data.getParty().isSearching) {
         if (this.data.getParty().gameType != null && this.data.getParty().gameType.length() > 0) {
            if (this.data.getParty().gameMaps.size() > 0) {
               ClientCloudManager.sendPacket(new PacketMMSearch("", this.data.getParty().getGameRegion(), new ArrayList()));
            } else {
               partyChat.add(new ConversationMessage("-cloud-", "No Maps Selected"));
            }
         } else {
            partyChat.add(new ConversationMessage("-cloud-", "No Game Mode Selected"));
         }
      }

      if (par1GuiButton.id == 14 && this.data.getParty() != null && this.data.getParty().isHost(this.data.getUsername()) && this.data.getParty().isSearching) {
         ClientCloudManager.sendPacket(new PacketMMSearchHostCanceled());
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.promptInputText.updateCursorCounter();
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (lastParty != data.getParty()) {
         if (data.getParty() == null) {
            super.mc.displayGuiScreen(new GuiCCMenuHome());
         }

         lastParty = data.getParty();
      }

      if (this.refreshPlayerData++ > 40) {
         if (data.getParty() != null) {
            Iterator i$ = data.getParty().getPartyUsernames().iterator();

            while(i$.hasNext()) {
               String name = (String)i$.next();
               if (!name.equalsIgnoreCase(data.getUsername()) && !FriendsManager.instance().getFriendList().contains(name)) {
                  PacketClientRequest.sendRequest(RequestType.PLAYER_DATA, name);
               }
            }
         }

         this.refreshPlayerData = 0;
      }

      if (this.refreshDelay++ > 5) {
         this.initSlots();
         this.refreshDelay = 0;
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.promptInputText.mouseClicked(par1, par2, par3);
      Iterator i$ = this.slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotParty slot = (GuiCCSlotParty)i$.next();
         if (slot.isMouseOver(par1, par2) && par3 == 1 && !slot.username.equalsIgnoreCase(super.mc.getSession().getUsername())) {
            GuiCCDDMFriendWhileParty gui = new GuiCCDDMFriendWhileParty(this, slot.x - 15, slot.y + 35, 80, 15, slot.username);
            super.mc.displayGuiScreen(gui);
         }
      }

   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      this.promptInputText.textboxKeyTyped(par1, par2);
      if (par2 == 28) {
         String text = this.promptInputText.getText();
         if (text != null && text.length() > 0) {
            partyChat.add(new ConversationMessage(super.mc.getSession().getUsername(), text));
            ClientCloudManager.sendPacket(new PacketPartyChatToCloud(text));
            this.promptInputText.setText("");
            this.promptInputText.setFocused(true);
         }
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      if (this.data.getParty() == null) {
         CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "Loading Party...", super.width / 2, 50);
      } else {
         super.drawScreen(par1, par2, par3);
         int x = super.width / 2 - 172;
         int x1 = x;
         int y1 = 68;
         int w1 = 110;
         CCRenderHelper.drawRectWithShadow((double)x, 68.0D, 110.0D, 165.0D, GuiCCMenu.menuTheme, 1.0F);
         CCRenderHelper.drawRectWithShadow((double)(x + 2), (double)(y1 + 2), (double)(w1 - 4), 20.0D, GuiCCMenu.menuTheme3, 1.0F);
         CCRenderHelper.drawRectWithShadow((double)(x + 2), (double)(y1 + 25), (double)(w1 - 4), 90.0D, GuiCCMenu.menuTheme3, 1.0F);
         CCRenderHelper.renderCenteredText("Party Info", x + w1 / 2, y1 + 8);
         CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Game Mode", x + 4, y1 + 30);
         CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "" + this.data.getParty().getGameType(), x + 4, y1 + 40);
         CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Map(s)", x + 4, y1 + 60);
         ArrayList maps = StringListHelperCC.getListLimitWidth(this.data.getParty().getGameMaps(), 100);

         int lineCount;
         for(lineCount = 0; lineCount < maps.size(); ++lineCount) {
            CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "" + (String)maps.get(lineCount), x1 + 4, y1 + 70 + lineCount * 10);
         }

         CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Search Region", x1 + 4, y1 + 90);
         CCRenderHelper.renderText(EnumChatFormatting.ITALIC + "" + this.data.getParty().getGameRegion(), x1 + 4, y1 + 100);
         x1 = x + 113;
         y1 = 25;
         int w11 = 237;
         CCRenderHelper.drawRectWithShadow((double)x1, (double)y1, 237.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
         CCRenderHelper.drawRectWithShadow((double)(x1 + 3), (double)(y1 + 2), (double)(w11 - 6), 20.0D, GuiCCMenu.menuTheme3, 1.0F);
         CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Multiplayer", x1 + w11 / 2, y1 + 8);
         CCRenderHelper.drawRectWithShadow((double)(x1 + 3), (double)(y1 + 85), (double)(w11 - 6), 96.0D, GuiCCMenu.menuTheme3, 1.0F);
         this.promptInputText.drawTextBox();
         x1 = x + 118;
         y1 = 115;
         lineCount = 0;
         int totalLines = 0;
         int maxLinesShowing = 9;
         int maxLineWidth = 165;
         String lastName = "";

         int i;
         for(i = 0; i < partyChat.size(); ++i) {
            totalLines += StringListHelperCC.getListLimitWidth(((ConversationMessage)partyChat.get(i)).getMessage(), maxLineWidth).size();
         }

         int skipLines = totalLines > maxLinesShowing ? totalLines - maxLinesShowing : 0;

         for(i = 0; i < partyChat.size(); ++i) {
            ConversationMessage mes = (ConversationMessage)partyChat.get(i);
            boolean greyed = mes.getSender().equalsIgnoreCase(super.mc.getSession().getUsername());
            EnumChatFormatting color = greyed ? EnumChatFormatting.GRAY : EnumChatFormatting.WHITE;
            if (!lastName.equals(mes.getSender())) {
               if (!mes.getSender().equals("-cloud-")) {
                  CCRenderHelper.renderPlayerHead(mes.getSender(), (double)x1, (double)(y1 + lineCount * 10), 0.4D, false);
               }

               lastName = mes.getSender();
            }

            if (lastName.equals("-cloud-")) {
               color = EnumChatFormatting.RED;
            }

            ArrayList messages = StringListHelperCC.getListLimitWidth(mes.getMessage(), maxLineWidth);
            boolean showUsername = true;

            for(int i1 = 0; i1 < messages.size(); ++i1) {
               if (skipLines > 0) {
                  --skipLines;
               } else {
                  CCRenderHelper.renderText((showUsername ? (!mes.getSender().equals("-cloud-") ? mes.getSender() + ": " : "") : "") + color + (String)messages.get(i1), x1 + 10, y1 + lineCount * 10);
                  showUsername = false;
                  ++lineCount;
               }
            }
         }

         Iterator i$ = this.slots.iterator();

         while(i$.hasNext()) {
            GuiCCSlotParty slot = (GuiCCSlotParty)i$.next();
            slot.doRender(par1, par2, par3);
            if (slot.isMouseOver(par1, par2)) {
               slot.doRenderHighlight(1);
            }
         }

         this.drawButtons(par1, par2, par3);
      }
   }
}
