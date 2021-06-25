package com.f3rullo14.fds.anticheat;

import java.io.File;

public class SizeCalculator {
   public static long calculateFileSize(String par1) {
      return calculateFileSize(new File(par1));
   }

   public static long calculateFileSize(File par1) {
      try {
         if (par1.exists()) {
            return par1.length() / 1024L;
         }
      } catch (Exception var2) {
         ;
      }

      return 0L;
   }
   }
