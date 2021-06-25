package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemGun;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScroller;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuInspect;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCCMenuSkinViewer extends GuiCCMenu {
   public static File[] skinFiles;
   public static ItemGun selectedGun;
   public GuiFGScroller customSkinScroller;
   public ResourceLocation currentSkinResourceLocation;
   public CloudItemGun displayItem = new CloudItemGun(100, 4000, "M4A4");
   public int vanillaItemID = 100;
   public int cloudItemID = 4000;
   public String skinName = "";
   public float rotationX = 0.0F;
   public float rotationY = 0.0F;

   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, super.mc.getSession().getUsername());
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Weapon/Skin Selection").setColor(GuiCCMenu.menuTheme3));
      this.addContainer((new GuiFGContainerText(6, x + 114, 25, 237, 14, this)).setText("Skin Viewer").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiFGContainer(7, x + 1, 83, 110, 150, this));
      this.addContainer(new GuiFGContainer(8, x + 114, 93, 237, 140, this));
      this.addContainer((new GuiFGContainer(9, x + 118, 97, 229, 132, this)).setColor(GuiCCMenu.menuTheme3));
      this.customSkinScroller = new GuiFGScroller(8, x + 3, 117, 106, 114, this);
      this.addContainer(this.customSkinScroller);
      super.buttonList.add((new GuiFGButton(20, x + 3, 85, 106, 13, "Select Weapon Model")).setToolTip("Select the Weapon Model for your Skin", Color.black));
      super.buttonList.add((new GuiFGButton(21, x + 3, 101, 106, 13, "Refresh Skins Folder")).setToolTip("Refresh the Skins Folder and Skin List", Color.black));
      super.buttonList.add((new GuiFGButton(22, x + 289, 215, 56, 12, "Publish Skin")).setToolTip("Publish to Workshop", Color.black));
      this.refreshSkinsFolder();
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      super.actionPerformed(par1GuiButton);
      this.addOverheadMenuActionPerformed(par1GuiButton);
      if (par1GuiButton.id == 20) {
         GuiCCDDMSelectGunItem gui = new GuiCCDDMSelectGunItem(this, par1GuiButton.xPosition + 107, par1GuiButton.yPosition, 60, 13, this);
         super.mc.displayGuiScreen(gui);
      }

      if (par1GuiButton.id == 21) {
         this.refreshSkinsFolder();
      }

      if (par1GuiButton.id == 22) {
         ClientNotification.createNotification("This feature is Coming Soon!");
      }

   }

   public void updateScreen() {
      super.updateScreen();
      ++this.rotationY;
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      int x1 = x + 107;
      int y1 = 41;
      int lineCount = 0;
      String instructions = "Select a Weapon and Skin from the left-side panel. You can add more skins to your skin selection via the supplied " + EnumChatFormatting.RESET + "../countercraft/client/skins" + EnumChatFormatting.WHITE + " folder " + "(in .png format). Please make sure to use the necessary weapon model and skin for the best " + "evaluation.";
      ArrayList messages = StringListHelperCC.getListLimitWidth(instructions, 235);
      CCRenderHelper.drawRectWithShadow((double)(x1 + 7), (double)(y1 - 1), 237.0D, (double)(10 * messages.size()), GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)(x + 118), 97.0D, GuiCCMenuInspect.background, 229.0D, 132.0D);
      if (this.currentSkinResourceLocation != null) {
         String textureName = this.currentSkinResourceLocation.toString().replace("minecraft:dynamic/", "");
         ItemGun gunItem = selectedGun;
         ItemStack gunStack = new ItemStack(gunItem, 1);
         ItemGun.setGunSkin(gunStack, textureName);
         GL11.glPushMatrix();
         CCRenderHelper.renderSpecialItemStackInventoryWithRotation(gunStack, (double)(super.width / 2 + 60), 195.0D, 4.0D, this.rotationX, this.rotationY);
         GL11.glPopMatrix();
      } else {
         CCRenderHelper.renderCenteredTextScaledWithOutline("No Skin has been Selected", x1 + 122, 158, 1.0D);
         CCRenderHelper.renderCenteredTextScaledWithOutline("Select a skin from the left-side panel", x1 + 122, 168, 0.5D);
      }

      for(int i1 = 0; i1 < messages.size(); ++i1) {
         CCRenderHelper.renderText(EnumChatFormatting.WHITE + (String)messages.get(i1), x1 + 10, y1 + lineCount * 10);
         ++lineCount;
      }

      this.drawButtons(par1, par2, par3);
   }

   public void refreshSkinsFolder() {
      this.customSkinScroller.slots.clear();
      this.customSkinScroller.onSlotHeightChanged();
      File skinsDirectory = new File("countercraft/client/skins/");
      if (!skinsDirectory.exists()) {
         skinsDirectory.mkdir();
         ClientNotification not = new ClientNotification("Skins folder did not exist and has now been created!");
         ClientTickHandler.addClientNotification(not);
      }

      skinFiles = skinsDirectory.listFiles();
      if (skinFiles != null) {
         File[] arr$ = skinFiles;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File file = arr$[i$];
            if (file.getName().endsWith(".png")) {
               this.customSkinScroller.slots.add(new GuiCCSlotCustomSkin(file, this));
               this.customSkinScroller.onSlotHeightChanged();
            }
         }
      }

   }

   static {
      selectedGun = (ItemGun)ItemManager.m4a1;
   }
}
