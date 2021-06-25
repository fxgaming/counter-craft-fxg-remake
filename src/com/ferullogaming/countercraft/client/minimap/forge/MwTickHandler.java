package com.ferullogaming.countercraft.client.minimap.forge;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;

public class MwTickHandler implements ITickHandler {
   Minimap mw;

   public MwTickHandler(Minimap mw) {
      this.mw = mw;
   }

   public void tickStart(EnumSet type, Object... tickData) {
   }

   public void tickEnd(EnumSet type, Object... tickData) {
      this.mw.onTick();
   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.RENDER);
   }

   public String getLabel() {
      return "MapWriter";
   }
}
