package com.ferullogaming.countercraft.commands;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class CommandGameManage extends CommandBase {
   private GameManager gameManager;

   public static void sendChat(EntityPlayer player, String par1) {
      player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "CC " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + par1));
   }

   public String getCommandName() {
      return "game";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/game <create|remove|edit|info|help> <id> [args] [args] [args]";
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      this.gameManager = GameManager.instance();
      if (icommandsender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)icommandsender;
         if (!player.worldObj.isRemote) {
            String var1;
            String var2;
            IGame tempGame;
            ArrayList info;
            IGame game;
            int i;
            if (astring.length >= 2) {
               var1 = astring[0];
               var2 = astring[1];
               if (var1.equals("create")) {
                  if (this.gameManager.getGameFromName(var2) == null) {
                     if (astring.length >= 3) {
                        String var3 = astring[2];
                        game = GameManager.instance().getGameObject(var3);
                        if (game != null) {
                           game.setGameName(var2);
                           GameManager.instance().gamesHosted.put(var2, game);
                           sendChat(player, "Game '" + var2 + "' created with game type '" + var3 + "'");
                           return;
                        }
                     }

                     sendChat(player, "Invalid Usage. /game create <ID> <type> (EX: /game create TDM1 tdm)");
                     return;
                  }

                  sendChat(player, "Game '" + var2 + "' already exists!");
                  return;
               }

               if (var1.equals("remove")) {
                  if (this.gameManager.getGameFromName(var2) != null) {
                     sendChat(player, "Game '" + var2 + "' was removed.");
                     GameManager.instance().gamesHosted.remove(var2);
                     return;
                  }
               } else if (var1.equals("edit")) {
                  tempGame = this.gameManager.getGameFromName(var2);
                  if (tempGame != null && astring.length >= 3) {
                     String var3 = astring[2];
                     String[] args1 = (String[])Arrays.copyOfRange(astring, 2, astring.length);
                     tempGame.getCommandHandler().onEditCommandCalled(player, args1);
                     return;
                  }
               } else if (var1.equals("info")) {
                  tempGame = this.gameManager.getGameFromName(var2);
                  if (tempGame != null) {
                     sendChat(player, EnumChatFormatting.BOLD + "=================================");
                     sendChat(player, "");
                     info = new ArrayList();
                     tempGame.getInformation(info);

                     for(i = 0; i < info.size(); ++i) {
                        sendChat(player, (String)info.get(i));
                     }

                     info.clear();
                     sendChat(player, "");
                     sendChat(player, EnumChatFormatting.BOLD + "=================================");
                     return;
                  }
               } else if (var1.equals("refresh")) {
                  tempGame = this.gameManager.getGameFromName(var2);
                  if (tempGame != null) {
                     tempGame.restartGame();
                     sendChat(player, "Restarted and Refreshed game '" + tempGame.getGameName() + "'.");
                     return;
                  }
               }
            }

            if (astring.length >= 1) {
               var1 = astring[0];
               if (var1.equals("list")) {
                  sendChat(player, EnumChatFormatting.BOLD + "=================================");
                  sendChat(player, "");
                  sendChat(player, "Games/Matchs: " + this.gameManager.gamesHosted.size());
                  ArrayList games = new ArrayList(this.gameManager.gamesHosted.values());

                  for(int i1 = 0; i1 < games.size(); ++i1) {
                     game = (IGame)games.get(i1);
                     sendChat(player, i1 + 1 + ": " + game.getGameName());
                  }

                  sendChat(player, "");
                  sendChat(player, EnumChatFormatting.BOLD + "=================================");
                  return;
               }

               if (var1.equals("help")) {
                  if (astring.length >= 2) {
                     var2 = astring[1];
                     tempGame = GameManager.instance().getGameObject(var2);
                     if (tempGame != null) {
                        sendChat(player, EnumChatFormatting.BOLD + "=================================");
                        sendChat(player, EnumChatFormatting.BOLD + "<> = required, [] = optional, () = note");
                        sendChat(player, "");
                        info = new ArrayList();
                        tempGame.getCommandHandler().getCommandInformation(info);

                        for(i = 0; i < info.size(); ++i) {
                           sendChat(player, "/game edit <ID> " + (String)info.get(i));
                        }

                        info.clear();
                        tempGame = null;
                        sendChat(player, "");
                        sendChat(player, EnumChatFormatting.BOLD + "=================================");
                        return;
                     }
                  }

                  sendChat(player, EnumChatFormatting.BOLD + "=================================");
                  sendChat(player, EnumChatFormatting.BOLD + "<> = required, [] = optional, () = note");
                  sendChat(player, "");
                  sendChat(player, "/game create <ID> <TYPE>");
                  sendChat(player, "/game remove <ID>");
                  sendChat(player, "/game edit <ID> toggle (Toggles Editing Mode)");
                  sendChat(player, "/game edit <ID> lobby (Sets Lobby Location at Player)");
                  sendChat(player, "/game edit <ID> map <NAME>");
                  sendChat(player, "/game info <ID>");
                  sendChat(player, "/game save-all (Saves all current game data)");
                  sendChat(player, "/game help [TYPE]");
                  sendChat(player, "");
                  sendChat(player, EnumChatFormatting.BOLD + "=================================");
                  return;
               }

               if (var1.equals("save-all")) {
                  GameManager.instance().saveGameData();
                  sendChat(player, "All Game/Match data saved to the folder!");
                  return;
               }
            }

            sendChat(player, "Invalid usage! /game <create|remove|edit|list|refresh> <id> [args] [args] [args]");
         }
      }
   }

   public int compareTo(Object o) {
      return 0;
   }
}
