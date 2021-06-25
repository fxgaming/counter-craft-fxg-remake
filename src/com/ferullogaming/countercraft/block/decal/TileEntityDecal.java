package com.ferullogaming.countercraft.block.decal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDecal extends TileEntity {
   private String decalName;

   public TileEntityDecal(String givenDecalName) {
      this.decalName = givenDecalName;
      this.writeToNBT(new NBTTagCompound());
   }

   public String getDecalName() {
      return this.decalName;
   }

   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readFromNBT(par1NBTTagCompound);
      this.decalName = par1NBTTagCompound.getString("decalName");
   }

   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeToNBT(par1NBTTagCompound);
      par1NBTTagCompound.setString("decalName", this.decalName);
   }
}
