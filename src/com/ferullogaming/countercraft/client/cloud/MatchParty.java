package com.ferullogaming.countercraft.client.cloud;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.game.references.GameType;
import java.util.ArrayList;

public class MatchParty {
   public static final int MAX_PLAYERS = 5;
   public boolean isSearching = false;
   public String gameType;
   public ArrayList gameMaps = new ArrayList();
   private String host = "";
   private String gameRegion = "All";
   private ArrayList members = new ArrayList();

   public MatchParty() {
      this.members = new ArrayList();
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.host = par1.getString("host");
      this.members.clear();
      this.members.addAll(par1.getStringArrayList("members"));
      this.isSearching = par1.getBoolean("searching");
      this.gameType = par1.getString("gamemode");
      this.gameRegion = par1.getString("region");
      this.gameMaps.clear();
      this.gameMaps.addAll(par1.getStringArrayList("maps"));
   }

   public boolean isPresent(String par1) {
      return this.host.equals(par1) ? true : this.members.contains(par1);
   }

   public boolean isHost(String par1) {
      return this.host.equals(par1);
   }

   public String getHost() {
      return this.host;
   }

   public String getGameType() {
      if (this.gameType != null && this.gameType.length() > 0) {
         GameType[] arr$ = GameType.gameTypeList;
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            GameType type = arr$[i$];
            if (type != null && type.getNameID().equals(this.gameType)) {
               return type.getDisplayName();
            }
         }

         return this.gameType;
      } else {
         return "None";
      }
   }

   public String getGameMaps() {
      if (this.gameMaps != null && this.gameMaps.size() > 0) {
         String var1 = "";

         for(int i = 0; i < this.gameMaps.size(); ++i) {
            var1 = var1 + (String)this.gameMaps.get(i);
            if (i != this.gameMaps.size() - 1) {
               var1 = var1 + ", ";
            }
         }

         return var1;
      } else {
         return "None";
      }
   }

   public String getGameRegion() {
      return this.gameRegion != null && this.gameRegion.length() > 0 ? this.gameRegion : "None";
   }

   public ArrayList getPartyUsernames() {
      ArrayList list = new ArrayList();
      list.add(this.host);
      list.addAll(this.members);
      return list;
   }
}
