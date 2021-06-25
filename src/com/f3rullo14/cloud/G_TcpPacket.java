package com.f3rullo14.cloud;

import com.f3rullo14.cloud.packet.PacketClientHeartBeat;
import com.f3rullo14.cloud.packet.PacketClientLogin;
import com.f3rullo14.cloud.packet.PacketClientLogout;
import com.f3rullo14.cloud.packet.PacketCloudDisconnected;
import com.f3rullo14.cloud.packet.PacketCloudHeartBeat;
import com.f3rullo14.cloud.util.IntHashMap;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public abstract class G_TcpPacket {
   public static IntHashMap packetIdToClassMap = new IntHashMap();
   private static Map packetClassToIdMap = new HashMap();
   public static boolean isClient = true;
   private static final boolean LOG_ERRORS = false;

   public static void addPacketMapping(int par1, Class par2Class) {
      if (packetIdToClassMap.containsItem(par1)) {
         System.out.println("[G_TcpPacket] PACKET ERROR: Overriding Packet ID [" + par1 + "] with a new packet!");
      }

      packetIdToClassMap.addKey(par1, par2Class);
      packetClassToIdMap.put(par2Class, par1);
   }

   public static G_TcpPacket readPacket(DataInput par1DataInput, Socket par3Socket) throws IOException {
      G_TcpPacket packet = null;

      try {
         int j = par1DataInput.readUnsignedByte();
         if (!packetIdToClassMap.containsItem(j)) {
            throw new IOException("[G_TcpPacket] Bad packet id " + j);
         }

         packet = getNewPacket(j);
         if (packet == null) {
            throw new IOException("[G_TcpPacket] Bad packet id " + j);
         }

         packet.readPacketData(par1DataInput);
      } catch (EOFException var5) {
         return null;
      } catch (SocketTimeoutException var6) {
         return null;
      } catch (SocketException var7) {
         return null;
      } catch (Exception var8) {
         return null;
      }

      par3Socket.setSoTimeout(30000);
      return packet;
   }

   public static void writeString(DataOutput par1, String par2Str) throws IOException {
      if (par2Str.length() > 32767) {
         throw new IOException("String too big");
      } else {
         par1.writeShort(par2Str.length());
         par1.writeChars(par2Str);
      }
   }

   public static String readString(DataInput par1) throws IOException {
      short short1 = par1.readShort();
      StringBuilder stringbuilder = new StringBuilder();

      for(int j = 0; j < short1; ++j) {
         stringbuilder.append(par1.readChar());
      }

      return stringbuilder.toString();
   }

   public static G_TcpPacket getNewPacket(int par1) {
      try {
         if (!packetIdToClassMap.containsItem(par1)) {
            return null;
         }

         Class class1 = (Class)packetIdToClassMap.lookup(par1);
         return (G_TcpPacket)class1.newInstance();
      } catch (InstantiationException var2) {
         var2.printStackTrace();
      } catch (IllegalAccessException var3) {
         var3.printStackTrace();
      }

      return null;
   }

   public static void writePacket(G_TcpPacket par0Packet, DataOutput par1DataOutput) throws IOException {
      par1DataOutput.write(par0Packet.getPacketId());
      par0Packet.writePacketData(par1DataOutput);
   }

   public final int getPacketId() {
      return ((Integer)packetClassToIdMap.get(this.getClass())).intValue();
   }

   public abstract void writePacketData(DataOutput var1) throws IOException;

   public abstract void readPacketData(DataInput var1) throws IOException;

   public abstract void processPacket(G_ITcpConnectionHandler var1);

   public abstract int getPacketSize();

   static {
      addPacketMapping(2, PacketClientLogin.class);
      addPacketMapping(3, PacketClientHeartBeat.class);
      addPacketMapping(4, PacketClientLogout.class);
      addPacketMapping(5, PacketCloudHeartBeat.class);
      addPacketMapping(6, PacketCloudDisconnected.class);
   }
}
