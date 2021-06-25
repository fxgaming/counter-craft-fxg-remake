package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.anim.AnimationManager;
import com.ferullogaming.countercraft.client.anim.GunAnimationPulled;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.events.ClientEvents;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuFindMatch;
import com.ferullogaming.countercraft.client.gui.game.GuiCCIngameMenu;
import com.ferullogaming.countercraft.client.gui.game.GuiWSMain;
import com.ferullogaming.countercraft.client.gui.game.GuiWeaponSelection;
import com.ferullogaming.countercraft.client.gui.market.GuiCCMenuMarketMyListings;
import com.ferullogaming.countercraft.client.render.RenderTickHandler;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.GameNotification;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.IGameClient;
import com.ferullogaming.countercraft.game.KillFeedMessage;
import com.ferullogaming.countercraft.game.KilledMessage;
import com.ferullogaming.countercraft.item.IItemMovementPenalty;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.network.CCPacketVoteNo;
import com.ferullogaming.countercraft.network.CCPacketVoteYes;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class ClientTickHandler implements ITickHandler {
   public static final float spreadSprinting = 0.7F;
   public static final float spreadJumping = 1.7F;
   public static final float spreadWalking = 0.25F;
   public static final float spreadWalkingMax = 2.5F;
   public static final float decreasing = 0.5F;
   public static final float decreasingSneaking = 0.8F;
   public static boolean hasSwitched = false;
   public static double muzzleTick = 0.0D;
   public static KilledMessage lastKilledMessage = null;
   public static int lastKilledMessageTimer = 0;
   public static int tradeDelay = 0;
   public static int firstWorldTick = 5;
   public static boolean gameActionKey;
   public static boolean lastGameActionKey;
   public static int gameActionDelay;
   public static boolean hasTexturepacks;
   public float gunRecoil = 0.0F;
   public float pitchU;
   public float yawR;
   public float yawL;
   public float zoomLevel = 0.0F;
   public float lastZoomLevel = 0.0F;
   public boolean autoRespawn = false;
   public int autoRespawnTick = 0;
   public ArrayList gameNotificationList = new ArrayList();
   public GameNotification currentGameNotification = null;
   public ArrayList clientNotificationList = new ArrayList();
   public ClientNotification currentClientNotification = null;
   public ArrayList killFeed = new ArrayList();
   public boolean isInGameServer = false;
   public boolean lastInGameServer = false;
   public String ingameSearchingText = "";
   public int lastInvSlot = 0;
   private Minecraft mc = Minecraft.getMinecraft();
   private int scannerDelay = 0;
   private RenderTickHandler renderHandler;
   private AnimationManager animationHandler;
   private TickChecker tickChecker;
   private int allowMods = 85;
   private float lastSens = 1.0F;
   private boolean isSens = false;
   private boolean isSensLast = false;
   private int ingameSearchTextDelay = 0;

   public ClientTickHandler() {
      TickRegistry.registerTickHandler(this, Side.CLIENT);
      this.renderHandler = new RenderTickHandler();
      this.animationHandler = new AnimationManager();
      this.tickChecker = new TickChecker();
      this.allowMods -= 80;
   }

   public static void addClientNotification(ClientNotification par1) {
      if (getInstance().clientNotificationList != null && par1 != null) {
         getInstance().clientNotificationList.add(par1);
      }

   }

   public static void addGameNotification(GameNotification par1) {
      if (par1 != null) {
         getInstance().gameNotificationList.add(par1);
      }

   }

   public static void addKillFeedMessage(KillFeedMessage par1) {
      if (par1 != null) {
         getInstance().killFeed.add(par1);
         if (getInstance().killFeed.size() > 5) {
            getInstance().killFeed.remove(0);
         }
      }

   }

   public static ClientTickHandler getInstance() {
      return CounterCraft.getClientManager().getTickHandler();
   }

   public void tickStart(EnumSet type, Object... tickData) {
   }

   public void tickEnd(EnumSet type, Object... tickData) {
      Minecraft mc = Minecraft.getMinecraft();
      if (type.equals(EnumSet.of(TickType.RENDER))) {
         this.renderHandler.onRenderTick(mc, ((Float)tickData[0]).floatValue());
      } else if (type.equals(EnumSet.of(TickType.CLIENT))) {
         GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
         if (guiscreen != null) {
            this.onTickInGUI(mc, guiscreen);
         } else {
            this.onTickInGame(mc);
         }

         this.onClientTick(mc);
      }

   }

   private void onClientTick(Minecraft mc) {
      if (muzzleTick >= 1.0D) {
         muzzleTick -= 0.7D;
      }

      if (muzzleTick <= 0.0D) {
         muzzleTick = 0.0D;
      }

      if (GuiCCIngameMenu.switchTeamCooldown > 0) {
         --GuiCCIngameMenu.switchTeamCooldown;
      }

      if (ClientEvents.chatSpawmDelay > 0) {
         --ClientEvents.chatSpawmDelay;
      }

      if (mc.currentScreen != null && mc.theWorld == null && (mc.currentScreen instanceof GuiCCMenu || mc.currentScreen instanceof GuiOptions)) {
         SoundHandler.playMenuMusic();
      }

      if (lastKilledMessage != null && lastKilledMessageTimer > 0) {
         --lastKilledMessageTimer;
         if (lastKilledMessageTimer == 0) {
            lastKilledMessage = null;
         }
      }

      boolean leave = false;
      if (this.scannerDelay++ > 80) {
         this.scannerDelay = 0;
      }

      if (!mc.getResourcePackRepository().getResourcePackName().equalsIgnoreCase("default")) {
         leave = true;
      }

      PlayerDataCloud clouddata = PlayerDataHandler.getPlayerCloudData(mc.getSession().getUsername());
      this.animationHandler.onUpdate(mc);
      CounterCraft.getClientManager().getCloudManager().onUpdate();
      CounterCraft.getClientManager().getFriendManager().onUpdate();
      if (leave && mc.theWorld != null && !mc.isSingleplayer()) {
         mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
         mc.theWorld.sendQuittingDisconnectingPacket();
         mc.loadWorld((WorldClient)null);
      }

      if (mc.theWorld == null && GameManager.instance().currentClientGame != null) {
         GameManager.instance().currentClientGame = null;
      }

      if (GuiCCMenuMarketMyListings.resyncListings > 0) {
         --GuiCCMenuMarketMyListings.resyncListings;
      }

      if (GuiCCMenuMarketMyListings.resyncListingsDisplay > 0) {
         --GuiCCMenuMarketMyListings.resyncListingsDisplay;
      }

      lastGameActionKey = gameActionKey;
      gameActionKey = Keyboard.isKeyDown(48);
      if (tradeDelay > 0) {
         --tradeDelay;
      }

      PlayerData data;
      if (mc.theWorld != null) {
         SoundHandler.stopMenuMusic();
         if (firstWorldTick-- > 0) {
            ClientManager.instance().getCloudManager().onMMServerJoined();
         }

         if (GameManager.instance().currentClientGame != null) {
            GameManager.instance().currentClientGame.getClientSide().onClientUpdate(mc);
         }
      } else {
         firstWorldTick = 5;
         GameManager.instance().currentClientGame = null;
         GameManager.instance().serverOnUUID = null;
         data = PlayerDataHandler.getClientPlayerData();
         data.isFrozen = false;
         data.flashTime = 0.0D;
         data.isAiming = false;
      }

      if (this.currentGameNotification == null) {
         if (this.gameNotificationList.size() > 0) {
            this.currentGameNotification = (GameNotification)this.gameNotificationList.get(0);
            this.gameNotificationList.remove(0);
         }
      } else {
         this.currentGameNotification.onUpdate();
         if (this.currentGameNotification.displayTime <= 0.0D) {
            this.currentGameNotification = null;
         }
      }

      if (!ClientManager.isGameLoading) {
         if (this.currentClientNotification == null) {
            if (this.clientNotificationList.size() > 0) {
               this.currentClientNotification = (ClientNotification)this.clientNotificationList.get(0);
               this.clientNotificationList.remove(0);
            }
         } else {
            this.currentClientNotification.onUpdate();
            if (this.currentClientNotification.displayTime <= 0.0D) {
               this.currentClientNotification = null;
            }
         }
      }

      if (mc.thePlayer != null) {
         data = PlayerDataHandler.getPlayerData((EntityPlayer)mc.thePlayer);
         ItemStack itemstack = mc.thePlayer.getCurrentEquippedItem();
         if (mc.thePlayer.getFoodStats() != null) {
            mc.thePlayer.getFoodStats().setFoodLevel(20);
         }

         IGame game;
         if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (!data.isGhost) {
               data.ghostViewing = null;
            } else if (data.ghostViewing == null && data.ghostDelay == 0) {
               game = GameManager.instance().currentClientGame;
               if (game != null && (game.getGameType().equals("cbd") || game.getGameType().equals("cas")) && data.ghostDelay <= 1) {
                  data.ghostViewing = game.getClientSide().getNextSpectatingUsername(0, data.username);
               } else {
                  data.ghostViewing = null;
               }
            }
         }

         if (data.muzzleTimer > 0) {
            --data.muzzleTimer;
         }

         int width;
         if (GameManager.instance().getPlayerGame((EntityPlayer)mc.thePlayer) != null) {
            game = GameManager.instance().getPlayerGame((EntityPlayer)mc.thePlayer);
            long mapTime = 5000L;
            switch(game.getMapTime()) {
            case DAY:
               mapTime = 5000L;
               break;
            case NIGHT:
               mapTime = 15000L;
            }

            for(width = 0; width < this.killFeed.size(); ++width) {
               KillFeedMessage km = (KillFeedMessage)this.killFeed.get(width);
               km.onUpdate();
               if (km.life <= 0) {
                  this.killFeed.remove(width);
               }
            }
         } else if (this.killFeed.size() > 0) {
            this.killFeed.clear();
         }

         if (gameActionDelay > 0) {
            --gameActionDelay;
         }

         if ((mc.currentScreen == null || mc.currentScreen instanceof GuiWeaponSelection) && gameActionKey && !lastGameActionKey && gameActionDelay <= 0) {
            if (GameManager.instance().getPlayerGame((EntityPlayer)mc.thePlayer) != null) {
               GameManager.instance().getPlayerGame((EntityPlayer)mc.thePlayer).getClientSide().onKeyPressed(48);
            } else {
               mc.displayGuiScreen(new GuiWSMain((GuiScreen)null, (String)null));
            }
         }
         
         if (GuiCCMenuFindMatch.isSearching && !(mc.currentScreen instanceof GuiCCMenuFindMatch)) {
        	 GuiCCMenuFindMatch.isSearching = false;
         }

         this.isSens = false;
         if (itemstack != null) {
            if (itemstack.getItem() instanceof ItemGun) {
               ItemGun itemGun = (ItemGun)itemstack.getItem();
               if (itemGun.isAimAloud && data.isAiming) {
                  mc.thePlayer.motionX *= 0.4D;
                  mc.thePlayer.motionZ *= 0.4D;
                  this.isSens = true;
               }
            }

            if (itemstack.getItem() instanceof IItemMovementPenalty) {
               IItemMovementPenalty itemMovement = (IItemMovementPenalty)itemstack.getItem();
               double var1 = itemMovement.getMovementPenalty(itemstack);
               double var2 = 1.0D - var1;
               if (var1 > 0.0D && itemMovement.isMovementAffected(mc.thePlayer, itemstack)) {
                  if (mc.thePlayer.onGround) {
                     mc.thePlayer.motionX *= var2;
                     mc.thePlayer.motionZ *= var2;
                  } else {
                     mc.thePlayer.motionX *= 1.0D - var1 / 3.0D;
                     mc.thePlayer.motionZ *= 1.0D - var1 / 3.0D;
                  }
               }
            }
         }

         if (this.isSensLast != this.isSens) {
            if (this.isSens) {
               this.lastSens = mc.gameSettings.mouseSensitivity;
               mc.gameSettings.mouseSensitivity *= 0.65F;
            } else {
               mc.gameSettings.mouseSensitivity = this.lastSens;
            }

            this.isSensLast = this.isSens;
         }

         if (data.isFrozen) {
            mc.thePlayer.motionX *= 0.0D;
            mc.thePlayer.motionY *= 0.0D;
            mc.thePlayer.motionZ *= 0.0D;
            mc.thePlayer.posX = mc.thePlayer.lastTickPosX;
            mc.thePlayer.posZ = mc.thePlayer.lastTickPosZ;
         }

         if (data.isGhost) {
            if (data.isSpectating() && mc.theWorld.getPlayerEntityByName(data.ghostViewing) != null) {
               EntityPlayer playerViewing = mc.theWorld.getPlayerEntityByName(data.ghostViewing);
               PlayerData data1 = PlayerDataHandler.getPlayerData(playerViewing);
               ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
               width = scaledresolution.getScaledWidth();
               int height = scaledresolution.getScaledHeight();
               int mwidth = width / 2;
               int mheight = height / 2;
               if (!mc.renderViewEntity.equals(playerViewing)) {
                  mc.renderViewEntity = playerViewing;
               }

               mc.thePlayer.setPositionAndUpdate(playerViewing.posX, playerViewing.posY + 4.0D, playerViewing.posZ);
            } else {
               mc.thePlayer.motionX *= 0.0D;
               mc.thePlayer.motionY *= 0.0D;
               mc.thePlayer.motionZ *= 0.0D;
               mc.thePlayer.posX = mc.thePlayer.lastTickPosX;
               mc.thePlayer.posZ = mc.thePlayer.lastTickPosZ;
               if (mc.renderViewEntity != mc.thePlayer) {
                  mc.renderViewEntity = mc.thePlayer;
               }
            }
         } else if (mc.renderViewEntity != mc.thePlayer) {
            mc.renderViewEntity = mc.thePlayer;
         }

         if (mc.thePlayer.isDead && data.ghostViewing == null) {
            data.isAiming = false;
            data.flashTime = 0.0D;
         }

         if (data.flashTime > 0.0D) {
            data.flashTime -= 0.10000000149011612D;
         }

         if (itemstack == null) {
            data.isAiming = false;
         }
      } else {
         gameActionDelay = 40;
      }

      if (this.autoRespawn && this.autoRespawnTick++ >= 60 && mc.thePlayer != null && (double)mc.thePlayer.getHealth() <= 0.0D) {
         Minecraft.getMinecraft().setDimensionAndSpawnPlayer(0);
         this.mc.thePlayer.respawnPlayer();
         this.mc.displayGuiScreen(new GuiChat());
         this.autoRespawn = false;
         this.autoRespawnTick = 0;
      }

   }

   private void onTickInGame(Minecraft mc) {
      this.tickChecker.onClientUpdateTick(mc);
      if (mc.gameSettings.thirdPersonView != 0) {
         mc.gameSettings.thirdPersonView = 0;
      }

      if (mc.thePlayer != null && !mc.thePlayer.isDead && mc.theWorld.isRemote) {
         EntityPlayer player = Minecraft.getMinecraft().thePlayer;
         this.renderHandler.playerHealth = (int)player.getHealth();
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)player);
         IGame game = GameManager.instance().currentClientGame;
         if (Minecraft.getMinecraft().gameSettings.hideGUI && playerData.isFlashed()) {
            Minecraft.getMinecraft().gameSettings.hideGUI = false;
         }

         if (RenderManager.field_85095_o) {
            RenderManager.field_85095_o = !RenderManager.field_85095_o;
         }

         if (!player.isSprinting() && Keyboard.isKeyDown(KeyBindingManager.keySprint.keyCode)) {
            player.setSprinting(true);
         }

         if (Keyboard.isKeyDown(KeyBindingManager.keyVoteYes.keyCode) && !playerData.hasVoted) {
            PacketDispatcher.sendPacketToServer(CCPacketVoteYes.buildPacket());
         }

         if (Keyboard.isKeyDown(KeyBindingManager.keyVoteNo.keyCode) && !playerData.hasVoted) {
            PacketDispatcher.sendPacketToServer(CCPacketVoteNo.buildPacket());
         }

         if (Keyboard.isKeyDown(KeyBindingManager.keyNextSpectate.keyCode) && game != null && playerData != null && playerData.isGhost && (game.getGameType().equals("cbd") || game.getGameType().equals("cas")) && playerData.ghostDelay <= 20 && !hasSwitched) {
            hasSwitched = true;
            playerData.ghostViewing = game.getClientSide().getNextSpectatingUsername(0, playerData.username);
         }

         if (Keyboard.isKeyDown(KeyBindingManager.keyPreviousSpectate.keyCode) && game != null && playerData != null && playerData.isGhost && (game.getGameType().equals("cbd") || game.getGameType().equals("cas")) && playerData.ghostDelay <= 20 && !hasSwitched) {
            hasSwitched = true;
            playerData.ghostViewing = game.getClientSide().getNextSpectatingUsername(1, playerData.username);
         }

         if (!Keyboard.isKeyDown(KeyBindingManager.keyPreviousSpectate.keyCode) && !Keyboard.isKeyDown(KeyBindingManager.keyNextSpectate.keyCode)) {
            hasSwitched = false;
         }

         if (playerData.isAiming) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getItem() instanceof ItemGun) {
               ItemGun itemgun = (ItemGun)stack.getItem();
               if (this.zoomLevel < itemgun.zoomLevel) {
                  this.lastZoomLevel = this.zoomLevel + 1.2F;
                  ++this.zoomLevel;
               }
            }
         } else {
            this.lastZoomLevel = 1.0F;
            this.zoomLevel = 1.0F;
         }

         if (GameManager.instance().isPlayerInGame(player)) {
            IGameClient gameClient = GameManager.instance().getPlayerGame((EntityPlayer)player).getClientSide();
            if (Keyboard.isKeyDown(21) && mc.currentScreen == null) {
               GuiChat chat = new GuiChat("[Team] ");
               mc.displayGuiScreen(chat);
            }

            if (mc.inGameHasFocus && this.hasItems() && gameClient.allowCustomScroll()) {
               int cur = player.inventory.currentItem;
               int var1;
               if (this.lastInvSlot != cur) {
                  if (player.inventory.getStackInSlot(cur) == null) {
                     if (this.lastInvSlot == 8) {
                        for(var1 = 0; var1 < 8 && player.inventory.getStackInSlot(var1) == null; ++var1) {
                           ;
                        }

                        player.inventory.currentItem = var1;
                     } else if (this.lastInvSlot == 0) {
                        for(var1 = 0; var1 < 8 && player.inventory.getStackInSlot(8 - var1) == null; ++var1) {
                           ;
                        }

                        player.inventory.currentItem = 8 - var1;
                     } else if (this.lastInvSlot < cur) {
                        for(var1 = cur; var1 < 8 && player.inventory.getStackInSlot(var1) == null; ++var1) {
                           ;
                        }

                        player.inventory.currentItem = var1 >= 8 ? 0 : var1;
                     } else if (this.lastInvSlot > cur) {
                        var1 = cur;

                        while(var1 >= 0 && player.inventory.getStackInSlot(var1) == null) {
                           --var1;
                           if (var1 == -1 && player.inventory.getStackInSlot(0) == null) {
                              var1 = 8;
                           }
                        }

                        player.inventory.currentItem = var1 < 0 ? 8 : var1;
                     }
                  }

                  AnimationManager.instance().cancelCurrentAnimation();
                  if (player.getCurrentEquippedItem() != null && player.inventory.getStackInSlot(this.lastInvSlot) != null) {
                     AnimationManager.instance().setNextGunAnimation(new GunAnimationPulled());
                     Minecraft.getMinecraft().sndManager.playSoundFX("countercraft:gunPulled", 0.8F, 1.0F);
                  }
               }

               this.lastInvSlot = cur;
               if (player.getCurrentEquippedItem() == null) {
                  for(var1 = player.inventory.currentItem; var1 < 8 && player.inventory.getStackInSlot(var1) == null; ++var1) {
                     ;
                  }

                  player.inventory.currentItem = var1 >= 8 ? 0 : var1;
               }
            }
         }

         double playerMotion = (Math.abs(player.motionX * player.motionX) + Math.abs(player.motionZ * player.motionZ)) / 2.0D * 10000.0D;
         playerMotion = (double)((int)playerMotion);
         double playerMotionMaxWalking = 69.0D;
         double playerMotionMaxRunning = 117.0D;
         float si1 = 0.25F;
         float si1m = 2.5F;
         float si2 = 0.7F;
         float si2m = 12.0F;
         float si3 = 1.7F;
         float sd1 = 0.5F;
         float sd2 = 0.8F;
         ItemStack stack = player.getCurrentEquippedItem();
         if (stack != null && stack.getItem() instanceof ItemGun) {
            ItemGun itemgun = (ItemGun)stack.getItem();
            this.renderHandler.ammoLoaded = ItemGun.getLoadedAmmo(stack);
            this.renderHandler.totalAmmo = ItemGun.getAmmo(stack);
            if (itemgun.gunSpreadIncreaseWalking != -1.0F) {
               si1 = itemgun.gunSpreadIncreaseWalking;
            }

            if (itemgun.gunSpreadMaxWalking != -1.0F) {
               si1m = itemgun.gunSpreadMaxWalking;
            }

            if (itemgun.gunSpreadIncreaseRunning != -1.0F) {
               si2 = itemgun.gunSpreadIncreaseRunning;
            }

            if (itemgun.gunSpreadMaxRunning != -1.0F) {
               si2m = itemgun.gunSpreadMaxRunning;
            }

            if (itemgun.gunSpreadIncreaseJumping != -1.0F) {
               si3 = itemgun.gunSpreadIncreaseJumping;
            }

            if (itemgun.gunSpreadDecrease != -1.0F) {
               sd1 = itemgun.gunSpreadDecrease;
            }

            if (itemgun.gunSpreadDecreaseShift != -1.0F) {
               sd2 = itemgun.gunSpreadDecreaseShift;
            }

            if (itemgun.getMovementPenalty(stack) > 0.0D) {
               double perc = 100.0D * itemgun.getMovementPenalty(stack) * 1.97D;
               playerMotionMaxWalking = (double)((int)(playerMotionMaxWalking - playerMotionMaxWalking * perc / 100.0D));
            }
         }

         if (player.isSprinting()) {
            playerData.addSpread(si2);
            if (playerData.spread > si2m) {
               playerData.setSpread(si2m);
            }
         } else if ((player.onGround || player.fallDistance < 0.6F) && player.motionY < 0.0D) {
            if (player.isOnLadder()) {
               if (player.motionY >= 0.1D || player.motionY <= -0.1D) {
                  playerData.addSpread(si3);
               }
            } else if (playerMotion > 20.0D) {
               double perc = (double)((int)(playerMotion * 100.0D / playerMotionMaxWalking));
               float si1p = (float)(perc * (double)si1 / 100.0D);
               if (playerData.spread != si1m) {
                  if (playerData.spread < si1m) {
                     playerData.addSpread(si1p);
                  } else {
                     playerData.decreaseSpread(sd1);
                     if (playerData.spread < si1m) {
                        playerData.setSpread(si1m);
                     }
                  }
               }
            } else if (playerData.spread >= 0.1F) {
               if (player.isSneaking() && player.onGround) {
                  playerData.decreaseSpread(sd2);
               } else {
                  playerData.decreaseSpread(sd1);
               }
            }
         } else {
            playerData.addSpread(si3);
         }
      }

      float var1 = 0.15F;
      Random rand = new Random();
      if (this.gunRecoil > 0.0F) {
         float f1 = this.gunRecoil + (0.5F - rand.nextFloat());
         switch(rand.nextInt(3)) {
         case 0:
            this.pitchU += f1;
            break;
         case 1:
            this.yawL += f1 / 3.0F;
            break;
         case 2:
            this.yawR += f1 / 3.0F;
         }

         this.pitchU += f1 * 0.5F;
         this.gunRecoil = 0.0F;
      }

      if (this.pitchU > 0.0F) {
         this.pitchU *= var1;
      }

      mc.thePlayer.rotationPitch -= this.pitchU;
      if (this.yawR > 0.0F) {
         this.yawR *= var1;
      }

      mc.thePlayer.rotationYaw += this.yawR;
      if (this.yawL > 0.0F) {
         this.yawL *= var1;
      }

      mc.thePlayer.rotationYaw -= this.yawL;
   }

   public boolean hasItems() {
      for(int i = 0; i < this.mc.thePlayer.inventory.mainInventory.length; ++i) {
         if (this.mc.thePlayer.inventory.mainInventory[i] != null) {
            return true;
         }
      }

      return false;
   }

   private void onTickInGUI(Minecraft mc, GuiScreen guiscreen) {
      if (mc.theWorld != null && GameManager.instance().isPlayerInGame(mc.thePlayer) && guiscreen instanceof GuiInventory) {
         mc.displayGuiScreen((GuiScreen)null);
      }

   }

   public void applyRecoil(float par1) {
      Minecraft mc = Minecraft.getMinecraft();
      ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
      if (itemstack != null && itemstack.getItem() instanceof ItemGun) {
         this.gunRecoil = par1;
      }

   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.RENDER, TickType.CLIENT, TickType.PLAYER);
   }

   public RenderTickHandler getRenderer() {
      return this.renderHandler;
   }

   public AnimationManager getAnimationManager() {
      return this.animationHandler;
   }

   public String getLabel() {
      return "ccclienttick";
   }

   static {
      lastGameActionKey = !gameActionKey;
      gameActionDelay = 0;
      hasTexturepacks = false;
   }
}
