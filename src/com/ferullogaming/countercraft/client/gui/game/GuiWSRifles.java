package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.network.CCPacketBuyMenuItem;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiScreen;

public class GuiWSRifles extends GuiWeaponSelection {
   private String team = "";

   public GuiWSRifles(GuiScreen par1, String par2) {
      super(par1);
      this.team = par2;
      if (this.team != null) {
         if (this.team.equalsIgnoreCase("red")) {
            this.addOption("AK-47", new String[]{""});
            this.addOption("FN-FAL", new String[]{""});
            this.addOption("Galil-AR", new String[]{""});
            return;
         }

         if (this.team.equalsIgnoreCase("blue")) {
            this.addOption("M4-A4", new String[]{""});
            this.addOption("Famas", new String[]{""});
            return;
         }
      }

      this.addOption("M4-A4", new String[]{""});
      this.addOption("AK-47", new String[]{""});
      this.addOption("Famas", new String[]{""});
      this.addOption("FN-FAL", new String[]{""});
      this.addOption("Galil-AR", new String[]{""});
   }

   public void onOptionClicked(String par1, int par2) {
      ItemCC item = ItemManager.getItemFromName(par1);
      if (item != null && GameManager.instance().currentClientGame != null) {
         PacketDispatcher.sendPacketToServer(CCPacketBuyMenuItem.buildPacket(item.itemID));
      }

   }
}
