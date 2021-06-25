package com.f3rullo14.cloud.server;

import com.f3rullo14.cloud.G_TcpPacket;
import com.f3rullo14.cloud.server.gui.ConnectionListComponent;
import com.f3rullo14.cloud.server.gui.LogAgent;
import com.f3rullo14.cloud.server.gui.ServerGui;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class G_CloudManager {
   public String cloudName = "";
   public String cloudVersion = "";
   private G_CloudNetworkManager network;
   private int port;
   public int cloudConnections = 0;
   private int cloudConnectionDelay = 0;
   public ConnectionListComponent connectionGUIList;
   private LogAgent logAgent;
   public boolean isCloudStopped = false;
   private String folderLocation;
   private ICloud cloudParent;
   public ArrayList potentialChannels = new ArrayList();

   public G_CloudManager(ICloud par1) {
      this.cloudParent = par1;
      this.cloudName = par1.getCloudUsername();
      this.cloudVersion = par1.getCloudVersion();
      this.port = par1.getCloudPort();
      this.cloudParent.getChannels(this.potentialChannels);
      this.setupFolderLocation();
      this.setupLogger();
      this.setupServerInformation();
      G_TcpPacket.isClient = false;
   }

   public void onUpdate() {
      if (this.cloudConnectionDelay > 10) {
         if (this.network != null) {
            this.cloudConnections = this.network.getTotalConnections();
            System.out.println(this.cloudConnections);
         }

         this.cloudConnectionDelay = 0;
      }
      else {
    	  ++this.cloudConnectionDelay;
      }

      this.network.onNetworkTick();
   }

   public void initiateShutdown() {
      this.network.onShutdown();
      this.isCloudStopped = true;
   }

   public void addPendingCommand(String par1) {
      this.getParent().handleCommand(par1);
   }

   public ArrayList getDisplayInformation(ArrayList par1) {
      par1.add(this.cloudName);
      par1.add(this.cloudVersion);
      this.cloudParent.getDisplayInformation(par1);
      par1.add("");
      if (this.network != null && this.network.getChannels().size() > 0) {
         for(int i = 0; i < this.network.getChannels().size(); ++i) {
            String var1 = (String)this.network.getChannels().get(i);
            String var2 = "" + this.network.getNetChannel(var1).size();
            par1.add(var1 + ": " + var2);
         }
      }

      return par1;
   }

   public void setPlayerListComponent(ConnectionListComponent par1) {
      this.connectionGUIList = par1;
   }

   public G_CloudNetworkManager getNetwork() {
      return this.network;
   }

   public LogAgent getLogAgent() {
      return this.logAgent;
   }

   public ICloud getParent() {
      return this.cloudParent;
   }

   public void log(String par1) {
      this.getLogAgent().logInfo(par1);
   }

   public void setupServerInformation() {
      this.getLogAgent().logInfo("Starting Cloud [" + this.cloudName + "]...");
      this.getLogAgent().logInfo("Version " + this.cloudVersion);
      this.getLogAgent().logInfo("Running Java Version: " + System.getProperty("java.version"));
      this.getLogAgent().logInfo("Starting Network Manager...");
      this.network = new G_CloudNetworkManager(this, this.port);
      this.getLogAgent().logInfo("Network Manager Loaded!");
   }

   public void setupGui() {
      ServerGui.func_120016_a(this);
   }

   public void setupFolderLocation() {
      this.folderLocation = "cloudLocation";
      this.folderLocation = this.folderLocation + "/.ferullogaming/Cloud-API/" + this.cloudName.toLowerCase().replace(" ", "-") + "/";
      File f1 = new File(this.folderLocation);
      if (!f1.exists()) {
         f1.mkdirs();
      }

   }

   public void setupLogger() {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      String dateString = dateFormat.format(date);
      int var1 = 0;
      File f1 = new File(this.folderLocation + "serverlog-" + dateString + "-" + var1 + ".txt");
      boolean flag = true;

      while(flag) {
         ++var1;
         f1 = new File(this.folderLocation + "serverlog-" + dateString + "-" + var1 + ".txt");
         if (!f1.exists()) {
            flag = false;
         }
      }

      try {
         f1.createNewFile();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      this.logAgent = new LogAgent("CCCloud", (String)null, f1.getAbsolutePath());
      this.logAgent.setupLogger();
   }
}
