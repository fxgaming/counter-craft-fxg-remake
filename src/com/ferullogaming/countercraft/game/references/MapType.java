package com.ferullogaming.countercraft.game.references;

import net.minecraft.util.ResourceLocation;

public class MapType {
   public static MapType[] mapList = new MapType[64];
   public static MapType CUSTOM = (new MapType(1, "Custom")).setImage("none").setMatchMaking(false);
   public static MapType DUST = (new MapType(2, "Dust")).setImage("dust");
   public static MapType MIRAGE = (new MapType(3, "Mirage")).setImage("mirage");
   public static MapType LAPUTA = (new MapType(4, "Laputa")).setImage("laputa");
   public static MapType SALVAGE = (new MapType(5, "Salvage")).setImage("salvage");
   public static MapType TERMINAL = (new MapType(6, "Terminal")).setImage("terminal");
   public static MapType DOME = (new MapType(7, "Dome")).setImage("dome");
   public static MapType RANGE = (new MapType(8, "Range")).setImage("range");
   public static MapType DECAY = (new MapType(9, "Decay")).setImage("decay");
   public static MapType TOXIC = (new MapType(10, "Toxic")).setImage("toxic");
   public static MapType INFERNO = (new MapType(11, "Inferno")).setImage("inferno");
   public static MapType AZTEC = (new MapType(12, "Aztec")).setImage("aztec");
   public static MapType CACHE = (new MapType(13, "Cache")).setImage("cache");
   public static MapType COBBLE = (new MapType(14, "Cobble")).setImage("cobble");
   public static MapType OVERPASS = (new MapType(15, "Overpass")).setImage("overpass");
   public static MapType TRAIN = (new MapType(16, "Train")).setImage("train");
   public int mapID;
   public String displayName;
   public ResourceLocation displayImage;
   public boolean isMatchMaking = true;

   public MapType(int par1, String par2) {
      mapList[par1] = this;
      this.mapID = par1;
      this.displayName = par2;
   }

   public MapType setMatchMaking(boolean par1) {
      this.isMatchMaking = par1;
      return this;
   }

   public MapType setImage(String par1) {
      this.displayImage = new ResourceLocation("countercraft:textures/misc/maps/" + par1 + ".png");
      return this;
   }
}
