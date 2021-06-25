package com.ferullogaming.countercraft.client.render.hats;

import com.ferullogaming.countercraft.client.model.hats.ModelHatTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderHatTV extends RenderHat {
   public RenderHatTV() {
      super("tv");
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x - 16.0D, y - 8.0D, 16.0D, false, -90.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderDisplayed(stack, x - 100.0D, y + 10.0D, 100.0D, false, -90.0D + rot);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
   }

   public void renderItemValues(ItemStack par1, ItemRenderType par2) {
      double scale;
      if (par2 == ItemRenderType.ENTITY || par2 == ItemRenderType.INVENTORY) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.0D, -0.2D, 0.0D);
         scale = 1.1D;
         GL11.glScaled(scale, scale, scale);
         GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
      }

      if (par2 == ItemRenderType.EQUIPPED || par2 == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.5D, -1.2D, -0.5D);
         scale = 1.3D;
         GL11.glScaled(scale, scale, scale);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      }

   }

   public void renderOnHead(ItemStack par1) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(super.res);
      GL11.glTranslated(0.0D, -0.5D, 0.0D);
      GL11.glRotated(90.0D, 0.0D, 1.0D, 0.0D);
      double scale = 1.001D;
      GL11.glScaled(scale, scale, scale);
      super.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
   }

   public ModelBase getModel() {
      return new ModelHatTV();
   }
}
