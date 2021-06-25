package com.ferullogaming.countercraft.client.gui;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScrollerSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class GuiCCScrollerTextSlot extends GuiFGScrollerSlot {
   public static final char COLOR_CHAR = '§';
   private final String displayText;
   private boolean renderSmall = false;
   private String linkURL;

   public GuiCCScrollerTextSlot(String text) {
      this.displayText = text;
   }

   public static String translateAlternateColorCodes(String textToTranslate) {
      char[] b = textToTranslate.toCharArray();

      for(int i = 0; i < b.length - 1; ++i) {
         if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
            b[i] = '§';
            b[i + 1] = Character.toLowerCase(b[i + 1]);
         }
      }

      return new String(b);
   }

   public GuiCCScrollerTextSlot setUrl(String givenURL) {
      this.linkURL = givenURL;
      return this;
   }

   public boolean canSelect() {
      return true;
   }

   public void doRender(int mouseX, int mouseY) {
      CCRenderHelper.renderTextScaled((!this.isSelected() && (!this.isHovered(mouseX, mouseY) || this.linkURL == null) ? "" : EnumChatFormatting.WHITE) + translateAlternateColorCodes(this.displayText), super.posX + 2, super.posY + 2, this.renderSmall ? 0.5D : 1.0D);
   }

   protected int height() {
      return this.renderSmall ? 6 : 11;
   }

   public void clicked(int mouseX, int mouseY) {
      super.clicked(mouseX, mouseY);
      String[] words = this.displayText.split(" ");
      if (this.linkURL != null) {
         if (this.linkURL.startsWith("http://") || this.linkURL.startsWith("https://")) {
            GuiCCMenu.openURL(this.linkURL);
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            return;
         }
      } else {
         String[] arr$ = words;
         int len$ = words.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String word = arr$[i$];
            if (word.length() > 2 && word.startsWith("&")) {
               word = word.substring(2);
            }

            if (word.startsWith("http://") || word.startsWith("https://")) {
               GuiCCMenu.openURL(word);
               Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
               return;
            }
         }
      }

   }

   public GuiCCScrollerTextSlot renderSmall() {
      this.renderSmall = true;
      return this;
   }
}
