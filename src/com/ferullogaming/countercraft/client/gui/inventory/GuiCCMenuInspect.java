package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuInspect extends GuiCCMenu {
   public double roationTick = 0.0D;
   public double lastRotationTick;
   public boolean soundPlayed = false;
   public static ResourceLocation background = new ResourceLocation("countercraft:textures/gui/inspectBackground.png");
   String inspectSound = "countercraft:gui.inspectItem";
   private GuiScreen parentGUI;
   private CloudItemStack cloudStack;

   public GuiCCMenuInspect(GuiScreen par1, CloudItemStack par2) {
      this.parentGUI = par1;
      this.cloudStack = par2;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      super.buttonList.add(new GuiFGButton(16, x + 5, 205, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      if (par1GuiButton.id == 16) {
         super.mc.displayGuiScreen(this.parentGUI);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.lastRotationTick = this.roationTick++;
      if (!this.soundPlayed) {
         Minecraft.getMinecraft().sndManager.playSoundFX(this.inspectSound, 1.0F, 1.0F);
         this.soundPlayed = true;
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 172;
      int color = Integer.parseInt(this.cloudStack.getCloudItem().getValue().rarityColor.replace("0x", ""), 16);
      CCRenderHelper.drawRectWithShadow((double)x, 30.0D, 350.0D, 160.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)x, 200.0D, 350.0D, 30.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)x, 30.0D, background, 350.0D, 160.0D);
      float val = (float)(Math.sin(this.roationTick / 15.0D) * 4.0D);
      double var10000 = this.lastRotationTick + (this.roationTick - this.lastRotationTick) * (double)par3;
      CCRenderHelper.renderSpecialItemStackInspection(this.cloudStack.getItemStack(), (double)(super.width / 2), 100.0D, (double)val);
      String nick = "";
      if (CloudItemStack.hasNameTag(this.cloudStack)) {
         nick = " (" + EnumChatFormatting.ITALIC + CloudItemStack.getNameTag(this.cloudStack) + EnumChatFormatting.RESET + "" + EnumChatFormatting.BOLD + ")";
      }

      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + this.cloudStack.getDisplayName() + nick, super.width / 2, 211, color);
      this.drawButtons(par1, par2, par3);
   }
}
