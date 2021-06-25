package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.util.ArrayList;
import net.minecraft.util.EnumChatFormatting;

public class Group {
   public String name = "Default";
   public String color = "&r";
   public boolean isPriorityQueuing = false;
   public ArrayList permissionNodes = new ArrayList();

   public void writeToDS(FDSTagCompound par1) {
      par1.setString("groupName", this.name);
      par1.setString("groupColor", this.color);
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.name = par1.getString("groupName");
      this.color = par1.getString("groupColor");
      this.isPriorityQueuing = par1.getBoolean("groupQue");
      this.permissionNodes = par1.getStringArrayList("permNodes");
   }

   public EnumChatFormatting getEnumColor() {
      char color = this.color.charAt(1);
      EnumChatFormatting[] arr$ = EnumChatFormatting.values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         EnumChatFormatting var1 = arr$[i$];
         if (var1.func_96298_a() == color) {
            return var1;
         }
      }

      return EnumChatFormatting.WHITE;
   }

   public String getName() {
      return this.name.toUpperCase();
   }

   public boolean hasPermission(String par1) {
      return this.permissionNodes.contains(par1) || this.permissionNodes.contains("*");
   }

   public String getDisplayName() {
      return this.name.equals("Default") ? "" : this.getEnumColor() + "" + this.getName() + EnumChatFormatting.RESET;
   }
}
