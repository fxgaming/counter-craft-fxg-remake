package com.f3rullo14.cloud.example;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.f3rullo14.cloud.server.G_CloudManager;
import com.f3rullo14.cloud.server.G_CloudNetworkManager;
import com.f3rullo14.cloud.server.G_CloudTcpConnectionHandler;
import com.f3rullo14.cloud.server.ICloud;
import com.f3rullo14.fds.tag.FDSTagCompound;

public class Cloud implements ICloud {
   public boolean isRunning = true;
   private G_CloudManager cloudManager = new G_CloudManager(this);
   public static Cloud instance;

   public Cloud() {
	  instance = this;
      this.cloudManager.getNetwork().registerPacketHandler(new CloudPacketHandler());
   }

   public void onUpdate() {
      this.cloudManager.onUpdate();
   }

   public void onShutdown() {
      this.cloudManager.initiateShutdown();
   }

   public String getCloudUsername() {
      return "SERVER";
   }

   public String getCloudVersion() {
      return "1.0.0";
   }

   public int getCloudPort() {
      return 2100;
   }

   public void onConnectionEstablished(G_CloudTcpConnectionHandler par1, FDSTagCompound par2) {
      this.cloudManager.log("User: " + par1.getConnectionUsername() + " connected!");
   }

   public void onConnectionDisconnected(G_CloudTcpConnectionHandler par1) {
   }

   public void handleCommand(String par1) {
   }

   public static void main(String[] args) {
      System.out.println("[Cloud] Starting...");
      Cloud cloud = new Cloud();
      System.out.println("[Cloud] Running Updates to the Program...");
      JFrame jf = new JFrame() {};
      jf.setVisible(true);
      jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
      jf.setSize(300, 300);
      jf.setLocation(100, 100);
      jf.setTitle("CS CLOUD TEST");
      jf.setBounds(300, 300, 300, 300);
      jf.add(new TextInfo());
      
      while(cloud.isRunning) {
         cloud.onUpdate();
         try {
            Thread.sleep(500L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      }

      System.out.println("[Cloud] Shutting Down...");
      cloud.onShutdown();
      System.exit(0);
   }

   public boolean onConnectionReached(G_CloudTcpConnectionHandler par1) {
      return true;
   }

   public void getDisplayInformation(ArrayList par1) {
   }

   public void getChannels(ArrayList par1) {
   }

   public boolean onConnectionAddedToThread(G_CloudTcpConnectionHandler par1) {
      return true;
   }
   
   public static class TextInfo extends JComponent {
	   @Override
	   public void paintComponent(Graphics g) {
		   Font f = new Font("Colibri", Font.BOLD, 10);
		   Graphics2D g2 = (Graphics2D) g;
		   G_CloudNetworkManager net = Cloud.instance.cloudManager.getNetwork();
		   G_CloudManager cm =  Cloud.instance.cloudManager;
		   g2.setFont(f);
		   int x = 2;
		   int y = 10;
		   g2.drawString("Connections - " + cm.cloudConnections, x, y);
		   g2.drawString("INFO - " + cm.cloudName + ":" + cm.cloudVersion + " | " + cm.isCloudStopped, x, y * 2);
		   g2.drawString("Potencial Channels - " + cm.potentialChannels.size(), x, y * 3);
		   g2.drawString("Connections Total - " + net.getTotalConnections(), x, y * 4);
		   g2.drawString("Channels - " + net.getChannels().size(), x, y * 5);
		   try {
			   InetAddress ia = InetAddress.getLocalHost();
			   g2.drawString("IPS:", x, y * 7);
			   g2.drawString("  HostAddress - " + ia.getHostAddress().trim(), x, y * 8);
			   g2.drawString("  HostName - " + ia.getHostName().trim(), x, y * 9);
			   g2.drawString("  CanonicalHostName - " + ia.getCanonicalHostName().trim(), x, y * 10);
		   } catch (UnknownHostException e) {} 
	   }
   }
}
