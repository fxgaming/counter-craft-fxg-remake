package com.f3rullo14.fds.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FDSHelper {
   private static final boolean LOG = false;

   public static void saveFDSTagCompound(String par1, FDSTagCompound par2) {
      if (!par1.endsWith(".FDS")) {
         par1 = par1 + ".FDS";
      }

      saveFDSTagCompound(new File(par1), par2);
   }

   public static void saveFDSTagCompound(File par1, FDSTagCompound par2) {
      if (par1.exists()) {
         par1.delete();
      }

      try {
         par1.createNewFile();
      } catch (IOException var7) {
         var7.printStackTrace();
      }

      try {
         log("Saving Compound '" + par2.getTag() + "'...");
         OutputStream stream = new FileOutputStream(par1);
         DataOutputStream dataout = new DataOutputStream(stream);

         try {
            par2.write(dataout);
         } catch (IOException var5) {
            var5.printStackTrace();
         }

         dataout.close();
         stream.close();
         log("Saved Compound '" + par2.getTag() + "'");
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public static FDSTagCompound loadFDSTagCompound(String par1) {
      if (!par1.endsWith(".FDS")) {
         par1 = par1 + ".FDS";
      }

      return loadFDSTagCompound(new File(par1));
   }

   public static FDSTagCompound loadFDSTagCompound(File par1) {
      try {
         if (par1.exists()) {
            log("Loading Compound...");
            InputStream stream = new FileInputStream(par1);
            DataInputStream datain = new DataInputStream(stream);
            FDSTagCompound tag = new FDSTagCompound();
            tag.load(datain);
            datain.close();
            stream.close();
            log("Loaded Compound '" + tag.getTag() + "'...");
            return tag;
         }
      } catch (Exception var4) {
         log("Failed to Load a Compound!");
      }

      return new FDSTagCompound();
   }

   private static void log(String par1) {
   }
}
