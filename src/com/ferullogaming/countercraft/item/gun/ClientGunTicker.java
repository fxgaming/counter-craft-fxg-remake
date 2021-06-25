package com.ferullogaming.countercraft.item.gun;

import com.ferullogaming.countercraft.CounterCraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ClientGunTicker extends Thread {
   public Minecraft minecraft;
   public boolean runningGunUpdate = true;

   public ClientGunTicker(Minecraft par1) {
      this.minecraft = par1;
      System.out.println("Creating Gun Thread");
   }

   public void run() {
      while(this.runningGunUpdate) {
         try {
            if (this.minecraft != null) {
               EntityPlayer player = this.minecraft.thePlayer;
               if (player != null && this.minecraft.theWorld != null && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
                  ItemStack gunstack = player.getCurrentEquippedItem();
                  if (gunstack != null && gunstack.getItem() instanceof ItemGun) {
                     ItemGun itemgun = (ItemGun)gunstack.getItem();
                     itemgun.onClientTick(this.minecraft.theWorld, player, gunstack);
                  }
               }
            }
         } catch (Exception var5) {
            CounterCraft.log("[WARNING] Gun Tick has encountered an ERROR! You might want to restart your client!");
            var5.printStackTrace();
         }

         try {
            Thread.sleep(25L);
         } catch (InterruptedException var4) {
            ;
         }
      }

   }

   public void stopThread() {
      this.runningGunUpdate = false;
   }
}
