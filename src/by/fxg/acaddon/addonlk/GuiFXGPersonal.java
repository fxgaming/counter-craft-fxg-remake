package by.fxg.acaddon.addonlk;

import com.f3rullo14.fds.MathHelper;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerList;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerListSlotText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GuiFXGPersonal extends GuiFGContainerList {
   public GuiFXGPersonal(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      
   }

   public void setupStats() {
   }
}
