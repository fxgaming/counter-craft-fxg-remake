package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemGun;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryItemClearName;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventorySetItemDefault;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.events.EventInvSetDefault;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketCreateListing;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;

public class GuiCCDDMItemClicked extends GuiFGDropDownMenu implements IGuiFGPromptYesNo {
   private CloudItemStack stack;

   public GuiCCDDMItemClicked(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      this.stack = stack;
      String def = cloud.isDefaultItem(stack) ? "Clear Default" : "Set Default";
      this.addOption("Inspect");
      this.addOption(def);
      this.addOption("Rename", stack.getCloudItem().isNameable() && !stack.getCloudItem().isDefault() && cloud.getInventory().hasItem(CloudItem.NAME_TAG));
      this.addOption("Clear Name", CloudItemStack.hasNameTag(stack));
      this.addOption("Sell on Market", stack.getCloudItem().isSellable() && !stack.getCloudItem().isDefault());
      if (stack.getCloudItem() instanceof CloudItemGun) {
         this.addOption("Manage Stickers");
      }

      this.addOption("Delete", !stack.getCloudItem().isDefault());
   }

   public void onOptionClicked(int par1) {
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1 == 2) {
         PacketInventorySetItemDefault packet = new PacketInventorySetItemDefault(this.stack.getUUID());
         EventInvSetDefault event = new EventInvSetDefault(this.stack);
         if (!MinecraftForge.EVENT_BUS.post(event)) {
            ClientCloudManager.sendPacket(packet);
            PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, cloud.getUsername());
         }
      }

      if (par1 == 1) {
         GuiCCMenuInspect gui = new GuiCCMenuInspect(super.parentGui, this.stack);
         Minecraft.getMinecraft().displayGuiScreen(gui);
      }

      if (par1 == 3) {
         super.mc.displayGuiScreen(new GuiCCMenuRenameItem(cloud.getInventory().getFirstStack(CloudItem.NAME_TAG.getID()), this.stack));
      }

      GuiFGYesNoPrompt gui;
      if (par1 == 4) {
         gui = new GuiFGYesNoPrompt(2, this);
         gui.addInformation("Are you sure you want to remove the", "Name Tag?");
         super.mc.displayGuiScreen(gui);
      }

      if (par1 == 5) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketCreateListing(this.stack));
      }

      if (this.stack.getCloudItem() instanceof CloudItemGun) {
         if (par1 == 6) {
            super.mc.displayGuiScreen(new GuiCCMenuManageSticker(this, this.stack));
         }

         if (par1 == 7) {
            gui = new GuiFGYesNoPrompt(1, this);
            gui.addInformation("Are you sure you want to delete this", "item? It will be lost forever...");
            super.mc.displayGuiScreen(gui);
         }
      } else if (par1 == 6) {
         gui = new GuiFGYesNoPrompt(1, this);
         gui.addInformation("Are you sure you want to delete this", "item? It will be lost forever...");
         super.mc.displayGuiScreen(gui);
      }

   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2) {
         PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
         cloud.getInventory().removeItemStack(this.stack);
      }

      if (par1 == 2 && par2) {
         ClientCloudManager.sendPacket(new PacketInventoryItemClearName(this.stack.getUUID()));
      }

   }
}
