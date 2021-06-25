package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.game.references.GameType;
import com.ferullogaming.countercraft.game.references.MapType;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMPartyMaps extends GuiFGDropDownMenu {
   private GameType type = null;

   public GuiCCDDMPartyMaps(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      GameType[] arr$ = GameType.gameTypeList;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         GameType type1 = arr$[i$];
         if (type1 != null && type1.getNameID().equals(par6)) {
            this.type = type1;
         }
      }

      if (this.type != null && this.type.getMaps() != null) {
         for(int i = 0; i < this.type.getMapsList().size(); ++i) {
            if (((MapType)this.type.getMapsList().get(i)).isMatchMaking) {
               this.addOption(((MapType)this.type.getMapsList().get(i)).displayName);
            }
         }
      }

   }

   public void onOptionClicked(int par1) {
      if (this.type != null) {
         for(int i = 0; i < this.type.getMapsList().size(); ++i) {
            if (i + 1 == par1 && ((MapType)this.type.getMapsList().get(i + 1)).isMatchMaking) {
               ((GuiCCMenuCreateParty)super.parentGui).updatePartySettings((String)null, ((MapType)this.type.getMapsList().get(i + 1)).displayName);
            }
         }
      }

   }
}
