package com.ferullogaming.countercraft.client.gui.inventory;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemRarity;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.awt.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCCSlotInventory {
   public CloudItemStack stack;
   public int x;
   public int y;
   public int width;
   public int height;
   public int lastX;
   public boolean isDefault = false;
   public boolean allowHighlight = false;
   public boolean isRendering = false;

   public GuiCCSlotInventory(int par1, int par2, CloudItemStack par3, int par4, int par5, boolean par6) {
      this.stack = par3;
      this.x = par1;
      this.lastX = this.x;
      this.y = par2;
      this.width = par4;
      this.height = par5;
      this.isDefault = par6;
   }

   public GuiCCSlotInventory setHighlight(boolean par1) {
      this.allowHighlight = par1;
      return this;
   }

   public void updateSlot(int par1, int par2) {
      this.lastX = this.x;
      this.x += par1;
      this.y += par2;
   }

   public void doRender(int par1, int par2, float par3) {
      double smoothX = (double)((float)this.lastX + (float)(this.x - this.lastX) * par3);
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (this.stack.getCloudItem() != null) {
         this.isRendering = true;
         boolean isShowcasing = false;
         if (data != null && data.isShowcasingItem(this.stack)) {
            isShowcasing = true;
         }

         String rarity = this.stack.getCloudItem().getValue().rarityColor;
         CCRenderHelper.drawRect(smoothX - 1.0D, (double)(this.y - 1), (double)(this.width + 2), (double)(this.height + 2), "0x000000", 0.2F);
         CCRenderHelper.drawRect(smoothX, (double)this.y, (double)this.width, (double)this.height, "0x000000", 1.0F);
         CCRenderHelper.drawImage(smoothX, (double)this.y, new ResourceLocation("countercraft:textures/gui/inventorygradient.png"), (double)this.width, (double)(this.height - 15));
         CCRenderHelper.drawRect(smoothX, (double)(this.y + this.height - 15), (double)this.width, 15.0D, rarity, 1.0F);
         CCRenderHelper.renderSpecialItemStackInventory(this.stack.getItemStack(), smoothX + (double)(this.width / 2), (double)(this.y + this.height / 2));
         EnumChatFormatting nameColor = EnumChatFormatting.WHITE;
         if (isShowcasing) {
            nameColor = EnumChatFormatting.YELLOW;
         }

         CCRenderHelper.renderTextScaled(nameColor + this.stack.getCloudItem().getName(), (int)(smoothX + 1.0D), this.y + this.height - 13, 0.75D);
         String suffix = this.stack.getCloudItem().getValue() != CloudItemRarity.DEFAULT ? this.stack.getCloudItem().getSuffix() : "Default";
         if (suffix.equals("none")) {
            suffix = "";
         }

         if (this.stack.hasMetaData() && CloudItemStack.hasNameTag(this.stack)) {
            suffix = EnumChatFormatting.ITALIC + CloudItemStack.getNameTag(this.stack);
         }

         CCRenderHelper.renderTextScaled(EnumChatFormatting.WHITE + suffix, (int)(smoothX + 1.0D), this.y + this.height - 6, 0.7D);
         if (this.isDefault) {
            CCRenderHelper.renderColor(rarity);
            CCRenderHelper.drawImage(smoothX + (double)this.width - 10.0D + 0.5D, (double)this.y, new ResourceLocation("countercraft:textures/gui/check.png"), 8.0D, 8.0D);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (this.allowHighlight && this.isMouseOver(par1, par2)) {
            this.doRenderHighlight(1);
         }
      }

   }

   public void doRenderHighlight(int par1) {
      String color = "0xffba00";
      CCRenderHelper.drawRect((double)(this.x - par1), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x + this.width), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y - par1), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y + this.height), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
   }

   public boolean isMouseOver(int x, int y) {
      Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
      return rect.contains(x, y);
   }
}
