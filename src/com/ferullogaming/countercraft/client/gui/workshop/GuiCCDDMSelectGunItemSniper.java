package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectGunItemSniper extends GuiFGDropDownMenu {
   public GuiCCMenuSkinViewer parentSkinViewer;

   public GuiCCDDMSelectGunItemSniper(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCMenuSkinViewer givenParentSkinViewer) {
      super(par1, par2, par3, par4, par5);
      this.addOption("AWP");
      this.addOption("SSG-08");
      this.addOption("Scar-20");
      this.addOption("Dragunov");
      this.parentSkinViewer = givenParentSkinViewer;
   }

   public void onOptionClicked(int par1) {
      switch(par1) {
      case 1:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.awp;
         break;
      case 2:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.ssg08;
         break;
      case 3:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.scar20;
         break;
      case 4:
         GuiCCMenuSkinViewer.selectedGun = (ItemGun)ItemManager.dragunov;
      }

   }
}
