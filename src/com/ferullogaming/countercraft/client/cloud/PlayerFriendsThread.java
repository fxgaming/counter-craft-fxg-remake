package com.ferullogaming.countercraft.client.cloud;

import com.ferullogaming.countercraft.client.FriendsManager;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import java.util.Iterator;

public class PlayerFriendsThread extends Thread {
   public void run() {
      Iterator i$ = FriendsManager.instance().getFriendList().iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         PacketClientRequest.sendRequest(RequestType.PLAYER_DATA, var1);
      }

   }
}
