package com.ferullogaming.countercraft.client.model.knives;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKnifeKarambit extends ModelBase {
   ModelRenderer Shape1;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
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
   ModelRenderer Shape17;
   ModelRenderer Shape18;
   ModelRenderer Shape19;
   ModelRenderer Shape21;
   ModelRenderer Shape22;
   ModelRenderer Shape23;
   ModelRenderer Shape24;
   ModelRenderer Shape26;
   ModelRenderer Shape15;

   public ModelKnifeKarambit() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Shape1 = new ModelRenderer(this, 48, 40);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 2);
      this.Shape1.setRotationPoint(-0.5F, 0.0F, 0.0F);
      this.Shape1.setTextureSize(128, 64);
      this.Shape1.mirror = true;
      this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
      this.Shape2 = new ModelRenderer(this, 57, 35);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1);
      this.Shape2.setRotationPoint(0.09F, -3.0F, 0.5F);
      this.Shape2.setTextureSize(128, 64);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0F, 0.0F, 0.1396263F);
      this.Shape3 = new ModelRenderer(this, 50, 45);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 5, 1, 2);
      this.Shape3.setRotationPoint(-0.2F, 1.5F, 0.0F);
      this.Shape3.setTextureSize(128, 64);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 55, 30);
      this.Shape5.addBox(0.0F, -3.0F, 0.0F, 4, 3, 1);
      this.Shape5.setRotationPoint(0.09F, -3.0F, 0.5F);
      this.Shape5.setTextureSize(128, 64);
      this.Shape5.mirror = true;
      this.setRotation(this.Shape5, 0.0F, 0.0F, 0.3316126F);
      this.Shape6 = new ModelRenderer(this, 55, 25);
      this.Shape6.addBox(0.0F, -3.0F, 0.0F, 3, 3, 1);
      this.Shape6.setRotationPoint(1.07F, -5.85F, 0.5F);
      this.Shape6.setTextureSize(128, 64);
      this.Shape6.mirror = true;
      this.setRotation(this.Shape6, 0.0F, 0.0F, 0.6806784F);
      this.Shape7 = new ModelRenderer(this, 55, 21);
      this.Shape7.addBox(0.0F, -2.0F, 0.0F, 2, 2, 1);
      this.Shape7.setRotationPoint(2.95F, -8.2F, 0.5F);
      this.Shape7.setTextureSize(128, 64);
      this.Shape7.mirror = true;
      this.setRotation(this.Shape7, 0.0F, 0.0F, 1.064651F);
      this.Shape8 = new ModelRenderer(this, 55, 17);
      this.Shape8.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
      this.Shape8.setRotationPoint(4.64F, -9.16F, 0.5F);
      this.Shape8.setTextureSize(128, 64);
      this.Shape8.mirror = true;
      this.setRotation(this.Shape8, 0.0F, 0.0F, 1.396263F);
      this.Shape9 = new ModelRenderer(this, 50, 36);
      this.Shape9.addBox(-2.0F, -2.0F, 0.0F, 2, 2, 1);
      this.Shape9.setRotationPoint(5.1F, 0.0F, 0.5F);
      this.Shape9.setTextureSize(128, 64);
      this.Shape9.mirror = true;
      this.setRotation(this.Shape9, 0.0F, 0.0F, -0.122173F);
      this.Shape10 = new ModelRenderer(this, 50, 31);
      this.Shape10.addBox(-1.0F, -2.0F, 0.0F, 1, 3, 1);
      this.Shape10.setRotationPoint(4.9F, -2.0F, 0.5F);
      this.Shape10.setTextureSize(128, 64);
      this.Shape10.mirror = true;
      this.setRotation(this.Shape10, 0.0F, 0.0F, 0.0F);
      this.Shape11 = new ModelRenderer(this, 50, 27);
      this.Shape11.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
      this.Shape11.setRotationPoint(3.9F, -4.0F, 0.5F);
      this.Shape11.setTextureSize(128, 64);
      this.Shape11.mirror = true;
      this.setRotation(this.Shape11, 0.0F, 0.0F, 0.1745329F);
      this.Shape12 = new ModelRenderer(this, 50, 22);
      this.Shape12.addBox(-1.0F, -2.0F, 0.0F, 1, 3, 1);
      this.Shape12.setRotationPoint(5.25F, -5.8F, 0.5F);
      this.Shape12.setTextureSize(128, 64);
      this.Shape12.mirror = true;
      this.setRotation(this.Shape12, 0.0F, 0.0F, 0.4363323F);
      this.Shape13 = new ModelRenderer(this, 50, 18);
      this.Shape13.addBox(-1.0F, -1.0F, 0.0F, 1, 2, 1);
      this.Shape13.setRotationPoint(6.12F, -7.6F, 0.5F);
      this.Shape13.setTextureSize(128, 64);
      this.Shape13.mirror = true;
      this.setRotation(this.Shape13, 0.0F, 0.0F, 0.715585F);
      this.Shape14 = new ModelRenderer(this, 50, 15);
      this.Shape14.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape14.setRotationPoint(6.6F, -9.52F, 0.5F);
      this.Shape14.setTextureSize(128, 64);
      this.Shape14.mirror = true;
      this.setRotation(this.Shape14, 0.0F, 0.0F, 0.837758F);
      this.Shape17 = new ModelRenderer(this, 38, 40);
      this.Shape17.addBox(0.5F, 0.0F, 0.0F, 3, 1, 1);
      this.Shape17.setRotationPoint(3.2F, 10.3F, 0.5F);
      this.Shape17.setTextureSize(128, 64);
      this.Shape17.mirror = true;
      this.setRotation(this.Shape17, 0.0F, 0.0F, -0.7679449F);
      this.Shape18 = new ModelRenderer(this, 71, 50);
      this.Shape18.addBox(0.0F, 0.0F, 0.0F, 3, 3, 2);
      this.Shape18.setRotationPoint(2.0F, 7.5F, 0.0F);
      this.Shape18.setTextureSize(128, 64);
      this.Shape18.mirror = true;
      this.setRotation(this.Shape18, 0.0F, 0.0F, -0.6457718F);
      this.Shape19 = new ModelRenderer(this, 60, 50);
      this.Shape19.addBox(0.0F, -2.0F, 0.0F, 3, 6, 2);
      this.Shape19.setRotationPoint(0.7F, 4.0F, 0.0F);
      this.Shape19.setTextureSize(128, 64);
      this.Shape19.mirror = true;
      this.setRotation(this.Shape19, 0.0F, 0.0F, -0.3665191F);
      this.Shape21 = new ModelRenderer(this, 38, 48);
      this.Shape21.addBox(0.5F, 3.0F, 0.0F, 3, 1, 1);
      this.Shape21.setRotationPoint(3.2F, 10.3F, 0.5F);
      this.Shape21.setTextureSize(128, 64);
      this.Shape21.mirror = true;
      this.setRotation(this.Shape21, 0.0F, 0.0F, -0.7679449F);
      this.Shape22 = new ModelRenderer(this, 43, 43);
      this.Shape22.addBox(0.0F, 0.5F, 0.0F, 1, 3, 1);
      this.Shape22.setRotationPoint(3.2F, 10.3F, 0.5F);
      this.Shape22.setTextureSize(128, 64);
      this.Shape22.mirror = true;
      this.setRotation(this.Shape22, 0.0F, 0.0F, -0.7679449F);
      this.Shape23 = new ModelRenderer(this, 37, 43);
      this.Shape23.addBox(3.0F, 0.5F, 0.0F, 1, 3, 1);
      this.Shape23.setRotationPoint(3.2F, 10.3F, 0.5F);
      this.Shape23.setTextureSize(128, 64);
      this.Shape23.mirror = true;
      this.setRotation(this.Shape23, 0.0F, 0.0F, -0.7679449F);
      this.Shape24 = new ModelRenderer(this, 71, 56);
      this.Shape24.addBox(0.0F, -0.3F, 0.0F, 1, 2, 2);
      this.Shape24.setRotationPoint(3.0F, 9.0F, 0.0F);
      this.Shape24.setTextureSize(128, 64);
      this.Shape24.mirror = true;
      this.setRotation(this.Shape24, 0.0F, 0.0F, -0.2443461F);
      this.Shape26 = new ModelRenderer(this, 53, 50);
      this.Shape26.addBox(-1.0F, 0.0F, 0.0F, 1, 2, 2);
      this.Shape26.setRotationPoint(4.3F, 2.4F, 0.0F);
      this.Shape26.setTextureSize(128, 64);
      this.Shape26.mirror = true;
      this.setRotation(this.Shape26, 0.0F, 0.0F, 0.1570796F);
      this.Shape15 = new ModelRenderer(this, 53, 11);
      this.Shape15.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape15.setRotationPoint(5.0F, -9.0F, 0.5F);
      this.Shape15.setTextureSize(128, 64);
      this.Shape15.mirror = true;
      this.setRotation(this.Shape15, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape5.render(f5);
      this.Shape6.render(f5);
      this.Shape7.render(f5);
      this.Shape8.render(f5);
      this.Shape9.render(f5);
      this.Shape10.render(f5);
      this.Shape11.render(f5);
      this.Shape12.render(f5);
      this.Shape13.render(f5);
      this.Shape14.render(f5);
      this.Shape17.render(f5);
      this.Shape18.render(f5);
      this.Shape19.render(f5);
      this.Shape21.render(f5);
      this.Shape22.render(f5);
      this.Shape23.render(f5);
      this.Shape24.render(f5);
      this.Shape26.render(f5);
      this.Shape15.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
   }
}
