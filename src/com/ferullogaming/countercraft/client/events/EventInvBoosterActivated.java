package com.ferullogaming.countercraft.client.events;

import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class EventInvBoosterActivated extends Event {
   public int itemID;
   public CloudItemStack stack;

   public EventInvBoosterActivated(CloudItemStack par1) {
      this.itemID = par1.getCloudItem().getID();
      this.stack = par1;
   }
}
