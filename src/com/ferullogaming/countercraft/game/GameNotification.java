package com.ferullogaming.countercraft.game;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class GameNotification {
   public String message;
   public double displayTime = 0.0D;
   public double messageScale = 1.0D;
   public int messageColor = 0;
   public double posX = 0.0D;
   public double posY = 0.0D;
   public double motionX = 0.0D;
   public double motionY = 0.0D;
   public float boxOpacity = 1.0F;
   public int boxColor = 0;
   public boolean centerX = false;
   public boolean centerY = false;

   public GameNotification(String par1) {
      this.message = par1;
      this.centerX = true;
      this.displayTime = 120.0D;
   }

   public GameNotification(String par1, double par2) {
      this.message = par1;
      this.displayTime = par2;
   }

   public static GameNotification createNotificationFromStream(DataInputStream par1) throws IOException {
      String var1 = par1.readUTF();
      double var2 = par1.readDouble();
      GameNotification var3 = new GameNotification(var1, var2);
      var3.messageScale = par1.readDouble();
      var3.messageColor = par1.readInt();
      var3.posX = par1.readDouble();
      var3.posY = par1.readDouble();
      var3.motionX = par1.readDouble();
      var3.motionY = par1.readDouble();
      var3.boxOpacity = par1.readFloat();
      var3.boxColor = par1.readInt();
      var3.centerX = par1.readBoolean();
      var3.centerY = par1.readBoolean();
      return var3;
   }

   public void onUpdate() {
      this.posX += this.motionX;
      this.posY += this.motionY;
      if (this.displayTime > 0.0D) {
         --this.displayTime;
      }

   }

   @SideOnly(Side.CLIENT)
   public void doRender(Minecraft par1Minecraft) {
      ScaledResolution scaledresolution = new ScaledResolution(par1Minecraft.gameSettings, par1Minecraft.displayWidth, par1Minecraft.displayHeight);
      int mcwidth = scaledresolution.getScaledWidth();
      int mcheight = scaledresolution.getScaledHeight();
      this.posY = (double)(mcheight / 4 + 15);
      if (this.centerX) {
         this.posX += (double)(mcwidth / 2);
      }

      if (this.centerY) {
         this.posY += (double)(mcheight / 2);
      }

      int width = 1;
      int widthMargin = 1;
      CCRenderHelper.renderCenteredTextScaled(this.message, (int)this.posX, (int)this.posY + 5, 2.0D);
      if (this.centerX) {
         this.posX -= (double)(mcwidth / 2);
      }

      if (this.centerY) {
         this.posY -= (double)(mcheight / 2);
      }

   }

   public void writeToStream(DataOutputStream par1) throws IOException {
      par1.writeUTF(this.message);
      par1.writeDouble(this.displayTime);
      par1.writeDouble(this.messageScale);
      par1.writeInt(this.messageColor);
      par1.writeDouble(this.posX);
      par1.writeDouble(this.posY);
      par1.writeDouble(this.motionX);
      par1.writeDouble(this.motionY);
      par1.writeFloat(this.boxOpacity);
      par1.writeInt(this.boxColor);
      par1.writeBoolean(this.centerX);
      par1.writeBoolean(this.centerY);
   }
}
