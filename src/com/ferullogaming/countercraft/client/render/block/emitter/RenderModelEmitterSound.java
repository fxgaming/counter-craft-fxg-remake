package com.ferullogaming.countercraft.client.render.block.emitter;

import com.ferullogaming.countercraft.client.model.emitter.ModelEmitter;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderModelEmitterSound extends TileEntitySpecialRenderer {
   private final ModelEmitter model = new ModelEmitter();
   private final ResourceLocation textureLocation = new ResourceLocation("countercraft:textures/models/emitters/sound.png");

   public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
      GL11.glPushMatrix();
      GL11.glRotatef(180.0F, 0.1F, 0.0F, 0.0F);
      int rotation = 0;
      GL11.glRotatef((float)rotation, 0.0F, -0.0F, 0.0F);
      switch(tileentity.getBlockMetadata() % 4) {
      case 0:
         rotation = 0;
         break;
      case 1:
         rotation = 90;
         break;
      case 2:
         rotation = 180;
         break;
      case 3:
         rotation = 270;
      }

      GL11.glRotatef((float)rotation, 0.0F, 1.0F, 0.0F);
      GL11.glPopMatrix();
      GL11.glPopMatrix();
   }
}
