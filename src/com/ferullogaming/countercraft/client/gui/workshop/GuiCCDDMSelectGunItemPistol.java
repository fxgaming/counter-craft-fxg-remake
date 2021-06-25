package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectGunItemPistol extends GuiFGDropDownMenu {
   public GuiCCMenuSkinViewer parentSkinViewer;

   public GuiCCDDMSelectGunItemPistol(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCMenuSkinViewer givenParentSkinViewer) {
      super(par1, par2, par3, par4, par5);
      this.addOption("M1911");
      this.addOption("G18");
      this.addOption("Deagle");
      this.addOption("P250");
      this.addOption("TEC-9");
      this.addOption("CZ75");
      this.addOption("Five-Seven");
      this.addOption("R8 Revolver");
      this.parentSkinViewer = givenParentSkinViewer;
   }

   public void onOptionClicked(int par1) {
      switch(par1) {
      case 1:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.m1911;
         break;
      case 2:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.g18;
         break;
      case 3:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.deagle;
         break;
      case 4:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.p250;
         break;
      case 5:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.tec9;
         break;
      case 6:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.cz75;
         break;
      case 7:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.fiveSeven;
         break;
      case 8:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.r8;
      }

   }
}
