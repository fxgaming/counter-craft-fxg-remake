package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.AbstractClientPlayerCC;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.EnumCompRank;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PlayerRank;
import com.ferullogaming.countercraft.client.cloud.item.CloudItemStack;
import com.ferullogaming.countercraft.client.gui.ImageBufferDownloadGray;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.render.IRenderOnGUI;
import com.ferullogaming.countercraft.client.render.guns.RenderGun;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.references.GameType;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;

public class CCRenderHelper {
   public static int gameColor = 10271186;
   public static int crosshairColor = 16777215;
   public static ResourceLocation RES_EMERALD_LOW = new ResourceLocation("countercraft:textures/gui/emerald1_16.png");
   public static ResourceLocation RES_EMERALD_MED = new ResourceLocation("countercraft:textures/gui/emerald1_32.png");
   public static ResourceLocation RES_EMERALD_HIGH = new ResourceLocation("countercraft:textures/gui/emerald1_64.png");
   private static ResourceLocation icons = new ResourceLocation("countercraft:textures/gui/icon.png");
   private static float zLevel = 0.0F;

   public static int getGameColor() {
      return gameColor;
   }

   public static void setGameColor(String par1) {
      gameColor = (int)Long.parseLong(par1.replaceFirst("0x", ""), 16);
   }

   public static void renderColor(int par1) {
      Color color = Color.decode("" + par1);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      GL11.glColor3f(red, green, blue);
   }

   public static void renderColor(String par1) {
      Color color = Color.decode("" + par1);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      GL11.glColor3f(red, green, blue);
   }

   public static void renderColor() {
      Color color = Color.decode("" + gameColor);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      GL11.glColor3f(red, green, blue);
   }

   public static void renderCrosshairColor() {
      Color color = Color.decode("" + crosshairColor);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      GL11.glColor4f(red, green, blue, 0.9F);
   }

