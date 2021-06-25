package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class BombKilledMessage extends KilledMessage {
   public String name;

   public BombKilledMessage(String name) {
      super(name, (ItemStack)null, 0);
      this.name = name;
   }

   public void doRender(Minecraft m, int width, int height) {
      int width1 = 200;
      int height1 = 80;
      int x = width / 2 - width1 / 2;
      int y = height / 2 + 10;
      CCRenderHelper.drawRectWithShadow((double)x, (double)y, (double)width1, (double)height1, "0x000000", 0.5F);
      CCRenderHelper.renderTextScaled(EnumChatFormatting.DARK_RED + "Убит игроком " + this.name, x + 5, y + 5, 1.5D);
      GL11.glPushMatrix();
      GL11.glTranslated((double)(x + 120), (double)(y + 70), 10.0D);
      double scale = 2.0D;
      GL11.glScaled(scale, scale, scale);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
      GL11.glPopMatrix();
   }
}
