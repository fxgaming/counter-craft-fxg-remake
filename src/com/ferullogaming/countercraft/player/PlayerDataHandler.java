package com.ferullogaming.countercraft.player;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerDataHandler {
   public static Map playerDataMapping;
   public static Map playerCloudDataMapping;

   public static PlayerDataCloud getPlayerCloudData(String givenUsername) {
      if (playerCloudDataMapping.get(givenUsername) == null) {
         playerCloudDataMapping.put(givenUsername, new PlayerDataCloud(givenUsername));
      }

      return (PlayerDataCloud)playerCloudDataMapping.get(givenUsername);
   }

   public static PlayerData getClientPlayerData() {
      Minecraft mc = Minecraft.getMinecraft();
      return getPlayerData(mc.getSession().getUsername());
   }

   public static PlayerData getPlayerData(EntityPlayer player) {
      return player == null ? null : getPlayerData(player.username);
   }

   public static PlayerData getPlayerData(String username) {
      if (playerDataMapping.get(username) == null) {
         playerDataMapping.put(username, new PlayerData(username));
      }

      return (PlayerData)playerDataMapping.get(username);
   }

   public static void removePlayerData(EntityPlayer player) {
      if (player != null) {
         removePlayerData(player.username);
      }
   }

   public static void removePlayerData(String username) {
      playerDataMapping.remove(username);
   }

   static {
      playerDataMapping = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      playerCloudDataMapping = new TreeMap(String.CASE_INSENSITIVE_ORDER);
   }
}
