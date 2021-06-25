package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindingManager extends KeyHandler {
   public static KeyBinding keyFriends = new KeyBinding("[NW]Меню друзей", 41);
   public static KeyBinding keySprint = new KeyBinding("Спринт", 29);
   public static KeyBinding keyVoteYes = new KeyBinding("Голосовать ДА", 62);
   public static KeyBinding keyVoteNo = new KeyBinding("Голосовать НЕТ", 63);
   public static KeyBinding keyNextSpectate = new KeyBinding("Наблюдать за следующим", 205);
   public static KeyBinding keyPreviousSpectate = new KeyBinding("Наблюдать за предидущим", 203);
   public static KeyBinding keyMapDisable = new KeyBinding("Выключить мини-карту", 24);
   public static KeyBinding keyMinimapZoomIn = new KeyBinding("Увеличить мини-карту", 201);
   public static KeyBinding keyMinimapZoomOut = new KeyBinding("Уменьшить мини-карту", 209);
   public static KeyBinding[] keyList;
   public static boolean[] keyListRepeat;

   public KeyBindingManager() {
      super(keyList, keyListRepeat);
   }

   public String getLabel() {
      return "Counter Craft";
   }

   public void keyDown(EnumSet types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
   }

   public void keyUp(EnumSet types, KeyBinding kb, boolean tickEnd) {
      if (!tickEnd && Minecraft.getMinecraft().currentScreen == null) {
         if (kb == keyMapDisable) {
            ClientVariables.enableMinimap = !ClientVariables.enableMinimap;
         } else if (kb == keyMinimapZoomIn) {
            Minimap.instance.miniMap.view.adjustZoomLevel(-1);
         } else if (kb == keyMinimapZoomOut) {
            Minimap.instance.miniMap.view.adjustZoomLevel(1);
         }
      }

   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.CLIENT);
   }

   static {
      keyList = new KeyBinding[]{keyFriends, keySprint, keyVoteYes, keyVoteNo, keyNextSpectate, keyPreviousSpectate, keyMapDisable, keyMinimapZoomIn, keyMinimapZoomOut};
      keyListRepeat = new boolean[]{true, true, true, true, true, true, true, true, true};
   }
}
