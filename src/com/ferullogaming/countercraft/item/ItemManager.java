package com.ferullogaming.countercraft.item;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.anim.GunAnimationFiredAWP;
import com.ferullogaming.countercraft.client.anim.GunAnimationInspectPistol;
import com.ferullogaming.countercraft.client.anim.GunAnimationInspectRifle;
import com.ferullogaming.countercraft.client.anim.GunAnimationInspectSMG;
import com.ferullogaming.countercraft.client.anim.GunAnimationPistolFired;
import com.ferullogaming.countercraft.client.anim.GunAnimationReloadAWP;
import com.ferullogaming.countercraft.client.anim.GunAnimationReloadGalil;
import com.ferullogaming.countercraft.client.anim.GunAnimationReloadM249;
import com.ferullogaming.countercraft.client.anim.GunAnimationReloadP90;
import com.ferullogaming.countercraft.client.anim.GunAnimationReloadPistol;
import com.ferullogaming.countercraft.client.anim.GunAnimationRifleFired;
import com.ferullogaming.countercraft.client.anim.GunAnimationSMGFired;
import com.ferullogaming.countercraft.client.anim.KnifeAnimationInspect;
import com.ferullogaming.countercraft.client.anim.KnifeAnimationInspectButterfly;
import com.ferullogaming.countercraft.client.anim.KnifeAnimationInspectKarambit;
import com.ferullogaming.countercraft.client.anim.KnifeAnimationInspectTac;
import com.ferullogaming.countercraft.item.gun.EnumFiremode;
import com.ferullogaming.countercraft.item.gun.ItemGun;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
/**
 * Все названия оружия(цифры) увеличины на 1 из-за бага мода BetterFonts
 * @author fxg
 */
public class ItemManager {
   public static CreativeTabs tabCounterCraft = new CreativeTabCounterCraft("cc");
   public static CreativeTabs tabCounterCraftProps = new CreativeTabCounterCraftProps("ccprops");
   public static CreativeTabs tabCounterCraftEmitters = new CreativeTabCounterCraftEmitters("ccemitters");
   public static CreativeTabs tabCounterCraftBlocks = new CreativeTabCounterCraftBlocks("ccblocks");
   public static CreativeTabs tabCounterCraftDecals = new CreativeTabCounterCraftDecals("ccdecals");
   public static ItemGun m4a1;
   public static ItemGun ak47;
   public static ItemGun famas;
   public static ItemGun fnfal;
   public static ItemGun galil;
   public static ItemGun nova;
   public static ItemGun sawedoff;
   public static ItemGun mag7;
   public static ItemGun m249;
   public static ItemGun m1911;
   public static ItemGun g18;
   public static ItemGun deagle;
   public static ItemGun p250;
   public static ItemGun tec9;
   public static ItemGun cz75;
   public static ItemGun fiveSeven;
   public static ItemGun r8;
   public static ItemGun p90;
   public static ItemGun mac10;
   public static ItemGun vector;
   public static ItemGun ump45;
   public static ItemGun awp;
   public static ItemGun dragunov;
   public static ItemGun scar20;
   public static ItemGun ssg08;
   public static Item knifeTacticalDisplay;
   public static Item knifeTactical;
   public static Item knifeBayonet;
   public static Item knifeKarambit;
   public static Item knifeButterfly;
   public static Item grenadeFlash;
   public static Item grenadeDecoy;
   public static Item grenadeSmoke;
   public static Item grenadeFire;
   public static Item grenadeFrag;
   public static Item bomb;
   public static Item bombKit;
   public static Item armorKevlar;
   public static Item armorHelmet;
   public static Item caseAlpha;
   public static Item caseAlpha2;
   public static Item capsuleAlpha;
   public static Item stickerCamper;
   public static Item stickerChickenStrike;
   public static Item stickerDinked;
   public static Item stickeriBuyPowerKatowice2014;
   public static Item stickerKawaiiKiller;
   public static Item stickerLucky13;
   public static Item stickerHowl;
   public static Item tradeContract;
   public static Item nameTag;
   public static Item coinAlphaTester;
   public static Item coinBetaTester;
   public static Item coinDiscord;
   public static Item coinSkinner;
   public static Item coinDev;
   public static Item coinStaff;
   public static Item coinSupporter;
   public static Item coinModeler;
   public static Item coinPrestige;
   public static Item coinAssetDev;
   public static Item boosterEXPx2;
   public static Item boosterEXPx3;
   public static Item boosterCoinx2;
   public static Item gameItemInfectedDeadHealth;
   public static Item gameItemInfectedDeadSpeed;
   public static Item gameItemInfectedDeadVanish;
   public static Item gameItemInfectedDeadDamage;
   public static Item gameItemInfectedLivingHealth;
   private BuyableManager buyableManager;

