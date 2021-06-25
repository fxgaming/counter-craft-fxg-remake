package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.item.gun.ItemGun;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import paulscode.sound.SoundSystem;

public class SoundHandler {
   public static ArrayList soundFiles;
   public static SoundPool soundPoolMusic;

   public SoundHandler() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   public static void playMenuMusic() {
      Minecraft mc = Minecraft.getMinecraft();
      SoundManager soundManager = mc.sndManager;
      SoundSystem soundHandler = soundManager.sndSystem;
      if (!soundManager.sndSystem.playing("BGMusic") && !soundManager.sndSystem.playing("streaming")) {
         soundManager.stopAllSounds();
         SoundPoolEntry soundpoolentry = soundPoolMusic.getRandomSound();
         if (soundpoolentry != null) {
            soundManager.sndSystem.backgroundMusic("BGMusic", soundpoolentry.getSoundUrl(), soundpoolentry.getSoundName(), false);
            soundManager.sndSystem.setVolume("BGMusic", Minecraft.getMinecraft().gameSettings.musicVolume);
            soundManager.sndSystem.play("BGMusic");
         }
      } else {
         soundManager.sndSystem.setVolume("BGMusic", Minecraft.getMinecraft().gameSettings.musicVolume);
      }

   }

   public static void stopMenuMusic() {
      if (Minecraft.getMinecraft().sndManager.sndSystem.playing("BGMusic")) {
         Minecraft.getMinecraft().sndManager.sndSystem.stop("BGMusic");
      }

   }

   public void addSound(String str) {
      soundFiles.add(str);
   }

