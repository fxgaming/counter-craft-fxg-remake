package com.ferullogaming.countercraft.client;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.friend.GuiCCFriendMenu;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class FriendsManager {
   private static boolean keyEvent;
   private static boolean lastKeyEvent;
   private ArrayList friendsList = new ArrayList();
   private ArrayList pendingFriendsList = new ArrayList();
   private int requestFriendDelay = 0;
   private int keyDelay = 0;

   public FriendsManager() {
      this.friendsList = new ArrayList();
   }

   public static FriendsManager instance() {
      return CounterCraft.getClientManager().getFriendManager();
   }

   public void onUpdate() {
      Minecraft mc = Minecraft.getMinecraft();
      lastKeyEvent = keyEvent;
      keyEvent = Keyboard.isKeyDown(KeyBindingManager.keyFriends.keyCode);
      if (this.keyDelay > 0) {
         --this.keyDelay;
      }

      if (lastKeyEvent != keyEvent && keyEvent && this.keyDelay <= 0) {
         this.keyDelay = 3;
         if (mc.currentScreen != null && mc.currentScreen instanceof GuiChat) {
            return;
         }

         if (mc.currentScreen instanceof GuiCCFriendMenu) {
            mc.displayGuiScreen(((GuiCCFriendMenu)mc.currentScreen).parentGui);
         } else if (mc.theWorld != null) {
            mc.displayGuiScreen(new GuiCCFriendMenu((GuiScreen)null));
         } else {
            mc.displayGuiScreen(new GuiCCFriendMenu(mc.currentScreen));
         }
      }

      if (this.requestFriendDelay++ >= 40) {
         ClientCloudManager.sendPacket(new PacketClientRequest(RequestType.PLAYER_FRIENDS, new String[]{Minecraft.getMinecraft().getSession().getUsername()}));
         this.requestFriendDelay = 0;
      }

   }

   public boolean isFriend(String par1) {
      Iterator i$ = this.friendsList.iterator();

      String var1;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         var1 = (String)i$.next();
      } while(!var1.equalsIgnoreCase(par1));

      return true;
   }

   public ArrayList getFriendListFiltered() {
      ArrayList list = new ArrayList();

      int i;
      for(i = 0; i < this.friendsList.size(); ++i) {
         PlayerDataCloud friendData = PlayerDataHandler.getPlayerCloudData((String)this.friendsList.get(i));
         if (friendData.isOnline) {
            list.add(this.friendsList.get(i));
         }
      }

      for(i = 0; i < this.friendsList.size(); ++i) {
         if (!list.contains(this.friendsList.get(i))) {
            list.add(this.friendsList.get(i));
         }
      }

      return list;
   }

   public ArrayList getFriendListOnline() {
      ArrayList list = new ArrayList();

      for(int i = 0; i < this.friendsList.size(); ++i) {
         PlayerDataCloud friendData = PlayerDataHandler.getPlayerCloudData((String)this.friendsList.get(i));
         if (friendData.isOnline) {
            list.add(this.friendsList.get(i));
         }
      }

      return list;
   }

   public ArrayList getFriendList() {
      return this.friendsList;
   }

   public ArrayList getPendingFriendList() {
      return this.pendingFriendsList;
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.friendsList.clear();
      this.friendsList = par1.getStringArrayList("friends");
      this.pendingFriendsList.clear();
      this.pendingFriendsList = par1.getStringArrayList("pending");
   }
}
