package com.ferullogaming.countercraft.client.minimap.region;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Nbt {
   public static final byte TAG_END = 0;
   public static final byte TAG_BYTE = 1;
   public static final byte TAG_SHORT = 2;
   public static final byte TAG_INT = 3;
   public static final byte TAG_LONG = 4;
   public static final byte TAG_FLOAT = 5;
   public static final byte TAG_DOUBLE = 6;
   public static final byte TAG_BYTE_ARRAY = 7;
   public static final byte TAG_STRING = 8;
   public static final byte TAG_LIST = 9;
   public static final byte TAG_COMPOUND = 10;
   public static final byte TAG_INT_ARRAY = 11;
   public static final byte TAG_NULL = -1;
   public static Nbt nullElement = new Nbt((byte)-1, "", (Object)null);
   public byte tagID;
   public String name;
   private Object data;

   public Nbt(byte tagID, String name, Object data) {
      this.tagID = tagID;
      this.name = name;
      this.data = data;
   }

   public static Nbt readNextElement(DataInputStream dis) throws IOException {
      String name = "";
      byte tagID = dis.readByte();
      if (tagID != 0) {
         name = dis.readUTF();
      }

      return readElementData(dis, tagID, name);
   }

   public static Nbt readElementData(DataInputStream dis, byte tagID, String name) throws IOException {
      Nbt elem = null;
      Nbt child;
      switch(tagID) {
      case 0:
         break;
      case 1:
         elem = new Nbt(tagID, name, new Byte(dis.readByte()));
         break;
      case 2:
         elem = new Nbt(tagID, name, new Short(dis.readShort()));
         break;
      case 3:
         elem = new Nbt(tagID, name, new Integer(dis.readInt()));
         break;
      case 4:
         elem = new Nbt(tagID, name, new Long(dis.readLong()));
         break;
      case 5:
         elem = new Nbt(tagID, name, new Float(dis.readFloat()));
         break;
      case 6:
         elem = new Nbt(tagID, name, new Double(dis.readDouble()));
         break;
      case 7:
         int baLength = dis.readInt();
         byte[] byteArray = null;
         if (baLength > 0) {
            byteArray = new byte[baLength];
            dis.readFully(byteArray);
         }

         elem = new Nbt(tagID, name, byteArray);
         break;
      case 8:
         elem = new Nbt(tagID, name, dis.readUTF());
         break;
      case 9:
         byte childType = dis.readByte();
         int listLength = dis.readInt();
         elem = new Nbt(tagID, name, (Object)null);

         for(int i = 0; i < listLength; ++i) {
            child = readElementData(dis, childType, "");
            elem.addChild(child);
         }

         return elem != null ? elem : nullElement;
      case 10:
         elem = new Nbt(tagID, name, (Object)null);
         boolean end = false;

         while(!end) {
            child = readNextElement(dis);
            if (child.isNull()) {
               end = true;
            } else {
               elem.addChild(child);
            }
         }

         return elem != null ? elem : nullElement;
      case 11:
         int iaLength = dis.readInt();
         int[] intArray = null;
         if (iaLength > 0) {
            intArray = new int[iaLength];

            for(int i = 0; i < iaLength; ++i) {
               intArray[i] = dis.readInt();
            }
         }

         elem = new Nbt(tagID, name, intArray);
         break;
      default:
         System.out.format("error: encountered unknown tag id\n");
      }

      return elem != null ? elem : nullElement;
   }

   public boolean isNull() {
      return this.tagID == -1;
   }

   public void addChild(Nbt child) {
      if (this.tagID == 9) {
         if (this.data == null) {
            this.data = new ArrayList();
         }

         List childrenList = (List)this.data;
         childrenList.add(child);
      }

      if (this.tagID == 10) {
         if (this.data == null) {
            this.data = new HashMap();
         }

         Map childrenMap = (Map)this.data;
         childrenMap.put(child.name, child);
      }

   }

   public Nbt getChild(int index) {
      Nbt child = null;
      if (this.tagID == 9 && this.data != null) {
         List childrenList = (List)this.data;
         if (index >= 0 && index < childrenList.size()) {
            child = (Nbt)childrenList.get(index);
         }
      }

      return child != null ? child : nullElement;
   }

   public Nbt getChild(String name) {
      Nbt child = null;
      if (this.tagID == 10 && this.data != null) {
         Map childrenMap = (Map)this.data;
         child = (Nbt)childrenMap.get(name);
      }

      return child != null ? child : nullElement;
   }

   public int size() {
      int size = 0;
      if (this.tagID == 9 && this.data != null) {
         List childrenList = (List)this.data;
         size = childrenList.size();
      }

      return size;
   }

   public byte getByte() {
      return this.tagID == 1 && this.data != null ? ((Byte)this.data).byteValue() : 0;
   }

   public short getShort() {
      return this.tagID == 2 && this.data != null ? ((Short)this.data).shortValue() : 0;
   }

   public int getInt() {
      return this.tagID == 3 && this.data != null ? ((Integer)this.data).intValue() : 0;
   }

   public long getLong() {
      return this.tagID == 4 && this.data != null ? ((Long)this.data).longValue() : 0L;
   }

   public float getFloat() {
      return this.tagID == 5 && this.data != null ? ((Float)this.data).floatValue() : 0.0F;
   }

   public double getDouble() {
      return this.tagID == 6 && this.data != null ? ((Double)this.data).doubleValue() : 0.0D;
   }

   public byte[] getByteArray() {
      return this.tagID == 7 && this.data != null ? (byte[])((byte[])this.data) : null;
   }

   public int[] getIntArray() {
      return this.tagID == 11 && this.data != null ? (int[])((int[])this.data) : null;
   }

   public String getString() {
      return this.tagID == 8 && this.data != null ? (String)this.data : null;
   }

   public void writeElement(DataOutputStream dos) throws IOException {
      dos.writeByte(this.tagID);
      if (this.tagID != 0) {
         dos.writeUTF(this.name);
         this.writeElementData(dos);
      }

   }

   public void writeElementData(DataOutputStream dos) throws IOException {
      int i;
      switch(this.tagID) {
      case 0:
         break;
      case 1:
         dos.writeByte(this.getByte());
         break;
      case 2:
         dos.writeShort(this.getShort());
         break;
      case 3:
         dos.writeInt(this.getInt());
         break;
      case 4:
         dos.writeLong(this.getLong());
         break;
      case 5:
         dos.writeFloat(this.getFloat());
         break;
      case 6:
         dos.writeDouble(this.getDouble());
         break;
      case 7:
         byte[] byteArray = this.getByteArray();
         if (byteArray != null) {
            dos.writeInt(byteArray.length);
            dos.write(byteArray);
         } else {
            dos.writeInt(0);
         }
         break;
      case 8:
         dos.writeUTF(this.getString());
         break;
      case 9:
         i = this.size();
         if (this.size() > 0) {
            dos.writeByte(this.getChild(0).tagID);
            dos.writeInt(i);

            for(int i1 = 0; i1 < i1; ++i1) {
               this.getChild(i1).writeElementData(dos);
            }

            return;
         } else {
            dos.writeByte(1);
            dos.writeInt(0);
            break;
         }
      case 10:
         Map childrenMap = (Map)this.data;
         Iterator i$ = childrenMap.values().iterator();

         while(i$.hasNext()) {
            Nbt child = (Nbt)i$.next();
            if (child != null) {
               child.writeElement(dos);
            }
         }

         dos.writeByte(0);
         break;
      case 11:
         int[] intArray = this.getIntArray();
         if (intArray != null) {
            dos.writeInt(intArray.length);

            for(i = 0; i < intArray.length; ++i) {
               dos.writeInt(intArray[i]);
            }

            return;
         } else {
            dos.writeInt(0);
            break;
         }
      default:
         System.out.format("error: encountered unknown tag id\n");
      }

   }
}
