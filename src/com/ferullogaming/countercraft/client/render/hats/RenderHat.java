package com.ferullogaming.countercraft.client.render.hats;

import com.ferullogaming.countercraft.client.render.IRenderOnGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public abstract class RenderHat implements IItemRenderer, IRenderOnGUI {
   protected ResourceLocation res;
   protected ModelBase model = this.getModel();

   public RenderHat(String par1) {
      this.res = new ResourceLocation("countercraft:textures/models/hats/" + par1 + ".png");
   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type != ItemRenderType.FIRST_PERSON_MAP;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(this.res);
      EntityPlayer entityplayer = null;
      if (type == ItemRenderType.EQUIPPED) {
         entityplayer = (EntityPlayer)data[1];
      }

      GL11.glPushMatrix();
      this.renderItemValues(item, type);
      this.model.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
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
      GL11.glTranslated(0.5D, -0.6D, 0.0D);
      GL11.glRotated(15.0D, 1.0D, 0.0D, 0.0D);
      GL11.glRotated(-35.0D, 0.0D, -1.0D, 0.0D);
      double scale2 = 1.5D;
      GL11.glScaled(scale2, scale2, scale2);
      GL11.glRotated(-rotation, 0.0D, 1.0D, 0.0D);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glDisable(2929);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }

   public abstract void renderItemValues(ItemStack var1, ItemRenderType var2);

   public abstract void renderOnHead(ItemStack var1);

   public abstract ModelBase getModel();

   public void renderOnHUD(ItemStack stack, double x, double y) {
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x - 18.0D, y - 7.0D, 18.0D, false, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderDisplayed(stack, x - 100.0D, y + 10.0D, 100.0D, false, rot);
   }
   
   @Override
   public void renderSkins(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x - 100.0D, y + 10.0D, 100.0D, false, 0.0D);
   }
}
