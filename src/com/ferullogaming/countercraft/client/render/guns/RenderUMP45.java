package com.ferullogaming.countercraft.client.render.guns;

import com.ferullogaming.countercraft.client.model.guns.ModelGun;
import com.ferullogaming.countercraft.client.model.guns.ModelUMP45;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderUMP45 extends RenderGun {
   public void renderHandLocation(EntityPlayer entityplayer, ItemStack itemstack, boolean par3Right) {
      if (par3Right) {
         GL11.glTranslatef(-0.1F, -0.15F, -0.3F);
      } else {
         GL11.glTranslatef(-0.1F, 0.3F, 0.04F);
      }

   }

   public void renderOnHUD(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 44.0D, y - 8.0D, 26.0D, 0.0D, 0.0D, 0.0D);
   }

   public void renderInventorySlot(ItemStack stack, double x, double y) {
      this.renderGunGUI(stack, x - 3.0D, y - 14.0D, 22.0D, -20.0D, 0.0D, 0.0D);
   }
   
   public void renderSkins(ItemStack stack, double x, double y){ this.renderGunGUI2(stack, x - 3.0D, y - 14.0D, 22.0D, -20.0D, 0.0D, 0.0D); }

   public void renderInspection(ItemStack stack, double x, double y, double rot) {
      this.renderGunGUI(stack, x + 4.0D, y - 15.0D, 100.0D, 0.0D, rot, 0.0D);
   }

   public void renderGunThirdPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(77.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.4F, -0.6F, 0.35F);
      GL11.glRotatef(15.0F, 0.0F, 0.0F, 1.0F);
      GL11.glTranslatef(0.1F, 0.3F, 0.0F);
      double scale = 1.1D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderGunFirstPerson(EntityPlayer entityplayer, ItemStack itemstack) {
      RenderGun.muzzlePosX = 0.05D;
      RenderGun.muzzlePosY = -0.02D;
      RenderGun.muzzlePosZ = -1.08D;
      GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(-30.0F, 0.0F, 0.0F, 1.0F);
      GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(1.0F, -0.4F, 0.15F);
      double scale = 1.2D;
      GL11.glScaled(scale, scale, scale);
   }

   public void renderAmmo(Entity entityplayer, ItemStack itemstack) {
   }

   public void renderGunOnGround(Entity entity, ItemStack itemstack) {
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      double scale = 1.1D;
      GL11.glScaled(scale, scale, scale);
   }

   public ModelGun getGunModel() {
      return new ModelUMP45();
   }

   public String getTexture() {
      return "ump45model";
   }

   public StickerPosition getSticker0Position() {
      StickerPosition stickerPos = new StickerPosition();
      stickerPos.stickerPosX = -165.0D;
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
      stickerPos.stickerPosX = 165.0D;
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
      stickerPos.stickerPosX = -30.0D;
      stickerPos.stickerPosY = 165.0D;
      stickerPos.stickerPosZ = -105.0D;
      stickerPos.rotX = 0.0D;
      stickerPos.rotY = 0.0D;
      stickerPos.rotZ = -8.0D;
      stickerPos.size = 0.12D;
      stickerPos.movesWithAmmo = false;
      return stickerPos;
   }
}
