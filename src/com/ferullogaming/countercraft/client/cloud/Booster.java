package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booster {
   public String name;
   public Date dateStarted;
   public int maxMinutes;
   public int multiplier;
   public int timeRemaining;

   public static Booster readFromFDS(FDSTagCompound par1) {
      if (par1.hasTag("booster-name")) {
         Booster booster = new Booster();
         booster.name = par1.getString("booster-name");
         booster.maxMinutes = par1.getInteger("booster-time");
         booster.multiplier = par1.getInteger("booster-multi");
         booster.dateStarted = getDateFromString(par1.getString("booster-date"));
         booster.timeRemaining = par1.getInteger("booster-remaining");
         return booster;
      } else {
         return null;
      }
   }

   private static Date getDateFromString(String par1) {
      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      try {
         return format.parse(par1);
      } catch (ParseException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   private String getDateStarted() {
      DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      return format.format(this.dateStarted);
   }

   public boolean isEXP() {
      return this.name.equalsIgnoreCase("exp");
   }

   public boolean isEmeralds() {
      return this.name.equalsIgnoreCase("emeralds");
   }

   public int getTimeRemaining() {
      return this.timeRemaining;
   }

   public boolean isActive() {
      return this.getTimeRemaining() > 0;
   }
}
