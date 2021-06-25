package com.f3rullo14.fds;

import java.util.Calendar;

public class TPSCounter {
   private double lastCount = 0.0D;
   private double potentialTPS;
   private int lastSecond = 0;

   public void onPostUpdate() {
      int var1 = Calendar.getInstance().get(13);
      if (this.lastSecond != var1) {
         this.potentialTPS = this.lastCount;
         this.lastCount = 0.0D;
         this.lastSecond = var1;
      }

      ++this.lastCount;
   }

   public double getTPSPerSecond() {
      return this.potentialTPS;
   }
}
