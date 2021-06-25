package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerStats;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;

public class GuiCCMenuInventory extends GuiCCMenu {
   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, super.mc.getSession().getUsername());
      GuiCCContainerInventory cont = new GuiCCContainerInventory(2, x + 114, 25, 237, 208, this);
      cont.addButtons((ArrayList)super.buttonList);
      this.addContainer(cont);
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("My Profile").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      this.drawButtons(par1, par2, par3);
   }
}
