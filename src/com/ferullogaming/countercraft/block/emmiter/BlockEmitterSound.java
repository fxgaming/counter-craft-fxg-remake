package com.ferullogaming.countercraft.block.emmiter;

import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEmitterSound extends BlockContainer {
   public String soundName = "";
   public int chance = 0;

   public BlockEmitterSound(int par1, String givenSoundName, int givenChance) {
      super(par1, Material.rock);
      this.soundName = givenSoundName;
      this.setBlockUnbreakable();
      this.setCreativeTab(ItemManager.tabCounterCraftEmitters);
      this.setUnlocalizedName("emitter.sound." + this.soundName);
      this.setHardness(0.3F);
      this.chance = givenChance;
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      if (par5Random.nextInt(this.chance) == 0) {
         par1World.playSound((double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), "countercraft:emitter." + this.soundName, 1.0F, 1.0F, true);
      }

   }

   @SideOnly(Side.CLIENT)
   protected String getTextureName() {
      return super.textureName = "countercraft:emitters/" + this.soundName;
   }

   public TileEntity createNewTileEntity(World world) {
      return new TileEntityEmitterSound();
   }

   public int getRenderType() {
      return 0;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }
}
