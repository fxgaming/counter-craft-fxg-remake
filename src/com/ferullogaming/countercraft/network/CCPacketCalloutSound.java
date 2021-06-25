package com.ferullogaming.countercraft.network;

import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class CCPacketCalloutSound extends CCPacket {
   public static Packet buildPacket(String par1, String par2, String par3) {
      Packet250CustomPayload packet = new Packet250CustomPayload();
      packet.channel = "ccNetworking";
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream data = new DataOutputStream(bytes);

      try {
         data.write(NetworkManager.getIDFromPacket(CCPacketCalloutSound.class));
         data.writeUTF(par1);
         data.writeUTF(par2);
         data.writeUTF(par3);
         packet.data = bytes.toByteArray();
         packet.length = packet.data.length;
         data.close();
         bytes.close();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return packet;
   }

   @SideOnly(Side.CLIENT)
   public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
      try {
         String team = stream.readUTF();
         String sound = stream.readUTF();
         String name = stream.readUTF();
         String myTeam = GameManager.instance().getPlayerGame(player).getPlayerEventHandler().getPlayerTeam(player).teamName;
         PlayerDataCloud data1 = PlayerDataHandler.getPlayerCloudData(name);
         if (team.equals(myTeam)) {
            if (sound.equals("smoke")) {
               player.playSound("countercraft:callout." + team.toLowerCase() + ".grenadesmoke", 1.0F, 1.0F);
               player.addChatMessage(data1.getUsernameFormatted() + "" + GameHelper.chatSeperator() + "Throwing a smoke");
            } else if (sound.equals("flashbang")) {
               player.playSound("countercraft:callout." + team.toLowerCase() + ".grenadeflashbang", 1.0F, 1.0F);
               player.addChatMessage(data1.getUsernameFormatted() + "" + GameHelper.chatSeperator() + "Throwing a flashbang");
            } else if (sound.equals("hegrenade")) {
               player.playSound("countercraft:callout." + team.toLowerCase() + ".grenadehegrenade", 1.0F, 1.0F);
               player.addChatMessage(data1.getUsernameFormatted() + "" + GameHelper.chatSeperator() + "Throwing a grenade");
            } else if (sound.equals("firebomb")) {
               player.playSound("countercraft:callout." + team.toLowerCase() + ".grenadefirebomb", 1.0F, 1.0F);
               player.addChatMessage(data1.getUsernameFormatted() + "" + GameHelper.chatSeperator() + "Throwing a firebomb");
            } else if (sound.equals("decoy")) {
               player.playSound("countercraft:callout." + team.toLowerCase() + ".grenadedecoy", 1.0F, 1.0F);
               player.addChatMessage(data1.getUsernameFormatted() + "" + GameHelper.chatSeperator() + "Throwing a decoy");
            }
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }
}