   public static void renderIcon(double par1, double par2, int var3, int var4, int imageSize, double scale) {
      int var5 = var3 * imageSize;
      int var6 = var4 * imageSize;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      float red = 1.0F;
      float green = 1.0F;
      float blue = 1.0F;
      GL11.glColor4f(red, green, blue, 0.9F);
      GL11.glScaled(scale, scale, scale);
      GL11.glTranslated(par1 * (1.0D / scale), par2 * (1.0D / scale), 0.0D);
      Minecraft.getMinecraft().getTextureManager().bindTexture(icons);
      drawTexturedModalRect(imageSize / 2, imageSize / 2, var5, var6, imageSize, imageSize);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void renderPlayerScoreboard(int par1, int par2, int width, int height, int textHeight, String par4, EnumChatFormatting par6, IGame currentGame, String... par7) {
      PlayerDataCloud cloud = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (par4.equalsIgnoreCase(cloud.getUsername())) {
         par6 = EnumChatFormatting.AQUA;
      }

      String[] extras = (String[])Arrays.copyOfRange(par7, 1, par7.length);
      int var1 = extras.length * 31;
      String ping = par7[0];
      String money = par7[1];
      drawRect((double)par1, (double)par2, 26.0D, (double)height, "0x000000", 0.7F);
      renderCenteredText(par6 + ping, par1 + 13, par2 + textHeight);
      int usernameWidth = width - 27 - var1 - 10;
      drawRect((double)(par1 + 27), (double)par2, (double)usernameWidth, (double)height, "0x000000", 0.7F);
      int x1 = par1 + usernameWidth + 28;
      drawRect((double)x1, (double)par2, 40.0D, (double)height, "0x000000", 0.7F);
      renderCenteredText(par6 + money, x1 + 20, par2 + textHeight);
      int usernameDx;
      if (par4 != null && par4.length() != 0) {
         usernameDx = 0;
         PlayerDataCloud cloud1 = PlayerDataHandler.getPlayerCloudData(par4);
         String displayName = par4;
         String clanTag = !cloud1.clanTag.equals("NONE") && cloud1.clanTag != null && cloud1.clanTag.length() > 0 ? EnumChatFormatting.YELLOW + "[" + cloud1.clanTag + "]" : "";
         String supporterStar = cloud1.isSupporter ? EnumChatFormatting.YELLOW + Character.toString('★') : "";
         if (cloud.getParty() != null && cloud.getParty().isPresent(par4)) {
            displayName = EnumChatFormatting.AQUA + "" + par4;
         }

         if (cloud1.group != null) {
            displayName = EnumChatFormatting.WHITE + "" + cloud1.group.getDisplayName() + " " + par6 + displayName;
         }

         int exp = cloud1.exp;
         if (exp >= 0) {
            PlayerRank rank = PlayerRank.getRankFromEXP(exp);
            ResourceLocation icon = new ResourceLocation("countercraft:textures/misc/ranks/rank_" + rank.getTexture() + ".png");
            drawImage((double)par1 + 43.5D, (double)(par2 + textHeight) + 0.5D, icon, 7.0D, 7.0D);
            renderCenteredTextScaledWithOutline(cloud1.prestigeLevel > 1 ? cloud1.prestigeLevel + "" : "", par1 + 47, par2 + 6, 0.5D);
         }

         if (cloud1 != null && currentGame != null && currentGame.getGameType().equals(GameType.competitive.getNameID()) && cloud1.getCompRank() != null) {
            EnumCompRank rank = cloud1.getCompRank();
            String rankResLoc = rank.getResourceName();
            ResourceLocation icon = new ResourceLocation("countercraft:textures/misc/compranks/" + rankResLoc + ".png");
            GL11.glPushMatrix();
            drawImage((double)par1 + 177.5D, (double)(par2 + textHeight) + -0.5D, icon, 18.0D, 9.0D);
            GL11.glPopMatrix();
         }

         if (cloud1.coinDisplayed > 0) {
            CloudItemStack coinStack = new CloudItemStack("-fake-", cloud1.coinDisplayed);
            if (coinStack != null && coinStack.getItemStack() != null) {
               GL11.glPushMatrix();
               GL11.glTranslated((double)(par1 + 54), (double)(par2 + textHeight) + 0.1D, 0.0D);
               double scale = 0.5D;
               GL11.glScaled(scale, scale, scale);
               renderItemStackIntoGUI(coinStack.getItemStack(), 0, 0);
               GL11.glPopMatrix();
               usernameDx += 11;
            }
         }

         if (cloud1.boostersActive.size() > 0) {
            for(int i = 0; i < cloud1.boostersActive.size(); ++i) {
               Booster boost = (Booster)cloud1.boostersActive.get(i);
               String booster = EnumChatFormatting.BOLD + "" + boost.multiplier;
               if (i == cloud1.boostersActive.size() - 1) {
                  booster = EnumChatFormatting.BOLD + "x" + boost.multiplier;
               }

               if (boost.isEXP()) {
                  booster = EnumChatFormatting.GOLD + booster;
               } else if (boost.isEmeralds()) {
                  booster = EnumChatFormatting.GREEN + booster;
               }

               renderTextRight(booster, par1 + 27 + usernameWidth - i * 8, par2 + textHeight);
            }
         }

         renderPlayerHead(par4, (double)(par1 + 32), (double)(par2 + textHeight) + 0.1D, 0.4D, false);
         renderText(supporterStar + " " + clanTag + " " + par6 + displayName, par1 + 54 + usernameDx, par2 + textHeight);
      } else {
         renderText(par6 + "USERNAME", par1 + 54, par2 + textHeight);
         ResourceLocation icon = new ResourceLocation("countercraft:textures/misc/ranks/rankbackground_1.png");
         drawImage((double)(par1 + 43), (double)(par2 + textHeight) - 0.2D, icon, 8.0D, 8.0D);
      }

      for(usernameDx = 0; usernameDx < extras.length; ++usernameDx) {
         if (usernameDx != 0) {
            String value = extras[usernameDx];
            drawRect((double)(x1 + 10 + usernameDx * 31), (double)par2, 30.0D, (double)height, "0x000000", 0.7F);
            renderCenteredText(par6 + value + "", x1 + 10 + usernameDx * 31 + 16, par2 + textHeight);
         }
      }

   }

   private static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
      tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
      tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
      tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
      tessellator.draw();
   }

