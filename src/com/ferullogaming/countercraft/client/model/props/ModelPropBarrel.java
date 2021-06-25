package com.ferullogaming.countercraft.client.model.props;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPropBarrel extends ModelBase {
   ModelRenderer Box_0;
   ModelRenderer Box_1;
   ModelRenderer Box_2;

   public ModelPropBarrel() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Box_0 = new ModelRenderer(this, 1, 1);
      this.Box_0.addBox(0.0F, 0.0F, 0.0F, 12, 22, 14);
      this.Box_0.setRotationPoint(-6.0F, -22.0F, -7.0F);
      this.Box_0.setTextureSize(64, 32);
      this.Box_0.mirror = true;
      this.setRotation(this.Box_0, 0.0F, 0.0F, 0.0F);
      this.Box_1 = new ModelRenderer(this, 57, 1);
      this.Box_1.addBox(0.0F, 0.0F, 0.0F, 1, 22, 12);
      this.Box_1.setRotationPoint(-7.0F, -22.0F, -6.0F);
      this.Box_1.setTextureSize(64, 32);
      this.Box_1.mirror = true;
      this.setRotation(this.Box_1, 0.0F, 0.0F, 0.0F);
      this.Box_2 = new ModelRenderer(this, 89, 1);
      this.Box_2.addBox(0.0F, 0.0F, 0.0F, 1, 22, 12);
      this.Box_2.setRotationPoint(6.0F, -22.0F, -6.0F);
      this.Box_2.setTextureSize(64, 32);
      this.Box_2.mirror = true;
      this.setRotation(this.Box_2, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f5, f, f1, f2, f3, f4, entity);
      this.Box_0.render(f5);
      this.Box_1.render(f5);
      this.Box_2.render(f5);
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
