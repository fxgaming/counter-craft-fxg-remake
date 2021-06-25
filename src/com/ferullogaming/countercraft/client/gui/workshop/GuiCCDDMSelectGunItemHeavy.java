package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectGunItemHeavy extends GuiFGDropDownMenu {
   public GuiCCMenuSkinViewer parentSkinViewer;

   public GuiCCDDMSelectGunItemHeavy(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCMenuSkinViewer givenParentSkinViewer) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Nova");
      this.addOption("Sawed-Off");
      this.addOption("Mag-7");
      this.addOption("M249");
      this.parentSkinViewer = givenParentSkinViewer;
   }

   public void onOptionClicked(int par1) {
      switch(par1) {
      case 1:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.nova;
         break;
      case 2:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.sawedoff;
         break;
      case 3:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.mag7;
         break;
      case 4:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.m249;
      }

   }
}
