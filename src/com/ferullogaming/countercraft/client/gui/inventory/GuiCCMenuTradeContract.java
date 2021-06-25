package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.CloudInventoryFilter;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryContract;
import com.ferullogaming.countercraft.client.events.EventInvTradeup;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

public class GuiCCMenuTradeContract extends GuiCCMenu implements IGuiCCPromptInvSelResults, IGuiFGPromptYesNo {
   private ArrayList selectedStacks = new ArrayList();
   private GuiFGButton buttonTrade;
   private String message = "Please Select Items";

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      int width1 = 350;
      super.buttonList.add(new GuiFGButton(10, x + (width1 / 2 - 125) + 5, 208, 80, 20, "Select Items"));
      this.buttonTrade = new GuiFGButton(11, x + (width1 / 2 - 125) + 250 - 85, 208, 80, 20, EnumChatFormatting.BOLD + "Begin Trade");
      super.buttonList.add(this.buttonTrade);
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1GuiButton.id == 10) {
         ArrayList list = new ArrayList(data.getInventory().getListed());
         list = CloudInventoryFilter.filterValue(list);
         list = CloudInventoryFilter.filterDefaultsOut(list);
         list = CloudInventoryFilter.filterContractablesOut(list);
         list = CloudInventoryFilter.filterTierOut(list, 5);
         list = CloudInventoryFilter.filterTierOut(list, 6);
         super.mc.displayGuiScreen(new GuiCCPromptInventorySelection(this, list, 10));
      }

      if (par1GuiButton.id == 11) {
         GuiFGYesNoPrompt gui = new GuiFGYesNoPrompt(1, this);
         gui.addInformation("Once Traded, these Items are deleted", "for a new (Higher Tier) Item.", EnumChatFormatting.RED + "Do you wish to continue?");
         super.mc.displayGuiScreen(gui);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      if (this.selectedStacks.size() < 10) {
         this.message = "Please Select 10 Items of the same Value";
         this.buttonTrade.enabled = false;
         if (this.selectedStacks.size() != 0) {
            this.message = "Please Select " + EnumChatFormatting.RED + "10 Items" + EnumChatFormatting.RESET + " of the same Value";
         }

      } else {
         if (this.selectedStacks.size() == 10) {
            if (this.getValueTrading() == -1) {
               this.message = EnumChatFormatting.RED + "Items MUST be of the SAME Value";
               this.buttonTrade.enabled = false;
               return;
            }

            if (this.getValueTrading() == 5) {
               this.message = EnumChatFormatting.RED + "'Diamond' Tier Items can not be Contracted.";
               this.buttonTrade.enabled = false;
               return;
            }
         }

         if (this.selectedStacks.size() == 10 && this.getValueTrading() != -1) {
            this.message = EnumChatFormatting.GREEN + "Click 'Begin Trade' to continue";
            this.buttonTrade.enabled = true;
         }

      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 172;
      int width1 = 350;
      int width2 = 250;
      int mwidth1 = x + (width1 / 2 - width2 / 2);
      int height1 = 208;
      CCRenderHelper.drawRectWithShadow((double)mwidth1, 25.0D, (double)width2, (double)height1, "0x343434", 1.0F);
      CCRenderHelper.drawRectWithShadow((double)mwidth1, 25.0D, (double)width2, 20.0D, GuiCCMenu.menuTheme3, 1.0F);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.GRAY + "" + EnumChatFormatting.BOLD + "Trade Contract", x + width1 / 2, 31);
      CCRenderHelper.renderTextScaled("NOTE: Once Traded, you may NEVER see the items again.", mwidth1 + 5, 50, 0.7D);
      CCRenderHelper.renderTextScaled("Items can not be retrieved once traded nor refunded. Good Luck!", mwidth1 + 5, 58, 0.7D);
      CCRenderHelper.renderCenteredText(this.message, x + width1 / 2, 188);
      CCRenderHelper.drawRectWithShadow((double)(mwidth1 + 5), 70.0D, (double)(width2 - 10), 107.0D, "0x222222", 1.0F);
      Iterator i$ = this.selectedStacks.iterator();

      while(i$.hasNext()) {
         GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
         slot.doRender(par1, par2, par3);
      }

      this.drawButtons(par1, par2, par3);
   }

   public int getValueTrading() {
      int var1 = -1;

      for(int i = 0; i < this.selectedStacks.size(); ++i) {
         if (!((GuiCCSlotInventory)this.selectedStacks.get(i)).stack.getCloudItem().isContrableAble()) {
            return -1;
         }

         if (var1 == -1) {
            var1 = ((GuiCCSlotInventory)this.selectedStacks.get(i)).stack.getCloudItem().getValue().rarityID;
         } else if (var1 != ((GuiCCSlotInventory)this.selectedStacks.get(i)).stack.getCloudItem().getValue().rarityID) {
            return -1;
         }
      }

      return var1;
   }

   public void onSelected(ArrayList stacks) {
      this.selectedStacks.clear();
      int x = super.width / 2 - 172;
      int width1 = 350;
      int width2 = 250;
      int mwidth1 = x + (width1 / 2 - width2 / 2);
      int sx1 = mwidth1 + 16;
      int sy1 = 80;
      int sdx = 0;
      int sdy = 0;

      for(int i = 0; i < stacks.size(); ++i) {
         if (i != 0 && i % 5 == 0) {
            sdx -= 220;
            sdy += 44;
         }

         GuiCCSlotInventory slot = new GuiCCSlotInventory(sx1 + 44 * i + sdx, sy1 + sdy, (CloudItemStack)stacks.get(i), 42, 42, false);
         this.selectedStacks.add(slot);
      }

   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2) {
         ArrayList list = new ArrayList();
         ArrayList list2 = new ArrayList();

         for(int i = 0; i < this.selectedStacks.size(); ++i) {
            list.add(((GuiCCSlotInventory)this.selectedStacks.get(i)).stack.getUUID());
            list2.add(((GuiCCSlotInventory)this.selectedStacks.get(i)).stack);
         }

         EventInvTradeup event = new EventInvTradeup(list2);
         if (!MinecraftForge.EVENT_BUS.post(event)) {
            Minecraft.getMinecraft().sndManager.playSoundFX("random.anvil_use", 1.0F, 1.0F);
            ClientCloudManager.sendPacket(new PacketInventoryContract(this.getValueTrading(), list));
            GuiCCContainerInventory.pageNumber = 0;
            super.mc.displayGuiScreen(new GuiCCMenuInventory());
         }
      }

   }
}
