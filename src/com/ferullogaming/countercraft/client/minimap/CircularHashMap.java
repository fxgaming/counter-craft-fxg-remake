package com.ferullogaming.countercraft.client.minimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class CircularHashMap {
   private Map nodeMap = new HashMap();
   private CircularHashMap.Node headNode = null;
   private CircularHashMap.Node currentNode = null;

   public Object put(Object key, Object value) {
      CircularHashMap.Node node = (CircularHashMap.Node)this.nodeMap.get(key);
      if (node == null) {
         node = new CircularHashMap.Node(key, value);
         this.nodeMap.put(key, node);
         if (this.headNode == null) {
            node.next = node;
            node.prev = node;
         } else {
            node.next = this.headNode.next;
            node.prev = this.headNode;
            this.headNode.next.prev = node;
            this.headNode.next = node;
         }

         if (this.currentNode == null) {
            this.currentNode = node;
         }

         this.headNode = node;
      } else {
         node.value = value;
      }

      return value;
   }

   public Object remove(Object key) {
      CircularHashMap.Node node = (CircularHashMap.Node)this.nodeMap.get(key);
      Object value = null;
      if (node != null) {
         if (this.headNode == node) {
            this.headNode = node.next;
            if (this.headNode == node) {
               this.headNode = null;
            }
         }

         if (this.currentNode == node) {
            this.currentNode = node.next;
            if (this.currentNode == node) {
               this.currentNode = null;
            }
         }

         node.prev.next = node.next;
         node.next.prev = node.prev;
         node.next = null;
         node.prev = null;
         value = node.value;
         this.nodeMap.remove(key);
      }

      return value;
   }

   public void clear() {
      Iterator i$ = this.nodeMap.values().iterator();

      while(i$.hasNext()) {
         CircularHashMap.Node node = (CircularHashMap.Node)i$.next();
         node.next = null;
         node.prev = null;
      }

      this.nodeMap.clear();
      this.headNode = null;
      this.currentNode = null;
   }

   public boolean containsKey(Object key) {
      return this.nodeMap.containsKey(key);
   }

   public int size() {
      return this.nodeMap.size();
   }

   public Set keySet() {
      return this.nodeMap.keySet();
   }

   public Collection values() {
      Collection list = new ArrayList();
      Iterator i$ = this.nodeMap.values().iterator();

      while(i$.hasNext()) {
         CircularHashMap.Node node = (CircularHashMap.Node)i$.next();
         list.add(node.value);
      }

      return list;
   }

   public Collection entrySet() {
      return new ArrayList(this.nodeMap.values());
   }

   public Object get(Object key) {
      CircularHashMap.Node node = (CircularHashMap.Node)this.nodeMap.get(key);
      return node != null ? node.value : null;
   }

   public boolean isEmpty() {
      return this.nodeMap.isEmpty();
   }

   public Entry getNextEntry() {
      if (this.currentNode != null) {
         this.currentNode = this.currentNode.next;
      }

      return this.currentNode;
   }

   public Entry getPrevEntry() {
      if (this.currentNode != null) {
         this.currentNode = this.currentNode.prev;
      }

      return this.currentNode;
   }

   public void rewind() {
      this.currentNode = this.headNode != null ? this.headNode.next : null;
   }

   public boolean setPosition(Object key) {
      CircularHashMap.Node node = (CircularHashMap.Node)this.nodeMap.get(key);
      if (node != null) {
         this.currentNode = node;
      }

      return node != null;
   }

   public class Node implements Entry {
      private final Object key;
      private Object value;
      private CircularHashMap.Node next;
      private CircularHashMap.Node prev;

      Node(Object key, Object value) {
         this.key = key;
         this.value = value;
         this.next = this;
         this.prev = this;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(Object value) {
         Object oldValue = this.value;
         this.value = value;
         return oldValue;
      }
   }
}
