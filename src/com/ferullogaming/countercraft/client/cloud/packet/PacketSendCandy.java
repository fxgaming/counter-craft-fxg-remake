package com.ferullogaming.countercraft.client.cloud.packet;

import com.f3rullo14.cloud.G_ITcpConnectionHandler;
import com.f3rullo14.cloud.G_TcpPacketCustomPayload;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.bind.DatatypeConverter;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class PacketSendCandy extends G_TcpPacketCustomPayload {
   private static IntBuffer intBuf;
   public String candy;
   public String candyName;
   public boolean lastCandy;

   public PacketSendCandy(String partialString, String candyName, boolean lastCandy) {
      this.candy = partialString;
      this.candyName = candyName;
      this.lastCandy = lastCandy;
   }

   public PacketSendCandy() {
   }

   static BufferedImage getScreenshot() {
      Minecraft mc = Minecraft.getMinecraft();
      int width = mc.displayWidth;
      int height = mc.displayHeight;
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);
      int intCount = width * height;
      if (intBuf == null || intBuf.capacity() < intCount) {
         intBuf = BufferUtils.createIntBuffer(intCount);
      }

      intBuf.clear();
      GL11.glReadPixels(0, 0, width, height, 32993, 33639, intBuf);
      GL30.glBindFramebuffer(36160, 0);
      BufferedImage img = new BufferedImage(width, height, 1);
      int[] imgData = ((DataBufferInt)img.getWritableTile(0, 0).getDataBuffer()).getData();
      int row = height;

      while(row > 0) {
         --row;
         intBuf.get(imgData, row * width, width);
      }

      return img;
   }

   public void writePacketToStream(DataOutputStream par1) throws IOException {
      writeString(par1, this.candy);
      writeString(par1, this.candyName);
      par1.writeBoolean(this.lastCandy);
   }

   public void readPacketFromStream(DataInputStream par1) throws IOException {
   }

   public void processPacketOnMainThread(G_ITcpConnectionHandler par1) throws IOException {
      try {
         BufferedImage capture = getScreenshot();
         String IMG_PATH = CounterCraft.instance.folderLocation + Minecraft.getMinecraft().getSession().getUsername() + "_" + System.currentTimeMillis() + ".png";
         File file = new File(IMG_PATH);
         ImageIO.write(capture, "png", file);
         File compressedFile = this.compress(file);
         byte[] bytes = Files.readAllBytes(compressedFile.toPath());
         Format formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
         String s = formatter.format(new Date(System.currentTimeMillis()));
         String base64 = DatatypeConverter.printBase64Binary(bytes);
         int index = 0;
         String temp = "";
         int overallIndex = 0;
         char[] arr$ = base64.toCharArray();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            char c = arr$[i$];
            if (index == 512) {
               ClientCloudManager.sendPacket(new PacketSendCandy(temp, s, overallIndex == base64.length() - 1));
               temp = "";
               index = 0;
            }

            temp = temp + c;
            ++index;
         }

         if (temp.length() > 0) {
            ClientCloudManager.sendPacket(new PacketSendCandy(temp, s, true));
         }

         int var18 = overallIndex + 1;
         file.delete();
         compressedFile.delete();
      } catch (Exception var17) {
         var17.printStackTrace();
      }

   }

   private File compress(File input) {
      try {
         BufferedImage image = ImageIO.read(input);
         File compressedImageFile = new File(input.getParentFile(), "compressed.jpg");
         OutputStream os = new FileOutputStream(compressedImageFile);
         Iterator writers = ImageIO.getImageWritersByFormatName("png");
         ImageWriter writer = (ImageWriter)writers.next();
         ImageOutputStream ios = ImageIO.createImageOutputStream(os);
         writer.setOutput(ios);
         ImageWriteParam param = writer.getDefaultWriteParam();
         if (param.canWriteCompressed()) {
            param.setCompressionMode(2);
            param.setCompressionQuality(0.5F);
         }

         writer.write((IIOMetadata)null, new IIOImage(image, (List)null, (IIOMetadata)null), param);
         os.close();
         ios.close();
         writer.dispose();
         return compressedImageFile;
      } catch (IOException var9) {
         var9.printStackTrace();
         return null;
      }
   }
}
