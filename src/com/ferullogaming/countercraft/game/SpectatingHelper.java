package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SpectatingHelper {
   public static void spectatePlayer() {
      Minecraft mc = Minecraft.getMinecraft();
      PlayerData data = PlayerDataHandler.getPlayerData(mc.thePlayer.username);
      EntityPlayer playerViewing = mc.theWorld.getPlayerEntityByName(data.ghostViewing);
      PlayerData data1 = PlayerDataHandler.getPlayerData(playerViewing);
      if (data != null && playerViewing != null && !playerViewing.isDead && !data1.isGhost && !mc.renderViewEntity.equals(playerViewing)) {
         mc.renderViewEntity = playerViewing;
      }

   }

   public static void spectatePlayerServerSide(EntityPlayer givenSpectator) {
      PlayerData playerData = PlayerDataHandler.getPlayerData(givenSpectator);
      EntityPlayer playerViewing = givenSpectator.worldObj.getPlayerEntityByName(playerData.ghostViewing);
      if (playerViewing != null) {
         PlayerData data1 = PlayerDataHandler.getPlayerData(playerViewing);
         if (data1 != null) {
            givenSpectator.swingProgress = playerViewing.swingProgress;
            givenSpectator.setPositionAndUpdate(playerViewing.posX, playerViewing.posY + 4.0D, playerViewing.posZ);
         }
      }

   }
}
