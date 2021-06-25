package com.ferullogaming.countercraft;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.Display;
import com.ferullogaming.countercraft.block.BlockManager;
import com.ferullogaming.countercraft.client.ClientManager;
import com.ferullogaming.countercraft.client.ClientProxy;
import com.ferullogaming.countercraft.client.SoundHandler;
import com.ferullogaming.countercraft.client.events.ClientEvents;
import com.ferullogaming.countercraft.client.events.RenderEvents;
import com.ferullogaming.countercraft.client.minimap.api.MwAPI;
import com.ferullogaming.countercraft.client.minimap.forge.MinimapConfig;
import com.ferullogaming.countercraft.client.minimap.overlay.OverlayGrid;
import com.ferullogaming.countercraft.client.minimap.overlay.OverlaySlime;
import com.ferullogaming.countercraft.commands.CommandGameManage;
import com.ferullogaming.countercraft.commands.CommandGamePlayer;
import com.ferullogaming.countercraft.commands.CommandScreenshot;
import com.ferullogaming.countercraft.entity.EntityManager;
import com.ferullogaming.countercraft.game.GameManager;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.network.NetworkManager;
import com.ferullogaming.countercraft.network.PacketHandler;
import com.ferullogaming.countercraft.network.PacketHandlerClient;
import com.ferullogaming.countercraft.network.cloud.CloudNetwork;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumOS;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;

@Mod(
   modid = "CounterCraft",
   name = "Counter Craft",
   version = "0.2"
)
@NetworkMod(
   clientSideRequired = false,
   serverSideRequired = false,
   clientPacketHandlerSpec = @SidedPacketHandler(
   channels = {"ccNetworking"},
   packetHandler = PacketHandlerClient.class
),
   serverPacketHandlerSpec = @SidedPacketHandler(
   channels = {"ccNetworking"},
   packetHandler = PacketHandler.class
)
)
public class CounterCraft {
   public static final String MC_VERSION = "1.6.4";
   public static final String MOD_ID = "CounterCraft";
   public static final String MOD_VERSION = "0.26";
   public static final String MOD_NAME = "CS";
   public static final String MOD_RESLOCATION = "countercraft";
   public static MinimapConfig config;
   public static int playerRenderAPI_ID = 0;
   @Instance("CounterCraft")
   public static CounterCraft instance;
   public static ClientProxy proxy;
   public static CommandBase[] cmds = new CommandBase[]{new CommandGameManage(), new CommandGamePlayer(), new CommandScreenshot()};
   public String folderLocation = "";
   public String forgeJsonLocation = "";
   public ItemManager itemManager;
   public BlockManager blockManager;
   public CommonTickHandler commonTick;
   public CommonEvents commonEvents;
   public CommonPlayerTracker commonPlayerTracker;
   public EntityManager entityManager;
   private ClientManager clientManager;
   private SoundHandler soundHandler;
   private ClientEvents clientEvents;
   private RenderEvents clientRenderEvents;
   private NetworkManager networkManager;
   private GameManager gameManager;
   private ServerManager serverManager;
   private CloudNetwork cloudNetwork;
   public static UpdateUtil upd;
   public static boolean needUpdate$ = false;
   public static Object[] Data;
   @SidedProxy(clientSide = "com.ferullogaming.countercraft.Client", serverSide = "com.ferullogaming.countercraft.Server")
   public static Server sidedProxy;
   public static HashMap<EntityPlayer, String[]> SKINS = new HashMap<EntityPlayer, String[]>();
   public static HashMap<EntityPlayer, String[]> STICKERS = new HashMap<EntityPlayer, String[]>();
   public static HashMap<EntityPlayer, String[]> KNIFE = new HashMap<EntityPlayer, String[]>();
   public HashMap<String, Boolean> ban = new HashMap<String, Boolean>();
   public HashMap<String, Boolean> sponsor = new HashMap<String, Boolean>();
   public HDD hdd;

   public static boolean isSessionOnlineMode() {
      String session = Minecraft.getMinecraft().getSession().getSessionID();
      return session != null && !session.equals("-") && !session.trim().equals("token:0:0") && session.length() >= 20;
   }

   public static CommonEvents getCommonEvents() {
      return instance.commonEvents;
   }

   public static ClientEvents getClientEvents() {
      return instance.clientEvents;
   }

   public static ClientManager getClientManager() {
      return instance.clientManager;
   }

   public static GameManager getGameManager() {
      return instance.gameManager;
   }

   public static ServerManager getServerManager() {
      return instance.serverManager;
   }

   public static NetworkManager getNetworkManager() {
      return instance.networkManager;
   }
   
