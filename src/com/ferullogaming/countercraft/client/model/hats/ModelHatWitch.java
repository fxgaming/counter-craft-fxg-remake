package com.ferullogaming.countercraft.client.model.hats;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHatWitch extends ModelBase {
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape5;
   ModelRenderer Shape6;
   ModelRenderer Shape7;

   public ModelHatWitch() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Shape2 = new ModelRenderer(this, 40, 8);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
      this.Shape2.setRotationPoint(-5.0F, -5.0F, -5.0F);
      this.Shape2.setTextureSize(128, 64);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 40, 21);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
      this.Shape3.setRotationPoint(-3.5F, -9.0F, -3.5F);
      this.Shape3.setTextureSize(128, 64);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, -0.0523599F, 0.0F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 40, 33);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4);
      this.Shape4.setRotationPoint(-2.0F, -11.8F, -1.6F);
      this.Shape4.setTextureSize(128, 64);
      this.Shape4.mirror = true;
      this.setRotation(this.Shape4, -0.1396263F, 0.0F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 40, 51);
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
      this.Shape5.setRotationPoint(-1.0F, -13.6F, -0.2F);
      this.Shape5.setTextureSize(128, 64);
      this.Shape5.mirror = true;
      this.setRotation(this.Shape5, -0.2094395F, 0.0F, 0.0F);
      this.Shape6 = new ModelRenderer(this, 40, 41);
      this.Shape6.addBox(0.0F, 2.0F, 0.0F, 8, 1, 8);
      this.Shape6.setRotationPoint(-4.0F, -9.0F, -4.0F);
      this.Shape6.setTextureSize(128, 64);
      this.Shape6.mirror = true;
      this.setRotation(this.Shape6, -0.0523599F, 0.0F, 0.0F);
      this.Shape7 = new ModelRenderer(this, 40, 41);
      this.Shape7.addBox(0.0F, 1.5F, -0.2F, 2, 2, 1);
      this.Shape7.setRotationPoint(-1.0F, -9.0F, -4.0F);
      this.Shape7.setTextureSize(128, 64);
      this.Shape7.mirror = true;
      this.setRotation(this.Shape7, -0.0523599F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
      this.Shape6.render(f5);
      this.Shape7.render(f5);
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
