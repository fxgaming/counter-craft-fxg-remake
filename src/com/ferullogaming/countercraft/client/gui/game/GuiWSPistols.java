package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.network.CCPacketBuyMenuItem;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiScreen;

public class GuiWSPistols extends GuiWeaponSelection {
   private String team = "";

   public GuiWSPistols(GuiScreen par1, String par2) {
      super(par1);
      this.team = par2;
      if (this.team != null) {
         if (this.team.equalsIgnoreCase("red")) {
            this.addOption("G18", new String[]{""});
            this.addOption("Deagle", new String[]{""});
            this.addOption("P250", new String[]{""});
            this.addOption("TEC-9", new String[]{""});
            this.addOption("CZ75", new String[]{""});
            this.addOption("R8 Revolver", new String[]{""});
            return;
         }

         if (this.team.equalsIgnoreCase("blue")) {
            this.addOption("M1911", new String[]{""});
            this.addOption("Deagle", new String[]{""});
            this.addOption("P250", new String[]{""});
            this.addOption("CZ75", new String[]{""});
            this.addOption("Five-Seven", new String[]{""});
            this.addOption("R8 Revolver", new String[]{""});
            return;
         }
      }

      this.addOption("M1911", new String[]{""});
      this.addOption("G18", new String[]{""});
      this.addOption("Deagle", new String[]{""});
      this.addOption("P250", new String[]{""});
      this.addOption("TEC-9", new String[]{""});
      this.addOption("CZ75", new String[]{""});
      this.addOption("Five-Seven", new String[]{""});
      this.addOption("R8 Revolver", new String[]{""});
   }

   public void onOptionClicked(String par1, int par2) {
      ItemCC item = ItemManager.getItemFromName(par1);
      if (item != null && GameManager.instance().currentClientGame != null) {
         PacketDispatcher.sendPacketToServer(CCPacketBuyMenuItem.buildPacket(item.itemID));
      }

   }
}
