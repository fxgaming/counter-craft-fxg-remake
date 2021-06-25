package com.f3rullo14.fds.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class FDSBase {
   private static final Class[] fdsList = new Class[32];

   public abstract void write(DataOutput var1) throws IOException;

   public abstract void load(DataInput var1) throws IOException;

   public abstract String getTag();

   public abstract String toString();

   public abstract Object getObject();

   public abstract FDSBase copy();

   public static void writeString(DataOutput par1, String par2Str) throws IOException {
      par1.writeUTF(par2Str);
   }

   public static String readString(DataInput par1) throws IOException {
      return par1.readUTF();
   }

   public static Class getFDSBaseFromType(byte par1) {
      return fdsList[par1];
   }

   public static byte getByteFromFDSType(Class par1) {
      for(int i = 0; i < fdsList.length; ++i) {
         if (fdsList[i] == par1) {
            return (byte)i;
         }
      }

      return -1;
   }

   static {
      fdsList[1] = FDSInteger.class;
      fdsList[2] = FDSString.class;
      fdsList[3] = FDSTagCompound.class;
      fdsList[4] = FDSBoolean.class;
      fdsList[5] = FDSArrayList.class;
   }
}
