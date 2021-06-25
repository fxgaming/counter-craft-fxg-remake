package com.ferullogaming.countercraft.entity;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.block.decal.TileEntityDecal;
import com.ferullogaming.countercraft.client.render.RenderTickHandler;
import com.ferullogaming.countercraft.network.CCPacketParticles;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class EntityManager {
   public static void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
      if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
         if (CounterCraft.getClientManager() != null && CounterCraft.getClientManager().getTickHandler() != null) {
            RenderTickHandler.spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
         }
      } else {
         PacketDispatcher.sendPacketToAllPlayers(CCPacketParticles.buildPacket(par1Str, par2, par4, par6, par8, par10, par12));
      }

   }

   public void init() {
      EntityRegistry.registerModEntity(EntityPlayerHead.class, "head", 70, CounterCraft.instance, 144, 20, true);
      EntityRegistry.registerModEntity(EntityGrenade.class, "grenade", 71, CounterCraft.instance, 48, 20, true);
      EntityRegistry.registerModEntity(EntityGrenadeFlash.class, "grenadeflash", 72, CounterCraft.instance, 48, 20, true);
      EntityRegistry.registerModEntity(EntityGrenadeDecoy.class, "grenadedecoy", 73, CounterCraft.instance, 48, 20, true);
      EntityRegistry.registerModEntity(EntityGrenadeSmoke.class, "grenadesmoke", 74, CounterCraft.instance, 48, 20, true);
      EntityRegistry.registerModEntity(EntityGrenadeFire.class, "grenadefire", 75, CounterCraft.instance, 48, 20, true);
      EntityRegistry.registerModEntity(EntityBomb.class, "thebomb", 76, CounterCraft.instance, 96, 20, true);
      GameRegistry.registerTileEntity(TileEntityDecal.class, "Decal");
   }
}
