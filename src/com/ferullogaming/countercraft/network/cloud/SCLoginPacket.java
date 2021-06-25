package com.ferullogaming.countercraft.network.cloud;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.acloud.AllInfo;
import com.ferullogaming.countercraft.acloud.ControlProcessor;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.game.GameNotification;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class SCLoginPacket extends CPacket {
	public static Packet buildPacket(EntityPlayer e) {
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "clap";
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);

		try {
			data.write(CloudNetwork.getIDFromPacket(SCLoginPacket.class));
			data.writeUTF(e.getEntityName());
			ControlProcessor cpu = new ControlProcessor();
			Object[] a = (Object[])cpu.unit_13(e.username.toLowerCase())[0];
			Object[][] b = (Object[][])cpu.unit_13(e.username.toLowerCase())[1];
			data.writeInt(a.length);
			for (int i = 0; i != a.length; i++) {
				data.writeUTF((String)a[i]);
			}
			data.writeInt(b.length);
			data.writeInt(b[0].length);
			for (int i = 0; i != b.length; i++) {
				for (int j = 0; j != b[i].length; j++) {
					data.writeUTF((String)b[i][j]);
				}
			}
			packet.data = bytes.toByteArray();
			packet.length = packet.data.length;
			data.close();
			bytes.close();
		} catch (Exception var5) {
			var5.printStackTrace();
		}
		return packet;
   }	

	public void execute(DataInputStream stream, EntityPlayer player, Object[] extradata, Side side) {
		try {
			int aLen = stream.readInt();
			Object[] a = new Object[aLen];
			for (int i = 0; i != aLen; i++) {
				a[i] = stream.readUTF();
			}
			int bLen0 = stream.readInt();
			int bLen1 = stream.readInt();
			Object[][] b = new Object[bLen0][bLen1];
			for (int i = 0; i != bLen0; i++) {
				for (int j = 0; j != bLen1; j++) {
					b[i][j] = stream.readUTF();
				}
			}
			if (side == Side.CLIENT) {
				AllInfo.playerdata = a;
				AllInfo.inventory = b;
				return;
			}
			AllInfo.udata.put(player.username.toLowerCase(), new Object[]{a, b});
		} catch (Exception var6) {
			var6.printStackTrace();
		}
	}
}
