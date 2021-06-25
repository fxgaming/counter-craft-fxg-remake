package com.ferullogaming.countercraft.client.model.hats;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHatHardhat extends ModelBase {
   ModelRenderer Shape1;
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape6;

   public ModelHatHardhat() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 9, 5, 9);
      this.Shape1.setRotationPoint(-4.0F, -4.0F, -4.0F);
      this.Shape1.setTextureSize(64, 32);
      this.Shape1.mirror = true;
      this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
      this.Shape2 = new ModelRenderer(this, 37, 0);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 7, 1, 3);
      this.Shape2.setRotationPoint(-3.0F, 0.0F, -7.0F);
      this.Shape2.setTextureSize(64, 32);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0872665F, 0.0F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 0, 15);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 3, 5, 1);
      this.Shape3.setRotationPoint(-1.0F, -5.0F, -5.0F);
      this.Shape3.setTextureSize(64, 32);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 9, 15);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 3, 1, 9);
      this.Shape4.setRotationPoint(-1.0F, -5.0F, -4.0F);
      this.Shape4.setTextureSize(64, 32);
      this.Shape4.mirror = true;
      this.setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
      this.Shape6 = new ModelRenderer(this, 0, 22);
      this.Shape6.addBox(0.0F, 0.0F, -1.0F, 3, 5, 1);
      this.Shape6.setRotationPoint(-1.0F, -5.0F, 6.0F);
      this.Shape6.setTextureSize(64, 32);
      this.Shape6.mirror = true;
      this.setRotation(this.Shape6, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape1.render(f5);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape6.render(f5);
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
