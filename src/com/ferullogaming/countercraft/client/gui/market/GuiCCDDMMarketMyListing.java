package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketMyListingAction;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuInspect;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMMarketMyListing extends GuiFGDropDownMenu {
   private GuiCCSlotMyListing slot;

   public GuiCCDDMMarketMyListing(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCSlotMyListing slot) {
      super(par1, par2, par3, par4, par5);
      this.slot = slot;
      this.addOption("Inspect");
      this.addOption("Remove Listing");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiCCMenuInspect(super.parentGui, this.slot.listing.getStack()));
      }

      if (par1 == 2) {
         ClientCloudManager.sendPacket(new PacketMarketMyListingAction(this.slot.listing.getUUID(), 1));
      }

   }
}
