package com.f3rullo14.fds.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FDSArrayList extends FDSBase {
   public String tag;
   public ArrayList value = new ArrayList();

   public FDSArrayList() {
   }

   public FDSArrayList(String par1, ArrayList par2) {
      this.tag = par1;
      this.value = par2;
   }

   public void write(DataOutput par1) throws IOException {
      writeString(par1, this.tag);
      par1.writeInt(this.value.size());
      Iterator i$ = this.value.iterator();

      while(i$.hasNext()) {
         Object val = i$.next();
         FDSBase obj = (FDSBase)val;
         par1.writeByte(FDSBase.getByteFromFDSType(obj.getClass()));
         obj.write(par1);
      }

   }

   public void load(DataInput par1) throws IOException {
      this.tag = readString(par1);
      this.value.clear();
      int var1 = par1.readInt();

      for(int i = 0; i < var1; ++i) {
         byte var2 = par1.readByte();

         try {
            FDSBase base = (FDSBase)FDSBase.getFDSBaseFromType(var2).newInstance();
            base.load(par1);
            this.value.add(base);
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

   }

   public String getTag() {
      return this.tag;
   }

   public String toString() {
      return "[" + this.tag + ":LIST:" + this.value + "]";
   }

   public Object getObject() {
      return this.value;
   }

   public FDSBase copy() {
      return new FDSArrayList(this.tag, new ArrayList(this.value));
   }
}
