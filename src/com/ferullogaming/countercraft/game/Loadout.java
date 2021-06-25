package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.item.ItemBomb;
import com.ferullogaming.countercraft.item.ItemBombKit;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class Loadout {
   public String loadoutName;
   public ItemStack primary;
   public ItemStack secondary;
   public ItemStack knife;
   public ArrayList grenades = new ArrayList();
   public ItemStack special;

   public Loadout(String par1) {
      this.loadoutName = par1;
   }

   public Loadout() {
      this.loadoutName = "Default";
   }

   public static Loadout createLoadout(EntityPlayer par1) {
      Loadout load = new Loadout();
      ItemStack[] arr$ = par1.inventory.mainInventory;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ItemStack stack = arr$[i$];
         if (stack != null) {
            load.addItemStack(stack.copy());
         }
      }

      return load;
   }

   public static Loadout readFromData(DataInputStream par1) throws IOException {
      String var1 = par1.readUTF();
      Loadout load = new Loadout(var1);
      int var2 = par1.readInt();

      for(int i = 0; i < var2; ++i) {
         int var3 = par1.readInt();
         ItemStack itemstack = new ItemStack(var3, 1, 0);
         load.addItemStack(itemstack);
      }

      return load;
   }

   public static Loadout createLoadoutFromFDS(String par1, FDSTagCompound par2) {
      if (par2.hasTag("loadout" + par1)) {
         FDSTagCompound tag = par2.getTagCompound("loadout" + par1);
         Loadout loadout = new Loadout(tag.getString("name"));
         int var2 = tag.getInteger("size");

         for(int i = 0; i < var2; ++i) {
            int var3 = tag.getInteger("stack" + i);
            ItemStack itemstack = new ItemStack(var3, 1, 0);
            if (itemstack.getItem() instanceof ItemGun) {
               ItemGun gun = (ItemGun)itemstack.getItem();
               ItemGun.setLoadedAmmo(itemstack, gun.clipSize);
               ItemGun.setAmmo(itemstack, gun.gunMaxAmmo);
            }

            loadout.addItemStack(itemstack);
         }

         return loadout;
      } else {
         return null;
      }
   }

   public void writeToData(DataOutputStream par1) throws IOException {
      par1.writeUTF(this.loadoutName);
      par1.writeInt(this.getInventoryList().size());

      for(int i = 0; i < this.getInventoryList().size(); ++i) {
         ItemStack itemstack = (ItemStack)this.getInventoryList().get(i);
         par1.writeInt(itemstack.itemID);
      }

   }

   public void saveLoadoutToFDS(String par1, FDSTagCompound par2) {
      FDSTagCompound tag = new FDSTagCompound("loadout" + par1);
      tag.setString("name", this.loadoutName);
      tag.setInteger("size", this.getInventoryList().size());

      for(int i = 0; i < this.getInventoryList().size(); ++i) {
         ItemStack itemstack = (ItemStack)this.getInventoryList().get(i);
         tag.setInteger("stack" + i, itemstack.getItem().itemID);
      }

      par2.setTagCompound("loadout" + par1, tag);
   }

   public void addItemStack(ItemStack par1) {
      if (par1.getItem() instanceof ItemGun) {
         if (((ItemGun)par1.getItem()).isPrimary) {
            this.primary = par1;
         } else {
            this.secondary = par1;
         }
      } else if (par1.getItem() instanceof ItemKnife) {
         this.knife = par1;
      } else if (par1.getItem() instanceof ItemGrenade) {
         this.grenades.add(par1);
         if (this.grenades.size() > 3) {
            this.grenades.remove(0);
         }
      } else if (par1.getItem() instanceof ItemBomb || par1.getItem() instanceof ItemBombKit) {
         this.special = par1;
      }

   }

   public boolean hasItems() {
      return this.getInventoryList().size() > 0;
   }

   public ArrayList getInventoryList() {
      ArrayList list = new ArrayList();
      if (this.primary != null) {
         list.add(this.primary);
      }

      if (this.secondary != null) {
         list.add(this.secondary);
      }

      if (this.knife != null) {
         list.add(this.knife);
      }

      list.addAll(this.grenades);
      if (this.special != null) {
         list.add(this.special);
      }

      return list;
   }

   public void setInventory(EntityPlayer par1) {
      InventoryPlayer inv = par1.inventory;

      for(int i = 0; i < inv.mainInventory.length; ++i) {
         if (inv.mainInventory[i] != null) {
            ItemStack itemstack = inv.mainInventory[i].copy();
            this.addItemStack(new ItemStack(itemstack.getItem()));
         }
      }

   }

   public Loadout copy() {
      Loadout var1 = new Loadout(this.loadoutName);

      for(int i = 0; i < this.getInventoryList().size(); ++i) {
         var1.addItemStack((ItemStack)this.getInventoryList().get(i));
      }

      return var1;
   }
}
