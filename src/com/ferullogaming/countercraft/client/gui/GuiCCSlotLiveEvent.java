package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollerSlot;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiCCSlotLiveEvent extends GuiFGScrollerSlot {
   public static final ResourceLocation liveLight_On = new ResourceLocation("countercraft:textures/gui/live_on.png");
   public static final ResourceLocation backgroundImage = new ResourceLocation("countercraft:textures/gui/blockimage_live.png");
   public String imageURL;
   public String userName;
   public String streamDescription;
   public int width;
   public int height;
   private int toolTipY;

   public GuiCCSlotLiveEvent(String getUsername, String getDescription, String streamUrl, int imageWidth, int imageHeight) {
      this.userName = getUsername;
      this.streamDescription = getDescription;
      this.imageURL = streamUrl;
      this.width = imageWidth;
      this.height = imageHeight;
   }

   public static BufferedImage downloadBanner(String url) {
      try {
         return ImageIO.read(new URL(url));
      } catch (IOException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   protected void init() {
      super.init();
   }

   public boolean canSelect() {
      return true;
   }

   public void doRender(int mouseX, int mouseY) {
      String toolTip = "Нажмите для просмотра " + EnumChatFormatting.RED + "Стрима" + EnumChatFormatting.RESET + "!";
      int toolTipWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(toolTip);
      CCRenderHelper.drawRectWithShadow((double)(super.posX + 5), (double)super.posY, (double)this.width, (double)(this.height - 6), "0x000000", 0.3F);
      CCRenderHelper.drawImageTransparent((double)(super.posX + 5), (double)super.posY, backgroundImage, (double)this.width, (double)(this.height - 6), 0.6D);
      if (this.isHovered(mouseX, mouseY)) {
         CCRenderHelper.drawRectWithShadow((double)(super.posX + 5), (double)super.posY, (double)this.width, (double)(this.height - 6), "0xFFFFFF", 0.1F);
         CCRenderHelper.drawRectWithShadow2(mouseX, mouseY - 10, this.toolTipY, 10, Color.black, 100);
         if (this.toolTipY < toolTipWidth + 2) {
            int toolTipGap = toolTipWidth + 2 - this.toolTipY;
            if (toolTipGap >= 10) {
               this.toolTipY += 10;
            } else {
               ++this.toolTipY;
            }
         } else if (this.toolTipY > toolTipWidth + 2) {
            --this.toolTipY;
         }

         if (this.toolTipY >= toolTipWidth + 2) {
            CCRenderHelper.renderText(toolTip, mouseX + 1, mouseY - 9);
         }
      }

      CCRenderHelper.drawImage((double)(super.posX + 10), (double)(super.posY + 5), liveLight_On, 20.0D, 20.0D);
      CCRenderHelper.renderText(this.userName + " is " + EnumChatFormatting.RED + "LIVE" + EnumChatFormatting.RESET + "!", super.posX + 36, super.posY + 8, 16777215);
      CCRenderHelper.renderTextScaled(this.streamDescription, super.posX + 36, super.posY + 18, 0.6000000238418579D);
   }

   public void clicked(int mouseX, int mouseY) {
      super.clicked(mouseX, mouseY);
      GuiCCMenu.openURL(this.imageURL);
      Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
   }

   protected int height() {
      return this.height;
   }
}
