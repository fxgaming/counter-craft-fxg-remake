package com.ferullogaming.countercraft.client;

import java.util.Calendar;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

public class TickChecker {
   private int tickCounter = 0;
   private Calendar cal;
   private int lastSecond = 0;
   private int fastTickCount = 0;

   public void onClientUpdateTick(Minecraft mc) {
      if (mc.theWorld != null) {
         int var1 = Calendar.getInstance().get(13);
         ++this.tickCounter;
         if (this.lastSecond != var1) {
            if (this.tickCounter > 25 && mc.inGameHasFocus && mc.currentScreen == null) {
               ++this.fastTickCount;
            }

            this.tickCounter = 0;
            this.lastSecond = var1;
         }
      }

      if (this.fastTickCount > 40 && mc.inGameHasFocus && mc.currentScreen == null) {
         mc.crashed(new CrashReport("Tick rate out of sync! Do not alter Minecraft's tick rate!", new Throwable()));
      }

   }
}
