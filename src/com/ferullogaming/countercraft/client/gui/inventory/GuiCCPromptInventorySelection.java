package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollBar;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCPromptInventorySelection extends GuiFGScreen {
   public int promptWidth;
   public int promptHeight;
   private GuiScreen parentGUI;
   private ArrayList tempList = new ArrayList();
   private ArrayList slots = new ArrayList();
   private int columns;
   private int rows;
   private ArrayList selectedSlotStackUUIDs = new ArrayList();
   private int topSlot = 0;
   private int itemsRequired = 0;
   private int promptX;
   private int promptY;
   private GuiFGScrollBar scrollBar;
   private boolean wasOpened = false;

   public GuiCCPromptInventorySelection(GuiScreen par1, ArrayList par2, int par3) {
      this.parentGUI = par1;
      this.tempList.clear();
      this.tempList.addAll(par2);
      this.itemsRequired = par3;
   }

   public void initGui() {
      Minecraft mc = Minecraft.getMinecraft();
      if (this.wasOpened) {
         mc.displayGuiScreen(this.parentGUI);
      }

      this.promptWidth = 180;
      this.promptHeight = 200;
      this.promptX = super.width / 2 - this.promptWidth / 2;
      this.promptY = 30;
      int var1 = this.tempList.size() / 3;
      if (this.tempList.size() % 3 != 0) {
         ++var1;
      }

      this.scrollBar = new GuiFGScrollBar(this.promptX + this.promptWidth - 21, this.promptY + 19, 18, 142, 13);
      this.scrollBar.setIncrements(var1 - 3);
      super.buttonList.add(new GuiFGButton(10, this.promptX + 2, this.promptY + this.promptHeight - 17, 70, 15, "Confirm"));
      super.buttonList.add(new GuiFGButton(11, this.promptX + this.promptWidth - 70 - 2, this.promptY + this.promptHeight - 17, 70, 15, "Cancel"));
      this.initSlots();
      this.wasOpened = true;
   }

   public void initSlots() {
      this.slots.clear();
      int stackx = this.promptX + 29;
      int stacky = this.promptY + 30;
      int stackw = 40;
      int stackh = 40;
      int slotmarginx = 41;
      int slotmarginy = 41;
      int count = 0;
      int var1 = this.tempList.size() / 3;
      if (this.tempList.size() % 3 != 0) {
         ++var1;
      }

      for(int i = 0; i < var1; ++i) {
         for(int j = 0; j < 3; ++j) {
            if (count < this.tempList.size()) {
               CloudItemStack stack = (CloudItemStack)this.tempList.get(count);
               GuiCCSlotInventory slot = new GuiCCSlotInventory(stackx + j * slotmarginx, stacky + i * slotmarginy - this.topSlot * slotmarginy, stack, stackw, stackh, false);
               this.slots.add(slot);
            }

            ++count;
         }
      }

   }

   public void actionPerformed(GuiButton guibutton) {
      if (guibutton.id == 10 && this.selectedSlotStackUUIDs.size() > 0) {
         ArrayList stacks = new ArrayList();

         for(int i = 0; i < this.selectedSlotStackUUIDs.size(); ++i) {
            for(int i1 = 0; i1 < this.tempList.size(); ++i1) {
               if (((CloudItemStack)this.tempList.get(i1)).getUUID().equals(this.selectedSlotStackUUIDs.get(i))) {
                  stacks.add(((CloudItemStack)this.tempList.get(i1)).copy());
               }
            }
         }

         if (this.parentGUI instanceof IGuiCCPromptInvSelResults) {
            ((IGuiCCPromptInvSelResults)this.parentGUI).onSelected(stacks);
         }

         super.mc.displayGuiScreen(this.parentGUI);
      }

      if (guibutton.id == 11) {
         super.mc.displayGuiScreen(this.parentGUI);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.scrollBar.update();
      if (this.scrollBar.isClicked() || this.scrollBar.isMouseScrolling()) {
         this.topSlot = this.scrollBar.getTopSlot();
         this.initSlots();
      }

   }

   protected void keyTyped(char par1, int par2) {
      super.keyTyped(par1, par2);
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      super.mouseClicked(par1, par2, par3);
      Rectangle rect = new Rectangle(this.promptX, this.promptY, this.promptWidth, this.promptHeight);
      Iterator i$ = this.slots.iterator();

      while(true) {
         while(true) {
            GuiCCSlotInventory slot;
            do {
               do {
                  if (!i$.hasNext()) {
                     if (rect.contains(par1, par2)) {
                        return;
                     }

                     super.mc.displayGuiScreen(this.parentGUI);
                     return;
                  }

                  slot = (GuiCCSlotInventory)i$.next();
               } while(!slot.isMouseOver(par1, par2));
            } while(!slot.isRendering);

            String stackuuid = slot.stack.getUUID();
            if (!this.selectedSlotStackUUIDs.contains(stackuuid) && !slot.stack.getCloudItem().isDefault()) {
               if (this.itemsRequired == 1) {
                  this.selectedSlotStackUUIDs.clear();
                  this.selectedSlotStackUUIDs.add(stackuuid);
                  return;
               }

               if (this.selectedSlotStackUUIDs.size() < this.itemsRequired) {
                  this.selectedSlotStackUUIDs.add(stackuuid);
               }
            } else {
               this.selectedSlotStackUUIDs.remove(stackuuid);
            }
         }
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      CCRenderHelper.drawRectWithShadow((double)this.promptX, (double)this.promptY, (double)this.promptWidth, (double)this.promptHeight, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.AQUA + "Item Selection", this.promptX + this.promptWidth / 2, this.promptY + 6);
      CCRenderHelper.renderCenteredText(this.selectedSlotStackUUIDs.size() + "/" + this.itemsRequired, this.promptX + this.promptWidth / 2, this.promptY + 165);
      this.scrollBar.doRender(par1, par2, par3);
      int m1 = 25;
      CCRenderHelper.drawRectWithShadow((double)(this.promptX + m1), (double)(this.promptY + 20), (double)(this.promptWidth - m1 * 2), (double)(this.promptHeight - 60), GuiCCMenu.menuTheme, 1.0F);
      Iterator i$ = this.slots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
         slot.isRendering = false;
         if (slot.y < this.promptHeight - 40 && slot.y > this.promptY + 20) {
            slot.doRender(par1, par2, par3);
            if (this.selectedSlotStackUUIDs.contains(slot.stack.getUUID())) {
               slot.doRenderHighlight(1);
            }
         }
      }

      for(int k = 0; k < super.buttonList.size(); ++k) {
         GuiButton guibutton = (GuiButton)super.buttonList.get(k);
         guibutton.drawButton(super.mc, par1, par2);
      }

   }
}
