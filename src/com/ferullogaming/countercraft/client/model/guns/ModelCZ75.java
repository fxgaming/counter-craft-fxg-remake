package com.ferullogaming.countercraft.client.model.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCZ75 extends ModelGun {
   ModelRenderer Shape1;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape5;
   ModelRenderer Shape6;
   ModelRenderer Shape7;
   ModelRenderer Shape8;
   ModelRenderer Shape9;
   ModelRenderer Shape10;
   ModelRenderer Shape11;
   ModelRenderer Shape12;
   ModelRenderer Shape13;
   ModelRenderer Shape14;

   public ModelCZ75() {
      super.textureWidth = 64;
      super.textureHeight = 128;
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Shape1.setRotationPoint(-4.0F, -0.5F, 0.0F);
      this.Shape1.setTextureSize(64, 128);
      this.Shape1.mirror = true;
      this.setRotation(this.Shape1, 0.0F, 0.0F, 0.2268928F);
      this.Shape2 = new ModelRenderer(this, 0, 10);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 2);
      this.Shape2.setRotationPoint(-4.0F, -2.0F, 0.0F);
      this.Shape2.setTextureSize(64, 128);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 0, 20);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Shape3.setRotationPoint(2.0F, -2.0F, 0.0F);
      this.Shape3.setTextureSize(64, 128);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 0, 30);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Shape4.setRotationPoint(2.0F, -1.2F, 0.5F);
      this.Shape4.setTextureSize(64, 128);
      this.Shape4.mirror = true;
      this.setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 0, 40);
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Shape5.setRotationPoint(-2.9F, 1.1F, 0.5F);
      this.Shape5.setTextureSize(64, 128);
      this.Shape5.mirror = true;
      this.setRotation(this.Shape5, 0.0F, 0.0F, -0.3141593F);
      this.Shape6 = new ModelRenderer(this, 0, 50);
      this.Shape6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Shape6.setRotationPoint(-1.1F, -0.8F, 0.5F);
      this.Shape6.setTextureSize(64, 128);
      this.Shape6.mirror = true;
      this.setRotation(this.Shape6, 0.0F, 0.0F, 0.2617994F);
      this.Shape7 = new ModelRenderer(this, 0, 60);
      this.Shape7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape7.setRotationPoint(-2.8F, 0.2F, 0.5F);
      this.Shape7.setTextureSize(64, 128);
      this.Shape7.mirror = true;
      this.setRotation(this.Shape7, 0.0F, 0.0F, -1.064651F);
      this.Shape8 = new ModelRenderer(this, 0, 70);
      this.Shape8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
      this.Shape8.setRotationPoint(-4.6F, 0.0F, 0.0F);
      this.Shape8.setTextureSize(64, 128);
      this.Shape8.mirror = true;
      this.setRotation(this.Shape8, 0.0F, 0.0F, -0.9599311F);
      this.Shape9 = new ModelRenderer(this, 0, 80);
      this.Shape9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape9.setRotationPoint(-4.4F, -1.4F, 0.5F);
      this.Shape9.setTextureSize(64, 128);
      this.Shape9.mirror = true;
      this.setRotation(this.Shape9, 0.0F, 0.0F, -0.6108652F);
      this.Shape10 = new ModelRenderer(this, 0, 90);
      this.Shape10.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Shape10.setRotationPoint(-3.5F, -0.4F, -0.2F);
      this.Shape10.setTextureSize(64, 128);
      this.Shape10.mirror = true;
      this.setRotation(this.Shape10, 0.0F, 0.0F, 0.2268928F);
      this.Shape11 = new ModelRenderer(this, 0, 100);
      this.Shape11.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Shape11.setRotationPoint(-3.5F, -0.4F, 1.2F);
      this.Shape11.setTextureSize(64, 128);
      this.Shape11.mirror = true;
      this.setRotation(this.Shape11, 0.0F, 0.0F, 0.2268928F);
      this.Shape12 = new ModelRenderer(this, 0, 110);
      this.Shape12.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Shape12.setRotationPoint(4.0F, -1.8F, 0.5F);
      this.Shape12.setTextureSize(64, 128);
      this.Shape12.mirror = true;
      this.setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
      this.Shape13 = new ModelRenderer(this, 40, 0);
      this.Shape13.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Shape13.setRotationPoint(0.2F, -0.7F, 0.5F);
      this.Shape13.setTextureSize(64, 128);
      this.Shape13.mirror = true;
      this.setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
      this.Shape14 = new ModelRenderer(this, 40, 10);
      this.Shape14.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Shape14.setRotationPoint(0.6F, 0.0F, 0.5F);
      this.Shape14.setTextureSize(64, 128);
      this.Shape14.mirror = true;
      this.setRotation(this.Shape14, 0.0F, 0.0F, -0.3141593F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
      this.Shape6.render(f5);
      this.Shape7.render(f5);
      this.Shape8.render(f5);
      this.Shape9.render(f5);
      this.Shape10.render(f5);
      this.Shape11.render(f5);
      this.Shape12.render(f5);
      this.Shape13.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
   }

   public void renderAmmo(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape14.render(f5);
   }
}
