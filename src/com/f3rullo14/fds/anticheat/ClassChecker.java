package com.f3rullo14.fds.anticheat;

import com.google.common.reflect.ClassPath;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClassChecker {
   private ArrayList dontWorry = new ArrayList();

   public void addBlacklistClass(String par1) {
      this.dontWorry.add(par1);
   }

   public void generateCheckList(String fileLocation, ArrayList packages) {
      File f1 = new File(fileLocation);
      File f2 = new File(f1.getParentFile().getPath());
      ArrayList list = new ArrayList();

      try {
         if (!f2.exists()) {
            f2.mkdirs();
         }

         if (f1.exists()) {
            f1.delete();
         }

         File f3 = new File(fileLocation);
         if (!f3.exists()) {
            f3.createNewFile();
         }

         ClassLoader loader = Thread.currentThread().getContextClassLoader();

         try {
            Iterator i$ = ClassPath.from(loader).getTopLevelClasses().iterator();

            while(i$.hasNext()) {
               ClassPath.ClassInfo info = (ClassPath.ClassInfo)i$.next();
               Iterator i$1 = packages.iterator();

               while(i$1.hasNext()) {
                  String checkingPackage = (String)i$1.next();
                  if (info.getName().startsWith(checkingPackage)) {
                     try {
                        Class clazz = info.load();
                        String var1 = "" + clazz.getName() + "//-fg-//" + ClassUtils.getClassBytes(clazz).length + "";
                        list.add(var1);
                     } catch (Exception var14) {
                        ;
                     }
                  }
               }
            }
         } catch (IOException var15) {
            var15.printStackTrace();
         }

         FileWriter fstream = new FileWriter(fileLocation, true);
         BufferedWriter out = new BufferedWriter(fstream);
         if (list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
               out.write((String)list.get(i));
               if (i != list.size() - 1) {
                  out.newLine();
               }
            }
         }

         out.close();
      } catch (MalformedURLException var16) {
         var16.printStackTrace();
      } catch (IOException var17) {
         var17.printStackTrace();
      }

   }

   public boolean checkPackagesAndFilesForDirt(String fileLocation, ArrayList packages, boolean debug) {
      File f1 = new File(fileLocation);
      if (!f1.exists()) {
         System.out.println("Checksum File Doesnt Exsist");
         return true;
      } else {
         HashMap mapping = this.readFileToMapping(f1);
         ClassLoader loader = Thread.currentThread().getContextClassLoader();

         try {
            Iterator i$ = ClassPath.from(loader).getTopLevelClasses().iterator();

            label64:
            while(i$.hasNext()) {
               ClassPath.ClassInfo info = (ClassPath.ClassInfo)i$.next();
               Iterator i$1 = packages.iterator();

               String checkingPackage;
               String className;
               boolean continueClass;
               do {
                  do {
                     if (!i$1.hasNext()) {
                        continue label64;
                     }

                     checkingPackage = (String)i$1.next();
                     className = info.getName();
                  } while(className.contains("ClientClassChecker"));

                  continueClass = false;
                  Iterator i$11 = this.dontWorry.iterator();

                  while(i$11.hasNext()) {
                     String blackListed = (String)i$11.next();
                     if (className.contains(blackListed)) {
                        continueClass = true;
                        break;
                     }
                  }
               } while(continueClass);

               boolean hasDirt = false;
               if (className.startsWith(checkingPackage)) {
                  try {
                     Class clazz = info.load();
                     if (mapping.containsKey(className)) {
                        int currentByteSize = ClassUtils.getClassBytes(clazz).length;
                        int checksumByteSize = ((Integer)mapping.get(className)).intValue();
                        if (currentByteSize > checksumByteSize) {
                           if (debug) {
                              System.out.println("Dirty Class: " + className + " current bytes=" + currentByteSize + " wanted bytes=" + checksumByteSize);
                           }

                           hasDirt = true;
                        }
                     }
                  } catch (Exception var17) {
                     ;
                  }
               }

               return hasDirt;
            }
         } catch (IOException var18) {
            var18.printStackTrace();
         }

         return false;
      }
   }

   private HashMap readFileToMapping(File file) {
      HashMap mapping = new HashMap();

      try {
         FileReader fileReader = new FileReader(file);
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         StringBuffer stringBuffer = new StringBuffer();

         String line;
         while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append("\n");
            String[] split = line.split("//-fg-//");
            mapping.put(split[0], Integer.parseInt(split[1]));
         }

         fileReader.close();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      return mapping;
   }
}
