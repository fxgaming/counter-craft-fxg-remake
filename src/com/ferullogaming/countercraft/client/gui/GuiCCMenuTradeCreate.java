package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PunishmentType;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeCreate;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCContainerInventorySelection;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuTradeCreate extends GuiCCMenu {
   public String userTradingWith;
   public ArrayList myItemsOffering = new ArrayList();
   public ArrayList otherItemsReturn = new ArrayList();
   private GuiScreen parentGui;
   private boolean invClientShowing = true;
   private int lastTradeDelay = 0;

   public GuiCCMenuTradeCreate(GuiScreen par1, String par2) {
      this.parentGui = par1;
      this.userTradingWith = par2;
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      super.buttonList.add(new GuiFGButton(20, x, 1, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      if (ClientTickHandler.tradeDelay <= 0) {
         int by = 25;
         super.buttonList.add(new GuiFGButton(15, x, by, 40, 20, "Мой инвентарь"));
         super.buttonList.add(new GuiFGButton(16, x + 43, by, 107, 20, this.userTradingWith + "'Инвентаризация"));
         GuiFGButton button = new GuiFGButton(17, x + 154 + 3, 210, 80, 20, "Отправить предложение");
         button.enabled = !data.hasPunishmentType(PunishmentType.BAN_TRADING);
         super.buttonList.add(button);
         super.buttonList.add(new GuiFGButton(18, x + 154 + 114, 210, 80, 20, "Отменить"));
         this.initCont((PlayerDataCloud)null);
         ((GuiCCContainerInventorySelection)this.getContainer(1)).addButtons((ArrayList)super.buttonList);
      }
   }

   public void initCont(PlayerDataCloud par1) {
      super.guiContainers.clear();
      int x = super.width / 2 - 173;
      this.invClientShowing = par1 == null;
      GuiCCContainerInventorySelection gui = (new GuiCCContainerInventorySelection(1, x, 49, 3, 3, this)).setData(par1);
      this.addContainer(gui);
   }

   public void refreshOffers() {
      GuiCCContainerInventorySelection cont = (GuiCCContainerInventorySelection)this.getContainer(1);
      int x = super.width / 2 - 173 + 154 + 4;
      int i;
      GuiCCSlotInventory slot;
      if (this.invClientShowing) {
         this.myItemsOffering.clear();
         this.myItemsOffering.addAll(new ArrayList(cont.getSelectedSlots()));

         for(i = 0; i < this.myItemsOffering.size(); ++i) {
            slot = (GuiCCSlotInventory)this.myItemsOffering.get(i);
            slot.x = x + i * 42;
            slot.y = 60;
            slot.updateSlot(0, 0);
         }
      } else {
         this.otherItemsReturn.clear();
         this.otherItemsReturn.addAll(new ArrayList(cont.getSelectedSlots()));

         for(i = 0; i < this.otherItemsReturn.size(); ++i) {
            slot = (GuiCCSlotInventory)this.otherItemsReturn.get(i);
            slot.x = x + i * 42;
            slot.y = 135;
            slot.updateSlot(0, 0);
         }
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      if (par1GuiButton.id == 20) {
         super.mc.displayGuiScreen(this.parentGui);
      } else {
         if (par1GuiButton.id == 15) {
            this.initCont((PlayerDataCloud)null);
         }

         if (par1GuiButton.id == 16) {
            PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(this.userTradingWith);
            this.initCont(data);
         }

         if (par1GuiButton.id == 17) {
            if (this.myItemsOffering.size() <= 0) {
               ClientTickHandler.addClientNotification(new ClientNotification("Must offer at least one item. Can't request user's items."));
               return;
            }

            ClientCloudManager.sendPacket(new PacketTradeCreate(this));
            ClientTickHandler.tradeDelay = 200;
            super.mc.displayGuiScreen((GuiScreen)null);
         }

         if (par1GuiButton.id == 18) {
            super.mc.displayGuiScreen((GuiScreen)null);
         }

         if (par1GuiButton.id == 62) {
            this.refreshOffers();
         }

      }
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
   }

   public void updateScreen() {
      super.updateScreen();
      if (this.lastTradeDelay != ClientTickHandler.tradeDelay) {
         if (this.lastTradeDelay > ClientTickHandler.tradeDelay && ClientTickHandler.tradeDelay == 0) {
            this.initGui();
         }

         this.lastTradeDelay = ClientTickHandler.tradeDelay;
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      int x = super.width / 2 - 173;
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      if (ClientTickHandler.tradeDelay > 0) {
         CCRenderHelper.renderCenteredText(EnumChatFormatting.RED + "Пожалуйста, подождите: " + ClientTickHandler.tradeDelay / 20 + "s", super.width / 2, 40);
         CCRenderHelper.renderCenteredText(EnumChatFormatting.RED + "Это для вашего же блага!", super.width / 2, 50);
      } else {
         CCRenderHelper.drawRectWithShadow((double)x, 215.0D, 150.0D, 18.0D, GuiCCMenu.menuTheme, 1.0F);
         CCRenderHelper.renderCenteredText("(Принятые сделки являются окончательными)", x + 75, 220);
         CCRenderHelper.drawRectWithShadow((double)(x + 154), 25.0D, 197.0D, 208.0D, GuiCCMenu.menuTheme, 1.0F);
         CCRenderHelper.renderCenteredText("Торговая информация", x + 154 + 98, 30);
         CCRenderHelper.renderText(EnumChatFormatting.AQUA + "Мои предложения", x + 154 + 4, 50);
         CCRenderHelper.drawRect((double)(x + 154 + 2), 59.0D, 128.0D, 42.0D, "0x222222", 1.0F);
         int count1 = 0;

         for(Iterator i$ = this.myItemsOffering.iterator(); i$.hasNext(); ++count1) {
            GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
            slot.doRender(par1, par2, par3);
            CCRenderHelper.renderTextScaled(slot.stack.getDisplayName(), x + 154 + 4, 103 + count1 * 6, 0.7D);
         }

         CCRenderHelper.renderText(EnumChatFormatting.GOLD + this.userTradingWith + "'Пункты в возврате", x + 154 + 4, 125);
         CCRenderHelper.drawRect((double)(x + 154 + 2), 134.0D, 128.0D, 42.0D, "0x222222", 1.0F);
         int count2 = 0;

         for(Iterator i$ = this.otherItemsReturn.iterator(); i$.hasNext(); ++count2) {
            GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
            slot.doRender(par1, par2, par3);
            CCRenderHelper.renderTextScaled(slot.stack.getDisplayName(), x + 154 + 4, 178 + count2 * 6, 0.7D);
         }

         PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
         if (data.hasPunishmentType(PunishmentType.BAN_TRADING)) {
            CCRenderHelper.renderText(EnumChatFormatting.YELLOW + "Торговля отключена", x + 157, 199);
         }

         this.drawButtons(par1, par2, par3);
      }
   }
}
