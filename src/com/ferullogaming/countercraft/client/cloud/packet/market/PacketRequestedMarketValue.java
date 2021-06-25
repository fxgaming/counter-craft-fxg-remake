package com.ferullogaming.countercraft.client.cloud.packet.market;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class PacketRequestedMarketValue extends G_TcpPacketCustomPayload {
   private static HashMap tempMarketValues = new HashMap();
   private static HashMap tempMarketValuesStarting = new HashMap();
   private static HashMap tempMarketValuesLastSold = new HashMap();

   public static int getLastSoldValue(int par1) {
      if (!tempMarketValuesLastSold.containsKey(par1)) {
         tempMarketValuesLastSold.put(par1, Integer.valueOf(-1));
      }

      return ((Integer)tempMarketValuesLastSold.get(par1)).intValue();
   }

   public static int getSuggestedValue(int par1) {
      if (!tempMarketValues.containsKey(par1)) {
         tempMarketValues.put(par1, Integer.valueOf(-1));
      }

      return ((Integer)tempMarketValues.get(par1)).intValue();
   }

   public static int getStartingValue(int par1) {
      if (!tempMarketValuesStarting.containsKey(par1)) {
         tempMarketValuesStarting.put(par1, Integer.valueOf(-1));
      }

      return ((Integer)tempMarketValuesStarting.get(par1)).intValue();
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
      int cloudItemID = par1.readInt();
      int value = par1.readInt();
      int value2 = par1.readInt();
      int value3 = par1.readInt();
      tempMarketValues.put(cloudItemID, value);
      tempMarketValuesLastSold.put(cloudItemID, value2);
      tempMarketValuesStarting.put(cloudItemID, value3);
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
