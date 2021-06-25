package com.ferullogaming.countercraft.client.beardiemodel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelBeardieBase extends ModelBase {
   public BeardieModelRenderer bipedRightArm;
   public BeardieModelRenderer bipedLeftArm;
   public ArrayList pieces = new ArrayList();
   public ArrayList bullets = new ArrayList();
   public int existedTime;
   public float sPosX;
   public float sPosY;
   public float sPosZ;
   public float fPosX;
   public float fPosY;
   public float fPosZ;
   public float ePosX;
   public float ePosY;
   public float ePosZ;
   public float lhPosX = 6.5F;
   public float lhPosY = 7.2F;
   public float lhPosZ = 3.5F;
   public float rhPosX = -9.0F;
   public float rhPosY = 0.72F;
   public float rhPosZ = -2.0F;
   public float rhRotX = 0.0F;
   public float rhRotY = 0.0F;
   public float rhRotZ = 0.0F;
   public float lhRotX = 0.0F;
   public float lhRotY = 0.0F;
   public float lhRotZ = 0.0F;
   public float flamePosX = 20.0F;
   public float flamePosY = -4.0F;
   public float flamePosZ = 0.0F;
   public ModelBeardieBase.EjectDirection ejDir;
   public float scale;
   public float mOffX;
   public float mOffY;
   public float mOffZ;
   public String path;
   public Field brightness;
   public boolean isObfuscated;
   float rotFix;

   public ModelBeardieBase() {
      this.ejDir = ModelBeardieBase.EjectDirection.right;
      this.scale = 1.0F;
      this.rotFix = 57.29578F;
   }

   public void renderBullet() {
      for(int i = 0; i < this.bullets.size(); ++i) {
         ModelBeardieBase mb = (ModelBeardieBase)this.bullets.get(i);
         BeardieModelRenderer bullet = (BeardieModelRenderer)mb.pieces.get(0);
         bullet.render(0.0625F);
      }

   }

   public void render(Entity p_78088_1_, double p_78088_2_, double p_78088_3_, double p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
      GL11.glTranslatef(this.mOffX * 0.0625F, this.mOffY * 0.0625F, this.mOffZ * 0.0625F);

      for(int i = 0; i < this.pieces.size(); ++i) {
         BeardieModelRenderer piece = (BeardieModelRenderer)this.pieces.get(i);
         if (!piece.field_78802_n.contains("scopeGlass") && !piece.field_78802_n.contains("scopeOverlay")) {
            piece.render(0.0625F, false);
         }
      }

   }

   public static enum EjectDirection {
      right,
      left,
      up,
      down;
   }
}
