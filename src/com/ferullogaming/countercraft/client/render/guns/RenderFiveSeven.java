package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelFiveSeven;
import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderFiveSeven extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      GL11.glTranslatef(0.02F, 0.04F, -0.12F);
      if (par3Right) {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      } else {
         GL11.glTranslatef(0.0F, 0.0F, 0.0F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x, y, 30.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 12.0D, y - 2.0D, 20.0D, -20.0D, 0.0D, 0.0D);
   }
   
   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x + 12.0D, y - 2.0D, 20.0D, -20.0D, 0.0D, 0.0D); }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x + 10.0D, y - 50.0D, 170.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(-0.5D, 0.35D, 0.0D);
      double scale = 0.8D;
      GL11.glScaled(scale, scale, scale);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(77.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.55F, -0.1F, 0.45F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.8F, 0.18F, 0.0F);
      double scale = 0.6D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.04D;
      RenderGun.muzzlePosY = -0.05D;
      RenderGun.muzzlePosZ = -1.02D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-30.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.37F, -0.17F, 0.5F);
      double scale = 0.6D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslated(0.0D, 0.2D, 0.2D);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelFiveSeven();
   }

   public String getTexture() {
      return "fivesevenmodel";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -285.0D;
      stickerPos.stickerPosY = -375.0D;
      stickerPos.stickerPosZ = -0.1D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = 15.0D;
      stickerPos.stickerPosY = -365.0D;
      stickerPos.stickerPosZ = -0.1D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = 335.0D;
      stickerPos.stickerPosY = -315.0D;
      stickerPos.stickerPosZ = -0.1D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 15.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
