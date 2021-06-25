package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.gui.api.GuiFGDropDownMenu;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenSubscription;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.GuiConnecting;

public class GuiLG extends GuiFGDropDownMenu {
   public GuiLG(GuiScreen par1, int par2, int par3, int par4, int par5) {
      super(par1, par2, par3, par4, par5);
      this.addOption("[CSGO] Mirage");
      this.addOption("[CSGO] Dust");
      this.addOption("[DAYZ] UK");
      this.addOption("???");
      //if (CounterCraft.Data[4].equals("true")) {
    	  this.addOption("GuiSelectWorld(this)");
      //}
   }

   public void onOptionClicked(int par1) {
      if (par1 == 1) {
         super.mc.displayGuiScreen(new GuiConnecting((GuiScreen)null, mc, "149.202.89.26", 16401));
      }
      
      if (par1 == 2) {
    	  super.mc.displayGuiScreen(new GuiConnecting((GuiScreen)null, mc, "149.202.89.26", 16402));
      }

      if (par1 == 3) {
    	  super.mc.displayGuiScreen(new GuiConnecting((GuiScreen)null, mc, "149.202.89.26", 16403));
      }
      
      if (par1 == 4) {
    	  //super.mc.displayGuiScreen(new GuiConnecting((GuiScreen)null, mc, "149.202.89.26", 16404));
      }
      
      if (par1 == 5) {
    	  super.mc.displayGuiScreen(new GuiSelectWorld(this));
    	  //super.mc.displayGuiScreen(new GuiConnecting((GuiScreen)null, mc, "193.70.18.69", 1111));
      }
   }
}
