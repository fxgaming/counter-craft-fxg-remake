package com.f3rullo14.fds;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimePeriod {
   private Date dateStarted = new Date();
   private Date dateEnding;

   public TimePeriod(int par1, int par2) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(this.dateStarted);
      if (par1 > 0) {
         cal.add(5, par1);
      }

      if (par2 > 0) {
         cal.add(10, par1);
      }

      this.dateEnding = cal.getTime();
   }

   public Date getDateStarted() {
      return this.dateStarted;
   }

   public Date getDateEnding() {
      return this.dateEnding;
   }

   public static Date getDateFromString(String par1) {
      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      try {
         return format.parse(par1);
      } catch (ParseException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public String getDateStartedString() {
      DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      return format.format(this.dateStarted);
   }

   public int getTimeDifference(TimeUnit par1) {
      int timePast = (int)MathHelper.getDateDiff(new Date(), this.dateEnding, par1);
      return timePast;
   }

   public boolean isEnded() {
      return this.getTimeDifference(TimeUnit.HOURS) <= 0;
   }
}
