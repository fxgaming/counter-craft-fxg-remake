package com.f3rullo14.fds.anticheat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class ClassUtils {
   private static ThreadLocal loadBuffer = new ThreadLocal();

   public static byte[] getClassBytes(Class clazz) throws IOException {
      InputStream classStream = null;

      byte[] var4;
      try {
         String className = clazz.getName();
         String classAsPath = className.replace('.', '/') + ".class";
         classStream = clazz.getClassLoader().getResourceAsStream(classAsPath);
         var4 = readFully(classStream);
      } finally {
         if (classStream != null) {
            try {
               classStream.close();
            } catch (IOException var11) {
               ;
            }
         }

      }

      return var4;
   }

   private static byte[] readFully(InputStream stream) {
      try {
         byte[] buf = (byte[])loadBuffer.get();
         if (buf == null) {
            loadBuffer.set(new byte[4096]);
            buf = (byte[])loadBuffer.get();
         }

         int totalLength = 0;

         int r;
         byte[] oldbuf;
         while((r = stream.read(buf, totalLength, buf.length - totalLength)) != -1) {
            totalLength += r;
            if (totalLength >= buf.length - 1) {
               oldbuf = buf;
               buf = new byte[buf.length + 4096];
               System.arraycopy(oldbuf, 0, buf, 0, oldbuf.length);
            }
         }

         oldbuf = new byte[totalLength];
         System.arraycopy(buf, 0, oldbuf, 0, totalLength);
         return oldbuf;
      } catch (Throwable var5) {
         return new byte[0];
      }
   }

   public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

      assert classLoader != null;

      String path = packageName.replace('.', '/');
      Enumeration resources = classLoader.getResources(path);
      ArrayList dirs = new ArrayList();

      while(resources.hasMoreElements()) {
         URL resource = (URL)resources.nextElement();
         dirs.add(new File(resource.getFile()));
      }

      ArrayList classes = new ArrayList();
      Iterator i$ = dirs.iterator();

      while(i$.hasNext()) {
         File directory = (File)i$.next();
         classes.addAll(findClasses(directory, packageName));
      }

      return (Class[])((Class[])classes.toArray(new Class[classes.size()]));
   }

   private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
      List classes = new ArrayList();
      if (!directory.exists()) {
         return classes;
      } else {
         File[] files = directory.listFiles();
         File[] arr$ = files;
         int len$ = files.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File file = arr$[i$];
            if (file.isDirectory()) {
               assert !file.getName().contains(".");

               classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
               classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
         }

         return classes;
      }
   }
}
