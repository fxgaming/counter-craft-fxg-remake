package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMInventoryOptions extends GuiFGDropDownMenu implements IGuiFGPromptYesNo {
   private CloudItemStack stack;

   public GuiCCDDMInventoryOptions(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Item Info");
      this.addOption("Sample UUID");
      this.addOption("Organize", GuiCCContainerInventory.syncDelay <= 0);
      this.addOption("Hard-Sync", GuiCCContainerInventory.syncDelay <= 0);
   }

   public void onOptionClicked(int par1) {
      GuiFGYesNoPrompt gui;
      if (par1 == 1) {
         gui = new GuiFGYesNoPrompt(1, this);
         gui.addInformation("The Displayed Information on each", "Item.");
         gui.setEnableDisable();
         super.mc.displayGuiScreen(gui);
      }

      if (par1 == 2) {
         gui = new GuiFGYesNoPrompt(2, this);
         gui.addInformation("The first few characters of the", "item stack's Unique ID assigned at", "creation.");
         gui.setEnableDisable();
         super.mc.displayGuiScreen(gui);
      }

      if (par1 == 3 || par1 == 4) {
         GuiCCContainerInventory.syncDelay = 40;
         PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY, Minecraft.getMinecraft().getSession().getUsername());
      }

   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1) {
         GuiCCContainerInventory.option_HoverInformation = par2;
      }

      if (par1 == 2) {
         GuiCCContainerInventory.option_HoverInformationUUID = par2;
      }

   }
}
