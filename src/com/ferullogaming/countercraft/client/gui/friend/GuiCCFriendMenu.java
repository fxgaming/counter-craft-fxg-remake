package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.FriendsManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuConsole;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCFriendMenu extends GuiCCMenu {
   public static int pageNumber = 0;
   public static int pages = 1;
   public GuiScreen parentGui;
   private ArrayList slots = new ArrayList();
   private GuiCCSlotFriend selectedSlot = null;
   private int itemsPerPage = 8;
   private int refreshPageDelay = 0;

   public GuiCCFriendMenu(GuiScreen par1) {
      this.parentGui = par1;
   }

   public static void increasePage() {
      if (pages > 1 && pageNumber < pages - 1) {
         ++pageNumber;
      }

   }

   public static void decreasePage() {
      if (pageNumber > 0) {
         --pageNumber;
      }

   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      super.buttonList.clear();
      super.buttonList.add(new GuiFGButton(1, x + 1, 1, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      if (data.group != null) {
         super.buttonList.add(new GuiFGButton(2, x + 32, 1, 30, 20, "Add"));
         super.buttonList.add(new GuiFGButton(6, x + 63, 1, 30, 20, new ResourceLocation("countercraft:textures/gui/console.png")));
      } else {
         super.buttonList.add(new GuiFGButton(2, x + 32, 1, 80, 20, "Add Friend"));
      }

      super.buttonList.add(new GuiFGButton(3, x + 271, 1, 80, 20, "Messages"));
      super.buttonList.add(new GuiFGButton(4, x + 1, 57, 8, 100, "<"));
      super.buttonList.add(new GuiFGButton(5, x + 343, 57, 8, 100, ">"));
      int var1 = FriendsManager.instance().getFriendList().size() + FriendsManager.instance().getPendingFriendList().size();
      pages = var1 / 8;
      if (var1 % 8 != 0) {
         ++pages;
      }

      this.slots.clear();
      int fx = x + 13;
      int fy = 25;
      int fw = 80;
      int fh = 80;
      int slotmarginx = 82;
      int slotmarginy = 82;
      int rows = 4;
      int col = 2;
      int count = 0 + pageNumber * this.itemsPerPage;
      ArrayList list = new ArrayList();
      list.addAll(FriendsManager.instance().getFriendListFiltered());
      list.addAll(FriendsManager.instance().getPendingFriendList());

      for(int i = 0; i < col; ++i) {
         for(int j = 0; j < rows; ++j) {
            if (count < list.size()) {
               GuiCCSlotFriend slot = new GuiCCSlotFriend((String)list.get(count), fx + j * slotmarginx, fy + i * slotmarginy, fw, fh, !FriendsManager.instance().getFriendList().contains(list.get(count)), this);
               this.slots.add(slot);
            }

            ++count;
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      if (par1GuiButton.id == 1) {
         super.mc.displayGuiScreen(this.parentGui);
      }

      if (par1GuiButton.id == 2) {
         super.mc.displayGuiScreen(new GuiCCTPAddFriend(this));
      }

      if (par1GuiButton.id == 3) {
         super.mc.displayGuiScreen(new GuiCCConversationMenu(this));
      }

      if (par1GuiButton.id == 4) {
         decreasePage();
         this.initGui();
      }

      if (par1GuiButton.id == 5) {
         increasePage();
         this.initGui();
      }

      if (par1GuiButton.id == 6) {
         super.mc.displayGuiScreen(new GuiCCMenuConsole(this));
      }

   }

   public void updateScreen() {
      super.updateScreen();
      if (this.refreshPageDelay++ > 20) {
         super.mc.displayGuiScreen(new GuiCCFriendMenu(this.parentGui));
         this.refreshPageDelay = 0;
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Iterator i$ = this.slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotFriend slot = (GuiCCSlotFriend)i$.next();
         Rectangle rect = new Rectangle(slot.x, slot.y, slot.width, slot.height);
         if (rect.contains(par1, par2)) {
            this.selectedSlot = slot;
            if (par3 == 1) {
               slot.onClicked(par1, par2);
            }
         }
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawIntendedBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "Friend Menu", super.width / 2, 3);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + "Max " + cloud.maxFriendSlots / 8 + " " + "Page " + (pageNumber + 1) + "/" + (pages == 0 ? 1 : pages), super.width / 2, 13);
      Iterator i$ = this.slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotFriend slot = (GuiCCSlotFriend)i$.next();
         slot.doRender(this.selectedSlot == slot);
      }

      if (this.slots == null || this.slots.size() <= 0) {
         CCRenderHelper.renderCenteredTextScaledWithOutline("No friends to display.", super.width / 2, 103, 1.0D);
      }

   }
}
