package com.ferullogaming.countercraft.damagesource;

import com.ferullogaming.countercraft.item.ItemManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;

public class DamageSourceCCFire extends DamageSourceCC {
   private Entity damageSourceEntity;

   public DamageSourceCCFire(Entity par2Entity) {
      super("ccfire");
      this.damageSourceEntity = par2Entity;
   }

   public Entity getEntity() {
      return this.damageSourceEntity;
   }

   public ItemStack getWeaponUsed() {
      return new ItemStack(ItemManager.grenadeFire);
   }

   public ItemStack getItemStack() {
      return new ItemStack(ItemManager.grenadeFire);
   }

   public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase) {
      return ChatMessageComponent.createFromText("damage.cc.fire");
   }
}
