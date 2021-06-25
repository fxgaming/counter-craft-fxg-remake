package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PunishmentType;
import com.ferullogaming.countercraft.client.cloud.packet.PacketTradeAction;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.gui.GuiScreen;

public class GuiCCDDMTradeAccept extends GuiFGDropDownMenu implements IGuiFGPromptYesNo {
   private String tradeUUID;

   public GuiCCDDMTradeAccept(GuiScreen par1, int par2, int par3, int par4, int par5, String par6) {
      super(par1, par2, par3, par4, par5);
      this.tradeUUID = par6;
      this.addOption("Принять");
      this.addOption("Отклонить");
   }

   public void onOptionClicked(int par1) {
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(super.mc.getSession().getUsername());
      if (data.hasPunishmentType(PunishmentType.BAN_TRADING)) {
         ClientTickHandler.addClientNotification(new ClientNotification("Trading Disabled"));
         super.mc.displayGuiScreen(super.parentGui);
      } else if (par1 == 1) {
         super.mc.displayGuiScreen((new GuiFGYesNoPrompt(1, this)).addInformation("--- Trade Confirmation ---", "Do you want to continue and", "accept the trade?"));
      } else {
         if (par1 == 2) {
            ClientCloudManager.sendPacket(new PacketTradeAction(this.tradeUUID, 2));
         }

         super.mc.displayGuiScreen(super.parentGui);
      }
   }

   public void onResult(int par1, boolean par2) {
      System.out.println("ok");
      if (par1 == 1 && par2) {
         ClientCloudManager.sendPacket(new PacketTradeAction(this.tradeUUID, 1));
         System.out.println("ok");
      }

      super.mc.displayGuiScreen(super.parentGui);
   }
}
