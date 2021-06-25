package com.ferullogaming.countercraft.client.render;

import net.minecraft.item.ItemStack;

public interface IRenderOnGUI {
   int stickerID = 0;

   void renderOnHUD(ItemStack var1, double var2, double var4);

   void renderInventorySlot(ItemStack var1, double var2, double var4);

   void renderInspection(ItemStack var1, double var2, double var4, double var6);

   void renderInspection(ItemStack var1, double var2, double var4, double var6, int var8);
   
   void renderSkins(ItemStack var1, double var2, double var4);
}
