package com.ferullogaming.countercraft.client.events;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.block.BlockMapBarrier;
import com.ferullogaming.countercraft.block.decal.BlockDecal;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.cloud.PlayerDataCloud;
import com.ferullogaming.countercraft.client.cloud.PunishmentType;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuUpdating;
import com.ferullogaming.countercraft.client.gui.api.GuiFGLoadingScreen;
import com.ferullogaming.countercraft.client.gui.game.GuiCCIngameMenu;
import com.ferullogaming.countercraft.client.gui.game.GuiWeaponSelection;
import com.ferullogaming.countercraft.client.particle.ParticleEffects;
import com.ferullogaming.countercraft.entity.EntityGrenadeSmoke;
import com.ferullogaming.countercraft.entity.EntityManager;
import com.ferullogaming.countercraft.game.GameHelper;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.item.ItemGrenade;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import com.ferullogaming.countercraft.utils.Utils_Updater;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class ClientEvents {
   public static int chatSpawmDelay = 0;
   public static ResourceLocation smokeNadeSmoke = new ResourceLocation("countercraft:textures/gui/menu.png");
   String hoverSound = "countercraft:gui.buttonHover";
   @SideOnly(Side.CLIENT)
   private Minecraft mc = Minecraft.getMinecraft();

   @ForgeSubscribe
   public void renderGameOverlayPost(Pre event) {
      if (event.type == ElementType.PLAYER_LIST && GameManager.instance().currentClientGame != null) {
         event.setCanceled(true);
      }

      Minecraft par1Minecraft = Minecraft.getMinecraft();
      if (par1Minecraft != null && par1Minecraft.thePlayer != null) {
         PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)par1Minecraft.thePlayer);
         ScaledResolution scaledresolution = new ScaledResolution(par1Minecraft.gameSettings, par1Minecraft.displayWidth, par1Minecraft.displayHeight);
         int width = scaledresolution.getScaledWidth();
         int height = scaledresolution.getScaledHeight();
         int mwidth = width / 2;
         int mheight = height / 2;
         if (playerData != null && playerData.ghostViewing != null) {
            if (PlayerDataHandler.getPlayerData(playerData.ghostViewing).isGhost) {
               CCRenderHelper.drawRect(0.0D, 0.0D, (double)width, (double)height, "0x000000", 255.0F);
               CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.RED + "DEAD", mwidth, mheight / 2 + 15, 1.5D);
            }

            PlayerDataCloud spectatingCloudData = PlayerDataHandler.getPlayerCloudData(playerData.ghostViewing);
            CCRenderHelper.renderCenteredTextScaled("Spectating: " + (spectatingCloudData != null ? spectatingCloudData.getUsernameFormatted() : EnumChatFormatting.WHITE + playerData.ghostViewing), mwidth, mheight / 2 - 5, 2.0D);
            CCRenderHelper.renderCenteredTextScaled(EnumChatFormatting.GRAY + "(Use arrow-keys to switch players)", mwidth, mheight / 2 - 15, 1.0D);
         }
      }

   }

   @ForgeSubscribe
   public void onBlockOutlineRender(DrawBlockHighlightEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      if (!event.player.capabilities.isCreativeMode && mc.objectMouseOver != null) {
         Block theBlock = Block.blocksList[mc.theWorld.getBlockId(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ)];
         if (theBlock instanceof BlockMapBarrier || theBlock instanceof BlockDecal) {
            event.setCanceled(true);
         }
      }

   }

   @ForgeSubscribe
   public void onJoinWorld(EntityJoinWorldEvent event) {
   }

   @ForgeSubscribe
   public void onWorldRenderLast(RenderWorldLastEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayer thePlayer = mc.thePlayer;
      World theWorld = thePlayer.worldObj;
      ++ClientVariables.swing;
      List givenEntities = theWorld.getLoadedEntityList();

      for(int i = 0; i < theWorld.getLoadedEntityList().size(); ++i) {
         Entity givenEntity = (Entity)theWorld.getLoadedEntityList().get(i);
         if (givenEntity instanceof EntityGrenadeSmoke) {
            EntityGrenadeSmoke smokeGrenade = (EntityGrenadeSmoke)givenEntity;
            if (smokeGrenade.hasBurst && smokeGrenade.smokeFuse > 0) {
               CCRenderHelper.renderSmokeNadeSmoke(new ResourceLocation("countercraft:textures/misc/smokenadesmoke.png"), smokeGrenade.posX, smokeGrenade.posY + 1.0D, smokeGrenade.posZ, event.partialTicks, 600, 600, "0xFFFFFF", (double)smokeGrenade.smokeFuse);
            }
         }
      }

   }

   public void onPacketReceived(Packet70GameEvent event) {
      System.out.println(event.getPacketId());
   }

   @ForgeSubscribe
   public void backgroundMusicEvent(PlayBackgroundMusicEvent event) {
   }

   @ForgeSubscribe
   public void onPlayerInteract(PlayerInteractEvent event) {
      EntityPlayer player = event.entityPlayer;
      PlayerData data = PlayerDataHandler.getPlayerData(player);
      if (player == null || data.isGhost) {
         event.setCanceled(true);
      }

   }

   @ForgeSubscribe
   public void onChatSentToServer(EventChatSend event) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)player);
      PlayerDataCloud data1 = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (GameManager.instance().currentClientGame != null) {
         if (chatSpawmDelay > 0) {
            boolean flag = true;
            if (data1.group != null && data1.group.hasPermission("chat.spam.bypass")) {
               flag = false;
            }

            if (flag) {
               player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "Chat Anti-Spam. Please Wait " + (double)chatSpawmDelay / 20.0D + " Seconds."));
               player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "(Donation Ranks remove Anti-Spam)"));
               event.setCanceled(true);
               return;
            }
         }

         chatSpawmDelay = 40;
      }

   }

   @ForgeSubscribe
   public void onBlockBreak(BreakEvent event) {
      if (event.getPlayer().getHeldItem() != null && (event.getPlayer().getHeldItem().getItem() instanceof ItemGun || event.getPlayer().getHeldItem().getItem() instanceof ItemGrenade || event.getPlayer().getHeldItem().getItem() instanceof ItemKnife)) {
         event.setCanceled(true);
      }

   }

   @ForgeSubscribe
   public void onChatReceive(ClientChatReceivedEvent event) {
      Minecraft mc = Minecraft.getMinecraft();
      EntityPlayer player = mc.thePlayer;
      player.playSound("countercraft:gui.chat", 0.2F, 1.0F);
   }

   @ForgeSubscribe
   public void onGuiOpened(GuiOpenEvent event) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      PlayerData data = PlayerDataHandler.getPlayerData((EntityPlayer)player);
      PlayerDataCloud data1 = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (Utils_Updater.isUpdating && !(event.gui instanceof GuiCCMenuUpdating)) {
         event.setCanceled(true);
         Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuUpdating());
      } else {
         if (ClientManager.isGameLoading && !(event.gui instanceof GuiFGLoadingScreen)) {
            event.setCanceled(true);
            Minecraft.getMinecraft().displayGuiScreen(new GuiFGLoadingScreen());
         }

         if (event.gui instanceof GuiAchievements) {
            event.setCanceled(true);
         } else {
            if (event.gui instanceof GuiMainMenu) {
               event.setCanceled(true);
               Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
            }

            if (player != null && GameManager.instance().getPlayerGame((EntityPlayer)player) != null && data1.getFirstPunishmentType(PunishmentType.MUTE) != null && event.gui instanceof GuiChat) {
               player.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "You are Muted. " + data1.getFirstPunishmentType(PunishmentType.MUTE).getTimeRemainingString()));
               event.setCanceled(true);
            } else if (data == null || !data.isGhost || !(event.gui instanceof GuiContainer) && !(event.gui instanceof GuiWeaponSelection)) {
               if (event.gui instanceof GuiMultiplayer && ClientManager.instance().getCloudManager().joinedMMGame) {
                  event.setCanceled(true);
                  GuiScreen gui = ClientManager.instance().getCloudManager().lastGuiScreen;
                  if (gui != null) {
                     try {
                        Minecraft.getMinecraft().displayGuiScreen(gui);
                     } catch (Exception var7) {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
                     }
                  } else {
                     Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
                  }

                  ClientManager.instance().getCloudManager().joinedMMGame = false;
               }

               if (player != null && GameManager.instance().getPlayerGame((EntityPlayer)player) != null) {
                  if (event.gui instanceof GuiIngameMenu) {
                     event.setCanceled(true);
                     Minecraft.getMinecraft().displayGuiScreen(new GuiCCIngameMenu());
                  }

                  if (event.gui instanceof GuiInventory) {
                     GameManager.instance().getPlayerGame((EntityPlayer)player).getClientSide().onKeyPressed(18);
                     if (!GameManager.instance().getPlayerGame((EntityPlayer)player).getClientSide().allowGuiInventory()) {
                        event.setCanceled(true);
                     }
                  }
               }

            } else {
               event.setCanceled(true);
            }
         }
      }
   }

   @ForgeSubscribe
   public void onClientChatReceived(ClientChatReceivedEvent event) {
      EntityPlayer player = Minecraft.getMinecraft().thePlayer;
      PlayerDataCloud data = PlayerDataHandler.getPlayerCloudData(Minecraft.getMinecraft().getSession().getUsername());
      if (player != null) {
         try {
            JsonParser par = new JsonParser();
            JsonObject obj = (JsonObject)par.parse(event.message);
            String username = null;
            String message = null;
            if (obj.get("using") != null && obj.get("using") instanceof JsonArray) {
               JsonArray arr = obj.get("using").getAsJsonArray();
               if (arr.isJsonNull() && arr.size() > 1) {
                  username = arr.get(0).getAsString();
                  message = arr.get(1).getAsString();
               }
            }

            if (obj.get("text") != null) {
               String text = obj.get("text").getAsString();
               String[] split = text.split(">");
               String var1 = split[0];
               String var2 = text.substring(var1.length()).replaceFirst(">", "").trim();
               var1 = EnumChatFormatting.func_110646_a(var1).replaceAll("<", "").replace(">", "").trim();
               username = var1;
               message = var2;
            }

            if (username != null && message != null && PlayerDataHandler.playerDataMapping.containsKey(username)) {
               PlayerDataCloud data1 = PlayerDataHandler.getPlayerCloudData(username);
               String chat1 = data1.getUsernameFormatted() + "" + GameHelper.chatSeperator() + "" + message;
               IGame game = GameManager.instance().currentClientGame;
               if (player.worldObj != null && Minecraft.getMinecraft().isSingleplayer()) {
                  game = GameManager.instance().getPlayerGame((EntityPlayer)player);
               }

               if (game != null && game.getPlayerEventHandler().isPlayerPresent(username)) {
                  chat1 = game.getPlayerEventHandler().onClientMessageReceived(chat1, username, message);
                  if (chat1 == null) {
                     event.setCanceled(true);
                     return;
                  }
               }

               event.message = ChatMessageComponent.createFromText(chat1).toJson();
            }
         } catch (Exception var12) {
            System.out.println("Counter Craft failed to process chat!");
            System.out.println(event.message);
            var12.printStackTrace();
         }
      }

      if (event.message.contains("damage.cc")) {
         event.setCanceled(true);
      }

   }

   public boolean isPlayerLegit(String par1) {
      for(int i = 0; i < FMLClientHandler.instance().getClient().getNetHandler().playerInfoList.size(); ++i) {
         GuiPlayerInfo info = (GuiPlayerInfo)FMLClientHandler.instance().getClient().getNetHandler().playerInfoList.get(i);
         if (info.name.equals(par1)) {
            return true;
         }
      }

      return false;
   }

   @ForgeSubscribe
   public void onMusic(PlayBackgroundMusicEvent event) {
   }

   @ForgeSubscribe
   public void onClientIngameGUI(Pre event) {
      if (event.type == ElementType.ALL && Minecraft.getMinecraft().thePlayer != null && GameManager.instance().isPlayerInGame(Minecraft.getMinecraft().thePlayer)) {
         GuiIngameForge.renderFood = false;
      }

   }

   public void onBulletCollisionEntity(EntityLivingBase par1, boolean par2, int par3) {
      Random rand = new Random();
      if (!par1.isDead) {
         ParticleEffects.spawnParticle("Blood", par1.posX, par1.posY, par1.posZ, (double)((rand.nextFloat() - 0.5F) * 3.0F), 0.0D, (double)((rand.nextFloat() - 0.5F) * 3.0F));
         ParticleEffects.spawnParticle("Blood", par1.posX, par1.posY, par1.posZ, (double)((rand.nextFloat() - 0.5F) * 3.0F), 0.0D, (double)((rand.nextFloat() - 0.5F) * 3.0F));
         ParticleEffects.spawnParticle("GroundBlood", par1.posX, par1.posY, par1.posZ, 0.0D, 0.0D, 0.0D);
      }

      if (par1 instanceof EntityPlayer && par2) {
         int particles = 15;

         for(int i = 0; i < particles; ++i) {
            EntityManager.spawnParticle("tilecrack_42_0", par1.posX, par1.posY + 1.75D, par1.posZ, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void onBulletCollisionBlock(World par2, double par3, double par4, double par5, int par6, int par7, Vec3 hitVec3, int par9) {
      int particles = 5;
      double vx = hitVec3.xCoord;
      double vy = hitVec3.yCoord;
      double vz = hitVec3.zCoord;
      Material mat = par2.getBlockMaterial((int)vx, (int)vy, (int)vz);
      if (Minecraft.getMinecraft().gameSettings.fancyGraphics) {
         particles = 12;
      }

      par2.playSound(par3, par4, par5, "countercraft:bullet.impact", 0.5F, 1.0F, true);
      if (!mat.equals(Material.air)) {
         int i5;
         for(i5 = 0; i5 < particles; ++i5) {
            EntityManager.spawnParticle("tilecrack_" + par6 + "_" + par9, vx, vy, vz, 0.0D, 0.0D, 0.0D);
         }

         particles = 2;

         for(i5 = 0; i5 < particles; ++i5) {
            EntityManager.spawnParticle("tilecrack_" + Block.cloth.blockID + "_" + 15, vx, vy, vz, 0.0D, 0.0D, 0.0D);
         }

         particles = 2;

         for(i5 = 0; i5 < particles; ++i5) {
            EntityManager.spawnParticle("tilecrack_" + Block.cloth.blockID + "_" + 0, vx, vy, vz, 0.0D, 0.0D, 0.0D);
         }

      }
   }
}
