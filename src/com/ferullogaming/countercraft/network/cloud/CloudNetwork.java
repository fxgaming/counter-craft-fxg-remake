package com.ferullogaming.countercraft.network.cloud;

import com.ferullogaming.countercraft.CounterCraft;
import java.util.HashMap;
import java.util.Map;

public class CloudNetwork {
   public static final String packetChannel = "clop";
   public static Map packetMapping = new HashMap();
   public static Map packetMappingToID = new HashMap();
   public int packetID = 0;

   public static int getIDFromPacket(Class par1) {
      return packetMappingToID.containsKey(par1) ? ((Integer)packetMappingToID.get(par1)).intValue() : 0;
   }

   public static CloudNetwork instance() {
      return CounterCraft.getCloudNetwork();
   }

   public void init(CounterCraft par1CounterCraft) {
      //this.registerPacket(this.nextPacketID(), new CCPacketGunTrigger());
	   this.registerPacket(this.nextPacketID(), new SCLoginPacket());
   }

   public void registerPacket(int par1, CPacket par1CCPacket) {
      packetMapping.put(par1, par1CCPacket);
      packetMappingToID.put(par1CCPacket.getClass(), par1);
   }

   public CPacket getPacketFromID(int par1) {
      return packetMapping.containsKey(par1) ? (CPacket)packetMapping.get(par1) : null;
   }

   public int nextPacketID() {
      ++this.packetID;
      return this.packetID;
   }
}
