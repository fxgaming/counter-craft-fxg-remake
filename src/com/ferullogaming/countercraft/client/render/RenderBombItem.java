package com.ferullogaming.countercraft.client.render;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.model.ModelTheBomb;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.Team;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderBombItem implements IItemRenderer {
   public static float particleTick = 0.0F;
   public Minecraft mc = Minecraft.getMinecraft();
   public ModelBase model = new ModelTheBomb();

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.INVENTORY;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/bombmodel.png"));
      Entity entity = null;
      GL11.glPushMatrix();
      if (data.length >= 2) {
         entity = (Entity)data[1];
      }

      if (type == ItemRenderType.EQUIPPED) {
         GL11.glRotated(190.0D, 0.0D, 0.0D, 1.0D);
         GL11.glTranslated(-0.1D, -0.25D, -0.3D);
         GL11.glRotated(-100.0D, 0.0D, 1.0D, 0.0D);
      } else {
         double scale;
         if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
            GL11.glTranslated(-0.5D, -1.0D, -1.0D);
            GL11.glRotated(50.0D, 0.0D, 0.0D, 1.0D);
            GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
            GL11.glTranslated(-0.8D, 0.2D, -0.9D);
            GL11.glRotated(-10.0D, 1.0D, 0.0D, 0.0D);
            scale = 0.7D;
            GL11.glScaled(scale, scale, scale);
         } else if (type == ItemRenderType.ENTITY) {
            GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
            GL11.glTranslated(-0.5D, 0.0D, -0.5D);
            scale = 1.2D;
            GL11.glScaled(scale, scale, scale);
         } else if (type == ItemRenderType.INVENTORY) {
            scale = 11.0D;
            GL11.glScaled(scale, scale, scale);
            GL11.glTranslated(1.68D, 0.35D, 1.0D);
            GL11.glRotated(45.0D, 0.0D, 0.0D, 1.0D);
            GL11.glRotated(45.0D, 1.0D, 0.0D, 0.0D);
            GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
            GL11.glTranslated(0.0D, 0.5D, 0.5D);
         }
      }

      this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      if (type == ItemRenderType.ENTITY) {
         boolean flag = true;
         IGame game = GameManager.instance().currentClientGame;
         if (game != null && game.getPlayerEventHandler().getPlayerTeam((EntityPlayer)this.mc.thePlayer) != null) {
            Team team = game.getPlayerEventHandler().getPlayerTeam((EntityPlayer)this.mc.thePlayer);
            if (team.teamName.equalsIgnoreCase("blue")) {
               flag = false;
            }
         }

         if (flag) {
            String bombRes = "countercraft:textures/items/bomb.png";
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)particleTick;
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)particleTick;
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)particleTick;
            double d3 = 2.5D;
            CCRenderHelper.renderImageFacingPlayer(new ResourceLocation(bombRes), d0, d1 + d3, d2, particleTick, 80, 80, "0xffffff");
         }
      }

   }
}
