package com.ferullogaming.countercraft.client.minimap.forge;

import com.ferullogaming.countercraft.client.minimap.Minimap;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public class MinimapConnectionHandler implements IConnectionHandler {
   Minimap mw;

   public MinimapConnectionHandler(Minimap mw) {
      this.mw = mw;
   }

   public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
   }

   public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
      return null;
   }

   public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onConnectionOpened(server, port);
      }

   }

   public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onConnectionOpened();
      }

   }

   public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onClientLoggedIn(login);
      }

   }

   public void connectionClosed(INetworkManager manager) {
      Side side = FMLCommonHandler.instance().getEffectiveSide();
      if (side == Side.CLIENT) {
         this.mw.onConnectionClosed();
      }

   }
}
