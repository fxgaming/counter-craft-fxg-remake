package com.f3rullo14.fds.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FDSBoolean extends FDSBase {
   public String tag;
   public boolean value;

   public FDSBoolean() {
   }

   public FDSBoolean(String par1, boolean par2) {
      this.tag = par1;
      this.value = par2;
   }

   public void write(DataOutput par1) throws IOException {
      writeString(par1, this.tag);
      writeString(par1, this.value ? "true" : "false");
   }

   public void load(DataInput par1) throws IOException {
      this.tag = readString(par1);
      String var1 = readString(par1);
      this.value = var1.equals("true");
   }

   public String getTag() {
      return this.tag;
   }

   public String toString() {
      return "[" + this.tag + ":BOOL:" + this.value + "]";
   }

   public Object getObject() {
      return this.value;
   }

   public FDSBase copy() {
      return new FDSBoolean(this.tag, this.value);
   }
}
