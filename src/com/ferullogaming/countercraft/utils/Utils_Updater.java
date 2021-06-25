package com.ferullogaming.countercraft.utils;

import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.ClientTickHandler;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuHome;
import com.ferullogaming.countercraft.client.gui.GuiCCMenuUpdating;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.FileUtils;

public class Utils_Updater {
   public static String updatePercentage = "";
   public static String fileName = "";
   public static int updatePercentageInt;
   public static boolean isUpdating = false;
   public static double updateSize;
   public static double updateSizeDownloaded;
   public static String latestUpdate;
   public static boolean hasUpdate = false;
   public static int latestVersionSize;

   public static String getLatestUpdate() {
      try {
         URL e = new URL(References.URL_EXTERNALLOCATION + "latestversion.txt");
         URLConnection yc = e.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         String inputLine = in.readLine();
         if (inputLine == null) {
            latestUpdate = "UNKNOWN";
            return "UNKNOWN";
         } else {
            latestUpdate = inputLine;
            return inputLine;
         }
      } catch (Exception var5) {
         latestUpdate = "UNKNOWN";
         return "UNKNOWN";
      }
   }

   public static boolean hasUpdate() throws OutOfMemoryError {
      try {
         URL givenURL = new URL(References.URL_EXTERNALLOCATION + "latestversion.txt");
         BufferedReader in = new BufferedReader(new InputStreamReader(givenURL.openStream()), 8192000);
         String inputLine = in.readLine();
         in.close();
         if (inputLine == null) {
            hasUpdate = false;
            return false;
         }

         String currentVersion = "1.2.5a";
         if (currentVersion.hashCode() != inputLine.hashCode()) {
            hasUpdate = true;
            return true;
         }
      } catch (Exception var5) {
         hasUpdate = false;
         return false;
      }

      hasUpdate = false;
      return false;
   }

   public static void initializeUpdate() {
      isUpdating = true;

      try {
         URL downloadURL = new URL(References.URL_EXTERNALDOWNLOAD + "/download/CounterCraft-" + getLatestUpdate() + ".jar");
         ReadableByteChannel rbc = Channels.newChannel(downloadURL.openStream());
         FileOutputStream fileOutputStream = new FileOutputStream("mods/CounterCraft.jar");
         fileOutputStream.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
         System.out.println("Updating CounterCraft complete!");
      } catch (IOException var3) {
         ServerConfigurationManager var10000 = MinecraftServer.getServer().getConfigurationManager();
         new ChatMessageComponent();
         var10000.sendChatMsg(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "Failed to update! Try again in 5 minutes!"));
         System.out.println("Failed to update server!");
      }

      isUpdating = false;
   }

   @SideOnly(Side.CLIENT)
   public static void initializeUpdateClient() {
      isUpdating = true;
      Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuUpdating());
      ClientTickHandler.addClientNotification(new ClientNotification(EnumChatFormatting.GREEN + "Updating!", "Updating game to " + getLatestUpdate() + "!"));
      (new Thread() {
         public void download(String remotePath, String localPath) {
            BufferedInputStream in = null;
            FileOutputStream out = null;

            try {
               URL url = new URL(remotePath);
               URLConnection conn = url.openConnection();
               long size = (long)conn.getContentLength();
               in = new BufferedInputStream(url.openStream());
               out = new FileOutputStream(localPath);
               byte[] data = new byte[1024];
               double sumCount = 0.0D;

               int count;
               while((count = in.read(data, 0, 1024)) != -1) {
                  out.write(data, 0, count);
                  sumCount += (double)count;
                  if (size > 0L) {
                     double percentage = sumCount / (double)size * 100.0D;
                     Utils_Updater.updatePercentageInt = (int)(sumCount / (double)size * 100.0D);
                     if (percentage >= 100.0D) {
                        Utils_Updater.updatePercentage = "Updating Complete! Closing Game...";
                     } else {
                        Utils_Updater.updatePercentage = "" + (int)percentage + "%";
                     }

                     Utils_Updater.fileName = url.toString();
                     Utils_Updater.updateSize = (double)(size / 1024L / 1024L);
                     if (Utils_Updater.updateSizeDownloaded != (double)((int)(sumCount / 1024.0D / 1024.0D))) {
                        Utils_Updater.updateSizeDownloaded = (double)((int)(sumCount / 1024.0D / 1024.0D));
                     }
                  }
               }
            } catch (MalformedURLException var30) {
               var30.printStackTrace();
            } catch (IOException var31) {
               System.out.println("Failed to Download File!");
               var31.printStackTrace();
               Utils_Updater.isUpdating = false;
               if (Minecraft.getMinecraft().theWorld == null) {
                  Minecraft.getMinecraft().displayGuiScreen(new GuiCCMenuHome());
               } else {
                  Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
               }

               ClientTickHandler.addClientNotification(new ClientNotification(EnumChatFormatting.RED + "Update failed!" + EnumChatFormatting.WHITE, "Try again in " + EnumChatFormatting.RED + "5" + EnumChatFormatting.WHITE + " minutes!"));
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var29) {
                     var29.printStackTrace();
                  }
               }

               if (out != null) {
                  try {
                     out.close();
                  } catch (IOException var28) {
                     var28.printStackTrace();
                  }
               }

            }

         }

         public void run() {
            File cachedDownloadDirectory = new File("cachedDownload/");
            if (!cachedDownloadDirectory.exists()) {
               cachedDownloadDirectory.mkdir();
            }

            this.download(References.URL_EXTERNALDOWNLOAD + "CounterCraft-" + Utils_Updater.getLatestUpdate() + ".jar", "cachedDownload/CounterCraft.jar");
            File downloadedFile = new File("cachedDownload/CounterCraft.jar");
            if (downloadedFile.exists()) {
               try {
                  if (downloadedFile.length() == (long)Utils_Updater.getLatestVersionSize()) {
                     Utils_Updater.moveFile(downloadedFile, new File("mods/CounterCraft.jar"));
                  } else {
                     ClientTickHandler.addClientNotification(new ClientNotification("Update Vailed!!", "Your Discord is already linked!"));
                  }
               } catch (IOException var4) {
                  var4.printStackTrace();
               }
            }

            Utils_Updater.isUpdating = false;
            Minecraft.getMinecraft().shutdown();
         }
      }).start();
   }

   public static int getLatestVersionSize() throws MalformedURLException {
      URLConnection conn = null;
      URL downloadURL = new URL(References.URL_EXTERNALDOWNLOAD + "CounterCraft-" + getLatestUpdate() + ".jar");

      int var2 = 0;

      return var2;
   }

   public static void moveFile(File origfile, File destfile) {
      boolean var2 = false;

      try {
         FileUtils.copyFileToDirectory(origfile, new File(destfile.getParent()), true);
         File newfile = new File(destfile.getParent() + File.separator + origfile.getName());
         if (newfile.exists() && FileUtils.contentEqualsIgnoreEOL(origfile, newfile, "UTF-8")) {
            origfile.delete();
            var2 = true;
         } else {
            System.out.println("File fail to move successfully!");
         }
      } catch (Exception var4) {
         System.out.println(var4);
      }

   }
}
