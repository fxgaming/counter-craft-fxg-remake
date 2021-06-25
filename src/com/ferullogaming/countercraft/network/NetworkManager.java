package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.CounterCraft;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
   public static final String packetChannel = "ccNetworking";
   public static Map packetMapping = new HashMap();
   public static Map packetMappingToID = new HashMap();
   public int packetID = 0;

   public static int getIDFromPacket(Class par1) {
      return packetMappingToID.containsKey(par1) ? ((Integer)packetMappingToID.get(par1)).intValue() : 0;
   }

   public static NetworkManager instance() {
      return CounterCraft.getNetworkManager();
   }

   public void init(CounterCraft par1CounterCraft) {
      this.registerPacket(this.nextPacketID(), new CCPacketGunTrigger());
      this.registerPacket(this.nextPacketID(), new CCPacketGunReload());
      this.registerPacket(this.nextPacketID(), new CCPacketGunSwitch());
      this.registerPacket(this.nextPacketID(), new CCPacketBulletCollision());
      this.registerPacket(this.nextPacketID(), new CCPacketBulletCollisionClient());
      this.registerPacket(this.nextPacketID(), new CCPacketGrenadeThrowing());
      this.registerPacket(this.nextPacketID(), new CCPacketGrenadeFlash());
      this.registerPacket(this.nextPacketID(), new CCPacketFrozen());
      this.registerPacket(this.nextPacketID(), new CCPacketGame());
      this.registerPacket(this.nextPacketID(), new CCPacketUpdateGameTP());
      this.registerPacket(this.nextPacketID(), new CCPacketUpdateRespawn());
      this.registerPacket(this.nextPacketID(), new CCPacketBuyMenuItem());
      this.registerPacket(this.nextPacketID(), new CCPacketGameNotification());
      this.registerPacket(this.nextPacketID(), new CCPacketMMDataToServer());
      this.registerPacket(this.nextPacketID(), new CCPacketPlayerData());
      this.registerPacket(this.nextPacketID(), new CCPacketKillFeed());
      this.registerPacket(this.nextPacketID(), new CCPacketKilledMessage());
      this.registerPacket(this.nextPacketID(), new CCPacketGunSound());
      this.registerPacket(this.nextPacketID(), new CCPacketGameSwitchTeam());
      this.registerPacket(this.nextPacketID(), new CCPacketParticles());
      this.registerPacket(this.nextPacketID(), new CCPacketBombPlanted());
      this.registerPacket(this.nextPacketID(), new CCPacketBombDefuse());
      this.registerPacket(this.nextPacketID(), new CCPacketCalloutSound());
      this.registerPacket(this.nextPacketID(), new CCPacketSoundEffectPlayer());
      this.registerPacket(this.nextPacketID(), new CCPacketHitMarker());
      this.registerPacket(this.nextPacketID(), new CCPacketSoundPlayer());
      this.registerPacket(this.nextPacketID(), new CCPacketSoundPlayerRequest());
      this.registerPacket(this.nextPacketID(), new CCPacketVote_KickPlayer());
      this.registerPacket(this.nextPacketID(), new CCPacketVoteData());
      this.registerPacket(this.nextPacketID(), new CCPacketVoteEnd());
      this.registerPacket(this.nextPacketID(), new CCPacketVoteYes());
      this.registerPacket(this.nextPacketID(), new CCPacketVoteNo());
      this.registerPacket(this.nextPacketID(), new PacketSkinsToUser());
      this.registerPacket(this.nextPacketID(), new PacketSkinsToServer());
   }

   public void registerPacket(int par1, CCPacket par1CCPacket) {
      packetMapping.put(par1, par1CCPacket);
      packetMappingToID.put(par1CCPacket.getClass(), par1);
   }

   public CCPacket getPacketFromID(int par1) {
      return packetMapping.containsKey(par1) ? (CCPacket)packetMapping.get(par1) : null;
   }

   public int nextPacketID() {
      ++this.packetID;
      return this.packetID;
   }
}
