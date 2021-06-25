package com.f3rullo14.fds;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FCFile {
   private String configLocation;
   private String fileTitle;

   public FCFile(String title, String location) {
      this.configLocation = location;
      this.fileTitle = title;
      File f1 = new File(location);
      if (!f1.exists()) {
         f1.getParentFile().mkdirs();

         try {
            f1.createNewFile();
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

   }

   public int getInteger(String par1, int par2) {
      return Integer.parseInt(this.read(par1, "" + par2, (String)null));
   }

   public int getInteger(String par1, int par2, String note) {
      return Integer.parseInt(this.read(par1, "" + par2, note));
   }

   public String getString(String par1, String par2) {
      return this.read(par1, par2, (String)null);
   }

   public String getString(String par1, String par2, String note) {
      return this.read(par1, par2, note);
   }

   public boolean getBoolean(String par1, boolean par2) {
      return this.read(par1, par2 ? "true" : "false", (String)null).equals("true");
   }

   public boolean getBoolean(String par1, boolean par2, String note) {
      return this.read(par1, par2 ? "true" : "false", note).equals("true");
   }

   public String read(String par1, String par2Default, String par3Note) {
      boolean flag = false;
      par1 = par1.replace(".", "-");

      try {
         File f1 = new File(this.configLocation);
         FileReader fileReader = new FileReader(f1);
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         StringBuffer stringBuffer = new StringBuffer();

         String line;
         while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append("\n");
            if (!line.startsWith("//") && line.split("=")[0].equals(par1)) {
               flag = true;
               return line.split("=")[1];
            }
         }

         if (!flag) {
            FileWriter fstream = new FileWriter(f1, true);
            BufferedWriter out = new BufferedWriter(fstream);
            if (par3Note != null) {
               out.write("//" + par3Note);
               out.newLine();
            }

            out.write(par1 + "=" + par2Default);
            out.newLine();
            out.close();
         }

         fileReader.close();
      } catch (IOException var12) {
         System.err.println("Ferullo Config File: Error reading a line in config [" + this.fileTitle + "]");
         var12.printStackTrace();
      }

      return par2Default;
   }
}
