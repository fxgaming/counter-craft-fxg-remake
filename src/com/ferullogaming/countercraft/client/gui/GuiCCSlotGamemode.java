package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.game.references.GameType;
import java.awt.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiCCSlotGamemode {
   public GameType game;
   public int x;
   public int y;
   public int width;
   public int height;
   public boolean shouldPlaySound = false;
   public boolean enabled = true;

   public GuiCCSlotGamemode(GameType par1, int par2, int par3) {
      this.game = par1;
      this.x = par2;
      this.y = par3;
      this.width = 28;
      this.height = 28;
   }

   public void doRender(int par1, int par2, float par3, boolean par4) {
      if (this.isHovered(par1, par2)) {
         if (this.shouldPlaySound) {
            Minecraft.getMinecraft().sndManager.playSound("countercraft:gui.buttonHover", 0.0F, 0.0F, 0.0F, 1.0F, 2.0F);
            this.shouldPlaySound = false;
         }

         CCRenderHelper.drawRectWithShadow((double)(this.x - 1), (double)(this.y - 1), (double)(this.width + 2), (double)(this.height + 2), "0xFFFF00", 1.0F);
      } else {
         this.shouldPlaySound = true;
      }

      CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)this.x, (double)this.y, new ResourceLocation("countercraft:textures/misc/gamemodes/" + this.game.getNameID() + (!this.enabled ? "_d" : "") + ".png"), (double)this.width, (double)this.height);
      if (this.game.isInDevelopment()) {
         CCRenderHelper.drawImageTransparent((double)this.x, (double)this.y, new ResourceLocation("countercraft:textures/misc/gamemodes/new.png"), (double)this.width, (double)this.height, 255.0D);
      }
   }

   public void doRenderHighlight(int par1) {
      String color = "0xFFFFFF";
      CCRenderHelper.drawRect((double)(this.x - par1), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x + this.width), (double)this.y, (double)par1, (double)this.height, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y - par1), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
      CCRenderHelper.drawRect((double)(this.x - par1), (double)(this.y + this.height), (double)(this.width + par1 * 2), (double)par1, color, 1.0F);
   }

   public void doRenderHighlightNot() {
      String color = "0x000000";
      CCRenderHelper.drawRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, color, 0.5F);
   }

   public boolean isMouseOver(int x, int y) {
      Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
      return rect.contains(x, y);
   }

   public boolean isHovered(int givenMouseX, int givenMouseY) {
      if (givenMouseX > this.x && givenMouseX < this.x + this.width && this.enabled) {
         return givenMouseY > this.y && givenMouseY < this.y + this.height;
      } else {
         return false;
      }
   }
}
