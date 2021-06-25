package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import java.util.ArrayList;
import java.util.UUID;

public class Trade {
   public static final int PENDING = 1;
   public static final int CLOSED = 2;
   public int STATUS = 1;
   public int closedState = 1;
   public String tradeUUID = UUID.randomUUID().toString();
   public String dateMade;
   public String dateEnded;
   public String trader1UUID;
   public String trader1Username;
   public ArrayList offer1 = new ArrayList();
   public String trader2UUID;
   public String trader2Username;
   public ArrayList offer2 = new ArrayList();

   public void readFromFDS(FDSTagCompound par1) {
      this.trader1UUID = par1.getString("tr1");
      this.trader1Username = par1.getString("tr1name");
      this.trader2UUID = par1.getString("tr2");
      this.trader2Username = par1.getString("tr2name");
      this.STATUS = par1.getInteger("status");
      this.dateMade = par1.getString("dateOpened");
      this.dateEnded = par1.getString("dateClosed");
      this.closedState = par1.getInteger("closedstate");
      this.tradeUUID = par1.getString("uuid");
      int var1 = par1.getInteger("tr1offersize");
      this.offer1.clear();

      int var2;
      for(var2 = 0; var2 < var1; ++var2) {
         FDSTagCompound tag = par1.getTagCompound("tr1stack" + var2);
         CloudItemStack stack = CloudItemStack.readFromFDS(tag);
         this.offer1.add(stack);
      }

      var2 = par1.getInteger("tr2offersize");
      this.offer2.clear();

      for(int i = 0; i < var2; ++i) {
         FDSTagCompound tag = par1.getTagCompound("tr2stack" + i);
         CloudItemStack stack = CloudItemStack.readFromFDS(tag);
         this.offer2.add(stack);
      }

   }
}