   public static ItemCC getItemFromName(String par1) {
      for(int i = 0; i < Item.itemsList.length; ++i) {
         Item item = Item.itemsList[i];
         if (item != null && item instanceof ItemCC && ((ItemCC)item).getDisplayName((ItemStack)null).equalsIgnoreCase(par1)) {
            return (ItemCC)item;
         }
      }
      return null;
   }

   public static ItemManager instance() {
      return CounterCraft.instance.itemManager;
   }

   private void initItems() {
	  m4a1 = (ItemGun) new ItemGun(4000, 575.0D, 0.8D, 12.0F, 1).setFiremode(EnumFiremode.AUTO).setDamage(7, 17).setSpreadFired(1.7F).setSpreadIncreaseVariables(0.4F, 0.8F, 2.0F).setSpreadDecreaseVariables(0.7F, 1.1F).setSpreadMaxIncreaseVariables(5.0F, 100.0F).setMovementPenalty(0.13D).setAmmunitionClips(30, 3).setSoundEffects("m4a1shoot", "m4a1shootsuppressed", "m4a1reload", "m4a1secondary").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setHasSecondary().setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("M4-A4").setCCItemTextureName("m4a1");
	  ak47 = (ItemGun) new ItemGun(4001, 550.0D, 0.7D, 12.0F, 2).setFiremode(EnumFiremode.AUTO).setDamage(8, 20).setSpreadFired(1.8F).setSpreadIncreaseVariables(0.5F, 0.9F, 2.0F).setSpreadDecreaseVariables(0.6F, 1.1F).setSpreadMaxIncreaseVariables(6.5F, 100.0F).setMovementPenalty(0.13D).setAmmunitionClips(30, 3).setSoundEffects("ak47shoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("AK-47").setCCItemTextureName("ak47");
	  famas = (ItemGun) new ItemGun(4002, 575.0D, 1.0D, 4.5F, 3).setFiremode(EnumFiremode.AUTO).setDamage(4, 7).setSpreadFired(2.2F).setSpreadIncreaseVariables(0.4F, 0.8F, 2.0F).setSpreadDecreaseVariables(0.7F, 1.25F).setSpreadMaxIncreaseVariables(4.0F, 100.0F).setMovementPenalty(0.11D).setAmmunitionClips(25, 3).setSoundEffects("famasshoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("Famas").setCCItemTextureName("famas");
	  fnfal = (ItemGun) new ItemGun(4003, 450.0D, 0.8D, 12.0F, 4).setFiremode(EnumFiremode.SEMI).setDamage(7, 14).setSpreadFired(2.2F).setSpreadIncreaseVariables(0.3F, 0.7F, 2.0F).setSpreadDecreaseVariables(1.0F, 1.5F).setSpreadMaxIncreaseVariables(5.0F, 100.0F).setMovementPenalty(0.13D).setAmmunitionClips(20, 4).setSoundEffects("fnfalshoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("FN-FAL").setCCItemTextureName("fnfal");
	  galil = (ItemGun) new ItemGun(4004, 585.0D, 0.8D, 14.0F, 5).setFiremode(EnumFiremode.AUTO).setDamage(4, 11).setSpreadFired(1.4F).setSpreadIncreaseVariables(0.4F, 0.8F, 2.0F).setSpreadDecreaseVariables(0.7F, 1.1F).setSpreadMaxIncreaseVariables(5.0F, 100.0F).setMovementPenalty(0.13D).setAmmunitionClips(35, 2).setSoundEffects("galilshoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAnimationReload(GunAnimationReloadGalil.class).setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("Galil-AR").setCCItemTextureName("galil");
	  m1911 = (ItemGun) new ItemGun(4010, 450.0D, 0.5D, 9.0F, 6).setFiremode(EnumFiremode.SEMI).setDamage(5, 8).setSpreadFired(2.0F).setSpreadIncreaseVariables(0.2F, 0.6F, 2.0F).setSpreadDecreaseVariables(0.6F, 1.0F).setSpreadMaxIncreaseVariables(2.0F, 3.5F).setAmmunitionClips(14, 3).setSoundEffects("m1911shoot", "", "m1911reload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.02D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("M1911").setCCItemTextureName("m1911");
	  g18 = (ItemGun) new ItemGun(4011, 600.0D, 0.6D, 11.0F, 7).setFiremode(EnumFiremode.SEMI).setDamage(5, 8).setSpreadFired(2.0F).setSpreadIncreaseVariables(0.3F, 0.7F, 3.0F).setSpreadDecreaseVariables(0.7F, 0.9F).setSpreadMaxIncreaseVariables(3.0F, 5.5F).setAmmunitionClips(20, 5).setSoundEffects("g18shoot", "", "m1911reload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.02D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("G18").setCCItemTextureName("g18");
	  deagle = (ItemGun) new ItemGun(4012, 310.0D, 0.8D, 30.0F, 8).setFiremode(EnumFiremode.SEMI).setDamage(6, 23).setSpreadFired(11.0F).setSpreadIncreaseVariables(1.2F, 2.0F, 6.0F).setSpreadDecreaseVariables(0.8F, 1.0F).setSpreadMaxIncreaseVariables(8.0F, 100.0F).setAmmunitionClips(7, 3).setSoundEffects("deagleshoot", "", "m1911reload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.07D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("Deagle").setCCItemTextureName("deagle");
	  p250 = (ItemGun) new ItemGun(4013, 500.0D, 0.7D, 9.0F, 9).setFiremode(EnumFiremode.SEMI).setDamage(4, 10).setSpreadFired(2.0F).setSpreadIncreaseVariables(0.4F, 0.6F, 2.0F).setSpreadDecreaseVariables(0.8F, 0.9F).setSpreadMaxIncreaseVariables(2.0F, 5.0F).setAmmunitionClips(13, 4).setSoundEffects("p250shoot", "", "m1911reload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.02D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("P250").setCCItemTextureName("p250");
	  tec9 = (ItemGun) new ItemGun(4014, 600.0D, 0.7D, 9.0F, 10).setFiremode(EnumFiremode.SEMI).setDamage(7, 10).setSpreadFired(1.75F).setSpreadIncreaseVariables(0.2F, 0.8F, 2.0F).setSpreadDecreaseVariables(0.5F, 0.7F).setSpreadMaxIncreaseVariables(2.5F, 4.5F).setAmmunitionClips(20, 3).setSoundEffects("tec9shoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.02D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("TEC-9").setCCItemTextureName("tec9");
	  cz75 = (ItemGun) new ItemGun(4015, 600.0D, 0.6D, 10.0F, 11).setFiremode(EnumFiremode.AUTO).setDamage(6, 9).setSpreadFired(2.0F).setSpreadIncreaseVariables(0.3F, 0.7F, 2.0F).setSpreadDecreaseVariables(0.7F, 0.9F).setSpreadMaxIncreaseVariables(2.0F, 4.5F).setAmmunitionClips(12, 3).setSoundEffects("cz75shoot", "", "m1911reload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.02D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("CZ75").setCCItemTextureName("cz75");
	  fiveSeven = (ItemGun) new ItemGun(4016, 600.0D, 0.7D, 9.0F, 12).setFiremode(EnumFiremode.SEMI).setDamage(5, 8).setSpreadFired(2.0F).setSpreadIncreaseVariables(0.4F, 0.6F, 2.0F).setSpreadDecreaseVariables(0.8F, 0.9F).setSpreadMaxIncreaseVariables(2.0F, 5.0F).setAmmunitionClips(20, 3).setSoundEffects("fivesevenshoot", "", "fivesevenreload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.02D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("Five-Seven").setCCItemTextureName("fiveseven");
	  r8 = (ItemGun) new ItemGun(4017, 70.0D, 0.8D, 28.0F, 13).setFiremode(EnumFiremode.SEMI).setDamage(16, 22).setSpreadFired(12.0F).setSpreadIncreaseVariables(1.2F, 2.0F, 6.0F).setSpreadDecreaseVariables(0.4F, 0.8F).setSpreadMaxIncreaseVariables(8.0F, 100.0F).setAmmunitionClips(8, 3).setSoundEffects("r8shoot", "", "r8reload", "").setAnimationFired(GunAnimationPistolFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectPistol.class).setMovementPenalty(0.15D).setSecondaryGun().setCCBuyType(EnumBuyType.PISTOL).setCCItemDisplayName("R8 Revolver").setCCItemTextureName("r8");
	  p90 = (ItemGun) new ItemGun(4020, 725.0D, 0.7D, 10.0F, 14).setFiremode(EnumFiremode.AUTO).setDamage(5, 7).setSpreadFired(1.7F).setSpreadIncreaseVariables(0.2F, 0.55F, 2.0F).setSpreadDecreaseVariables(0.7F, 0.8F).setSpreadMaxIncreaseVariables(4.0F, 25.0F).setAmmunitionClips(50, 3).setSoundEffects("p90shoot", "", "p90reload", "").setAnimationFired(GunAnimationSMGFired.class).setAnimationReload(GunAnimationReloadP90.class).setAnimationInspecting(GunAnimationInspectSMG.class).setMovementPenalty(0.17D).setCCBuyType(EnumBuyType.SMG).setCCItemDisplayName("P90").setCCItemTextureName("p90");
	  mac10 = (ItemGun) new ItemGun(4021, 600.0D, 0.8D, 7.0F, 15).setFiremode(EnumFiremode.AUTO).setDamage(4, 6).setSpreadFired(1.8F).setSpreadIncreaseVariables(0.2F, 0.5F, 2.0F).setSpreadDecreaseVariables(0.7F, 0.8F).setSpreadMaxIncreaseVariables(4.0F, 4.0F).setMovementPenalty(0.07D).setAmmunitionClips(30, 3).setSoundEffects("mac10shoot", "", "m4a1reload", "").setAnimationFired(GunAnimationSMGFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectSMG.class).setCCBuyType(EnumBuyType.SMG).setCCItemDisplayName("MAC-10").setCCItemTextureName("mac10");
	  vector = (ItemGun) new ItemGun(4022, 650.0D, 0.7D, 7.0F, 16).setFiremode(EnumFiremode.AUTO).setDamage(3, 8).setSpreadFired(1.7F).setSpreadIncreaseVariables(0.1F, 0.45F, 2.2F).setSpreadDecreaseVariables(0.7F, 0.8F).setSpreadMaxIncreaseVariables(3.0F, 4.0F).setMovementPenalty(0.07D).setAmmunitionClips(30, 3).setSoundEffects("vectorshoot", "", "m4a1reload", "").setAnimationFired(GunAnimationSMGFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectSMG.class).setCCBuyType(EnumBuyType.SMG).setCCItemDisplayName("Vector").setCCItemTextureName("vector");
	  ump45 = (ItemGun) new ItemGun(4023, 500.0D, 1.0D, 8.0F, 17).setFiremode(EnumFiremode.AUTO).setDamage(6, 7).setSpreadFired(1.8F).setSpreadIncreaseVariables(0.2F, 0.7F, 3.0F).setSpreadDecreaseVariables(0.7F, 0.8F).setSpreadMaxIncreaseVariables(3.0F, 6.0F).setMovementPenalty(0.17D).setAmmunitionClips(25, 3).setSoundEffects("ump45shoot", "", "m4a1reload", "").setAnimationFired(GunAnimationSMGFired.class).setAnimationReload(GunAnimationReloadPistol.class).setAnimationInspecting(GunAnimationInspectSMG.class).setCCBuyType(EnumBuyType.SMG).setCCItemDisplayName("UMP-45").setCCItemTextureName("ump45");
	  awp = (ItemGun) new ItemGun(4030, 40.0D, 0.95D, 28.0F, 18).setFiremode(EnumFiremode.SEMI).setDamage(30, 60).setSpreadFired(35.0F).setSpreadIncreaseVariables(2.6F, 4.6F, 8.0F).setSpreadDecreaseVariables(0.4F, 0.6F).setSpreadMaxIncreaseVariables(20.0F, 100.0F).setMovementPenalty(0.45D).setAmmunitionClips(10, 2).setSoundEffects("awpshoot", "", "awpreload", "").setAnimationFired(GunAnimationFiredAWP.class).setAnimationReload(GunAnimationReloadAWP.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAimable(1.0F).setRenderCrosshairs(false).setSightTexture("scope").setSightTextureBlur("scopeblur").setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("AWP").setCCItemTextureName("awp");
	  dragunov = (ItemGun) new ItemGun(4031, 250.0D, 0.8D, 16.0F, 19).setFiremode(EnumFiremode.SEMI).setDamage(15, 35).setSpreadFired(5.0F).setSpreadIncreaseVariables(0.8F, 1.9F, 5.0F).setSpreadDecreaseVariables(0.5F, 0.9F).setSpreadMaxIncreaseVariables(6.0F, 100.0F).setMovementPenalty(0.16D).setAmmunitionClips(10, 3).setSoundEffects("dragunovshoot", "", "awpreload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAimable(2.0F).setRenderCrosshairs(false).setSightTexture("scope").setSightTextureBlur("scopeblur").setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("Dragunov").setCCItemTextureName("dragunov");
	  scar20 = (ItemGun) new ItemGun(4032, 250.0D, 0.8D, 16.0F, 20).setFiremode(EnumFiremode.AUTO).setDamage(15, 35).setSpreadFired(5.0F).setSpreadIncreaseVariables(0.8F, 1.9F, 5.0F).setSpreadDecreaseVariables(0.5F, 0.9F).setSpreadMaxIncreaseVariables(6.0F, 100.0F).setMovementPenalty(0.16D).setAmmunitionClips(10, 3).setSoundEffects("scar20shoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAimable(2.0F).setRenderCrosshairs(false).setSightTexture("scope").setSightTextureBlur("scopeblur").setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("Scar-20").setCCItemTextureName("scar20");
	  ssg08 = (ItemGun) new ItemGun(4033, 45.0D, 0.95D, 28.0F, 21).setFiremode(EnumFiremode.SEMI).setDamage(16, 22).setSpreadFired(25.0F).setSpreadIncreaseVariables(2.6F, 4.6F, 8.0F).setSpreadDecreaseVariables(0.8F, 1.0F).setSpreadMaxIncreaseVariables(20.0F, 100.0F).setAmmunitionClips(10, 3).setSoundEffects("ssg08shoot", "", "awpreload", "").setAnimationFired(GunAnimationFiredAWP.class).setAnimationReload(GunAnimationReloadAWP.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAimable(1.0F).setRenderCrosshairs(false).setSightTexture("scope").setSightTextureBlur("scopeblur").setCCBuyType(EnumBuyType.RIFLE).setCCItemDisplayName("SSG-08").setCCItemTextureName("ssg08");
	  nova = (ItemGun) new ItemGun(4040, 75.0D, 1.4D, 30.0F, 22).setFiremode(EnumFiremode.SEMI).setDamage(5, 8).setSpreadFired(3.5F).setBulletAmountFired(6).setFirstBulletAffectedBySpray().setSpreadIncreaseVariables(1.2F, 2.0F, 6.0F).setSpreadDecreaseVariables(0.8F, 1.0F).setSpreadMaxIncreaseVariables(8.0F, 100.0F).setMovementPenalty(0.13D).setAmmunitionClips(7, 4).setSoundEffects("novashoot", "", "novareload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setCCBuyType(EnumBuyType.HEAVY).setCCItemDisplayName("Nova").setCCItemTextureName("nova");
	  sawedoff = (ItemGun) new ItemGun(4041, 80.0D, 1.4D, 30.0F, 23).setFiremode(EnumFiremode.SEMI).setDamage(6, 8).setSpreadFired(4.0F).setBulletAmountFired(6).setFirstBulletAffectedBySpray().setSpreadIncreaseVariables(1.2F, 2.0F, 6.0F).setSpreadDecreaseVariables(0.8F, 1.0F).setSpreadMaxIncreaseVariables(8.0F, 50.0F).setMovementPenalty(0.03D).setAmmunitionClips(7, 4).setSoundEffects("sawedoffshoot", "", "novareload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setCCBuyType(EnumBuyType.HEAVY).setCCItemDisplayName("Sawed-off").setCCItemTextureName("sawedoff");
	  mag7 = (ItemGun) new ItemGun(4042, 100.0D, 0.8D, 30.0F, 24).setFiremode(EnumFiremode.SEMI).setDamage(6, 8).setSpreadFired(4.0F).setBulletAmountFired(6).setFirstBulletAffectedBySpray().setSpreadIncreaseVariables(1.2F, 2.0F, 6.0F).setSpreadDecreaseVariables(0.8F, 1.0F).setSpreadMaxIncreaseVariables(8.0F, 50.0F).setMovementPenalty(0.13D).setAmmunitionClips(5, 4).setSoundEffects("mag7shoot", "", "m4a1reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAnimationReload(GunAnimationReloadPistol.class).setCCBuyType(EnumBuyType.HEAVY).setCCItemDisplayName("Mag-7").setCCItemTextureName("mag7");
	  m249 = (ItemGun) new ItemGun(4043, 550.0D, 2.2D, 13.0F, 25).setFiremode(EnumFiremode.AUTO).setDamage(7, 10).setSpreadFired(2.0F).setBulletAmountFired(1).setSpreadIncreaseVariables(1.2F, 2.0F, 6.0F).setSpreadDecreaseVariables(0.8F, 1.0F).setSpreadMaxIncreaseVariables(8.0F, 100.0F).setMovementPenalty(0.23D).setAmmunitionClips(100, 2).setSoundEffects("m249shoot", "", "m249reload", "").setAnimationFired(GunAnimationRifleFired.class).setAnimationReload(GunAnimationReloadM249.class).setAnimationInspecting(GunAnimationInspectRifle.class).setAnimationReload(GunAnimationReloadPistol.class).setCCBuyType(EnumBuyType.HEAVY).setCCItemDisplayName("M249").setCCItemTextureName("m249");
	  caseAlpha = (new ItemCase(4100)).setCCItemDisplayName("Alpha Case").setCCItemTextureName("casealpha");
      caseAlpha2 = (new ItemCase(4101)).setCCItemDisplayName("Alpha 2 Case").setCCItemTextureName("casealpha2");
      knifeTactical = (new ItemKnife(4200)).setAnimationInspecting(KnifeAnimationInspectTac.class).setCCItemDisplayName("TacKnife").setCCItemTextureName("blueknife");
      knifeTacticalDisplay = (new ItemKnife(4201)).setAnimationInspecting(KnifeAnimationInspectTac.class).setCCItemDisplayName("TacKnife").setCCItemTextureName("blueknife");
      knifeBayonet = (new ItemKnife(4210)).setAnimationInspecting(KnifeAnimationInspect.class).setCCItemDisplayName("Bayonet").setCCItemTextureName("blueknife");
      knifeKarambit = (new ItemKnife(4220)).setAnimationInspecting(KnifeAnimationInspectKarambit.class).setCCItemDisplayName("Karambit").setCCItemTextureName("blueknife");
      knifeButterfly = (new ItemKnife(4230)).setAnimationInspecting(KnifeAnimationInspectButterfly.class).setCCItemDisplayName("Butterfly").setCCItemTextureName("blueknife");
      grenadeFlash = (new ItemGrenadeFlash(4300)).setCCBuyType(EnumBuyType.GRENADE).setCCItemDisplayName("Flash Grenade").setCCItemTextureName("flashbang");
      grenadeDecoy = (new ItemGrenadeDecoy(4301)).setCCBuyType(EnumBuyType.GRENADE).setCCItemDisplayName("Decoy Grenade").setCCItemTextureName("decoy");
      grenadeSmoke = (new ItemGrenadeSmoke(4302)).setCCBuyType(EnumBuyType.GRENADE).setCCItemDisplayName("Smoke Grenade").setCCItemTextureName("smoke");
      grenadeFire = (new ItemGrenadeFire(4303)).setCCBuyType(EnumBuyType.GRENADE).setCCItemDisplayName("Fire Grenade").setCCItemTextureName("firebomb");
      grenadeFrag = (new ItemGrenade(4304)).setCCBuyType(EnumBuyType.GRENADE).setCCItemDisplayName("Frag Grenade").setCCItemTextureName("frag");
      bomb = (new ItemBomb(4310)).setCCItemDisplayName("Бомба").setCCItemTextureName("bomb");
      bombKit = (new ItemBombKit(4311)).setCCItemDisplayName("Набор сапера").setCCItemTextureName("defusekit");
      armorKevlar = (new ItemArmorCC(4312)).setCCItemDisplayName("Бронежилет").setCCItemTextureName("kevlar");
      armorHelmet = (new ItemArmorCC(4313)).setCCItemDisplayName("Шлем").setCCItemTextureName("helmet");
      tradeContract = (new ItemCC(4450)).setCCItemDisplayName("Trade Contract").setCCItemTextureName("tradecontract");
      nameTag = (new ItemCC(4451)).setCCItemDisplayName("Item Name Tag").setCCItemTextureName("nametag");
      coinBetaTester = (new ItemCC(4460)).setCCItemDisplayName("Beta Tester Coin").setCCItemTextureName("coin_beta");
      coinAlphaTester = (new ItemCC(4461)).setCCItemDisplayName("Alpha Tester Coin").setCCItemTextureName("coin_alpha");
      coinDiscord = (new ItemCC(4462)).setCCItemDisplayName("Linked Discord Coin").setCCItemTextureName("coin_discord");
      coinSkinner = (new ItemCC(4463)).setCCItemDisplayName("Skinner Coin").setCCItemTextureName("coin_skinner");
      coinDev = (new ItemCC(4464)).setCCItemDisplayName("Dev Coin").setCCItemTextureName("coin_dev");
      coinStaff = (new ItemCC(4465)).setCCItemDisplayName("Staff Coin").setCCItemTextureName("coin_staff");
      coinSupporter = (new ItemCC(4466)).setCCItemDisplayName("Supporter Coin").setCCItemTextureName("coin_supporter");
      coinModeler = (new ItemCC(4467)).setCCItemDisplayName("Modelling Coin").setCCItemTextureName("coin_modeler");
      coinPrestige = (new ItemCC(4468)).setCCItemDisplayName("Prestige Coin").setCCItemTextureName("coin_prestige");
      coinAssetDev = (new ItemCC(4469)).setCCItemDisplayName("Asset Dev Coin").setCCItemTextureName("coin_assetdev");
      boosterEXPx2 = (new ItemCC(4500)).setCCItemDisplayName("x2 EXP Booster").setCCItemTextureName("boosterexp2");
      boosterEXPx3 = (new ItemCC(4501)).setCCItemDisplayName("x3 EXP Booster").setCCItemTextureName("boosterexp3");
      boosterCoinx2 = (new ItemCC(4502)).setCCItemDisplayName("x2 Coin Booster").setCCItemTextureName("boostercoins2");
      stickerCamper = (new ItemSticker(4550)).setCCItemDisplayName("Camper").setCCItemTextureName("stickercamper");
      stickerChickenStrike = (new ItemSticker(4551)).setCCItemDisplayName("Chicken Strike").setCCItemTextureName("stickerchickenstrike");
      stickerDinked = (new ItemSticker(4552)).setCCItemDisplayName("Dinked").setCCItemTextureName("stickerdinked");
      stickeriBuyPowerKatowice2014 = (new ItemSticker(4553)).setCCItemDisplayName("iBuyPower").setCCItemTextureName("stickeribuypowerkatowice2014");
      stickerKawaiiKiller = (new ItemSticker(4554)).setCCItemDisplayName("Kawaii Killer").setCCItemTextureName("stickerkawaiikiller");
      stickerLucky13 = (new ItemSticker(4555)).setCCItemDisplayName("Lucky 13").setCCItemTextureName("stickerlucky13");
      stickerHowl = (new ItemSticker(4556)).setCCItemDisplayName("Howl").setCCItemTextureName("stickerhowl");
      capsuleAlpha = (new ItemStickerCapsule(4600)).setCCItemDisplayName("Alpha Sticker Capsule").setCCItemTextureName("capsulealpha");
      this.buyableManager.addBuyableItem("blue", m4a1);
      this.buyableManager.addBuyableItem("blue", famas);
      this.buyableManager.addBuyableItem("blue", vector);
      this.buyableManager.addBuyableItem("blue", p90);
      this.buyableManager.addBuyableItem("blue", awp);
      this.buyableManager.addBuyableItem("blue", scar20);
      this.buyableManager.addBuyableItem("blue", ssg08);
      this.buyableManager.addBuyableItem("blue", m1911);
      this.buyableManager.addBuyableItem("blue", deagle);
      this.buyableManager.addBuyableItem("blue", p250);
      this.buyableManager.addBuyableItem("blue", r8);
      this.buyableManager.addBuyableItem("blue", nova);
      this.buyableManager.addBuyableItem("blue", mag7);
      this.buyableManager.addBuyableItem("blue", grenadeDecoy);
      this.buyableManager.addBuyableItem("blue", grenadeFlash);
      this.buyableManager.addBuyableItem("blue", grenadeFire);
      this.buyableManager.addBuyableItem("blue", grenadeSmoke);
      this.buyableManager.addBuyableItem("red", ak47);
      this.buyableManager.addBuyableItem("red", fnfal);
      this.buyableManager.addBuyableItem("red", mac10);
      this.buyableManager.addBuyableItem("red", p90);
      this.buyableManager.addBuyableItem("red", awp);
      this.buyableManager.addBuyableItem("red", dragunov);
      this.buyableManager.addBuyableItem("red", ssg08);
      this.buyableManager.addBuyableItem("red", g18);
      this.buyableManager.addBuyableItem("red", deagle);
      this.buyableManager.addBuyableItem("red", p250);
      this.buyableManager.addBuyableItem("red", r8);
      this.buyableManager.addBuyableItem("red", nova);
      this.buyableManager.addBuyableItem("red", mag7);
      this.buyableManager.addBuyableItem("red", grenadeDecoy);
      this.buyableManager.addBuyableItem("red", grenadeFlash);
      this.buyableManager.addBuyableItem("red", grenadeFrag);
      this.buyableManager.addBuyableItem("red", grenadeSmoke);
   }

   public void init() {
      LanguageRegistry.instance().addStringLocalization("itemGroup.cc", "CS");
      LanguageRegistry.instance().addStringLocalization("itemGroup.ccprops", "CS Map Props");
      LanguageRegistry.instance().addStringLocalization("itemGroup.ccemitters", "CS Map Emitters");
      LanguageRegistry.instance().addStringLocalization("itemGroup.ccblocks", "CS Blocks");
      LanguageRegistry.instance().addStringLocalization("itemGroup.ccdecals", "CS Decals");
      this.buyableManager = new BuyableManager();
      this.initItems();
   }

   public BuyableManager getBuyableManager() {
      return this.buyableManager;
   }
}
