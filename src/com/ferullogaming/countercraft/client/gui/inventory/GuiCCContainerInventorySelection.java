package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.CloudInventoryFilter;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiCCContainerInventorySelection extends GuiFGContainer {
   public static int pageNumber = 0;
   public static int pages = 1;
   private static ArrayList slots = new ArrayList();
   private int itemsPerPage = 20;
   private PlayerDataCloud playerData;
   private int columns;
   private int rows;
   private ArrayList selectedSlotStackUUIDs = new ArrayList();

   public GuiCCContainerInventorySelection(int par1, int par2, int par3, int par4Columns, int par5Rows, GuiFGScreen par6) {
      super(par1, par2, par3, 0, 0, par6);
      this.columns = par4Columns;
      this.rows = par5Rows;
      super.width = par4Columns * 50;
      super.height = par5Rows * 50 + 15;
      this.itemsPerPage = par4Columns * par5Rows;
      pageNumber = 0;
   }

   public GuiCCContainerInventorySelection setData(PlayerDataCloud par1) {
      this.playerData = par1;
      if (this.playerData == null) {
         this.playerData = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      }

      return this;
   }

   public void initGui() {
      this.initSlots();
   }

   public void addButtons(ArrayList par1) {
      GuiFGButton button = new GuiFGButton(60, super.posX - 1, super.posY + 75, 10, 10, "<");
      button.drawBackground = false;
      par1.add(button);
      button = new GuiFGButton(61, super.posX + super.width - 9, super.posY + 75, 10, 10, ">");
      button.drawBackground = false;
      par1.add(button);
      button = new GuiFGButton(62, super.posX + 14, super.posY + super.height - 20, 50, 15, "Confirm");
      button.drawBackground = true;
      par1.add(button);
   }

   public void initSlots() {
      ArrayList list = CloudInventoryFilter.filterValue(this.playerData.getInventory().getListed());
      slots.clear();
      pages = list.size() / this.itemsPerPage + 1;
      int stackx = super.posX + 14;
      int stacky = super.posY + 18;
      int stackw = 40;
      int stackh = 40;
      int slotmarginx = 41;
      int slotmarginy = 41;
      int count = pageNumber * this.itemsPerPage;

      for(int i = 0; i < this.columns; ++i) {
         for(int j = 0; j < this.rows; ++j) {
            if (count < list.size()) {
               CloudItemStack stack = (CloudItemStack)list.get(count);
               GuiCCSlotInventory slot = new GuiCCSlotInventory(stackx + j * slotmarginx, stacky + i * slotmarginy, stack, stackw, stackh, false);
               slots.add(slot);
            }

            ++count;
         }
      }

   }

   public ArrayList getSelectedSlots() {
      ArrayList list = new ArrayList();
      ArrayList list1 = CloudInventoryFilter.filterValue(this.playerData.getInventory().getListed());
      Iterator i$ = list1.iterator();

      while(i$.hasNext()) {
         CloudItemStack slot = (CloudItemStack)i$.next();
         if (this.selectedSlotStackUUIDs.contains(slot.getUUID())) {
            list.add(new GuiCCSlotInventory(0, 0, slot.copy(), 40, 40, false));
         }
      }

      return list;
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      if (par1GuiButton.id == 60) {
         if (pageNumber > 0) {
            --pageNumber;
         }

         this.initSlots();
      }

      if (par1GuiButton.id == 61) {
         if (pageNumber < pages - 1) {
            ++pageNumber;
         }

         this.initSlots();
      }

   }

   public void mouseClicked(int par1, int par2, int par3) {
      Iterator i$ = slots.iterator();

      while(true) {
         while(true) {
            GuiCCSlotInventory slot;
            do {
               if (!i$.hasNext()) {
                  return;
               }

               slot = (GuiCCSlotInventory)i$.next();
            } while(!slot.isMouseOver(par1, par2));

            String stackuuid = slot.stack.getUUID();
            if (!this.selectedSlotStackUUIDs.contains(stackuuid) && !slot.stack.getCloudItem().isDefault() && slot.stack.getCloudItem().isTradable()) {
               if (this.selectedSlotStackUUIDs.size() < 3) {
                  this.selectedSlotStackUUIDs.add(stackuuid);
               }
            } else {
               this.selectedSlotStackUUIDs.remove(stackuuid);
            }
         }
      }
   }

   public void updateScreen() {
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground();
      Iterator i$ = slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
         slot.doRender(par1, par2, par3);
         if (this.selectedSlotStackUUIDs.contains(slot.stack.getUUID())) {
            slot.doRenderHighlight(1);
         }
      }

      CCRenderHelper.renderCenteredText(pageNumber + 1 + "/" + pages, super.posX + super.width - 23, super.posY + super.height - 16);
      String username = this.playerData.getUsername().equals(Minecraft.getMinecraft().getSession().getUsername()) ? "My" : this.playerData.getUsername() + "'s";
      CCRenderHelper.renderCenteredText(username + " Inventory", super.posX + super.width / 2, super.posY + 5);
   }
}
