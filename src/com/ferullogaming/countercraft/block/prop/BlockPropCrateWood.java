package com.ferullogaming.countercraft.block.prop;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPropCrateWood extends BlockContainer {
   public BlockPropCrateWood(int par1) {
      super(par1, Material.wood);
      this.setBlockUnbreakable();
      this.setCreativeTab(ItemManager.tabCounterCraftProps);
   }

   @SideOnly(Side.CLIENT)
   protected String getTextureName() {
      return super.textureName = "countercraft:props/cratewood";
   }

   public TileEntity createNewTileEntity(World world) {
      return new TileEntityPropCrateWood();
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
