package com.ferullogaming.countercraft.client.gui.game;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiMinimap {
   public static void render(Minecraft mc, int givenX, int givenY, int givenWidth, int givenHeight) {
	   PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)mc.thePlayer);
		 if (!data.isFlashed()) {
	      GL11.glPushMatrix();
	      int centerX = givenX + givenWidth / 2;
	      int centerY = givenY + givenHeight / 2;
	      World world = mc.theWorld;
	      EntityPlayer player = mc.thePlayer;
	      CCRenderHelper.drawRectWithShadow((double)givenX, (double)givenY, (double)givenWidth, (double)givenHeight, "0x000000", 0.5F);
	      CCRenderHelper.drawRectWithShadow((double)centerX, (double)centerY, 1.0D, 1.0D, "0xFFFFFF", 1.0F);
	      GL11.glTranslated((double)centerX, (double)centerY, 0.0D);
	      GL11.glRotatef(-player.rotationYaw + 180.0F, 0.0F, 0.0F, 1.0F);
	      GL11.glTranslated((double)(-givenWidth / 2), (double)(-givenHeight / 2), 0.0D);
	      int maxDistance = givenWidth / 2;
	      if (GameManager.instance().currentClientGame != null) {
	         Iterator i$ = GameManager.instance().currentClientGame.getPlayerEventHandler().getPlayers().iterator();
	
	         label65:
	         while(true) {
	            while(true) {
	               String currentPlayerUsername;
	               EntityPlayer currentPlayerEntity;
	               PlayerData currentPlayerData;
	               do {
	                  do {
	                     do {
	                        do {
	                           if (!i$.hasNext()) {
	                              break label65;
	                           }
	
	                           currentPlayerUsername = (String)i$.next();
	                           currentPlayerEntity = world.getPlayerEntityByName(currentPlayerUsername);
	                        } while(currentPlayerEntity == null);
	
	                        currentPlayerData = PlayerDataHandler.getPlayerData(currentPlayerUsername);
	                     } while(currentPlayerData == null);
	                  } while(currentPlayerData.isGhost);
	               } while(currentPlayerUsername.equalsIgnoreCase(player.username));
	
	               double xDifference = 0.0D;
	               double yDifference = 0.0D;
	               xDifference = currentPlayerEntity.posX - player.posX;
	               yDifference = currentPlayerEntity.posZ - player.posZ;
	               if (xDifference > (double)maxDistance) {
	                  xDifference = (double)maxDistance;
	               } else if (xDifference < (double)(-maxDistance - 1)) {
	                  xDifference = (double)(-maxDistance - 1);
	               }
	
	               if (yDifference > (double)maxDistance) {
	                  yDifference = (double)maxDistance;
	               } else if (yDifference < (double)(-maxDistance - 1)) {
	                  yDifference = (double)(-maxDistance - 1);
	               }
	
	               if (GameManager.instance().currentClientGame.getPlayerEventHandler().getPlayerTeam(currentPlayerEntity).teamName.equalsIgnoreCase(GameManager.instance().currentClientGame.getPlayerEventHandler().getPlayerTeam(player.username).teamName)) {
	                  GL11.glPushMatrix();
	                  GL11.glTranslated(xDifference, yDifference, 0.0D);
	                  if (currentPlayerData.calloutTimer > 0 && currentPlayerData.calloutTimer % 5 == 0) {
	                     CCRenderHelper.drawRect((double)(givenWidth / 2 - 1), (double)(givenHeight / 2 - 1), 3.0D, 3.0D, "0xFFFF00", 1.0F);
	                  }
	
	                  CCRenderHelper.drawRectWithShadow((double)(givenWidth / 2), (double)(givenHeight / 2), 1.0D, 1.0D, "0x18AD43", 1.0F);
	                  GL11.glPopMatrix();
	               } else if (currentPlayerData.seenTimer > 0 || player.canEntityBeSeen(currentPlayerEntity)) {
	                  GL11.glPushMatrix();
	                  GL11.glTranslated(xDifference, yDifference, 0.0D);
	                  CCRenderHelper.drawRectWithShadow((double)(givenWidth / 2), (double)(givenHeight / 2), 1.0D, 1.0D, "0xFF0000", 1.0F);
	                  GL11.glPopMatrix();
	               }
	            }
	         }
	      }
	
	      GL11.glPopMatrix();
	  }
   }
}
