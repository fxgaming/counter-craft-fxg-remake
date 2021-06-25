package com.ferullogaming.countercraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;

public class BlockSlabOpaqueCC extends BlockSlabCC {
   public BlockSlabOpaqueCC(int p_i2208_1_, boolean p_i2208_2_, Material p_i2208_3_) {
      super(p_i2208_1_, p_i2208_2_, Material.glass);
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 0;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }
}
