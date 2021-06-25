package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import com.ferullogaming.countercraft.client.model.guns.ModelTec9;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderTec9 extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(0.02F, -0.1F, -0.12F);
      } else {
         GL11.glTranslatef(0.04F, 0.11F, -0.08F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 10.0D, y - 9.0D, 35.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x + 10.0D, y - 8.0D, 24.0D, -20.0D, 0.0D, 0.0D);
   }
   
   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x + 10.0D, y - 8.0D, 24.0D, -20.0D, 0.0D, 0.0D); }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUIPre(stack, x + 0.0D, y - 40.0D, 150.0D, 0.0D, rot, 0.0D);
      GL11.glTranslated(-0.4D, 0.0D, 0.0D);
      this.renderGunGUIPost(stack);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(78.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-0.06F, -0.23F, 0.35F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(-0.05F, 0.0F, 0.0F);
      double scale = 1.0D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.04D;
      RenderGun.muzzlePosY = -0.0D;
      RenderGun.muzzlePosZ = -0.92D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-35.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.45F, -0.3F, 0.35F);
      double scale = 0.7D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GL11.glTranslated(-0.3D, -0.15D, 0.0D);
      double scale = 0.9D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelTec9();
   }

   public String getTexture() {
      return "tec9model";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -455.0D;
      stickerPos.stickerPosY = 5.0D;
      stickerPos.stickerPosZ = -167.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 5.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker1Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -165.0D;
      stickerPos.stickerPosY = 295.0D;
      stickerPos.stickerPosZ = -167.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = -13.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }

   public StickerPosition getSticker2Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -165.0D;
      stickerPos.stickerPosY = -20.0D;
      stickerPos.stickerPosZ = -167.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = 5.0D;
      stickerPos.size = 0.1D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
