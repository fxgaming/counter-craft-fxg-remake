package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import com.ferullogaming.countercraft.client.model.guns.ModelSSG08;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderSSG08 extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(-0.1F, -0.15F, -0.3F);
      } else {
         GL11.glTranslatef(0.01F, 0.15F, -0.1F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 43.0D, y - 3.0D, 30.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 5.0D, y - 7.0D, 15.0D, -20.0D, 0.0D, 0.0D);
   }
   
   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x + 5.0D, y - 7.0D, 15.0D, -20.0D, 0.0D, 0.0D); }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x, y, 120.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(-0.5D, 0.0D, 0.0D);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(77.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.6F, -0.35F, 0.35F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.5F, 0.25F, 0.0F);
      GL11.glRotatef(5.0F, 1.0F, 0.0F, 0.0F);
      double scale = 1.2D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = -0.1D;
      RenderGun.muzzlePosY = 0.07D;
      RenderGun.muzzlePosZ = -1.08D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-38.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-5.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.5F, -0.2F, 0.2F);
      double scale = 0.75D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      double scale = 1.0D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelSSG08();
   }

   public String getTexture() {
      return "ssg08model";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -905.0D;
      stickerPos.stickerPosY = 5.0D;
      stickerPos.stickerPosZ = -139.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.09D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -525.0D;
      stickerPos.stickerPosY = 5.0D;
      stickerPos.stickerPosZ = -126.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -55.0D;
      stickerPos.stickerPosY = 18.0D;
      stickerPos.stickerPosZ = -94.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 1.0D;
      stickerPos.size = 0.15D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
