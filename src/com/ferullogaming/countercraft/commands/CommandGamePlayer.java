package com.ferullogaming.countercraft.commands;

import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.Arrays;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class CommandGamePlayer extends CommandBase {
   private GameManager gameManager;

   public static void sendChat(EntityPlayer player, String par1) {
      player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "CC " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + par1));
   }

   public String getCommandName() {
      return "cc";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/cc <join|leave> <id> [args] [args] [args]";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      this.gameManager = GameManager.instance();
      if (icommandsender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)icommandsender;
         if (FMLCommonHandler.instance().getSide() == Side.SERVER && ServerManager.instance().isMatchMakingAccepted) {
            sendChat(player, "Запрещено во время игры.");
            return;
         }

         if (astring.length >= 1) {
            String var1 = astring[0];
            if (var1.equals("join")) {
               if (astring.length >= 2) {
                  String var2 = astring[1];
                  if (this.gameManager.getGameFromName(var2) != null) {
                     IGame game = this.gameManager.getGameFromName(var2);
                     if (GameManager.instance().isPlayerInGame(player)) {
                        sendChat(player, "Вы уже в игре!");
                        return;
                     }

                     if (game.isEditing) {
                        sendChat(player, "Игра которую вы выбрали изменяется, приходите позже.");
                        return;
                     }

                     String[] args1 = (String[])Arrays.copyOfRange(astring, 2, astring.length);
                     if (!this.gameManager.assignPlayerToGame(player, game.getGameName(), args1)) {
                        sendChat(player, "Невозможно присоеденится к игре.");
                        return;
                     }

                     System.out.println("Игрок '" + player.username + "'присоеденился к игре '" + var2 + "'");
                     return;
                  }

                  sendChat(player, "Не существует игры с таким ID!");
                  return;
               }

               sendChat(player, "Неправильное использование! Попробуйте /cc <join/leave> <ID> [args]");
               return;
            }

            if (var1.equals("leave")) {
               IGame game = this.gameManager.getPlayerGame(player);
               if (game != null) {
                  game.getPlayerEventHandler().removePlayer(player);
                  return;
               }

               sendChat(player, "You are not in a game!");
               return;
            }

            sendChat(player, "Неправильное использование! /cc <join|leave> <ID>");
            return;
         }
      }

   }

   public int compareTo(Object o) {
      return 0;
   }
}
