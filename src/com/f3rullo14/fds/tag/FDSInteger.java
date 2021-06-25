package com.f3rullo14.fds.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FDSInteger extends FDSBase {
   public String tag;
   public int value;

   public FDSInteger() {
   }

   public FDSInteger(String par1, int par2) {
      this.tag = par1;
      this.value = par2;
   }

   public void write(DataOutput par1) throws IOException {
      writeString(par1, this.tag);
      writeString(par1, "" + this.value);
   }

   public void load(DataInput par1) throws IOException {
      this.tag = readString(par1);
      String var1 = readString(par1);
      this.value = Integer.parseInt(var1);
   }

   public String getTag() {
      return this.tag;
   }

   public String toString() {
      return "[" + this.tag + ":INT:" + this.value + "]";
   }

   public Object getObject() {
      return this.value;
   }

   public FDSBase copy() {
      return new FDSInteger(this.tag, this.value);
   }
}
