package com.ferullogaming.countercraft.client;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.block.decal.TileEntityDecal;
import com.ferullogaming.countercraft.block.emmiter.TileEntityEmitterSound;
import com.ferullogaming.countercraft.block.prop.TileEntityPropBarrel;
import com.ferullogaming.countercraft.block.prop.TileEntityPropCrateMetal;
import com.ferullogaming.countercraft.block.prop.TileEntityPropCrateWood;
import com.ferullogaming.countercraft.block.prop.TileEntityPropTable;
import com.ferullogaming.countercraft.client.minimap.Minimap;
import com.ferullogaming.countercraft.client.minimap.forge.EventHandlerMinimap;
import com.ferullogaming.countercraft.client.minimap.forge.MinimapConnectionHandler;
import com.ferullogaming.countercraft.client.minimap.forge.MwTickHandler;
import com.ferullogaming.countercraft.client.render.RenderBombEntity;
import com.ferullogaming.countercraft.client.render.RenderBombItem;
import com.ferullogaming.countercraft.client.render.RenderCase;
import com.ferullogaming.countercraft.client.render.RenderCoin;
import com.ferullogaming.countercraft.client.render.RenderEntityPlayerHead;
import com.ferullogaming.countercraft.client.render.RenderGrenade;
import com.ferullogaming.countercraft.client.render.RenderGrenadeFlash;
import com.ferullogaming.countercraft.client.render.RenderGrenadeSmoke;
import com.ferullogaming.countercraft.client.render.RenderSticker;
import com.ferullogaming.countercraft.client.render.RenderStickerCapsule;
import com.ferullogaming.countercraft.client.render.block.RenderModelPropBarrel;
import com.ferullogaming.countercraft.client.render.block.RenderModelPropCrateMetal;
import com.ferullogaming.countercraft.client.render.block.RenderModelPropCrateWood;
import com.ferullogaming.countercraft.client.render.block.RenderModelPropTable;
import com.ferullogaming.countercraft.client.render.block.decal.RenderDecal;
import com.ferullogaming.countercraft.client.render.block.emitter.RenderModelEmitterSound;
import com.ferullogaming.countercraft.client.render.grenades.RenderGrenadeDecoyHand;
import com.ferullogaming.countercraft.client.render.grenades.RenderGrenadeFireHand;
import com.ferullogaming.countercraft.client.render.grenades.RenderGrenadeFlashHand;
import com.ferullogaming.countercraft.client.render.grenades.RenderGrenadeHEHand;
import com.ferullogaming.countercraft.client.render.grenades.RenderGrenadeSmokeHand;
import com.ferullogaming.countercraft.client.render.guns.RenderAK47;
import com.ferullogaming.countercraft.client.render.guns.RenderAWP;
import com.ferullogaming.countercraft.client.render.guns.RenderCZ75;
import com.ferullogaming.countercraft.client.render.guns.RenderDeagle;
import com.ferullogaming.countercraft.client.render.guns.RenderDragunov;
import com.ferullogaming.countercraft.client.render.guns.RenderFAMAS;
import com.ferullogaming.countercraft.client.render.guns.RenderFNFAL;
import com.ferullogaming.countercraft.client.render.guns.RenderFiveSeven;
import com.ferullogaming.countercraft.client.render.guns.RenderG18;
import com.ferullogaming.countercraft.client.render.guns.RenderGalil;
import com.ferullogaming.countercraft.client.render.guns.RenderGun;
import com.ferullogaming.countercraft.client.render.guns.RenderM1911;
import com.ferullogaming.countercraft.client.render.guns.RenderM249;
import com.ferullogaming.countercraft.client.render.guns.RenderM4A1;
import com.ferullogaming.countercraft.client.render.guns.RenderMAC10;
import com.ferullogaming.countercraft.client.render.guns.RenderMag7;
import com.ferullogaming.countercraft.client.render.guns.RenderNova;
import com.ferullogaming.countercraft.client.render.guns.RenderP250;
import com.ferullogaming.countercraft.client.render.guns.RenderP90;
import com.ferullogaming.countercraft.client.render.guns.RenderR8;
import com.ferullogaming.countercraft.client.render.guns.RenderSSG08;
import com.ferullogaming.countercraft.client.render.guns.RenderSawedoff;
import com.ferullogaming.countercraft.client.render.guns.RenderScar20;
import com.ferullogaming.countercraft.client.render.guns.RenderTec9;
import com.ferullogaming.countercraft.client.render.guns.RenderUMP45;
import com.ferullogaming.countercraft.client.render.guns.RenderVector;
import com.ferullogaming.countercraft.client.render.knives.RenderKnifeBayonet;
import com.ferullogaming.countercraft.client.render.knives.RenderKnifeButterfly;
import com.ferullogaming.countercraft.client.render.knives.RenderKnifeKarambit;
import com.ferullogaming.countercraft.client.render.knives.RenderKnifeTactical;
import com.ferullogaming.countercraft.entity.EntityBomb;
import com.ferullogaming.countercraft.entity.EntityGrenade;
import com.ferullogaming.countercraft.entity.EntityGrenadeDecoy;
import com.ferullogaming.countercraft.entity.EntityGrenadeFire;
import com.ferullogaming.countercraft.entity.EntityGrenadeFlash;
import com.ferullogaming.countercraft.entity.EntityGrenadeSmoke;
import com.ferullogaming.countercraft.entity.EntityPlayerHead;
import com.ferullogaming.countercraft.item.ItemManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy {
   private static Map gunRenderers = new HashMap();

   public static RenderGun getGunRenderer(int par1) {
      return (RenderGun)gunRenderers.get(par1);
   }

   public ClientProxy() {
   }
   
   public void pre() {
	   RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPlayerCCR()); 
   }
   
   public void load() {
      this.registerGunRenderer(ItemManager.m4a1.itemID, new RenderM4A1());
      this.registerGunRenderer(ItemManager.ak47.itemID, new RenderAK47());
      this.registerGunRenderer(ItemManager.famas.itemID, new RenderFAMAS());
      this.registerGunRenderer(ItemManager.fnfal.itemID, new RenderFNFAL());
      this.registerGunRenderer(ItemManager.galil.itemID, new RenderGalil());
      this.registerGunRenderer(ItemManager.nova.itemID, new RenderNova());
      this.registerGunRenderer(ItemManager.sawedoff.itemID, new RenderSawedoff());
      this.registerGunRenderer(ItemManager.mag7.itemID, new RenderMag7());
      this.registerGunRenderer(ItemManager.m249.itemID, new RenderM249());
      this.registerGunRenderer(ItemManager.m1911.itemID, new RenderM1911());
      this.registerGunRenderer(ItemManager.g18.itemID, new RenderG18());
      this.registerGunRenderer(ItemManager.deagle.itemID, new RenderDeagle());
      this.registerGunRenderer(ItemManager.p250.itemID, new RenderP250());
      this.registerGunRenderer(ItemManager.tec9.itemID, new RenderTec9());
      this.registerGunRenderer(ItemManager.cz75.itemID, new RenderCZ75());
      this.registerGunRenderer(ItemManager.fiveSeven.itemID, new RenderFiveSeven());
      this.registerGunRenderer(ItemManager.r8.itemID, new RenderR8());
      this.registerGunRenderer(ItemManager.p90.itemID, new RenderP90());
      this.registerGunRenderer(ItemManager.mac10.itemID, new RenderMAC10());
      this.registerGunRenderer(ItemManager.vector.itemID, new RenderVector());
      this.registerGunRenderer(ItemManager.ump45.itemID, new RenderUMP45());
      this.registerGunRenderer(ItemManager.awp.itemID, new RenderAWP());
      this.registerGunRenderer(ItemManager.dragunov.itemID, new RenderDragunov());
      this.registerGunRenderer(ItemManager.scar20.itemID, new RenderScar20());
      this.registerGunRenderer(ItemManager.ssg08.itemID, new RenderSSG08());
      MinecraftForgeClient.registerItemRenderer(ItemManager.bomb.itemID, new RenderBombItem());
      RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, new RenderBombEntity());
      MinecraftForgeClient.registerItemRenderer(ItemManager.caseAlpha.itemID, new RenderCase("case_alpha"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.caseAlpha2.itemID, new RenderCase("case_alpha2"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinBetaTester.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinAlphaTester.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinDiscord.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinSkinner.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinDev.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinSupporter.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinModeler.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinStaff.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinPrestige.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.coinAssetDev.itemID, new RenderCoin());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickerCamper.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickerChickenStrike.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickerDinked.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickeriBuyPowerKatowice2014.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickerKawaiiKiller.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickerLucky13.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.stickerHowl.itemID, new RenderSticker());
      MinecraftForgeClient.registerItemRenderer(ItemManager.capsuleAlpha.itemID, new RenderStickerCapsule("capsule_alpha"));
      RenderingRegistry.registerEntityRenderingHandler(EntityPlayerHead.class, new RenderEntityPlayerHead());
      RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
      RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeFlash.class, new RenderGrenadeFlash());
      RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeDecoy.class, new RenderGrenadeFlash("grenadedecoy"));
      RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeSmoke.class, new RenderGrenadeSmoke());
      RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeFire.class, new RenderGrenadeSmoke("grenadefire"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.knifeTactical.itemID, new RenderKnifeTactical("tactical"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.knifeTacticalDisplay.itemID, new RenderKnifeTactical("tactical_display"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.knifeBayonet.itemID, new RenderKnifeBayonet("bayonet"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.knifeKarambit.itemID, new RenderKnifeKarambit("karambit"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.knifeButterfly.itemID, new RenderKnifeButterfly("butterfly"));
      MinecraftForgeClient.registerItemRenderer(ItemManager.grenadeFlash.itemID, new RenderGrenadeFlashHand());
      MinecraftForgeClient.registerItemRenderer(ItemManager.grenadeDecoy.itemID, new RenderGrenadeDecoyHand());
      MinecraftForgeClient.registerItemRenderer(ItemManager.grenadeFrag.itemID, new RenderGrenadeHEHand());
      MinecraftForgeClient.registerItemRenderer(ItemManager.grenadeSmoke.itemID, new RenderGrenadeSmokeHand());
      MinecraftForgeClient.registerItemRenderer(ItemManager.grenadeFire.itemID, new RenderGrenadeFireHand());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPropCrateWood.class, new RenderModelPropCrateWood());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPropCrateMetal.class, new RenderModelPropCrateMetal());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPropBarrel.class, new RenderModelPropBarrel());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPropTable.class, new RenderModelPropTable());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecal.class, new RenderDecal());
      ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEmitterSound.class, new RenderModelEmitterSound());

      Minimap mw = new Minimap(CounterCraft.config);
      MinecraftForge.EVENT_BUS.register(new EventHandlerMinimap(mw));
      NetworkRegistry.instance().registerConnectionHandler(new MinimapConnectionHandler(mw));
      TickRegistry.registerTickHandler(new MwTickHandler(mw), Side.CLIENT);
   }

   public void registerGunRenderer(int par1, RenderGun par2) {
      MinecraftForgeClient.registerItemRenderer(par1, par2);
      gunRenderers.put(par1, par2);
   }
}
