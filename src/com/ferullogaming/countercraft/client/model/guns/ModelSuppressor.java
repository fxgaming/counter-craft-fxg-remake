package com.ferullogaming.countercraft.client.model.guns;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSuppressor extends ModelBase {
   ModelRenderer Shape0;

   public ModelSuppressor() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Shape0 = new ModelRenderer(this, 1, 1);
      this.Shape0.addBox(0.0F, -2.0F, -2.0F, 16, 4, 4);
      this.Shape0.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Shape0.setTextureSize(64, 32);
      this.Shape0.mirror = true;
      this.setRotation(this.Shape0, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Shape0.render(f5);
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