   @ForgeSubscribe
   public void onSoundsLoaded(SoundLoadEvent evt) {
      soundFiles = new ArrayList();
      soundPoolMusic = new SoundPool(Minecraft.getMinecraft().getResourceManager(), "music", true);
      this.addSound("gunEmpty");
      this.addSound("gunFiremode");
      this.addSound("gunPulled");
      this.addSound("knifewhoosh1");
      this.addSound("knifewhoosh2");
      this.addSound("knifewhoosh3");
      this.addSound("impact1");
      this.addSound("impact2");
      this.addSound("impact3");
      this.addSound("bullet/impact1");
      this.addSound("bullet/impact2");
      this.addSound("bullet/impact3");
      this.addSound("bullet/impact4");
      this.addSound("grenadeThrow");
      this.addSound("grenadeSmokeBurst");
      this.addSound("grenadeFlashBurst1");
      this.addSound("grenadeFlashBurst2");
      this.addSound("grenadeFlashBurst3");
      this.addSound("grenadeExplode1");
      this.addSound("grenadeExplode2");
      this.addSound("grenadeExplode3");
      this.addSound("gui/vote/voteStarted");
      this.addSound("gui/vote/voteFailure");
      this.addSound("gui/vote/voteSuccess");
      this.addSound("gui/vote/voteYes");
      this.addSound("gui/vote/voteNo");
      this.addSound("gui/buttonHover");
      this.addSound("gui/buttonPress");
      this.addSound("gui/inspectItem");
      this.addSound("gui/newItem");
      this.addSound("gui/newItemRare");
      this.addSound("gui/chat");
      this.addSound("gui/comprank");
      this.addSound("gui/comprankclose");
      this.addSound("emitter/birds1");
      this.addSound("emitter/birds2");
      this.addSound("emitter/birds3");
      this.addSound("emitter/birds4");
      this.addSound("emitter/birds5");
      this.addSound("emitter/birds6");
      this.addSound("emitter/birds7");
      this.addSound("emitter/birds8");
      this.addSound("emitter/wind1");
      this.addSound("emitter/wind2");
      this.addSound("emitter/wind3");
      this.addSound("emitter/dog1");
      this.addSound("emitter/dog2");
      this.addSound("emitter/helicopter1");
      this.addSound("emitter/war1");
      this.addSound("emitter/war2");
      this.addSound("emitter/war3");
      this.addSound("emitter/night1");
      this.addSound("emitter/night2");
      this.addSound("emitter/city1");
      this.addSound("emitter/city2");
      this.addSound("emitter/shop1");
      this.addSound("emitter/shop2");
      this.addSound("misc/hitmarker");
      this.addSound("misc/helmet");
      this.addSound("misc/kevlar");
      this.addSound("match/countdown");
      this.addSound("match/startmusic");
      this.addSound("match/music_victory");
      this.addSound("match/music_defeat");
      this.addSound("gui/menu/music");
      this.addSound("entity/human/step/grass0");
      this.addSound("entity/human/step/grass1");
      this.addSound("entity/human/step/grass2");
      this.addSound("entity/human/step/grass3");
      this.addSound("entity/human/step/grass4");
      this.addSound("entity/human/step/gravel0");
      this.addSound("entity/human/step/gravel1");
      this.addSound("entity/human/step/gravel2");
      this.addSound("entity/human/step/gravel3");
      this.addSound("entity/human/step/gravel4");
      this.addSound("entity/human/step/metal0");
      this.addSound("entity/human/step/metal1");
      this.addSound("entity/human/step/metal2");
      this.addSound("entity/human/step/metal3");
      this.addSound("entity/human/step/metal4");
      this.addSound("entity/human/step/sand0");
      this.addSound("entity/human/step/sand1");
      this.addSound("entity/human/step/sand2");
      this.addSound("entity/human/step/sand3");
      this.addSound("entity/human/step/sand4");
      this.addSound("entity/human/step/snow0");
      this.addSound("entity/human/step/snow1");
      this.addSound("entity/human/step/snow2");
      this.addSound("entity/human/step/snow3");
      this.addSound("entity/human/step/snow4");
      this.addSound("entity/human/step/stone0");
      this.addSound("entity/human/step/stone1");
      this.addSound("entity/human/step/stone2");
      this.addSound("entity/human/step/stone3");
      this.addSound("entity/human/step/stone4");
      this.addSound("entity/human/step/wood0");
      this.addSound("entity/human/step/wood1");
      this.addSound("entity/human/step/wood2");
      this.addSound("entity/human/step/wood3");
      this.addSound("entity/human/step/wood4");
      this.addSound("entity/human/headshot0");
      this.addSound("entity/human/headshot1");
      this.addSound("entity/human/headshot2");
      this.addSound("entity/human/headshothelmet0");
      this.addSound("entity/human/headshothelmet1");
      this.addSound("entity/human/headshothelmet2");
      this.addSound("entity/human/headshothelmet3");
      this.addSound("entity/human/kevlarshot0");
      this.addSound("entity/human/kevlarshot1");
      this.addSound("entity/human/kevlarshot2");
      this.addSound("callout/red/grenadefirebomb1");
      this.addSound("callout/red/grenadefirebomb2");
      this.addSound("callout/red/grenadefirebomb3");
      this.addSound("callout/red/grenadeflashbang1");
      this.addSound("callout/red/grenadeflashbang2");
      this.addSound("callout/red/grenadeflashbang3");
      this.addSound("callout/red/grenadehegrenade1");
      this.addSound("callout/red/grenadehegrenade2");
      this.addSound("callout/red/grenadehegrenade3");
      this.addSound("callout/red/grenadesmoke1");
      this.addSound("callout/red/grenadesmoke2");
      this.addSound("callout/red/grenadesmoke3");
      this.addSound("callout/red/grenadedecoy1");
      this.addSound("callout/red/grenadedecoy2");
      this.addSound("callout/red/grenadedecoy3");
      this.addSound("callout/red/victory");
      this.addSound("callout/red/defeat");
      this.addSound("callout/blue/grenadefirebomb1");
      this.addSound("callout/blue/grenadefirebomb2");
      this.addSound("callout/blue/grenadefirebomb3");
      this.addSound("callout/blue/grenadeflashbang1");
      this.addSound("callout/blue/grenadeflashbang2");
      this.addSound("callout/blue/grenadeflashbang3");
      this.addSound("callout/blue/grenadehegrenade1");
      this.addSound("callout/blue/grenadehegrenade2");
      this.addSound("callout/blue/grenadehegrenade3");
      this.addSound("callout/blue/grenadesmoke1");
      this.addSound("callout/blue/grenadesmoke2");
      this.addSound("callout/blue/grenadesmoke3");
      this.addSound("callout/blue/grenadedecoy1");
      this.addSound("callout/blue/grenadedecoy2");
      this.addSound("callout/blue/grenadedecoy3");
      this.addSound("callout/blue/victory");
      this.addSound("callout/blue/defeat");
      this.addSound("entity/bomb/tick");
      this.addSound("entity/bomb/ignite");
      this.addSound("entity/bomb/plant");
      this.addSound("entity/bomb/plantstart");
      this.addSound("entity/bomb/planted");
      this.addSound("entity/bomb/explode");
      this.addSound("entity/bomb/defuse");
      this.addSound("entity/defusekit/use");

      for(int i = 0; i < Item.itemsList.length; ++i) {
         if (Item.itemsList[i] != null && Item.itemsList[i] instanceof ItemGun) {
            ItemGun gun = (ItemGun)Item.itemsList[i];
            if (gun.soundFire != null && gun.soundFire.length() > 0) {
               this.addSound(gun.soundFire.trim().toLowerCase());
            }

            if (gun.soundFire != null && gun.soundFire.length() > 0) {
               this.addSound(gun.soundDistant.trim().toLowerCase());
            }

            if (gun.soundFireSuppressed != null && gun.soundFireSuppressed.length() > 0) {
               this.addSound(gun.soundFireSuppressed.trim().toLowerCase());
            }

            if (gun.soundReload != null && gun.soundReload.length() > 0) {
               this.addSound(gun.soundReload.trim().toLowerCase());
            }

            if (gun.soundSecondary != null && gun.soundSecondary.length() > 0) {
               this.addSound(gun.soundSecondary.trim().toLowerCase());
            }
         }
      }

      SoundManager manager = evt.manager;
      boolean flag = true;
      Iterator i$ = soundFiles.iterator();

      while(i$.hasNext()) {
         String sound = (String)i$.next();

         try {
            String soundName = "countercraft:" + sound + ".ogg";
            manager.addSound(soundName);
         } catch (Exception var7) {
            System.out.println("FAILED to load sound: " + sound);
            flag = false;
         }
      }

      if (!flag) {
         System.out.println("Counter Craft SOUND Failed to load!");
      }

      soundPoolMusic.addSound("countercraft:menu.ogg");
   }
}
