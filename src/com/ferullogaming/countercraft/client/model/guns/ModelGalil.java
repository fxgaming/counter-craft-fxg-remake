package com.ferullogaming.countercraft.client.model.guns;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelGalil extends ModelGun {
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
   ModelRenderer Part_17;
   ModelRenderer Part_18;
   ModelRenderer Part_19;
   ModelRenderer Part_20;
   ModelRenderer Part_21;
   ModelRenderer Part_22;
   ModelRenderer Part_23;
   ModelRenderer Part_24;
   ModelRenderer Part_25;
   ModelRenderer Part_26;
   ModelRenderer Part_27;
   ModelRenderer Part_28;
   ModelRenderer Part_29;
   ModelRenderer Part_30;
   ModelRenderer Part_31;
   ModelRenderer Part_32;
   ModelRenderer Part_33;
   ModelRenderer Part_34;
   ModelRenderer Part_35;
   ModelRenderer Part_36;
   ModelRenderer Part_37;
   ModelRenderer Part_38;
   ModelRenderer Part_39;
   ModelRenderer Part_40;
   ModelRenderer Part_41;
   ModelRenderer Part_42;
   ModelRenderer Part_43;
   ModelRenderer Part_44;
   ModelRenderer Part_45;
   ModelRenderer Part_46;
   ModelRenderer Part_47;
   ModelRenderer Part_48;
   ModelRenderer Part_49;

   public ModelGalil() {
      super.textureWidth = 128;
      super.textureHeight = 64;
      this.Part_0 = new ModelRenderer(this, 0, 53);
      this.Part_0.addBox(0.0F, 0.0F, 0.0F, 21, 1, 1);
      this.Part_0.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.Part_0.setTextureSize(128, 64);
      this.Part_0.mirror = true;
      this.setRotation(this.Part_0, 0.0F, 0.0F, 0.0F);
      this.Part_1 = new ModelRenderer(this, 0, 35);
      this.Part_1.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1);
      this.Part_1.setRotationPoint(6.0F, -1.95F, 0.0F);
      this.Part_1.setTextureSize(128, 64);
      this.Part_1.mirror = true;
      this.setRotation(this.Part_1, 0.0F, 0.0F, 0.0F);
      this.Part_2 = new ModelRenderer(this, 0, 25);
      this.Part_2.addBox(0.0F, 0.0F, 0.0F, 1, 5, 2);
      this.Part_2.setRotationPoint(5.0F, -3.5F, -0.5F);
      this.Part_2.setTextureSize(128, 64);
      this.Part_2.mirror = true;
      this.setRotation(this.Part_2, 0.0F, 0.0F, 0.0F);
      this.Part_3 = new ModelRenderer(this, 47, 43);
      this.Part_3.addBox(0.0F, 0.0F, 0.0F, 10, 3, 1);
      this.Part_3.setRotationPoint(11.0F, -1.25F, -1.5F);
      this.Part_3.setTextureSize(128, 64);
      this.Part_3.mirror = true;
      this.setRotation(this.Part_3, 0.0F, 0.0F, 0.0F);
      this.Part_4 = new ModelRenderer(this, 47, 38);
      this.Part_4.addBox(0.0F, 0.0F, 0.0F, 10, 1, 3);
      this.Part_4.setRotationPoint(11.01F, -2.1F, -1.0F);
      this.Part_4.setTextureSize(128, 64);
      this.Part_4.mirror = true;
      this.setRotation(this.Part_4, 0.0F, 0.0F, 0.0F);
      this.Part_5 = new ModelRenderer(this, 47, 53);
      this.Part_5.addBox(0.0F, 0.0F, 0.0F, 10, 1, 3);
      this.Part_5.setRotationPoint(11.01F, 1.25F, -1.0F);
      this.Part_5.setTextureSize(128, 64);
      this.Part_5.mirror = true;
      this.setRotation(this.Part_5, 0.0F, 0.0F, 0.0F);
      this.Part_6 = new ModelRenderer(this, 47, 48);
      this.Part_6.addBox(0.0F, 0.0F, 0.0F, 10, 3, 1);
      this.Part_6.setRotationPoint(11.0F, -1.25F, 1.5F);
      this.Part_6.setTextureSize(128, 64);
      this.Part_6.mirror = true;
      this.setRotation(this.Part_6, 0.0F, 0.0F, 0.0F);
      this.Part_7 = new ModelRenderer(this, 47, 33);
      this.Part_7.addBox(0.0F, -3.0F, 0.0F, 7, 1, 1);
      this.Part_7.setRotationPoint(13.0F, 0.5F, 0.0F);
      this.Part_7.setTextureSize(128, 64);
      this.Part_7.mirror = true;
      this.setRotation(this.Part_7, 0.0F, 0.0F, 0.0F);
      this.Part_8 = new ModelRenderer(this, 76, 38);
      this.Part_8.addBox(-1.0F, -1.0F, -2.0F, 1, 1, 1);
      this.Part_8.setRotationPoint(23.0F, 0.0F, 0.0F);
      this.Part_8.setTextureSize(128, 64);
      this.Part_8.mirror = true;
      this.setRotation(this.Part_8, 0.0F, 0.0F, 0.0F);
      this.Part_9 = new ModelRenderer(this, 76, 43);
      this.Part_9.addBox(0.0F, 0.0F, 0.0F, 12, 4, 3);
      this.Part_9.setRotationPoint(21.0F, -2.0F, -1.0F);
      this.Part_9.setTextureSize(128, 64);
      this.Part_9.mirror = true;
      this.setRotation(this.Part_9, 0.0F, 0.0F, 0.0F);
      this.Part_10 = new ModelRenderer(this, 89, 59);
      this.Part_10.addBox(0.0F, 0.0F, 0.0F, 7, 2, 1);
      this.Part_10.setRotationPoint(23.0F, -1.0F, -1.1F);
      this.Part_10.setTextureSize(128, 64);
      this.Part_10.mirror = true;
      this.setRotation(this.Part_10, 0.0F, 0.0F, 0.0F);
      this.Part_11 = new ModelRenderer(this, 83, 38);
      this.Part_11.addBox(0.0F, -3.0F, 0.0F, 7, 1, 1);
      this.Part_11.setRotationPoint(24.0F, 0.5F, 0.0F);
      this.Part_11.setTextureSize(128, 64);
      this.Part_11.mirror = true;
      this.setRotation(this.Part_11, 0.0F, 0.0F, 0.0F);
      this.Part_12 = new ModelRenderer(this, 102, 38);
      this.Part_12.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_12.setRotationPoint(31.0F, -3.0F, 0.6F);
      this.Part_12.setTextureSize(128, 64);
      this.Part_12.mirror = true;
      this.setRotation(this.Part_12, 0.0F, 0.0F, 0.0F);
      this.Part_13 = new ModelRenderer(this, 102, 35);
      this.Part_13.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_13.setRotationPoint(31.0F, -3.0F, -0.6F);
      this.Part_13.setTextureSize(128, 64);
      this.Part_13.mirror = true;
      this.setRotation(this.Part_13, 0.0F, 0.0F, 0.0F);
      this.Part_14 = new ModelRenderer(this, 98, 52);
      this.Part_14.addBox(0.0F, 0.0F, 0.0F, 2, 2, 2);
      this.Part_14.setRotationPoint(25.0F, 2.0F, -0.5F);
      this.Part_14.setTextureSize(128, 64);
      this.Part_14.mirror = true;
      this.setRotation(this.Part_14, 0.0F, 0.0F, 0.0F);
      this.Part_15 = new ModelRenderer(this, 76, 51);
      this.Part_15.addBox(0.0F, 0.0F, 0.0F, 5, 3, 1);
      this.Part_15.setRotationPoint(22.0F, -1.0F, 0.75F);
      this.Part_15.setTextureSize(128, 64);
      this.Part_15.mirror = true;
      this.setRotation(this.Part_15, 0.0F, 0.0F, 0.4537856F);
      this.Part_16 = new ModelRenderer(this, 76, 56);
      this.Part_16.addBox(0.0F, 0.0F, 0.0F, 5, 3, 1);
      this.Part_16.setRotationPoint(22.0F, -1.0F, -0.75F);
      this.Part_16.setTextureSize(128, 64);
      this.Part_16.mirror = true;
      this.setRotation(this.Part_16, 0.0F, 0.0F, 0.4537856F);
      this.Part_17 = new ModelRenderer(this, 110, 46);
      this.Part_17.addBox(0.0F, 0.0F, 0.0F, 3, 4, 2);
      this.Part_17.setRotationPoint(22.0F, 2.0F, -0.51F);
      this.Part_17.setTextureSize(128, 64);
      this.Part_17.mirror = true;
      this.setRotation(this.Part_17, 0.0F, 0.0F, 0.01745329F);
      this.Part_18 = new ModelRenderer(this, 110, 46);
      this.Part_18.addBox(0.0F, 0.0F, 0.0F, 3, 4, 2);
      this.Part_18.setRotationPoint(22.0F, 2.0F, -0.49F);
      this.Part_18.setTextureSize(128, 64);
      this.Part_18.mirror = true;
      this.setRotation(this.Part_18, 0.0F, 0.0F, 0.01745329F);
      this.Part_19 = new ModelRenderer(this, 110, 46);
      this.Part_19.addBox(0.0F, 0.0F, 0.0F, 3, 4, 2);
      this.Part_19.setRotationPoint(21.99F, 2.0F, -0.5F);
      this.Part_19.setTextureSize(128, 64);
      this.Part_19.mirror = true;
      this.setRotation(this.Part_19, 0.0F, 0.0F, 0.01745329F);
      this.Part_20 = new ModelRenderer(this, 110, 46);
      this.Part_20.addBox(0.0F, 0.0F, 0.0F, 3, 4, 2);
      this.Part_20.setRotationPoint(22.01F, 2.0F, -0.5F);
      this.Part_20.setTextureSize(128, 64);
      this.Part_20.mirror = true;
      this.setRotation(this.Part_20, 0.0F, 0.0F, 0.0F);
      this.Part_21 = new ModelRenderer(this, 110, 53);
      this.Part_21.addBox(0.0F, 0.0F, 0.0F, 3, 5, 2);
      this.Part_21.setRotationPoint(22.13F, 5.1F, -0.5F);
      this.Part_21.setTextureSize(128, 64);
      this.Part_21.mirror = true;
      this.setRotation(this.Part_21, 0.0F, 0.0F, 0.29670596F);
      this.Part_22 = new ModelRenderer(this, 64, 27);
      this.Part_22.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Part_22.setRotationPoint(29.5F, 2.0F, -0.5F);
      this.Part_22.setTextureSize(128, 64);
      this.Part_22.mirror = true;
      this.setRotation(this.Part_22, 0.0F, 0.0F, 0.0F);
      this.Part_23 = new ModelRenderer(this, 75, 27);
      this.Part_23.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2);
      this.Part_23.setRotationPoint(29.4F, 3.0F, -0.51F);
      this.Part_23.setTextureSize(128, 64);
      this.Part_23.mirror = true;
      this.setRotation(this.Part_23, 0.0F, 0.0F, -0.29670596F);
      this.Part_24 = new ModelRenderer(this, 75, 27);
      this.Part_24.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2);
      this.Part_24.setRotationPoint(29.4F, 3.0F, -0.49F);
      this.Part_24.setTextureSize(128, 64);
      this.Part_24.mirror = true;
      this.setRotation(this.Part_24, 0.0F, 0.0F, -0.29670596F);
      this.Part_25 = new ModelRenderer(this, 75, 27);
      this.Part_25.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2);
      this.Part_25.setRotationPoint(29.41F, 3.01F, -0.5F);
      this.Part_25.setTextureSize(128, 64);
      this.Part_25.mirror = true;
      this.setRotation(this.Part_25, 0.0F, 0.0F, -0.29670596F);
      this.Part_26 = new ModelRenderer(this, 75, 27);
      this.Part_26.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2);
      this.Part_26.setRotationPoint(29.38F, 3.01F, -0.5F);
      this.Part_26.setTextureSize(128, 64);
      this.Part_26.mirror = true;
      this.setRotation(this.Part_26, 0.0F, 0.0F, -0.29670596F);
      this.Part_27 = new ModelRenderer(this, 67, 32);
      this.Part_27.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_27.setRotationPoint(28.5F, 1.5F, -0.25F);
      this.Part_27.setTextureSize(128, 64);
      this.Part_27.mirror = true;
      this.setRotation(this.Part_27, 0.0F, 0.0F, 0.0F);
      this.Part_28 = new ModelRenderer(this, 88, 31);
      this.Part_28.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
      this.Part_28.setRotationPoint(27.0F, 3.0F, -0.5F);
      this.Part_28.setTextureSize(128, 64);
      this.Part_28.mirror = true;
      this.setRotation(this.Part_28, 0.0F, 0.0F, 0.0F);
      this.Part_29 = new ModelRenderer(this, 120, 41);
      this.Part_29.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Part_29.setRotationPoint(33.0F, -1.0F, 0.0F);
      this.Part_29.setTextureSize(128, 64);
      this.Part_29.mirror = true;
      this.setRotation(this.Part_29, 0.0F, 0.0F, 0.0F);
      this.Part_30 = new ModelRenderer(this, 0, 0);
      this.Part_30.addBox(0.0F, 0.0F, 0.0F, 8, 3, 2);
      this.Part_30.setRotationPoint(35.99F, -1.5F, -0.51F);
      this.Part_30.setTextureSize(128, 64);
      this.Part_30.mirror = true;
      this.setRotation(this.Part_30, 0.0F, 0.0F, 0.0F);
      this.Part_31 = new ModelRenderer(this, 0, 0);
      this.Part_31.addBox(0.0F, 0.0F, 0.0F, 8, 3, 2);
      this.Part_31.setRotationPoint(36.0F, -1.5F, -0.49F);
      this.Part_31.setTextureSize(128, 64);
      this.Part_31.mirror = true;
      this.setRotation(this.Part_31, 0.0F, 0.0F, 0.0F);
      this.Part_32 = new ModelRenderer(this, 0, 0);
      this.Part_32.addBox(0.0F, 0.0F, 0.0F, 8, 3, 2);
      this.Part_32.setRotationPoint(35.99F, -1.51F, -0.5F);
      this.Part_32.setTextureSize(128, 64);
      this.Part_32.mirror = true;
      this.setRotation(this.Part_32, 0.0F, 0.0F, 0.0F);
      this.Part_33 = new ModelRenderer(this, 0, 0);
      this.Part_33.addBox(0.0F, 0.0F, 0.0F, 8, 3, 2);
      this.Part_33.setRotationPoint(35.99F, -1.49F, -0.5F);
      this.Part_33.setTextureSize(128, 64);
      this.Part_33.mirror = true;
      this.setRotation(this.Part_33, 0.0F, 0.0F, 0.0F);
      this.Part_34 = new ModelRenderer(this, 0, 7);
      this.Part_34.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
      this.Part_34.setRotationPoint(36.82F, 0.52F, -0.5F);
      this.Part_34.setTextureSize(128, 64);
      this.Part_34.mirror = true;
      this.setRotation(this.Part_34, 0.0F, 0.0F, 0.5969026F);
      this.Part_35 = new ModelRenderer(this, 40, 18);
      this.Part_35.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Part_35.setRotationPoint(40.75F, 2.01F, -0.5F);
      this.Part_35.setTextureSize(128, 64);
      this.Part_35.mirror = true;
      this.setRotation(this.Part_35, 0.0F, 0.0F, 0.41887903F);
      this.Part_36 = new ModelRenderer(this, 40, 18);
      this.Part_36.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Part_36.setRotationPoint(40.75F, 2.03F, -0.5F);
      this.Part_36.setTextureSize(128, 64);
      this.Part_36.mirror = true;
      this.setRotation(this.Part_36, 0.0F, 0.0F, 0.41887903F);
      this.Part_37 = new ModelRenderer(this, 40, 18);
      this.Part_37.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Part_37.setRotationPoint(40.75F, 2.02F, -0.51F);
      this.Part_37.setTextureSize(128, 64);
      this.Part_37.mirror = true;
      this.setRotation(this.Part_37, 0.0F, 0.0F, 0.41887903F);
      this.Part_38 = new ModelRenderer(this, 40, 18);
      this.Part_38.addBox(0.0F, 0.0F, 0.0F, 4, 1, 2);
      this.Part_38.setRotationPoint(40.75F, 2.02F, -0.49F);
      this.Part_38.setTextureSize(128, 64);
      this.Part_38.mirror = true;
      this.setRotation(this.Part_38, 0.0F, 0.0F, 0.41887903F);
      this.Part_39 = new ModelRenderer(this, 16, 12);
      this.Part_39.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Part_39.setRotationPoint(38.75F, 2.02F, -0.52F);
      this.Part_39.setTextureSize(128, 64);
      this.Part_39.mirror = true;
      this.setRotation(this.Part_39, 0.0F, 0.0F, 0.0F);
      this.Part_40 = new ModelRenderer(this, 16, 12);
      this.Part_40.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Part_40.setRotationPoint(38.75F, 2.01F, -0.5F);
      this.Part_40.setTextureSize(128, 64);
      this.Part_40.mirror = true;
      this.setRotation(this.Part_40, 0.0F, 0.0F, 0.0F);
      this.Part_41 = new ModelRenderer(this, 16, 12);
      this.Part_41.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Part_41.setRotationPoint(38.75F, 2.02F, -0.48F);
      this.Part_41.setTextureSize(128, 64);
      this.Part_41.mirror = true;
      this.setRotation(this.Part_41, 0.0F, 0.0F, 0.0F);
      this.Part_42 = new ModelRenderer(this, 16, 12);
      this.Part_42.addBox(0.0F, 0.0F, 0.0F, 2, 1, 2);
      this.Part_42.setRotationPoint(38.75F, 2.03F, -0.5F);
      this.Part_42.setTextureSize(128, 64);
      this.Part_42.mirror = true;
      this.setRotation(this.Part_42, 0.0F, 0.0F, 0.0F);
      this.Part_43 = new ModelRenderer(this, 27, 18);
      this.Part_43.addBox(0.0F, 0.0F, 0.0F, 3, 1, 2);
      this.Part_43.setRotationPoint(40.75F, 2.05F, -0.5F);
      this.Part_43.setTextureSize(128, 64);
      this.Part_43.mirror = true;
      this.setRotation(this.Part_43, 0.0F, 0.0F, 0.715585F);
      this.Part_44 = new ModelRenderer(this, 14, 8);
      this.Part_44.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1);
      this.Part_44.setRotationPoint(37.75F, 1.0F, 0.0F);
      this.Part_44.setTextureSize(128, 64);
      this.Part_44.mirror = true;
      this.setRotation(this.Part_44, 0.0F, 0.0F, 0.0F);
      this.Part_45 = new ModelRenderer(this, 28, 12);
      this.Part_45.addBox(0.0F, 0.0F, 0.0F, 3, 2, 1);
      this.Part_45.setRotationPoint(38.75F, 1.0F, 0.0F);
      this.Part_45.setTextureSize(128, 64);
      this.Part_45.mirror = true;
      this.setRotation(this.Part_45, 0.0F, 0.0F, 0.0F);
      this.Part_46 = new ModelRenderer(this, 40, 12);
      this.Part_46.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1);
      this.Part_46.setRotationPoint(41.75F, 1.0F, 0.0F);
      this.Part_46.setTextureSize(128, 64);
      this.Part_46.mirror = true;
      this.setRotation(this.Part_46, 0.0F, 0.0F, 0.0F);
      this.Part_47 = new ModelRenderer(this, 59, 0);
      this.Part_47.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
      this.Part_47.setRotationPoint(44.0F, -2.0F, -0.5F);
      this.Part_47.setTextureSize(128, 64);
      this.Part_47.mirror = true;
      this.setRotation(this.Part_47, 0.0F, 0.0F, 0.0F);
      this.Part_48 = new ModelRenderer(this, 59, 5);
      this.Part_48.addBox(0.0F, 0.0F, 0.0F, 1, 6, 3);
      this.Part_48.setRotationPoint(44.0F, -1.5F, -1.0F);
      this.Part_48.setTextureSize(128, 64);
      this.Part_48.mirror = true;
      this.setRotation(this.Part_48, 0.0F, 0.0F, 0.0F);
      this.Part_49 = new ModelRenderer(this, 59, 15);
      this.Part_49.addBox(0.0F, 0.0F, 0.0F, 1, 1, 2);
      this.Part_49.setRotationPoint(44.0F, 4.0F, -0.5F);
      this.Part_49.setTextureSize(128, 64);
      this.Part_49.mirror = true;
      this.setRotation(this.Part_49, 0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      GL11.glTranslated(1.5D, -0.25D, 0.2D);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
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
      this.Part_22.render(f5);
      this.Part_23.render(f5);
      this.Part_24.render(f5);
      this.Part_25.render(f5);
      this.Part_26.render(f5);
      this.Part_27.render(f5);
      this.Part_28.render(f5);
      this.Part_29.render(f5);
      this.Part_30.render(f5);
      this.Part_31.render(f5);
      this.Part_32.render(f5);
      this.Part_33.render(f5);
      this.Part_34.render(f5);
      this.Part_35.render(f5);
      this.Part_36.render(f5);
      this.Part_37.render(f5);
      this.Part_38.render(f5);
      this.Part_39.render(f5);
      this.Part_40.render(f5);
      this.Part_41.render(f5);
      this.Part_42.render(f5);
      this.Part_43.render(f5);
      this.Part_44.render(f5);
      this.Part_45.render(f5);
      this.Part_46.render(f5);
      this.Part_47.render(f5);
      this.Part_48.render(f5);
      this.Part_49.render(f5);
   }

   private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
   }

   public void renderAmmo(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.Part_17.render(f5);
      this.Part_21.render(f5);
   }
}
