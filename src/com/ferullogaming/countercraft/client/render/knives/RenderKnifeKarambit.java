package com.ferullogaming.countercraft.client.render.knives;

import org.lwjgl.opengl.GL11;

import com.ferullogaming.countercraft.client.model.knives.ModelKnifeKarambit;
import com.ferullogaming.countercraft.item.ItemKnife;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderKnifeKarambit extends RenderKnife {
   public RenderKnifeKarambit(String par1) {
      super(par1);
   }

   public void renderItemValues(ItemStack par1, ItemRenderType par2) {
      double scale;
      if (par2 == ItemRenderType.ENTITY) {
         GL11.glRotated(90.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
         scale = 0.5D;
         GL11.glScaled(scale, scale, scale);
         GL11.glScaled(0.8999999761581421D, 1.100000023841858D, 0.8999999761581421D);
      }

      if (par2 == ItemRenderType.EQUIPPED) {
         GL11.glRotated(-100.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(30.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslated(-0.8D, 0.8D, 0.3D);
         scale = 1.0D;
         GL11.glScaled(scale, scale, scale);
         GL11.glRotated(100.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-20.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(-0.2D, -0.4D, -0.6D);
         GL11.glScaled(0.8999999761581421D, 1.100000023841858D, 0.8999999761581421D);
      }

      if (par2 == ItemRenderType.INVENTORY) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(15.0D, 1.0D, 0.0D, 1.0D);
         GL11.glRotated(-45.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(45.0D, 0.0D, 0.0D, 1.0D);
         GL11.glTranslated(-0.4D, -0.1D, 0.2D);
         scale = 1.0D;
         GL11.glScaled(scale, scale, scale);
         GL11.glScaled(0.8999999761581421D, 1.100000023841858D, 0.8999999761581421D);
      }

      if (par2 == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.7D, -1.0D, -0.3D);
         scale = 1.0D;
         GL11.glScaled(scale, scale, scale);
         GL11.glRotated(-20.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-40.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(-0.2D, -0.0D, -0.1D);
         GL11.glRotated(-90.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(155.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(-0.3D, -0.3D, -0.1D);
         GL11.glRotated(20.0D, 0.0D, 0.0D, 1.0D);
         GL11.glScaled(0.8999999761581421D, 1.100000023841858D, 0.8999999761581421D);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      GL11.glPushMatrix();
      GL11.glTranslated(x - 44.0D, y + 3.0D, 20.0D);
      GL11.glRotated(50.0D, 0.0D, 0.0D, 1.0D);
      this.renderDisplayed(stack, 0.0D, 0.0D, 30.0D, false, 0.0D);
      GL11.glPopMatrix();
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x - 5.0D, y, 18.0D, false, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderDisplayed(stack, x - 40.0D, y + 50.0D, 100.0D, false, rot);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
   }

   public void renderDisplayed(ItemStack par1ItemStack, double posx, double posy, double scale, boolean tilted, double rotation) {
      Minecraft mc = Minecraft.getMinecraft();
      String v1 = ((ItemKnife)par1ItemStack.getItem()).getKnifeSkin(par1ItemStack).equals("") ? this.par1 : this.par1 + "_" + ((ItemKnife)par1ItemStack.getItem()).getKnifeSkin(par1ItemStack);
      mc.getTextureManager().bindTexture(new ResourceLocation(v1 + ".png"));
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(2929);
      GL11.glTranslated(posx + scale / 2.0D, posy + scale / 2.0D, 0.0D);
      GL11.glRotated(-rotation, 0.0D, 1.0D, 0.0D);
      GL11.glScaled(scale, -scale, scale);
      GL11.glRotated(10.0D, 0.0D, 0.0D, 1.0D);
      GL11.glTranslated(-0.3D, 1.15D, 0.0D);
      GL11.glRotated(-55.0D, 0.0D, 0.0D, 1.0D);
      double scale2 = 1.1D;
      GL11.glScaled(scale2, scale2, scale2);
      GL11.glScaled(0.949999988079071D, 1.0499999523162842D, 0.949999988079071D);
      super.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glDisable(2929);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }

   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslated(0.2D, 0.2D, 0.3D);
      } else {
         GL11.glTranslated(0.1D, 0.2D, -0.2D);
      }

   }

   public ModelBase getModel() {
      return new ModelKnifeKarambit();
   }
}
