package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ClientVariables {
   public static ScaledResolution sR;
   public static boolean hasSeenNotification;
   public static int notification_fade;
   public static int notification_top_y;
   public static int notification_bottom_y;
   public static boolean hasPlayedNotificationOpenSound;
   public static boolean hasPlayedNotificationCloseSound;
   public static float swing;
   public static float menuRotation;
   public static float menuRotation2;
   public static boolean enableMinimap;

   public static void checkRank() throws FileNotFoundException {
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (data != null && data.compRank != null && !getSavedRank().toLowerCase().equals(data.compRank.toString().toLowerCase())) {
         System.out.println(data.compRank.toString() + "||" + getSavedRank());
         hasSeenNotification = false;
         hasPlayedNotificationOpenSound = false;
         saveRank();
      }

   }

   public static String getSavedRank() throws FileNotFoundException {
      File fold = new File("savedRank.txt");
      if (!fold.exists()) {
         try {
            fold.createNewFile();
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

      Scanner in = new Scanner(new FileReader("savedRank.txt"));
      StringBuilder sb = new StringBuilder();

      while(in.hasNext()) {
         sb.append(in.next());
      }

      in.close();
      return sb.toString();
   }

   public static void saveRank() {
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      File fold = new File("savedRank.txt");
      fold.delete();
      File fnew = new File("savedRank.txt");
      String source = data.compRank.toString();
      System.out.println(source);

      try {
         FileWriter f2 = new FileWriter(fnew, false);
         f2.write(source);
         f2.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   static {
      sR = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      hasSeenNotification = true;
      notification_fade = 0;
      notification_top_y = -30;
      notification_bottom_y = sR.getScaledHeight();
      hasPlayedNotificationOpenSound = false;
      hasPlayedNotificationCloseSound = false;
      enableMinimap = true;
   }
}
