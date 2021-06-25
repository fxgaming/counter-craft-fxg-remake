package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.item.CloudItem;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.cloud.packet.PacketInventoryItemDelete;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;

public class CloudInventory {
   public ArrayList newItemsList = new ArrayList();
   String newItemSound = "countercraft:gui.newItem";
   String newItemSoundRare = "countercraft:gui.newItemRare";
   private ArrayList inventory = new ArrayList();

   public void playSoundEffect(boolean par1) {
      if (par1) {
         Minecraft.getMinecraft().sndManager.playSoundFX(this.newItemSoundRare, 1.0F, 1.0F);
      } else {
         Minecraft.getMinecraft().sndManager.playSoundFX(this.newItemSound, 1.0F, 1.0F);
      }

   }

   public String getLatestItemUUID() {
      if (this.newItemsList.size() > 0) {
         String var1 = new String((String)this.newItemsList.get(0));
         this.newItemsList.remove(0);
         return var1;
      } else {
         return null;
      }
   }

   public boolean hasItem(CloudItem par1) {
      return this.getFirstStack(par1.getID()) != null;
   }

   public CloudItemStack getFirstStack(int par1) {
      for(int i = 0; i < this.inventory.size(); ++i) {
         if (((CloudItemStack)this.inventory.get(i)).getCloudItem().getID() == par1) {
            return ((CloudItemStack)this.inventory.get(i)).copy();
         }
      }

      return null;
   }

   public void clearNewItems() {
      this.newItemsList.clear();
   }

   public void addItemStackLocal(CloudItemStack stack) {
      this.inventory.add(stack);
      this.newItemsList.add(stack.getUUID());
   }

   public void removeItemStackLocal(CloudItemStack stack) {
      for(int i = 0; i < this.inventory.size(); ++i) {
         if (stack.getUUID().equals(((CloudItemStack)this.inventory.get(i)).getUUID())) {
            if (!stack.getCloudItem().isDefault()) {
               this.inventory.remove(i);
            }

            return;
         }
      }

   }

   public void removeItemStack(CloudItemStack stack) {
      if (!stack.getCloudItem().isDefault()) {
         this.removeItemStackLocal(stack);
         PacketInventoryItemDelete packet = new PacketInventoryItemDelete(stack.getUUID());
         ClientCloudManager.sendPacket(packet);
      }

   }

   public CloudItemStack getStackFromUUID(String par1) {
      Iterator i$ = this.inventory.iterator();

      CloudItemStack stack;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         stack = (CloudItemStack)i$.next();
      } while(!stack.getUUID().equals(par1));

      return stack;
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.inventory.clear();
      int var1 = par1.getInteger("size");

      for(int i = 0; i < var1; ++i) {
         FDSTagCompound stacktag = par1.getTagCompound("stack" + i);
         if (stacktag != null) {
            String uuid = stacktag.getString("uuid");
            int itemid = stacktag.getInteger("itemid");
            CloudItemStack stack = new CloudItemStack(uuid, itemid);
            this.inventory.add(stack);
         }
      }

   }

   public void readSectionFromFDS(int par1, FDSTagCompound par2) {
      if (par1 == 0) {
         this.inventory.clear();
      }

      int var1 = par2.getInteger("splitSize");

      for(int i = 0; i < var1; ++i) {
         FDSTagCompound stacktag = par2.getTagCompound("stackSplit" + i);
         if (stacktag != null) {
            CloudItemStack stack = CloudItemStack.readFromFDS(stacktag);
            this.inventory.add(stack);
         }
      }

   }

   public ArrayList getListed() {
      return this.inventory;
   }
}
