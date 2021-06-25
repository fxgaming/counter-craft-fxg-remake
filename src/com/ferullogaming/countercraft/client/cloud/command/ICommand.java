package com.ferullogaming.countercraft.client.cloud.command;

public abstract class ICommand {
   public abstract String getCommand();

   public abstract String getUsage();

   public abstract boolean onCommand(ICommandSender var1, String[] var2);

   public String getPermission() {
      return null;
   }
}
