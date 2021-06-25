package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMSelectGunItem extends GuiFGDropDownMenu {
   public GuiCCMenuSkinViewer parentSkinViewer;

   public GuiCCDDMSelectGunItem(GuiScreen par1, int par2, int par3, int par4, int par5, GuiCCMenuSkinViewer givenParentSkinViewer) {
      super(par1, par2, par3, par4, par5);
      this.addOption("Rifle");
      this.addOption("SMG");
      this.addOption("Heavy");
      this.addOption("Snipers");
      this.addOption("Pistol");
      this.parentSkinViewer = givenParentSkinViewer;
   }

   public void onOptionClicked(int par1) {
      switch(par1) {
      case 1:
         GuiCCDDMSelectGunItemRifle guiRifle = new GuiCCDDMSelectGunItemRifle(super.parentGui, super.buttonX + 60, super.buttonY, 60, 13, this.parentSkinViewer);
         super.mc.displayGuiScreen(guiRifle);
         return;
      case 2:
         GuiCCDDMSelectGunItemSMG guiSMG = new GuiCCDDMSelectGunItemSMG(super.parentGui, super.buttonX + 61, super.buttonY, 60, 13, this.parentSkinViewer);
         super.mc.displayGuiScreen(guiSMG);
         return;
      case 3:
         GuiCCDDMSelectGunItemHeavy guiHeavy = new GuiCCDDMSelectGunItemHeavy(super.parentGui, super.buttonX + 61, super.buttonY, 60, 13, this.parentSkinViewer);
         super.mc.displayGuiScreen(guiHeavy);
         return;
      case 4:
         GuiCCDDMSelectGunItemSniper guiSniper = new GuiCCDDMSelectGunItemSniper(super.parentGui, super.buttonX + 61, super.buttonY, 60, 13, this.parentSkinViewer);
         super.mc.displayGuiScreen(guiSniper);
         return;
      case 5:
         GuiCCDDMSelectGunItemPistol guiPistol = new GuiCCDDMSelectGunItemPistol(super.parentGui, super.buttonX + 61, super.buttonY, 60, 13, this.parentSkinViewer);
         super.mc.displayGuiScreen(guiPistol);
         return;
      default:
      }
   }
}
