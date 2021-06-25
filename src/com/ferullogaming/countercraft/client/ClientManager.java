package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.cloud.ClientCloudManager;
import com.ferullogaming.countercraft.client.cloud.ConversationHandler;
import com.ferullogaming.countercraft.client.cloud.TradeHandler;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.item.gun.ClientGunTicker;
import com.ferullogaming.countercraft.utils.Utils_Crosshair;
import com.ferullogaming.countercraft.utils.Utils_ForgeFixer;
import com.ferullogaming.countercraft.utils.Utils_Updater;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.stats.StatList;
import org.lwjgl.input.Keyboard;

public class ClientManager extends Thread {
   public static boolean isGameLoading = true;
   public String dirLocation = "";
   public String cloudAddress;
   private ClientGunTicker clientGunUpdater;
   private ClientTickHandler clientTick;
   private ModSettings modSettings;
   private ClientCloudManager cloudManager;
   private FriendsManager friendManager;
   private TradeHandler tradeHandler;
   private ConversationHandler conversationHandler;
   public String WEB_FORUMS = "http://mixlab.pw";
   public String WEB_VK = "http://vk.com/projectmixlab";
   
   public ClientManager() {
      this.cloudAddress = References.IP_CLOUD;
   }

   public static ClientManager instance() {
      return CounterCraft.getClientManager();
   }

   public static String getMCLaunchedVersion() {
      return "1.6.4";
   }

   public void preInit(FMLPreInitializationEvent event) {
      try {
         Utils_ForgeFixer.init();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      try {
         Utils_Crosshair.init();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      Runtime.getRuntime().addShutdownHook(this);
      Minecraft.getMinecraft().gameSettings.guiScale = 3;
      this.setDirectory(event.getModConfigurationDirectory().getParent() + "/countercraft/");
      this.modSettings = new ModSettings();
      this.modSettings.loadSettings();
      this.cloudAddress = References.IP_CLOUD;
      this.cloudManager = new ClientCloudManager(this.cloudAddress);
      this.friendManager = new FriendsManager();
      this.tradeHandler = new TradeHandler();
      this.conversationHandler = new ConversationHandler();
      KeyBindingRegistry.registerKeyBinding(new KeyBindingManager());
   }

   public void init(FMLInitializationEvent event) {
      this.clientTick = new ClientTickHandler();
      this.clientGunUpdater = new ClientGunTicker(Minecraft.getMinecraft());
      this.clientGunUpdater.start();
      String key = Keyboard.getKeyName(KeyBindingManager.keyFriends.keyCode);
    }

   public void joinServerHUB() {
      Minecraft mc = Minecraft.getMinecraft();
      if (mc.theWorld != null && !mc.isSingleplayer()) {
         mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
         mc.theWorld.sendQuittingDisconnectingPacket();
         mc.loadWorld((WorldClient)null);
      }
   }

   public void run() {
      this.cloudManager.onShutdown();
      if (this.clientGunUpdater != null) {
         this.clientGunUpdater.stopThread();
         this.clientGunUpdater.interrupt();
         this.clientGunUpdater = null;
      }

   }

   public String getDirectory() {
      return this.dirLocation;
   }

   public void setDirectory(String par1) {
      this.dirLocation = par1;
      File f1 = new File(this.dirLocation);
      f1.mkdirs();
   }

   public ClientTickHandler getTickHandler() {
      return this.clientTick;
   }

   public ModSettings getModSettings() {
      return this.modSettings;
   }

   public ClientCloudManager getCloudManager() {
      return this.cloudManager;
   }

   public FriendsManager getFriendManager() {
      return this.friendManager;
   }

   public TradeHandler getTradeHandler() {
      return this.tradeHandler;
   }

   public ConversationHandler getConversationHandler() {
      return this.conversationHandler;
   }
}
