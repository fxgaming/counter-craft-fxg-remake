package com.f3rullo14.cloud.util;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap {
   private transient IntHashMapEntry[] slots = new IntHashMapEntry[16];
   private transient int count;
   private int threshold = 12;
   private final float growFactor = 0.75F;
   private transient volatile int versionStamp;
   private Set keySet = new HashSet();

   private static int computeHash(int par0) {
      par0 ^= par0 >>> 20 ^ par0 >>> 12;
      return par0 ^ par0 >>> 7 ^ par0 >>> 4;
   }

   private static int getSlotIndex(int par0, int par1) {
      return par0 & par1 - 1;
   }

   public Object lookup(int par1) {
      int j = computeHash(par1);

      for(IntHashMapEntry inthashmapentry = this.slots[getSlotIndex(j, this.slots.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.nextEntry) {
         if (inthashmapentry.hashEntry == par1) {
            return inthashmapentry.valueEntry;
         }
      }

      return null;
   }

   public boolean containsItem(int par1) {
      return this.lookupEntry(par1) != null;
   }

   final IntHashMapEntry lookupEntry(int par1) {
      int j = computeHash(par1);

      for(IntHashMapEntry inthashmapentry = this.slots[getSlotIndex(j, this.slots.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.nextEntry) {
         if (inthashmapentry.hashEntry == par1) {
            return inthashmapentry;
         }
      }

      return null;
   }

   public void addKey(int par1, Object par2Obj) {
      this.keySet.add(par1);
      int j = computeHash(par1);
      int k = getSlotIndex(j, this.slots.length);

      for(IntHashMapEntry inthashmapentry = this.slots[k]; inthashmapentry != null; inthashmapentry = inthashmapentry.nextEntry) {
         if (inthashmapentry.hashEntry == par1) {
            inthashmapentry.valueEntry = par2Obj;
            return;
         }
      }

      ++this.versionStamp;
      this.insert(j, par1, par2Obj, k);
   }

   private void grow(int par1) {
      IntHashMapEntry[] ainthashmapentry = this.slots;
      int j = ainthashmapentry.length;
      if (j == 1073741824) {
         this.threshold = Integer.MAX_VALUE;
      } else {
         IntHashMapEntry[] ainthashmapentry1 = new IntHashMapEntry[par1];
         this.copyTo(ainthashmapentry1);
         this.slots = ainthashmapentry1;
         float var10001 = (float)par1;
         this.getClass();
         this.threshold = (int)(var10001 * 0.75F);
      }

   }

   private void copyTo(IntHashMapEntry[] par1ArrayOfIntHashMapEntry) {
      IntHashMapEntry[] ainthashmapentry1 = this.slots;
      int i = par1ArrayOfIntHashMapEntry.length;

      for(int j = 0; j < ainthashmapentry1.length; ++j) {
         IntHashMapEntry inthashmapentry = ainthashmapentry1[j];
         if (inthashmapentry != null) {
            ainthashmapentry1[j] = null;

            IntHashMapEntry inthashmapentry1;
            do {
               inthashmapentry1 = inthashmapentry.nextEntry;
               int k = getSlotIndex(inthashmapentry.slotHash, i);
               inthashmapentry.nextEntry = par1ArrayOfIntHashMapEntry[k];
               par1ArrayOfIntHashMapEntry[k] = inthashmapentry;
               inthashmapentry = inthashmapentry1;
            } while(inthashmapentry1 != null);
         }
      }

   }

   public Object removeObject(int par1) {
      this.keySet.remove(par1);
      IntHashMapEntry inthashmapentry = this.removeEntry(par1);
      return inthashmapentry == null ? null : inthashmapentry.valueEntry;
   }

   final IntHashMapEntry removeEntry(int par1) {
      int j = computeHash(par1);
      int k = getSlotIndex(j, this.slots.length);
      IntHashMapEntry inthashmapentry = this.slots[k];

      IntHashMapEntry inthashmapentry1;
      IntHashMapEntry inthashmapentry2;
      for(inthashmapentry1 = inthashmapentry; inthashmapentry1 != null; inthashmapentry1 = inthashmapentry2) {
         inthashmapentry2 = inthashmapentry1.nextEntry;
         if (inthashmapentry1.hashEntry == par1) {
            ++this.versionStamp;
            --this.count;
            if (inthashmapentry == inthashmapentry1) {
               this.slots[k] = inthashmapentry2;
            } else {
               inthashmapentry.nextEntry = inthashmapentry2;
            }

            return inthashmapentry1;
         }

         inthashmapentry = inthashmapentry1;
      }

      return inthashmapentry1;
   }

   public void clearMap() {
      ++this.versionStamp;
      IntHashMapEntry[] ainthashmapentry = this.slots;

      for(int i = 0; i < ainthashmapentry.length; ++i) {
         ainthashmapentry[i] = null;
      }

      this.count = 0;
   }

   private void insert(int par1, int par2, Object par3Obj, int par4) {
      IntHashMapEntry inthashmapentry = this.slots[par4];
      this.slots[par4] = new IntHashMapEntry(par1, par2, par3Obj, inthashmapentry);
      if (this.count++ >= this.threshold) {
         this.grow(2 * this.slots.length);
      }

   }

   static int getHash(int par0) {
      return computeHash(par0);
   }
}
