package com.ferullogaming.countercraft.client.gui.market;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerListing;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketMyListingAction;
import com.ferullogaming.countercraft.client.cloud.packet.market.PacketMarketMyListingSync;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMMarketMyListingOptions extends GuiFGDropDownMenu {
   private ArrayList tempList;

   public GuiCCDDMMarketMyListingOptions(GuiScreen par1, int par2, int par3, int par4, int par5, ArrayList par6) {
      super(par1, par2, par3, par4, par5);
      this.tempList = par6;
      this.addOption("Remove All", GuiCCMenuMarketMyListings.resyncListingsDisplay <= 0);
      this.addOption("Hard-Sync", GuiCCMenuMarketMyListings.resyncListings <= 0);
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         for(int i = 0; i < this.tempList.size(); ++i) {
            PlayerListing listing = (PlayerListing)this.tempList.get(i);
            ClientCloudManager.sendPacket(new PacketMarketMyListingAction(listing.getUUID(), 1));
         }
      }

      if (par1 == 2) {
         GuiCCMenuMarketMyListings.resyncListings = 20;
         GuiCCMenuMarketMyListings.resyncListingsDisplay = 100;
         ClientCloudManager.sendPacket(new PacketMarketMyListingSync());
      }

   }
}
