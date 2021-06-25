package com.ferullogaming.countercraft.client.minimap;

import com.ferullogaming.countercraft.client.ClientVariables;
import com.ferullogaming.countercraft.client.minimap.forge.MinimapConfig;
import com.ferullogaming.countercraft.client.minimap.map.MapTexture;
import com.ferullogaming.countercraft.client.minimap.map.MarkerManager;
import com.ferullogaming.countercraft.client.minimap.map.MiniMap;
import com.ferullogaming.countercraft.client.minimap.map.UndergroundTexture;
import com.ferullogaming.countercraft.client.minimap.region.BlockColours;
import com.ferullogaming.countercraft.client.minimap.region.RegionManager;
import com.ferullogaming.countercraft.client.minimap.tasks.CloseRegionManagerTask;
import com.ferullogaming.countercraft.game.BombPoint;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.game.IGame;
import com.ferullogaming.countercraft.game.cas.CasualBombDefusal;
import com.ferullogaming.countercraft.game.cbd.CompetitiveBombDefusal;
import com.ferullogaming.countercraft.player.PlayerData;
import com.ferullogaming.countercraft.player.PlayerDataHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.input.Keyboard;

public class Minimap {
   public static final String catWorld = "world";
   public static final String catMarkers = "markers";
   public static final String catOptions = "options";
   public static final String worldDirConfigName = "mapwriter.cfg";
   public static final String blockColourSaveFileName = "MapWriterBlockColours.txt";
   public static final String blockColourOverridesFileName = "MapWriterBlockColourOverrides.txt";
   public static Minimap instance;
   private final File configDir;
   private final File saveDir;
   public Minecraft mc = null;
   public String worldName = "default";
   public MinimapConfig config;
   public MinimapConfig worldConfig = null;
   public File worldDir = null;
   public File imageDir = null;
   public boolean linearTextureScalingEnabled = true;
   public int coordsMode = 0;
   public boolean undergroundMode = true;
   public boolean teleportEnabled = true;
   public String teleportCommand = "tp";
   public int defaultTeleportHeight = 80;
   public int maxZoom = 5;
   public int minZoom = -5;
   public boolean useSavedBlockColours = false;
   public int maxChunkSaveDistSq = 16384;
   public boolean mapPixelSnapEnabled = true;
   public int textureSize = 2048;
   public int configTextureSize = 2048;
   public int maxDeathMarkers = 3;
   public int chunksPerTick = 5;
   public boolean portNumberInWorldNameEnabled = true;
   public String saveDirOverride = "";
   public boolean regionFileOutputEnabledSP = true;
   public boolean regionFileOutputEnabledMP = true;
   public int backgroundTextureMode = 0;
   public boolean ready = false;
   public boolean multiplayer = false;
   public int tickCounter = 0;
   public List dimensionList = new ArrayList();
   public double playerX = 0.0D;
   public double playerZ = 0.0D;
   public double playerY = 0.0D;
   public int playerXInt = 0;
   public int playerYInt = 0;
   public int playerZInt = 0;
   public double playerHeading = 0.0D;
   public int playerDimension = 0;
   public double mapRotationDegrees = 0.0D;
   public MapTexture mapTexture = null;
   public UndergroundTexture undergroundMapTexture = null;
   public BackgroundExecutor executor = null;
   public MiniMap miniMap = null;
   public MarkerManager markerManager = null;
   public BlockColours blockColours = null;
   public RegionManager regionManager = null;
   public ChunkManager chunkManager = null;
   private String serverName = "default";
   private int serverPort = 0;
   private boolean onPlayerDeathAlreadyFired = false;

   public Minimap(MinimapConfig config) {
      this.mc = Minecraft.getMinecraft();
      this.config = config;
      this.saveDir = new File(this.mc.mcDataDir, "saves");
      this.configDir = new File(this.mc.mcDataDir, "config");
      this.ready = false;
      instance = this;
   }

   public String getWorldName() {
      String worldName;
      if (this.multiplayer) {
         if (this.portNumberInWorldNameEnabled) {
            worldName = String.format("%s_%d", this.serverName, this.serverPort);
         } else {
            worldName = String.format("%s", this.serverName);
         }
      } else {
         IntegratedServer server = this.mc.getIntegratedServer();
         worldName = server != null ? server.getFolderName() : "sp_world";
      }

      worldName = MinimapUtils.mungeString(worldName);
      if (worldName == "") {
         worldName = "default";
      }

      return worldName;
   }

