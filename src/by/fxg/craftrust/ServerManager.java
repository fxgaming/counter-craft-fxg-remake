package by.fxg.craftrust;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class ServerManager {
	   public static void broadcastMessage(String message) {
		      String[] var1 = MinecraftServer.getServer().getAllUsernames();
		      int var2 = var1.length;

		      for(int var3 = 0; var3 < var2; ++var3) {
		         String name = var1[var3];
		         getPlayerExact(name).sendChatToPlayer(ChatMessageComponent.createFromText(message));
		      }

		   }

		   public static EntityPlayerMP getPlayerExact(String username) {
		      return username == null ? null : MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		   }
}
