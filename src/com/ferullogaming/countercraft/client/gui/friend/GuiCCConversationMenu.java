package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.FriendsManager;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.Conversation;
import com.ferullogaming.countercraft.client.cloud.ConversationMessage;
import com.ferullogaming.countercraft.client.cloud.packet.friend.PacketMessageSend;
import com.ferullogaming.countercraft.client.gui.GuiCCDDMSettings;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCConversationMenu extends GuiCCMenu {
   public static String playerViewing;
   private static ArrayList slots = new ArrayList();
   private static ArrayList tempList = new ArrayList();
   private static int topSlot = 0;
   private static int refreshDelay = 0;
   private static GuiFGScrollBar scrollBar;
   public GuiScreen parentGui;
   private ResourceLocation messageBackground = new ResourceLocation("countercraft", "textures/gui/messagebackground.png");
   private int totalToShow = 0;
   private int showingPerPage = 9;
   private GuiTextField promptInputText;

   public GuiCCConversationMenu(GuiScreen par1) {
      this.parentGui = par1;
   }

   public GuiCCConversationMenu setViewing(String par1) {
      playerViewing = par1;
      return this;
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 172;
      super.buttonList.clear();
      super.buttonList.add(new GuiFGButton(1, x, 1, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      super.buttonList.add(new GuiFGButton(2, x + 31, 1, 80, 20, "Options"));
      scrollBar = new GuiFGScrollBar(x + 108, 26, 18, 205, 0);
      topSlot = 0;
      GuiFGButton but1 = new GuiFGButton(7, x + 310, 28, 30, 20, "Options");
      but1.drawBackground = false;
      super.buttonList.add(but1);
      this.promptInputText = new GuiTextField(super.fontRenderer, x + 132, 210, 150, 20);
      this.promptInputText.setMaxStringLength(60);
      super.buttonList.add(new GuiFGButton(5, x + 285, 210, 30, 20, "Send"));
      super.buttonList.add(new GuiFGButton(6, x + 287 + 31, 210, 30, 20, "Clear"));
      this.initSlots();
   }

   public void initSlots() {
      int x = super.width / 2 - 172;
      tempList.clear();
      tempList.addAll(new ArrayList(FriendsManager.instance().getFriendListFiltered()));
      int tx = x + 4;
      int ty = 31;
      int tw = 100;
      int th = 20;
      int tym = 22;
      slots.clear();

      for(int i = 0; i < 9; ++i) {
         int i1 = i + topSlot;
         if (i1 < tempList.size() && i1 > -1) {
            GuiCCSlotFriendSmall slot = new GuiCCSlotFriendSmall((String)tempList.get(i1), tx, ty + i * tym, tw, th);
            slots.add(slot);
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      int x = super.width / 2 - 172;
      if (par1GuiButton.id == 1) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      if (par1GuiButton.id == 2) {
         super.mc.displayGuiScreen(new GuiCCDDMSettings(this, par1GuiButton.xPosition, par1GuiButton.yPosition + 21, 80, 15));
      }

      if (par1GuiButton.id == 5) {
         String text = this.promptInputText.getText();
         if (text != null && text.length() > 0) {
            Conversation con = ClientManager.instance().getConversationHandler().getConversation(playerViewing);
            con.addMessage(super.mc.getSession().getUsername(), text);
            this.promptInputText.setText("");
            this.promptInputText.setFocused(true);
            PacketMessageSend packet = new PacketMessageSend(con.player, text);
            ClientCloudManager.sendPacket(packet);
         }
      }

      if (par1GuiButton.id == 6) {
         this.promptInputText.setText("");
      }

      if (par1GuiButton.id == 7 && playerViewing != null && playerViewing.length() > 0) {
         GuiCCDDMFriendWhileMessaging gui = new GuiCCDDMFriendWhileMessaging(this, x + 285, 47, 80, 15, playerViewing);
         super.mc.displayGuiScreen(gui);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      scrollBar.update();
      this.promptInputText.updateCursorCounter();
      if (topSlot > tempList.size()) {
         topSlot = 0;
         scrollBar.resetScroller();
         this.initSlots();
      }

      if (scrollBar.isClicked() || scrollBar.isMouseScrolling()) {
         topSlot = scrollBar.getTopSlot();
         this.initSlots();
      }

      if (refreshDelay++ > 5) {
         this.totalToShow = tempList.size();
         if (this.totalToShow - this.showingPerPage > 0) {
            this.totalToShow -= this.showingPerPage;
         } else {
            this.totalToShow = 0;
         }

         scrollBar.setIncrements(this.totalToShow);
         this.initSlots();
         refreshDelay = 0;
      }

      for(int i = 0; i < tempList.size(); ++i) {
         Conversation con = ClientManager.instance().getConversationHandler().getConversation((String)tempList.get(i));
         if (con.hasNotification) {
            if (con.notifyBlinkTick++ >= 10) {
               con.blink = !con.blink;
               con.notifyBlinkTick = 0;
            }
         } else {
            con.notifyBlinkTick = 0;
            con.blink = false;
         }
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      this.promptInputText.textboxKeyTyped(par1, par2);
      if (par2 == 28) {
         String text = this.promptInputText.getText();
         if (text != null && text.length() > 0) {
            Conversation con = ClientManager.instance().getConversationHandler().getConversation(playerViewing);
            con.addMessage(super.mc.getSession().getUsername(), text);
            this.promptInputText.setText("");
            this.promptInputText.setFocused(true);
            PacketMessageSend packet = new PacketMessageSend(con.player, text);
            ClientCloudManager.sendPacket(packet);
         }
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.promptInputText.mouseClicked(par1, par2, par3);
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotFriendSmall slot = (GuiCCSlotFriendSmall)i$.next();
         if (slot.isMouseOver(par1, par2) && par3 == 0) {
            playerViewing = slot.username;
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
         }
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawIntendedBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 172;
      CCRenderHelper.drawRectWithShadow((double)x, 25.0D, 127.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x + 2), 27.0D, 104.0D, 204.0D, "0x111111", 1.0F);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "Messages", super.width / 2, 7);
      scrollBar.doRender(par1, par2, par3);

      for(int i = 0; i < slots.size(); ++i) {
         ((GuiCCSlotFriendSmall)slots.get(i)).doRender(par1, par2, par3);
         if (((GuiCCSlotFriendSmall)slots.get(i)).isMouseOver(par1, par2)) {
            ((GuiCCSlotFriendSmall)slots.get(i)).doRenderHighlight(1);
         }
      }

      CCRenderHelper.drawRectWithShadow((double)(x + 130), 25.0D, 220.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      if (playerViewing != null && playerViewing.length() > 0) {
         CCRenderHelper.drawRectWithShadow((double)(x + 132), 50.0D, 216.0D, 155.0D, "0x111111", 1.0F);
         this.promptInputText.drawTextBox();
         CCRenderHelper.renderPlayerHead(playerViewing, (double)(x + 133), 28.0D, 1.0D, false);
         CCRenderHelper.renderText(playerViewing, x + 156, 34);
         Conversation con = ClientManager.instance().getConversationHandler().getConversation(playerViewing);
         con.hasNotification = false;
         int x1 = x + 134;
         int y1 = 52;
         int lineCount = 0;
         int totalLines = 0;
         int maxLinesShowing = 15;
         int maxLineWidth = 140;
         String lastName = "";

         int i;
         for(i = 0; i < con.messages.size(); ++i) {
            totalLines += StringListHelperCC.getListLimitWidth(((ConversationMessage)con.messages.get(i)).getMessage(), maxLineWidth).size();
         }

         int skipLines = totalLines > maxLinesShowing ? totalLines - maxLinesShowing : 0;

         for(i = 0; i < con.messages.size(); ++i) {
            ConversationMessage mes = (ConversationMessage)con.messages.get(i);
            boolean greyed = mes.getSender().equalsIgnoreCase(super.mc.getSession().getUsername());
            EnumChatFormatting color = greyed ? EnumChatFormatting.GRAY : EnumChatFormatting.WHITE;
            if (!lastName.equals(mes.getSender())) {
               CCRenderHelper.renderPlayerHead(mes.getSender(), (double)x1, (double)(y1 + lineCount * 10), 0.4D, false);
               lastName = mes.getSender();
            }

            ArrayList messages = StringListHelperCC.getListLimitWidth(mes.getMessage(), maxLineWidth);
            boolean firstMessage = true;

            for(int i1 = 0; i1 < messages.size(); ++i1) {
               if (skipLines > 0) {
                  --skipLines;
               } else {
                  CCRenderHelper.renderText((firstMessage ? mes.getSender() + ": " : "") + color + (String)messages.get(i1), x1 + 10, y1 + lineCount * 10);
                  firstMessage = false;
                  ++lineCount;
               }
            }
         }

         this.drawButtons(par1, par2, par3);
      }

   }
}
