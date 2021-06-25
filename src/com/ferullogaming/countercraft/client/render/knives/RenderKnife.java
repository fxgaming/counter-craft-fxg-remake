package com.ferullogaming.countercraft.client.render.knives;

import com.ferullogaming.countercraft.AbstractClientPlayerCC;
import com.ferullogaming.countercraft.client.anim.AnimationManager;
import com.ferullogaming.countercraft.client.anim.GunAnimation;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.render.IRenderOnGUI;
import com.ferullogaming.countercraft.client.render.IRenderParticleTick;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
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

public abstract class RenderKnife implements IItemRenderer, IRenderOnGUI, IRenderParticleTick {
   public static double rotationX;
   public static double rotationY;
   public static double previousRotationX;
   public static double previousRotationY;
   public float partialTick;
   protected String par1;
   protected ModelBase model = this.getModel();
   private Minecraft mc = Minecraft.getMinecraft();

   public RenderKnife(String par1) {
      this.par1 = "countercraft:textures/models/knives/" + par1;
   }

   public void setParticleTick(float par1) {
      this.partialTick = par1;
   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type != ItemRenderType.FIRST_PERSON_MAP;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      EntityPlayer entityplayer = null;
      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GL11.glRotated(rotationY - 10.0D, 0.0D, 0.0D, 1.0D);
         GL11.glRotated(rotationX, 0.0D, 1.0D, 0.0D);
      }

      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         entityplayer = (EntityPlayer)data[1];
         EntityClientPlayerMP entityclientplayermp = this.mc.thePlayer;
         PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(this.mc.thePlayer.username);
         if (!entityplayer.isBlocking()) {
            GL11.glPushMatrix();
            GunAnimation animation = AnimationManager.instance().getCurrentAnimation();
            if (animation != null) {
               animation.doRenderHand(itemstack, this.partialTick, true);
            }

            GL11.glEnable(32826);
            this.mc.getTextureManager().bindTexture((new AbstractClientPlayerCC(this.mc.thePlayer.worldObj, this.mc.thePlayer.username, cloudData)).getLocationSkin());
            GL11.glTranslatef(0.0F, 2.0F, 0.0F);
            GL11.glRotatef(110.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(50.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(-0.7F, -1.7F, -0.4F);
            this.renderHandLocation(entityplayer, itemstack, true);
            Render render = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            float f11 = 2.0F;
            GL11.glScalef(f11, f11, f11);
            if (!entityplayer.isSwingInProgress) {
               renderplayer.renderFirstPersonArm(this.mc.thePlayer);
            }

            GL11.glPopMatrix();
            GL11.glPushMatrix();
            if (animation != null) {
               animation.doRenderHand(itemstack, this.partialTick, false);
            }

            GL11.glEnable(32826);
            this.mc.getTextureManager().bindTexture((new AbstractClientPlayerCC(this.mc.thePlayer.worldObj, this.mc.thePlayer.username, cloudData)).getLocationSkin());
            GL11.glTranslatef(0.0F, 2.0F, 0.0F);
            GL11.glRotatef(100.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-0.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(-0.5F, -2.7F, 0.6F);
            GL11.glRotatef(40.0F, 1.0F, 1.0F, 0.0F);
            this.renderHandLocation(entityplayer, itemstack, false);
            GL11.glScalef(f11, f11, f11);
            renderplayer.renderFirstPersonArm(this.mc.thePlayer);
            GL11.glPopMatrix();
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

      if (type == ItemRenderType.EQUIPPED) {
         entityplayer = (EntityPlayer)data[1];
      }
      
      String v1 = this.par1 + "_" + ((ItemKnife)itemstack.getItem()).getKnifeSkin(itemstack);
      String v2 = v1.contains("_none") ? v1.replaceAll("_none", "") : v1;
      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(v2 + ".png"));
      GL11.glPushMatrix();
      this.renderItemValues(itemstack, type);
      if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
         GunAnimation animation = AnimationManager.instance().getCurrentAnimation();
         if (animation != null) {
            animation.doRender(itemstack, this.partialTick);
         }
      }

      this.model.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
   }

   public void renderDisplayed(ItemStack par1ItemStack, double posx, double posy, double scale, boolean tilted, double rotation) {
      Minecraft mc = Minecraft.getMinecraft();
      String v1 = ((ItemKnife)par1ItemStack.getItem()).getKnifeSkin(par1ItemStack).equals("") ? this.par1 : this.par1 + "_" + ((ItemKnife)par1ItemStack.getItem()).getKnifeSkin(par1ItemStack);
      mc.getTextureManager().bindTexture(new ResourceLocation(v1 + ".png"));
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

   public abstract void renderItemValues(ItemStack var1, ItemRenderType var2);

   public abstract ModelBase getModel();

   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      } else {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      GL11.glPushMatrix();
      GL11.glTranslated(x - 25.0D, y + 12.0D, 20.0D);
      GL11.glRotated(-35.0D, 0.0D, 0.0D, 1.0D);
      this.renderDisplayed(stack, 0.0D, 0.0D, 35.0D, false, 0.0D);
      GL11.glPopMatrix();
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderDisplayed(stack, x, y, 20.0D, false, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderDisplayed(stack, x, y + 40.0D, 100.0D, false, rot);
   }
   
   @Override
   public void renderSkins(ItemStack stack, double x, double y) {
	   this.renderDisplayed(stack, x, y, 20.0D, false, 0.0D);
   }
}
