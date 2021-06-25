package com.ferullogaming.countercraft.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public abstract class IGamePlayerHandler {
   public static final String VAR_BUYTIME = "buyTime";
   public static final String VAR_SPAWNPROTECTION = "spawnPro";
   public static final String VAR_PLAYTIME = "playTime";
   private IGame game;
   private ArrayList teams;

   public IGamePlayerHandler(IGame par1) {
      this.game = par1;
      this.teams = new ArrayList();
   }

   public abstract void onPlayerJoined(EntityPlayer var1, String var2);

   public abstract void onPlayerExit(EntityPlayer var1);

   public abstract void onPlayerUpdate(EntityPlayer var1);

   public abstract void onPlayerDeath(EntityPlayer var1, DamageSource var2);

   public abstract void onPlayerRespawn(EntityPlayer var1);

   public abstract boolean onPlayerDamaged(EntityPlayer var1, DamageSource var2);

   public abstract int getMaxPlayers();

   public abstract boolean acceptsPlayersFromMM();

   public abstract BlockLocation getLobby();

   public ArrayList onPlayerDeathItemsDropped(EntityPlayer par1, ArrayList par2) {
      return par2;
   }

   public boolean allowItemTossed(EntityPlayer par1, ItemStack par2) {
      return true;
   }

   public boolean allowItemPickup(EntityPlayer par1, ItemStack par2, EntityItem par3) {
      return true;
   }

   public void onPlayerSwitchTeam(EntityPlayer par1) {
   }

   public void onBuyMenuPurchased(EntityPlayer par1, ItemStack par2) {
   }

   public void onGunFired(EntityPlayer par1, ItemStack par2) {
   }

   public String onClientMessageReceived(String par1, String par2, String par3) {
      return par1;
   }

   public String getPlayerDisplayUsername(String par1) {
      return par1;
   }

   public IGame getGame() {
      return this.game;
   }

   public void addTeam(Team par1) {
      this.teams.add(par1);
   }

   public void removeTeam(Team par1) {
      this.teams.remove(par1);
   }

   public boolean hasTeam(String par1) {
      Iterator i$ = this.teams.iterator();

      Team team;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         team = (Team)i$.next();
      } while(!team.teamName.equals(par1));

      return true;
   }

   public Team getRandomTeam() {
      Random rand = new Random();
      return (Team)this.getTeams().get(rand.nextInt(this.getTeams().size()));
   }

   public boolean hasPlayerTeam(EntityPlayer par1) {
      return this.getPlayerTeam(par1) != null;
   }

   public Team getPlayerTeam(EntityPlayer par1) {
      Iterator i$ = this.teams.iterator();

      Team team;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         team = (Team)i$.next();
      } while(!team.isPlayerPresent(par1.username));

      return team;
   }

   public Team getPlayerTeam(String par1) {
      Iterator i$ = this.teams.iterator();

      Team team;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         team = (Team)i$.next();
      } while(!team.isPlayerPresent(par1));

      return team;
   }

   public boolean isPlayerPresent(String par1) {
      Iterator i$ = this.getPlayers().iterator();

      String player;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         player = (String)i$.next();
      } while(!player.equalsIgnoreCase(par1));

      return true;
   }

   public void removePlayer(EntityPlayer par1) {
      Iterator i$ = this.teams.iterator();

      Team team;
      do {
         if (!i$.hasNext()) {
            return;
         }

         team = (Team)i$.next();
      } while(!team.removePlayerFromTeam(par1.username));

      GameHelper.unfreezePlayer(par1.username);
      this.onPlayerExit(par1);
   }

   public boolean addPlayer(EntityPlayer par1, String par2Team) {
      Iterator i$ = this.teams.iterator();

      Team team;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         team = (Team)i$.next();
      } while(!team.teamName.toLowerCase().equals(par2Team.toLowerCase()) || !team.addPlayerToTeam(par1.username));

      this.onPlayerJoined(par1, par2Team);
      return true;
   }

   public Team getNextBalancedOpenTeam() {
      Team team = this.getTeam("blue");
      Team red = this.getTeam("red");
      if (red.getPlayers().size() < team.getPlayers().size()) {
         team = red;
      }

      return team;
   }

   public Team getNextRandomOpenTeam() {
      Team var1 = null;

      while(true) {
         while(var1 == null) {
            Iterator i$ = this.teams.iterator();

            while(i$.hasNext()) {
               Team team = (Team)i$.next();
               if (team.getPlayers().size() < team.teamMaxPlayers) {
                  var1 = team;
                  break;
               }
            }
         }

         return var1;
      }
   }

   public Team getTeam(String par1) {
      Iterator i$ = this.teams.iterator();

      Team team;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         team = (Team)i$.next();
      } while(!team.teamName.equalsIgnoreCase(par1));

      return team;
   }

   public ArrayList getTeams() {
      return this.teams;
   }

   public ArrayList getPlayers() {
      ArrayList list = new ArrayList();
      Iterator i$ = this.teams.iterator();

      while(i$.hasNext()) {
         Team team = (Team)i$.next();
         Iterator i$1 = team.getPlayers().iterator();

         while(i$1.hasNext()) {
            String username = (String)i$1.next();
            list.add(username);
         }
      }

      return list;
   }
}
