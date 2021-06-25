package com.ferullogaming.countercraft.client.render.block.decal;

import com.ferullogaming.countercraft.block.decal.TileEntityDecal;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderDecal extends TileEntitySpecialRenderer {
   private ResourceLocation textureLocation;

   public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
      TileEntityDecal decal = (TileEntityDecal)tileEntity;
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x + 1.0F, (float)y + 1.0F, (float)z + 0.99F);
      this.textureLocation = new ResourceLocation("countercraft:textures/blocks/decal/" + decal.getDecalName() + ".png");
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 0.1F, 0.0F, 0.0F);
      int rotation = 0;
      switch(tileEntity.getBlockMetadata() % 4) {
      case 0:
         rotation = 90;
         GL11.glTranslatef(-0.01F, 0.0F, 1.0F);
         break;
      case 1:
         rotation = 180;
         break;
      case 2:
         rotation = 270;
         GL11.glTranslatef(-0.98F, 0.0F, 0.0F);
         break;
      case 3:
         rotation = 0;
         GL11.glTranslatef(-1.0F, 0.0F, 0.98F);
      }

      GL11.glRotatef((float)rotation, 0.0F, 1.0F, 0.0F);
      CCRenderHelper.drawImage(0.0D, 0.0D, this.textureLocation, 1.0D, 1.0D);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
   }
}
