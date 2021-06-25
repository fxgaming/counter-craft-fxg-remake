package com.ferullogaming.countercraft.client.render.knives;

import com.ferullogaming.countercraft.client.model.knives.ModelKnifeBayonet;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderKnifeBayonet extends RenderKnife {
   public RenderKnifeBayonet(String par1) {
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
         scale = 1.0D;
         GL11.glScaled(scale, scale, scale);
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
         GL11.glRotated(-20.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-40.0D, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(-10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(-0.2D, -0.0D, -0.1D);
      }

   }

   public ModelBase getModel() {
      return new ModelKnifeBayonet();
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
   }
}