   public void loadConfig() {
      this.config.load();
      this.linearTextureScalingEnabled = this.config.getOrSetBoolean("options", "linearTextureScaling", this.linearTextureScalingEnabled);
      this.useSavedBlockColours = this.config.getOrSetBoolean("options", "useSavedBlockColours", this.useSavedBlockColours);
      this.teleportEnabled = this.config.getOrSetBoolean("options", "teleportEnabled", this.teleportEnabled);
      this.teleportCommand = this.config.get("options", "teleportCommand", this.teleportCommand).getString();
      this.coordsMode = this.config.getOrSetInt("options", "coordsMode", this.coordsMode, 0, 2);
      this.maxChunkSaveDistSq = this.config.getOrSetInt("options", "maxChunkSaveDistSq", this.maxChunkSaveDistSq, 1, 65536);
      this.mapPixelSnapEnabled = this.config.getOrSetBoolean("options", "mapPixelSnapEnabled", this.mapPixelSnapEnabled);
      this.maxDeathMarkers = this.config.getOrSetInt("options", "maxDeathMarkers", this.maxDeathMarkers, 0, 1000);
      this.chunksPerTick = this.config.getOrSetInt("options", "chunksPerTick", this.chunksPerTick, 1, 500);
      this.saveDirOverride = this.config.get("options", "saveDirOverride", this.saveDirOverride).getString();
      this.portNumberInWorldNameEnabled = this.config.getOrSetBoolean("options", "portNumberInWorldNameEnabled", this.portNumberInWorldNameEnabled);
      this.regionFileOutputEnabledSP = this.config.getOrSetBoolean("options", "regionFileOutputEnabledSP", this.regionFileOutputEnabledSP);
      this.regionFileOutputEnabledMP = this.config.getOrSetBoolean("options", "regionFileOutputEnabledMP", this.regionFileOutputEnabledMP);
      this.backgroundTextureMode = this.config.getOrSetInt("options", "backgroundTextureMode", this.backgroundTextureMode, 0, 1);
      this.maxZoom = this.config.getOrSetInt("options", "zoomOutLevels", this.maxZoom, 1, 256);
      this.minZoom = -this.config.getOrSetInt("options", "zoomInLevels", -this.minZoom, 1, 256);
      this.configTextureSize = this.config.getOrSetInt("options", "textureSize", this.configTextureSize, 1024, 8192);
      this.setTextureSize();
   }

   public void loadWorldConfig() {
      File worldConfigFile = new File(this.worldDir, "mapwriter.cfg");
      this.worldConfig = new MinimapConfig(worldConfigFile);
      this.worldConfig.load();
      this.dimensionList.clear();
      this.worldConfig.getIntList("world", "dimensionList", this.dimensionList);
      this.addDimension(0);
      this.cleanDimensionList();
   }

   public void saveConfig() {
      this.config.setBoolean("options", "linearTextureScaling", this.linearTextureScalingEnabled);
      this.config.setBoolean("options", "useSavedBlockColours", this.useSavedBlockColours);
      this.config.setInt("options", "textureSize", this.configTextureSize);
      this.config.setInt("options", "coordsMode", this.coordsMode);
      this.config.setInt("options", "maxChunkSaveDistSq", this.maxChunkSaveDistSq);
      this.config.setBoolean("options", "mapPixelSnapEnabled", this.mapPixelSnapEnabled);
      this.config.setInt("options", "maxDeathMarkers", this.maxDeathMarkers);
      this.config.setInt("options", "chunksPerTick", this.chunksPerTick);
      this.config.setInt("options", "backgroundTextureMode", this.backgroundTextureMode);
      this.config.save();
   }

   public void saveWorldConfig() {
      this.worldConfig.setIntList("world", "dimensionList", this.dimensionList);
      this.worldConfig.save();
   }

   public void setTextureSize() {
      if (this.configTextureSize != this.textureSize) {
         int maxTextureSize = Render.getMaxTextureSize();

         int textureSize;
         for(textureSize = 1024; textureSize <= maxTextureSize && textureSize <= this.configTextureSize; textureSize *= 2) {
            ;
         }

         textureSize /= 2;
         MinimapUtils.log("GL reported max texture size = %d", maxTextureSize);
         MinimapUtils.log("texture size from config = %d", this.configTextureSize);
         MinimapUtils.log("setting map texture size to = %d", textureSize);
         this.textureSize = textureSize;
         if (this.ready) {
            this.reloadMapTexture();
         }
      }

   }

