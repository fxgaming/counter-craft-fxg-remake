package com.ferullogaming.countercraft.client.cloud.command;

import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.packet.PacketConsoleCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LocalCommandHandler {
   private HashMap commandMapping = new HashMap();

   public void handleCommandPresend(ICommandSender par1, String par2) {
      String[] var1 = par2.replace("/", "").trim().split(" ");
      if (this.commandMapping.containsKey(var1[0])) {
         try {
            boolean flag = false;
            ICommand command = (ICommand)((ICommand)this.commandMapping.get(var1[0])).getClass().newInstance();
            if (par1 instanceof PlayerDataCloud && (command.getPermission() == null || ((PlayerDataCloud)par1).hasPermission(command.getPermission()))) {
               flag = true;
            }

            if (flag) {
               if (!command.onCommand(par1, var1.length > 1 ? (String[])Arrays.copyOfRange(var1, 1, var1.length) : new String[0])) {
                  par1.sendChat(command.getUsage());
               }
            } else {
               par1.sendChat("Invalid Permissions.");
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      } else if (ClientManager.instance().getCloudManager().getConnectionHandler().isConnected()) {
         ClientCloudManager.sendPacket(new PacketConsoleCommand(par2));
      } else {
         par1.sendChat("Cloud Connection not established.");
      }

   }

   public void registerCommand(ICommand par1) {
      this.commandMapping.put(par1.getCommand(), par1);
   }

   public ArrayList getCommands() {
      return new ArrayList(this.commandMapping.values());
   }
}