   public static CloudNetwork getCloudNetwork() {
	   return instance.cloudNetwork;
   }

   public static void registerSound(String par1) {
      instance.soundHandler.addSound(par1);
   }

   public static void log(String par1, Object... par2) {
      String var1 = String.format(par1, par2);
      System.out.println("[CS] " + var1);
   }

   @EventHandler
   public void preLoad(FMLPreInitializationEvent event) {
	   this.sponsor.clear();
	   this.ban.clear();
	   upd = new UpdateUtil();
	   hdd = new HDD();
		try {
			this.Data = upd.checkThroughFile(new URL("http://beetroot.s50.wh1.su/lavaguden/lgdayz.txt"));
			hdd.client();
			hdd.writeAllInformation();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
      playerRenderAPI_ID = (playerRenderAPI_ID + "").hashCode();
      System.out.println("Render API ID is: " + playerRenderAPI_ID);
      log("CS Version %s found!", this.MOD_VERSION);
      log("Pre-Loading initialized...");
      this.folderLocation = event.getSuggestedConfigurationFile().getParentFile().getParent() + "/countercraft/";
      this.forgeJsonLocation = event.getSuggestedConfigurationFile().getParentFile().getParent() + "/versions/1.6.4-Forge9.11.1.1345/";
      config = new MinimapConfig(event.getSuggestedConfigurationFile());
      if (FMLCommonHandler.instance().getSide().isClient()) {
         this.clientEvents = new ClientEvents();
         MinecraftForge.EVENT_BUS.register(this.clientEvents);
         this.clientRenderEvents = new RenderEvents();
         MinecraftForge.EVENT_BUS.register(this.clientRenderEvents);
         this.clientManager = new ClientManager();
         this.clientManager.preInit(event);
         Display.setTitle("CS - v" + this.MOD_VERSION);
         if (Util.getOSType() != EnumOS.MACOS) {
            try {
               Display.setIcon(new ByteBuffer[]{this.readImage(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("countercraft:textures/gui/icon_16x16.png")).getInputStream()), this.readImage(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("countercraft:textures/gui/icon_32x32.png")).getInputStream())});
            } catch (IOException var3) {
               var3.printStackTrace();
            }
         }
         proxy = new ClientProxy();
         proxy.pre();
      }
   }

   public static HDD getHDD() {
	   return instance.hdd;
   }
   
   @EventHandler
   public void load(FMLInitializationEvent event) {
      log("Main Loading initialized...");
      if (FMLCommonHandler.instance().getSide().isClient()) {
         this.clientManager.init(event);
      }

      if (FMLCommonHandler.instance().getSide().isServer()) {
         this.serverManager = new ServerManager();
      }

      this.gameManager = new GameManager();
      this.commonTick = new CommonTickHandler(event);
      this.commonEvents = new CommonEvents();
      this.commonPlayerTracker = new CommonPlayerTracker();
      GameRegistry.registerPlayerTracker(this.commonPlayerTracker);
      this.networkManager = new NetworkManager();
      this.networkManager.init(this);
      this.cloudNetwork = new CloudNetwork();
      this.cloudNetwork.init(this);
      this.itemManager = new ItemManager();
      this.itemManager.init();
      this.blockManager = new BlockManager();
      this.blockManager.init();
      this.entityManager = new EntityManager();
      this.entityManager.init();
   }

   @SideOnly(Side.CLIENT)
   @EventHandler
   public void postLoad(FMLPostInitializationEvent event) {
      log("Post Loading initialized...");
      if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
         proxy = new ClientProxy();
         proxy.load();
         this.soundHandler = new SoundHandler();
         MwAPI.registerDataProvider("Slime", new OverlaySlime());
         MwAPI.registerDataProvider("Grid", new OverlayGrid());
      }
      
   }

   @EventHandler
   public void serverStart(FMLServerStartingEvent event) {
      if (cmds.length > 0) {
         for(int i = 0; i < cmds.length; ++i) {
            event.registerServerCommand(cmds[i]);
         }
      }

      this.gameManager.onServerStarted();
   }

   @EventHandler
   public void serverStopping(FMLServerStoppingEvent event) {
      this.gameManager.onServerStopped();
      if (this.serverManager != null) {
         this.serverManager.onServerStopped();
      }

   }

   private ByteBuffer readImage(InputStream par1File) throws IOException {
      BufferedImage bufferedimage = ImageIO.read(par1File);
      int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
      ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
      int[] aint1 = aint;
      int i = aint.length;

      for(int j = 0; j < i; ++j) {
         int k = aint1[j];
         bytebuffer.putInt(k << 8 | k >> 24 & 255);
      }

      bytebuffer.flip();
      return bytebuffer;
   }
}
