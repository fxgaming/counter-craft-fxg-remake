package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.AbstractClientPlayerCC;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.anim.AnimationManager;
import com.ferullogaming.countercraft.client.anim.GunAnimation;
import com.ferullogaming.countercraft.client.anim.GunAnimationReload;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import com.ferullogaming.countercraft.client.model.guns.ModelMuzzle;
import com.ferullogaming.countercraft.client.model.guns.ModelSuppressor;
import com.ferullogaming.countercraft.client.render.IRenderOnGUI;
import com.ferullogaming.countercraft.client.render.IRenderParticleTick;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerData;
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

public abstract class RenderGun implements IItemRenderer, IRenderOnGUI, IRenderParticleTick {
   public static double muzzlePosX = -0.3D;
   public static double muzzlePosY = 0.1D;
   public static double muzzlePosZ = -1.4D;
   public static double rotationX;
   public static double rotationY;
   public static double previousRotationX;
   public static double previousRotationY;
   public ModelGun gunModel = null;
   public ModelBase muzzleModel;
   public ModelSuppressor gunSuppressor = null;
   public float partialTick;
   public int highlightedSticker = 0;
   public boolean shouldHighlightSticker = false;
   public boolean shouldRenderLeftHand = true;
   private Minecraft mc = Minecraft.getMinecraft();

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      return type == ItemRenderType.EQUIPPED || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return false;
   }

   public void setParticleTick(float par1) {
      this.partialTick = par1;
   }

   public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
      this.muzzleModel = new ModelMuzzle();
      if (this.gunModel == null) {
         this.gunModel = this.getGunModel();
      }

      if (this.gunSuppressor == null) {
         this.gunSuppressor = new ModelSuppressor();
      }

      int textureIndex = (int)(Math.random() * 3.0D);
      if (itemstack != null) {
         Entity entityplayer = (Entity)data[1];
         EntityClientPlayerMP entityclientplayermp = this.mc.thePlayer;
         if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            float val = (float)(Math.sin((double)(ClientVariables.swing / 200.0F)) * 10.0D);
            float val2 = (float)(Math.sin((double)(ClientVariables.swing / 800.0F)) * 10.0D);
            GL11.glTranslatef(0.0F, val / 500.0F, 0.0F);
            GL11.glTranslatef(0.0F, 0.0F, val2 / 500.0F);
            GL11.glRotated(rotationY - 10.0D, 0.0D, 0.0D, 1.0D);
            GL11.glRotated(rotationX, 0.0D, 1.0D, 0.0D);
         }

         PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(this.mc.thePlayer.username);
         if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glPushMatrix();
            GunAnimation animation = AnimationManager.instance().getCurrentAnimation();
            if (animation != null) {
               animation.doRenderHand(itemstack, this.partialTick, true);
            }

            GL11.glEnable(32826);
            this.mc.getTextureManager().bindTexture((new AbstractClientPlayerCC(this.mc.thePlayer.worldObj, this.mc.thePlayer.username, cloudData)).getLocationSkin());
            GL11.glTranslatef(1.0F, 1.0F, 0.0F);
            GL11.glRotatef(125.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef(0.19F, -1.4F, 0.5F);
            this.renderHandLocation((EntityPlayer)entityplayer, itemstack, true);
            Render render = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer renderplayer = (RenderPlayer)render;
            float f11 = 1.0F;
            GL11.glScalef(f11, f11, f11);
            renderplayer.renderFirstPersonArm(this.mc.thePlayer);
            GL11.glPopMatrix();
            if (this.shouldRenderLeftHand || animation != null && !animation.toString().contains("Fired")) {
               GL11.glPushMatrix();
               if (animation != null) {
                  animation.doRenderHand(itemstack, this.partialTick, false);
               }

               GL11.glEnable(32826);
               this.mc.getTextureManager().bindTexture((new AbstractClientPlayerCC(this.mc.thePlayer.worldObj, this.mc.thePlayer.username, cloudData)).getLocationSkin());
               GL11.glTranslatef(1.05F, 1.0F, 0.0F);
               GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(130.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
               GL11.glScalef(1.0F, 1.0F, 1.0F);
               GL11.glTranslatef(0.1F, -1.6F, -0.12F);
               this.renderHandLocation((EntityPlayer)entityplayer, itemstack, false);
               GL11.glScalef(f11, f11 + 0.2F, f11);
               renderplayer.renderFirstPersonArm(this.mc.thePlayer);
               GL11.glPopMatrix();
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
         }

         GL11.glPushMatrix();
         String guntexture = this.getTexture(itemstack);
         if (guntexture.contains("dynamic")) {
            this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft:dynamic/" + guntexture.substring(guntexture.indexOf("_") + 1)));
         } else {
            this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/" + guntexture + ".png"));
         }

         if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            this.renderGunFirstPerson((EntityPlayer)entityplayer, itemstack);
            GunAnimation animation = AnimationManager.instance().getCurrentAnimation();
            if (animation != null) {
               animation.doRender(itemstack, this.partialTick);
            }

            this.gunModel.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            if (ItemGun.hasLoadedAmmo(itemstack) || ItemGun.isReloading(itemstack)) {
               GL11.glPushMatrix();
               if (animation != null && animation instanceof GunAnimationReload) {
                  ((GunAnimationReload)animation).doRenderAmmo(itemstack, this.partialTick);
               }

               this.renderAmmo(entityplayer, itemstack);
               this.gunModel.renderAmmo(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GL11.glPopMatrix();
            }

            if (ItemGun.isSecondaryFire(itemstack) && this.gunSuppressor != null) {
               GL11.glPushMatrix();
               GL11.glTranslated(1.3D, -0.22D, 0.1D);
               GL11.glScaled(0.5D, 0.5D, 0.5D);
               this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/suppressor.png"));
               this.gunSuppressor.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GL11.glPopMatrix();
            }

            this.renderStickers(itemstack);
            GL11.glPopMatrix();
            PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)entityplayer);
            if (ClientTickHandler.muzzleTick >= 1.0D && !ItemGun.isSecondaryFire(itemstack)) {
               GL11.glPushMatrix();
               GL11.glPushMatrix();
               GL11.glRotated(-85.0D, -0.08D, 1.0D, 0.0D);
               GL11.glRotated(30.0D, 1.0D, 0.0D, 0.0D);
               GL11.glScaled(2.0D, 2.0D, 2.0D);
               GL11.glTranslated(muzzlePosX, muzzlePosY, muzzlePosZ);
               GL11.glEnable(32826);
               GL11.glDisable(3008);
               GL11.glDisable(2896);
               GL11.glEnable(3042);
               this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/muzzleflash" + textureIndex + ".png"));
               GL11.glDepthMask(false);
               this.muzzleModel.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GL11.glDepthMask(true);
               GL11.glPopMatrix();
               GL11.glPopMatrix();
            }

            return;
         }

         if (type == ItemRenderType.EQUIPPED) {
            this.renderGunThirdPerson((EntityPlayer)entityplayer, itemstack);
         } else if (type == ItemRenderType.ENTITY) {
            this.renderGunOnGround((Entity)data[1], itemstack);
         }

         this.gunModel.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
         if (ItemGun.hasLoadedAmmo(itemstack)) {
            GL11.glPushMatrix();
            this.renderAmmo(entityplayer, itemstack);
            this.gunModel.renderAmmo(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
         }

         if (ItemGun.isSecondaryFire(itemstack) && this.gunSuppressor != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(1.3D, -0.22D, 0.1D);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/suppressor.png"));
            this.gunSuppressor.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GL11.glPopMatrix();
         }

         this.renderStickers(itemstack);
         GL11.glPopMatrix();
         if (type == ItemRenderType.EQUIPPED) {
            PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)entityplayer);
            if (playerData != null && playerData.muzzleTimer > 0 && !ItemGun.isSecondaryFire(itemstack)) {
               GL11.glRotated(-165.0D, 1.0D, 0.0D, 0.0D);
               GL11.glRotated(-14.0D, 0.0D, 1.0D, 0.0D);
               GL11.glScaled(2.0D, 2.0D, 2.0D);
               GL11.glTranslated(muzzlePosX + 0.18000000715255737D, muzzlePosY - 0.15000000596046448D, muzzlePosZ + 0.55D);
               GL11.glEnable(32826);
               GL11.glDisable(2896);
               GL11.glEnable(3042);
               this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/muzzleflash" + textureIndex + ".png"));
               GL11.glDepthMask(false);
               this.muzzleModel.render(entityplayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GL11.glDepthMask(true);
            }
         }
      }

   }

   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      } else {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderStickers(stack);
      this.renderGunGUI(stack, x + 2.0D, y - 10.0D, 15.0D, -20.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderStickers(stack);
      this.renderGunGUI(stack, x + 2.0D, y - 10.0D, 15.0D, -20.0D, 0.0D, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
      this.shouldHighlightSticker = true;
      this.highlightedSticker = givenStickerID;
      this.renderStickers(stack);
      this.renderGunGUI(stack, x, y, 100.0D, 0.0D, rot, 0.0D);
   }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderStickers(stack);
      this.renderGunGUI(stack, x, y, 100.0D, 0.0D, rot, 0.0D);
   }
   
   @Override
   public void renderSkins(ItemStack stack, double x, double y) {
	   this.renderStickers(stack);
	   this.renderGunGUI2(stack, x + 2.0D, y - 10.0D, 15.0D, -20.0D, 0.0D, 0.0D);
   }

   protected void renderGunGUIPre(ItemStack par1, double x, double y, double scale, double rotx, double roty, double rotz) {
      if (this.gunModel == null) {
         this.gunModel = this.getGunModel();
      }

      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(2929);
      String guntexture = this.getTexture(par1);
      if (guntexture.contains("dynamic")) {
         this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft:dynamic/" + guntexture.substring(guntexture.indexOf("_") + 1)));
      } else {
         this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/" + guntexture + ".png"));
      }

      GL11.glTranslated(x, y, 0.0D);
      GL11.glScaled(scale, scale, scale);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(rotx, 0.0D, 0.0D, 1.0D);
      GL11.glRotated(roty, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(rotz, 1.0D, 0.0D, 0.0D);
   }
   
   protected void renderGunGUIPre2(ItemStack par1, double x, double y, double scale, double rotx, double roty, double rotz) {
      if (this.gunModel == null) {
         this.gunModel = this.getGunModel();
      }

      GL11.glPushMatrix();
      GL11.glDisable(2884);
      GL11.glEnable(2929);
      String guntexture = this.getTexture(par1);
      if (guntexture.contains("dynamic")) {
         this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft:dynamic/" + guntexture.substring(guntexture.indexOf("_") + 1)));
      } else {
         this.mc.getTextureManager().bindTexture(new ResourceLocation("countercraft:textures/models/guns/" + guntexture + ".png"));
      }

      GL11.glTranslated(x, y, 0.0D);
      GL11.glScaled(-scale, -scale, -scale);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(rotx + 180.0D, 0.0D, 0.0D, 1.0D);
      GL11.glRotated(roty, 0.0D, 1.0D, 0.0D);
      GL11.glRotated(rotz, 1.0D, 0.0D, 0.0D);
   }

   protected void renderGunGUIPost(ItemStack par1) {
      this.gunModel.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPushMatrix();
      this.renderAmmo((Entity)null, par1);
      this.gunModel.renderAmmo((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GL11.glPopMatrix();
      GL11.glDisable(2929);
      GL11.glEnable(2884);
      this.renderStickers(par1);
      GL11.glPopMatrix();
   }

   protected void renderGunGUI(ItemStack par1, double x, double y, double scale, double rotx, double roty, double rotz) {
      this.renderGunGUIPre(par1, x, y, scale, rotx, roty, rotz);
      this.renderGunGUIPost(par1);
   }
   
   protected void renderGunGUI2(ItemStack par1, double x, double y, double scale, double rotx, double roty, double rotz) {
	   this.renderGunGUIPre2(par1, x, y, scale, rotx, roty, rotz);
	   this.renderGunGUIPost(par1);
   }

   protected String getTexture(ItemStack par1) {
      String skin = ItemGun.getGunSkin(par1);
      return skin != null && skin.length() > 0 ? this.getTexture() + "_" + skin.toLowerCase().replaceAll(" ", "") : this.getTexture();
   }

   public abstract void renderGunThirdPerson(EntityPlayer var1, ItemStack var2);

   public abstract void renderGunFirstPerson(EntityPlayer var1, ItemStack var2);

   public abstract void renderAmmo(Entity var1, ItemStack var2);

   public abstract void renderGunOnGround(Entity var1, ItemStack var2);

   public abstract ModelGun getGunModel();

   public abstract String getTexture();

   public void renderStickers(ItemStack itemstack) {
	  boolean arming = ((ItemGun)itemstack.getItem()).isReloading(itemstack);
      GL11.glScaled(0.01D, 0.01D, 0.01D);
      GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
      GL11.glPushMatrix();
      StickerPosition sticker1Pos = this.getSticker0Position();
      String sticker1Name = ItemGun.getStickerInPosition(itemstack, 0);
      if (this.shouldHighlightSticker && this.highlightedSticker != 0) {
         sticker1Name = "none";
      } else if (this.shouldHighlightSticker && (sticker1Name == null || sticker1Name.equals("none"))) {
         sticker1Name = "stickertemplate";
      }

      if (sticker1Pos != null && sticker1Name != null && !sticker1Name.equals("none")) {
         GL11.glScaled(sticker1Pos.size, sticker1Pos.size, sticker1Pos.size);
         GL11.glTranslated(sticker1Pos.stickerPosX, sticker1Pos.stickerPosY, sticker1Pos.stickerPosZ);
         GL11.glRotated(sticker1Pos.rotX, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(sticker1Pos.rotY, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(sticker1Pos.rotZ, 0.0D, 0.0D, 1.0D);
         if (sticker1Pos.movesWithAmmo) if (arming) ;
         else CCRenderHelper.drawImage(0.0D, 0.0D, new ResourceLocation("countercraft", "textures/items/" + sticker1Name + ".png"), 128.0D, 128.0D);
         else CCRenderHelper.drawImage(0.0D, 0.0D, new ResourceLocation("countercraft", "textures/items/" + sticker1Name + ".png"), 128.0D, 128.0D);
      }

      GL11.glPopMatrix();
      GL11.glPushMatrix();
      StickerPosition sticker2Pos = this.getSticker1Position();
      String sticker2Name = ItemGun.getStickerInPosition(itemstack, 1);
      if (this.shouldHighlightSticker && this.highlightedSticker != 1) {
         sticker2Name = "none";
      } else if (this.shouldHighlightSticker && (sticker2Name == null || sticker2Name.equals("none"))) {
         sticker2Name = "stickertemplate";
      }

      if (sticker2Pos != null && sticker2Name != null && !sticker2Name.equals("none")) {
         GL11.glScaled(sticker2Pos.size, sticker2Pos.size, sticker2Pos.size);
         GL11.glTranslated(sticker2Pos.stickerPosX, sticker2Pos.stickerPosY, sticker2Pos.stickerPosZ);
         GL11.glRotated(sticker2Pos.rotX, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(sticker2Pos.rotY, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(sticker2Pos.rotZ, 0.0D, 0.0D, 1.0D);
         if (sticker2Pos.movesWithAmmo) if (arming) ;
         else CCRenderHelper.drawImage(0.0D, 0.0D, new ResourceLocation("countercraft", "textures/items/" + sticker2Name + ".png"), 128.0D, 128.0D);
         else CCRenderHelper.drawImage(0.0D, 0.0D, new ResourceLocation("countercraft", "textures/items/" + sticker2Name + ".png"), 128.0D, 128.0D);
      }

      GL11.glPopMatrix();
      GL11.glPushMatrix();
      StickerPosition sticker3Pos = this.getSticker2Position();
      String sticker3Name = ItemGun.getStickerInPosition(itemstack, 2);
      if (this.shouldHighlightSticker && this.highlightedSticker != 2) {
         sticker3Name = "none";
      } else if (this.shouldHighlightSticker && (sticker3Name == null || sticker3Name.equals("none"))) {
         sticker3Name = "stickertemplate";
      }

      if (sticker3Pos != null && sticker3Name != null && !sticker3Name.equals("none")) {
         GL11.glScaled(sticker3Pos.size, sticker3Pos.size, sticker3Pos.size);
         GL11.glTranslated(sticker3Pos.stickerPosX, sticker3Pos.stickerPosY, sticker3Pos.stickerPosZ);
         GL11.glRotated(sticker3Pos.rotX, 1.0D, 0.0D, 0.0D);
         GL11.glRotated(sticker3Pos.rotY, 0.0D, 1.0D, 0.0D);
         GL11.glRotated(sticker3Pos.rotZ, 0.0D, 0.0D, 1.0D);
         if (sticker3Pos.movesWithAmmo) if (arming) ;
         else CCRenderHelper.drawImage(0.0D, 0.0D, new ResourceLocation("countercraft", "textures/items/" + sticker3Name + ".png"), 128.0D, 128.0D);
         else CCRenderHelper.drawImage(0.0D, 0.0D, new ResourceLocation("countercraft", "textures/items/" + sticker3Name + ".png"), 128.0D, 128.0D);
      }

      GL11.glPopMatrix();
      this.shouldHighlightSticker = false;
   }

   public abstract StickerPosition getSticker0Position();

   public abstract StickerPosition getSticker1Position();

   public abstract StickerPosition getSticker2Position();
}
