package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.util.ArrayList;
import java.util.Iterator;

public class TradeHandler {
   public ArrayList openTrades = new ArrayList();
   public ArrayList history = new ArrayList();

   public Trade getOpenTrade(String uuid) {
      Iterator i$ = this.openTrades.iterator();

      Trade trade;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         trade = (Trade)i$.next();
      } while(!trade.tradeUUID.equals(uuid));

      return trade;
   }

   public void readFromFDS(FDSTagCompound par1) {
      int var1 = par1.getInteger("openTradeSize");
      this.openTrades.clear();

      int var2;
      for(var2 = 0; var2 < var1; ++var2) {
         FDSTagCompound tag = par1.getTagCompound("openTrade" + var2);
         Trade trade = new Trade();
         trade.readFromFDS(tag);
         this.openTrades.add(trade);
      }

      var2 = par1.getInteger("historySize");
      this.history.clear();

      for(int i = 0; i < var2; ++i) {
         FDSTagCompound tag = par1.getTagCompound("history" + i);
         Trade trade = new Trade();
         trade.readFromFDS(tag);
         this.history.add(trade);
      }

   }
}
