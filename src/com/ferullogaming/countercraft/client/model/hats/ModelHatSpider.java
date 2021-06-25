package com.ferullogaming.countercraft.client.model.hats;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHatSpider extends ModelBase {
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

   public ModelHatSpider() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Shape1 = new ModelRenderer(this, 0, 0);
      this.Shape1.addBox(0.0F, 0.0F, 0.0F, 8, 8, 8);
      this.Shape1.setRotationPoint(-4.0F, -4.0F, -4.0F);
      this.Shape1.setTextureSize(128, 64);
      this.Shape1.mirror = true;
      this.setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
      this.Shape2 = new ModelRenderer(this, 0, 29);
      this.Shape2.addBox(0.0F, 0.0F, 0.0F, 3, 3, 2);
      this.Shape2.setRotationPoint(-1.5F, -7.0F, -1.0F);
      this.Shape2.setTextureSize(128, 64);
      this.Shape2.mirror = true;
      this.setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
      this.Shape3 = new ModelRenderer(this, 0, 35);
      this.Shape3.addBox(0.0F, 0.0F, 0.0F, 5, 4, 4);
      this.Shape3.setRotationPoint(-2.5F, -8.0F, -5.0F);
      this.Shape3.setTextureSize(128, 64);
      this.Shape3.mirror = true;
      this.setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
      this.Shape4 = new ModelRenderer(this, 0, 17);
      this.Shape4.addBox(0.0F, 0.0F, 0.0F, 6, 5, 6);
      this.Shape4.setRotationPoint(-3.0F, -9.0F, 1.0F);
      this.Shape4.setTextureSize(128, 64);
      this.Shape4.mirror = true;
      this.setRotation(this.Shape4, 0.0F, 0.0F, 0.0F);
      this.Shape5 = new ModelRenderer(this, 25, 17);
      this.Shape5.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
      this.Shape5.setRotationPoint(1.0F, -5.0F, 0.0F);
      this.Shape5.setTextureSize(128, 64);
      this.Shape5.mirror = true;
      this.setRotation(this.Shape5, 0.5576792F, 0.0F, -0.9772811F);
      this.Shape6 = new ModelRenderer(this, 25, 17);
      this.Shape6.addBox(0.0F, 0.0F, 0.0F, 1, 7, 1);
      this.Shape6.setRotationPoint(1.0F, -5.0F, 0.0F);
      this.Shape6.setTextureSize(128, 64);
      this.Shape6.mirror = true;
      this.setRotation(this.Shape6, 0.1115358F, 0.0F, -1.07818F);
      this.Shape7 = new ModelRenderer(this, 25, 17);
      this.Shape7.addBox(0.0F, 0.0F, -1.0F, 1, 7, 1);
      this.Shape7.setRotationPoint(1.0F, -5.0F, 0.0F);
      this.Shape7.setTextureSize(128, 64);
      this.Shape7.mirror = true;
      this.setRotation(this.Shape7, -0.5948578F, 0.0F, -1.041001F);
      this.Shape8 = new ModelRenderer(this, 25, 17);
      this.Shape8.addBox(0.0F, 0.0F, -1.0F, 1, 7, 1);
      this.Shape8.setRotationPoint(1.0F, -5.0F, 0.0F);
      this.Shape8.setTextureSize(128, 64);
      this.Shape8.mirror = true;
      this.setRotation(this.Shape8, -0.1115358F, 0.0F, -1.07818F);
      this.Shape9 = new ModelRenderer(this, 25, 17);
      this.Shape9.addBox(-1.0F, 0.0F, 0.0F, 1, 7, 1);
      this.Shape9.setRotationPoint(-1.0F, -5.0F, 0.0F);
      this.Shape9.setTextureSize(128, 64);
      this.Shape9.mirror = true;
      this.setRotation(this.Shape9, 0.5948578F, 0.0F, 1.07818F);
      this.Shape10 = new ModelRenderer(this, 25, 17);
      this.Shape10.addBox(-1.0F, 0.0F, 0.0F, 1, 7, 1);
      this.Shape10.setRotationPoint(-1.0F, -5.0F, 0.0F);
      this.Shape10.setTextureSize(128, 64);
      this.Shape10.mirror = true;
      this.setRotation(this.Shape10, 0.1115358F, 0.0F, 1.226894F);
      this.Shape11 = new ModelRenderer(this, 25, 17);
      this.Shape11.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 1);
      this.Shape11.setRotationPoint(-1.0F, -5.0F, 0.0F);
      this.Shape11.setTextureSize(128, 64);
      this.Shape11.mirror = true;
      this.setRotation(this.Shape11, -0.669215F, 0.0F, 1.003822F);
      this.Shape12 = new ModelRenderer(this, 25, 17);
      this.Shape12.addBox(-1.0F, 0.0F, -1.0F, 1, 7, 1);
      this.Shape12.setRotationPoint(-1.0F, -5.0F, 0.0F);
      this.Shape12.setTextureSize(128, 64);
      this.Shape12.mirror = true;
      this.setRotation(this.Shape12, -0.1858931F, 0.0F, 1.152537F);
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
