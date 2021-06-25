package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketCreateListing;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMItemClickedSticker extends GuiFGDropDownMenu {
   private CloudItemStack stack;

   public GuiCCDDMItemClickedSticker(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      this.stack = stack;
      this.addOption("Inspect");
      this.addOption("Sell on Market");
   }

   public void onOptionClicked(int par1) {
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1 == 1) {
         GuiCCMenuInspect gui = new GuiCCMenuInspect(super.parentGui, this.stack);
         Minecraft.getMinecraft().displayGuiScreen(gui);
      }

      if (par1 == 2) {
         super.mc.displayGuiScreen(new GuiCCMenuMarketCreateListing(this.stack));
      }

   }
}
