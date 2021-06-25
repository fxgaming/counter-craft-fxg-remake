package com.ferullogaming.countercraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelStickerCapsule extends ModelBase {
   ModelRenderer Part_0;
   ModelRenderer Part_1;
   ModelRenderer Part_2;
   ModelRenderer Part_3;
   ModelRenderer Part_4;
   ModelRenderer Part_5;
   ModelRenderer Part_6;

   public ModelStickerCapsule() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Part_0 = new ModelRenderer(this, 22, 6);
      this.Part_0.addBox(0.0F, 0.0F, 0.0F, 1, 6, 4);
      this.Part_0.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Part_0.setTextureSize(512, 512);
      this.Part_0.mirror = true;
      this.setRotation(this.Part_0, 0.0F, 0.0F, 0.0F);
      this.Part_1 = new ModelRenderer(this, 0, 6);
      this.Part_1.addBox(0.0F, 0.0F, 0.0F, 4, 6, 1);
      this.Part_1.setRotationPoint(1.0F, 0.0F, -1.0F);
      this.Part_1.setTextureSize(512, 512);
      this.Part_1.mirror = true;
      this.setRotation(this.Part_1, 0.0F, 0.0F, 0.0F);
      this.Part_2 = new ModelRenderer(this, 11, 6);
      this.Part_2.addBox(0.0F, 0.0F, 0.0F, 1, 6, 4);
      this.Part_2.setRotationPoint(5.0F, 0.0F, 0.0F);
      this.Part_2.setTextureSize(512, 512);
      this.Part_2.mirror = true;
      this.setRotation(this.Part_2, 0.0F, 0.0F, 0.0F);
      this.Part_3 = new ModelRenderer(this, 33, 6);
      this.Part_3.addBox(0.0F, 0.0F, 0.0F, 4, 6, 1);
      this.Part_3.setRotationPoint(1.0F, 0.0F, 4.0F);
      this.Part_3.setTextureSize(512, 512);
      this.Part_3.mirror = true;
      this.setRotation(this.Part_3, 0.0F, 0.0F, 0.0F);
      this.Part_4 = new ModelRenderer(this, 0, 0);
      this.Part_4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Part_4.setRotationPoint(1.0F, -1.0F, 0.0F);
      this.Part_4.setTextureSize(512, 512);
      this.Part_4.mirror = true;
      this.setRotation(this.Part_4, 0.0F, 0.0F, 0.0F);
      this.Part_5 = new ModelRenderer(this, 17, 0);
      this.Part_5.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4);
      this.Part_5.setRotationPoint(1.0F, 6.0F, 0.0F);
      this.Part_5.setTextureSize(512, 512);
      this.Part_5.mirror = true;
      this.setRotation(this.Part_5, 0.0F, 0.0F, 0.0F);
      this.Part_6 = new ModelRenderer(this, 34, 0);
      this.Part_6.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
      this.Part_6.setRotationPoint(2.0F, 3.0F, -1.5F);
      this.Part_6.setTextureSize(512, 512);
      this.Part_6.mirror = true;
      this.setRotation(this.Part_6, 0.0F, 0.0F, 0.0F);
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
