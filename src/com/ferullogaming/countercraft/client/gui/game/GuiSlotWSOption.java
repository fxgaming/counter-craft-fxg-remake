package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.game.IGameComponentEconomy;
import java.awt.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GuiSlotWSOption {
   public String option;
   public ItemStack stackOption;
   public String[] info;
   public int x;
   public int y;
   public int width;
   public int height;

   public GuiSlotWSOption(int par1, int par2, String par3, int par4, int par5, String[] par6) {
      this.x = par1;
      this.y = par2;
      this.option = par3;
      this.width = par4;
      this.height = par5;
      this.info = par6;
   }

   public GuiSlotWSOption setStack(ItemStack par1) {
      this.stackOption = par1;
      return this;
   }

   public void doRender(int par1, int par2, float par3, IGameComponentEconomy par4) {
      if (par4 != null && this.stackOption != null) {
         String var1 = "$" + par4.getItemPrice(this.stackOption, this.stackOption.itemID);
         String var2 = GuiCCMenu.menuTheme3;
         if (par4.hasPlayerEconomy(Minecraft.getMinecraft().thePlayer.username, par4.getItemPrice(this.stackOption, this.stackOption.itemID))) {
            var1 = EnumChatFormatting.GREEN + var1;
            var2 = GuiCCMenu.menuTheme3;
         } else {
            var1 = EnumChatFormatting.RED + var1;
            var2 = GuiCCMenu.menuTheme3;
         }

         CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, var2, 1.0F);
         CCRenderHelper.renderTextRight(var1, this.x + this.width - 2, this.y + 7);
      } else {
         CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, GuiCCMenu.menuTheme3, 1.0F);
      }

      CCRenderHelper.renderText(this.option.replaceAll(" Граната", ""), this.x + 4, this.y + 7);
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
