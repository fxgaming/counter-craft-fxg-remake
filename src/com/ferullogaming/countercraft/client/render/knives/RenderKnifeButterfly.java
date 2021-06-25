package com.ferullogaming.countercraft.client.render.knives;

import com.ferullogaming.countercraft.client.model.knives.ModelKnifeButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderKnifeButterfly extends RenderKnife {
   public RenderKnifeButterfly(String par1) {
      super(par1);
   }

   public void renderItemValues(ItemStack par1, ItemRenderType par2) {
      double scale;
      if (par2 == ItemRenderType.ENTITY) {
         GL11.glRotated(90.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
         scale = 0.5D;
         GL11.glScaled(scale, scale, scale);
      }

      if (par2 == ItemRenderType.EQUIPPED) {
         GL11.glRotated(-100.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(30.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslated(-0.8D, 0.8D, 0.3D);
         scale = 0.8D;
         GL11.glScaled(scale, scale, scale);
         GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(100.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(-0.1D, -0.3D, 0.1D);
         GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      }

      if (par2 == ItemRenderType.INVENTORY) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(15.0D, 1.0D, 0.0D, 1.0D);
         GL11.glRotated(-45.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(45.0D, 0.0D, 0.0D, 1.0D);
         GL11.glTranslated(-0.2D, 0.15D, 0.4D);
         scale = 0.9D;
         GL11.glScaled(scale, scale, scale);
      }

      if (par2 == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.5D, -1.0D, -0.5D);
         scale = 1.0D;
         GL11.glScaled(scale, scale, scale);
         GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-40.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(100.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.0D, -0.3D, 0.0D);
         GL11.glRotated(180.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      }

   }

   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      } else {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      GL11.glPushMatrix();
      GL11.glTranslated(x - 25.0D, y + 12.0D, 20.0D);
      GL11.glRotated(-35.0D, 0.0D, 0.0D, 1.0D);
      this.renderDisplayed(stack, 0.0D, 0.0D, 35.0D, false, 0.0D);
      GL11.glPopMatrix();
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x, y - 2.0D, 17.0D, false, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderDisplayed(stack, x, y + 40.0D, 100.0D, false, rot);
   }

   public ModelBase getModel() {
      return new ModelKnifeButterfly();
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
   }
}
