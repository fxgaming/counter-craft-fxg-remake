package com.ferullogaming.countercraft.client.render;

import com.ferullogaming.countercraft.CCUtils;
import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.Crosshair;
import com.ferullogaming.countercraft.client.ModSettings;
import com.ferullogaming.countercraft.client.ModVariablesClient;
import com.ferullogaming.countercraft.client.RenderPlayerEvents;
import com.ferullogaming.countercraft.client.cloud.Booster;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuFindMatch;
import com.ferullogaming.countercraft.entity.EntityBomb;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.GameNotification;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.KillFeedMessage;
import com.ferullogaming.countercraft.item.ItemBomb;
import com.ferullogaming.countercraft.item.ItemBombKit;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkSparkFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.particle.EntityHugeExplodeFX;
import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class RenderTickHandler {
   public static final double RENDER_DISTANCE_PARTICLES = 128.0D;
   public static final double RENDER_DISTANCE_PLAYER = 128.0D;
   public static final double RENDER_DISTANCE_ITEMS = 96.0D;
   public static final double RENDER_DISTANCE_BOMB = 96.0D;
   public boolean renderCrosshairs = false;
   public ResourceLocation ccBlueIcon = new ResourceLocation("countercraft:textures/misc/ccblueicon.png");
   public ResourceLocation ccRedIcon = new ResourceLocation("countercraft:textures/misc/ccredicon.png");
   public ResourceLocation hitMarker = new ResourceLocation("countercraft:textures/gui/hitmarker.png");
   public RenderPlayerEvents renderPlayerEvents = new RenderPlayerEvents();
   public int fakePartialTick = 0;
   public int ammoLoaded;
   public int totalAmmo;
   public int playerHealth;
   public int timeSinceSneak = 0;
   protected float zLevel = 0.0F;
   private ResourceLocation icons = new ResourceLocation("countercraft:textures/gui/icon.png");
   private World theWorld;
   private Minecraft mc = Minecraft.getMinecraft();
   private float renderSpread = 0.0F;
   private float lastRenderSpread = 0.0F;

   public static void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
      if (FMLCommonHandler.instance().getSide() == Side.CLIENT && instance() != null && instance().theWorld != null) {
         instance().doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
      }

   }

   public static RenderTickHandler instance() {
      return CounterCraft.getClientManager().getTickHandler().getRenderer();
   }

   public void onRenderTick(Minecraft par1Minecraft, float par2) {
      ++this.fakePartialTick;
      this.mc = par1Minecraft;
      ScaledResolution scaledresolution = new ScaledResolution(par1Minecraft.gameSettings, par1Minecraft.displayWidth, par1Minecraft.displayHeight);
      int width = scaledresolution.getScaledWidth();
      int height = scaledresolution.getScaledHeight();
      int mwidth = width / 2;
      int mheight = height / 2;
      boolean renderNotification = true;
      boolean renderGameToolbar = false;
      boolean renderMCToolbar = true;
      if (!ClientManager.isGameLoading) {
         EntityPlayer player = par1Minecraft.thePlayer;
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)player);
         if (par1Minecraft.gameSettings.particleSetting != 0) {
            par1Minecraft.gameSettings.particleSetting = 0;
         }

         if (this.mc.theWorld != null && GameManager.instance().currentClientGame == null && GuiCCMenuFindMatch.isSearching) {
            CCRenderHelper.renderCenteredText(ClientTickHandler.getInstance().ingameSearchingText, mwidth, mheight / 2 * 3 - 5);
         }

         int boxX;
         if (playerData != null) {
            if (GameManager.instance() != null && GameManager.instance().getPlayerGame((EntityPlayer)player) != null) {
               IGame game = GameManager.instance().getPlayerGame((EntityPlayer)player);
               renderGameToolbar = game.getClientSide().allowCustomHUD();
               if (playerData.isAiming && (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemGun))) {
                  playerData.isAiming = false;
               }

               if (playerData != null && playerData.ghostViewing != null && PlayerDataHandler.getPlayerData(playerData.ghostViewing).isGhost && this.mc.gameSettings.hideGUI) {
                  this.mc.gameSettings.hideGUI = false;
               }

               if (!playerData.isAiming && this.mc.currentScreen == null && !Keyboard.isKeyDown(15)) {
                  game.getClientSide().onRenderHUD(par1Minecraft, width, height);
                  int kx = 5;
                  int ky = 5;

                  for(boxX = 0; boxX < ClientTickHandler.getInstance().killFeed.size(); ++boxX) {
                     KillFeedMessage km = (KillFeedMessage)ClientTickHandler.getInstance().killFeed.get(boxX);
                     km.doRender(kx, ky + boxX * 12, par2);
                  }

                  GameNotification notification = ClientTickHandler.getInstance().currentGameNotification;
                  if (notification != null) {
                     notification.doRender(par1Minecraft);
                  }
               }

               if ((player.isDead || playerData.isGhost) && ClientTickHandler.lastKilledMessage != null) {
                  ClientTickHandler.lastKilledMessage.doRender(this.mc, width, height);
               }

               if (Keyboard.isKeyDown(15)) {
                  game.getClientSide().onRenderTAB(par1Minecraft, width, height);
               }
            }

            if (playerData.isGhost) {
               renderGameToolbar = false;
               renderMCToolbar = false;
            }
         }

         this.theWorld = par1Minecraft.theWorld;
         if (par1Minecraft.theWorld != null) {
            ArrayList list = (ArrayList)par1Minecraft.theWorld.getLoadedEntityList();

            for(int i = 0; i < list.size(); ++i) {
               Entity entity = (Entity)list.get(i);
               if (entity instanceof EntityPlayer) {
                  entity.renderDistanceWeight = 2.0D;
               } else if (entity instanceof EntityItem) {
                  entity.renderDistanceWeight = 1.5D;
               } else if (entity instanceof EntityBomb) {
                  entity.renderDistanceWeight = 1.5D;
               }
            }
         }

         if (player != null && !player.isDead) {
            ItemStack itemstack = player.getCurrentEquippedItem();
            boolean renderDefaultCrosshairs = true;
            boolean flag1 = true;
            if (itemstack != null) {
               if (itemstack.getItem() instanceof ItemGun) {
                  this.renderCrosshairs = true;
                  ItemGun itemgun = (ItemGun)itemstack.getItem();
                  float newRenderSpread;
                  if (playerData.isAiming && par1Minecraft.currentScreen == null) {
                     flag1 = false;
                     if (itemgun.sightTexture != null) {
                        GL11.glPushMatrix();
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                        GL11.glScaled(1.0D, 1.0D, 0.0D);
                        GL11.glBlendFunc(770, 771);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        newRenderSpread = (float)(Math.sin((double)(this.fakePartialTick / 7)) * (double)(playerData.spread / 2.0F));
                        float val2 = (float)(Math.sin((double)(this.fakePartialTick / 5)) * (double)(playerData.spread / 2.0F));
                        par1Minecraft.renderEngine.bindTexture(itemgun.sightTexture);
                        Tessellator tessellator = Tessellator.instance;
                        tessellator.startDrawingQuads();
                        tessellator.addVertexWithUV((double)((float)(width / 2 - 2 * height) + val2), (double)((float)height + newRenderSpread), -90.0D, 0.0D, 1.0D);
                        tessellator.addVertexWithUV((double)((float)(width / 2 + 2 * height) + val2), (double)((float)height + newRenderSpread), -90.0D, 1.0D, 1.0D);
                        tessellator.addVertexWithUV((double)((float)(width / 2 + 2 * height) + val2), 0.0D + (double)newRenderSpread, -90.0D, 1.0D, 0.0D);
                        tessellator.addVertexWithUV((double)((float)(width / 2 - 2 * height) + val2), 0.0D + (double)newRenderSpread, -90.0D, 0.0D, 0.0D);
                        tessellator.draw();
                        CCRenderHelper.drawRect(0.0D, 0.0D, (double)width, 20.0D, "0x000000", 255.0F);
                        CCRenderHelper.drawRect(0.0D, (double)(height - 20), (double)width, 20.0D, "0x000000", 255.0F);
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                        GL11.glEnable(3008);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glPopMatrix();
                        this.renderCrosshairs = false;
                        renderDefaultCrosshairs = false;
                     }

                     newRenderSpread = ClientTickHandler.getInstance().lastZoomLevel + (ClientTickHandler.getInstance().zoomLevel - ClientTickHandler.getInstance().lastZoomLevel) * par2;
                     if (newRenderSpread > 1.0F) {
                        this.updateZoomLevels(par1Minecraft, newRenderSpread);
                     }
                  }

                  if (!itemgun.renderCrosshairs) {
                     this.renderCrosshairs = false;
                     renderDefaultCrosshairs = false;
                  }

                  if (this.renderCrosshairs && ModSettings.instance().getDefaultCrosshair() != null && par1Minecraft.currentScreen == null) {
                     renderDefaultCrosshairs = false;
                     this.lastRenderSpread = this.renderSpread;
                     this.renderSpread = playerData.getLastSpreadAverage() * 5.0F;
                     newRenderSpread = this.lastRenderSpread + (this.renderSpread - this.lastRenderSpread) * par2;
                     GL11.glPushMatrix();
                     GL11.glTranslated(-3.5D, -3.5D, 0.0D);
                     Crosshair crosshair = ModSettings.instance().getDefaultCrosshair();
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     GL11.glTranslated(4.0D, 4.0D, 0.0D);
                     CCRenderHelper.drawImage((double)(mwidth - 8), (double)(mheight - 8), crosshair.middle, 16.0D, 16.0D);
                     if (crosshair.isStatic) {
                        newRenderSpread = 0.0F;
                     }

                     CCRenderHelper.drawImage((double)(mwidth - 8), (double)((float)mheight - (14.0F + newRenderSpread)), crosshair.top, 16.0D, 16.0D);
                     CCRenderHelper.drawImage((double)(mwidth - 8), (double)((float)mheight - (2.0F - newRenderSpread)), crosshair.bottom, 16.0D, 16.0D);
                     CCRenderHelper.drawImage((double)((float)mwidth - (14.0F + newRenderSpread)), (double)(mheight - 8), crosshair.left, 16.0D, 16.0D);
                     CCRenderHelper.drawImage((double)((float)mwidth - (2.0F - newRenderSpread)), (double)(mheight - 8), crosshair.right, 16.0D, 16.0D);
                     GL11.glPopMatrix();
                  }

                  if (ModVariablesClient.hmTimer > 0) {
                     --ModVariablesClient.hmTimer;
                  } else if (ModVariablesClient.hmTimer < 0) {
                     ModVariablesClient.hmTimer = 0;
                  }
               }

               if (itemstack.getItem() instanceof ItemBomb) {
                  ItemBomb bomb = (ItemBomb)itemstack.getItem();
                  if (ItemBomb.plantTime > 0) {
                     CCRenderHelper.drawRect((double)(width / 2 - 70), (double)(height / 2 - 30), 140.0D, 32.0D, "0x000000", 0.5F);
                     CCRenderHelper.drawImage((double)(width / 2 - 70 - 16), (double)(height / 2 - 16 - 14), this.ccRedIcon, 32.0D, 32.0D);
                     CCRenderHelper.renderCenteredText("Planting " + CCUtils.getTickAsTime(ItemBomb.plantTime), width / 2, height / 2 - 30 + 7);
                     CCRenderHelper.drawRect((double)(width / 2 - 50), (double)(height / 2 - 10), 100.0D, 10.0D, "0x000000", 0.5F);
                     CCRenderHelper.drawRect((double)(width / 2 - 50), (double)(height / 2 - 10), (double)(100 * ItemBomb.plantTime / ItemBomb.plantTimeMax), 10.0D, "0xff0000", 0.5F);
                  }
               }

               if (itemstack.getItem() instanceof ItemBombKit) {
                  ItemBombKit bombKit = (ItemBombKit)itemstack.getItem();
                  if (ItemBombKit.defuseTime > 0) {
                     CCRenderHelper.drawRect((double)(width / 2 - 70), (double)(height / 2 - 30), 140.0D, 32.0D, "0x000000", 0.5F);
                     CCRenderHelper.drawImage((double)(width / 2 - 70 - 16), (double)(height / 2 - 16 - 14), this.ccBlueIcon, 32.0D, 32.0D);
                     CCRenderHelper.renderCenteredText("Defusing " + CCUtils.getTickAsTime(ItemBombKit.defuseTime), width / 2, height / 2 - 30 + 7);
                     CCRenderHelper.drawRect((double)(width / 2 - 50), (double)(height / 2 - 10), 100.0D, 10.0D, "0x000000", 0.5F);
                     CCRenderHelper.drawRect((double)(width / 2 - 50), (double)(height / 2 - 10), (double)(100 * ItemBombKit.defuseTime / ItemBombKit.defuseTimeMax), 10.0D, "0x0000ff", 0.5F);
                  }
               }
            }

            GuiIngameForge.renderHotbar = true;
            GuiIngameForge.renderHealth = true;
            GuiIngameForge.renderFood = true;
            GuiIngameForge.renderExperiance = true;
            GuiIngameForge.renderAir = true;
            GuiIngameForge.renderArmor = true;
            if (renderGameToolbar || !renderMCToolbar) {
               GuiIngameForge.renderHotbar = false;
               GuiIngameForge.renderHealth = false;
               GuiIngameForge.renderFood = false;
               GuiIngameForge.renderExperiance = false;
               GuiIngameForge.renderAir = false;
               GuiIngameForge.renderArmor = false;
            }

            if (renderGameToolbar && this.mc.currentScreen == null) {
               boxX = width - 115;
               int boxWidth = 110;
               int boxY = height - 144;
               int boxHeight = 30;
               int boxMarginY = 31;
               float boxAlpha = 0.4F;
               float boxAlpha1 = 0.8F;
               int curItem = player.inventory.currentItem;
               ItemStack itemstack1 = player.inventory.getStackInSlot(0);
               if (curItem == 0) {
                  CCRenderHelper.drawRect((double)(boxX + 1), (double)(boxY + 1), (double)(boxWidth - 2), (double)(boxHeight - 2), "0xffffff", boxAlpha1);
               }

               CCRenderHelper.drawRect((double)boxX, (double)boxY, (double)boxWidth, (double)boxHeight, "0x000000", boxAlpha);
               CCRenderHelper.renderText("1", boxX + 5, boxY + 5);
               if (itemstack1 != null) {
                  GL11.glPushMatrix();
                  GL11.glTranslated((double)(boxX + boxWidth), (double)(boxY + boxHeight / 2), 20.0D);
                  CCRenderHelper.renderSpecialItemStackHUD(itemstack1, 0.0D, 0.0D);
                  GL11.glPopMatrix();
               }

               itemstack1 = player.inventory.getStackInSlot(1);
               boxY += boxMarginY;
               if (curItem == 1) {
                  CCRenderHelper.drawRect((double)(boxX + 1), (double)(boxY + 1), (double)(boxWidth - 2), (double)(boxHeight - 2), "0xffffff", boxAlpha1);
               }

               CCRenderHelper.drawRect((double)boxX, (double)boxY, (double)boxWidth, (double)boxHeight, "0x000000", boxAlpha);
               CCRenderHelper.renderText("2", boxX + 5, boxY + 5);
               if (itemstack1 != null) {
                  GL11.glPushMatrix();
                  GL11.glTranslated((double)(boxX + boxWidth), (double)(boxY + boxHeight / 2), 20.0D);
                  CCRenderHelper.renderSpecialItemStackHUD(itemstack1, 0.0D, 0.0D);
                  GL11.glPopMatrix();
               }

               itemstack1 = player.inventory.getStackInSlot(2);
               boxY += boxMarginY;
               if (curItem == 2) {
                  CCRenderHelper.drawRect((double)(boxX + 1), (double)(boxY + 1), (double)(boxWidth - 2), (double)(boxHeight - 2), "0xffffff", boxAlpha1);
               }

               CCRenderHelper.drawRect((double)boxX, (double)boxY, (double)boxWidth, (double)boxHeight, "0x000000", boxAlpha);
               CCRenderHelper.renderText("3", boxX + 5, boxY + 5);
               if (itemstack1 != null) {
                  GL11.glPushMatrix();
                  GL11.glTranslated((double)(boxX + boxWidth), (double)(boxY + boxHeight / 2), 20.0D);
                  CCRenderHelper.renderSpecialItemStackHUD(itemstack1, 0.0D, 0.0D);
                  GL11.glPopMatrix();
               }

               boxY += boxMarginY;
               boxHeight = 25;
               boxWidth = 25;

               for(int i = 0; i < 3; ++i) {
                  itemstack1 = player.inventory.getStackInSlot(3 + i);
                  if (curItem == 3 + i) {
                     CCRenderHelper.drawRect((double)(boxX + 1), (double)(boxY + 1), (double)(boxWidth - 2), (double)(boxHeight - 2), "0xffffff", boxAlpha1);
                  }

                  CCRenderHelper.drawRect((double)boxX, (double)boxY, (double)boxWidth, (double)boxHeight, "0x000000", boxAlpha);
                  CCRenderHelper.renderText("" + (4 + i), boxX + 1, boxY + 1);
                  if (itemstack1 != null && itemstack1.getItem() instanceof ItemGrenade) {
                     GL11.glPushMatrix();
                     GL11.glTranslated((double)(boxX + boxWidth), (double)(boxY + boxHeight / 2), 20.0D);
                     double scale = 0.3D;
                     GL11.glScaled(scale, scale, scale);
                     CCRenderHelper.renderSpecialItemStackHUD(itemstack1, 0.0D, 0.0D);
                     GL11.glPopMatrix();
                  }

                  boxX += 28;
               }

               itemstack1 = player.inventory.getStackInSlot(6);
               if (itemstack1 != null) {
                  if (curItem == 6) {
                     CCRenderHelper.drawRect((double)(boxX + 1), (double)(boxY + 1), (double)(boxWidth - 2), (double)(boxHeight - 2), "0xffffff", boxAlpha1);
                  }

                  CCRenderHelper.drawRect((double)boxX, (double)boxY, (double)boxWidth, (double)boxHeight, "0x000000", boxAlpha);
                  CCRenderHelper.renderText("7", boxX + 1, boxY + 1);
                  if (itemstack1.getItem() instanceof ItemBomb || itemstack1.getItem() instanceof ItemBombKit) {
                     GL11.glPushMatrix();
                     GL11.glTranslated((double)(boxX + boxWidth), (double)(boxY + boxHeight / 2), 20.0D);
                     double scale = 0.3D;
                     GL11.glScaled(scale, scale, scale);
                     CCRenderHelper.renderSpecialItemStackHUD(itemstack1, 0.0D, 0.0D);
                     GL11.glPopMatrix();
                  }
               }

               CCRenderHelper.drawRect((double)(width - 125), (double)(height - 25), 100.0D, 20.0D, "0x000000", boxAlpha);
               itemstack1 = player.inventory.getCurrentItem();
               if (itemstack1 != null && itemstack1.getItem() instanceof ItemGun) {
                  String firemode = ((ItemGun)itemstack1.getItem()).firemode.displayName;
                  CCRenderHelper.renderText(EnumChatFormatting.WHITE + "" + this.ammoLoaded + " / " + this.totalAmmo, width - 105, height - 18);
                  CCRenderHelper.renderText("" + firemode, width - 50, height - 18);
               }

               CCRenderHelper.drawRect(5.0D, (double)(height - 25), 110.0D, 20.0D, "0x000000", boxAlpha);
               CCRenderHelper.drawImage(5.0D, (double)(height - 25), new ResourceLocation("countercraft:textures/gui/health.png"), 20.0D, 20.0D);
               CCRenderHelper.renderText("" + this.playerHealth, 28, height - 18);
               CCRenderHelper.drawRect(45.0D, (double)(height - 20), 65.0D, 10.0D, "0x000000", boxAlpha);
               CCRenderHelper.drawRect(45.0D, (double)(height - 20), (double)(65 * this.playerHealth / 20), 10.0D, "0xffffff", 0.8F);
               if (playerData.wearingHelmet || playerData.wearingKevlar) {
                  CCRenderHelper.drawRect(117.0D, (double)(height - 25), 40.0D, 20.0D, "0x000000", boxAlpha);
               }

               if (playerData.wearingHelmet) {
                  CCRenderHelper.renderTextScaled(EnumChatFormatting.WHITE + "H " + EnumChatFormatting.RESET + playerData.getHelmetHealth() + "/" + playerData.helmetMaxHealth, 119, height - 23, 1.0D);
               }

               if (playerData.wearingKevlar) {
                  CCRenderHelper.renderTextScaled(EnumChatFormatting.WHITE + "K " + EnumChatFormatting.RESET + playerData.getKevlarHealth() + "/" + playerData.kevlarMaxHealth, 119, height - 14, 1.0D);
               }

               int i;
               if (GameManager.instance().getPlayerGame((EntityPlayer)player) != null) {
                  IGame game = GameManager.instance().getPlayerGame((EntityPlayer)player);
                  i = game.getPlayerGameData(player.username).getInteger("killsCL");
                  if (i > 0) {
                     if (i <= 5) {
                        for(int i1 = 0; i1 < i1; ++i1) {
                           CCRenderHelper.drawImage((double)(width - 129 - i1 * 11), (double)(height - 24), new ResourceLocation("countercraft:textures/gui/kill.png"), 14.0D, 14.0D);
                        }
                     } else {
                        CCRenderHelper.drawImage((double)(width - 129 - 20), (double)(height - 24), new ResourceLocation("countercraft:textures/gui/kill.png"), 14.0D, 14.0D);
                        CCRenderHelper.renderText("x" + i, width - 132, height - 21);
                     }
                  }
               }

               PlayerDataCloud clouddata = PlayerDataHandler.getPlayerCloudData(player.username);
               if (clouddata.boostersActive.size() > 0) {
                  for(i = 0; i < clouddata.boostersActive.size(); ++i) {
                     Booster boost = (Booster)clouddata.boostersActive.get(i);
                     String booster = EnumChatFormatting.BOLD + "x" + boost.multiplier + "";
                     if (boost.isEXP()) {
                        booster = EnumChatFormatting.GOLD + booster + " EXP";
                     }

                     if (boost.isEmeralds()) {
                        booster = EnumChatFormatting.GREEN + booster + " Emeralds";
                     }

                     CCRenderHelper.renderCenteredTextScaled(booster + " Active", mwidth, height - 34 + i * 9, 1.0D);
                  }
               }
            }

            if (flag1) {
               this.updateZoomLevels(par1Minecraft, 1.0F);
            }

            GuiIngameForge.renderCrosshairs = renderDefaultCrosshairs;
            float var1 = (float)playerData.flashTime * 1.0F / 6.0F;
            if (var1 >= 0.1F) {
               CCRenderHelper.drawRect(0.0D, 0.0D, (double)width, (double)height, "0xffffff", var1);
            }
         }

         ClientNotification clietNotification = CounterCraft.getClientManager().getTickHandler().currentClientNotification;
         if (clietNotification != null && renderNotification) {
            clietNotification.doRender(par1Minecraft);
         }

      }
   }

   private EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12) {
      if (this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null) {
         double d6 = this.mc.renderViewEntity.posX - par2;
         double d7 = this.mc.renderViewEntity.posY - par4;
         double d8 = this.mc.renderViewEntity.posZ - par6;
         EntityFX entityfx = null;
         if (par1Str.equals("hugeexplosion")) {
            this.mc.effectRenderer.addEffect((EntityFX)(entityfx = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12)));
         } else if (par1Str.equals("largeexplode")) {
            this.mc.effectRenderer.addEffect((EntityFX)(entityfx = new EntityLargeExplodeFX(this.mc.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12)));
         }

         if (entityfx != null) {
            return (EntityFX)entityfx;
         } else {
            double d9 = 128.0D;
            if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9) {
               return null;
            } else {
               if (par1Str.equals("smoke")) {
                  entityfx = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if (par1Str.equals("explode")) {
                  entityfx = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if (par1Str.equals("flame")) {
                  entityfx = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if (par1Str.equals("largesmoke")) {
                  entityfx = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5F);
               } else if (par1Str.equals("cloud")) {
                  entityfx = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
               } else if (par1Str.equals("reddust")) {
                  entityfx = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
               } else if (par1Str.equals("fireworksSpark")) {
                  this.mc.effectRenderer.addEffect((EntityFX)(entityfx = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer)));
               } else {
                  int j;
                  String[] astring;
                  int k;
                  if (par1Str.startsWith("iconcrack_")) {
                     astring = par1Str.split("_", 3);
                     j = Integer.parseInt(astring[1]);
                     if (astring.length > 2) {
                        k = Integer.parseInt(astring[2]);
                        entityfx = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[j], k);
                     } else {
                        entityfx = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[j], 0);
                     }
                  } else if (par1Str.startsWith("tilecrack_")) {
                     astring = par1Str.split("_", 3);
                     j = Integer.parseInt(astring[1]);
                     k = Integer.parseInt(astring[2]);
                     entityfx = (new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[j], k)).applyRenderColor(k);
                  }
               }

               if (entityfx != null) {
                  this.mc.effectRenderer.addEffect((EntityFX)entityfx);
               }

               return (EntityFX)entityfx;
            }
         }
      } else {
         return null;
      }
   }

   public void updateZoomLevels(Minecraft mc, float par2) {
      try {
         ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, (double)par2, new String[]{"cameraZoom", "Y", "field_78503_V"});
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   public void drawCrosshairIcon(Minecraft mc, double par1, double par2, int var3, int var4, int imageSize, double scale) {
      int var5 = var3 * imageSize;
      int var6 = var4 * imageSize;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      CCRenderHelper.renderCrosshairColor();
      GL11.glScaled(scale, scale, scale);
      GL11.glTranslated(par1 * (1.0D / scale), par2 * (1.0D / scale), 0.0D);
      mc.getTextureManager().bindTexture(this.icons);
      this.drawTexturedModalRect(imageSize / 2, imageSize / 2, var5, var6, imageSize, imageSize);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
      tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
      tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
      tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
      tessellator.draw();
   }
}
