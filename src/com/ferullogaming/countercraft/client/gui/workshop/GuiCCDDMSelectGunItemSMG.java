package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectGunItemSMG extends GuiFGDropDownMenu {
   public GuiCCMenuSkinViewer parentSkinViewer;

   public GuiCCDDMSelectGunItemSMG(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCMenuSkinViewer givenParentSkinViewer) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Vector");
      this.addOption("MAC-10");
      this.addOption("P90");
      this.addOption("UMP-45");
      this.parentSkinViewer = givenParentSkinViewer;
   }

   public void onOptionClicked(int par1) {
      switch(par1) {
      case 1:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.vector;
         break;
      case 2:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.mac10;
         break;
      case 3:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.p90;
         break;
      case 4:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.ump45;
      }

   }
}
