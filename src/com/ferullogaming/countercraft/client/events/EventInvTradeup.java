package com.ferullogaming.countercraft.client.events;

import java.util.ArrayList;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class EventInvTradeup extends Event {
   public ArrayList itemsTradingUp;

   public EventInvTradeup(ArrayList par1) {
      this.itemsTradingUp = par1;
   }
}
