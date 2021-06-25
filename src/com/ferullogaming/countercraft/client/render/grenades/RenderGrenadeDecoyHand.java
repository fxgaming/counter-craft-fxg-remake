package com.ferullogaming.countercraft.client.render.grenades;

import com.ferullogaming.countercraft.AbstractClientPlayerCC;
import com.ferullogaming.countercraft.client.anim.AnimationManager;
import com.ferullogaming.countercraft.client.anim.GunAnimation;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.model.ModelGrenade;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderGrenadeDecoyHand implements IItemRenderer {
   public static ResourceLocation res = new ResourceLocation("countercraft:textures/models/grenadedecoy.png");
   public static double rotationX;
   public static double rotationY;
   public static double previousRotationX;
   public static double previousRotationY;
   public static boolean goUp = true;
   public static double upRotation;
   public static double upRotationSpeed;
   private static RenderItem renderItem = new RenderItem();
   public int rotationTimer;
   public float partialTick;
   protected ModelGrenade model = new ModelGrenade();
   private Minecraft mc = Minecraft.getMinecraft();

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      switch(type) {
      case EQUIPPED:
         return true;
      case EQUIPPED_FIRST_PERSON:
         return true;
      default:
         return false;
      }
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      EntityPlayer entityplayer = null;
      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotated(rotationY - 20.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(rotationX, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(upRotationSpeed, 0.0D, 0.0D, 1.0D);
      }

      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         entityplayer = (EntityPlayer)data[1];
         EntityClientPlayerMP entityclientplayermp = this.mc.thePlayer;
         PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(this.mc.thePlayer.username);
         if (!entityplayer.isBlocking() && !entityplayer.isSwingInProgress) {
            GL11.glPushMatrix();
            GunAnimation animation = AnimationManager.instance().getCurrentAnimation();
            if (animation != null) {
               animation.doRenderHand(itemstack, this.partialTick, true);
            }

            GL11.glEnable(32826);
            this.mc.getTextureManager().bindTexture((new AbstractClientPlayerCC(this.mc.thePlayer.worldObj, this.mc.thePlayer.username, cloudData)).getLocationSkin());
            GL11.glTranslatef(0.0F, 2.0F, 0.0F);
            GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(60.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(1.0F, 1.9F, 1.0F);
            GL11.glTranslatef(-1.3F, -1.0F, -0.0F);
            this.renderHandLocation(entityplayer, itemstack, true);
            Render render = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            float f11 = 2.0F;
            GL11.glScalef(f11, f11, f11);
            renderplayer.renderFirstPersonArm(this.mc.thePlayer);
            GL11.glPopMatrix();
            ++this.rotationTimer;
            if (this.rotationTimer > 2000) {
               this.rotationTimer = 0;
               goUp = !goUp;
            }

            if (this.rotationTimer < 1000) {
               upRotationSpeed += 0.005D;
            } else {
               upRotationSpeed -= 0.005D;
            }

            if (previousRotationX < (double)entityclientplayermp.rotationYaw) {
               previousRotationX = (double)entityclientplayermp.rotationYaw;
               rotationX -= 0.25D;
            }

            if (previousRotationX > (double)entityclientplayermp.rotationYaw) {
               previousRotationX = (double)entityclientplayermp.rotationYaw;
               rotationX += 0.25D;
            }

            if (previousRotationY < (double)entityclientplayermp.rotationPitch) {
               previousRotationY = (double)entityclientplayermp.rotationPitch;
               rotationY -= 0.2D;
            }

            if (previousRotationY > (double)entityclientplayermp.rotationPitch) {
               previousRotationY = (double)entityclientplayermp.rotationPitch;
               rotationY += 0.2D;
            }

            if (rotationX < 0.0D) {
               rotationX += 0.1D;
            }

            if (rotationX > 0.0D) {
               rotationX -= 0.1D;
            }

            if (rotationY < 0.0D) {
               rotationY += 0.1D;
            }

            if (rotationY > 0.0D) {
               rotationY -= 0.1D;
            }

            if (rotationX < -15.0D) {
               rotationX = -15.0D;
            }

            if (rotationX > 15.0D) {
               rotationX = 15.0D;
            }

            if (rotationY < 5.0D) {
               rotationY = 5.0D;
            }

            if (rotationY > 8.0D) {
               rotationY = 8.0D;
            }
         }
      } else {
         GL11.glTranslatef(-1.2F, -0.1F, -0.3F);
         GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
      }

      if (type == ItemRenderType.EQUIPPED) {
         entityplayer = (EntityPlayer)data[1];
      }

      Minecraft.getMinecraft().getTextureManager().bindTexture(res);
      GL11.glPushMatrix();
      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GunAnimation animation = AnimationManager.instance().getCurrentAnimation();
         if (animation != null) {
            animation.doRender(itemstack, this.partialTick);
         }
      }

      GL11.glScaled(1.2D, 1.2D, 1.2D);
      GL11.glRotatef(210.0F, 0.5F, 0.1F, 1.0F);
      GL11.glTranslated(-0.75D, -0.0D, 1.1D);
      this.model.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public void renderDisplayed(ItemStack par1ItemStack, double posx, double posy, double scale, boolean tilted, double rotation) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.getTextureManager().bindTexture(res);
      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(2929);
      GL11.glTranslated(posx + scale / 2.0D, posy + scale / 2.0D, 0.0D);
      GL11.glScaled(scale, -scale, scale);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(180.0D, 0.0D, 0.0D, 1.0D);
      GL11.glTranslated(-0.35D, -0.7D, 0.0D);
      GL11.glRotated(-55.0D, 0.0D, 0.0D, 1.0D);
      double scale2 = 1.0D;
      GL11.glScaled(scale2, scale2, scale2);
      GL11.glRotated(-rotation, 0.0D, 1.0D, 0.0D);
      this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glDisable(2929);
      GL11.glEnable(2884);
      GL11.glPopMatrix();
   }

   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      } else {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      }

   }
}
