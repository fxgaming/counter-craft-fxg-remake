package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelGalil;
import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderGalil extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(-0.1F, -0.15F, -0.3F);
      } else {
         GL11.glTranslatef(0.03F, 0.15F, -0.05F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 42.0D, y, 28.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 1.0D, y - 7.0D, 16.0D, -20.0D, 0.0D, 0.0D);
   }
   
   public void renderSkins(ItemStack stack, double x, double y) {this.renderGunGUI2(stack, x + 1.0D, y - 7.0D, 16.0D, -20.0D, 0.0D, 0.0D);}

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x + 5.0D, y + 10.0D, 100.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(0.0D, 0.0D, 0.0D);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(77.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.6F, -0.3F, 0.25F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.15F, 0.55F, 0.0F);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.02D;
      RenderGun.muzzlePosY = 0.0D;
      RenderGun.muzzlePosZ = -1.04D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-33.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.6F, -0.0F, 0.25F);
      double scale = 0.7D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      double scale = 0.8D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelGalil();
   }

   public String getTexture() {
      return "galilmodel";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -795.0D;
      stickerPos.stickerPosY = -55.0D;
      stickerPos.stickerPosZ = 68.0D;
      stickerPos.rotX = 180.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 180.0D;
      stickerPos.size = 0.14D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -595.0D;
      stickerPos.stickerPosY = -55.0D;
      stickerPos.stickerPosZ = 68.0D;
      stickerPos.rotX = 180.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 180.0D;
      stickerPos.size = 0.14D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -1395.0D;
      stickerPos.stickerPosY = -55.0D;
      stickerPos.stickerPosZ = 60.0D;
      stickerPos.rotX = 180.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 180.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }


}
