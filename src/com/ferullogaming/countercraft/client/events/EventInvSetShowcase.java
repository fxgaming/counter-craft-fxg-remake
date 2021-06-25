package com.ferullogaming.countercraft.client.events;

import java.util.ArrayList;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class EventInvSetShowcase extends Event {
   public ArrayList stacksShowcasing = new ArrayList();

   public EventInvSetShowcase(ArrayList par1) {
      this.stacksShowcasing = par1;
   }
}
