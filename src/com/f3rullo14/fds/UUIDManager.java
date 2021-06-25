package com.f3rullo14.fds;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class UUIDManager {
   private static volatile TreeMap usernameUUIDMapping;
   private static volatile Map uuidUsernameMapping;
   private static JsonParser jsonParser;
   public static boolean DEBUG;
   public static final int PROFILES_PER_REQUEST = 100;
   public static volatile ArrayList pendingProfiles;

   public static String getUUIDUsername(String par1) {
      return (String)uuidUsernameMapping.get(par1);
   }

   public static String getUsernameUUID(String par1) {
      if (usernameUUIDMapping.get(par1) == null) {
         if (!DEBUG) {
            if (!pendingProfiles.contains(par1)) {
               pendingProfiles.add(par1);
            }

            return null;
         }

         String uuid = UUID.randomUUID().toString();
         usernameUUIDMapping.put(par1, uuid);
         uuidUsernameMapping.put(uuid, par1);
      }

      return (String)usernameUUIDMapping.get(par1);
   }

   public static synchronized String getUsernameUUIDForced(String par1) {
      if (usernameUUIDMapping.get(par1) == null) {
         if (DEBUG) {
            String uuid = UUID.randomUUID().toString();
            usernameUUIDMapping.put(par1, uuid);
            uuidUsernameMapping.put(uuid, par1);
         } else {
            String[] data = fetchUUID(par1);
            if (data[0] != null && data[1] != null) {
               usernameUUIDMapping.put(data[1], data[0]);
               uuidUsernameMapping.put(data[0], data[1]);
            }
         }
      }

      return (String)usernameUUIDMapping.get(par1);
   }

   public static String[] fetchUUID(String par1) {
      try {
         HttpURLConnection connection = createConnection("https://mcapi.ca/uuid/player/" + par1.trim());
         JsonArray array = (JsonArray)jsonParser.parse((Reader)(new InputStreamReader(connection.getInputStream())));
         Iterator i$ = array.iterator();
         if (i$.hasNext()) {
            Object profile = i$.next();
            JsonObject jsonProfile = (JsonObject)profile;
            String uuid = jsonProfile.get("uuid_formatted").getAsString();
            String name = jsonProfile.get("name").getAsString();
            return new String[]{uuid.toString(), name};
         }

         Thread.sleep(100L);
      } catch (Exception var8) {
         ;
      }

      return new String[]{null, null};
   }

   private static void writeBody(HttpURLConnection connection, String body) throws Exception {
      OutputStream stream = connection.getOutputStream();
      stream.write(body.getBytes());
      stream.flush();
      stream.close();
   }

   private static UUID getUUIDFromString(String id) {
      return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
   }

   private static HttpURLConnection createConnection(String par1) throws Exception {
      URL url = new URL(par1);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setUseCaches(false);
      connection.setDoInput(true);
      connection.setDoOutput(true);
      return connection;
   }

   public static void updatePendingProfiles() {
      if (pendingProfiles.size() > 0) {
         UUIDManager.UUIDFetcher thread = new UUIDManager.UUIDFetcher();
         thread.start();
      }

   }

   private static void fetchUUIDsAndInject() {
      int requests = 0;

      for(int var1 = pendingProfiles.size(); var1 > 0; ++requests) {
         var1 -= 100;
      }

      try {
         for(int i = 0; i < requests; ++i) {
            HttpURLConnection connection = createConnection("https://api.mojang.com/profiles/minecraft");
            Gson gson = new Gson();
            String bd = gson.toJson((Object)pendingProfiles.subList(i * 100, Math.min((i + 1) * 100, pendingProfiles.size())));
            writeBody(connection, bd);
            JsonArray array = (JsonArray)jsonParser.parse((Reader)(new InputStreamReader(connection.getInputStream())));
            Iterator i$ = array.iterator();

            while(i$.hasNext()) {
               Object profile = i$.next();
               JsonObject jsonProfile = (JsonObject)profile;
               String id = jsonProfile.get("id").getAsString();
               String name = jsonProfile.get("name").getAsString();
               UUID uuid = getUUIDFromString(id);
               TreeMap var13 = usernameUUIDMapping;
               synchronized(usernameUUIDMapping) {
                  usernameUUIDMapping.put(name, uuid.toString());
               }

               Map var22 = uuidUsernameMapping;
               synchronized(uuidUsernameMapping) {
                  uuidUsernameMapping.put(uuid.toString(), name);
               }
            }

            Thread.sleep(100L);
         }
      } catch (Exception var20) {
         ;
      }

      ArrayList var21 = pendingProfiles;
      synchronized(pendingProfiles) {
         pendingProfiles.clear();
      }
   }

   static {
      usernameUUIDMapping = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      uuidUsernameMapping = new HashMap();
      jsonParser = new JsonParser();
      DEBUG = false;
      pendingProfiles = new ArrayList();
   }

   private static class UUIDFetcher extends Thread {
      private UUIDFetcher() {
      }

      public void run() {
         try {
            UUIDManager.fetchUUIDsAndInject();
         } catch (Exception var2) {
            var2.printStackTrace();
         }

      }
   }
}