   public void updatePlayer() {
      this.undergroundMode = true;
      this.playerX = this.mc.thePlayer.posX;
      this.playerY = this.mc.thePlayer.posY;
      this.playerZ = this.mc.thePlayer.posZ;
      this.playerXInt = (int)Math.floor(this.playerX);
      this.playerYInt = (int)Math.floor(this.playerY);
      this.playerZInt = (int)Math.floor(this.playerZ);
      this.playerHeading = Math.toRadians((double)this.mc.thePlayer.rotationYaw) + 1.5707963267948966D;
      this.mapRotationDegrees = (double)(-this.mc.thePlayer.rotationYaw + 180.0F);
   }

   public void addDimension(int dimension) {
      int i = this.dimensionList.indexOf(dimension);
      if (i < 0) {
         this.dimensionList.add(dimension);
      }

   }

   public void cleanDimensionList() {
      List dimensionListCopy = new ArrayList(this.dimensionList);
      this.dimensionList.clear();
      Iterator i$ = dimensionListCopy.iterator();

      while(i$.hasNext()) {
         int dimension = ((Integer)i$.next()).intValue();
         this.addDimension(dimension);
      }

   }

   public void loadBlockColourOverrides(BlockColours bc) {
      File f = new File(this.configDir, "MapWriterBlockColourOverrides.txt");
      if (f.isFile()) {
         MinimapUtils.logInfo("loading block colour overrides file %s", f);
         bc.loadFromFile(f);
      } else {
         MinimapUtils.logInfo("recreating block colour overrides file %s", f);
         BlockColours.writeOverridesFile(f);
         if (f.isFile()) {
            bc.loadFromFile(f);
         }
      }

   }

   public void saveBlockColours(BlockColours bc) {
      File f = new File(this.configDir, "MapWriterBlockColours.txt");
      MinimapUtils.logInfo("saving block colours to '%s'", f);
      bc.saveToFile(f);
   }

   public void reloadBlockColours() {
      BlockColours bc = new BlockColours();
      File f = new File(this.configDir, "MapWriterBlockColours.txt");
      if (this.useSavedBlockColours && f.isFile()) {
         MinimapUtils.logInfo("loading block colours from %s", f);
         bc.loadFromFile(f);
         this.loadBlockColourOverrides(bc);
      } else {
         MinimapUtils.logInfo("generating block colours");
         this.loadBlockColourOverrides(bc);
         BlockColourGen.genBlockColours(bc);
         this.loadBlockColourOverrides(bc);
         this.saveBlockColours(bc);
      }

      this.blockColours = bc;
   }

   public void reloadMapTexture() {
      this.executor.addTask(new CloseRegionManagerTask(this.regionManager));
      this.executor.close();
      MapTexture oldMapTexture = this.mapTexture;
      MapTexture newMapTexture = new MapTexture(this.textureSize, this.linearTextureScalingEnabled);
      this.mapTexture = newMapTexture;
      if (oldMapTexture != null) {
         oldMapTexture.close();
      }

      this.executor = new BackgroundExecutor();
      this.regionManager = new RegionManager(this.worldDir, this.imageDir, this.blockColours, this.minZoom, this.maxZoom);
      UndergroundTexture oldTexture = this.undergroundMapTexture;
      UndergroundTexture newTexture = new UndergroundTexture(this, this.textureSize, this.linearTextureScalingEnabled);
      this.undergroundMapTexture = newTexture;
      if (oldTexture != null) {
         this.undergroundMapTexture.close();
      }

   }

   public void setCoordsMode(int mode) {
      this.coordsMode = Math.min(Math.max(0, mode), 2);
   }

   public int toggleCoords() {
      this.setCoordsMode((this.coordsMode + 1) % 3);
      return this.coordsMode;
   }

   public void onConnectionOpened() {
      MinimapUtils.log("connection opened to integrated server");
      this.multiplayer = false;
   }

   public void onConnectionOpened(String server, int port) {
      MinimapUtils.log("connection opened to remote server: %s %d", server, port);
      this.serverName = server;
      this.serverPort = port;
      this.multiplayer = true;
   }

