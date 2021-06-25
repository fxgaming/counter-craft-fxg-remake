package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.References;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.bouncycastle.jcajce.provider.digest.SHA256;

public class NewsManager {
   private static final URL NEWS_URL;
   private static final URL UPDATE_URL;
   private static final URL COMMUNITY_URL;
   public static boolean needsToCheckNews = true;
   public static boolean needsToCheckUpdates = true;

   public static List getNewsAsStringList(GuiCCMenu.NewsText givenEnum) throws IOException {
      BufferedReader reader;
      switch(givenEnum) {
      case NEWS:
         reader = new BufferedReader(new InputStreamReader(NEWS_URL.openStream()));
         break;
      case UPDATES:
         reader = new BufferedReader(new InputStreamReader(UPDATE_URL.openStream()));
         break;
      case COMMUNITY:
         reader = new BufferedReader(new InputStreamReader(COMMUNITY_URL.openStream()));
         break;
      default:
         reader = new BufferedReader(new InputStreamReader(NEWS_URL.openStream()));
      }

      List list = new ArrayList();
      for(String line = reader.readLine(); line != null; line = reader.readLine()) {
         list.add(line);
      }

      reader.close();
      return list;
   }

   static {
      try {
         NEWS_URL = new URL(References.URI_PW + "/pw/news.txt");
         UPDATE_URL = new URL(References.URI_PW + "/pw/update.txt");
         COMMUNITY_URL = new URL(References.URI_PW + "/pw/community.txt");
      } catch (MalformedURLException var1) {
         throw new RuntimeException(var1);
      }
   }
}
