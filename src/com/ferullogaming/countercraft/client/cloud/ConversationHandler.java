package com.ferullogaming.countercraft.client.cloud;

import java.util.HashMap;

public class ConversationHandler {
   public HashMap playerConversations = new HashMap();

   public Conversation getConversation(String par1) {
      if (!this.playerConversations.containsKey(par1)) {
         this.playerConversations.put(par1, new Conversation(par1));
      }

      return (Conversation)this.playerConversations.get(par1);
   }
}
