package com.ferullogaming.countercraft.client.cloud.item;

import com.ferullogaming.countercraft.item.gun.ItemGun;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class CloudItemGun extends CloudItem {
   public CloudItemGun(int par1, int par2, String par3) {
      super(par1, par2, par3);
      this.setFilterIT("gun");
   }

   public void addInformation(CloudItemStack par1, ArrayList par2) {
      super.addInformation(par1, par2);
      if (super.itemCollection != null) {
         par2.add("");
         par2.add(EnumChatFormatting.BOLD + "Collection");
         par2.add(EnumChatFormatting.GRAY + super.itemCollection);
      }

      par2.add(EnumChatFormatting.BOLD + "Author");
      par2.add(EnumChatFormatting.GRAY + this.getAuthor());
      if (this.hasStickers(par1.getItemStack())) {
         par2.add(EnumChatFormatting.BOLD + "Stickers");

         for(int i = 0; i < 3; ++i) {
            ItemGun theGun = (ItemGun)par1.getItemStack().getItem();
            if (ItemGun.getStickerInPosition(par1.getItemStack(), i) != null && !ItemGun.getStickerInPosition(par1.getItemStack(), i).equalsIgnoreCase("none")) {
               String stickerName = ItemGun.getStickerInPosition(par1.getItemStack(), i);
               CloudItem[] arr$ = CloudItem.itemList;
               int len$ = arr$.length;

               for(int i$ = 0; i$ < len$; ++i$) {
                  CloudItem item = arr$[i$];
                  if (item instanceof CloudItemSticker) {
                     CloudItemSticker theSticker = (CloudItemSticker)item;
                     if (theSticker.getStickerTextureName().equals(stickerName)) {
                        stickerName = theSticker.value.chatColor + theSticker.getDisplayName().replace("Sticker", "").replace(" ", "");
                     }
                  }
               }

               par2.add(stickerName);
            }
         }
      }

   }

   public boolean hasStickers(ItemStack itemstack) {
      for(int i = 0; i < 3; ++i) {
         ItemGun theGun = (ItemGun)itemstack.getItem();
         if (ItemGun.getStickerInPosition(itemstack, i) != null && !ItemGun.getStickerInPosition(itemstack, i).equalsIgnoreCase("none")) {
            return true;
         }
      }

      return false;
   }
}
