package com.ferullogaming.countercraft.client.cloud;

import java.util.ArrayList;

public class Conversation {
   public String player;
   public ArrayList messages = new ArrayList();
   public boolean hasNotification = false;
   public int notifyBlinkTick = 0;
   public boolean blink = false;

   public Conversation(String par1) {
      this.player = par1;
   }

   public void addMessage(String par1, String par2) {
      if (this.messages.size() > 14) {
         this.messages.remove(0);
      }

      this.messages.add(new ConversationMessage(par1, par2));
   }
}
