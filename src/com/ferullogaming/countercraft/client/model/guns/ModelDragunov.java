package com.ferullogaming.countercraft.client.model.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDragunov extends ModelGun {
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
   ModelRenderer Shape15;
   ModelRenderer Shape16;
   ModelRenderer Shape17;
   ModelRenderer Shape18;
   ModelRenderer Shape19;
   ModelRenderer Shape20;

   public ModelDragunov() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Shape1 = new ModelRenderer(this, 60, 34);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 9, 3, 2);
      this.Shape1.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.Shape1.setTextureSize(128, 64);
      this.Shape1.mirror = true;
      this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
      this.Shape2 = new ModelRenderer(this, 35, 34);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 10, 3, 2);
      this.Shape2.setRotationPoint(5.0F, -0.5F, 0.0F);
      this.Shape2.setTextureSize(128, 64);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 26, 34);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Shape3.setRotationPoint(15.0F, 0.0F, 0.5F);
      this.Shape3.setTextureSize(128, 64);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 60, 31);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1);
      this.Shape4.setRotationPoint(-3.0F, -0.5F, 0.5F);
      this.Shape4.setTextureSize(128, 64);
      this.Shape4.mirror = true;
      this.setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 60, 40);
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 4, 3, 2);
      this.Shape5.setRotationPoint(1.0F, 3.0F, 0.0F);
      this.Shape5.setTextureSize(128, 64);
      this.Shape5.mirror = true;
      this.setRotation(this.Shape5, 0.0F, 0.0F, -0.0872665F);
      this.Shape6 = new ModelRenderer(this, 80, 40);
      this.Shape6.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 2);
      this.Shape6.setRotationPoint(-2.0F, 3.0F, 0.0F);
      this.Shape6.setTextureSize(128, 64);
      this.Shape6.mirror = true;
      this.setRotation(this.Shape6, 0.0F, 0.0F, 0.2792527F);
      this.Shape7 = new ModelRenderer(this, 73, 40);
      this.Shape7.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Shape7.setRotationPoint(-2.5F, 3.0F, 0.5F);
      this.Shape7.setTextureSize(128, 64);
      this.Shape7.mirror = true;
      this.setRotation(this.Shape7, 0.0F, 0.0F, 0.0F);
      this.Shape8 = new ModelRenderer(this, 83, 34);
      this.Shape8.addBox(0.0F, 0.0F, 0.0F, 8, 2, 2);
      this.Shape8.setRotationPoint(-12.0F, 1.0F, 0.0F);
      this.Shape8.setTextureSize(128, 64);
      this.Shape8.mirror = true;
      this.setRotation(this.Shape8, 0.0F, 0.0F, 0.0F);
      this.Shape9 = new ModelRenderer(this, 102, 40);
      this.Shape9.addBox(0.0F, 0.0F, 0.0F, 4, 4, 2);
      this.Shape9.setRotationPoint(-12.0F, 3.0F, 0.0F);
      this.Shape9.setTextureSize(128, 64);
      this.Shape9.mirror = true;
      this.setRotation(this.Shape9, 0.0F, 0.0F, 0.0F);
      this.Shape10 = new ModelRenderer(this, 89, 43);
      this.Shape10.addBox(0.0F, -1.0F, 0.0F, 4, 1, 2);
      this.Shape10.setRotationPoint(-8.0F, 7.0F, 0.0F);
      this.Shape10.setTextureSize(128, 64);
      this.Shape10.mirror = true;
      this.setRotation(this.Shape10, 0.0F, 0.0F, -0.1047198F);
      this.Shape11 = new ModelRenderer(this, 3, 37);
      this.Shape11.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1);
      this.Shape11.setRotationPoint(18.0F, 1.0F, 0.5F);
      this.Shape11.setTextureSize(128, 64);
      this.Shape11.mirror = true;
      this.setRotation(this.Shape11, 0.0F, 0.0F, 0.0F);
      this.Shape12 = new ModelRenderer(this, 6, 33);
      this.Shape12.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Shape12.setRotationPoint(25.0F, -0.5F, 0.5F);
      this.Shape12.setTextureSize(128, 64);
      this.Shape12.mirror = true;
      this.setRotation(this.Shape12, 0.0F, 0.0F, 0.0F);
      this.Shape13 = new ModelRenderer(this, 87, 31);
      this.Shape13.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.Shape13.setRotationPoint(-11.0F, 0.5F, 0.5F);
      this.Shape13.setTextureSize(128, 64);
      this.Shape13.mirror = true;
      this.setRotation(this.Shape13, 0.0F, 0.0F, 0.0F);
      this.Shape14 = new ModelRenderer(this, 19, 34);
      this.Shape14.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Shape14.setRotationPoint(18.0F, 0.0F, 0.5F);
      this.Shape14.setTextureSize(128, 64);
      this.Shape14.mirror = true;
      this.setRotation(this.Shape14, 0.0F, 0.0F, 0.5585054F);
      this.Shape15 = new ModelRenderer(this, 39, 40);
      this.Shape15.addBox(0.0F, -1.0F, 0.0F, 9, 1, 1);
      this.Shape15.setRotationPoint(5.0F, 3.0F, 0.5F);
      this.Shape15.setTextureSize(128, 64);
      this.Shape15.mirror = true;
      this.setRotation(this.Shape15, 0.0F, 0.0F, -0.0523599F);
      this.Shape16 = new ModelRenderer(this, 62, 23);
      this.Shape16.addBox(0.0F, 0.0F, 0.0F, 7, 1, 1);
      this.Shape16.setRotationPoint(-2.0F, -2.5F, 0.5F);
      this.Shape16.setTextureSize(128, 64);
      this.Shape16.mirror = true;
      this.setRotation(this.Shape16, 0.0F, 0.0F, 0.0F);
      this.Shape17 = new ModelRenderer(this, 74, 26);
      this.Shape17.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape17.setRotationPoint(-0.5F, -1.5F, 0.5F);
      this.Shape17.setTextureSize(128, 64);
      this.Shape17.mirror = true;
      this.setRotation(this.Shape17, 0.0F, 0.0F, 0.0F);
      this.Shape18 = new ModelRenderer(this, 62, 26);
      this.Shape18.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Shape18.setRotationPoint(3.0F, -1.5F, 0.5F);
      this.Shape18.setTextureSize(128, 64);
      this.Shape18.mirror = true;
      this.setRotation(this.Shape18, 0.0F, 0.0F, 0.0F);
      this.Shape19 = new ModelRenderer(this, 49, 23);
      this.Shape19.addBox(0.0F, 0.0F, 0.0F, 4, 2, 2);
      this.Shape19.setRotationPoint(5.0F, -3.0F, 0.0F);
      this.Shape19.setTextureSize(128, 64);
      this.Shape19.mirror = true;
      this.setRotation(this.Shape19, 0.0F, 0.0F, 0.0F);
      this.Shape20 = new ModelRenderer(this, 79, 23);
      this.Shape20.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
      this.Shape20.setRotationPoint(-3.5F, -3.0F, 0.0F);
      this.Shape20.setTextureSize(128, 64);
      this.Shape20.mirror = true;
      this.setRotation(this.Shape20, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape6.render(f5);
      this.Shape7.render(f5);
      this.Shape8.render(f5);
      this.Shape9.render(f5);
      this.Shape10.render(f5);
      this.Shape11.render(f5);
      this.Shape12.render(f5);
      this.Shape13.render(f5);
      this.Shape14.render(f5);
      this.Shape15.render(f5);
      this.Shape16.render(f5);
      this.Shape17.render(f5);
      this.Shape18.render(f5);
      this.Shape19.render(f5);
      this.Shape20.render(f5);
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
      this.Shape5.render(f5);
   }
}
