package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryBooster;
import com.ferullogaming.countercraft.client.events.EventInvBoosterActivated;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;

public class GuiCCDDMItemClickedBooster extends GuiFGDropDownMenu {
   private CloudItemStack stack;

   public GuiCCDDMItemClickedBooster(GuiScreen par1, int par2, int par3, int par4, int par5, CloudItemStack stack) {
      super(par1, par2, par3, par4, par5);
      this.stack = stack;
      this.addOption("Inspect");
      this.addOption("Activate");
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuInspect(super.parentGui, this.stack));
      }

      if (par1 == 2) {
         EventInvBoosterActivated event = new EventInvBoosterActivated(this.stack);
         if (!MinecraftForge.EVENT_BUS.post(event)) {
            ClientCloudManager.sendPacket(new PacketInventoryBooster(this.stack.getUUID()));
         }
      }

   }
}
