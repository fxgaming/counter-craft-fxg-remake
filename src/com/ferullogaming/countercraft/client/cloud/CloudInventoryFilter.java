package com.ferullogaming.countercraft.client.cloud;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import java.util.ArrayList;

public class CloudInventoryFilter {
   public static ArrayList filterValue(ArrayList par1) {
      ArrayList var1 = new ArrayList();

      for(int var2 = 6; var2 >= 0; --var2) {
         for(int i = 0; i < par1.size(); ++i) {
            CloudItemStack stack = (CloudItemStack)par1.get(i);
            if (stack.getCloudItem().getValue().rarityID == var2) {
               var1.add(stack.copy());
            }
         }
      }

      return var1;
   }

   public static ArrayList filterDefaultsOut(ArrayList par1) {
      ArrayList var1 = new ArrayList();

      for(int i = 0; i < par1.size(); ++i) {
         if (!((CloudItemStack)par1.get(i)).getCloudItem().isDefault()) {
            var1.add(((CloudItemStack)par1.get(i)).copy());
         }
      }

      return var1;
   }

   public static ArrayList filterContractablesOut(ArrayList par1) {
      ArrayList var1 = new ArrayList();

      for(int i = 0; i < par1.size(); ++i) {
         if (((CloudItemStack)par1.get(i)).getCloudItem().isContrableAble()) {
            var1.add(((CloudItemStack)par1.get(i)).copy());
         }
      }

      return var1;
   }

   public static ArrayList filterTierOut(ArrayList par1, int par2) {
      ArrayList var1 = new ArrayList();

      for(int i = 0; i < par1.size(); ++i) {
         if (((CloudItemStack)par1.get(i)).getCloudItem().getValue().rarityID != par2) {
            var1.add(((CloudItemStack)par1.get(i)).copy());
         }
      }

      return var1;
   }

   public static ArrayList filterOutNameableItems(ArrayList par1) {
      ArrayList var1 = new ArrayList();

      for(int i = 0; i < par1.size(); ++i) {
         if (((CloudItemStack)par1.get(i)).getCloudItem().isNameable()) {
            var1.add(((CloudItemStack)par1.get(i)).copy());
         }
      }

      return var1;
   }

   public static ArrayList filterOutShoawcaseItems(ArrayList par1) {
      ArrayList var1 = new ArrayList();

      for(int i = 0; i < par1.size(); ++i) {
         if (((CloudItemStack)par1.get(i)).getCloudItem().isShowcaseable()) {
            var1.add(((CloudItemStack)par1.get(i)).copy());
         }
      }

      return var1;
   }
}
