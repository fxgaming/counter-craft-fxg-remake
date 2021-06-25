package com.ferullogaming.countercraft.block;

import net.minecraft.world.IBlockAccess;

public class BlockLightCC extends BlockCC {
   public BlockLightCC(int par1) {
      super(par1);
   }

   public int getLightValue(IBlockAccess world, int x, int y, int z) {
      return 15;
   }
}