   public void onClientLoggedIn(Packet1Login login) {
      MinimapUtils.log("onClientLoggedIn: dimension = %d", login.dimension);
      this.loadConfig();
      this.worldName = this.getWorldName();
      File saveDir = this.saveDir;
      if (this.saveDirOverride.length() > 0) {
         File d = new File(this.saveDirOverride);
         if (d.isDirectory()) {
            saveDir = d;
         } else {
            MinimapUtils.log("error: no such directory %s", this.saveDirOverride);
         }
      }

      if (this.multiplayer) {
         this.worldDir = new File(new File(saveDir, "mapwriter_mp_worlds"), this.worldName);
      } else {
         this.worldDir = new File(new File(saveDir, "mapwriter_sp_worlds"), this.worldName);
      }

      this.loadWorldConfig();
      this.imageDir = new File(this.worldDir, "images");
      if (!this.imageDir.exists()) {
         this.imageDir.mkdirs();
      }

      if (!this.imageDir.isDirectory()) {
         MinimapUtils.log("Mapwriter: ERROR: could not create images directory '%s'", this.imageDir.getPath());
      }

      this.tickCounter = 0;
      this.onPlayerDeathAlreadyFired = false;
      this.markerManager = new MarkerManager();
      this.markerManager.load(this.worldConfig, "markers");
      this.executor = new BackgroundExecutor();
      this.mapTexture = new MapTexture(this.textureSize, this.linearTextureScalingEnabled);
      this.undergroundMapTexture = new UndergroundTexture(this, this.textureSize, this.linearTextureScalingEnabled);
      this.reloadBlockColours();
      this.regionManager = new RegionManager(this.worldDir, this.imageDir, this.blockColours, this.minZoom, this.maxZoom);
      this.miniMap = new MiniMap(this);
      this.miniMap.view.setDimension(login.dimension);
      this.chunkManager = new ChunkManager(this);
      this.ready = true;
   }

   public void onWorldLoad(World world) {
      this.playerDimension = world.provider.dimensionId;
      if (this.ready) {
         this.addDimension(this.playerDimension);
         this.miniMap.view.setDimension(this.playerDimension);
      }

   }

   public void onWorldUnload(World world) {
   }

   public void onConnectionClosed() {
      MinimapUtils.log("connection closed");
      if (this.ready) {
         this.ready = false;
         this.chunkManager.close();
         this.chunkManager = null;
         this.executor.addTask(new CloseRegionManagerTask(this.regionManager));
         this.regionManager = null;
         MinimapUtils.log("waiting for %d tasks to finish...", this.executor.tasksRemaining());
         if (this.executor.close()) {
            MinimapUtils.log("error: timeout waiting for tasks to finish");
         }

         MinimapUtils.log("done");
         this.markerManager.save(this.worldConfig, "markers");
         this.markerManager.clear();
         this.miniMap.close();
         this.miniMap = null;
         this.undergroundMapTexture.close();
         this.mapTexture.close();
         this.saveWorldConfig();
         this.saveConfig();
         this.tickCounter = 0;
      }

   }

