package com.ferullogaming.countercraft.game.cas;

import com.ferullogaming.countercraft.commands.CommandGameManage;
import com.ferullogaming.countercraft.game.BlockLocation;
import com.ferullogaming.countercraft.game.BombPoint;
import com.ferullogaming.countercraft.game.EnumMapTime;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameCommandHandler;
import com.ferullogaming.countercraft.game.Loadout;
import com.ferullogaming.countercraft.game.Team;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;

public class CASCommands extends IGameCommandHandler {
   public CASCommands(IGame par1) {
      super(par1);
   }

   public void onEditCommandCalled(EntityPlayer par1, String[] args) {
      CasualBombDefusal compGame = (CasualBombDefusal)this.getGame();
      CASPlayerHandler playerHandler = (CASPlayerHandler)this.getGame().getPlayerEventHandler();
      if (args.length >= 1) {
         String var1 = args[0];
         if (var1.equals("lobby")) {
            playerHandler.lobby = new BlockLocation(par1);
            CommandGameManage.sendChat(par1, "Lobby location placed.");
            return;
         }

         String var2;
         if (var1.equals("bombsite") && args.length >= 2) {
            var2 = args[1];
            if (var2.equals("add") && args.length >= 3) {
               var2 = args[2];
               BombPoint location = new BombPoint(par1, var2);
               compGame.bombPoints.add(location);
               CommandGameManage.sendChat(par1, "Bomb Site " + var2.toUpperCase() + " Added!");
               return;
            }

            if (var2.equals("clear")) {
               compGame.bombPoints.clear();
               CommandGameManage.sendChat(par1, "Cleared all Bomb Points");
               return;
            }
         }

         if (var1.equals("map") && args.length >= 2) {
            var2 = args[1];
            CommandGameManage.sendChat(par1, "Map type set to [" + var2 + "].");
            compGame.setGameMapType(var2);
            return;
         }

         if (var1.equals("time") && args.length >= 2) {
            var2 = args[1];
            EnumMapTime newTime = EnumMapTime.DAY;
            String var20 = var2.toLowerCase();
            byte var9 = -1;
            switch(var20.hashCode()) {
            case 99228:
               if (var20.equals("day")) {
                  var9 = 0;
               }
               break;
            case 104817688:
               if (var20.equals("night")) {
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
            compGame.setMapTime(newTime);
            return;
         }

         int var21;
         if (var1.equals("teammaxplayers") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               CommandGameManage.sendChat(par1, "Max Team Players set to [" + var21 + "].");

               Team team;
               for(Iterator i$ = playerHandler.getTeams().iterator(); i$.hasNext(); team.teamMaxPlayers = var21) {
                  team = (Team)i$.next();
               }

               return;
            } catch (Exception var13) {
               ;
            }
         }

         if (var1.equals("maxwins") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               CommandGameManage.sendChat(par1, "Max Round Wins set to [" + var21 + "].");
               compGame.maxRoundWins = var21;
               return;
            } catch (Exception var12) {
               ;
            }
         }

         if (var1.equals("maxrounds") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               CommandGameManage.sendChat(par1, "Max Rounds set to [" + var21 + "]");
               compGame.maxRounds = var21;
               return;
            } catch (Exception var11) {
               ;
            }
         }

         if (var1.equals("maxroundtime") && args.length >= 2) {
            try {
               var21 = Integer.parseInt(args[1]);
               CommandGameManage.sendChat(par1, "Max Round Time set to [" + var21 + "] minute(s).");
               compGame.maxRoundTime = var21;
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
      par1.add("bombsite <add|clear> <LETTER> (Add a Bomb-Site Location. Requires a 'title')");
      par1.add("<Red|Blue> <spawnadd|spawnclear> (Add Team Spawn/Clear All)");
      par1.add("<Red|Blue> loadout (Update Default Loadout)");
      par1.add("maxwins <INT> (Number of round wins to win the match)");
      par1.add("maxrounds <INT> (Number of total rounds)");
      par1.add("maxroundtime <INT> (<Time period for each round MINUTES)");
   }
}
