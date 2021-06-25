package com.ferullogaming.countercraft.client.cloud.item;

import java.util.ArrayList;
import net.minecraft.util.EnumChatFormatting;

public class CloudItemHat extends CloudItem {
   public CloudItemHat(int par1, int par2, String par3) {
      super(par1, par2, par3);
      this.setFilterIT("hat");
   }

   public void addInformation(CloudItemStack par1, ArrayList par2) {
      super.addInformation(par1, par2);
      par2.add("");
      par2.add(EnumChatFormatting.BOLD + "Author");
      par2.add(EnumChatFormatting.GRAY + this.getAuthor());
   }
}
