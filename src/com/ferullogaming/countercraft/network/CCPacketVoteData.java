package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.Vote;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketVoteData extends CCPacket {
   public static Packet buildPacket(String givenTeamVoting, int givenYes, int givenNo, int voteType, String voteData, long givenVoteTime) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketVoteData.class));
         data.writeUTF(givenTeamVoting);
         data.writeInt(givenYes);
         data.writeInt(givenNo);
         data.writeInt(voteType);
         data.writeUTF(voteData);
         data.writeLong(givenVoteTime);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String teamVoting = stream.readUTF();
         int givenYes = stream.readInt();
         int givenNo = stream.readInt();
         Vote.VoteType voteType = Vote.VoteType.getById(stream.readInt());
         String voteData = stream.readUTF();
         long voteTime = stream.readLong();
         if (GameManager.instance().isPlayerInGame(player)) {
            IGameClient gameClient = GameManager.instance().getPlayerGame(player).getClientSide();
            gameClient.setCurrentVote(new Vote(teamVoting, givenYes, givenNo, voteType, voteData));
            gameClient.getCurrentVote().voteTime = voteTime;
         }
      } catch (IOException var13) {
      }
   }
}
