package com.ferullogaming.countercraft.client.minimap.forge;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.ChunkEvent.Load;
import net.minecraftforge.event.world.ChunkEvent.Unload;

public class EventHandlerMinimap {
   Minimap mw;

   public EventHandlerMinimap(Minimap mw) {
      this.mw = mw;
   }

   @ForgeSubscribe
   public void eventChunkLoad(Load event) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onChunkLoad(event.getChunk());
      }

   }

   @ForgeSubscribe
   public void eventChunkUnload(Unload event) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onChunkUnload(event.getChunk());
      }

   }

   @ForgeSubscribe
   public void eventWorldLoad(net.minecraftforge.event.world.WorldEvent.Load event) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onWorldLoad(event.world);
      }

   }

   @ForgeSubscribe
   public void eventWorldUnload(net.minecraftforge.event.world.WorldEvent.Unload event) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onWorldUnload(event.world);
      }

   }

   @ForgeSubscribe
   public void eventLivingDeath(LivingDeathEvent event) {
   }
}
