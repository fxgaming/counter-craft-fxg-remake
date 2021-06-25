package com.ferullogaming.countercraft.commands;

import com.ferullogaming.countercraft.game.GameManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class CommandScreenshot extends CommandBase {
   private GameManager gameManager;

   public static void sendChat(EntityPlayer player, String par1) {
      player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "CC " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + par1));
   }

   public String getCommandName() {
      return "screenshot";
   }

   public String getCommandUsage(ICommandSender icommandsender) {
      return "/screenshot <username>";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public void processCommand(ICommandSender icommandsender, String[] astring) {
      if (icommandsender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)icommandsender;
         if (astring.length >= 1) {
            String givenUsername = astring[0];
            if (MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(givenUsername) != null) {
               sendChat(player, "Fetching Screenshot from " + givenUsername + "...");
            } else {
               sendChat(player, "The given username doesn't exist! Try again!");
            }

         } else {
            sendChat(player, "Invalid Usage! /screenshot <username>");
         }
      }
   }

   public int compareTo(Object o) {
      return 0;
   }
}
