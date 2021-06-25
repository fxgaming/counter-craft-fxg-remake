package com.f3rullo14.fds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FFReader {
   public ArrayList readFileToArray(String filename) {
      File file = new File(filename);
      if (!file.exists()) {
         file.getParentFile().mkdirs();

         try {
            file.createNewFile();
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      ArrayList lines = new ArrayList();

      try {
         FileReader fileReader = new FileReader(file);
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         StringBuffer stringBuffer = new StringBuffer();

         String line;
         while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append("\n");
            lines.add(line.trim());
         }

         fileReader.close();
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      return lines;
   }
}
