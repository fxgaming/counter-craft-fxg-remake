package com.ferullogaming.countercraft.client.gui.store;

import com.ferullogaming.countercraft.client.cloud.StoreCategory;
import com.ferullogaming.countercraft.client.cloud.StoreItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCTPStoreAdd extends GuiCCStorePrompt {
   private StoreCategory cat;
   private StoreItem item;

   public GuiCCTPStoreAdd(GuiScreen par1, StoreCategory par2, StoreItem par3) {
      super(par1, par3.getCloudItemID());
      this.cat = par2;
      this.item = par3;
      super.promptedTextInput = "1";
      super.maxCharacters = 3;
      this.addInformation(new String[]{EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + par3.getName(), EnumChatFormatting.WHITE + par3.getDescription(), EnumChatFormatting.WHITE + "", EnumChatFormatting.WHITE + "", EnumChatFormatting.WHITE + "Set Quantity (Max " + par3.getMaxQuantity() + ")"});
   }

   public void onPromptEntered() {
      String var1 = this.getTextField();
      GuiCCMenuStore storeMenu = (GuiCCMenuStore)super.parentGui;

      try {
         int amount = Integer.parseInt(var1);
         if (amount < 1) {
            amount = 1;
         }

         if (amount > this.item.getMaxQuantity()) {
            amount = this.item.getMaxQuantity();
         }

         GuiCCMenuStore.cart.add(new StoreOrder(this.cat.getId(), this.item.getID(), amount));
      } catch (Exception var4) {
         ;
      }

      super.mc.displayGuiScreen(storeMenu);
   }
}
