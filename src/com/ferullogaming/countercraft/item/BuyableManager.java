package com.ferullogaming.countercraft.item;

import java.util.ArrayList;
import net.minecraft.item.Item;

public class BuyableManager {
   private ArrayList blueBuyableItems = new ArrayList();
   private ArrayList redBuyableItems = new ArrayList();

   public void addBuyableItem(String par1, Item par2) {
      if (par2 instanceof ItemCC) {
         if (par1.equals("red")) {
            this.redBuyableItems.add(par2);
            return;
         }

         this.blueBuyableItems.add(par2);
      }

   }

   public ArrayList getBuyableItemsList(String par1) {
      return par1.equals("red") ? this.redBuyableItems : this.blueBuyableItems;
   }

   public boolean isItemBuyable(Item par1) {
      return this.blueBuyableItems.contains(par1) || this.redBuyableItems.contains(par1);
   }

   public String getItemSide(Item par1) {
      if (this.blueBuyableItems.contains(par1) && this.redBuyableItems.contains(par1)) {
         return "Any";
      } else if (this.blueBuyableItems.contains(par1)) {
         return "Blue";
      } else {
         return this.redBuyableItems.contains(par1) ? "Red" : "None";
      }
   }
}
