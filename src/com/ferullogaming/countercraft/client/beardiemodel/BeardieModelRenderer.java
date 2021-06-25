package com.ferullogaming.countercraft.client.beardiemodel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class BeardieModelRenderer extends ModelRenderer {
   public final String field_78802_n;
   public boolean bright;
   public int field_78803_o;
   public int field_78813_p;
   public boolean field_78809_i;
   public boolean field_78806_j;
   public int discriminator;
   public int discriminator2;
   public boolean field_78807_k;
   public List field_78804_l;
   public List field_78805_m;
   public float field_82906_o;
   public float field_82908_p;
   public float field_82907_q;
   public float textureSizeY;
   public float textureSizeX;
   public float prevRotationPointX;
   public float prevRotationPointY;
   public float prevRotationPointZ;
   public float prevRotationX;
   public float prevRotationY;
   public float prevRotationZ;
   public float[][] vectorPos;
   public float[][] uv;
   public boolean complexUV;
   public boolean converted;
   public float scale;
   public boolean isChild;
   public ResourceLocation chilTex;
   private boolean compiled;
   private int displayList;
   private ModelBase baseModel;
   private int displayListC;

   public BeardieModelRenderer(ModelBase par1ModelBase, String par2Str) {
      super(par1ModelBase, par2Str);
      this.bright = false;
      this.discriminator = 0;
      this.discriminator2 = 0;
      this.field_78805_m = new ArrayList();
      this.vectorPos = new float[][]{{1.0F, 1.0F, 1.0F}, {0.0F, 1.0F, 1.0F}, {1.0F, 0.0F, 1.0F}, {0.0F, 0.0F, 1.0F}, {1.0F, 1.0F, 0.0F}, {0.0F, 1.0F, 0.0F}, {1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 0.0F}};
      this.uv = new float[][]{{0.0F, 0.0F, 1.0F, 1.0F}, {0.0F, 0.0F, 1.0F, 1.0F}, {0.0F, 0.0F, 1.0F, 1.0F}, {0.0F, 0.0F, 1.0F, 1.0F}, {0.0F, 0.0F, 1.0F, 1.0F}, {0.0F, 0.0F, 1.0F, 1.0F}};
      this.complexUV = false;
      this.converted = false;
      this.scale = 1.0F;
      this.isChild = false;
      super.textureWidth = 64.0F;
      super.textureHeight = 32.0F;
      this.field_78806_j = true;
      this.field_78804_l = new ArrayList();
      this.baseModel = par1ModelBase;
      par1ModelBase.boxList.add(this);
      this.field_78802_n = par2Str;
      this.setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
   }

   public BeardieModelRenderer(ModelBase par1ModelBase) {
      this(par1ModelBase, (String)null);
   }

   public BeardieModelRenderer(ModelBase par1ModelBase, int par2, int par3) {
      this(par1ModelBase);
      this.setTextureOffset(par2, par3);
   }

   public BeardieModelRenderer(ModelBase par1ModelBase, BeardieModelRenderer bmr) {
      this(par1ModelBase, bmr.field_78802_n);
      this.field_78804_l = bmr.field_78804_l;
      this.field_78803_o = bmr.field_78803_o;
      this.field_78813_p = bmr.field_78813_p;
      this.field_78805_m = bmr.field_78805_m;
   }

   public void addChild(BeardieModelRenderer par1ModelRenderer) {
      if (this.field_78805_m == null) {
         this.field_78805_m = new ArrayList();
      }

      this.field_78805_m.add(par1ModelRenderer);
   }

   public void actLikeChildOf(ModelRenderer par) {
      this.resetPos();
      this.resetRot();
      super.rotateAngleX += par.rotateAngleX;
      super.rotateAngleY += par.rotateAngleY;
      super.rotateAngleZ += par.rotateAngleZ;
      super.rotationPointX += par.rotationPointX;
      super.rotationPointY += par.rotationPointY;
      super.rotationPointZ += par.rotationPointZ;
   }

   public void actLikeChildOfNo(ModelRenderer par) {
      super.rotateAngleX += par.rotateAngleX;
      super.rotateAngleY += par.rotateAngleY;
      super.rotateAngleZ += par.rotateAngleZ;
      super.rotationPointX += par.rotationPointX;
      super.rotationPointY += par.rotationPointY;
      super.rotationPointZ += par.rotationPointZ;
   }

   public BeardieModelRenderer setTextureOffset(int par1, int par2) {
      this.field_78803_o = par1;
      this.field_78813_p = par2;
      return this;
   }

   public BeardieModelRenderer addBox(float p_78789_1_, float p_78789_2_, float p_78789_3_, int p_78789_4_, int p_78789_5_, int p_78789_6_) {
      return this.addBox(p_78789_1_, p_78789_2_, p_78789_3_, (float)p_78789_4_, (float)p_78789_5_, (float)p_78789_6_);
   }

   public BeardieModelRenderer addBox(String par1Str, float par2, float par3, float par4, float par5, float par6, float par7) {
      par1Str = this.field_78802_n + "." + par1Str;
      TextureOffset textureoffset = this.baseModel.getTextureOffset(par1Str);
      this.setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
      this.field_78804_l.add((new BeardieModelBox(this, this.field_78803_o, this.field_78813_p, par2, par3, par4, par5, par6, par7, 0.0F)).func_78244_a(par1Str));
      return this;
   }

   public void setUV(float[][] uv) {
      this.uv = uv;
      this.complexUV = true;
   }

   public BeardieModelRenderer addShape(float[][] vecs, double width, double height, double length) {
      return this.addShape(0.0F, 0.0F, 0.0F, vecs, (float)width, (float)height, (float)length);
   }

   public BeardieModelRenderer addShape(float par2, float par3, float par4, float[][] vecs, float width, float height, float length) {
      this.field_78804_l.add(new BeardieModelBox(this, this.field_78803_o, this.field_78813_p, par2, par3, par4, vecs, width, height, length, 0.0F));
      return this;
   }

   public BeardieModelRenderer addBox(float par1, float par2, float par3, float par4, float par5, float par6) {
      this.field_78804_l.add(new BeardieModelBox(this, this.field_78803_o, this.field_78813_p, par1, par2, par3, par4, par5, par6, 0.0F));
      return this;
   }

   public void addBox(float par1, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.field_78804_l.add(new BeardieModelBox(this, this.field_78803_o, this.field_78813_p, par1, par2, par3, par4, par5, par6, par7));
   }

   public void resetPos() {
      super.rotationPointX = this.prevRotationPointX;
      super.rotationPointY = this.prevRotationPointY;
      super.rotationPointZ = this.prevRotationPointZ;
   }

   public void resetRot() {
      super.rotateAngleX = this.prevRotationX;
      super.rotateAngleY = this.prevRotationY;
      super.rotateAngleZ = this.prevRotationZ;
   }

   public void resetPos(int z) {
      super.rotationPointX = this.prevRotationPointX;
      super.rotationPointY = this.prevRotationPointY;
      super.rotationPointZ = this.prevRotationPointZ;
      super.rotationPointZ -= (float)z;
   }

   public void setRotationPoint(float par1, float par2, float par3) {
      super.rotationPointX = par1;
      super.rotationPointY = par2;
      super.rotationPointZ = par3;
      this.prevRotationPointX = par1;
      this.prevRotationPointY = par2;
      this.prevRotationPointZ = par3;
   }

   public void setRotation(float x, float y, float z) {
      super.rotateAngleX = x;
      super.rotateAngleY = y;
      super.rotateAngleZ = z;
      this.prevRotationX = x;
      this.prevRotationY = y;
      this.prevRotationZ = z;
   }

   @SideOnly(Side.CLIENT)
   public void render(float par1) {
      this.render(par1, true);
   }

   @SideOnly(Side.CLIENT)
   public void render(float par1, boolean list) {
      GL11.glPushMatrix();
      boolean mud = false;
      if (!this.field_78807_k && this.field_78806_j) {
         if (!this.compiled && list) {
            this.compileDisplayList(par1);
         }

         int i;
         Tessellator tessellator;
         int x;
         BeardieModelBox box;
         if (super.rotateAngleX == 0.0F && super.rotateAngleY == 0.0F && super.rotateAngleZ == 0.0F) {
            if (super.rotationPointX == 0.0F && super.rotationPointY == 0.0F && super.rotationPointZ == 0.0F) {
               GL11.glScalef(this.scale, this.scale, this.scale);
               if (list) {
                  GL11.glCallList(this.displayList);
               } else {
                  tessellator = Tessellator.instance;
                  if (this.textureSizeX == 0.0F && this.textureSizeY == 0.0F) {
                     if (this.complexUV && this.uv != null && this.uv.length >= 8) {
                        for(x = 0; x < this.field_78804_l.size(); ++x) {
                           box = (BeardieModelBox)this.field_78804_l.get(x);
                           box.bright = this.bright;
                           box.renderUV(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.uv);
                        }
                     } else {
                        for(x = 0; x < this.field_78804_l.size(); ++x) {
                           box = (BeardieModelBox)this.field_78804_l.get(x);
                           box.bright = this.bright;
                           box.render(tessellator, par1);
                        }
                     }
                  } else {
                     for(x = 0; x < this.field_78804_l.size(); ++x) {
                        box = (BeardieModelBox)this.field_78804_l.get(x);
                        box.bright = this.bright;
                        box.renderTex(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.textureSizeX, this.textureSizeY);
                     }
                  }
               }

               if (list) {
                  GL11.glCallList(this.displayListC);
               } else {
                  if (this.chilTex != null) {
                     Minecraft.getMinecraft().renderEngine.bindTexture(this.chilTex);
                  }

                  if (this.field_78805_m != null) {
                     for(i = 0; i < this.field_78805_m.size(); ++i) {
                        ((BeardieModelRenderer)this.field_78805_m.get(i)).bright = this.bright;
                        ((BeardieModelRenderer)this.field_78805_m.get(i)).render(par1, false);
                     }
                  }
               }
            } else {
               GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);
               GL11.glScalef(this.scale, this.scale, this.scale);
               if (list) {
                  GL11.glCallList(this.displayList);
               } else {
                  tessellator = Tessellator.instance;
                  if (this.textureSizeX == 0.0F && this.textureSizeY == 0.0F) {
                     if (this.complexUV && this.uv != null && this.uv.length >= 8) {
                        for(x = 0; x < this.field_78804_l.size(); ++x) {
                           box = (BeardieModelBox)this.field_78804_l.get(x);
                           box.bright = this.bright;
                           box.renderUV(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.uv);
                        }
                     } else {
                        for(x = 0; x < this.field_78804_l.size(); ++x) {
                           box = (BeardieModelBox)this.field_78804_l.get(x);
                           box.bright = this.bright;
                           box.render(tessellator, par1);
                        }
                     }
                  } else {
                     for(x = 0; x < this.field_78804_l.size(); ++x) {
                        box = (BeardieModelBox)this.field_78804_l.get(x);
                        box.bright = this.bright;
                        box.renderTex(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.textureSizeX, this.textureSizeY);
                     }
                  }
               }

               if (list) {
                  GL11.glCallList(this.displayListC);
               } else {
                  if (this.chilTex != null) {
                     Minecraft.getMinecraft().renderEngine.bindTexture(this.chilTex);
                  }

                  if (this.field_78805_m != null) {
                     for(i = 0; i < this.field_78805_m.size(); ++i) {
                        ((BeardieModelRenderer)this.field_78805_m.get(i)).bright = this.bright;
                        ((BeardieModelRenderer)this.field_78805_m.get(i)).render(par1, false);
                     }
                  }
               }

               GL11.glTranslatef(-super.rotationPointX * par1, -super.rotationPointY * par1, -super.rotationPointZ * par1);
            }
         } else {
            GL11.glPushMatrix();
            GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);
            GL11.glTranslatef(this.field_82906_o * par1, this.field_82908_p * par1, this.field_82907_q * par1);
            if (super.rotateAngleZ != 0.0F) {
               GL11.glRotatef(super.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            if (super.rotateAngleY != 0.0F) {
               GL11.glRotatef(super.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (super.rotateAngleX != 0.0F) {
               GL11.glRotatef(super.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glTranslatef(-this.field_82906_o * par1, -this.field_82908_p * par1, -this.field_82907_q * par1);
            GL11.glScalef(this.scale, this.scale, this.scale);
            if (list) {
               GL11.glCallList(this.displayList);
            } else {
               tessellator = Tessellator.instance;
               if (this.textureSizeX == 0.0F && this.textureSizeY == 0.0F) {
                  if (this.complexUV && this.uv != null && this.uv.length >= 8) {
                     for(x = 0; x < this.field_78804_l.size(); ++x) {
                        box = (BeardieModelBox)this.field_78804_l.get(x);
                        box.bright = this.bright;
                        box.renderUV(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.uv);
                     }
                  } else {
                     for(x = 0; x < this.field_78804_l.size(); ++x) {
                        box = (BeardieModelBox)this.field_78804_l.get(x);
                        box.bright = this.bright;
                        box.render(tessellator, par1);
                     }
                  }
               } else {
                  for(x = 0; x < this.field_78804_l.size(); ++x) {
                     box = (BeardieModelBox)this.field_78804_l.get(x);
                     box.bright = this.bright;
                     box.renderTex(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.textureSizeX, this.textureSizeY);
                  }
               }
            }

            if (list) {
               GL11.glCallList(this.displayListC);
            } else {
               if (this.chilTex != null) {
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.chilTex);
               }

               if (this.field_78805_m != null) {
                  for(i = 0; i < this.field_78805_m.size(); ++i) {
                     ((BeardieModelRenderer)this.field_78805_m.get(i)).bright = this.bright;
                     ((BeardieModelRenderer)this.field_78805_m.get(i)).render(par1, false);
                  }
               }
            }

            GL11.glPopMatrix();
         }
      }

      GL11.glPopMatrix();
   }

   @SideOnly(Side.CLIENT)
   public void renderWithRotation(float par1) {
      if (!this.field_78807_k && this.field_78806_j) {
         if (!this.compiled) {
            this.compileDisplayList(par1);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);
         if (super.rotateAngleY != 0.0F) {
            GL11.glRotatef(super.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
         }

         if (super.rotateAngleX != 0.0F) {
            GL11.glRotatef(super.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
         }

         if (super.rotateAngleZ != 0.0F) {
            GL11.glRotatef(super.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
         }

         GL11.glCallList(this.displayList);
         GL11.glPopMatrix();
      }

   }

   @SideOnly(Side.CLIENT)
   public void postRender(float par1) {
      if (!this.field_78807_k && this.field_78806_j) {
         if (!this.compiled) {
            this.compileDisplayList(par1);
         }

         if (super.rotateAngleX == 0.0F && super.rotateAngleY == 0.0F && super.rotateAngleZ == 0.0F) {
            if (super.rotationPointX != 0.0F || super.rotationPointY != 0.0F || super.rotationPointZ != 0.0F) {
               GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);
            }
         } else {
            GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);
            if (super.rotateAngleZ != 0.0F) {
               GL11.glRotatef(super.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            if (super.rotateAngleY != 0.0F) {
               GL11.glRotatef(super.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (super.rotateAngleX != 0.0F) {
               GL11.glRotatef(super.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   private void compileDisplayList(float par1) {
      this.displayList = GLAllocation.generateDisplayLists(1);
      GL11.glNewList(this.displayList, 4864);
      Tessellator tessellator = Tessellator.instance;
      int i;
      BeardieModelBox box;
      if (this.textureSizeX == 0.0F && this.textureSizeY == 0.0F) {
         if (this.complexUV && this.uv != null && this.uv.length >= 8) {
            for(i = 0; i < this.field_78804_l.size(); ++i) {
               box = (BeardieModelBox)this.field_78804_l.get(i);
               box.bright = this.bright;
               box.renderUV(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.uv);
            }
         } else {
            for(i = 0; i < this.field_78804_l.size(); ++i) {
               box = (BeardieModelBox)this.field_78804_l.get(i);
               box.bright = this.bright;
               box.render(tessellator, par1);
            }
         }
      } else {
         for(i = 0; i < this.field_78804_l.size(); ++i) {
            box = (BeardieModelBox)this.field_78804_l.get(i);
            box.bright = this.bright;
            box.renderTex(tessellator, par1, (float)this.field_78803_o, (float)this.field_78813_p, this.textureSizeX, this.textureSizeY);
         }
      }

      GL11.glEndList();
      this.compiled = true;
      this.displayListC = GLAllocation.generateDisplayLists(1);
      GL11.glNewList(this.displayListC, 4864);
      if (this.field_78805_m != null) {
         for(i = 0; i < this.field_78805_m.size(); ++i) {
            ((BeardieModelRenderer)this.field_78805_m.get(i)).bright = this.bright;
            ((BeardieModelRenderer)this.field_78805_m.get(i)).render(par1, false);
         }
      }

      GL11.glEndList();
   }

   public BeardieModelRenderer setTextureSize(int par1, int par2) {
      super.textureWidth = (float)par1;
      super.textureHeight = (float)par2;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public void renderBlock(float par1) {
      Tessellator tessellator = Tessellator.instance;
      if (!this.field_78807_k && this.field_78806_j) {
         GL11.glTranslatef(this.field_82906_o, this.field_82908_p, this.field_82907_q);
         int i;
         int x;
         if (super.rotateAngleX == 0.0F && super.rotateAngleY == 0.0F && super.rotateAngleZ == 0.0F) {
            if (super.rotationPointX == 0.0F && super.rotationPointY == 0.0F && super.rotationPointZ == 0.0F) {
               for(x = 0; x < this.field_78804_l.size(); ++x) {
                  ((BeardieModelBox)this.field_78804_l.get(x)).render(tessellator, par1);
               }

               if (this.field_78805_m != null) {
                  for(i = 0; i < this.field_78805_m.size(); ++i) {
                     ((BeardieModelRenderer)this.field_78805_m.get(i)).renderBlock(par1);
                  }
               }
            } else {
               GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);

               for(x = 0; x < this.field_78804_l.size(); ++x) {
                  ((BeardieModelBox)this.field_78804_l.get(x)).render(tessellator, par1);
               }

               if (this.field_78805_m != null) {
                  for(i = 0; i < this.field_78805_m.size(); ++i) {
                     ((BeardieModelRenderer)this.field_78805_m.get(i)).renderBlock(par1);
                  }
               }

               GL11.glTranslatef(-super.rotationPointX * par1, -super.rotationPointY * par1, -super.rotationPointZ * par1);
            }
         } else {
            GL11.glPushMatrix();
            GL11.glTranslatef(super.rotationPointX * par1, super.rotationPointY * par1, super.rotationPointZ * par1);
            if (super.rotateAngleZ != 0.0F) {
               GL11.glRotatef(super.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            if (super.rotateAngleY != 0.0F) {
               GL11.glRotatef(super.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (super.rotateAngleX != 0.0F) {
               GL11.glRotatef(super.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            for(x = 0; x < this.field_78804_l.size(); ++x) {
               ((BeardieModelBox)this.field_78804_l.get(x)).render(tessellator, par1);
            }

            if (this.field_78805_m != null) {
               for(i = 0; i < this.field_78805_m.size(); ++i) {
                  ((BeardieModelRenderer)this.field_78805_m.get(i)).renderBlock(par1);
               }
            }

            GL11.glPopMatrix();
         }

         GL11.glTranslatef(-this.field_82906_o, -this.field_82908_p, -this.field_82907_q);
      }

   }
}
