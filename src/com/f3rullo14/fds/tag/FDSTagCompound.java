package com.f3rullo14.fds.tag;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FDSTagCompound extends FDSBase {
   private String tag;
   private Map tags = new HashMap();

   public FDSTagCompound() {
   }

   public FDSTagCompound(String par1) {
      this.tag = par1;
   }

   public FDSTagCompound setTag(String par1) {
      this.tag = par1;
      return this;
   }

   public int getTagsSize() {
      return this.tags.size();
   }

   public boolean hasTag(String par1) {
      return this.tags.containsKey(par1);
   }

   public FDSBase get(String par1) {
      return this.tags.containsKey(par1) ? (FDSBase)this.tags.get(par1) : null;
   }

   public void setTagCompound(String par1, FDSTagCompound par2) {
      this.tags.put(par1, par2);
   }

   public FDSTagCompound getTagCompound(String par1) {
      return this.tags.containsKey(par1) ? (FDSTagCompound)this.tags.get(par1) : null;
   }

   public void setInteger(String par1, int par2) {
      FDSInteger obj = new FDSInteger(par1, par2);
      this.tags.put(par1, obj);
   }

   public int getInteger(String par1) {
      return this.tags.containsKey(par1) ? ((FDSInteger)this.tags.get(par1)).value : 0;
   }

   public void setString(String par1, String par2) {
      FDSString obj = new FDSString(par1, par2);
      this.tags.put(par1, obj);
   }

   public String getString(String par1) {
      return this.tags.containsKey(par1) ? ((FDSString)this.tags.get(par1)).value : null;
   }

   public void setBoolean(String par1, boolean par2) {
      FDSBoolean obj = new FDSBoolean(par1, par2);
      this.tags.put(par1, obj);
   }

   public boolean getBoolean(String par1) {
      return (this.tags.containsKey(par1) ? ((FDSBoolean)this.tags.get(par1)).value : null).booleanValue();
   }

   public void setIntegerArrayList(String par1, ArrayList par2) {
      ArrayList list = new ArrayList();

      for(int i = 0; i < par2.size(); ++i) {
         list.add(new FDSInteger("" + i, ((Integer)par2.get(i)).intValue()));
      }

      FDSArrayList obj = new FDSArrayList(par1, list);
      this.tags.put(par1, obj);
   }

   public ArrayList getIntegerArrayList(String par1) {
      FDSArrayList list = this.tags.containsKey(par1) ? (FDSArrayList)this.tags.get(par1) : null;
      ArrayList list2 = new ArrayList();
      if (list != null) {
         for(int i = 0; i < list.value.size(); ++i) {
            list2.add(((FDSInteger)list.value.get(i)).value);
         }
      }

      return list2;
   }

   public void setStringArrayList(String par1, ArrayList par2) {
      ArrayList list = new ArrayList();

      for(int i = 0; i < par2.size(); ++i) {
         list.add(new FDSString("" + i, (String)par2.get(i)));
      }

      FDSArrayList obj = new FDSArrayList(par1, list);
      this.tags.put(par1, obj);
   }

   public ArrayList getStringArrayList(String par1) {
      FDSArrayList list = this.tags.containsKey(par1) ? (FDSArrayList)this.tags.get(par1) : null;
      ArrayList list2 = new ArrayList();
      if (list != null) {
         for(int i = 0; i < list.value.size(); ++i) {
            list2.add(((FDSString)list.value.get(i)).value);
         }
      }

      return list2;
   }

   public void setBooleanArrayList(String par1, ArrayList par2) {
      ArrayList list = new ArrayList();

      for(int i = 0; i < par2.size(); ++i) {
         list.add(new FDSBoolean("" + i, ((Boolean)par2.get(i)).booleanValue()));
      }

      FDSArrayList obj = new FDSArrayList(par1, list);
      this.tags.put(par1, obj);
   }

   public ArrayList getBooleanArrayList(String par1) {
      FDSArrayList list = this.tags.containsKey(par1) ? (FDSArrayList)this.tags.get(par1) : null;
      ArrayList list2 = new ArrayList();
      if (list != null) {
         for(int i = 0; i < list.value.size(); ++i) {
            list2.add(((FDSBoolean)list.value.get(i)).value);
         }
      }

      return list2;
   }

   public void write(DataOutput par1) throws IOException {
      Iterator iterator = this.tags.keySet().iterator();
      writeString(par1, this.tag);

      while(iterator.hasNext()) {
         String s = (String)iterator.next();
         FDSBase base = (FDSBase)this.tags.get(s);
         par1.writeByte(FDSBase.getByteFromFDSType(base.getClass()));
         base.write(par1);
      }

      par1.writeByte(0);
   }

   public void load(DataInput par1) throws IOException {
      this.tags.clear();
      this.tag = readString(par1);

      byte var1;
      while((var1 = par1.readByte()) != 0) {
         try {
            FDSBase base = (FDSBase)FDSBase.getFDSBaseFromType(var1).newInstance();
            base.load(par1);
            this.tags.put(base.getTag(), base);
         } catch (Exception var4) {
            System.out.println("[FDS] Bad Byte, skipping...");
            var4.printStackTrace();
         }
      }

   }

   public FDSTagCompound copy() {
      FDSTagCompound var1 = new FDSTagCompound(this.getTag());
      ArrayList bases = new ArrayList(this.tags.values());
      Iterator i$ = bases.iterator();

      while(i$.hasNext()) {
         FDSBase base = (FDSBase)i$.next();
         var1.tags.put(base.getTag(), base.copy());
      }

      return var1;
   }

   public String getTag() {
      return this.tag;
   }

   public String toString() {
      return "[" + this.tag + ":COMPOUND:" + this.tags + "]";
   }

   public Object getObject() {
      return this;
   }
}
