package com.ferullogaming.countercraft.client.gui.api;

import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiFGButton extends GuiButton {
   public int isOver = 2;
   public ItemStack renderStack = null;
   public String hoverText = "";
   public boolean drawBackground = true;
   public boolean drawShadow = true;
   public String buttonColor;
   public boolean centeredText;
   public boolean soundPlayed;
   String hoverSound;
   String clickSound;
   private ResourceLocation iconTexture;
   private int fade;
   private boolean highlighted;
   private boolean stretchImage;
   private int xMovement;
   private boolean animationStarted;
   private int toolTipY;
   public String toolTip;
   private boolean showToolTip;
   private Color toolTipColor;
   private float textScale;
   public boolean work = true;
   public boolean par7 = false;

   public GuiFGButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
      super(par1, par2, par3, par4, par5, par6Str);
      this.buttonColor = GuiCCMenu.menuTheme;
      this.centeredText = true;
      this.soundPlayed = true;
      this.hoverSound = "countercraft:gui.buttonHover";
      this.clickSound = "countercraft:gui.buttonClick";
      this.iconTexture = null;
      this.fade = 0;
      this.highlighted = false;
      this.stretchImage = false;
      this.animationStarted = true;
      this.showToolTip = false;
      this.textScale = 1.0F;
   }
   
   public GuiFGButton(int par1, int par2, int par3, int par4, int par5, String par6Str, boolean par7Speci) {
      super(par1, par2, par3, par4, par5, par6Str);
      this.buttonColor = GuiCCMenu.menuTheme;
      this.centeredText = true;
      this.soundPlayed = true;
      this.hoverSound = "countercraft:gui.buttonHover";
      this.clickSound = "countercraft:gui.buttonClick";
      this.iconTexture = null;
      this.fade = 0;
      this.highlighted = false;
      this.stretchImage = false;
      this.animationStarted = true;
      this.showToolTip = false;
      this.textScale = 1.0F;
      par7 = par7Speci;
   }

   public GuiFGButton(int par1, int par2, int par3, ItemStack par4Stack) {
      super(par1, par2, par3, 18, 18, "");
      this.buttonColor = GuiCCMenu.menuTheme;
      this.centeredText = true;
      this.soundPlayed = true;
      this.hoverSound = "countercraft:gui.buttonHover";
      this.clickSound = "countercraft:gui.buttonClick";
      this.iconTexture = null;
      this.fade = 0;
      this.highlighted = false;
      this.stretchImage = false;
      this.animationStarted = true;
      this.showToolTip = false;
      this.textScale = 1.0F;
      this.renderStack = par4Stack;
   }

   public GuiFGButton(int par1, int par2, int par3, int par4, int par5, ResourceLocation par6) {
      super(par1, par2, par3, par4, par5, "");
      this.buttonColor = GuiCCMenu.menuTheme;
      this.centeredText = true;
      this.soundPlayed = true;
      this.hoverSound = "countercraft:gui.buttonHover";
      this.clickSound = "countercraft:gui.buttonClick";
      this.iconTexture = null;
      this.fade = 0;
      this.highlighted = false;
      this.stretchImage = false;
      this.animationStarted = true;
      this.showToolTip = false;
      this.textScale = 1.0F;
      this.iconTexture = par6;
   }

   public GuiFGButton setCenter(boolean par1) {
      this.centeredText = par1;
      return this;
   }

   public GuiFGButton setColor(String par1) {
      this.buttonColor = par1;
      return this;
   }

   public GuiFGButton stretchImage() {
      this.stretchImage = true;
      return this;
   }

   public GuiFGButton setHighlighted(boolean givenBoolean) {
      this.highlighted = givenBoolean;
      return this;
   }

   public GuiFGButton setToolTip(String givenToolTip, Color givenToolTipColor) {
      this.showToolTip = true;
      if (this.par7) {
    	  this.toolTip = givenToolTip.equals("") ? "Обычный" : givenToolTip;
          this.toolTipColor = givenToolTipColor;
          return this;
      }
      this.toolTip = givenToolTip;
      this.toolTipColor = givenToolTipColor;
      return this;
   }

   public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
	  if (this.work) {
	      if (super.drawButton && ClientVariables.hasSeenNotification) {
	         int toolTipWidth = minecraft.fontRenderer.getStringWidth(this.toolTip);
	         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	         super.field_82253_i = mouseX >= super.xPosition && mouseY >= super.yPosition && mouseX < super.xPosition + super.width && mouseY < super.yPosition + super.height;
	         this.isOver = this.getHoverState(super.field_82253_i);
	         if (this.drawBackground) {
	            if (this.drawShadow) {
	               CCRenderHelper.drawRectWithShadow((double)super.xPosition, (double)super.yPosition, (double)super.width, (double)super.height, this.buttonColor, 1.0F);
	            } else {
	               CCRenderHelper.drawRect((double)super.xPosition, (double)super.yPosition, (double)super.width, (double)super.height, this.buttonColor, 1.0F);
	            }
	         }
	
	         this.mouseDragged(minecraft, mouseX, mouseY);
	         String displayText = super.displayString;
	         if (this.isOver == 2) {
	            if (this.fade <= 0) {
	               this.fade = 0;
	            } else {
	               --this.fade;
	            }
	
	            CCRenderHelper.drawRectAlpha(super.xPosition, super.yPosition, super.width, super.height, new Color(255, 255, 255, this.fade));
	            if (!this.soundPlayed) {
	               Minecraft.getMinecraft().sndManager.playSoundFX(this.hoverSound, 1.0F, 2.0F);
	               this.soundPlayed = true;
	            }
	         } else {
	            this.fade = 90;
	            this.soundPlayed = false;
	         }
	
	         if (this.highlighted) {
	            CCRenderHelper.drawRect((double)(super.xPosition - 1), (double)(super.yPosition - 1), (double)(super.width + 2), (double)(super.height + 2), "0xFFFFFF", 1.0F);
	         }
	
	         if (this.iconTexture != null) {
	            if (this.isOver == 2) {
	               CCRenderHelper.renderColor(8421504);
	            }
	
	            if (this.stretchImage) {
	               CCRenderHelper.drawImage((double)super.xPosition, (double)super.yPosition, this.iconTexture, (double)super.width, (double)super.height);
	            } else {
	               CCRenderHelper.drawImage((double)(super.xPosition + super.width / 2 - 8), (double)(super.yPosition + (super.height - 8) - 10), this.iconTexture, 16.0D, 16.0D);
	            }
	
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            return;
	         }
	
	         if (this.renderStack != null) {
	            CCRenderHelper.renderItemStackIntoGUI(this.renderStack, super.xPosition, super.yPosition + 1);
	            return;
	         }
	
	         if (!super.enabled) {
	            displayText = EnumChatFormatting.GRAY + displayText;
	         }
	
	         if (this.showToolTip && this.isOver == 2) {
	            CCRenderHelper.drawRectWithShadow2(mouseX, mouseY - 10, this.toolTipY, 10, this.toolTipColor, 100);
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
	               CCRenderHelper.renderText(this.toolTip, mouseX + 1, mouseY - 9);
	            }
	         }
	
	         if (!this.centeredText) {
	            CCRenderHelper.renderText(displayText, super.xPosition + 4, super.yPosition + (super.height - 8) / 2, this.isOver == 2 ? 8421504 : 16777215);
	            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	            return;
	         }
	
	         if (this.textScale != 1.0F) {
	            CCRenderHelper.renderCenteredTextScaled(displayText, super.xPosition + super.width / 2, super.yPosition + (super.height - 4) / 2, (double)this.textScale);
	         } else {
	            CCRenderHelper.renderCenteredText(displayText, super.xPosition + super.width / 2, super.yPosition + (super.height - 8) / 2, this.isOver == 2 ? 8421504 : 16777215);
	         }
	
	         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      } else {
	         this.isOver = 0;
	      }
	  }
   }

   public GuiFGButton disableBackground() {
      this.drawBackground = false;
      return this;
   }

   public GuiFGButton disableShadow() {
      this.drawShadow = false;
      return this;
   }

   public GuiFGButton setScale(float givenScale) {
      this.textScale = givenScale;
      return this;
   }
}
