package com.ferullogaming.countercraft.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet254ServerPing;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class Instrumentarium {
	public static int USERS_ONLINE = 0;
	public static void setOnlinePlayersValue() {
		ServerData a = null;
		Integer b = 0;
		for (String c : References.ALL_SERVERS) {
			a = new ServerData("null", c);
			b += Integer.valueOf(poll(a).split("/")[0]);
		}
		USERS_ONLINE = b;
	}
	
    private static String poll(ServerData a) {
        ServerAddress b = ServerAddress.func_78860_a(a.serverIP);
        Socket c = null;
        DataInputStream d = null;
        DataOutputStream e = null;
        try {
            c = new Socket();
            c.setSoTimeout(3000);
            c.setTcpNoDelay(true);
            c.setTrafficClass(18);
            c.connect(new InetSocketAddress(b.getIP(), b.getPort()), 3000);
            d = new DataInputStream(c.getInputStream());
            e = new DataOutputStream(c.getOutputStream());
            Packet254ServerPing f = new Packet254ServerPing(78, b.getIP(), b.getPort());
            e.writeByte(f.getPacketId());
            f.writePacketData(e);
            if (d.read() != 255) return "0000/";
            String g = Packet.readString(d, 256);
            char[] h = g.toCharArray();
            for (int i = 0; i < h.length; ++i) if(h[i]!=167&&h[i]!=0&&ChatAllowedCharacters.allowedCharacters.indexOf(h[i])<0)h[i]=63;
            g = new String(h);
            String[] i;
            if (g.startsWith("\u00a7") && g.length() > 1) {
                i = g.substring(1).split("\u0000");
                if (MathHelper.parseIntWithDefault(i[0], 0) == 1) return "" + MathHelper.parseIntWithDefault(i[4], 0) + "/" + MathHelper.parseIntWithDefault(i[5], 0);
                else return "0000/";
            }
            else return "0000/";
        } catch (Exception j) {}
        finally{try{if(d!=null)d.close();}catch(Throwable k){;}try{if(e!=null){e.close();}}catch(Throwable k){;}try{if(c!=null){c.close();}}catch(Throwable k){;}}
        return "0000/";
    }
}
