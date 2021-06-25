package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class KilledMessage {
   public String username;
   public ItemStack itemUsed;
   public int userHealth;

   public KilledMessage(String par1, ItemStack par2, int par3) {
      this.username = par1;
      this.itemUsed = par2;
      this.userHealth = par3;
   }

   public void doRender(Minecraft m, int width, int height) {
      int width1 = 200;
      int height1 = 80;
      int x = width / 2 - width1 / 2;
      int y = height / 2 + 10;
      CCRenderHelper.drawRectWithShadow((double)x, (double)y, (double)width1, (double)height1, "0x000000", 0.5F);
      CCRenderHelper.renderTextScaled(EnumChatFormatting.RED + "Убит игроком " + this.username, x + 5, y + 5, 1.5D);
      CCRenderHelper.renderTextScaled("Здоровья: " + this.userHealth, x + 5, y + 20, 1.5D);
      CCRenderHelper.renderPlayerHead(this.username, (double)(x + 5), (double)(y + 38), 2.0D, false);
      String itemName = this.itemUsed.getDisplayName() + "";
      CCRenderHelper.renderTextScaled("" + itemName, x + 80, y + 30, 1.0D);
      GL11.glPushMatrix();
      GL11.glTranslated((double)(x + 120), (double)(y + 70), 10.0D);
      double scale = 2.0D;
      GL11.glScaled(scale, scale, scale);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
      CCRenderHelper.renderSpecialItemStackInventory(this.itemUsed, 0.0D, 0.0D);
      GL11.glPopMatrix();
   }
}
