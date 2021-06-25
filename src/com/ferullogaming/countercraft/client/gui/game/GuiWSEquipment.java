package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.item.ItemCC;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.network.CCPacketBuyMenuItem;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.gui.GuiScreen;

public class GuiWSEquipment extends GuiWeaponSelection {
   private String team = "";

   public GuiWSEquipment(GuiScreen par1, String par2) {
      super(par1);
      this.team = par2;
      this.addOption("Helmet", new String[]{"Уменьшает урон от head-shot на 50%"});
      this.addOption("Kevlar", new String[]{"Уменьшает урон возле головы на 50%"});
   }

   public void onOptionClicked(String par1, int par2) {
      ItemCC item = ItemManager.getItemFromName(par1);
      if (item != null && GameManager.instance().currentClientGame != null) {
         PacketDispatcher.sendPacketToServer(CCPacketBuyMenuItem.buildPacket(item.itemID));
      }

   }
}
