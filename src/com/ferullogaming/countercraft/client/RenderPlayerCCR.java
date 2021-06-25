package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.AbstractClientPlayerCC;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.model.armor.ModelKevlar;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderPlayerCCR extends RenderPlayer {

   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private ModelBiped modelBipedMain;
   private ModelBiped modelArmorChestplate;
   private ModelBiped modelArmor;
   private ModelKevlar kevlarModel = new ModelKevlar();

   public RenderPlayerCCR() {
      super.mainModel = new ModelBiped(0.0F);
      super.shadowSize = 0.5F;
      this.modelBipedMain = (ModelBiped)super.mainModel;
      this.modelArmorChestplate = new ModelBiped(1.0F);
      this.modelArmor = new ModelBiped(0.5F);
   }

   protected int setArmorModel(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3) {
      ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
      SetArmorModel event = new SetArmorModel(par1AbstractClientPlayer, this, 3 - par2, par3, itemstack);
      MinecraftForge.EVENT_BUS.post(event);
      if (event.result != -1) {
         return event.result;
      } else {
         if (itemstack != null) {
            Item item = itemstack.getItem();
            if (item instanceof ItemArmor) {
               ItemArmor itemarmor = (ItemArmor)item;
               this.bindTexture(RenderBiped.getArmorResource(par1AbstractClientPlayer, itemstack, par2, (String)null));
               ModelBiped modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
               modelbiped.bipedHead.showModel = par2 == 0;
               modelbiped.bipedHeadwear.showModel = par2 == 0;
               modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
               modelbiped.bipedRightArm.showModel = par2 == 1;
               modelbiped.bipedLeftArm.showModel = par2 == 1;
               modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
               modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
               modelbiped = ForgeHooksClient.getArmorModel(par1AbstractClientPlayer, itemstack, par2, modelbiped);
               this.setRenderPassModel(modelbiped);
               modelbiped.onGround = super.mainModel.onGround;
               modelbiped.isRiding = super.mainModel.isRiding;
               modelbiped.isChild = super.mainModel.isChild;
               float f1 = 1.0F;
               int j = itemarmor.getColor(itemstack);
               if (j != -1) {
                  float f2 = (float)(j >> 16 & 255) / 255.0F;
                  float f3 = (float)(j >> 8 & 255) / 255.0F;
                  float f4 = (float)(j & 255) / 255.0F;
                  GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);
                  if (itemstack.isItemEnchanted()) {
                     return 31;
                  }

                  return 16;
               }

               GL11.glColor3f(f1, f1, f1);
               if (itemstack.isItemEnchanted()) {
                  return 15;
               }

               return 1;
            }
         }

         return -1;
      }
   }

   protected void func_130220_b(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3) {
      ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
      if (itemstack != null) {
         Item item = itemstack.getItem();
         if (item instanceof ItemArmor) {
            this.bindTexture(RenderBiped.getArmorResource(par1AbstractClientPlayer, itemstack, par2, "overlay"));
            float f1 = 1.0F;
            GL11.glColor3f(f1, f1, f1);
         }
      }

   }

   public void func_130009_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9) {
      if (!MinecraftForge.EVENT_BUS.post(new Pre(par1AbstractClientPlayer, this, par9))) {
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)par1AbstractClientPlayer);
         if (playerData != null && playerData.muzzleTimer >= 1) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glDisable(2896);
         }

         if (Minecraft.getMinecraft().thePlayer == null || playerData == null || playerData.isSpectating() || EntityViewHelper.canEntityBeSeen(Minecraft.getMinecraft().thePlayer, par1AbstractClientPlayer)) {
            RenderPlayerEvents.instance().onPreRender(par1AbstractClientPlayer, this.modelArmorChestplate, this.modelArmor, this.modelBipedMain, this);
            float f2 = 1.0F;
            GL11.glColor3f(f2, f2, f2);
            ItemStack itemstack = par1AbstractClientPlayer.inventory.getCurrentItem();
            this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack != null ? 1 : 0;
            if (itemstack != null && par1AbstractClientPlayer.getItemInUseCount() > 0) {
               EnumAction enumaction = itemstack.getItemUseAction();
               if (enumaction == EnumAction.block) {
                  this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;
               } else if (enumaction == EnumAction.bow) {
                  this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
               }
            }

            this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1AbstractClientPlayer.isSneaking();
            double d3 = par4 - (double)par1AbstractClientPlayer.yOffset;
            if (par1AbstractClientPlayer.isSneaking() && !(par1AbstractClientPlayer instanceof EntityPlayerSP)) {
               d3 -= 0.125D;
            }

            GL11.glPushMatrix();
            GL11.glDisable(2884);
            super.mainModel.onGround = this.renderSwingProgress(par1AbstractClientPlayer, par9);
            if (par1AbstractClientPlayer.isSneaking()) {
               GL11.glTranslated(0.0D, -0.20000000298023224D, 0.0D);
            }

            if (par1AbstractClientPlayer.username.equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.username)) {
               GL11.glTranslated(0.0D, -1.66D, 0.0D);
            }

            if (super.renderPassModel != null) {
               super.renderPassModel.onGround = super.mainModel.onGround;
            }

            super.mainModel.isRiding = par1AbstractClientPlayer.isRiding();
            if (super.renderPassModel != null) {
               super.renderPassModel.isRiding = super.mainModel.isRiding;
            }

            super.mainModel.isChild = par1AbstractClientPlayer.isChild();
            if (super.renderPassModel != null) {
               super.renderPassModel.isChild = super.mainModel.isChild;
            }

            try {
               float f2_sec = this.interpolateRotation(par1AbstractClientPlayer.prevRenderYawOffset, par1AbstractClientPlayer.renderYawOffset, par9);
               float f3 = this.interpolateRotation(par1AbstractClientPlayer.prevRotationYawHead, par1AbstractClientPlayer.rotationYawHead, par9);
               float f4;
               if (par1AbstractClientPlayer.isRiding() && par1AbstractClientPlayer.ridingEntity instanceof EntityLivingBase) {
                  EntityLivingBase entitylivingbase1 = (EntityLivingBase)par1AbstractClientPlayer.ridingEntity;
                  f2_sec = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, par9);
                  f4 = MathHelper.wrapAngleTo180_float(f3 - f2_sec);
                  if (f4 < -85.0F) {
                     f4 = -85.0F;
                  }

                  if (f4 >= 85.0F) {
                     f4 = 85.0F;
                  }

                  f2_sec = f3 - f4;
                  if (f4 * f4 > 2500.0F) {
                     f2_sec += f4 * 0.2F;
                  }
               }

               float f5 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par9;
               this.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
               f4 = this.handleRotationFloat(par1AbstractClientPlayer, par9);
               this.rotateCorpse(par1AbstractClientPlayer, f4, f2_sec, par9);
               float f6 = 0.0625F;
               GL11.glEnable(32826);
               GL11.glScalef(-1.0F, -1.0F, 1.0F);
               this.preRenderCallback(par1AbstractClientPlayer, par9);
               GL11.glTranslatef(0.0F, -24.0F * f6 - 0.0078125F, 0.0F);
               float f7 = par1AbstractClientPlayer.prevLimbSwingAmount + (par1AbstractClientPlayer.limbSwingAmount - par1AbstractClientPlayer.prevLimbSwingAmount) * par9;
               float f8 = par1AbstractClientPlayer.limbSwing - par1AbstractClientPlayer.limbSwingAmount * (1.0F - par9);
               if (par1AbstractClientPlayer.isChild()) {
                  f8 *= 3.0F;
               }

               if (f7 > 1.0F) {
                  f7 = 1.0F;
               }

               GL11.glEnable(3008);
               super.mainModel.setLivingAnimations(par1AbstractClientPlayer, f8, f7, par9);
               GL11.glRotatef(f2_sec + 180.0F, 0.0F, 1.0F, 0.0F);
               this.renderModel(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);

               float f9;
               int i;
               float f10;
               float f11;
               int k;
               for(int j = 0; j < 4; ++j) {
                  i = this.shouldRenderPass(par1AbstractClientPlayer, j, par9);
                  if (i > 0) {
                     super.renderPassModel.setLivingAnimations(par1AbstractClientPlayer, f8, f7, par9);
                     super.renderPassModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);
                     if ((i & 240) == 16) {
                        this.func_82408_c(par1AbstractClientPlayer, j, par9);
                        super.renderPassModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);
                     }

                     if ((i & 15) == 15) {
                        f9 = (float)par1AbstractClientPlayer.ticksExisted + par9;
                        this.bindTexture(RES_ITEM_GLINT);
                        GL11.glEnable(3042);
                        f10 = 0.5F;
                        GL11.glColor4f(f10, f10, f10, 1.0F);
                        GL11.glDepthFunc(514);
                        GL11.glDepthMask(false);

                        for(k = 0; k < 2; ++k) {
                           GL11.glDisable(2896);
                           f11 = 0.76F;
                           GL11.glColor4f(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
                           GL11.glBlendFunc(768, 1);
                           GL11.glMatrixMode(5890);
                           GL11.glLoadIdentity();
                           float f12 = f9 * (0.001F + (float)k * 0.003F) * 20.0F;
                           float f13 = 0.33333334F;
                           GL11.glScalef(f13, f13, f13);
                           GL11.glRotatef(30.0F - (float)k * 60.0F, 0.0F, 0.0F, 1.0F);
                           GL11.glTranslatef(0.0F, f12, 0.0F);
                           GL11.glMatrixMode(5888);
                           super.renderPassModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(5890);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(5888);
                        GL11.glEnable(2896);
                        GL11.glDisable(3042);
                        GL11.glDepthFunc(515);
                     }

                     GL11.glDisable(3042);
                     GL11.glEnable(3008);
                  }
               }

               GL11.glDepthMask(true);
               this.renderEquippedItems(par1AbstractClientPlayer, par9);
               float f14 = par1AbstractClientPlayer.getBrightness(par9);
               i = this.getColorMultiplier(par1AbstractClientPlayer, f14, par9);
               OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
               GL11.glDisable(3553);
               OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
               if ((i >> 24 & 255) > 0 || par1AbstractClientPlayer.hurtTime > 0 || par1AbstractClientPlayer.deathTime > 0) {
                  GL11.glDisable(3553);
                  GL11.glDisable(3008);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  GL11.glDepthFunc(514);
                  if (par1AbstractClientPlayer.hurtTime > 0 || par1AbstractClientPlayer.deathTime > 0) {
                     GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                     super.mainModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);

                     for(k = 0; k < 4; ++k) {
                        if (this.inheritRenderPass(par1AbstractClientPlayer, k, par9) >= 0) {
                           GL11.glColor4f(f14, 0.0F, 0.0F, 0.4F);
                           super.renderPassModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);
                        }
                     }
                  }

                  if ((i >> 24 & 255) > 0) {
                     f9 = (float)(i >> 16 & 255) / 255.0F;
                     f10 = (float)(i >> 8 & 255) / 255.0F;
                     float f15 = (float)(i & 255) / 255.0F;
                     f11 = (float)(i >> 24 & 255) / 255.0F;
                     GL11.glColor4f(f9, f10, f15, f11);
                     super.mainModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);

                     for(int i1 = 0; i1 < 4; ++i1) {
                        if (this.inheritRenderPass(par1AbstractClientPlayer, i1, par9) >= 0) {
                           GL11.glColor4f(f9, f10, f15, f11);
                           super.renderPassModel.render(par1AbstractClientPlayer, f8, f7, f4, f3 - f2_sec, f5, f6);
                        }
                     }
                  }

                  GL11.glDepthFunc(515);
                  GL11.glDisable(3042);
                  GL11.glEnable(3008);
                  GL11.glEnable(3553);
               }

               GL11.glDisable(32826);
            } catch (Exception var30) {
               var30.printStackTrace();
            }

            if (par1AbstractClientPlayer.isSneaking()) {
               GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
            }

            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnable(2884);
            GL11.glPopMatrix();
            this.passSpecialRender(par1AbstractClientPlayer, par2, par4, par6);
            this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
            this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
            this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
            if (playerData != null && playerData.muzzleTimer >= 1) {
               GL11.glEnable(2896);
            }

            MinecraftForge.EVENT_BUS.post(new Post(par1AbstractClientPlayer, this, par9));
            RenderPlayerEvents.instance().onPostRender(par1AbstractClientPlayer, this.modelArmorChestplate, this.modelArmor, this.modelBipedMain, this);
         }
      }
   }

   protected ResourceLocation func_110817_a(AbstractClientPlayer par1AbstractClientPlayer) {
      PlayerDataCloud playerDataCloud = PlayerDataHandler.getPlayerCloudData(par1AbstractClientPlayer.username);
      return playerDataCloud != null ? (new AbstractClientPlayerCC(par1AbstractClientPlayer.worldObj, par1AbstractClientPlayer.username, playerDataCloud)).getLocationSkin() : par1AbstractClientPlayer.getLocationSkin();
   }

   protected void renderSpecials(AbstractClientPlayer par1AbstractClientPlayer, float par2) {
      net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre event = new net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre(par1AbstractClientPlayer, this, par2);
      if (!MinecraftForge.EVENT_BUS.post(event)) {
         PlayerDataCloud cloudData = PlayerDataHandler.getPlayerCloudData(par1AbstractClientPlayer.username);
         PlayerData playerData = PlayerDataHandler.getPlayerData(par1AbstractClientPlayer.username);
         if (playerData == null || !playerData.isGhost) {
            float f1 = 1.0F;
            GL11.glColor3f(f1, f1, f1);
            ItemStack itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3);
            boolean flag2;
            if (itemstack != null && event.renderHelmet) {
               GL11.glPushMatrix();
               this.modelBipedMain.bipedHead.postRender(0.0625F);
               float f2;
               if (itemstack != null && itemstack.getItem() instanceof ItemBlock) {
                  IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, ItemRenderType.EQUIPPED);
                  flag2 = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack, ItemRendererHelper.BLOCK_3D);
                  if (flag2 || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
                     f2 = 0.625F;
                     GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                     GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                     GL11.glScalef(f2, -f2, -f2);
                  }

                  super.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, itemstack, 0);
               } else if (itemstack.getItem().itemID == Item.skull.itemID) {
                  f2 = 1.0625F;
                  GL11.glScalef(f2, -f2, -f2);
                  String s = "";
                  if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner")) {
                     s = itemstack.getTagCompound().getString("SkullOwner");
                  }

                  TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), s);
               }

               GL11.glPopMatrix();
            }

            RenderPlayerEvents.instance().onRenderSpecials(par1AbstractClientPlayer, this.modelArmorChestplate, this.modelArmor, this.modelBipedMain, this);
            float f6;
            if (par1AbstractClientPlayer.getCommandSenderName().equals("deadmau5") && par1AbstractClientPlayer.getTextureSkin().isTextureUploaded()) {
               this.bindTexture(par1AbstractClientPlayer.getLocationSkin());

               for(int i = 0; i < 2; ++i) {
                  float f3 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
                  float f4 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
                  GL11.glPushMatrix();
                  GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
                  GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
                  GL11.glTranslatef(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
                  GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                  GL11.glRotatef(-f4, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
                  f6 = 1.3333334F;
                  GL11.glScalef(f6, f6, f6);
                  this.modelBipedMain.renderEars(0.0625F);
                  GL11.glPopMatrix();
               }
            }

            boolean flag = par1AbstractClientPlayer.getTextureCape().isTextureUploaded();
            boolean flag1 = !par1AbstractClientPlayer.isInvisible();
            flag2 = !par1AbstractClientPlayer.getHideCape();
            flag = event.renderCape && flag;
            if (flag && flag1 && flag2) {
               this.bindTexture(par1AbstractClientPlayer.getLocationCape());
               GL11.glPushMatrix();
               GL11.glTranslatef(0.0F, 0.0F, 0.125F);
               double d0 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * (double)par2 - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * (double)par2);
               double d1 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * (double)par2 - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * (double)par2);
               double d2 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * (double)par2 - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * (double)par2);
               f6 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2;
               double d3 = (double)MathHelper.sin(f6 * 3.1415927F / 180.0F);
               double d4 = (double)(-MathHelper.cos(f6 * 3.1415927F / 180.0F));
               float f7 = (float)d1 * 10.0F;
               if (f7 < -6.0F) {
                  f7 = -6.0F;
               }

               if (f7 > 32.0F) {
                  f7 = 32.0F;
               }

               float f8 = (float)(d0 * d3 + d2 * d4) * 100.0F;
               float f9 = (float)(d0 * d4 - d2 * d3) * 100.0F;
               if (f8 < 0.0F) {
                  f8 = 0.0F;
               }

               float f10 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * par2;
               f7 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * f10;
               if (par1AbstractClientPlayer.isSneaking()) {
                  f7 += 25.0F;
               }

               GL11.glRotatef(6.0F + f8 / 2.0F + f7, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(f9 / 2.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-f9 / 2.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
               this.modelBipedMain.renderCloak(0.0625F);
               GL11.glPopMatrix();
            }

            ItemStack itemstack1 = par1AbstractClientPlayer.inventory.getCurrentItem();
            if (itemstack1 != null && event.renderItem) {
               GL11.glPushMatrix();
               this.modelBipedMain.bipedRightArm.postRender(0.0625F);
               GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
               if (par1AbstractClientPlayer.fishEntity != null) {
                  itemstack1 = new ItemStack(Item.stick);
               }

               EnumAction enumaction = null;
               if (par1AbstractClientPlayer.getItemInUseCount() > 0) {
                  enumaction = itemstack1.getItemUseAction();
               }

               IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, ItemRenderType.EQUIPPED);
               boolean is3D = customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, itemstack1, ItemRendererHelper.BLOCK_3D);
               boolean isBlock = itemstack1.itemID < Block.blocksList.length && itemstack1.getItemSpriteNumber() == 0;
               float f11;
               if (!is3D && (!isBlock || !RenderBlocks.renderItemIn3d(Block.blocksList[itemstack1.itemID].getRenderType()))) {
                  if (itemstack1.itemID == Item.bow.itemID) {
                     f11 = 0.625F;
                     GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                     GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                     GL11.glScalef(f11, -f11, f11);
                     GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                     GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                  } else if (Item.itemsList[itemstack1.itemID].isFull3D()) {
                     f11 = 0.625F;
                     if (Item.itemsList[itemstack1.itemID].shouldRotateAroundWhenRendering()) {
                        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                     }

                     if (par1AbstractClientPlayer.getItemInUseCount() > 0 && enumaction == EnumAction.block) {
                        GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                        GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                        GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                     }

                     GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                     GL11.glScalef(f11, -f11, f11);
                     GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                     GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                  } else {
                     f11 = 0.375F;
                     GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                     GL11.glScalef(f11, f11, f11);
                     GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                     GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                     GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
                  }
               } else {
                  f11 = 0.5F;
                  GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                  f11 *= 0.75F;
                  GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                  GL11.glScalef(-f11, -f11, f11);
               }

               float f13;
               float f12;
               int j;
               if (itemstack1.getItem().requiresMultipleRenderPasses()) {
                  for(j = 0; j < itemstack1.getItem().getRenderPasses(itemstack1.getItemDamage()); ++j) {
                     int k = itemstack1.getItem().getColorFromItemStack(itemstack1, j);
                     f13 = (float)(k >> 16 & 255) / 255.0F;
                     f12 = (float)(k >> 8 & 255) / 255.0F;
                     f6 = (float)(k & 255) / 255.0F;
                     GL11.glColor4f(f13, f12, f6, 1.0F);
                     super.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, itemstack1, j);
                  }
               } else {
                  j = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                  float f14 = (float)(j >> 16 & 255) / 255.0F;
                  f13 = (float)(j >> 8 & 255) / 255.0F;
                  f12 = (float)(j & 255) / 255.0F;
                  GL11.glColor4f(f14, f13, f12, 1.0F);
                  super.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, itemstack1, 0);
               }

               GL11.glPopMatrix();
            }

            if (playerData.isWearingKevlar()) {
               GL11.glPushMatrix();
               this.bindTexture(new ResourceLocation("countercraft", "textures/models/armor/kevlar.png"));
               if (par1AbstractClientPlayer.isSneaking()) {
                  GL11.glRotatef(28.0F, 1.0F, 0.0F, 0.0F);
               }

               GL11.glTranslated(-0.25D, 2.8D, -0.25D);
               this.kevlarModel.render(par1AbstractClientPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
               GL11.glPopMatrix();
            }

            boolean hasRenderedACape = false;
            if (playerData != null && !playerData.isGhost && !playerData.isSpectating()) {
               if (cloudData != null && cloudData.group != null && (cloudData.group.getName().toLowerCase().equals("dev") || cloudData.group.getName().toLowerCase().equals("asset-dev"))) {
                  this.renderCape(par1AbstractClientPlayer, par2, cloudData.group.name.toLowerCase());
                  hasRenderedACape = true;
               }

               if (!hasRenderedACape && cloudData.isSupporter && cloudData.enableCape) {
                  this.renderCape(par1AbstractClientPlayer, par2, "supporter");
               }
            }

            MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post(par1AbstractClientPlayer, this, par2));
         }
      }
   }

   public void renderCape(AbstractClientPlayer par1AbstractClientPlayer, float partialRenderTick, String groupName) {
      if (par1AbstractClientPlayer != Minecraft.getMinecraft().thePlayer || Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("countercraft", "textures/models/cape/cape_" + groupName + ".png"));
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 0.0F, 0.125F);
         double d3 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * (double)partialRenderTick - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * (double)partialRenderTick);
         double d4 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * (double)partialRenderTick - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * (double)partialRenderTick);
         double d0 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * (double)partialRenderTick - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * (double)partialRenderTick);
         float f4 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * partialRenderTick;
         double d1 = (double)MathHelper.sin(f4 * 3.1415927F / 180.0F);
         double d2 = (double)(-MathHelper.cos(f4 * 3.1415927F / 180.0F));
         float f5 = (float)d4 * 10.0F;
         if (f5 < -6.0F) {
            f5 = -6.0F;
         }

         if (f5 > 32.0F) {
            f5 = 32.0F;
         }

         float f6 = (float)(d3 * d1 + d0 * d2) * 100.0F;
         float f7 = (float)(d3 * d2 - d0 * d1) * 100.0F;
         if (f6 < 0.0F) {
            f6 = 0.0F;
         }

         float f8 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * partialRenderTick;
         f5 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * partialRenderTick) * 6.0F) * 32.0F * f8;
         if (par1AbstractClientPlayer.isSneaking()) {
            f5 += 25.0F;
         }

         GL11.glRotatef(6.0F + f6 / 2.0F + f5, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(f7 / 2.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-f7 / 2.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         this.modelBipedMain.renderCloak(0.0625F);
         GL11.glPopMatrix();
      }
   }

   protected void renderPlayerScale(AbstractClientPlayer par1AbstractClientPlayer, float par2) {
      float f1 = 0.9375F;
      GL11.glScalef(f1, f1, f1);
   }

   protected void func_96450_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10) {
      Scoreboard scoreboard;
      ScoreObjective scoreobjective;
      Score score;
      if (par10 < 100.0D) {
         scoreboard = par1AbstractClientPlayer.getWorldScoreboard();
         scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            score = scoreboard.func_96529_a(par1AbstractClientPlayer.getEntityName(), scoreobjective);
            if (par1AbstractClientPlayer.isPlayerSleeping()) {
               this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4 - 1.5D, par6, 64);
            } else {
               this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4, par6, 64);
            }

            par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9);
         }
      }

      if (par10 < 100.0D) {
         scoreboard = par1AbstractClientPlayer.getWorldScoreboard();
         scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            score = scoreboard.func_96529_a(par1AbstractClientPlayer.getEntityName(), scoreobjective);
            if (par1AbstractClientPlayer.isPlayerSleeping()) {
               this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4 - 1.5D, par6, 64);
            } else {
               this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4, par6, 64);
            }

            par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9);
         }
      }

      if (par10 < 100.0D) {
         scoreboard = par1AbstractClientPlayer.getWorldScoreboard();
         scoreobjective = scoreboard.func_96539_a(2);
         if (scoreobjective != null) {
            score = scoreboard.func_96529_a(par1AbstractClientPlayer.getEntityName(), scoreobjective);
            if (par1AbstractClientPlayer.isPlayerSleeping()) {
               this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4 - 1.5D, par6, 64);
            } else {
               this.renderLivingLabel(par1AbstractClientPlayer, score.getScorePoints() + " " + scoreobjective.getDisplayName(), par2, par4, par6, 64);
            }

            par4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9);
         }
      }

      if (par1AbstractClientPlayer.isPlayerSleeping()) {
         this.renderLivingLabel(par1AbstractClientPlayer, par8Str, par2, par4 - 1.5D, par6, 64);
      } else {
         this.renderLivingLabel(par1AbstractClientPlayer, par8Str, par2, par4, par6, 64);
      }

   }

   public void renderFirstPersonArm(EntityPlayer par1EntityPlayer) {
      float f = 1.0F;
      GL11.glColor3f(f, f, f);
      this.modelBipedMain.onGround = 0.0F;
      this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
      this.modelBipedMain.bipedRightArm.render(0.0625F);
      RenderPlayerEvents.instance().onPostFirstPersonRender(par1EntityPlayer, this, this.modelBipedMain);
   }

   protected void renderModel(EntityLivingBase par1AbstractClientPlayer, float par2, float par3, float par4, float par5, float par6, float par7) {
      this.bindEntityTexture(par1AbstractClientPlayer);
      RenderPlayerEvents.instance().renderPlayerModel(this, super.mainModel, par1AbstractClientPlayer, par2, par3, par4, par5, par6, par7);
   }

   protected void renderPlayerSleep(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6) {
      if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping()) {
         this.renderLivingAt(par1AbstractClientPlayer, par2 + (double)par1AbstractClientPlayer.field_71079_bU, par4 + (double)par1AbstractClientPlayer.field_71082_cx, par6 + (double)par1AbstractClientPlayer.field_71089_bV);
      } else {
         this.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
      }

   }

   protected void rotatePlayer(AbstractClientPlayer par1AbstractClientPlayer, float par2, float par3, float par4) {
      if (par1AbstractClientPlayer.isEntityAlive() && par1AbstractClientPlayer.isPlayerSleeping()) {
         GL11.glRotatef(par1AbstractClientPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.getDeathMaxRotation(par1AbstractClientPlayer), 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
      }

   }

   protected void func_96449_a(EntityLivingBase par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10) {
      this.func_96450_a((AbstractClientPlayer)par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
   }

   protected void preRenderCallback(EntityLivingBase par1AbstractClientPlayer, float par2) {
      this.renderPlayerScale((AbstractClientPlayer)par1AbstractClientPlayer, par2);
   }

   protected void func_82408_c(EntityLivingBase par1AbstractClientPlayer, int par2, float par3) {
      this.func_130220_b((AbstractClientPlayer)par1AbstractClientPlayer, par2, par3);
   }

   protected int shouldRenderPass(EntityLivingBase par1AbstractClientPlayer, int par2, float par3) {
      return this.setArmorModel((AbstractClientPlayer)par1AbstractClientPlayer, par2, par3);
   }

   protected void renderEquippedItems(EntityLivingBase par1AbstractClientPlayer, float par2) {
      this.renderSpecials((AbstractClientPlayer)par1AbstractClientPlayer, par2);
   }

   protected void rotateCorpse(EntityLivingBase par1AbstractClientPlayer, float par2, float par3, float par4) {
      this.rotatePlayer((AbstractClientPlayer)par1AbstractClientPlayer, par2, par3, par4);
   }

   protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6) {
      GL11.glTranslatef((float)par2, (float)par4, (float)par6);
   }

   protected ResourceLocation getEntityTexture(Entity par1Entity) {
      return this.func_110817_a((AbstractClientPlayer)par1Entity);
   }

   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.func_130009_a((AbstractClientPlayer)par1Entity, par2, par4, par6, par8, par9);
   }

   protected int getColorMultiplier(EntityLivingBase par1AbstractClientPlayer, float par2, float par3) {
      return 0;
   }

   private float interpolateRotation(float par1, float par2, float par3) {
      float f3;
      for(f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F) {
         ;
      }

      while(f3 >= 180.0F) {
         f3 -= 360.0F;
      }

      return par1 + par3 * f3;
   }
}
