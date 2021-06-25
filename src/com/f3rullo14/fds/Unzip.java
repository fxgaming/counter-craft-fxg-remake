package com.f3rullo14.fds;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {
   private static final int BUFFER_SIZE = 4096;

   public void unzip(String zipFilePath, String destDirectory) throws IOException {
      File destDir = new File(destDirectory);
      if (!destDir.exists()) {
         destDir.mkdir();
      }

      ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));

      for(ZipEntry entry = zipIn.getNextEntry(); entry != null; entry = zipIn.getNextEntry()) {
         String filePath = destDirectory + File.separator + entry.getName();
         if (!entry.isDirectory()) {
            this.extractFile(zipIn, filePath);
         } else {
            File dir = new File(filePath);
            dir.mkdir();
         }

         zipIn.closeEntry();
      }

      zipIn.close();
   }

   private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
      byte[] bytesIn = new byte[4096];
      boolean var5 = false;

      int read;
      while((read = zipIn.read(bytesIn)) != -1) {
         bos.write(bytesIn, 0, read);
      }

      bos.close();
   }
}
