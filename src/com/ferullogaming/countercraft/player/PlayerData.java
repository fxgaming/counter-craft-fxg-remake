package com.ferullogaming.countercraft.player;

import com.f3rullo14.fds.tag.FDSTagCompound;
import com.ferullogaming.countercraft.game.BlockLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class PlayerData {
   public static final float defaultSpread = 0.1F;
   public static final float maxSpread = 12.0F;
   public String username;
   public boolean isShooting = false;
   public boolean isAiming = false;
   public float spread = 0.1F;
   public ArrayList lastSpreads = new ArrayList();
   public boolean isRespawning = false;
   public double flashTime = 0.0D;
   public boolean gunEnabled = true;
   public boolean grenadeEnabled = true;
   public BlockLocation tpLocation;
   public boolean isTeleporting = false;
   public boolean hasVoted = false;
   public int skinIndex = 0;
   public int muzzleTimer = 0;
   public int seenTimer = 0;
   public int calloutTimer = 0;
   public int helmetHealth = 8;
   public int kevlarHealth = 10;
   public int helmetMaxHealth = 8;
   public int kevlarMaxHealth = 10;
   public boolean wearingHelmet = false;
   public boolean wearingKevlar = false;
   public boolean isFrozen = false;
   public BlockLocation frozenLocation;
   public boolean isGhost = false;
   public int ghostDelay = 0;
   public String ghostViewing = null;
   public boolean isInvisible = false;
   public boolean grenadeHit = false;
   public int mmNoGameDelay = 0;
   public int hatWearing = -1;
   public int featuredCoin = -1;
   public boolean hasBomb = false;
   public int gameToClientDelay = 0;

   public PlayerData(String name) {
      this.username = name;
   }

   public void writeToFDS(FDSTagCompound par1) {
      par1.setBoolean("frozen", this.isFrozen);
      par1.setBoolean("ghost", this.isGhost);
      par1.setInteger("hat", this.hatWearing);
      par1.setInteger("coin", this.featuredCoin);
      par1.setInteger("ghosttime", this.ghostDelay);
      par1.setBoolean("hasbomb", this.hasBomb);
      par1.setBoolean("invs", this.isInvisible);
      par1.setBoolean("hasVoted", this.hasVoted);
      par1.setInteger("skinIndex", this.skinIndex);
      par1.setInteger("muzzleTimer", this.muzzleTimer);
      par1.setInteger("seenTimer", this.seenTimer);
      par1.setInteger("calloutTimer", this.calloutTimer);
      par1.setInteger("flashTime", (int)this.flashTime);
      par1.setBoolean("wearingKevlar", this.wearingKevlar);
      par1.setInteger("kevlarHealth", this.kevlarHealth);
      par1.setBoolean("wearingHelmet", this.wearingHelmet);
      par1.setInteger("helmetHealth", this.helmetHealth);
   }

   public void readFromFDS(FDSTagCompound par1) {
      this.isFrozen = par1.getBoolean("frozen");
      this.hatWearing = par1.getInteger("hat");
      this.featuredCoin = par1.getInteger("coin");
      this.hasBomb = par1.getBoolean("hasbomb");
      this.isInvisible = par1.getBoolean("invs");
      this.isGhost = par1.getBoolean("ghost");
      this.hasVoted = par1.getBoolean("hasVoted");
      this.skinIndex = par1.getInteger("skinIndex");
      this.muzzleTimer = par1.getInteger("muzzleTimer");
      this.seenTimer = par1.getInteger("seenTimer");
      this.calloutTimer = par1.getInteger("calloutTimer");
      this.flashTime = (double)par1.getInteger("flashTime");
      this.wearingKevlar = par1.getBoolean("wearingKevlar");
      this.kevlarHealth = par1.getInteger("kevlarHealth");
      this.wearingHelmet = par1.getBoolean("wearingHelmet");
      this.helmetHealth = par1.getInteger("helmetHealth");
   }

   public void setRandomSkindex() {
      Random r = new Random();
      int Low = 0;
      int High = 5;
      this.skinIndex = r.nextInt(High - Low) + Low;
   }

   public void addSpread(float par1) {
      if (!this.isGhost) {
         this.spread += par1;
         if (this.spread > 12.0F) {
            this.spread = 12.0F;
         }

         this.addAverage(this.spread);
      }
   }

   public void decreaseSpread(float par1) {
      if (!this.isGhost) {
         this.spread -= par1;
         if (this.spread < 0.1F) {
            this.spread = 0.1F;
         }

         this.addAverage(this.spread);
      }
   }

   public void setSpread(float par1) {
      if (!this.isGhost) {
         this.spread = par1;
         if (this.spread < 0.1F) {
            this.spread = 0.1F;
         }

         if (this.spread > 12.0F) {
            this.spread = 12.0F;
         }

         this.addAverage(this.spread);
      }
   }

   private void addAverage(float par1) {
      this.lastSpreads.add(this.spread);
      if (this.lastSpreads.size() > 2) {
         this.lastSpreads.remove(0);
      }

   }

   public boolean isSpectating() {
      return this.ghostViewing != null;
   }

   public float getLastSpreadAverage() {
      if (this.lastSpreads != null && this.lastSpreads.size() <= 0) {
         return 0.1F;
      } else {
         try {
            ArrayList list = new ArrayList(this.lastSpreads);
            float var1 = 0.1F;

            float var2;
            for(Iterator i$ = list.iterator(); i$.hasNext(); var1 += var2) {
               var2 = ((Float)i$.next()).floatValue();
            }

            return var1 / (float)list.size();
         } catch (Exception var5) {
            return 0.1F;
         }
      }
   }

   public boolean isFlashed() {
      return this.flashTime > 0.0D;
   }

   public void teleportPlayer(BlockLocation par1) {
      this.isTeleporting = true;
      this.tpLocation = par1;
   }

   public boolean hasVoted() {
      return this.hasVoted;
   }

   public boolean isWearingHelmet() {
      return this.wearingHelmet;
   }

   public boolean isWearingKevlar() {
      return this.wearingKevlar;
   }

   public int getHelmetHealth() {
      return this.helmetHealth;
   }

   public void setHelmetHealth(int helmetHealth) {
      this.helmetHealth = helmetHealth;
   }

   public int getKevlarHealth() {
      return this.kevlarHealth;
   }

   public void setKevlarHealth(int kevlarHealth) {
      this.kevlarHealth = kevlarHealth;
   }
}
