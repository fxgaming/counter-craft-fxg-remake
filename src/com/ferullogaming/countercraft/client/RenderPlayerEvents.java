package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.render.hats.RenderHat;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class RenderPlayerEvents {
   private ResourceLocation tSkin0 = new ResourceLocation("countercraft:textures/mobs/skin_t_0.png");

   public static RenderPlayerEvents instance() {
      return CounterCraft.getClientManager().getTickHandler().getRenderer().renderPlayerEvents;
   }

   public void onPreRender(AbstractClientPlayer par1, ModelBiped par2, ModelBiped par3, ModelBiped par4, RenderPlayer par5) {
      ItemStack itemstack = par1.getCurrentEquippedItem();
      if (itemstack != null && itemstack.getItem() instanceof ItemGun && !par1.isSprinting()) {
         par2.aimedBow = par3.aimedBow = par4.aimedBow = true;
      }

   }

   public void onRenderSpecials(AbstractClientPlayer par1, ModelBiped par2, ModelBiped par3, ModelBiped par4, RenderPlayer par5) {
      PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)par1);
      PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(par1.username);
      boolean renderHat = false;
      RenderHat renderer;
      if (cloudData != null && !playerData.isGhost && !playerData.isInvisible) {
         CloudItemStack stack = cloudData.getItemDefault("hat", -1);
         if (stack != null) {
            GL11.glPushMatrix();
            renderer = (RenderHat)MinecraftForgeClient.getItemRenderer(stack.getItemStack(), ItemRenderType.ENTITY);
            par4.bipedHead.postRender(0.0625F);
            renderer.renderOnHead(stack.getItemStack());
            GL11.glPopMatrix();
            renderHat = true;
         }
      }

      if (!renderHat && !playerData.isGhost && !playerData.isInvisible && playerData.hatWearing != -1) {
         ItemStack stack = new ItemStack(playerData.hatWearing, 1, 0);
         GL11.glPushMatrix();
         renderer = (RenderHat)MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.ENTITY);
         par4.bipedHead.postRender(0.0625F);
         renderer.renderOnHead(stack);
         GL11.glPopMatrix();
      }
   }

   public void renderPlayerModel(RenderPlayer renderer, ModelBase model, EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7) {
      EntityPlayer player = (EntityPlayer)par1EntityLivingBase;
      boolean renderFaded = false;
      boolean renderInvis = false;
      if (renderer != null && model != null && player != null) {
         PlayerData data = PlayerDataHandler.getPlayerData(player);
         IGame game;
         if (GameManager.instance().currentClientGame != null) {
            game = GameManager.instance().currentClientGame;
            if (game.getPlayerEventHandler().isPlayerPresent(player.username)) {
               player.deathTime = 0;
               player.hurtTime = 0;
               int var1 = game.getPlayerGameData(player.username).getInteger("spawnPro");
               if (game.hasPlayerGameData(player.username) && var1 > 0) {
                  renderFaded = true;
               }
            }
         }

         if (data.isGhost || data.isInvisible) {
            renderInvis = true;
         }

         if (renderInvis) {
            model.setRotationAngles(par2, par3, par4, par5, par6, par7, par1EntityLivingBase);
         } else {
            if (renderFaded) {
               GL11.glPushMatrix();
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
               GL11.glDepthMask(false);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               GL11.glAlphaFunc(516, 0.003921569F);
               model.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
               GL11.glDisable(3042);
               GL11.glAlphaFunc(516, 0.1F);
               GL11.glPopMatrix();
               GL11.glDepthMask(true);
            } else {
               model.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
            }

            game = null;
            if (GameManager.instance().currentClientGame != null) {
               game = GameManager.instance().currentClientGame;
            }

            if (game != null && game.getPlayerEventHandler().isPlayerPresent(player.username) && game.getPlayerEventHandler().getPlayerTeam(player) != null) {
               String teamName = game.getPlayerEventHandler().getPlayerTeam(player).teamName;
               boolean binded = false;
               if (teamName.equalsIgnoreCase("blue") || teamName.equalsIgnoreCase("living")) {
                  Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("countercraft:textures/mobs/skin_ct_" + data.skinIndex + (data.isWearingHelmet() ? "_helmet" : "") + ".png"));
                  binded = true;
               }

               if (teamName.equalsIgnoreCase("red")) {
                  Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("countercraft:textures/mobs/skin_t_" + data.skinIndex + (data.isWearingHelmet() ? "_helmet" : "") + ".png"));
                  binded = true;
               }

               if (teamName.equalsIgnoreCase("players")) {
                  Minecraft.getMinecraft().renderEngine.bindTexture(this.tSkin0);
                  binded = true;
               }

               if (binded) {
                  GL11.glEnable(2884);
                  GL11.glDepthMask(false);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  GL11.glAlphaFunc(516, 0.003921569F);
                  float alpha = renderFaded ? 0.2F : 1.0F;
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
                  model.render(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
                  GL11.glDisable(3042);
                  GL11.glAlphaFunc(516, 0.1F);
                  GL11.glDepthMask(true);
               }
            }

         }
      }
   }

   public void onPostRender(AbstractClientPlayer par1, ModelBiped par2, ModelBiped par3, ModelBiped par4, RenderPlayer par5) {
   }

   public void onPostFirstPersonRender(EntityPlayer par1, RenderPlayer par2, ModelBiped par3) {
      IGame game = null;
      PlayerData data = PlayerDataHandler.getPlayerData(par1);
      if (GameManager.instance().currentClientGame != null) {
         game = GameManager.instance().currentClientGame;
      }

      if (game != null && game.getPlayerEventHandler() != null && game.getPlayerEventHandler().getPlayerTeam(par1) != null) {
         String teamName = game.getPlayerEventHandler().getPlayerTeam(par1).teamName;
         boolean binded = false;
         if (teamName.equalsIgnoreCase("blue") || teamName.equalsIgnoreCase("living")) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("countercraft:textures/mobs/skin_ct_" + data.skinIndex + (data.isWearingHelmet() ? "_helmet" : "") + ".png"));
            binded = true;
         }

         if (teamName.equalsIgnoreCase("red")) {
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("countercraft:textures/mobs/skin_t_" + data.skinIndex + (data.isWearingHelmet() ? "_helmet" : "") + ".png"));
            binded = true;
         }

         if (binded) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            par3.onGround = 0.0F;
            par3.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1);
            par3.bipedRightArm.render(0.0625F);
            GL11.glDisable(3042);
         }
      }
   }
}
