package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelDeagle;
import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderDeagle extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      GL11.glTranslatef(0.05F, -0.0F, -0.05F);
   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 10.0D, y - 9.0D, 40.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 9.0D, y - 10.0D, 22.0D, -20.0D, 0.0D, 0.0D);
   }
   
   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x + 9.0D, y - 10.0D, 22.0D, -20.0D, 0.0D, 0.0D); }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x, y - 25.0D, 170.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(-0.4D, 0.0D, 0.0D);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(78.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.1F, -0.35F, 0.35F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.05F, 0.03F, 0.0F);
      double scale = 1.0D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.0D;
      RenderGun.muzzlePosY = -0.04D;
      RenderGun.muzzlePosZ = -1.08D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-32.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-1.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.5F, -0.38F, 0.4F);
      double scale = 0.7D;
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
      return new ModelDeagle();
   }

   public String getTexture() {
      return "deaglemodel";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -635.0D;
      stickerPos.stickerPosY = 5.0D;
      stickerPos.stickerPosZ = -109.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -375.0D;
      stickerPos.stickerPosY = 30.0D;
      stickerPos.stickerPosZ = -109.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = -15.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -135.0D;
      stickerPos.stickerPosY = 5.0D;
      stickerPos.stickerPosZ = -109.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 15.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
