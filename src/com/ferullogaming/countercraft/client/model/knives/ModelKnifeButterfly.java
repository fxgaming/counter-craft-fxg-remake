package com.ferullogaming.countercraft.client.model.knives;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelKnifeButterfly extends ModelBase {
   ModelRenderer Part_0;
   ModelRenderer Part_1;
   ModelRenderer Part_2;
   ModelRenderer Part_3;
   ModelRenderer Part_4;
   ModelRenderer Part_5;
   ModelRenderer Part_6;
   ModelRenderer Part_7;
   ModelRenderer Part_8;
   ModelRenderer Part_9;
   ModelRenderer Part_10;

   public ModelKnifeButterfly() {
      super.textureWidth = 64;
      super.textureHeight = 32;
      this.Part_0 = new ModelRenderer(this, 21, 0);
      this.Part_0.addBox(0.0F, 0.0F, 0.0F, 1, 14, 2);
      this.Part_0.setRotationPoint(0.0F, -3.0F, -1.0F);
      this.Part_0.setTextureSize(64, 32);
      this.Part_0.mirror = true;
      this.setRotation(this.Part_0, 0.0F, 0.0F, 0.0F);
      this.Part_1 = new ModelRenderer(this, 0, 6);
      this.Part_1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Part_1.setRotationPoint(0.0F, 10.3F, 0.3F);
      this.Part_1.setTextureSize(64, 32);
      this.Part_1.mirror = true;
      this.setRotation(this.Part_1, -0.7853982F, 0.0F, 0.0F);
      this.Part_2 = new ModelRenderer(this, 0, 0);
      this.Part_2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1);
      this.Part_2.setRotationPoint(0.0F, 9.5F, -1.0F);
      this.Part_2.setTextureSize(64, 32);
      this.Part_2.mirror = true;
      this.setRotation(this.Part_2, -0.38397244F, 0.0F, 0.0F);
      this.Part_3 = new ModelRenderer(this, 14, 0);
      this.Part_3.addBox(0.0F, 0.0F, 0.0F, 1, 4, 2);
      this.Part_3.setRotationPoint(0.0F, 8.0F, -2.4F);
      this.Part_3.setTextureSize(64, 32);
      this.Part_3.mirror = true;
      this.setRotation(this.Part_3, 0.2443461F, 0.0F, 0.0F);
      this.Part_4 = new ModelRenderer(this, 14, 7);
      this.Part_4.addBox(0.0F, 0.0F, 0.0F, 1, 6, 2);
      this.Part_4.setRotationPoint(0.0F, 2.0F, -1.9F);
      this.Part_4.setTextureSize(64, 32);
      this.Part_4.mirror = true;
      this.setRotation(this.Part_4, -0.08726646F, 0.0F, 0.0F);
      this.Part_5 = new ModelRenderer(this, 5, 5);
      this.Part_5.addBox(0.0F, 0.0F, 0.0F, 1, 5, 2);
      this.Part_5.setRotationPoint(0.0F, -2.0F, -2.5F);
      this.Part_5.setTextureSize(64, 32);
      this.Part_5.mirror = true;
      this.setRotation(this.Part_5, 0.10471976F, 0.0F, 0.0F);
      this.Part_6 = new ModelRenderer(this, 5, 0);
      this.Part_6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3);
      this.Part_6.setRotationPoint(0.0F, -2.5F, 0.0F);
      this.Part_6.setTextureSize(64, 32);
      this.Part_6.mirror = true;
      this.setRotation(this.Part_6, 0.0F, 0.0F, 0.0F);
      this.Part_7 = new ModelRenderer(this, 27, 18);
      this.Part_7.addBox(0.0F, 0.0F, 0.0F, 2, 11, 2);
      this.Part_7.setRotationPoint(-0.5F, -12.0F, -0.5F);
      this.Part_7.setTextureSize(64, 32);
      this.Part_7.mirror = true;
      this.setRotation(this.Part_7, 0.0F, 0.0F, 0.0F);
      this.Part_8 = new ModelRenderer(this, 18, 18);
      this.Part_8.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Part_8.setRotationPoint(-0.5F, -12.0F, -2.5F);
      this.Part_8.setTextureSize(64, 32);
      this.Part_8.mirror = true;
      this.setRotation(this.Part_8, 0.0F, 0.0F, 0.0F);
      this.Part_9 = new ModelRenderer(this, 9, 16);
      this.Part_9.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Part_9.setRotationPoint(-0.5F, -20.5F, 1.0F);
      this.Part_9.setTextureSize(64, 32);
      this.Part_9.mirror = true;
      this.setRotation(this.Part_9, -0.17453294F, 0.0F, 0.0F);
      this.Part_10 = new ModelRenderer(this, 0, 16);
      this.Part_10.addBox(0.0F, 0.0F, 0.0F, 2, 9, 2);
      this.Part_10.setRotationPoint(-0.5F, -20.8F, -0.9F);
      this.Part_10.setTextureSize(64, 32);
      this.Part_10.mirror = true;
      this.setRotation(this.Part_10, -0.17453294F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
      this.Part_0.render(f5);
      this.Part_1.render(f5);
      this.Part_2.render(f5);
      this.Part_3.render(f5);
      this.Part_4.render(f5);
      this.Part_5.render(f5);
      this.Part_6.render(f5);
      this.Part_7.render(f5);
      this.Part_8.render(f5);
      this.Part_9.render(f5);
      this.Part_10.render(f5);
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
