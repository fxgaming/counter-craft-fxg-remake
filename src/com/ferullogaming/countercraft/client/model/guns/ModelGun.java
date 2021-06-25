package com.ferullogaming.countercraft.client.model.guns;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public abstract class ModelGun extends ModelBase {
   public abstract void renderAmmo(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7);
}