   public void onTick() {
      if (this.ready && this.mc.thePlayer != null) {
         this.updatePlayer();
         PlayerData myData = PlayerDataHandler.getPlayerData((EntityPlayer)this.mc.thePlayer);
         if (myData != null && GameManager.instance().currentClientGame != null && (Minecraft.getMinecraft().gameSettings.fancyGraphics || this.tickCounter % 20 == 0)) {
            this.markerManager.markerList.clear();
            IGame clientGame = GameManager.instance().currentClientGame;
            Iterator i$;
            BombPoint bombPoint;
            if (clientGame instanceof CasualBombDefusal) {
               i$ = ((CasualBombDefusal)clientGame).bombPoints.iterator();

               while(i$.hasNext()) {
                  bombPoint = (BombPoint)i$.next();
                  this.markerManager.addMarker(bombPoint.title, "player", (int)bombPoint.posX, (int)bombPoint.posY, (int)bombPoint.posZ, this.mc.thePlayer.dimension, -1, true, bombPoint.isBombPlaneted);
               }
            } else if (clientGame instanceof CompetitiveBombDefusal) {
               i$ = ((CompetitiveBombDefusal)clientGame).bombPoints.iterator();

               while(i$.hasNext()) {
                  bombPoint = (BombPoint)i$.next();
                  this.markerManager.addMarker(bombPoint.title, "player", (int)bombPoint.posX, (int)bombPoint.posY, (int)bombPoint.posZ, this.mc.thePlayer.dimension, -1, true, bombPoint.isBombPlaneted);
               }
            }

            i$ = clientGame.getPlayerEventHandler().getPlayers().iterator();

            label117:
            while(true) {
               while(true) {
                  while(true) {
                     EntityPlayer currentPlayerEntity;
                     PlayerData currentPlayerData;
                     String currentPlayerUsername;
                     do {
                        do {
                           do {
                              do {
                                 if (!i$.hasNext()) {
                                    this.markerManager.setVisibleGroupName("player");
                                    this.markerManager.update();
                                    break label117;
                                 }

                                 currentPlayerUsername = (String)i$.next();
                                 currentPlayerEntity = this.mc.theWorld.getPlayerEntityByName(currentPlayerUsername);
                              } while(currentPlayerEntity == null);

                              currentPlayerData = PlayerDataHandler.getPlayerData(currentPlayerUsername);
                           } while(currentPlayerData == null);
                        } while(currentPlayerData.isGhost);
                     } while(currentPlayerUsername.equalsIgnoreCase(this.mc.thePlayer.username));

                     if (clientGame.getPlayerEventHandler().getPlayerTeam(currentPlayerEntity).teamName.equalsIgnoreCase(clientGame.getPlayerEventHandler().getPlayerTeam(this.mc.thePlayer.username).teamName)) {
                        if (currentPlayerData.calloutTimer > 0 && currentPlayerData.calloutTimer % 5 == 0) {
                           this.markerManager.addMarker("Team Mate", "player", (int)currentPlayerEntity.posX, (int)currentPlayerEntity.posY, (int)currentPlayerEntity.posZ, currentPlayerEntity.dimension, -16776961, false, false);
                        } else {
                           this.markerManager.addMarker("Team Mate", "player", (int)currentPlayerEntity.posX, (int)currentPlayerEntity.posY, (int)currentPlayerEntity.posZ, currentPlayerEntity.dimension, -16711936, false, false);
                        }
                     } else if (currentPlayerData.seenTimer > 0 || this.mc.thePlayer.canEntityBeSeen(currentPlayerEntity) || myData.isSpectating()) {
                        this.markerManager.addMarker("Enemy", "player", (int)currentPlayerEntity.posX, (int)currentPlayerEntity.posY, (int)currentPlayerEntity.posZ, currentPlayerEntity.dimension, -65536, false, false);
                     }
                  }
               }
            }
         }

         if (this.undergroundMode && this.tickCounter % 40 == 0) {
            this.undergroundMapTexture.update();
         }

         if (this.mc.currentScreen instanceof GuiGameOver) {
            if (!this.onPlayerDeathAlreadyFired) {
               this.onPlayerDeathAlreadyFired = true;
            }
         } else {
            this.onPlayerDeathAlreadyFired = false;
            if (this.mc.currentScreen == null) {
               this.miniMap.view.setViewCentreScaled(this.playerX, this.playerZ, this.playerDimension);
               PlayerData playerData = PlayerDataHandler.getPlayerData((EntityPlayer)this.mc.thePlayer);
               if (!Keyboard.isKeyDown(15) && this.mc.currentScreen == null && playerData != null && !playerData.isAiming && GameManager.instance().currentClientGame != null && ClientVariables.enableMinimap) {
                  this.miniMap.drawCurrentMap();
               }
            }
         }

         for(int maxTasks = 50; !this.executor.processTaskQueue() && maxTasks > 0; --maxTasks) {
            ;
         }

         this.chunkManager.onTick();
         this.mapTexture.processTextureUpdates();
         ++this.tickCounter;
      }

   }

   public void onChunkLoad(Chunk chunk) {
      if (this.ready && chunk != null && chunk.worldObj instanceof WorldClient) {
         this.chunkManager.addChunk(chunk);
      }

   }

   public void onChunkUnload(Chunk chunk) {
      if (this.ready && chunk != null && chunk.worldObj instanceof WorldClient) {
         this.chunkManager.removeChunk(chunk);
      }

   }
}
