package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.CloudInventoryFilter;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryItemRename;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerStats;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuRenameItem extends GuiCCMenu implements IGuiCCPromptInvSelResults {
   private CloudItemStack tagStack;
   private CloudItemStack stack;
   private GuiTextField promptInputText;

   public GuiCCMenuRenameItem(CloudItemStack par1) {
      this.tagStack = par1;
   }

   public GuiCCMenuRenameItem(CloudItemStack par1, CloudItemStack par2) {
      this.tagStack = par1;
      this.stack = par2;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("My Profile").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
      int x1 = x + 114;
      int y1 = 1;
      int w1 = 237;
      this.promptInputText = new GuiTextField(super.fontRenderer, x1 + w1 / 2 - 75, 130, 150, 20);
      this.promptInputText.setMaxStringLength(10);
      super.buttonList.add(new GuiFGButton(12, x1 + w1 / 2 - 75, 155, 150, 20, "Set Name Tag"));
      super.buttonList.add(new GuiFGButton(10, x + 116, 211, 80, 20, "Select Item"));
      super.buttonList.add(new GuiFGButton(11, x + 269, 211, 80, 20, "Cancel"));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1GuiButton.id == 10) {
         ArrayList list = new ArrayList(data.getInventory().getListed());
         list = CloudInventoryFilter.filterValue(list);
         list = CloudInventoryFilter.filterDefaultsOut(list);
         list = CloudInventoryFilter.filterOutNameableItems(list);
         super.mc.displayGuiScreen(new GuiCCPromptInventorySelection(this, list, 1));
      }

      if (par1GuiButton.id == 11) {
         super.mc.displayGuiScreen(new GuiCCMenuInventory());
      }

      if (par1GuiButton.id == 12) {
         String name = this.promptInputText.getText();
         if (this.tagStack != null && this.stack != null) {
            if (name != null && name.length() > 0) {
               ClientCloudManager.sendPacket(new PacketInventoryItemRename(this.tagStack.getUUID(), this.stack.getUUID(), name));
               data.getInventory().playSoundEffect(false);
               GuiCCContainerInventory.pageNumber = 0;
               super.mc.displayGuiScreen(new GuiCCMenuInventory());
            }
         } else {
            ClientNotification.createNotification("You need a Tag to rename that!");
         }
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.promptInputText.updateCursorCounter();
   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
      this.promptInputText.textboxKeyTyped(par1, par2);
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      this.promptInputText.mouseClicked(par1, par2, par3);
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      int x1 = x + 114;
      int y1 = 25;
      int w1 = 237;
      CCRenderHelper.drawRectWithShadow((double)x1, (double)y1, (double)w1, 208.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + "Rename Item", x1 + w1 / 2, y1 + 8);
      CCRenderHelper.drawRectWithShadow((double)(x1 + 2), (double)(y1 + 25), (double)(w1 - 4), 70.0D, "0x999999", 1.0F);
      CCRenderHelper.drawRectWithShadow((double)(x1 + w1 / 2 - 30), (double)(y1 + 30), 60.0D, 60.0D, "0x222222", 1.0F);
      this.promptInputText.drawTextBox();
      if (this.stack != null) {
         GuiCCSlotInventory slot = new GuiCCSlotInventory(x1 + w1 / 2 - 23, y1 + 37, this.stack, 46, 46, false);
         slot.doRender(par1, par2, par3);
      }

      this.drawButtons(par1, par2, par3);
   }

   public void onSelected(ArrayList stacks) {
      if (stacks.size() > 0) {
         this.stack = (CloudItemStack)stacks.get(0);
      }

   }
}
