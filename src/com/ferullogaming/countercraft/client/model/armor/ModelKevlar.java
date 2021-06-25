package com.ferullogaming.countercraft.client.model.armor;

import com.ferullogaming.countercraft.client.beardiemodel.BeardieModelRenderer;
import com.ferullogaming.countercraft.client.beardiemodel.ModelBeardieBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelKevlar extends ModelBeardieBase {
   BeardieModelRenderer bodyModel0;
   BeardieModelRenderer bodyModel1;
   BeardieModelRenderer bodyModel2;
   BeardieModelRenderer bodyModel3;
   BeardieModelRenderer bodyModel4;
   BeardieModelRenderer bodyModel5;
   BeardieModelRenderer bodyModel6;
   BeardieModelRenderer bodyModel7;
   BeardieModelRenderer bodyModel8;
   BeardieModelRenderer bodyModel9;
   BeardieModelRenderer bodyModel10;
   BeardieModelRenderer bodyModel11;
   BeardieModelRenderer bodyModel12;
   BeardieModelRenderer bodyModel13;
   BeardieModelRenderer bodyModel14;
   BeardieModelRenderer bodyModel15;
   BeardieModelRenderer bodyModel16;
   BeardieModelRenderer bodyModel17;
   BeardieModelRenderer bodyModel18;
   BeardieModelRenderer bodyModel19;
   BeardieModelRenderer bodyModel20;

   public ModelKevlar() {
      super.textureWidth = 64;
      super.textureHeight = 64;
      this.bodyModel0 = new BeardieModelRenderer(this, 1, 1);
      this.bodyModel0.addShape(-2.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 3.0F, 2.0F);
      this.bodyModel0.setRotationPoint(1.8F, -36.66667F, 0.0F);
      this.bodyModel0.setRotation(0.0F, 0.1570796F, 0.0F);
      this.bodyModel1 = new BeardieModelRenderer(this, 17, 1);
      this.bodyModel1.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 3.0F, 2.0F);
      this.bodyModel1.setRotationPoint(6.2F, -36.66667F, 0.0F);
      this.bodyModel1.setRotation(0.0F, -0.1570796F, 0.0F);
      this.bodyModel2 = new BeardieModelRenderer(this, 33, 1);
      this.bodyModel2.addShape(-2.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 3.0F, 2.0F);
      this.bodyModel2.setRotationPoint(3.9F, -36.66667F, -0.2F);
      this.bodyModel2.setRotation(0.0F, 0.1047198F, 0.0F);
      this.bodyModel3 = new BeardieModelRenderer(this, 49, 1);
      this.bodyModel3.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 3.0F, 2.0F);
      this.bodyModel3.setRotationPoint(4.1F, -36.66667F, -0.2F);
      this.bodyModel3.setRotation(0.0F, -0.1047198F, 0.0F);
      this.bodyModel4 = new BeardieModelRenderer(this, 1, 9);
      this.bodyModel4.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel4.setRotationPoint(1.066667F, -35.86666F, 6.133333F);
      this.bodyModel4.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel5 = new BeardieModelRenderer(this, 17, 9);
      this.bodyModel5.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 1.0F, 5.0F);
      this.bodyModel5.setRotationPoint(6.0F, -45.26667F, 1.8F);
      this.bodyModel5.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel6 = new BeardieModelRenderer(this, 33, 9);
      this.bodyModel6.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 8.0F, 12.0F, 1.0F);
      this.bodyModel6.setRotationPoint(0.0F, -45.0F, 6.0F);
      this.bodyModel6.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel7 = new BeardieModelRenderer(this, 1, 17);
      this.bodyModel7.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel7.setRotationPoint(1.066667F, -37.4F, 6.133333F);
      this.bodyModel7.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel8 = new BeardieModelRenderer(this, 17, 17);
      this.bodyModel8.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel8.setRotationPoint(1.066667F, -43.4F, 0.9333333F);
      this.bodyModel8.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel9 = new BeardieModelRenderer(this, 1, 25);
      this.bodyModel9.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 3.0F, 1.0F);
      this.bodyModel9.setRotationPoint(1.066667F, -40.26667F, 0.4F);
      this.bodyModel9.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel10 = new BeardieModelRenderer(this, 17, 25);
      this.bodyModel10.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel10.setRotationPoint(1.066667F, -43.4F, 6.133333F);
      this.bodyModel10.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel11 = new BeardieModelRenderer(this, 33, 25);
      this.bodyModel11.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel11.setRotationPoint(1.066667F, -41.86666F, 6.133333F);
      this.bodyModel11.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel12 = new BeardieModelRenderer(this, 49, 25);
      this.bodyModel12.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel12.setRotationPoint(1.066667F, -40.4F, 6.133333F);
      this.bodyModel12.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel13 = new BeardieModelRenderer(this, 1, 33);
      this.bodyModel13.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel13.setRotationPoint(1.066667F, -38.86666F, 6.133333F);
      this.bodyModel13.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel14 = new BeardieModelRenderer(this, 57, 9);
      this.bodyModel14.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 1.0F, 1.0F);
      this.bodyModel14.setRotationPoint(6.0F, -45.0F, 1.0F);
      this.bodyModel14.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel15 = new BeardieModelRenderer(this, 17, 33);
      this.bodyModel15.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 1.0F, 5.0F);
      this.bodyModel15.setRotationPoint(0.0F, -45.26667F, 1.8F);
      this.bodyModel15.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel16 = new BeardieModelRenderer(this, 33, 33);
      this.bodyModel16.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 1.0F, 8.0F, 5.0F);
      this.bodyModel16.setRotationPoint(7.266667F, -41.0F, 1.333333F);
      this.bodyModel16.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel17 = new BeardieModelRenderer(this, 57, 17);
      this.bodyModel17.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 2.0F, 1.0F, 1.0F);
      this.bodyModel17.setRotationPoint(0.0F, -45.0F, 1.0F);
      this.bodyModel17.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel18 = new BeardieModelRenderer(this, 1, 41);
      this.bodyModel18.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 8.0F, 11.0F, 1.0F);
      this.bodyModel18.setRotationPoint(0.0F, -44.0F, 1.0F);
      this.bodyModel18.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel19 = new BeardieModelRenderer(this, 49, 33);
      this.bodyModel19.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 1.0F, 8.0F, 5.0F);
      this.bodyModel19.setRotationPoint(-0.2666667F, -41.0F, 1.333333F);
      this.bodyModel19.setRotation(0.0F, 0.0F, 0.0F);
      this.bodyModel20 = new BeardieModelRenderer(this, 25, 49);
      this.bodyModel20.addShape(0.0F, 0.0F, 0.0F, new float[][]{{0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}}, 6.0F, 1.0F, 1.0F);
      this.bodyModel20.setRotationPoint(1.066667F, -41.86666F, 0.9333333F);
      this.bodyModel20.setRotation(0.0F, 0.0F, 0.0F);
   }

   public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.render(entity, f, f1, f2, f3, f4, f5);
      this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      this.bodyModel0.render(f5);
      this.bodyModel1.render(f5);
      this.bodyModel2.render(f5);
      this.bodyModel3.render(f5);
      this.bodyModel4.render(f5);
      this.bodyModel5.render(f5);
      this.bodyModel6.render(f5);
      this.bodyModel7.render(f5);
      this.bodyModel8.render(f5);
      this.bodyModel9.render(f5);
      this.bodyModel10.render(f5);
      this.bodyModel11.render(f5);
      this.bodyModel12.render(f5);
      this.bodyModel13.render(f5);
      this.bodyModel14.render(f5);
      this.bodyModel15.render(f5);
      this.bodyModel16.render(f5);
      this.bodyModel17.render(f5);
      this.bodyModel18.render(f5);
      this.bodyModel19.render(f5);
      this.bodyModel20.render(f5);
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
