package com.ferullogaming.countercraft.client.gui;

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

public class GuiCCCTurnouts extends GuiFGContainerList {
   public GuiCCCTurnouts(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
      this.setupStats();
   }

   public static double round(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         long factor = (long)Math.pow(10.0D, (double)places);
         value *= (double)factor;
         long tmp = Math.round(value);
         return (double)tmp / (double)factor;
      }
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      CCRenderHelper.renderShowcaseRus("Турнир!", "26 Февраля пройдет турнир на LGCS Mirage.", super.posX, super.posY + 12, 110, 38, CCRenderHelper.EnumShowcaseColor.RED, new ItemStack(4812, 1, 0), true, 20, 0, 0, 1f);
      CCRenderHelper.renderShowcaseRus("Вы нам нужны!", "1 Марта будет проведен набор в администрацию LG.", super.posX, super.posY + 94, 110, 36, CCRenderHelper.EnumShowcaseColor.ALPHA2, new ItemStack(4721, 1, 0), false, 20, 0, 0, 1f);
      CCRenderHelper.renderShowcaseRus("Оценивайте!", "Оценивайте работу сервера, и при багах сообщайте администрации, помогите улучшить сервер!", super.posX, super.posY + 50, 110, 44, CCRenderHelper.EnumShowcaseColor.GREEN, new ItemStack(4722, 1, 0), false, 20, 0, 0, 1f);
   }

   public void setupStats() {
      ClientCloudManager.sendPacket(new PacketClientRequest(RequestType.CLOUDSTATS, new String[0]));
      PlayerDataCloud clouddata = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      ArrayList text = new ArrayList();
      text.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "Версия " + CounterCraft.MOD_VERSION + (clouddata.isSupporter ? EnumChatFormatting.YELLOW + " " + Character.toString('★') + " Supporter" : ""));
      text.add("");

      if (!ClientManager.instance().getCloudManager().getConnectionHandler().isConnected()) {
         text.clear();
         text.add(EnumChatFormatting.BLUE + "" + EnumChatFormatting.ITALIC + "Версия: " + EnumChatFormatting.WHITE + "" + EnumChatFormatting.ITALIC + CounterCraft.MOD_VERSION);
      }

      this.updateDisplayedSlots(GuiFGContainerListSlotText.getListFromStrings(text));
   }
}
