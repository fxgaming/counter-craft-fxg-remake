package com.ferullogaming.countercraft.client.gui.workshop;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollerSlot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class GuiCCSlotCustomSkin extends GuiFGScrollerSlot {
   public File skinFile;
   public GuiCCMenuSkinViewer parentSkinViewer;
   public ResourceLocation skinTexture;

   public GuiCCSlotCustomSkin(File givenImageFile, GuiCCMenuSkinViewer givenSkinViewer) {
      this.skinFile = givenImageFile;
      this.parentSkinViewer = givenSkinViewer;
   }

   public static BufferedImage downloadBanner(String url) {
      try {
         return ImageIO.read(new URL(url));
      } catch (IOException var2) {
         System.out.println("Errors reading online image: '" + url + "'");
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
      if (this.isHovered(mouseX, mouseY)) {
         CCRenderHelper.drawRectWithShadow((double)(super.posX + 2), (double)(super.posY + 3), 85.0D, (double)(this.height() - 4), GuiCCMenu.menuTheme3, 1.0F);
      } else {
         CCRenderHelper.drawRectWithShadow((double)(super.posX + 2), (double)(super.posY + 3), 85.0D, (double)(this.height() - 4), GuiCCMenu.menuTheme, 1.0F);
      }

      if (this.skinTexture != null) {
         CCRenderHelper.drawImageTransparent((double)(super.posX + 3), (double)(super.posY + 3), this.skinTexture, 16.0D, 16.0D, 1.0D);
         CCRenderHelper.renderTextScaled(this.skinFile.getName(), super.posX + 23, super.posY + 10, 0.5D);
      } else if (this.skinFile != null) {
         BufferedImage img = null;

         try {
            img = ImageIO.read(this.skinFile);
         } catch (IOException var12) {
            ;
         }

         Random r = new Random();
         int low = 10;
         int high = 1000;
         int result = r.nextInt(high - low) + low;
         if (Minecraft.getMinecraft().getTextureManager().getTexture(new ResourceLocation(this.skinFile.getName())) == null) {
            String str = this.skinFile.getName();
            String skinName = str.substring(str.indexOf("_") + 1, str.indexOf("."));

            try {
               this.skinTexture = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(skinName + "dynamic", new DynamicTexture(img));
            } catch (NullPointerException var11) {
               var11.printStackTrace();
            }
         } else {
            this.skinTexture = new ResourceLocation(this.skinFile.getName());
         }
      }

   }

   public void clicked(int mouseX, int mouseY) {
      super.clicked(mouseX, mouseY);
      if (this.skinTexture != null) {
         this.parentSkinViewer.currentSkinResourceLocation = this.skinTexture;
      }

      Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
   }

   protected void actionPerformed(GuiButton button) {
      super.actionPerformed(button);
   }

   protected int height() {
      return 20;
   }
}
