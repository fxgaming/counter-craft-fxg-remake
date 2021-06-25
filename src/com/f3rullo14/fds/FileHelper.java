package com.f3rullo14.fds;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class FileHelper {
   public static ArrayList downloadAndReadToArray(String filename, String url) {
      downloadFile(filename, url);
      return readFileToArray(filename);
   }

   public static void copy(File sourceLocation, File targetLocation) {
      try {
         if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
         } else {
            copyFile(sourceLocation, targetLocation);
         }
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public static void copyDirectory(File source, File target) throws IOException {
      if (!target.exists()) {
         target.mkdir();
      }

      String[] arr$ = source.list();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String f = arr$[i$];
         copy(new File(source, f), new File(target, f));
      }

   }

   public static void copyFile(File source, File target) throws IOException {
      InputStream in = new FileInputStream(source);
      Throwable var3 = null;

      try {
         OutputStream out = new FileOutputStream(target);
         Throwable var5 = null;

         try {
            byte[] buf = new byte[1024];

            int length;
            while((length = in.read(buf)) > 0) {
               out.write(buf, 0, length);
            }
         } catch (Throwable var29) {
            var5 = var29;
            throw var29;
         } finally {
            if (out != null) {
               if (var5 != null) {
                  try {
                     out.close();
                  } catch (Throwable var28) {
                     var5.addSuppressed(var28);
                  }
               } else {
                  out.close();
               }
            }

         }
      } catch (Throwable var31) {
         var3 = var31;
         try {
			throw var31;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      } finally {
         if (in != null) {
            if (var3 != null) {
               try {
                  in.close();
               } catch (Throwable var27) {
                  var3.addSuppressed(var27);
               }
            } else {
               in.close();
            }
         }

      }

   }

   public static void removeLineStartingWith(String par1, String par2) {
      if (par1 != null && par1.length() > 0) {
         File f1 = new File(par1);
         if (f1.exists()) {
            try {
               File temp = new File(par1.replace(".txt", "2.txt"));
               BufferedReader reader = new BufferedReader(new FileReader(f1));
               BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

               String currentLine;
               while((currentLine = reader.readLine()) != null) {
                  if (!currentLine.startsWith(par2)) {
                     System.out.println(currentLine);
                     writer.write(currentLine + System.getProperty("line.separator"));
                  }
               }

               writer.close();
               reader.close();
               f1.delete();
               temp.renameTo(f1);
               (new File(par1.replace(".txt", "2.txt"))).delete();
            } catch (Exception var7) {
               var7.printStackTrace();
            }
         }

      }
   }

   public static void appendLineToFile(String par1, String par2) {
      if (par1 != null && par1.length() > 0) {
         File f1 = new File(par1);
         f1.getParentFile().mkdirs();
         if (!f1.exists()) {
            try {
               f1.createNewFile();
            } catch (IOException var6) {
               var6.printStackTrace();
            }
         }

         try {
            FileWriter fstream = new FileWriter(f1, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(par2);
            out.newLine();
            out.close();
            fstream.close();
         } catch (IOException var5) {
            var5.printStackTrace();
         }

      }
   }

   public static void writeArrayToFile(String filename, ArrayList par2) {
      File file = new File(filename);
      if (file.exists()) {
         file.delete();

         try {
            file.createNewFile();
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      }

      try {
         FileWriter fstream = new FileWriter(file, true);
         BufferedWriter out = new BufferedWriter(fstream);
         Iterator i$ = par2.iterator();

         while(i$.hasNext()) {
            String var1 = (String)i$.next();
            if (var1 != null && var1.trim().length() > 0) {
               out.write(var1);
               out.newLine();
            }
         }

         out.close();
         fstream.close();
      } catch (Exception var8) {
         ;
      }

   }

   public static ArrayList readFileToArray(File file) {
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
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      return lines;
   }

   public static ArrayList readFileToArray(String filename) {
      File file = new File(filename);
      if (!file.exists()) {
         file.getParentFile().mkdirs();

         try {
            file.createNewFile();
         } catch (IOException var7) {
            var7.printStackTrace();
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
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      return lines;
   }

   public static void downloadFile(String filename, String urlString) {
      try {
         File checkForOld = new File(filename);
         if (checkForOld.exists()) {
            checkForOld.delete();
         }

         BufferedInputStream in = null;
         FileOutputStream fout = null;

         try {
            in = new BufferedInputStream((new URL(urlString)).openStream());
            fout = new FileOutputStream(filename);
            byte[] data = new byte[1024];

            int count;
            while((count = in.read(data, 0, 1024)) != -1) {
               fout.write(data, 0, count);
            }
         } finally {
            if (in != null) {
               in.close();
            }

            if (fout != null) {
               fout.close();
            }

         }
      } catch (MalformedURLException var12) {
         var12.printStackTrace();
      } catch (IOException var13) {
         var13.printStackTrace();
      }

   }

   public static boolean downloadFileWithUrl(String filename, String urlString) {
      boolean successful = false;

      try {
         File checkForOld = new File(filename);
         if (checkForOld.exists()) {
            checkForOld.delete();
         }

         BufferedInputStream in = null;
         FileOutputStream fout = null;

         try {
            in = new BufferedInputStream((new URL(urlString)).openStream());
            fout = new FileOutputStream(filename);
            byte[] data = new byte[1024];

            int count;
            while((count = in.read(data, 0, 1024)) != -1) {
               fout.write(data, 0, count);
            }
         } finally {
            if (in != null) {
               in.close();
            }

            if (fout != null) {
               fout.close();
            }

            successful = true;
         }

         return successful;
      } catch (MalformedURLException var13) {
         return false;
      } catch (IOException var14) {
         return false;
      }
   }
}
