package com.ferullogaming.countercraft.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

public class DamageSourceBullet extends DamageSourceCC {
   private Entity damageSourceEntity;
   private ItemStack gunstack;
   private boolean wallBang = false;
   private int wallBangReduction = 0;
   private boolean headshot = false;

   public DamageSourceBullet(Entity par2Entity, ItemStack par3GunStack) {
      super("bullet");
      this.damageSourceEntity = par2Entity;
      this.gunstack = par3GunStack;
   }

   public DamageSourceBullet setWallbang(boolean par1, int par2) {
      this.wallBang = par1;
      this.wallBangReduction = par2;
      return this;
   }

   public boolean isHeadshot() {
      return this.headshot;
   }

   public DamageSourceBullet setHeadshot(boolean par1) {
      this.headshot = par1;
      return this;
   }

   public boolean isWallbang() {
      return this.wallBang;
   }

   public int getWallbangReduction() {
      return this.wallBangReduction;
   }

   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   public ItemStack getWeaponUsed() {
      return this.gunstack;
   }

   public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase) {
      return ChatMessageComponent.createFromText("damage.cc.gun.bullet" + (this.wallBang ? ".wallbang" : "") + (this.headshot ? ".headshot" : ""));
   }
}
