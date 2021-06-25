package com.ferullogaming.countercraft.utils;

import com.ferullogaming.countercraft.References;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Utils_Crosshair {
   public static void init() throws IOException {
      URL CROSSHAIRS_URL = new URL(References.URL_EXTERNALLOCATION + "crosshairs.zip");
      File crosshairDirectory = new File("countercraft/client/crosshairs/");
      File crosshairZip = new File("crosshairs.zip");
      if (!crosshairDirectory.exists()) {
         crosshairDirectory.mkdirs();
         CROSSHAIRS_URL.openConnection();
         InputStream reader = CROSSHAIRS_URL.openStream();
         FileOutputStream writer = new FileOutputStream(crosshairZip);
         byte[] buffer = new byte[102400];
         int totalBytesRead = 0;
         int bytesRead = 0;
         System.out.println("Downloading Crosshairs Zip File...");

         int bytesRead1;
         while((bytesRead1 = reader.read(buffer)) > 0) {
            writer.write(buffer, 0, bytesRead1);
            buffer = new byte[102400];
            totalBytesRead += bytesRead1;
         }

         long endTime = System.currentTimeMillis();
         writer.close();
         reader.close();
         System.out.println("Crosshairs Zip Download Complete! Unzipping...");
         ZipFile zipFile = new ZipFile(crosshairZip);
         Enumeration zipEntries = zipFile.entries();
         System.out.println("Extracting Crosshairs Contents...");

         while(zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry)zipEntries.nextElement();
            if (zipEntry.isDirectory()) {
               (new File(zipEntry.getName())).mkdir();
            } else {
               copyInputStream(zipFile.getInputStream(zipEntry), new BufferedOutputStream(new FileOutputStream(zipEntry.getName())));
            }
         }

         zipFile.close();
      }

   }

   public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
      byte[] buffer = new byte[1024];

      int len;
      while((len = in.read(buffer)) >= 0) {
         out.write(buffer, 0, len);
      }

      in.close();
      out.close();
   }
}
