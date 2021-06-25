package com.ferullogaming.countercraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import net.minecraft.client.Minecraft;

public class ClientSessionHandler {
   public String MOD_KEY = "ModSessionHandlers";

   public boolean handleClientSessionLogin() {
      this.MOD_KEY = this.generateNewModKey();
      Minecraft mc = Minecraft.getMinecraft();
      String sessionID = mc.getSession().getSessionID();
      String username = mc.getSession().getUsername();
      if (this.isSessionIDValid(sessionID)) {
         String result = this.sendSessionRequest(username, sessionID);
         return result.equalsIgnoreCase("ok");
      } else {
         return false;
      }
   }

   private boolean isSessionIDValid(String par1) {
      return par1 != null && !par1.equals("-") && !par1.trim().equals("token:0:0") && par1.length() >= 20;
   }

   private String sendSessionRequest(String par1Str, String par2Str) {
      try {
         URL url = new URL("http://session.minecraft.net/game/joinserver.jsp?user=" + this.urlEncode(par1Str) + "&sessionId=" + this.urlEncode(par2Str) + "&serverId=" + this.urlEncode(this.MOD_KEY));
         InputStream inputstream = url.openConnection().getInputStream();
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
         String s3 = bufferedreader.readLine();
         bufferedreader.close();
         return s3;
      } catch (IOException var7) {
         return var7.toString();
      }
   }

   public String generateNewModKey() {
      SecureRandom random = new SecureRandom();
      return (new BigInteger(200, random)).toString(16).substring(0, 40);
   }

   private String sendModIDSessionRequest(String par1Str, String par2) {
      try {
         URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + this.urlEncode(par1Str) + "&serverId=" + this.urlEncode(par2));
         InputStream inputstream = url.openConnection().getInputStream();
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
         String s3 = bufferedreader.readLine();
         bufferedreader.close();
         return s3;
      } catch (IOException var7) {
         return var7.toString();
      }
   }

   public String urlEncode(String par0Str) throws IOException {
      return URLEncoder.encode(par0Str, "UTF-8");
   }
}
