package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeAction;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMTradeClose extends GuiFGDropDownMenu {
   private String tradeUUID;

   public GuiCCDDMTradeClose(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.tradeUUID = par6;
      this.addOption("Закрыть торговлю");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         ClientCloudManager.sendPacket(new PacketTradeAction(this.tradeUUID, 3));
      }

      super.mc.displayGuiScreen(super.parentGui);
   }
}
