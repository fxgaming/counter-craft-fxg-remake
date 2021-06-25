package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectGunItemRifle extends GuiFGDropDownMenu {
   public GuiCCMenuSkinViewer parentSkinViewer;

   public GuiCCDDMSelectGunItemRifle(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCMenuSkinViewer givenParentSkinViewer) {
      super(par1, par2, par3, par4, par5);
      this.addOption("M4-A4");
      this.addOption("AK-47");
      this.addOption("Famas");
      this.addOption("FN-FAL");
      this.addOption("Galil-AR");
      this.parentSkinViewer = givenParentSkinViewer;
   }

   public void onOptionClicked(int par1) {
      switch(par1) {
      case 1:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.m4a1;
         break;
      case 2:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.ak47;
         break;
      case 3:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.famas;
         break;
      case 4:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.fnfal;
         break;
      case 5:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.galil;
      }

   }
}
