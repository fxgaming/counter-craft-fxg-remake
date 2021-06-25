package com.ferullogaming.countercraft.client.cloud.packet.server;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.ServerManager;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketServerGameStatus extends G_TcpPacketCustomPayload {
   public void writePacketToStream(DataOutputStream par1) throws IOException {
      GameManager gm = GameManager.instance();
      par1.writeBoolean(gm.matchMakingGame != null);
      if (gm.matchMakingGame != null) {
         IGame game = gm.matchMakingGame;
         writeString(par1, game.getGameType());
         writeString(par1, ServerManager.instance().serverRegion);
         writeString(par1, game.getGameMapType());
         par1.writeInt(game.getPlayerEventHandler().getMaxPlayers() - game.getPlayerEventHandler().getPlayers().size());
         par1.writeBoolean(game.getPlayerEventHandler().acceptsPlayersFromMM());
         int red = 0;
         int blue = 0;
         if (game.getGameType().equals("cbd")) {
            red = game.getPlayerEventHandler().getTeam("Red").getPlayers().size();
            blue = game.getPlayerEventHandler().getTeam("Blue").getPlayers().size();
         }

         par1.writeInt(red);
         par1.writeInt(blue);
      }

   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
   }
}
