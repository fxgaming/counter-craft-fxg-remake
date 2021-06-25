package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.game.references.GameType;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMPartyGamemode extends GuiFGDropDownMenu {
   public GuiCCDDMPartyGamemode(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      GameType[] arr$ = GameType.gameTypeList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         GameType type = arr$[i$];
         if (type != null) {
            this.addOption("" + type.getDisplayName());
         }
      }

   }

   public void onOptionClicked(int par1) {
      int count = 0;
      GameType[] arr$ = GameType.gameTypeList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         GameType type = arr$[i$];
         if (type != null) {
            ++count;
            if (count == par1) {
               ((GuiCCMenuCreateParty)super.parentGui).updatePartySettings(type.getNameID(), (String)null);
            }
         }
      }

   }
}
