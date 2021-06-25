package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryItemApplySticker;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryItemRemoveSticker;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGYesNoPrompt;
import com.ferullogaming.countercraft.client.gui.api.IGuiFGPromptYesNo;
import com.ferullogaming.countercraft.item.ItemSticker;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCMenuManageSticker extends GuiCCMenu implements IGuiCCPromptInvSelResults, IGuiFGPromptYesNo {
   public double roationTick = 0.0D;
   public double lastRotationTick;
   public boolean soundPlayed = false;
   ResourceLocation background = new ResourceLocation("countercraft:textures/gui/inspectBackground.png");
   String inspectSound = "countercraft:gui.inspectItem";
   private GuiScreen parentGUI;
   private CloudItemStack cloudStack;
   private CloudItemStack stickerStack;
   private int stickerSelection = 0;

   public GuiCCMenuManageSticker(GuiScreen par1, CloudItemStack par2) {
      this.parentGUI = par1;
      this.cloudStack = par2;
   }

   public GuiCCMenuManageSticker(GuiScreen par1, CloudItemStack par2, int givenStickerID) {
      this.parentGUI = par1;
      this.cloudStack = par2;
      this.stickerSelection = givenStickerID;
      this.soundPlayed = true;
   }

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 172;
      super.buttonList.add(new GuiFGButton(16, x + 5, 205, 30, 20, new ResourceLocation("countercraft:textures/gui/return.png")));
      super.buttonList.add(new GuiFGButton(17, x + 40, 205, 20, 20, "<"));
      super.buttonList.add(new GuiFGButton(18, x + 65, 205, 20, 20, ">"));
      String stickerName = "none";
      switch(this.stickerSelection) {
      case 0:
         stickerName = CloudItemStack.getSticker0(this.cloudStack);
         break;
      case 1:
         stickerName = CloudItemStack.getSticker1(this.cloudStack);
         break;
      case 2:
         stickerName = CloudItemStack.getSticker2(this.cloudStack);
         break;
      default:
         stickerName = CloudItemStack.getSticker0(this.cloudStack);
      }

      if (stickerName != null && !stickerName.equals("none")) {
         super.buttonList.add(new GuiFGButton(20, x + 90, 205, 70, 20, "Remove Sticker"));
      } else {
         super.buttonList.add(new GuiFGButton(19, x + 90, 205, 70, 20, "Apply Sticker"));
      }

   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par1GuiButton.id == 16) {
         super.mc.displayGuiScreen(this.parentGUI);
      }

      if (par1GuiButton.id == 17) {
         ++this.stickerSelection;
         if (this.stickerSelection > 2) {
            this.stickerSelection = 0;
         }

         super.mc.displayGuiScreen(new GuiCCMenuManageSticker(this.parentGUI, this.cloudStack, this.stickerSelection));
      }

      if (par1GuiButton.id == 18) {
         --this.stickerSelection;
         if (this.stickerSelection < 0) {
            this.stickerSelection = 2;
         }

         super.mc.displayGuiScreen(new GuiCCMenuManageSticker(this.parentGUI, this.cloudStack, this.stickerSelection));
      }

      if (par1GuiButton.id == 19) {
         ArrayList list = new ArrayList(data.getInventory().getListed());
         ArrayList newList = new ArrayList();
         Iterator i$ = list.iterator();

         while(i$.hasNext()) {
            CloudItemStack cloudItemStack = (CloudItemStack)i$.next();
            if (cloudItemStack.getItemStack().getItem() instanceof ItemSticker) {
               newList.add(cloudItemStack);
            }
         }

         super.mc.displayGuiScreen(new GuiCCPromptInventorySelection(this, newList, 1));
      }

      if (par1GuiButton.id == 20) {
         super.mc.displayGuiScreen((new GuiFGYesNoPrompt(2, this)).addInformation("Scratch this sticker?", "Scratch sticker off at position " + this.stickerSelection + "?"));
      }

   }

   public void updateScreen() {
      super.updateScreen();
      this.lastRotationTick = this.roationTick++;
      if (!this.soundPlayed) {
         Minecraft.getMinecraft().sndManager.playSoundFX(this.inspectSound, 1.0F, 1.0F);
         this.soundPlayed = true;
      }

      if (this.stickerStack != null) {
         super.mc.displayGuiScreen((new GuiFGYesNoPrompt(1, this)).addInformation("Place this sticker?", "Place sticker at position " + this.stickerSelection + "?"));
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 172;
      int color = Integer.parseInt(this.cloudStack.getCloudItem().getValue().rarityColor.replace("0x", ""), 16);
      CCRenderHelper.drawRectWithShadow((double)x, 30.0D, 350.0D, 160.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawRectWithShadow((double)x, 200.0D, 350.0D, 30.0D, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)x, 30.0D, this.background, 350.0D, 160.0D);
      float val = (float)(Math.sin(this.roationTick / 20.0D) * 2.0D);
      double var10000 = this.lastRotationTick + (this.roationTick - this.lastRotationTick) * (double)par3;
      CCRenderHelper.renderSpecialItemStackInspection(this.cloudStack.getItemStack(), (double)(super.width / 2), 100.0D, (double)val, this.stickerSelection);
      String nick = "";
      if (CloudItemStack.hasNameTag(this.cloudStack)) {
         nick = " (" + EnumChatFormatting.ITALIC + CloudItemStack.getNameTag(this.cloudStack) + EnumChatFormatting.RESET + "" + EnumChatFormatting.BOLD + ")";
      }

      CCRenderHelper.renderCenteredText(EnumChatFormatting.BOLD + this.cloudStack.getDisplayName() + nick, super.width / 2 + 95, 211, color);
      this.drawButtons(par1, par2, par3);
   }

   public void onSelected(ArrayList stacks) {
      if (stacks.size() > 0) {
         this.stickerStack = (CloudItemStack)stacks.get(0);
      }

   }

   public void onResult(int par1, boolean par2) {
      if (par1 == 1 && par2) {
         ClientCloudManager.sendPacket(new PacketInventoryItemApplySticker(this.stickerStack.getUUID(), this.cloudStack.getUUID(), "" + this.stickerSelection));
         super.mc.displayGuiScreen(new GuiCCMenuInventory());
      }

      if (par1 == 2 && par2) {
         ClientCloudManager.sendPacket(new PacketInventoryItemRemoveSticker(this.cloudStack.getUUID(), "" + this.stickerSelection));
         super.mc.displayGuiScreen(new GuiCCMenuInventory());
      }

      this.stickerStack = null;
   }
}
