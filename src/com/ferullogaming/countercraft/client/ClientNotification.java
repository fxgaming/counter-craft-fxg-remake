package com.ferullogaming.countercraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ClientNotification {
   public ResourceLocation notificationTexture = new ResourceLocation("countercraft:textures/gui/notification.png");
   public ResourceLocation notificationTextureAC = new ResourceLocation("countercraft:textures/gui/notification_ac.png");
   public String[] message;
   public String subMessage;
   public double displayTime = 100.0D;
   private double posY = -35.0D;
   private double motionY = 1.75D;
   private double alpha = 0.0D;
   private int type = 0;

   public ClientNotification(String par1) {
      this.message = this.trimString(par1);
      this.subMessage = "CS";
   }

   public ClientNotification(String par1, String par2) {
      this.message = new String[2];
      this.message[0] = par1;
      this.message[1] = par2;
      this.subMessage = "CS";
   }

   public static void createNotification(String par1) {
      ClientNotification not = new ClientNotification(par1);
      ClientManager.instance().getTickHandler().clientNotificationList.add(not);
   }

   public ClientNotification setSubMessage(String par1) {
      this.subMessage = par1;
      return this;
   }

   public ClientNotification setType(int par1) {
      this.type = par1;
      return this;
   }

   public void onUpdate() {
      if (this.posY < 0.0D) {
         this.posY += this.motionY;
         this.alpha += 0.045D;
      }

      if (this.posY > 0.0D) {
         this.posY = 0.0D;
      }

      if (this.displayTime > 0.0D) {
         --this.displayTime;
      }

   }

   public void doRender(Minecraft par1Minecraft) {
      int boxWidth = 1;
      CCRenderHelper.drawImageTransparent(3.0D, (double)((int)this.posY + 3), this.type == 0 ? this.notificationTexture : this.notificationTextureAC, 160.0D, 41.0D, this.alpha);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + this.message[0].trim(), 7, (int)this.posY + 14);
      CCRenderHelper.renderText(EnumChatFormatting.WHITE + this.message[1].trim(), 7, (int)this.posY + 24);
      CCRenderHelper.renderTextScaled((this.type == 0 ? EnumChatFormatting.YELLOW : EnumChatFormatting.RESET) + this.subMessage, 7, (int)this.posY + 35, 0.65D);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private String[] trimString(String par1) {
      int var1 = Minecraft.getMinecraft().fontRenderer.getStringWidth(par1);
      String[] returnMessage = new String[]{"", ""};
      int var3 = 0;
      int max = 160;
      if (var1 > max) {
         String[] var2 = par1.split(" ");

         for(int i = 0; i < var2.length; ++i) {
            String var4 = returnMessage[var3] + " " + var2[i];
            if (Minecraft.getMinecraft().fontRenderer.getStringWidth(var4) < max) {
               returnMessage[var3] = returnMessage[var3] + " " + var2[i];
            } else if (var3 != 1) {
               var3 = 1;
               returnMessage[var3] = returnMessage[var3] + " " + var2[i];
            }
         }
      } else {
         returnMessage[0] = par1;
      }

      return returnMessage;
   }
}
