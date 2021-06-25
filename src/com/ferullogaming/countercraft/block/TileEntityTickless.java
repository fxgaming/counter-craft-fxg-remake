package com.ferullogaming.countercraft.block;

import net.minecraft.tileentity.TileEntity;

public class TileEntityTickless extends TileEntity {
   public boolean canUpdate() {
      return false;
   }
}
