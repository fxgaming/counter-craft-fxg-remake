package com.ferullogaming.countercraft.core;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.launchwrapper.IClassTransformer;

public class CCClassTransformer implements IClassTransformer {
   public static void log(String par1) {
      System.out.println("[CounterCraftCore] " + par1);
   }

   public byte[] transform(String arg0, String arg1, byte[] arg2) {
      if (arg0.equals("bhj")) {
         log("PATCHING! RenderPlayer: " + arg0);
         arg2 = this.patchClassInJar(arg0, arg2, arg0, CCFMLLoadingPlugin.location);
      }

      if (arg0.equals("atd")) {
         log("PATCHING! Vec3Pool: " + arg0);
         arg2 = this.patchClassInJar(arg0, arg2, arg0, CCFMLLoadingPlugin.location);
      }

      if (arg0.equals("auw")) {
         log("PATCHING! GuiChat: " + arg0);
         arg2 = this.patchClassInJar(arg0, arg2, arg0, CCFMLLoadingPlugin.location);
      }

      return arg2;
   }

   public byte[] patchClassInJar(String name, byte[] bytes, String ObfName, File location) {
      try {
         ZipFile zip = new ZipFile(location);
         ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
         if (entry == null) {
            System.out.println(name + " not found in " + location.getName());
         } else {
            InputStream zin = zip.getInputStream(entry);
            bytes = new byte[(int)entry.getSize()];
            zin.read(bytes);
            zin.close();
            System.out.println("[CounterCraftCore]: Class " + name + " patched!");
         }

         zip.close();
         return bytes;
      } catch (Exception var8) {
         throw new RuntimeException("Error overriding " + name + " from " + location.getName(), var8);
      }
   }
}
