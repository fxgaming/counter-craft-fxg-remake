package com.f3rullo14.fds.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FDSString extends FDSBase {
   public String tag;
   public String value;

   public FDSString() {
   }

   public FDSString(String par1, String par2) {
      this.tag = par1;
      this.value = par2;
   }

   public void write(DataOutput par1) throws IOException {
      writeString(par1, this.tag);
      writeString(par1, this.value != null && this.value.length() != 0 ? this.value : "-");
   }

   public void load(DataInput par1) throws IOException {
      this.tag = readString(par1);
      this.value = readString(par1);
      if (this.value.equals("-")) {
         this.value = "";
      }

   }

   public String getTag() {
      return this.tag;
   }

   public String toString() {
      return "[" + this.tag + ":STR:" + this.value + "]";
   }

   public Object getObject() {
      return this.value;
   }

   public FDSBase copy() {
      return new FDSString(this.tag, this.value);
   }
}
