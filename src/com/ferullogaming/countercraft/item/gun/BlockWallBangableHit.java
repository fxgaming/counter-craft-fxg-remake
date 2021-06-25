package com.ferullogaming.countercraft.item.gun;

import com.ferullogaming.countercraft.game.BlockLocation;

public class BlockWallBangableHit extends BlockLocation {
   public int blockID = 0;

   public BlockWallBangableHit(double par1, double par2, double par3, int par4) {
      super(par1, par2, par3, 0.0D, 0.0D);
      this.blockID = par4;
   }
}
