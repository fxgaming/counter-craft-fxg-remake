package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import com.ferullogaming.countercraft.client.model.guns.ModelP90;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderP90 extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(-0.01F, -0.0F, -0.3F);
      } else {
         GL11.glRotated(15.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslatef(0.05F, 0.15F, -0.2F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 26.0D, y - 5.0D, 40.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 7.0D, y - 8.0D, 20.0D, -20.0D, 0.0D, 0.0D);
   }

   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x + 7.0D, y - 8.0D, 20.0D, -20.0D, 0.0D, 0.0D); }
   
   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x, y - 10.0D, 150.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(-0.4D, 0.0D, 0.0D);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(77.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.6F, -0.35F, 0.35F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.8F, 0.25F, 0.0F);
      GL11.glRotatef(5.0F, 1.0F, 0.0F, 0.0F);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.06D;
      RenderGun.muzzlePosY = -0.02D;
      RenderGun.muzzlePosZ = -1.0D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-35.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-2.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.5F, -0.25F, 0.1F);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderHUDPlacement(ItemStack par1) {
      double scale = 1.0D;
      GL11.glScaled(scale, scale, scale);
      double x = 2.0D;
      double y = 1.0D;
      GL11.glTranslated(x - y, x + y, 0.0D);
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      double scale = 1.0D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelP90();
   }

   public String getTexture() {
      return "p90model";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -955.0D;
      stickerPos.stickerPosY = 105.0D;
      stickerPos.stickerPosZ = -188.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -575.0D;
      stickerPos.stickerPosY = 85.0D;
      stickerPos.stickerPosZ = -188.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 10.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -115.0D;
      stickerPos.stickerPosY = 40.0D;
      stickerPos.stickerPosZ = -94.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 5.0D;
      stickerPos.size = 0.2D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
