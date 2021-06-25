package com.ferullogaming.countercraft.client.render;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.model.ModelCoinCase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderCoin implements IItemRenderer, IRenderOnGUI {
   protected ResourceLocation res = new ResourceLocation("countercraft:textures/models/coincase.png");
   protected ModelBase model = new ModelCoinCase();

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type != ItemRenderType.INVENTORY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      if (type == ItemRenderType.ENTITY) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(15.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslated(-0.8D, 0.0D, 0.0D);
         this.renderDisplayed(item, 0.0D, 0.0D, 0.8D, false, 0.0D);
         GL11.glEnable(2929);
      }

      if (type == ItemRenderType.EQUIPPED) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(10.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.0D, -0.3D, 0.0D);
         this.renderDisplayed(item, 0.0D, 0.0D, 0.5D, false, 0.0D);
         GL11.glEnable(2929);
      }

      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(5.0D, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(100.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(-0.2D, -0.5D, -0.5D);
         this.renderDisplayed(item, 0.0D, 0.0D, 0.5D, false, 0.0D);
         GL11.glEnable(2929);
      }

      if (type == ItemRenderType.INVENTORY) {
         this.renderDisplayed(item, 3.0D, 12.0D, 5.0D, false, 0.0D);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x - 7.0D, y - 0.15D, 8.5D, false, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderDisplayed(stack, x - 40.0D, y + 50.0D, 50.0D, false, rot);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
   }
   
	@Override
	public void renderSkins(ItemStack stack, double x, double y) {
		this.renderDisplayed(stack, x - 7.0D, y - 0.15D, 8.5D, false, 0.0D);
	}

   public void renderDisplayed(ItemStack par1ItemStack, double posx, double posy, double scale, boolean tilted, double rotation) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.getTextureManager().bindTexture(this.res);
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(2929);
      GL11.glTranslated(posx + scale / 2.0D, posy + scale / 2.0D, 0.0D);
      GL11.glScaled(scale, -scale, scale);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
      GL11.glTranslated(0.5D, -1.7D, 0.0D);
      GL11.glRotated(15.0D, 1.0D, 0.0D, 0.0D);
      GL11.glRotated(-160.0D, 0.0D, -1.0D, 0.0D);
      double scale2 = 1.5D;
      GL11.glScaled(scale2, scale2, scale2);
      GL11.glRotated(-rotation, 0.0D, 1.0D, 0.0D);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glRotated(15.0D, 1.0D, 0.0D, 0.0D);
      GL11.glScaled(1.0D, -1.0D, 1.0D);
      GL11.glTranslated(0.0D, 0.0D, 0.0D);
      CCRenderHelper.renderItemStackIngameStyle(par1ItemStack, 0.0D, -0.33D);
      GL11.glDisable(2929);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }
}
