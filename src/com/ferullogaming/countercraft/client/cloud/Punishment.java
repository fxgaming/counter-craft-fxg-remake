package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.MathHelper;
import com.f3rullo14.fds.tag.FDSTagCompound;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Punishment {
   public String id;
   public Date dateCurrent;
   public Date dateEnding;
   public String reason = "";
   public PunishmentType type = null;
   public String issuer = "";
   public String closedReason = "";
   private boolean closed = false;

   public static Punishment readFromFDS(FDSTagCompound par1) {
      if (!par1.hasTag("reason")) {
         return null;
      } else {
         Punishment ban = new Punishment();
         ban.id = par1.getString("id");
         ban.reason = par1.getString("reason");
         ban.dateCurrent = convertStringToDate(par1.getString("datecurrent"));
         ban.dateEnding = convertStringToDate(par1.getString("dateend"));
         ban.type = PunishmentType.getFromString(par1.getString("type"));
         ban.closed = !par1.hasTag("closed") || par1.getBoolean("closed");
         ban.issuer = par1.getString("issuer");
         return ban;
      }
   }

   private static Date convertStringToDate(String par1) {
      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      try {
         return format.parse(par1);
      } catch (ParseException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public void closePunishment() {
      this.closed = true;
   }

   public boolean isActive() {
      return !this.closed && this.getTimeRemaining(TimeUnit.MINUTES) > 0;
   }

   private String convertDateToString(Date par1) {
      DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      return format.format(par1);
   }

   public int getTimeRemaining(TimeUnit par1) {
      return (int)MathHelper.getDateDiff(this.dateCurrent, this.dateEnding, par1);
   }

   public String getTimeRemainingString() {
      int minutesLeft = this.getTimeRemaining(TimeUnit.MINUTES);
      int days = 0;
      int hours = 0;
      int minutes = 0;
      if (this.isActive()) {
         while(minutesLeft - 1440 >= 0) {
            minutesLeft -= 1440;
            ++days;
         }

         while(true) {
            if (minutesLeft - 60 < 0) {
               minutes = minutesLeft;
               break;
            }

            minutesLeft -= 60;
            ++hours;
         }
      }

      return days + "d " + hours + "h " + minutes + "m";
   }

   private Date getCalcDate(String par1) {
      int days = 0;
      int hours = 0;
      int mins = 0;
      if (par1.endsWith("d")) {
         days += Integer.parseInt(par1.replace("d", ""));
      }

      if (par1.endsWith("h")) {
         hours += Integer.parseInt(par1.replace("h", ""));
      }

      if (par1.endsWith("m")) {
         mins += Integer.parseInt(par1.replace("m", ""));
      }

      Calendar c = Calendar.getInstance();
      c.setTime(new Date());
      c.add(5, days);
      c.add(11, hours);
      c.add(12, mins);
      return c.getTime();
   }
}
