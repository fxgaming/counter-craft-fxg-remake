package com.ferullogaming.countercraft.client.cloud;

public class ConversationMessage {
   private String sender;
   private String message;

   public ConversationMessage(String par1, String par2) {
      this.sender = par1;
      this.message = par2;
   }

   public String getSender() {
      return this.sender;
   }

   public String getMessage() {
      return this.message;
   }
}
