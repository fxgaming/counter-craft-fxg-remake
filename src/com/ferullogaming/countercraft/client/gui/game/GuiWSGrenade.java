package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.network.CCPacketBuyMenuItem;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiScreen;

public class GuiWSGrenade extends GuiWeaponSelection {
   private String team = "";

   public GuiWSGrenade(GuiScreen par1, String par2) {
      super(par1);
      this.team = par2;
      this.addOption("Flash Grenade", new String[]{""});
      this.addOption("Decoy Grenade", new String[]{""});
      this.addOption("Smoke Grenade", new String[]{""});
      this.addOption("Fire Grenade", new String[]{""});
      this.addOption("Frag Grenade", new String[]{""});
   }

   public void onOptionClicked(String par1, int par2) {
      ItemCC item = ItemManager.getItemFromName(par1);
      if (item != null && GameManager.instance().currentClientGame != null) {
         PacketDispatcher.sendPacketToServer(CCPacketBuyMenuItem.buildPacket(item.itemID));
      }

   }
}
