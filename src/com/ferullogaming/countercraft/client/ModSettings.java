package com.ferullogaming.countercraft.client;

import com.f3rullo14.fds.tag.FDSHelper;
import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.CounterCraft;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ModSettings {
   public int USERS_ONLINE = 0;
   public String CROSSHAIR_DEFAULT = "Default";
   public ArrayList CROSSHAIR_LIST = new ArrayList();
   private String dir = CounterCraft.getClientManager().getDirectory() + "client/";

   public static ModSettings instance() {
      return ClientManager.instance().getModSettings();
   }

   public void loadSettings() {
      FDSTagCompound tag = FDSHelper.loadFDSTagCompound(this.dir + "settings");
      Instrumentarium.setOnlinePlayersValue();
      this.loadCrosshairs();
      this.CROSSHAIR_DEFAULT = tag.hasTag("crosshair") ? tag.getString("crosshair") : "Default";
      if (this.getDefaultCrosshair() == null) {
         this.CROSSHAIR_DEFAULT = "Default";
      }
   }

   public void saveSettings() {
      FDSTagCompound tag = new FDSTagCompound("settings");
      tag.setString("crosshair", this.CROSSHAIR_DEFAULT);
      FDSHelper.saveFDSTagCompound(this.dir + "settings", tag);
   }

   public void loadCrosshairs() {
      this.CROSSHAIR_LIST.clear();
      File dir1 = new File(this.dir + "crosshairs/");
      if (dir1.exists() && dir1.listFiles().length > 0) {
         File[] arr$ = dir1.listFiles();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            File crosshairFile = arr$[i$];
            Crosshair crosshair = new Crosshair(crosshairFile);
            if (crosshair.loadFromFile()) {
               this.CROSSHAIR_LIST.add(crosshair);
            }
         }
      }

      System.out.println(this.CROSSHAIR_LIST.size() + " Crosshairs Loaded.");
   }

   public Crosshair getDefaultCrosshair() {
      Iterator i$ = this.CROSSHAIR_LIST.iterator();

      Crosshair var1;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         var1 = (Crosshair)i$.next();
      } while(!var1.name.equalsIgnoreCase(this.CROSSHAIR_DEFAULT));

      return var1;
   }
}
