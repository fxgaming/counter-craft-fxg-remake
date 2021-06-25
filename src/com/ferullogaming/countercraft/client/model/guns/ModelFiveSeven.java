package com.ferullogaming.countercraft.client.model.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelFiveSeven extends ModelGun {
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
   ModelRenderer Part_11;
   ModelRenderer Part_12;
   ModelRenderer Part_13;
   ModelRenderer Part_14;
   ModelRenderer Part_15;
   ModelRenderer Part_16;

   public ModelFiveSeven() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.Part_0 = new ModelRenderer(this, 1, 1);
      this.Part_0.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.Part_0.setRotationPoint(-6.0F, -4.0F, 0.5F);
      this.Part_0.setTextureSize(64, 64);
      this.Part_0.mirror = true;
      this.setRotation(this.Part_0, 0.0F, 0.0F, 0.0F);
      this.Part_1 = new ModelRenderer(this, 17, 1);
      this.Part_1.addBox(0.0F, 0.0F, 0.0F, 5, 2, 2);
      this.Part_1.setRotationPoint(-6.0F, -6.0F, 0.0F);
      this.Part_1.setTextureSize(64, 64);
      this.Part_1.mirror = true;
      this.setRotation(this.Part_1, 0.0F, 0.0F, 0.0F);
      this.Part_2 = new ModelRenderer(this, 33, 1);
      this.Part_2.addBox(0.0F, 0.0F, 0.0F, 8, 4, 2);
      this.Part_2.setRotationPoint(-1.0F, -6.5F, 0.0F);
      this.Part_2.setTextureSize(64, 64);
      this.Part_2.mirror = true;
      this.setRotation(this.Part_2, 0.0F, 0.0F, 0.0F);
      this.Part_3 = new ModelRenderer(this, 57, 1);
      this.Part_3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1);
      this.Part_3.setRotationPoint(0.0F, -2.5F, 0.5F);
      this.Part_3.setTextureSize(64, 64);
      this.Part_3.mirror = true;
      this.setRotation(this.Part_3, 0.0F, 0.0F, 0.0F);
      this.Part_4 = new ModelRenderer(this, 1, 9);
      this.Part_4.addBox(0.0F, 0.0F, 0.0F, 4, 1, 1);
      this.Part_4.setRotationPoint(0.5F, -1.0F, 0.5F);
      this.Part_4.setTextureSize(64, 64);
      this.Part_4.mirror = true;
      this.setRotation(this.Part_4, 0.0F, 0.0F, 0.0F);
      this.Part_5 = new ModelRenderer(this, 17, 9);
      this.Part_5.addBox(0.0F, 0.0F, 0.0F, 3, 7, 2);
      this.Part_5.setRotationPoint(4.0F, -2.5F, 0.0F);
      this.Part_5.setTextureSize(64, 64);
      this.Part_5.mirror = true;
      this.setRotation(this.Part_5, 0.0F, 0.0F, -0.20943952F);
      this.Part_6 = new ModelRenderer(this, 33, 9);
      this.Part_6.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1);
      this.Part_6.setRotationPoint(-6.0F, -6.5F, 0.5F);
      this.Part_6.setTextureSize(64, 64);
      this.Part_6.mirror = true;
      this.setRotation(this.Part_6, 0.0F, 0.0F, 0.0F);
      this.Part_7 = new ModelRenderer(this, 49, 9);
      this.Part_7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
      this.Part_7.setRotationPoint(7.0F, -6.5F, 0.0F);
      this.Part_7.setTextureSize(64, 64);
      this.Part_7.mirror = true;
      this.setRotation(this.Part_7, 0.0F, 0.0F, 0.0F);
      this.Part_8 = new ModelRenderer(this, 1, 17);
      this.Part_8.addBox(3.0F, -3.1F, 0.0F, 2, 4, 2);
      this.Part_8.setRotationPoint(4.0F, -2.5F, 0.0F);
      this.Part_8.setTextureSize(64, 64);
      this.Part_8.mirror = true;
      this.setRotation(this.Part_8, 0.0F, 0.0F, -0.20943952F);
      this.Part_9 = new ModelRenderer(this, 57, 9);
      this.Part_9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_9.setRotationPoint(2.3F, -2.5F, 0.5F);
      this.Part_9.setTextureSize(64, 64);
      this.Part_9.mirror = true;
      this.setRotation(this.Part_9, 0.0F, 0.0F, 0.0F);
      this.Part_10 = new ModelRenderer(this, 33, 17);
      this.Part_10.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_10.setRotationPoint(-5.5F, -7.5F, 0.5F);
      this.Part_10.setTextureSize(64, 64);
      this.Part_10.mirror = true;
      this.setRotation(this.Part_10, 0.0F, 0.0F, 0.0F);
      this.Part_11 = new ModelRenderer(this, 41, 17);
      this.Part_11.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
      this.Part_11.setRotationPoint(4.5F, -6.7F, 0.5F);
      this.Part_11.setTextureSize(64, 64);
      this.Part_11.mirror = true;
      this.setRotation(this.Part_11, 0.0F, 0.0F, 0.0F);
      this.Part_12 = new ModelRenderer(this, 49, 17);
      this.Part_12.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_12.setRotationPoint(6.5F, -7.0F, 0.5F);
      this.Part_12.setTextureSize(64, 64);
      this.Part_12.mirror = true;
      this.setRotation(this.Part_12, 0.0F, 0.0F, 0.0F);
      this.Part_13 = new ModelRenderer(this, 57, 17);
      this.Part_13.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_13.setRotationPoint(-1.0F, -6.5F, 0.1F);
      this.Part_13.setTextureSize(64, 64);
      this.Part_13.mirror = true;
      this.setRotation(this.Part_13, 0.0F, 0.0F, -0.5235988F);
      this.Part_14 = new ModelRenderer(this, 1, 25);
      this.Part_14.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
      this.Part_14.setRotationPoint(-1.0F, -2.5F, 0.1F);
      this.Part_14.setTextureSize(64, 64);
      this.Part_14.mirror = true;
      this.setRotation(this.Part_14, 0.0F, 0.0F, -0.34906584F);
      this.Part_15 = new ModelRenderer(this, 9, 25);
      this.Part_15.addBox(0.0F, -2.0F, 0.0F, 1, 2, 1);
      this.Part_15.setRotationPoint(-1.0F, -2.5F, 0.9F);
      this.Part_15.setTextureSize(64, 64);
      this.Part_15.mirror = true;
      this.setRotation(this.Part_15, 0.0F, 0.0F, -0.34906584F);
      this.Part_16 = new ModelRenderer(this, 17, 25);
      this.Part_16.addBox(-1.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_16.setRotationPoint(-1.0F, -6.5F, 0.9F);
      this.Part_16.setTextureSize(64, 64);
      this.Part_16.mirror = true;
      this.setRotation(this.Part_16, 0.0F, 0.0F, -0.5235988F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glTranslatef(0.2F, 0.06F, 0.0F);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslatef(0.6F, 0.0F, 0.0F);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
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
      this.Part_11.render(f5);
      this.Part_12.render(f5);
      this.Part_13.render(f5);
      this.Part_14.render(f5);
      this.Part_15.render(f5);
      this.Part_16.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
   }

   public void renderAmmo(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
   }
}
