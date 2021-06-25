package com.f3rullo14.fds;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MathHelper {
   public static double round(double value, int places) {
      if (places < 0) {
         throw new IllegalArgumentException();
      } else {
         long factor = (long)Math.pow(10.0D, (double)places);
         value *= (double)factor;
         long tmp = Math.round(value);
         return (double)tmp / (double)factor;
      }
   }

   public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
      long diffInMillies = date2.getTime() - date1.getTime();
      return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
   }
}
