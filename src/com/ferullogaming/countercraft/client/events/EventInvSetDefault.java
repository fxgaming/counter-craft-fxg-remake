package com.ferullogaming.countercraft.client.events;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import net.minecraftforge.event.Event;

public class EventInvSetDefault extends Event {
   public CloudItemStack stack;

   public EventInvSetDefault(CloudItemStack par1) {
      this.stack = par1;
   }
}
