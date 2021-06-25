package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.item.CaseContent;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemCase;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryOpenCase;
import com.ferullogaming.countercraft.client.cloud.packet.server.PacketPlayerDataIncreaseValue;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerStats;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCMenuOpenCase extends GuiCCMenu implements IGuiFGPromptYesNo {
   private CloudItemStack caseOpeningUUID;
   private ArrayList potentialWinSlots = new ArrayList();
   private ArrayList rollingSlots = new ArrayList();
   private boolean isRolling = false;
   private float rollingForce = 0.0F;
   private int rollingOverDelay = 0;
   private GuiCCSlotInventory lastRollingSlot = null;
   private GuiFGButton openButton;
   private boolean isRequestingCaseOpen = false;

   public GuiCCMenuOpenCase(CloudItemStack par1) {
      this.caseOpeningUUID = par1;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("My Profile").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiCCContainerStats(3, x + 1, 83, 110, 150, this));
      int x1 = x + 114;
      int y1 = 25;
      int width1 = 1;
      int height1 = 1;
      String var1 = "" + ((CloudItemCase)this.caseOpeningUUID.getCloudItem()).costToOpen + "c";
      this.openButton = new GuiFGButton(30, x1 + 2, y1 + 2, 60, 10, EnumChatFormatting.BOLD + "Open " + var1);
      this.openButton.buttonColor = "0x00dc00";
      super.buttonList.add(this.openButton);
      if (this.isRequestingCaseOpen || this.isRolling) {
         this.openButton.enabled = false;
      }

      ArrayList stacks = this.getPotentailStacks();
      this.potentialWinSlots.clear();
      int sx1 = x1 + 10;
      int sy1 = y1 + 73;
      int sdx = 0;
      int sdy = 0;

      for(int i = 0; i < stacks.size(); ++i) {
         if (i != 0 && i % 5 == 0) {
            sdx -= 220;
            sdy += 44;
         }

         GuiCCSlotInventory slot = new GuiCCSlotInventory(sx1 + 44 * i + sdx, sy1 + sdy, (CloudItemStack)stacks.get(i), 42, 42, false);
         this.potentialWinSlots.add(slot);
      }

   }

   public ArrayList getPotentailStacks() {
      ArrayList list = new ArrayList();
      CloudItemCase caze = (CloudItemCase)this.caseOpeningUUID.getCloudItem();

      for(int i = 0; i < caze.contentDisplay.size(); ++i) {
         if (i <= 14) {
            CloudItemStack stack = new CloudItemStack("fake", ((CaseContent)caze.contentDisplay.get(i)).getCloudItem().getID());
            list.add(stack);
         }
      }

      return list;
   }

   public void onResultsIn(ArrayList par1) {
      int x = super.width / 2 - 173;
      int x1 = x + 114;
      int y1 = 25;
      int width1 = 237;
      int height1 = 1;
      if (this.isRequestingCaseOpen) {
         Random rand = new Random();
         float randomF = (0.5F - rand.nextFloat()) / 5.0F;
         if (randomF < 0.0F) {
            randomF *= 10.0F;
            if ((double)randomF < -0.28D) {
               randomF = -0.28F + rand.nextFloat() / 10.0F;
            }
         }

         if ((double)randomF > 0.1D) {
            randomF = 0.1F;
         }

         if (randomF < -0.29F) {
            randomF = -0.29F;
         }

         this.isRolling = true;
         this.rollingForce = 30.0F + randomF;
         this.rollingSlots.clear();
         int sx1 = x1 + 10;
         int sy1 = y1 + 20;
         int sdx = 1;
         int sdy = 0;

         for(int i = 0; i < par1.size(); ++i) {
            GuiCCSlotInventory slot = new GuiCCSlotInventory(x1 + width1 + 44 * i, sy1 + sdy, (CloudItemStack)par1.get(i), 42, 42, false);
            this.rollingSlots.add(slot);
         }

         this.isRequestingCaseOpen = false;
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      if (this.isRolling) {
         Iterator i$ = this.rollingSlots.iterator();

         while(i$.hasNext()) {
            GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
            slot.updateSlot(0, 0);
         }
      }

      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      if (par1GuiButton.id == 30 && !this.isRequestingCaseOpen && !this.isRolling) {
         String var1 = "" + ((CloudItemCase)this.caseOpeningUUID.getCloudItem()).costToOpen + " Emeralds";
         GuiFGYesNoPrompt gui = (new GuiFGYesNoPrompt(1, this)).addInformation("Opening the case will cost:", EnumChatFormatting.GREEN + "" + var1, "Continue?");
         super.mc.displayGuiScreen(gui);
      }

   }

   public void updateScreen() {
      super.updateScreen();
      Iterator i$ = this.rollingSlots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
         slot.updateSlot((int)(-this.rollingForce), 0);
      }

      if (this.isRolling) {
         if (this.rollingForce > 0.0F) {
            this.rollingForce -= 0.34F;
         }

         if (this.rollingForce <= 0.2F && this.rollingOverDelay++ >= 30) {
            if (this.caseOpeningUUID.getCloudItem().getID() == 2031) {
               ClientCloudManager.sendPacket(new PacketPlayerDataIncreaseValue(Minecraft.getMinecraft().getSession().getUsername(), "halloweencases", 1));
            }

            super.mc.displayGuiScreen(new GuiCCMenuInventory());
         }
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      int x1 = x + 114;
      int y1 = 25;
      int width1 = 237;
      int height1 = 208;
      CCRenderHelper.drawRectWithShadow((double)x1, (double)y1, (double)width1, (double)height1, "0x343434", 1.0F);
      CCRenderHelper.renderCenteredText(EnumChatFormatting.YELLOW + "" + this.caseOpeningUUID.getDisplayName(), x1 + width1 / 2, y1 + 3);
      CCRenderHelper.drawRectWithShadow((double)(x1 + 1), (double)(y1 + 14), (double)(width1 - 2), 54.0D, "0x64768c", 1.0F);
      int widthMargin = 52;
      CCRenderHelper.drawRectWithShadow((double)(x1 + widthMargin), (double)(y1 + 16), (double)(width1 - widthMargin * 2), 50.0D, "0x343434", 1.0F);
      Iterator i$ = this.potentialWinSlots.iterator();

      while(i$.hasNext()) {
         GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
         slot.doRender(par1, par2, par3);
      }

      int min = x1 + width1 / 2 - 15;
      int max = x1 + width1 / 2 + 15;
      Iterator i$1 = this.rollingSlots.iterator();

      while(true) {
         GuiCCSlotInventory slot;
         while(true) {
            if (!i$1.hasNext()) {
               CCRenderHelper.drawRect((double)(x1 + 1), (double)(y1 + 15), 57.0D, 52.0D, "0x64768c", 1.0F);
               CCRenderHelper.drawRect((double)(x1 + 179), (double)(y1 + 15), 57.0D, 52.0D, "0x64768c", 1.0F);
               CCRenderHelper.drawRect((double)(x1 + width1 / 2), 42.0D, 1.0D, 48.0D, "0xffb700", 1.0F);
               this.drawButtons(par1, par2, par3);
               return;
            }

            slot = (GuiCCSlotInventory)i$1.next();
            if (slot.x <= x1 + 2 || slot.x >= x1 + 52 + 123) {
               break;
            }

            if (this.rollingForce <= 22.0F || slot.x <= x1 + 52 + 115) {
               slot.doRender(par1, par2, par3);
               break;
            }
         }

         if (slot.x >= min && slot.x <= max && this.lastRollingSlot != slot) {
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 3.0F);
            this.lastRollingSlot = slot;
         }
      }
   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2) {
         ClientCloudManager.sendPacket(new PacketInventoryOpenCase(this.caseOpeningUUID.getUUID()));
         this.openButton.enabled = false;
         this.isRequestingCaseOpen = true;
      }

   }
}
