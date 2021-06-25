package com.ferullogaming.countercraft.game;

import com.f3rullo14.fds.tag.FDSTagCompound;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class Team {
   public String teamName;
   public ArrayList teamSpawns;
   public EnumChatFormatting teamColor;
   public int teamMaxPlayers = 5;
   public Loadout teamDefaultLoadout;
   private ArrayList teamPlayers;
   private FDSTagCompound teamObjects = new FDSTagCompound("teamObjects");

   public Team(String par1, int par2, EnumChatFormatting par3) {
      this.teamName = par1;
      this.teamMaxPlayers = par2;
      this.teamPlayers = new ArrayList();
      this.teamSpawns = new ArrayList();
      this.teamColor = par3;
   }

   public void writeTeamToCompound(FDSTagCompound par1) {
      FDSTagCompound team = new FDSTagCompound("team" + this.teamName);
      team.setString("teamName", this.teamName);
      team.setInteger("teamMaxPlayers", this.teamMaxPlayers);
      if (this.teamDefaultLoadout != null) {
         this.teamDefaultLoadout.saveLoadoutToFDS(this.teamName, team);
      }

      team.setInteger("teamSpawnsSize", this.teamSpawns.size());

      int i;
      for(i = 0; i < this.teamSpawns.size(); ++i) {
         ((BlockLocation)this.teamSpawns.get(i)).saveToFDS("teamSpawns" + i, team);
      }

      team.setInteger("teamPlayersSize", this.teamPlayers.size());

      for(i = 0; i < this.teamPlayers.size(); ++i) {
         team.setString("teamPlayer" + i, (String)this.teamPlayers.get(i));
      }

      if (this.teamObjects != null && this.teamObjects.getTagsSize() > 0) {
         team.setTagCompound("teamObjects", this.teamObjects);
      }

      par1.setTagCompound("team" + this.teamName, team);
   }

   public void readTeamFromFDS(FDSTagCompound par2) {
      if (par2.hasTag("team" + this.teamName)) {
         FDSTagCompound tag = par2.getTagCompound("team" + this.teamName);
         this.teamMaxPlayers = tag.getInteger("teamMaxPlayers");
         Loadout load = Loadout.createLoadoutFromFDS(this.teamName, tag);
         if (load != null) {
            this.teamDefaultLoadout = load.copy();
         }

         int var1 = tag.getInteger("teamSpawnsSize");
         this.teamSpawns.clear();

         int i;
         for(i = 0; i < var1; ++i) {
            BlockLocation spawn = BlockLocation.createBlockLocationFromFDS("teamSpawns" + i, tag);
            this.teamSpawns.add(spawn.copy());
         }

         var1 = tag.getInteger("teamPlayersSize");
         this.teamPlayers.clear();

         for(i = 0; i < var1; ++i) {
            String var2 = tag.getString("teamPlayer" + i);
            this.teamPlayers.add(var2);
         }

         if (tag.hasTag("teamObjects")) {
            this.teamObjects = tag.getTagCompound("teamObjects");
         }
      }

   }

   public void clearTeamObjects() {
      this.teamObjects = new FDSTagCompound("teamObjects");
   }

   public void tpTeamSetSpawns() {
      Random rand = new Random();
      ArrayList randomSpawns = new ArrayList(this.teamSpawns);

      for(int i = 0; i < this.getPlayers().size(); ++i) {
         BlockLocation loc = (BlockLocation)randomSpawns.get(rand.nextInt(randomSpawns.size()));
         GameHelper.teleportPlayer((String)this.getPlayers().get(i), loc);
      }

   }

   public void tpTeamRandomSpawns() {
      for(int i = 0; i < this.getPlayers().size(); ++i) {
         GameHelper.teleportPlayer((String)this.getPlayers().get(i), this.getRandomSpawn());
      }

   }

   public String getRandomPlayer() {
      if (this.teamPlayers != null && this.teamPlayers.size() > 0) {
         Random rand = new Random();
         return (String)this.teamPlayers.get(rand.nextInt(this.teamPlayers.size()));
      } else {
         return null;
      }
   }

   public BlockLocation getRandomSpawn() {
      Random rand = new Random();
      return (BlockLocation)this.teamSpawns.get(rand.nextInt(this.teamSpawns.size()));
   }

   public FDSTagCompound getTeamObjectData() {
      if (this.teamObjects == null) {
         this.teamObjects = new FDSTagCompound("teamObjects");
      }

      return this.teamObjects;
   }

   public void setTeamObject(String par1, Object par2) {
      if (par1 != null && par2 != null) {
         if (par2 instanceof Integer) {
            this.getTeamObjectData().setInteger(par1, ((Integer)par2).intValue());
         }

         if (par2 instanceof String) {
            this.getTeamObjectData().setString(par1, (String)par2);
         }

         if (par2 instanceof Boolean) {
            this.getTeamObjectData().setBoolean(par1, ((Boolean)par2).booleanValue());
         }
      }

   }

   public String getTeamObjectString(String par1) {
      return this.getTeamObjectData().getString(par1);
   }

   public Boolean getTeamObjectBoolean(String par1) {
      return this.getTeamObjectData().getBoolean(par1);
   }

   public Integer getTeamObjectInteger(String par1) {
      return this.getTeamObjectData().getInteger(par1);
   }

   public boolean addPlayerToTeam(String par1) {
      int var1 = this.teamPlayers.size();
      if (var1 + 1 <= this.teamMaxPlayers) {
         this.teamPlayers.add(par1);
         return true;
      } else {
         return false;
      }
   }

   public boolean removePlayerFromTeam(String par1) {
      if (this.teamPlayers.contains(par1)) {
         this.teamPlayers.remove(par1);
         return true;
      } else {
         return false;
      }
   }

   public void setTeamsInventorys(Loadout par1) {
      if (par1 != null) {
         for(int i = 0; i < this.getPlayers().size(); ++i) {
            String var1 = (String)this.getPlayers().get(i);
            GameHelper.getPlayerFromUsername(var1).inventory.clearInventory(-1, -1);
            GameHelper.setPlayerInventory(GameHelper.getPlayerFromUsername(var1), par1);
            GameHelper.getPlayerFromUsername(var1).inventoryContainer.detectAndSendChanges();
         }
      }

   }

   public void playSoundFireworkPerPlayer() {
      Iterator i$ = this.getPlayers().iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         EntityPlayer player = GameHelper.getPlayerFromUsername(var1);
         if (player != null) {
            GameHelper.playSoundFireworks(player.worldObj, player.posX, player.posY, player.posZ);
         }
      }

   }

   public void playVictoryGameMusicPerPlayer(String winner) {
      Iterator i$ = this.getPlayers().iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         EntityPlayer player = GameHelper.getPlayerFromUsername(var1);
         GameHelper.playMusic(player.worldObj, player.posX, player.posY, player.posZ, "callout." + winner + ".victory");
         GameHelper.playMusic(player.worldObj, player.posX, player.posY, player.posZ, "match.music_victory");
      }

   }

   public void playDefeatGameMusicPerPlayer(String loser) {
      Iterator i$ = this.getPlayers().iterator();

      while(i$.hasNext()) {
         String var1 = (String)i$.next();
         EntityPlayer player = GameHelper.getPlayerFromUsername(var1);
         GameHelper.playMusic(player.worldObj, player.posX, player.posY, player.posZ, "callout." + loser + ".defeat");
         GameHelper.playMusic(player.worldObj, player.posX, player.posY, player.posZ, "match.music_defeat");
      }

   }

   public void removeAllPlayers() {
      this.teamPlayers.clear();
   }

   public ArrayList getPlayers() {
      return this.teamPlayers;
   }

   public boolean isPlayerPresent(String par1) {
      Iterator i$ = this.getPlayers().iterator();

      String var1;
      do {
         if (!i$.hasNext()) {
            return false;
         }

         var1 = (String)i$.next();
      } while(!var1.equalsIgnoreCase(par1));

      return true;
   }

   public String toString() {
      return "Team[name=" + this.teamName + ", spawns=" + this.teamSpawns.size() + ", size=" + this.teamMaxPlayers + ", loadout=" + (this.teamDefaultLoadout != null) + "]";
   }
}
