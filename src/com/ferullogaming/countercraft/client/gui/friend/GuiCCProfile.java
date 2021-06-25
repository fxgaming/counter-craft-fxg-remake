package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.CloudInventoryFilter;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryItemShowcase;
import com.ferullogaming.countercraft.client.events.EventInvSetShowcase;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCPromptInventorySelection;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCSlotInventory;
import com.ferullogaming.countercraft.client.gui.inventory.IGuiCCPromptInvSelResults;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

public class GuiCCProfile extends GuiCCProfileBase implements IGuiCCPromptInvSelResults {
   private ArrayList list = new ArrayList();
   private int refreshDelay2 = 0;

   public GuiCCProfile(GuiScreen par1, String par2) {
      super(par1, par2);
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
      if (super.username.equals(super.mc.getSession().getUsername())) {
         GuiFGButton button = new GuiFGButton(11, x + 25, 132, 40, 10, EnumChatFormatting.ITALIC + "Edit");
         button.drawBackground = false;
         super.buttonList.add(button);
      } else if (!super.data.enableProfileViewing) {
         ClientNotification.createNotification("This users profile is private!");
         super.mc.displayGuiScreen(new GuiCCMenuHome());
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      if (par1GuiButton.id == 11) {
         ArrayList list = new ArrayList(super.data.getInventory().getListed());
         list = CloudInventoryFilter.filterValue(list);
         list = CloudInventoryFilter.filterDefaultsOut(list);
         list = CloudInventoryFilter.filterOutShoawcaseItems(list);
         super.mc.displayGuiScreen(new GuiCCPromptInventorySelection(this, list, 5));
      }

   }

   public void updateScreen() {
      super.updateScreen();
      if (this.refreshDelay2++ > 3) {
         int x = super.width / 2 - 173;
         this.list.clear();

         for(int i = 0; i < super.data.itemsShowcasing.size(); ++i) {
            CloudItemStack stack = (CloudItemStack)super.data.itemsShowcasing.get(i);
            GuiCCSlotInventory slot = new GuiCCSlotInventory(x + 54 + 50 * i, 155, stack, 45, 45, false);
            this.list.add(slot);
         }

         this.refreshDelay2 = 0;
      }

   }

   public void drawTab(int par1, int par2, float par3) {
      int x = super.width / 2 - 173;
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + "Items 'Own/Owned' Showcase", x + 35, 122);
      Iterator i$ = this.list.iterator();

      while(i$.hasNext()) {
         GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
         slot.doRender(par1, par2, par3);
      }

   }

   public void onSelected(ArrayList stacks) {
      EventInvSetShowcase event = new EventInvSetShowcase(stacks);
      if (!MinecraftForge.EVENT_BUS.post(event)) {
         int i;
         for(i = 0; i < super.data.itemsShowcasing.size(); ++i) {
            ClientCloudManager.sendPacket(new PacketInventoryItemShowcase(((CloudItemStack)super.data.itemsShowcasing.get(i)).getUUID()));
         }

         for(i = 0; i < stacks.size(); ++i) {
            ClientCloudManager.sendPacket(new PacketInventoryItemShowcase(((CloudItemStack)stacks.get(i)).getUUID()));
         }
      }

   }
}
