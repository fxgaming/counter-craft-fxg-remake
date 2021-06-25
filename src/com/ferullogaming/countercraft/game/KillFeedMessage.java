package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class KillFeedMessage {
   public String player;
   public String playerDead;
   public ItemStack itemUsed;
   public int metaType = 0;
   public int life = 900;

   public KillFeedMessage(String par1, String par2, ItemStack par3, int par4) {
      this.player = par1;
      this.playerDead = par2;
      this.itemUsed = par3;
      this.metaType = par4;
   }

   public void onUpdate() {
      if (this.life > 0) {
         --this.life;
      }

   }

   public void doRender(int x, int y, float particle) {
      Minecraft mc = Minecraft.getMinecraft();
      int nameWidth = mc.fontRenderer.getStringWidth(this.player);
      int nameWidth1 = mc.fontRenderer.getStringWidth(this.playerDead);
      int spacing = 35;
      float alpha = 0.5F;
      boolean renderStack = true;
      if (mc.thePlayer.username.equalsIgnoreCase(EnumChatFormatting.func_110646_a(this.player))) {
         alpha = 0.7F;
      }

      if (this.metaType != 0) {
         spacing += 16;
      }

      if (this.metaType == 3) {
         spacing += 16;
      }

      if (this.metaType == 4) {
         spacing = 35;
         renderStack = false;
      }

      CCRenderHelper.drawRect((double)x, (double)y, (double)(nameWidth + nameWidth1 + spacing), 11.0D, "0x000000", alpha);
      if (EnumChatFormatting.func_110646_a(this.player).equals(mc.thePlayer.username)) {
         CCRenderHelper.renderText(EnumChatFormatting.AQUA + EnumChatFormatting.func_110646_a(this.player), x + 2, y + 2);
      } else {
         CCRenderHelper.renderText(this.player, x + 2, y + 2);
      }

      if (EnumChatFormatting.func_110646_a(this.playerDead).equals(mc.thePlayer.username)) {
         CCRenderHelper.renderText(EnumChatFormatting.AQUA + EnumChatFormatting.func_110646_a(this.playerDead), x + nameWidth + spacing - 1, y + 2);
      } else {
         CCRenderHelper.renderText(this.playerDead, x + nameWidth + spacing - 1, y + 2);
      }

      switch(this.metaType) {
      case 1:
         CCRenderHelper.drawImage((double)(x + nameWidth + 35), (double)(y - 1), new ResourceLocation("countercraft:textures/gui/headshot1.png"), 12.0D, 12.0D);
         break;
      case 2:
         CCRenderHelper.drawImage((double)(x + nameWidth + 35), (double)(y - 1), new ResourceLocation("countercraft:textures/gui/wallbang.png"), 12.0D, 12.0D);
         break;
      case 3:
         CCRenderHelper.drawImage((double)(x + nameWidth + 35), (double)(y - 1), new ResourceLocation("countercraft:textures/gui/wallbang.png"), 12.0D, 12.0D);
         CCRenderHelper.drawImage((double)(x + nameWidth + 35 + 14), (double)(y - 1), new ResourceLocation("countercraft:textures/gui/headshot1.png"), 12.0D, 12.0D);
         break;
      case 4:
         CCRenderHelper.drawImage((double)(x + nameWidth + 12), (double)(y - 1), new ResourceLocation("countercraft:textures/gui/burned1.png"), 12.0D, 12.0D);
      }

      if (this.itemUsed != null && renderStack) {
         GL11.glPushMatrix();
         GL11.glTranslated((double)(x + nameWidth + 15), (double)(y + 10), 10.0D);
         double scale;
         if (this.itemUsed.getItem() instanceof ItemGun) {
            scale = 0.6D;
            GL11.glScaled(scale, scale, scale);
            GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
         }

         if (this.itemUsed.getItem() instanceof ItemKnife) {
            scale = 0.6D;
            GL11.glScaled(scale, scale, scale);
            GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
            GL11.glRotated(-20.0D, 0.0D, 0.0D, 1.0D);
         }

         if (this.itemUsed.getItem() instanceof ItemGrenade) {
            scale = 0.8D;
            GL11.glScaled(scale, scale, scale);
            GL11.glTranslated(4.0D, 1.0D, 0.0D);
         }

         CCRenderHelper.renderSpecialItemStackInventory(this.itemUsed, 0.0D, 0.0D);
         GL11.glPopMatrix();
      }

   }
}
