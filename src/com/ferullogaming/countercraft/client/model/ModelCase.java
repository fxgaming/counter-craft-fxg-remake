package com.ferullogaming.countercraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCase extends ModelBase {
   ModelRenderer Part_0;
   ModelRenderer Part_1;
   ModelRenderer Part_2;
   ModelRenderer Part_3;
   ModelRenderer Part_4;
   ModelRenderer Part_5;
   ModelRenderer Part_6;
   ModelRenderer Part_7;

   public ModelCase() {
      super.textureWidth = 128;
      super.textureHeight = 128;
      this.Part_0 = new ModelRenderer(this, 1, 1);
      this.Part_0.addBox(0.0F, 0.0F, 0.0F, 16, 10, 11);
      this.Part_0.setRotationPoint(0.0F, -1.0F, 0.0F);
      this.Part_0.setTextureSize(128, 128);
      this.Part_0.mirror = true;
      this.setRotation(this.Part_0, 0.0F, 0.0F, 0.0F);
      this.Part_1 = new ModelRenderer(this, 57, 1);
      this.Part_1.addBox(0.0F, 0.0F, 0.0F, 17, 1, 12);
      this.Part_1.setRotationPoint(-0.5F, 0.0F, -0.5F);
      this.Part_1.setTextureSize(128, 128);
      this.Part_1.mirror = true;
      this.setRotation(this.Part_1, 0.0F, 0.0F, 0.0F);
      this.Part_2 = new ModelRenderer(this, 49, 17);
      this.Part_2.addBox(0.0F, 0.0F, 0.0F, 1, 10, 12);
      this.Part_2.setRotationPoint(1.0F, -1.0F, -0.5F);
      this.Part_2.setTextureSize(128, 128);
      this.Part_2.mirror = true;
      this.setRotation(this.Part_2, 0.0F, 0.0F, 0.0F);
      this.Part_3 = new ModelRenderer(this, 81, 17);
      this.Part_3.addBox(0.0F, 0.0F, 0.0F, 1, 10, 12);
      this.Part_3.setRotationPoint(3.0F, -1.0F, -0.5F);
      this.Part_3.setTextureSize(128, 128);
      this.Part_3.mirror = true;
      this.setRotation(this.Part_3, 0.0F, 0.0F, 0.0F);
      this.Part_4 = new ModelRenderer(this, 1, 25);
      this.Part_4.addBox(0.0F, 0.0F, 0.0F, 1, 10, 12);
      this.Part_4.setRotationPoint(14.0F, -1.0F, -0.5F);
      this.Part_4.setTextureSize(128, 128);
      this.Part_4.mirror = true;
      this.setRotation(this.Part_4, 0.0F, 0.0F, 0.0F);
      this.Part_5 = new ModelRenderer(this, 33, 33);
      this.Part_5.addBox(0.0F, 0.0F, 0.0F, 1, 10, 12);
      this.Part_5.setRotationPoint(12.0F, -1.0F, -0.5F);
      this.Part_5.setTextureSize(128, 128);
      this.Part_5.mirror = true;
      this.setRotation(this.Part_5, 0.0F, 0.0F, 0.0F);
      this.Part_6 = new ModelRenderer(this, 1, 1);
      this.Part_6.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.Part_6.setRotationPoint(-0.2F, -0.5F, 11.2F);
      this.Part_6.setTextureSize(128, 128);
      this.Part_6.mirror = true;
      this.setRotation(this.Part_6, 0.0F, 0.7853982F, 0.0F);
      this.Part_7 = new ModelRenderer(this, 49, 1);
      this.Part_7.addBox(-0.5F, 0.0F, -1.0F, 1, 2, 2);
      this.Part_7.setRotationPoint(-0.4F, 0.5F, 11.4F);
      this.Part_7.setTextureSize(128, 128);
      this.Part_7.mirror = true;
      this.setRotation(this.Part_7, 0.0F, 0.7853982F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Part_0.render(f5);
      this.Part_1.render(f5);
      this.Part_2.render(f5);
      this.Part_3.render(f5);
      this.Part_4.render(f5);
      this.Part_5.render(f5);
      this.Part_6.render(f5);
      this.Part_7.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
   }
}
