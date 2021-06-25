package com.f3rullo14.fds;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
   public static void log(String par1, String par2) {
      FileLogger.WriterThread thread = new FileLogger.WriterThread(par1, par2);
      thread.start();
   }

   private static class WriterThread extends Thread {
      private String fileName;
      private String message;

      public WriterThread(String par1, String par2) {
         this.fileName = par1;
         this.message = par2;
      }

      public void run() {
         if (this.fileName != null && this.fileName.length() > 0) {
            File f1 = new File(this.fileName);
            f1.getParentFile().mkdirs();
            if (!f1.exists()) {
               try {
                  f1.createNewFile();
               } catch (IOException var5) {
                  var5.printStackTrace();
               }
            }

            try {
               FileWriter fstream = new FileWriter(f1, true);
               BufferedWriter out = new BufferedWriter(fstream);
               out.write(this.message);
               out.newLine();
               out.close();
               fstream.close();
            } catch (IOException var4) {
               var4.printStackTrace();
            }

         }
      }
   }
}
