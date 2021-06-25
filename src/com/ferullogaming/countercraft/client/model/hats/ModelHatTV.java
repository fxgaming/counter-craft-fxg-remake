package com.ferullogaming.countercraft.client.model.hats;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHatTV extends ModelBase {
   ModelRenderer Shape2;
   ModelRenderer Shape3;
   ModelRenderer Shape4;
   ModelRenderer Shape5;

   public ModelHatTV() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Shape2 = new ModelRenderer(this, 0, 17);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 9, 8, 9);
      this.Shape2.setRotationPoint(-4.5F, -0.5F, -4.5F);
      this.Shape2.setTextureSize(128, 64);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 0, 35);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 3, 1, 5);
      this.Shape3.setRotationPoint(-1.0F, -1.5F, -2.5F);
      this.Shape3.setTextureSize(128, 64);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 0, 42);
      this.Shape4.addBox(0.0F, -5.0F, 0.0F, 1, 5, 1);
      this.Shape4.setRotationPoint(0.0F, -1.5F, 1.0F);
      this.Shape4.setTextureSize(128, 64);
      this.Shape4.mirror = true;
      this.setRotation(this.Shape4, -0.3490659F, 0.0F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 0, 42);
      this.Shape5.addBox(0.0F, -5.0F, -1.0F, 1, 5, 1);
      this.Shape5.setRotationPoint(0.0F, -1.5F, -1.0F);
      this.Shape5.setTextureSize(128, 64);
      this.Shape5.mirror = true;
      this.setRotation(this.Shape5, 0.3490659F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape2.render(f5);
      this.Shape3.render(f5);
      this.Shape4.render(f5);
      this.Shape5.render(f5);
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