   public static void renderItemStackIntoGUI(ItemStack itemstack, int posx, int posy) {
      GL11.glPushMatrix();
      RenderItem itemRenderer = new RenderItem();
      Minecraft mc = Minecraft.getMinecraft();
      itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), itemstack, posx, posy);
      GL11.glDisable(2896);
      GL11.glPopMatrix();
   }

   public static void renderItemStackIngameStyle(ItemStack itemstack, double posx, double posy) {
      Tessellator tessellator = Tessellator.instance;
      Icon icon = itemstack.getIconIndex();
      if (icon == null) {
         TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
         ResourceLocation resourcelocation = texturemanager.getResourceLocation(itemstack.getItemSpriteNumber());
         icon = ((TextureMap)texturemanager.getTexture(resourcelocation)).getAtlasSprite("missingno");
      }

      float f4 = ((Icon)icon).getMinU();
      float f5 = ((Icon)icon).getMaxU();
      float f6 = ((Icon)icon).getMinV();
      float f7 = ((Icon)icon).getMaxV();
      float f9 = 0.5F;
      float f10 = 0.25F;
      float f11 = 0.021875F;
      GL11.glPushMatrix();
      float f12 = 0.0625F;
      int j = itemstack.stackSize;
      byte thickness = 1;
      GL11.glTranslated(posx, posy, 0.0D);
      GL11.glTranslatef(-f9, -f10, -((f12 + f11) * (float)thickness / 2.0F));

      for(int k = 0; k < thickness; ++k) {
         GL11.glTranslatef(0.0F, 0.0F, f12 + f11);
         if (itemstack.getItemSpriteNumber() == 0) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationItemsTexture);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         ItemRenderer.renderItemIn2D(tessellator, f5, f6, f4, f7, ((Icon)icon).getIconWidth(), ((Icon)icon).getIconHeight(), f12);
      }

      GL11.glPopMatrix();
   }

   public static void renderSpecialItemStackInventory(ItemStack stack, double x, double y) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         GL11.glPushMatrix();
         GL11.glTranslated(0.0D, 0.0D, 15.0D);
         ((IRenderOnGUI)renderer).renderInventorySlot(stack, x, y);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 12.0D, y - 19.0D, 10.0D);
         double scale = 1.5D;
         GL11.glScaled(scale, scale, scale);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }

   }

   public static void renderSpecialItemStackInventory(ItemStack stack, double x, double y, double scale) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         GL11.glPushMatrix();
         GL11.glTranslated(x, y, 20.0D);
         GL11.glScaled(scale, scale, scale);
         ((IRenderOnGUI)renderer).renderInventorySlot(stack, 0.0D, 0.0D);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 12.0D, y - 18.0D, 0.0D);
         double scale1 = 1.5D;
         GL11.glScaled(scale1, scale1, scale1);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }

   }

   public static void renderSpecialItemStackInventoryWithRotation(ItemStack stack, double x, double y, double scale, float rotX, float rotZ) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         GL11.glPushMatrix();
         GL11.glTranslated(x, y, 20.0D);
         GL11.glScaled(scale, scale, scale);
         GL11.glRotatef(rotZ, 0.0F, 1.0F, 0.0F);
         ((IRenderOnGUI)renderer).renderInventorySlot(stack, 0.0D, 0.0D);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 12.0D, y - 18.0D, 0.0D);
         double scale1 = 1.5D;
         GL11.glScaled(scale1, scale1, scale1);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }
   }
   
   public static void renderSpecialItemStackInventoryWithRotationSTIK(ItemStack stack, double x, double y, double scale, float rotX, float rotZ) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         GL11.glPushMatrix();
         GL11.glTranslated(x, y, 20.0D);
         GL11.glScaled(scale, scale, scale);
         GL11.glRotatef(rotZ, 0.0F, 1.0F, 0.0F);
         ((IRenderOnGUI)renderer).renderSkins(stack, 0.0D, 0.0D);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 12.0D, y - 18.0D, 0.0D);
         double scale1 = 1.5D;
         GL11.glScaled(scale1, scale1, scale1);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }
   }

   public static void renderSpecialItemStackInspection(ItemStack stack, double x, double y, double rot) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         GL11.glPushMatrix();
         GL11.glTranslated(0.0D, 0.0D, 200.0D);
         ((IRenderOnGUI)renderer).renderInspection(stack, x, y, rot);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 60.0D, y - 45.0D, 0.0D);
         double scale1 = 7.0D;
         GL11.glScaled(scale1, scale1, scale1);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }

   }

   public static void renderSpecialItemStackInspection(ItemStack stack, double x, double y, double rot, int givenStickerID) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         GL11.glPushMatrix();
         GL11.glTranslated(0.0D, 0.0D, 200.0D);
         if (stack.getItem() instanceof ItemGun) {
            ((RenderGun)renderer).highlightedSticker = givenStickerID;
            ((RenderGun)renderer).shouldHighlightSticker = true;
         }

         ((IRenderOnGUI)renderer).renderInspection(stack, x, y, rot);
         GL11.glPopMatrix();
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 60.0D, y - 45.0D, 0.0D);
         double scale1 = 7.0D;
         GL11.glScaled(scale1, scale1, scale1);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }

   }

   public static void renderSpecialItemStackHUD(ItemStack stack, double x, double y) {
      IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(stack, ItemRenderType.EQUIPPED);
      if (renderer != null && renderer instanceof IRenderOnGUI) {
         ((IRenderOnGUI)renderer).renderOnHUD(stack, x, y);
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated(x - 95.0D, y - 55.0D, 0.0D);
         double scale1 = 7.0D;
         GL11.glScaled(scale1, scale1, scale1);
         renderItemStackIntoGUI(stack, 0, 0);
         GL11.glPopMatrix();
      }

   }

   public static void renderScaledItemStackIcon(ItemStack par1, int x, int y, double scale) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y, 0.0F);
      GL11.glScaled(scale, scale, scale);
      renderItemStackIntoGUI(par1, 0, 0);
      GL11.glPopMatrix();
   }

   public static void drawRectWithShadow(double par0, double par1, double par2, double par3, String par4Hex, float par5Alpha) {
      drawRect(par0 - 1.0D, par1 - 1.0D, par2 + 2.0D, par3 + 2.0D, "0x000000", 0.3F);
      drawRect(par0, par1, par2, par3, par4Hex, par5Alpha);
   }

   public static void drawRectWithShadow2(int x, int y, int width, int height, Color color, int alpha) {
      drawRect2(x - 1, y - 1, width + 2, height + 2, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha / 2));
      drawRect2(x, y, width, height, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
   }

   public static void drawRect2(int xStart, int yStart, int xEnd, int yEnd, Color color) {
      float r = (float)color.getRed() / 255.0F;
      float g = (float)color.getGreen() / 255.0F;
      float b = (float)color.getBlue() / 255.0F;
      float a = (float)color.getAlpha() / 255.0F;
      Tessellator tessellator = Tessellator.instance;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glColor4f(r, g, b, a);
      tessellator.startDrawingQuads();
      tessellator.addVertex((double)xStart, (double)(yStart + yEnd), 0.0D);
      tessellator.addVertex((double)(xStart + xEnd), (double)(yStart + yEnd), 0.0D);
      tessellator.addVertex((double)(xStart + xEnd), (double)yStart, 0.0D);
      tessellator.addVertex((double)xStart, (double)yStart, 0.0D);
      tessellator.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void drawRectAlpha(int xStart, int yStart, int xEnd, int yEnd, Color color) {
      float r = (float)color.getRed() / 255.0F;
      float g = (float)color.getGreen() / 255.0F;
      float b = (float)color.getBlue() / 255.0F;
      float a = (float)color.getAlpha() / 255.0F;
      Tessellator tessellator = Tessellator.instance;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glColor4f(r, g, b, a);
      tessellator.startDrawingQuads();
      tessellator.addVertex((double)xStart, (double)(yStart + yEnd), 0.0D);
      tessellator.addVertex((double)(xStart + xEnd), (double)(yStart + yEnd), 0.0D);
      tessellator.addVertex((double)(xStart + xEnd), (double)yStart, 0.0D);
      tessellator.addVertex((double)xStart, (double)yStart, 0.0D);
      tessellator.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void drawGradientRect(int par0, int par1, int par2, int par3, float par4) {
      GL11.glEnable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, par4);
      ResourceLocation gradient1 = new ResourceLocation("countercraft:textures/gui/gradient.png");
      drawImage((double)par0, (double)par1, gradient1, (double)par2, (double)par3);
   }

   public static void drawRect(double par0, double par1, double par2, double par3, String par4Hex, float par5Alpha) {
      Color color = Color.decode(par4Hex);
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      drawRect(par0, par1, par0 + par2, par1 + par3, red, green, blue, par5Alpha);
   }

   public static void drawRect(double par0, double par1, double par2, double par3, float par4Red, float par5Green, float par6Blue, float par7Alpha) {
      Tessellator var9 = new Tessellator();
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glColor4f(par4Red, par5Green, par6Blue, par7Alpha);
      var9.startDrawingQuads();
      var9.addVertex(par0, par3, 0.0D);
      var9.addVertex(par2, par3, 0.0D);
      var9.addVertex(par2, par1, 0.0D);
      var9.addVertex(par0, par1, 0.0D);
      var9.draw();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static int resWidth() {
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
      return scaledresolution.getScaledWidth();
   }

   public static int resHeight() {
      Minecraft mc = Minecraft.getMinecraft();
      ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
      return scaledresolution.getScaledHeight();
   }

   public static void renderTextScaled(String text, int posX, int posY, double par4) {
      GL11.glPushMatrix();
      GL11.glTranslated((double)posX, (double)posY, 0.0D);
      GL11.glScaled(par4, par4, par4);
      renderText(text, 0, 0);
      GL11.glPopMatrix();
   }

   public static void renderText(String text, int posX, int posY) {
      renderText(text, posX, posY, true);
   }

   public static void renderTextNoShadow(String text, int posX, int posY) {
      renderText(text, posX, posY, false);
   }

   public static void renderText(String text, int posX, int posY, boolean par4) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.fontRenderer.drawString(text, posX, posY, gameColor, par4);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawTextWithOutline(String text, int x, int y, int color) {
      Minecraft mc = Minecraft.getMinecraft();
      FontRenderer fr = mc.fontRenderer;
      fr.drawString(text, x - 1, y + 1, 0);
      fr.drawString(text, x, y + 1, 0);
      fr.drawString(text, x + 1, y + 1, 0);
      fr.drawString(text, x - 1, y, 0);
      fr.drawString(text, x + 1, y, 0);
      fr.drawString(text, x - 1, y - 1, 0);
      fr.drawString(text, x, y - 1, 0);
      fr.drawString(text, x + 1, y - 1, 0);
      fr.drawString(text, x, y, color);
   }

   public static void renderCenteredText(String text, int posX, int posY) {
      renderCenteredText(text, posX, posY, true);
   }

   public static void renderCenteredText(String text, int posX, int posY, boolean par4) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.fontRenderer.drawString(text, posX - mc.fontRenderer.getStringWidth(text) / 2, posY, gameColor, par4);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderTextRight(String text, int posX, int posY) {
      renderTextRight(text, posX, posY, true);
   }

   public static void renderTextRight(String text, int posX, int posY, boolean par4) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.fontRenderer.drawString(text, posX - mc.fontRenderer.getStringWidth(text), posY, gameColor, par4);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderTextScaled(String text, int posX, int posY, double par4, int par5) {
      GL11.glPushMatrix();
      GL11.glTranslated((double)posX, (double)posY, 0.0D);
      GL11.glScaled(par4, par4, par4);
      renderText(text, 0, 0, par5);
      GL11.glPopMatrix();
   }

   public static void renderText(String text, int posX, int posY, int par4) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.fontRenderer.drawString(text, posX, posY, par4, true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderCenteredText(String text, int posX, int posY, int par4) {
      Minecraft mc = Minecraft.getMinecraft();
      mc.fontRenderer.drawString(text, posX - mc.fontRenderer.getStringWidth(text) / 2, posY, par4, true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderCenteredTextScaled(String text, int posX, int posY, double par4) {
      Minecraft mc = Minecraft.getMinecraft();
      double width = (double)(mc.fontRenderer.getStringWidth(text) / 2) * par4;
      GL11.glPushMatrix();
      GL11.glTranslated((double)posX - width, (double)posY, 0.0D);
      GL11.glScaled(par4, par4, par4);
      renderText(text, 0, 0);
      GL11.glPopMatrix();
   }

   public static void renderCenteredTextScaledWithOutline(String text, int posX, int posY, double par4) {
      Minecraft mc = Minecraft.getMinecraft();
      double width = (double)(mc.fontRenderer.getStringWidth(text) / 2) * par4;
      GL11.glPushMatrix();
      GL11.glTranslated((double)posX - width, (double)posY, 0.0D);
      GL11.glScaled(par4, par4, par4);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, -1, -1, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, 0, -1, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, 1, -1, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, -1, 0, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, 1, 0, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, -1, 1, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, 0, 1, 0);
      mc.fontRenderer.drawString(EnumChatFormatting.BLACK + text, 1, 1, 0);
      mc.fontRenderer.drawString(text, 0, 0, 16777215);
      GL11.glPopMatrix();
   }

   public static void drawImageTransparent(double x, double y, ResourceLocation image, double width, double height, double alpha) {
      Minecraft.getMinecraft().renderEngine.bindTexture(image);
      GL11.glColor4d(255.0D, 255.0D, 255.0D, alpha);
      Tessellator tess = Tessellator.instance;
      GL11.glEnable(3042);
      GL11.glEnable(2832);
      tess.setColorRGBA_F(255.0F, 0.0F, 0.0F, 0.5F);
      tess.startDrawingQuads();
      tess.addVertexWithUV(x, y + height, 0.0D, 0.0D, 1.0D);
      tess.addVertexWithUV(x + width, y + height, 0.0D, 1.0D, 1.0D);
      tess.addVertexWithUV(x + width, y, 0.0D, 1.0D, 0.0D);
      tess.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
      tess.draw();
      GL11.glDisable(2832);
      GL11.glDisable(3042);
   }

   public static void drawImage(double par0, double par1, ResourceLocation image, double par2Width, double par3Height) {
      Minecraft.getMinecraft().renderEngine.bindTexture(image);
      Tessellator tess = new Tessellator();
      tess.startDrawingQuads();
      tess.addVertexWithUV(par0, par1 + par3Height, 0.0D, 0.0D, 1.0D);
      tess.addVertexWithUV(par0 + par2Width, par1 + par3Height, 0.0D, 1.0D, 1.0D);
      tess.addVertexWithUV(par0 + par2Width, par1, 0.0D, 1.0D, 0.0D);
      tess.addVertexWithUV(par0, par1, 0.0D, 0.0D, 0.0D);
      tess.draw();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void renderPlayerHead(String par1, int par2, int par3) {
      renderPlayerHead(par1, (double)par2, (double)par3, 1.0D, true);
   }

   public static void renderPlayerHead(String par1, double par2, double par3, double par4, boolean par5) {
      if (par1.length() > 0) {
         ResourceLocation resourcelocation = AbstractClientPlayer.locationStevePng;
         if (par1 != null && par1.length() > 0) {
            PlayerDataCloud playerDataCloud = PlayerDataHandler.getPlayerCloudData(par1);
            if (playerDataCloud != null) {
               resourcelocation = AbstractClientPlayerCC.getLocationSkull(playerDataCloud.playerUUID);
               getDownloadImageSkin(resourcelocation, playerDataCloud.playerUUID);
            } else {
               resourcelocation = AbstractClientPlayer.getLocationSkull(par1);
               AbstractClientPlayer.getDownloadImageSkin(resourcelocation, par1);
            }
         }

         GL11.glPushMatrix();
         if (par5) {
            drawRect(par2 - 1.0D, par3 - 1.0D, 20.0D, 20.0D, "0x000000", 0.5F);
         }

         Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
         GL11.glTranslated(par2, par3, 0.0D);
         GL11.glScaled(0.75D, 0.39D, 0.0D);
         double scale = 0.75D;
         GL11.glScaled(scale, scale, scale);
         GL11.glScaled(par4, par4, par4);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 32, 64, 32, 64);
         Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 160, 64, 32, 64);
         GL11.glPopMatrix();
      }

   }

   public static void renderStringFacingPlayer(String par1, double par2, double par3, double par4, float par5, double scale) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayer player = mc.thePlayer;
      GL11.glPushMatrix();
      FontRenderer fontrenderer = mc.fontRenderer;
      float f122 = 1.8F;
      float scale2 = 0.016F;
      double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)par5;
      double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)par5;
      double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)par5;
      GL11.glTranslated(par2, par3, par4);
      GL11.glTranslated(-d0, -d1, -d2);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-player.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(player.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-scale2, -scale2, scale2);
      GL11.glScaled(scale, scale, scale);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      Tessellator tessellator = Tessellator.instance;
      byte b0 = 0;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      fontrenderer.drawString(par1, -fontrenderer.getStringWidth(par1) / 2, b0, 553648127);
      GL11.glDepthMask(true);
      fontrenderer.drawString(par1, -fontrenderer.getStringWidth(par1) / 2, b0, -1);
      GL11.glEnable(2896);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glPopMatrix();
   }

   public static void renderImageFacingPlayer(ResourceLocation par1, double par2, double par3, double par4, float par5, int width, int height, String color) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayer player = mc.thePlayer;
      GL11.glPushMatrix();
      FontRenderer fontrenderer = mc.fontRenderer;
      float f122 = 1.8F;
      float scale2 = 0.02F;
      double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)par5;
      double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)par5;
      double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)par5;
      GL11.glTranslated(par2, par3, par4);
      GL11.glTranslated(-d0, -d1, -d2);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glRotatef(-player.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(player.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-scale2, -scale2, scale2);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      renderColor(color);
      drawImage((double)(-width / 2), (double)(-height / 2), par1, (double)width, (double)height);
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(2896);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void renderImageFacingPlayerWithDepth(ResourceLocation par1, double par2, double par3, double par4, float par5, int width, int height, String color) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayer player = mc.thePlayer;
      GL11.glPushMatrix();
      FontRenderer fontrenderer = mc.fontRenderer;
      float f122 = 1.8F;
      float scale2 = 0.02F;
      double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)par5;
      double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)par5;
      double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)par5;
      GL11.glTranslated(par2, par3, par4);
      GL11.glTranslated(-d0, -d1, -d2);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glRotatef(-player.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(player.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-scale2, -scale2, scale2);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      renderColor(color);
      drawImage((double)(-width / 2), (double)(-height / 2), par1, (double)width, (double)height);
      GL11.glEnable(2896);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void renderSmokeNadeSmoke(ResourceLocation par1, double par2, double par3, double par4, float par5, int width, int height, String color, double alpha) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayer player = mc.thePlayer;
      GL11.glPushMatrix();
      FontRenderer fontrenderer = mc.fontRenderer;
      float f122 = 1.8F;
      float scale2 = 0.02F;
      double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)par5;
      double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)par5;
      double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)par5;
      GL11.glTranslated(par2, par3, par4);
      GL11.glTranslated(-d0, -d1, -d2);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glScalef(-scale2, -scale2, scale2);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3008);
      renderColor(color);
      float val = (float)Math.sin(alpha / 20.0D) / 100.0F;

      for(int i1 = 0; i1 < 4; ++i1) {
         for(int i = 0; i < 9; ++i) {
            drawImage((double)(-width / 2), (double)(-height / 2), par1, (double)width, (double)height);
            GL11.glRotated(64.0D, 0.0D, 1.0D, 0.0D);
         }

         GL11.glRotated(90.0D, 1.0D, 0.0D, 0.0D);
      }

      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawOutlinedBox(int x, int y, int z, float par4) {
      drawOutlinedBox((double)x, (double)y, (double)z, 255.0F, 255.0F, 255.0F, 1.0D, par4);
   }

   @SideOnly(Side.CLIENT)
   public static void drawOutlinedBox(double x, double y, double z, float f1, float f2, float f3, double boxsize, float particle) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      double posX = x + boxsize / 2.0D;
      double posY = y + boxsize / 2.0D;
      double posZ = z + boxsize / 2.0D;
      double min = -(boxsize / 2.0D);
      double max = boxsize / 2.0D;
      AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB(posX + min, posY + min, posZ + min, posX + max, posY + max, posZ + max);
      GL11.glPushMatrix();
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(f1, f2, f3, 0.4F);
      GL11.glLineWidth(5.0F);
      GL11.glDisable(3553);
      GL11.glDepthMask(false);
      double par4 = 0.98D;
      double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)particle;
      double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)particle;
      double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)particle;
      drawOutlinedBoundingBox(box.getOffsetBoundingBox(-d0, -d1, -d2));
      GL11.glDepthMask(true);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glPopMatrix();
   }

   @SideOnly(Side.CLIENT)
   protected static void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB) {
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawing(3);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      tessellator.draw();
      tessellator.startDrawing(3);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      tessellator.draw();
      tessellator.startDrawing(1);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
      tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
      tessellator.draw();
   }

   public static void triggerHitMarker() {
      ModVariablesClient.hmTimer = 20;
   }

   public static void drawPositionedImageInViewWithDepth(ResourceLocation par1, double par2, double par3, double par4, float par5, float width, float height, float givenAlpha) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      GL11.glPushMatrix();
      double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)par5;
      double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)par5;
      double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)par5;
      GL11.glTranslated(par2, par3, par4);
      GL11.glTranslated(-d0, -d1, -d2);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-player.rotationYaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(player.rotationPitch, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-0.03F, -0.03F, 0.03F);
      GL11.glDisable(2896);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3008);
      drawImageTransparent((double)(-width / 2.0F), (double)(-height / 2.0F), par1, (double)width, (double)height, (double)givenAlpha);
      GL11.glEnable(2896);
      GL11.glPopMatrix();
   }

   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String givenUUID) {
      TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
      Object object = texturemanager.getTexture(resourceLocationIn);
      if (object == null) {
         object = new ThreadDownloadImageData(String.format("https://crafatar.com/skins/%s.png", StringUtils.stripControlCodes(givenUUID)), AbstractClientPlayerCC.locationStevePng, new ImageBufferDownload());
         texturemanager.loadTexture(resourceLocationIn, (TextureObject)object);
      }

      return (ThreadDownloadImageData)object;
   }

   public static ThreadDownloadImageData getDownloadImageSkinGrayscale(ResourceLocation resourceLocationIn, String givenUUID) {
      TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
      Object object = texturemanager.getTexture(resourceLocationIn);
      if (object == null) {
         object = new ThreadDownloadImageData(String.format("https://crafatar.com/skins/%s.png", StringUtils.stripControlCodes(givenUUID)), AbstractClientPlayerCC.locationStevePng, new ImageBufferDownloadGray());
         texturemanager.loadTexture(resourceLocationIn, (TextureObject)object);
      }

      return (ThreadDownloadImageData)object;
   }

   public static boolean isInBox(int x, int y, int width, int height, int checkX, int checkY) {
      return checkX >= x && checkY >= y && checkX <= x + width && checkY <= y + height;
   }

   public static void renderShowcase(String givenTitle, String givenParagraph, int givenX, int givenY, int givenWidth, int givenHeight, CCRenderHelper.EnumShowcaseColor givenCaseColor, ItemStack givenItemStack, boolean isNew, float givenParticlaTicks) {
      drawImageTransparent((double)givenX, (double)givenY, new ResourceLocation("countercraft:textures/gui/showcase_fade1.png"), 1.0D, (double)givenHeight, 1.0D);
      drawImageTransparent((double)(givenX + givenWidth - 1), (double)givenY, new ResourceLocation("countercraft:textures/gui/showcase_fade1.png"), 1.0D, (double)givenHeight, 1.0D);
      drawImageTransparent((double)givenX, (double)givenY, new ResourceLocation("countercraft:textures/gui/showcase_fade2.png"), (double)givenWidth, 1.0D, 1.0D);
      drawImageTransparent((double)(givenX + 1), (double)(givenY + 1), new ResourceLocation("countercraft:textures/gui/showcase_" + givenCaseColor.getColorIndex() + ".png"), (double)(givenWidth - 2), (double)(givenHeight - 1), 0.800000011920929D);
      if (isNew) {
         drawRect((double)(givenX + givenWidth - 17), (double)(givenY + 1), 14.0D, 7.0D, "0x4488ff", 1.0F);
         renderTextScaled(EnumChatFormatting.WHITE + "NEW!", givenX + givenWidth - 16, givenY + 2, 0.6D);
      }

      renderTextScaled(EnumChatFormatting.WHITE + givenTitle, givenX + 4, givenY + 4, 0.8D);
      ArrayList desc = StringListHelperCC.getListLimitWidth(givenParagraph, givenWidth);

      for(int i = 0; i < desc.size(); ++i) {
         String var1 = (String)desc.get(i);
         renderTextScaled(EnumChatFormatting.WHITE + var1, givenX + 4, givenY + 13 + i * 5, 0.5D);
      }

      GL11.glPushMatrix();
      renderSpecialItemStackInventory(givenItemStack, (double)(givenX + givenWidth - 22), (double)(givenY + givenHeight - 8), 1.1D);
      GL11.glPopMatrix();
   }

   public static void setSaturation(float givenSaturation) {
      int color2 = Color.HSBtoRGB(1.0F, givenSaturation, 1.0F);
      float red = (float)(color2 >> 16 & 255) / 255.0F;
      float blue = (float)(color2 >> 8 & 255) / 255.0F;
      float green = (float)(color2 & 255) / 255.0F;
      float alpha = (float)(color2 >> 24 & 255) / 255.0F;
      GL11.glColor4f(red, blue, green, alpha);
   }

   public static enum EnumShowcaseColor {
      RED(0),
      BLUE(1),
      GREEN(2),
      ORANGE(3),
      YELLOW(4),
      NORMAL(5),
      ALPHA2(6);

      public int colorIndex = 0;

      private EnumShowcaseColor(int givenID) {
         this.colorIndex = givenID;
      }

      public int getColorIndex() {
         return this.colorIndex;
      }
   }
   
   public static void renderShowcaseRus(String givenTitle, String givenParagraph, int givenX, int givenY, int givenWidth, int givenHeight, CCRenderHelper.EnumShowcaseColor givenCaseColor, ItemStack givenItemStack, boolean isNew, float givenParticlaTicks, int xMoveStack, int yMoveStack, float scaleStack) {
	      drawImageTransparent((double)givenX, (double)givenY, new ResourceLocation("countercraft:textures/gui/showcase_fade1.png"), 1.0D, (double)givenHeight, 1.0D);
	      drawImageTransparent((double)(givenX + givenWidth - 1), (double)givenY, new ResourceLocation("countercraft:textures/gui/showcase_fade1.png"), 1.0D, (double)givenHeight, 1.0D);
	      drawImageTransparent((double)givenX, (double)givenY, new ResourceLocation("countercraft:textures/gui/showcase_fade2.png"), (double)givenWidth, 1.0D, 1.0D);
	      drawImageTransparent((double)(givenX + 1), (double)(givenY + 1), new ResourceLocation("countercraft:textures/gui/showcase_" + givenCaseColor.getColorIndex() + ".png"), (double)(givenWidth - 2), (double)(givenHeight - 1), 0.800000011920929D);
	      if (isNew) {
	         drawRect((double)(givenX + givenWidth - 17), (double)(givenY + 1), 14.0D, 7.0D, "0x4488ff", 1.0F);
	         renderTextScaled(EnumChatFormatting.WHITE + "NEW!", givenX + givenWidth - 16, givenY + 2, 0.6D);
	      }

	      renderTextScaled(EnumChatFormatting.WHITE + givenTitle, givenX + 4, givenY + 4, 0.8D);
	      ArrayList desc = StringListHelperCC.getListLimitWidthRus(givenParagraph, givenWidth);

	      for(int i = 0; i < desc.size(); ++i) {
	         String var1 = (String)desc.get(i);
	         renderTextScaled(EnumChatFormatting.WHITE + var1, givenX + 4, givenY + 13 + i * 5, 0.5D);
	      }

	      GL11.glPushMatrix();
	      GL11.glScalef(scaleStack, scaleStack, scaleStack);
	      renderSpecialItemStackInventory(givenItemStack, (double)(givenX + givenWidth - 22 + xMoveStack), (double)(givenY + givenHeight - 8 + yMoveStack), 1.1D);
	      GL11.glPopMatrix();
	   }
}
