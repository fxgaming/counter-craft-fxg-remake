package com.ferullogaming.countercraft.client.events;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class EventChatSend extends Event {
   public String message;

   public EventChatSend(String par1) {
      this.message = par1;
   }
}
