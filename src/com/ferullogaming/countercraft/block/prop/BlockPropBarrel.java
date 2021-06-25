package com.ferullogaming.countercraft.block.prop;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPropBarrel extends BlockContainer {
   public BlockPropBarrel(int par1) {
      super(par1, Material.iron);
      this.setBlockUnbreakable();
      this.setCreativeTab(ItemManager.tabCounterCraftProps);
      this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 1.4F, 0.9F);
   }

   @SideOnly(Side.CLIENT)
   protected String getTextureName() {
      return super.textureName = "countercraft:props/barrel";
   }

   public TileEntity createNewTileEntity(World world) {
      return new TileEntityPropBarrel();
   }

   public int getRenderType() {
      return -1;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }
}
