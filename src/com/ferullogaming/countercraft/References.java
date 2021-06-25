package com.ferullogaming.countercraft;

public class References {
   public static String URL_DISCORD = "https://discord.gg/MPr7FXd";
   public static String URL_WEBSITE1 = "http://mixlab.pw";
   public static String URL_WEBSITE = "http://mixlab.pw";
   public static String URL_EXTERNALDOWNLOAD;
   public static String URL_EXTERNALLOCATION;
   public static String URL_CLIENT;
   public static String IP_CLOUD;
   public static String PORT_CLOUD;
   public static String SERVER_IP;
   public static String URI_PW;
   public static String SPONSOR_BUY;
   public static String[] ALL_SERVERS;

   static {
      URL_EXTERNALDOWNLOAD = URL_WEBSITE + "/serverfiles/download/";
      URL_EXTERNALLOCATION = URL_WEBSITE + "/serverfiles/countercraft/";
      URL_CLIENT = URL_EXTERNALLOCATION + "countercraft/CLIENT.txt";
      IP_CLOUD = "127.0.0.1";
      PORT_CLOUD = "0";
      SERVER_IP = "127.0.0.1:0";
      //URI_PW = "http://beetroot.s50.wh1.su";
      URI_PW = "http://launcher.mixlab.pw";
      SPONSOR_BUY = "http://anuscraft.net";
      //ALL_SERVERS = new String[]{"164.132.201.202:10040", "164.132.201.202:2001", "164.132.201.202:2002", "164.132.201.202:10030", "164.132.201.202:10036"};
      ALL_SERVERS = new String[]{""};
   }
}
