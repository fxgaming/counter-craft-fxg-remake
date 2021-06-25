package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventorySetItemDefault;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.events.EventInvSetDefault;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;

public class GuiCCDDMItemClickedCoin extends GuiFGDropDownMenu {
   private CloudItemStack stack;

   public GuiCCDDMItemClickedCoin(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      this.stack = stack;
      this.addOption("Inspect");
      String def = cloud.isDefaultItem(stack) ? "Clear Default" : "Set Default";
      this.addOption(def);
   }

   public void onOptionClicked(int par1) {
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1 == 1) {
         GuiCCMenuInspect gui = new GuiCCMenuInspect(super.parentGui, this.stack);
         Minecraft.getMinecraft().displayGuiScreen(gui);
      }

      if (par1 == 2) {
         PacketInventorySetItemDefault packet = new PacketInventorySetItemDefault(this.stack.getUUID());
         EventInvSetDefault event = new EventInvSetDefault(this.stack);
         if (!MinecraftForge.EVENT_BUS.post(event)) {
            ClientCloudManager.sendPacket(packet);
            PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, cloud.getUsername());
         }
      }

   }
}
