package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.game.GameManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBarrier extends TileEntity {
   public boolean canSee = false;
   public boolean lastSee = false;

   public TileEntityBarrier() {
      new Random();
   }

   public void updateEntity() {
      if (super.worldObj.isRemote && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
         this.canSee = GameManager.instance().currentClientGame == null;
         if (this.lastSee != this.canSee) {
            Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(super.xCoord, super.yCoord, super.zCoord);
            this.lastSee = this.canSee;
         }
      }

   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
   }
}
