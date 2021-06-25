package com.ferullogaming.countercraft.client.render;

import com.ferullogaming.countercraft.client.model.ModelTheBomb;
import com.ferullogaming.countercraft.entity.EntityBomb;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBombEntity extends Render {
   public ModelBase bombModel = new ModelTheBomb();

   public void renderBomb(EntityBomb par1Bomb, double par2, double par4, double par6, float par8, float par9) {
      super.renderManager.renderEngine.bindTexture(this.getEntityTexture(par1Bomb));
      GL11.glPushMatrix();
      GL11.glTranslated(par2 - 0.22D, par4 + 0.11D, par6 + 0.18D);
      double scale = 0.06D;
      GL11.glScaled(scale, scale, scale);
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      float par7 = 0.625F;
      this.bombModel.render(par1Bomb, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.625F);
      GL11.glPopMatrix();
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.renderBomb((EntityBomb)par1Entity, par2, par4, par6, par8, par9);
   }

   protected ResourceLocation getEntityTexture(Entity entity) {
      return new ResourceLocation("countercraft:textures/models/bombmodel.png");
   }
}
