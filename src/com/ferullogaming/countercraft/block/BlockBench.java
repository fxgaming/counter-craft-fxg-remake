package com.ferullogaming.countercraft.block;

import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.entity.EntityManager;
import com.ferullogaming.countercraft.game.GameManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockBench extends BlockCC {
   @SideOnly(Side.CLIENT)
   private Icon iconTop;
   @SideOnly(Side.CLIENT)
   private Icon iconSide;

   public BlockBench(int par1) {
      super(par1, Material.wood);
      this.setTickRandomly(true);
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
      int particles = 2;

      int i5;
      for(i5 = 0; i5 < particles; ++i5) {
         EntityManager.spawnParticle("tilecrack_" + Block.blockIron.blockID + "_" + 0, (double)par2 + 0.5D, (double)par3 + 1.1D, (double)par4 + 0.5D, 0.0D, 0.0D, 0.0D);
      }

      particles = 2;

      for(i5 = 0; i5 < particles; ++i5) {
         EntityManager.spawnParticle("tilecrack_" + Block.blockLapis.blockID + "_" + 0, (double)par2 + 0.5D, (double)par3 + 1.1D, (double)par4 + 0.5D, 0.0D, 0.0D, 0.0D);
      }

      particles = 2;

      for(i5 = 0; i5 < particles; ++i5) {
         EntityManager.spawnParticle("tilecrack_" + Block.blockRedstone.blockID + "_" + 0, (double)par2 + 0.5D, (double)par3 + 1.1D, (double)par4 + 0.5D, 0.0D, 0.0D, 0.0D);
      }

   }

   @SideOnly(Side.CLIENT)
   public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
      if (FMLCommonHandler.instance().getSide() == Side.CLIENT && GameManager.instance().currentClientGame == null) {
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
      }

      return true;
   }

   @SideOnly(Side.CLIENT)
   public Icon getIcon(int par1, int par2) {
      return par1 == 1 ? this.iconTop : this.iconSide;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IconRegister par1IconRegister) {
      this.iconTop = par1IconRegister.registerIcon("countercraft:bench_top");
      this.iconSide = par1IconRegister.registerIcon("countercraft:bench_side");
   }
}
