package com.f3rullo14.cloud;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class G_TcpPacketCustomPayload extends G_TcpPacket {
   public int length;
   public byte[] data;

   public void writePacketData(DataOutput par1Out) throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      DataOutputStream outputData = new DataOutputStream(bytes);
      this.writePacketToStream(outputData);
      this.data = bytes.toByteArray();
      this.length = this.data.length;
      outputData.close();
      bytes.close();
      par1Out.writeShort(this.length);
      if (this.data != null) {
         par1Out.write(this.data);
      }

   }

   public void readPacketData(DataInput par1In) throws IOException {
      this.length = par1In.readShort();
      if (this.length > 0) {
         this.data = new byte[this.length];
         par1In.readFully(this.data);
      }

      if (this.data != null) {
         DataInputStream stream = new DataInputStream(new ByteArrayInputStream(this.data));
         this.readPacketFromStream(stream);
      }

   }

   public void processPacket(G_ITcpConnectionHandler par1) {
      try {
         if (par1.processesUnexpectedPacket(this)) {
            this.processPacketOnMainThread(par1);
         }
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public int getPacketSize() {
      return this.data == null ? 0 : this.data.length + 2;
   }

   public abstract void writePacketToStream(DataOutputStream var1) throws IOException;

   public abstract void readPacketFromStream(DataInputStream var1) throws IOException;

   public abstract void processPacketOnMainThread(G_ITcpConnectionHandler var1) throws IOException;
}
