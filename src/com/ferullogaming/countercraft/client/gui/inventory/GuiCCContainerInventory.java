package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.CloudInventoryFilter;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemBooster;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemCase;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemCoin;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemSticker;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStickerCapsule;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScreen;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class GuiCCContainerInventory extends GuiFGContainer {
   public static int pageNumber = 0;
   public static int pages = 1;
   public static boolean option_HoverInformation = true;
   public static boolean option_HoverInformationUUID = false;
   public static int syncDelay = 0;
   private static ArrayList slots = new ArrayList();
   private static CloudItemStack latestItem = null;
   private static String latestItemUUID = null;
   private static GuiFGButton buttonNext;
   private static GuiFGButton buttonNextAll;
   private static double itemScale = 1.0D;
   private static double lastItemScale = 1.0D;
   private static int refreshDelay = 0;
   private int itemsPerPage = 20;
   private GuiCCSlotInventory hoverSlot = null;

   public GuiCCContainerInventory(int par1, int par2, int par3, int par4, int par5, GuiFGScreen par6) {
      super(par1, par2, par3, par4, par5, par6);
   }

   public static void increasePage() {
      if (pages > 1 && pageNumber < pages - 1) {
         ++pageNumber;
      }

   }

   public static void decreasePage() {
      if (pageNumber > 0) {
         --pageNumber;
      }

   }

   public void initGui() {
      Minecraft mc = Minecraft.getMinecraft();
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(mc.getSession().getUsername());
      ArrayList inv = CloudInventoryFilter.filterValue(cloud.getInventory().getListed());
      slots.clear();
      pages = this.getPages(inv.size());
      int stackx = super.posX + 9;
      int stacky = 50;
      int stackw = 42;
      int stackh = 42;
      int slotmarginx = 44;
      int slotmarginy = 44;
      int rows = 5;
      int col = 4;
      int count = 0 + pageNumber * this.itemsPerPage;

      for(int i = 0; i < col; ++i) {
         for(int j = 0; j < rows; ++j) {
            if (count < inv.size()) {
               CloudItemStack stack = (CloudItemStack)inv.get(count);
               GuiCCSlotInventory slot = (new GuiCCSlotInventory(stackx + j * slotmarginx, stacky + i * slotmarginy, stack, stackw, stackh, cloud.isDefaultItem(stack))).setHighlight(true);
               slots.add(slot);
            }

            ++count;
         }
      }

   }

   public void onSlotClicked(GuiCCSlotInventory par1) {
      Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 2.0F);
   }

   public void addButtons(ArrayList par1) {
      GuiFGButton button = new GuiFGButton(60, super.posX - 1, super.posY + super.height / 2 + 3, 10, 10, "<");
      button.drawBackground = false;
      par1.add(button);
      button = new GuiFGButton(61, super.posX + super.width - 9, super.posY + super.height / 2 + 3, 10, 10, ">");
      button.drawBackground = false;
      par1.add(button);
      buttonNext = new GuiFGButton(62, super.posX + super.width - 65, super.posY + super.height - 40, 30, 15, "Next");
      buttonNext.drawBackground = false;
      par1.add(buttonNext);
      button = new GuiFGButton(64, super.posX + 50, super.posY + 2, 40, 10, EnumChatFormatting.ITALIC + "Options");
      button.drawBackground = false;
      par1.add(button);
      buttonNextAll = new GuiFGButton(63, super.posX + 40, super.posY + super.height - 40, 30, 15, "Finish");
      buttonNextAll.drawBackground = false;
      par1.add(buttonNextAll);
      this.setButtonState(false);
   }

   public void setButtonState(boolean par1) {
      buttonNext.drawButton = par1;
      buttonNext.enabled = par1;
      buttonNextAll.drawButton = par1;
      buttonNextAll.enabled = par1;
   }

   public void actionPerformed(GuiButton par1GuiButton) {
      if (latestItem == null) {
         if (par1GuiButton.id == 60) {
            decreasePage();
            this.initGui();
         }

         if (par1GuiButton.id == 61) {
            increasePage();
            this.initGui();
         }

         if (par1GuiButton.id == 64) {
            GuiCCDDMInventoryOptions gui = new GuiCCDDMInventoryOptions(super.parentGUI, super.posX + 9, super.posY + 24, 80, 12);
            Minecraft.getMinecraft().displayGuiScreen(gui);
         }
      } else {
         if (par1GuiButton.id == 62) {
            latestItem = null;
            latestItemUUID = null;
            itemScale = 1.0D;
            lastItemScale = 1.0D;
            this.initGui();
         }

         if (par1GuiButton.id == 63) {
            latestItem = null;
            latestItemUUID = null;
            itemScale = 1.0D;
            lastItemScale = 1.0D;
            this.initGui();
            PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
            cloud.getInventory().clearNewItems();
         }
      }

   }

   public void mouseClicked(int par1, int par2, int par3) {
      if (latestItem == null) {
         Iterator i$ = slots.iterator();

         while(i$.hasNext()) {
            GuiCCSlotInventory slot = (GuiCCSlotInventory)i$.next();
            Rectangle rect = new Rectangle(slot.x, slot.y, slot.width, slot.height);
            if (rect.contains(par1, par2)) {
               this.onSlotClicked(slot);
               if (par3 == 1) {
                  GuiScreen gui = new GuiCCDDMItemClicked(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  if (slot.stack.getCloudItem() instanceof CloudItemCase) {
                     gui = new GuiCCDDMItemClickedCase(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  if (slot.stack.getCloudItem() instanceof CloudItemSticker) {
                     gui = new GuiCCDDMItemClickedSticker(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  if (slot.stack.getCloudItem() instanceof CloudItemStickerCapsule) {
                     gui = new GuiCCDDMItemClickedStickerCapsule(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  if (slot.stack.getCloudItem().getID() == CloudItem.TRADE_CONTRACT.getID()) {
                     gui = new GuiCCDDMItemClickedTrade(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  if (slot.stack.getCloudItem().getID() == CloudItem.NAME_TAG.getID()) {
                     gui = new GuiCCDDMItemClickedTag(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  if (slot.stack.getCloudItem() instanceof CloudItemCoin) {
                     gui = new GuiCCDDMItemClickedCoin(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  if (slot.stack.getCloudItem() instanceof CloudItemBooster) {
                     gui = new GuiCCDDMItemClickedBooster(super.parentGUI, par1 + 2, par2 + 2, 80, 12, slot.stack);
                  }

                  Minecraft.getMinecraft().displayGuiScreen((GuiScreen)gui);
               }
            }
         }
      }

   }

   public void updateScreen() {
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (syncDelay > 0) {
         --syncDelay;
      } else {
         if (refreshDelay++ > 5) {
            this.initGui();
            refreshDelay = 0;
         }

         GuiCCSlotInventory slot;
         for(Iterator i$ = slots.iterator(); i$.hasNext(); slot.isDefault = cloud.isDefaultItem(slot.stack)) {
            slot = (GuiCCSlotInventory)i$.next();
         }

         if (latestItemUUID == null) {
            latestItemUUID = cloud.getInventory().getLatestItemUUID();
         }

         if (latestItemUUID != null && latestItem == null) {
            latestItem = cloud.getInventory().getStackFromUUID(latestItemUUID);
            if (latestItem != null) {
               cloud.getInventory().playSoundEffect(latestItem.getCloudItem().getValue().rarityID >= 4);
            }
         }

         if (latestItem != null) {
            lastItemScale = itemScale;
            if (itemScale < 4.0D) {
               itemScale += 0.4D;
               if (itemScale > 4.0D) {
                  itemScale = 4.0D;
               }
            }
         }

         this.setButtonState(latestItem != null);
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      super.drawScreen(par1, par2, par3);
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      CCRenderHelper.drawRectWithShadow((double)(super.posX + 1), (double)super.posY, (double)(super.width - 1), 14.0D, GuiCCMenu.menuTheme3, 1.0F);
      CCRenderHelper.renderText("Inventory - ", super.posX + 3, super.posY + 3);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + "Max " + cloud.maxInvSlots / 20 + " Pages", super.posX + super.width - 58, super.posY + 3);
      CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.WHITE + "Page " + (pageNumber + 1) + "/" + pages, super.posX + super.width / 2, super.posY + 17, 0.75D);
      if (syncDelay > 0) {
         CCRenderHelper.renderCenteredText("Syncing Inventory...", super.posX + super.width / 2, super.posY + 50);
      } else {
         this.hoverSlot = null;

         for(int i = 0; i < slots.size(); ++i) {
            GuiCCSlotInventory slot = (GuiCCSlotInventory)slots.get(i);
            if (latestItem != null) {
               if (i == 0 || i == 4 || i == 5 || i == 9 || i == 10 || i == 14 || i >= 15) {
                  slot.doRender(par1, par2, par3);
               }
            } else {
               slot.doRender(par1, par2, par3);
               if (slot.isMouseOver(par1, par2) && this.hoverSlot != slot) {
                  this.hoverSlot = slot;
               }
            }
         }

         if (slots == null || slots.size() <= 0) {
            CCRenderHelper.renderCenteredTextScaledWithOutline("No items to display.", super.posX + 118, super.posY + 108, 1.0D);
         }

         if (Minecraft.getMinecraft().currentScreen == super.parentGUI) {
            if (latestItem != null) {
               this.hoverSlot = null;
               String color = latestItem.getCloudItem().getValue().rarityColor;
               CCRenderHelper.drawRectWithShadow((double)(super.posX + 20), (double)(super.posY + 20), (double)(super.width - 40), (double)(super.height - 40), color, 1.0F);
               CCRenderHelper.drawRectWithShadow((double)(super.posX + 25), (double)(super.posY + 25), (double)(super.width - 50), (double)(super.height - 80), GuiCCMenu.menuTheme, 1.0F);
               CCRenderHelper.drawGradientRect(super.posX + 25, super.posY + 25, 188, 129, 0.2F);
               GL11.glPushMatrix();
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glTranslated((double)(super.posX + super.width / 2 - 4), (double)(super.posY + super.height / 2 + 15), 0.0D);
               double scale = lastItemScale + (itemScale - lastItemScale) * (double)par3;
               GL11.glScaled(scale, scale, scale);
               CCRenderHelper.renderSpecialItemStackInventory(latestItem.getItemStack(), 0.0D, 0.0D);
               GL11.glPopMatrix();
               String name = EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + latestItem.getDisplayName();
               CCRenderHelper.renderCenteredText(name, super.posX + super.width / 2, super.height - 23);
               CCRenderHelper.renderTextScaled(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "New Item!", super.posX + 80, super.posY + 30, 1.4D);
               if (cloud.getInventory().newItemsList.size() > 0) {
                  String items = "+ " + cloud.getInventory().newItemsList.size() + " More";
                  CCRenderHelper.renderCenteredText(EnumChatFormatting.WHITE + items, super.posX + super.width / 2, super.height - 10);
               }
            }

            if (this.hoverSlot != null && option_HoverInformation) {
               int margin = 3;
               int x1 = this.hoverSlot.x + this.hoverSlot.width + margin;
               int y1 = this.hoverSlot.y - 5;
               int width = 80;
               CloudItemStack stack = this.hoverSlot.stack;
               CloudItem item = this.hoverSlot.stack.getCloudItem();
               ArrayList info = new ArrayList();
               item.addInformation(stack, info);
               if (option_HoverInformationUUID) {
                  info.add("");
                  info.add(EnumChatFormatting.GRAY + stack.getUUID().substring(0, 12));
                  info.add(EnumChatFormatting.GRAY + "" + stack.getCloudItem().getID());
               }

               int i;
               for(i = 0; i < info.size(); ++i) {
                  int infowidth = Minecraft.getMinecraft().fontRenderer.getStringWidth((String)info.get(i));
                  if (infowidth > width) {
                     width = infowidth;
                  }
               }

               int height = info.size() * 10 + 7;
               width += 8;
               if (x1 > super.posX + super.width / 2 + 50) {
                  x1 = this.hoverSlot.x - width - margin;
               }

               CCRenderHelper.drawRectWithShadow((double)x1, (double)y1, (double)width, (double)height, "0xffffff", 1.0F);
               CCRenderHelper.drawRectWithShadow((double)(x1 + 2), (double)(y1 + 2), (double)(width - 4), (double)(height - 4), "0x222222", 1.0F);

               for(i = 0; i < info.size(); ++i) {
                  String var1 = (String)info.get(i);
                  CCRenderHelper.renderText(var1, x1 + 4, y1 + 5 + i * 10);
               }
            }

         }
      }
   }

   public int getPages(int size) {
      int var1 = size;

      int var2;
      for(var2 = 0; var1 > 0; ++var2) {
         var1 -= this.itemsPerPage;
      }

      return var2;
   }
}
