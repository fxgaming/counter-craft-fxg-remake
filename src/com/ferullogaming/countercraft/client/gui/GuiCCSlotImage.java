package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollerSlot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class GuiCCSlotImage extends GuiFGScrollerSlot {
   public String imageURL;
   public ResourceLocation imageResourceLocation;
   public int width;
   public int height;

   public GuiCCSlotImage(String imageURL, int imageWidth, int imageHeight) {
      this.imageURL = imageURL;
      this.width = imageWidth;
      this.height = imageHeight;
   }

   public static BufferedImage downloadBanner(String url) {
      try {
         return ImageIO.read(new URL(url));
      } catch (IOException var2) {
         System.out.println("Ошибка чтения изображения: '" + url + "'");
         return null;
      }
   }

   protected void init() {
      super.init();
   }

   public boolean canSelect() {
      return false;
   }

   public void doRender(int mouseX, int mouseY) {
      if (this.imageResourceLocation != null) {
         CCRenderHelper.drawImageTransparent((double)(super.posX + 5), (double)super.posY, this.imageResourceLocation, (double)this.width, (double)this.height, 1.0D);
      } else {
         try {
            this.imageResourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(this.imageURL, new DynamicTexture(downloadBanner(this.imageURL)));
         } catch (NullPointerException var4) {
            var4.printStackTrace();
         }
      }

   }

   public void clicked(int mouseX, int mouseY) {
      super.clicked(mouseX, mouseY);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
   }

   protected int height() {
      return this.height;
   }
}
