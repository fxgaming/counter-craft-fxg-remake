package com.ferullogaming.countercraft.game.inf;

import com.ferullogaming.countercraft.commands.CommandGameManage;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameCommandHandler;
import com.ferullogaming.countercraft.game.Loadout;
import com.ferullogaming.countercraft.game.Team;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;

public class INFCommands extends IGameCommandHandler {
   public INFCommands(IGame par1) {
      super(par1);
   }

   public void onEditCommandCalled(EntityPlayer par1, String[] args) {
      Infected theGame = (Infected)this.getGame();
      INFPlayerHandler playerHandler = (INFPlayerHandler)theGame.getPlayerEventHandler();
      if (args.length >= 1) {
         String var1 = args[0];
         if (var1.equals("lobby")) {
            theGame.lobby = new BlockLocation(par1);
            CommandGameManage.sendChat(par1, "Lobby location placed.");
            return;
         }

         String var2;
         if (var1.equals("map") && args.length >= 2) {
            var2 = args[1];
            CommandGameManage.sendChat(par1, "Map type set to [" + var2 + "].");
            theGame.setGameMapType(var2);
            return;
         }

         if (var1.equals("toggle") && args.length >= 2) {
            var2 = args[1];
            if (var2.equals("night")) {
               theGame.toggleNight = !theGame.toggleNight;
               CommandGameManage.sendChat(par1, "Controls World Time: " + theGame.toggleNight);
               return;
            }

            if (var2.equals("rain")) {
               theGame.toggleRain = !theGame.toggleRain;
               CommandGameManage.sendChat(par1, "Controls World Weather: " + theGame.toggleRain);
               return;
            }
         }

         if (var1.equals("itemspawn") && args.length >= 2) {
            var2 = args[1];
            if (var2.equals("add") && args.length >= 3) {
               int var3 = Integer.parseInt(args[2]);
               ItemSpawn itemSpawn = new ItemSpawn(par1, var3);
               itemSpawn.respawnDelay = 300;
               theGame.itemSpawns.add(itemSpawn);
               CommandGameManage.sendChat(par1, "Item Spawn Point with Item ID '" + var3 + "' Added!");
               return;
            }

            if (var2.equals("clear")) {
               theGame.itemSpawns.clear();
               CommandGameManage.sendChat(par1, "Cleared all Capture Points");
               return;
            }
         }

         if (var1.equals("maxtime") && args.length >= 2) {
            try {
               int var21 = Integer.parseInt(args[1]);
               if (var21 < 1) {
                  var21 = 1;
               }

               if (var21 > 30) {
                  var21 = 30;
               }

               CommandGameManage.sendChat(par1, "Max Time set to [" + var21 + "] minute(s).");
               theGame.maxTime = var21;
               return;
            } catch (Exception var9) {
               ;
            }
         }

         Team team = playerHandler.getTeam(var1);
         if (team != null && args.length >= 2) {
            String var21 = args[1];
            if (var21.equals("spawnadd")) {
               BlockLocation location = new BlockLocation(par1);
               team.teamSpawns.add(location);
               CommandGameManage.sendChat(par1, team.teamName + " team spawn added.");
               return;
            }

            if (var21.equals("spawnclear")) {
               team.teamSpawns.clear();
               CommandGameManage.sendChat(par1, team.teamName + " team's spawns cleared.");
               return;
            }

            if (var21.equals("loadout")) {
               Loadout load = new Loadout();
               load.setInventory(par1);
               team.teamDefaultLoadout = load.copy();
               CommandGameManage.sendChat(par1, team.teamName + " team's default loadout updated.");
               return;
            }
         }

         CommandGameManage.sendChat(par1, "Invalid Usage.");
      }

   }

   public void getCommandInformation(ArrayList par1) {
      par1.add("lobby (Place Lobby)");
      par1.add("map <ID> (Set the Game's map type)");
      par1.add("<Dead|Living> <spawnadd|spawnclear> (Add Team Spawn/Clear All)");
      par1.add("<Dead|Living> loadout (Update Default Loadout)");
      par1.add("maxtime <INT> (<1-30> in minutes)");
      par1.add("itemspawn <add|clear> [itemid] (Specific to Buffs)");
      par1.add("toggle <night|rain> (Toggles if the Mod controls the weather or not)");
   }
}
