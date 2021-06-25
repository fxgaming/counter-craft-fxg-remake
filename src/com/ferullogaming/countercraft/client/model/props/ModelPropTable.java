package com.ferullogaming.countercraft.client.model.props;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPropTable extends ModelBase {
   ModelRenderer Import_Box0;
   ModelRenderer Import_Box1;
   ModelRenderer Import_Box2;
   ModelRenderer Import_Box3;
   ModelRenderer Import_Box4;
   ModelRenderer Import_Box5;

   public ModelPropTable() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Import_Box0 = new ModelRenderer(this, 1, 1);
      this.Import_Box0.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
      this.Import_Box0.setRotationPoint(5.0F, -15.0F, 5.0F);
      this.Import_Box0.setTextureSize(64, 32);
      this.Import_Box0.mirror = true;
      this.setRotation(this.Import_Box0, 0.0F, 0.0F, 0.0F);
      this.Import_Box1 = new ModelRenderer(this, 17, 1);
      this.Import_Box1.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
      this.Import_Box1.setRotationPoint(5.0F, -15.0F, -7.0F);
      this.Import_Box1.setTextureSize(64, 32);
      this.Import_Box1.mirror = true;
      this.setRotation(this.Import_Box1, 0.0F, 0.0F, 0.0F);
      this.Import_Box2 = new ModelRenderer(this, 33, 1);
      this.Import_Box2.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
      this.Import_Box2.setRotationPoint(-7.0F, -15.0F, -7.0F);
      this.Import_Box2.setTextureSize(64, 32);
      this.Import_Box2.mirror = true;
      this.setRotation(this.Import_Box2, 0.0F, 0.0F, 0.0F);
      this.Import_Box3 = new ModelRenderer(this, 49, 1);
      this.Import_Box3.addBox(0.0F, 0.0F, 0.0F, 2, 15, 2);
      this.Import_Box3.setRotationPoint(-7.0F, -15.0F, 5.0F);
      this.Import_Box3.setTextureSize(64, 32);
      this.Import_Box3.mirror = true;
      this.setRotation(this.Import_Box3, 0.0F, 0.0F, 0.0F);
      this.Import_Box4 = new ModelRenderer(this, 49, 9);
      this.Import_Box4.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16);
      this.Import_Box4.setRotationPoint(-8.0F, -16.0F, -8.0F);
      this.Import_Box4.setTextureSize(64, 32);
      this.Import_Box4.mirror = true;
      this.setRotation(this.Import_Box4, 0.0F, 0.0F, 0.0F);
      this.Import_Box5 = new ModelRenderer(this, 1, 25);
      this.Import_Box5.addBox(0.0F, 0.0F, 0.0F, 12, 1, 12);
      this.Import_Box5.setRotationPoint(-6.0F, -15.0F, -6.0F);
      this.Import_Box5.setTextureSize(64, 32);
      this.Import_Box5.mirror = true;
      this.setRotation(this.Import_Box5, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f5, f, f1, f2, f3, f4, entity);
      this.Import_Box0.render(f5);
      this.Import_Box1.render(f5);
      this.Import_Box2.render(f5);
      this.Import_Box3.render(f5);
      this.Import_Box4.render(f5);
      this.Import_Box5.render(f5);
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
