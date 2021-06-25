package com.ferullogaming.countercraft.game.tdm;

import com.ferullogaming.countercraft.commands.CommandGameManage;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.EnumMapTime;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameCommandHandler;
import com.ferullogaming.countercraft.game.Loadout;
import com.ferullogaming.countercraft.game.Team;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;

public class TDMCommands extends IGameCommandHandler {
   public TDMCommands(IGame par1) {
      super(par1);
   }

   public void onEditCommandCalled(EntityPlayer par1, String[] args) {
      TeamDeathMatch tdmGame = (TeamDeathMatch)this.getGame();
      TDMPlayerHandler playerHandler = (TDMPlayerHandler)this.getGame().getPlayerEventHandler();
      if (args.length >= 1) {
         String var1 = args[0];
         if (var1.equals("lobby")) {
            playerHandler.lobby = new BlockLocation(par1);
            CommandGameManage.sendChat(par1, "Lobby location placed.");
            return;
         }

         if (var1.equals("rsadd")) {
            playerHandler.randomSpawns.add(new BlockLocation(par1));
            CommandGameManage.sendChat(par1, "Added Random Spawn");
            return;
         }

         if (var1.equals("rsclear")) {
            playerHandler.randomSpawns.clear();
            CommandGameManage.sendChat(par1, "Cleared Random Spawns");
            return;
         }

         String var2;
         if (var1.equals("map") && args.length >= 2) {
            var2 = args[1];
            CommandGameManage.sendChat(par1, "Map type set to [" + var2 + "].");
            tdmGame.setGameMapType(var2);
            return;
         }

         if (var1.equals("time") && args.length >= 2) {
            var2 = args[1];
            EnumMapTime newTime = EnumMapTime.DAY;
            String var19 = var2.toLowerCase();
            byte var9 = -1;
            switch(var19.hashCode()) {
            case 99228:
               if (var19.equals("day")) {
                  var9 = 0;
               }
               break;
            case 104817688:
               if (var19.equals("night")) {
                  var9 = 1;
               }
            }

            switch(var9) {
            case 0:
               newTime = EnumMapTime.DAY;
               break;
            case 1:
               newTime = EnumMapTime.NIGHT;
               break;
            default:
               newTime = EnumMapTime.DAY;
            }

            CommandGameManage.sendChat(par1, "Time type set to [" + newTime.toString() + "].");
            tdmGame.setMapTime(newTime);
            return;
         }

         int var21;
         if (var1.equals("maxscore") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               CommandGameManage.sendChat(par1, "Max Score set to [" + var21 + "].");
               tdmGame.maxScore = var21;
               return;
            } catch (Exception var12) {
               ;
            }
         }

         if (var1.equals("teammaxplayers") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               CommandGameManage.sendChat(par1, "Max Team Players set to [" + var21 + "].");

               Team team;
               for(Iterator i$ = playerHandler.getTeams().iterator(); i$.hasNext(); team.teamMaxPlayers = var21) {
                  team = (Team)i$.next();
               }

               return;
            } catch (Exception var11) {
               ;
            }
         }

         if (var1.equals("maxtime") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               if (var21 < 1) {
                  var21 = 1;
               }

               if (var21 > 30) {
                  var21 = 30;
               }

               CommandGameManage.sendChat(par1, "Max Time set to [" + var21 + "] minute(s).");
               tdmGame.maxTime = var21;
               return;
            } catch (Exception var10) {
               ;
            }
         }

         Team team = playerHandler.getTeam(var1);
         if (team != null && args.length >= 2) {
            String var211 = args[1];
            if (var211.equals("spawnadd")) {
               BlockLocation location = new BlockLocation(par1);
               team.teamSpawns.add(location);
               CommandGameManage.sendChat(par1, team.teamName + " team spawn added.");
               return;
            }

            if (var211.equals("spawnclear")) {
               team.teamSpawns.clear();
               CommandGameManage.sendChat(par1, team.teamName + " team's spawns cleared.");
               return;
            }

            if (var211.equals("loadout")) {
               Loadout load = new Loadout();
               load.setInventory(par1);
               team.teamDefaultLoadout = load.copy();
               CommandGameManage.sendChat(par1, team.teamName + " team's default loadout updated.");
               return;
            }
         }
      }

      CommandGameManage.sendChat(par1, "Invalid Usage.");
   }

   public void getCommandInformation(ArrayList par1) {
      par1.add("lobby (Place Lobby)");
      par1.add("map <ID> (Set the Game's map type)");
      par1.add("<Red|Blue> <spawnadd|spawnclear> (Add Team Spawn/Clear All)");
      par1.add("<Red|Blue> loadout (Update Default Loadout)");
      par1.add("maxscore <INT> (0 = Till time runs out)");
      par1.add("maxtime <INT> (<1-30> in minutes)");
      par1.add("rsadd (Add Random Spawn)");
      par1.add("rsclear (Clear Random Spawns)");
   }
}
