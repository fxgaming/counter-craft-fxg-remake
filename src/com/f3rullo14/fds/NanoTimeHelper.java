package com.f3rullo14.fds;

public class NanoTimeHelper {
   private String prefix;
   private long nanoTime = 0L;
   private long nextNanoTime = 0L;
   public static boolean LOG_NANO_TICKS = false;

   public NanoTimeHelper(String par1) {
      this.prefix = par1;
   }

   public static NanoTimeHelper createNanoTimer(String par1) {
      NanoTimeHelper nano = new NanoTimeHelper(par1);
      nano.setNanoTime();
      return nano;
   }

   public void setNanoTime() {
      this.nanoTime = System.nanoTime();
   }

   public double getElapsed() {
      this.nextNanoTime = System.nanoTime();
      return (double)((this.nextNanoTime - this.nanoTime) / 1000000L);
   }

   public void updateAndPrintResult() {
      double var1 = this.getElapsed();
      if (var1 > 1.0D && LOG_NANO_TICKS) {
         System.out.println("Nano Time [" + this.prefix + "] = " + var1 + "ms delay.");
      }

   }
}
