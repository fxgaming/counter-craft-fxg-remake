package com.ferullogaming.countercraft.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

public class DamageSourceMelee extends DamageSourceCC {
   private Entity damageSourceEntity;
   private ItemStack melee;

   public DamageSourceMelee(Entity par2Entity, ItemStack par3Stack) {
      super("knife");
      this.damageSourceEntity = par2Entity;
      this.melee = par3Stack;
   }

   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   public ItemStack getWeaponUsed() {
      return this.melee;
   }

   public ItemStack getItemStack() {
      return this.melee;
   }

   public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase) {
      return ChatMessageComponent.createFromText("damage.cc.melee.knife");
   }
}
