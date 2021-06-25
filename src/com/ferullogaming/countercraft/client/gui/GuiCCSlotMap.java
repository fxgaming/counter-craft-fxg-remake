package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.game.references.MapType;
import net.minecraft.client.Minecraft;

public class GuiCCSlotMap {
   public MapType map;
   public int x;
   public int y;
   public boolean shouldPlaySound = false;
   public boolean selected = false;

   public GuiCCSlotMap(MapType par1, int par2, int par3) {
      this.map = par1;
      this.x = par2;
      this.y = par3;
   }

   public void doRender(int par1, int par2, float par3, boolean par4) {
      if (this.isHovered(par1, par2)) {
         if (this.shouldPlaySound) {
            Minecraft.getMinecraft().sndManager.playSound("countercraft:gui.buttonHover", 0.0F, 0.0F, 0.0F, 1.0F, 2.0F);
            this.shouldPlaySound = false;
         }

         CCRenderHelper.drawRectWithShadow((double)(this.x - 1), (double)(this.y - 1), 47.0D, 47.0D, "0xFFFF00", 1.0F);
      } else {
         this.shouldPlaySound = true;
      }

      CCRenderHelper.drawRectWithShadow((double)this.x, (double)this.y, 45.0D, 45.0D, GuiCCMenu.menuTheme3, 1.0F);
      if (par4) {
         CCRenderHelper.drawRect((double)this.x, (double)this.y, 45.0D, 45.0D, "0xFFFFFF", 1.0F);
      }

      CCRenderHelper.drawImage((double)(this.x + 1), (double)(this.y + 1), this.map.displayImage, 43.0D, 43.0D);
      if (!par4) {
         CCRenderHelper.drawRect((double)this.x, (double)this.y, 45.0D, 45.0D, "0x000000", 0.3F);
      }

      CCRenderHelper.drawRect((double)(this.x + 1), (double)(this.y + 31), 43.0D, 13.0D, "0x000000", 0.5F);
      CCRenderHelper.renderCenteredTextScaledWithOutline(this.map.displayName, this.x + 23, this.y + 34, 1.0D);
   }

   public boolean isHovered(int givenMouseX, int givenMouseY) {
      if (givenMouseX > this.x && givenMouseX < this.x + 45) {
         return givenMouseY > this.y && givenMouseY < this.y + 45;
      } else {
         return false;
      }
   }
}
