package com.ferullogaming.countercraft.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

public class DamageSourceGrenade extends DamageSourceCC {
   private Entity damageSourceEntity;
   private ItemStack grenadeStack;

   public DamageSourceGrenade(Entity par2Entity, ItemStack par3Stack) {
      super("grenade");
      this.damageSourceEntity = par2Entity;
      this.grenadeStack = par3Stack;
   }

   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   public ItemStack getWeaponUsed() {
      return this.grenadeStack;
   }

   public ItemStack getItemStack() {
      return this.grenadeStack;
   }

   public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase) {
      return ChatMessageComponent.createFromText("damage.cc.grenade.he");
   }
}
