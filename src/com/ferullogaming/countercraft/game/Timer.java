package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSTagCompound;

public class Timer {
   private int counter = 0;
   private int maxTime = 0;
   private int lastSecond = 0;
   private boolean hasSecondPast = false;

   public Timer(int par1, int par2) {
      this.maxTime = 1200 * par1;
      this.maxTime += par2 * 20;
      this.counter = this.maxTime;
      this.lastSecond = this.maxTime / 20;
   }

   public void updateTimer() {
      if (this.counter >= 0) {
         --this.counter;
      }

      if (this.lastSecond != this.getTimeRemainingSeconds()) {
         this.hasSecondPast = true;
         this.lastSecond = this.getTimeRemainingSeconds();
      } else {
         this.hasSecondPast = false;
      }

   }

   public void writeToFDS(String tag, FDSTagCompound par1) {
      par1.setInteger(tag + "timercount", this.counter);
      par1.setInteger(tag + "timermax", this.maxTime);
   }

   public void readFromFDS(String tag, FDSTagCompound par1) {
      this.counter = par1.getInteger(tag + "timercount");
      this.maxTime = par1.getInteger(tag + "timermax");
   }

   public boolean isTicking() {
      return this.counter > 0;
   }

   public int getTimeElapsed() {
      return this.maxTime - this.counter;
   }

   public int getTimeElapsedSeconds() {
      return this.getTimeElapsed() / 20;
   }

   public int getTimeRemaining() {
      return this.counter;
   }

   public int getTimeRemainingSeconds() {
      return this.getTimeRemaining() / 20;
   }

   public void setTimeRemainingSeconds(int par1) {
      this.counter = par1 * 20;
   }

   public boolean hasSecondPast() {
      return this.hasSecondPast;
   }
}
