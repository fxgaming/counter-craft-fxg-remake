package com.ferullogaming.countercraft.utils;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Utils_ForgeFixer {
   public static boolean userNeedsToRestart = false;

   public static void init() throws IOException {
      File certifiedFile = new File(CounterCraft.instance.folderLocation + "certified.con");
      if (!certifiedFile.exists()) {
         certifiedFile.createNewFile();
         fixLaunchArguments();
         userNeedsToRestart = true;
      }

   }

   public static void fixLaunchArguments() {
      try {
         URL url = new URL(References.URL_EXTERNALLOCATION + "1.6.4-Forge9.11.1.1345.json");
         URLConnection con = url.openConnection();
         DataInputStream dis = new DataInputStream(con.getInputStream());
         byte[] fileData = new byte[con.getContentLength()];

         for(int q = 0; q < fileData.length; ++q) {
            fileData[q] = dis.readByte();
         }

         dis.close();
         FileOutputStream fos = new FileOutputStream(new File(CounterCraft.instance.forgeJsonLocation + "1.6.4-Forge9.11.1.1345.json"));
         fos.write(fileData);
         fos.close();
      } catch (Exception var6) {
         System.out.println(var6);
      }

   }
}
