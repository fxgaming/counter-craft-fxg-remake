package com.ferullogaming.countercraft.client.gui.friend;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.Punishment;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCProfilePunishments extends GuiCCProfileBase {
   public GuiCCProfilePunishments(GuiScreen par1, String par2) {
      super(par1, par2);
   }

   public void initGui() {
      super.initGui();
      int x = super.width / 2 - 173;
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
   }

   public void updateScreen() {
      super.updateScreen();
   }

   public void drawTab(int par1, int par2, float par3) {
      int x = super.width / 2 - 173;
      CCRenderHelper.renderText(EnumChatFormatting.GRAY + "" + "Punishment Records (" + super.data.getActivePunishments().size() + ")", x + 35, 122);

      for(int i = 0; i < super.data.getActivePunishments().size(); ++i) {
         Punishment punish = (Punishment)super.data.getActivePunishments().get(i);
         CCRenderHelper.renderText(EnumChatFormatting.YELLOW + "" + punish.type.displayName + " '" + punish.getTimeRemainingString() + "' " + punish.reason, x + 35, 135 + i * 10);
      }

   }
}
