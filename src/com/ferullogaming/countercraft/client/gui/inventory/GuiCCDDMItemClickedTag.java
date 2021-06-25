package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketCreateListing;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMItemClickedTag extends GuiFGDropDownMenu {
   private CloudItemStack stack;

   public GuiCCDDMItemClickedTag(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      this.stack = stack;
      this.addOption("Rename Item");
      this.addOption("Sell on Market", stack.getCloudItem().isSellable() && !stack.getCloudItem().isDefault());
      this.addOption("Delete", !stack.getCloudItem().isDefault());
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCMenuRenameItem(this.stack));
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketCreateListing(this.stack));
      }

      if (par1 == 3) {
         GuiFGYesNoPrompt gui = new GuiFGYesNoPrompt(1, this);
         gui.addInformation("Are you sure you want to delete this", "item? It will be lost forever...");
         super.mc.displayGuiScreen(gui);
      }

   }
}
