package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import com.ferullogaming.countercraft.client.model.guns.ModelVector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderVector extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(-0.01F, -0.0F, -0.3F);
      } else {
         GL11.glRotated(15.0D, 1.0D, 0.0D, 0.0D);
         GL11.glTranslatef(0.05F, 0.15F, -0.2F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 24.0D, y - 12.0D, 30.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 7.0D, y - 11.0D, 21.0D, -20.0D, 0.0D, 0.0D);
   }
   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x + 7.0D, y - 11.0D, 21.0D, -20.0D, 0.0D, 0.0D); }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x, y - 45.0D, 150.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(-0.4D, 0.0D, 0.0D);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(77.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.6F, -0.35F, 0.35F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.65F, 0.25F, 0.0F);
      GL11.glRotatef(5.0F, 1.0F, 0.0F, 0.0F);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.04D;
      RenderGun.muzzlePosY = 0.0D;
      RenderGun.muzzlePosZ = -1.0D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-35.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(-2.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.5F, -0.27F, 0.12F);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderHUDPlacement(ItemStack par1) {
      double scale = 0.8D;
      GL11.glScaled(scale, scale, scale);
      double x = 7.5D;
      double y = 0.5D;
      GL11.glTranslated(x - y, x + y, 0.0D);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      double scale = 1.0D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelVector();
   }

   public String getTexture() {
      return "vectormodel";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -765.0D;
      stickerPos.stickerPosY = 65.0D;
      stickerPos.stickerPosZ = -105.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 0.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -505.0D;
      stickerPos.stickerPosY = 65.0D;
      stickerPos.stickerPosZ = -105.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 15.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -420.0D;
      stickerPos.stickerPosY = 255.0D;
      stickerPos.stickerPosZ = -105.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = -8.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
