package com.ferullogaming.countercraft.client.gui.api;

import org.lwjgl.opengl.GL11;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class GuiFGLoadingScreen extends GuiFGScreen {
   private static boolean flag = false;
   private float white = 0.0F;
   private float loadingBackground = 0.0F;
   private float titleCD = 0.0F;
   private int incDelay = 0;
   private int incDelay2 = 0;
   private int guiDisplay = 0;

   public void updateScreen() {
      if (this.white < 1.0F) {
         this.white += 0.15F;
      } else {
         if (this.guiDisplay++ >= 70) {
            ClientManager.isGameLoading = false;
            Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
            if (!flag) {
               flag = true;
            }
         }

         if (this.incDelay++ >= 15) {
            if (this.loadingBackground < 1.0F) {
               this.loadingBackground += 0.05F;
            }

            if (this.incDelay2++ >= 20) {
               if (this.titleCD < 1.0F) {
                  this.titleCD += 0.05F;
               }

            }
         }
      }
   }

   protected void keyTyped(char par1, int par2) {
   }

   protected void mouseClicked(int par1, int par2, int par3) {
   }

   public void drawScreen(int par1, int par2, float par3) {
      CCRenderHelper.drawRect(0.0D, 0.0D, (double)super.width, (double)super.height, "0x000000", 1.0F);
      CCRenderHelper.drawRect(0.0D, 0.0D, (double)super.width, (double)super.height, "0xffffff", this.white);
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      int imgW = 1;
      int imgH = 1;
      CCRenderHelper.drawImageTransparent(0.0D, 0.0D, new ResourceLocation("countercraft:textures/gui/loading_screen.png"), (double)super.width, (double)super.height, (double)this.loadingBackground);
      GL11.glPopMatrix();
   }
}
