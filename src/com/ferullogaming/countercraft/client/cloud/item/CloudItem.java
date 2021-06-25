package com.ferullogaming.countercraft.client.cloud.item;

import java.util.ArrayList;
import net.minecraft.util.EnumChatFormatting;

public class CloudItem {
   public static CloudItem[] itemList = new CloudItem[4096];
   public static CloudItem M4A1 = (new CloudItemGun(100, 4000, "M4A4")).setDefault().setFilterMT("rifle");
   public static CloudItem M4A1_WAVES;
   public static CloudItem M4A1_EVILDAIMYO;
   public static CloudItem M4A4_CAKEY;
   public static CloudItem AK47;
   public static CloudItem AK47_PROPAGANDA;
   public static CloudItem AK47_ASMO;
   public static CloudItem AK47_NEONREVOLUTION;
   public static CloudItem FAMAS;
   public static CloudItem FAMAS_AKARI;
   public static CloudItem FNFAL;
   public static CloudItem GALIL;
   public static CloudItem M1911;
   public static CloudItem G18;
   public static CloudItem TEC9;
   public static CloudItem TEC9_QUAKORONIC;
   public static CloudItem DEAGLE;
   public static CloudItem DEAGLE_NIGHTRAZER;
   public static CloudItem P250;
   public static CloudItem P250_IRRADIATED;
   public static CloudItem P250_SEEYALATER;
   public static CloudItem P90;
   public static CloudItem P90_RUST;
   public static CloudItem P90_KRAKEN;
   public static CloudItem MAC10;
   public static CloudItem MAC10_RACE;
   public static CloudItem VECTOR;
   public static CloudItem VECTOR_CIRCUIT;
   public static CloudItem AWP;
   public static CloudItem AWP_DRAGON;
   public static CloudItem AWP_MANOWAR;
   public static CloudItem DRAGUNOV;
   public static CloudItem DRAGUNOV_SKY;
   public static CloudItem SCAR20;
   public static CloudItem SCAR20_CYREX;
   public static CloudItem SCAR20_UNICORN;
   public static CloudItem SSG08;
   public static CloudItem KNIFE_TAC;
   public static CloudItem KNIFE_TAC_DISPLAY;
   public static CloudItem KNIFE_BAY;
   public static CloudItem KNIFE_KAR;
   public static CloudItem KNIFE_BUT;
   public static CloudItem NOVA;
   public static CloudItem NOVA_OXIDEBLAZE;
   public static CloudItem SAWEDOFF;
   public static CloudItem SAWEDOFF_WASTELANDPRINCESS;
   public static CloudItem SAWEDOFF_KRAKEN;
   public static CloudItem MAG7;
   public static CloudItem MAG7_ROYALFIRE;
   public static CloudItem M249;
   public static CloudItem M249_NEON;
   public static CloudItem CZ75;
   public static CloudItem UMP45;
   public static CloudItem FIVESEVEN;
   public static CloudItem R8;
   public static CloudItem R8_FADE;
   public static CloudItem WC_ALPHA;
   public static CloudItem WC_ALPHA2;
   public static CloudItem TRADE_CONTRACT;
   public static CloudItem NAME_TAG;
   public static CloudItem COIN_BETA;
   public static CloudItem COIN_ALPHA;
   public static CloudItem COIN_DISCORD;
   public static CloudItem COIN_SKINNER;
   public static CloudItem COIN_DEV;
   public static CloudItem COIN_STAFF;
   public static CloudItem COIN_SUPPORTER;
   public static CloudItem COIN_MODELER;
   public static CloudItem COIN_PRESTIGE;
   public static CloudItem COIN_ASSETDEV;
   public static CloudItem BOOSTER_EXP_2;
   public static CloudItem BOOSTER_EXP_3;
   public static CloudItem BOOSTER_EMD_2;
   public static CloudItem STICKER_CAMP;
   public static CloudItem STICKER_CHICKEN_STRIKE;
   public static CloudItem STICKER_DINKED;
   public static CloudItem STICKER_KATO2014_IBUYPOWER;
   public static CloudItem STICKER_KAWAIIKILLER;
   public static CloudItem STICKER_LUCKY13;
   public static CloudItem STICKER_HOWL;
   public static CloudItem SC_ALPHA;
   protected CloudItemRarity value;
   protected String filterInventoryType;
   protected String filterMarketType;
   protected String author;
   protected boolean isDefault;
   protected boolean isTradable;
   protected boolean isSellable;
   protected boolean isShowcaseable;
   protected boolean isNameable;
   protected boolean isContractable;
   protected String itemCollection;
   protected int tradeContractWiehgt;
   private int id;
   private int mcId;
   private String name;
   private String suffix;
   private String sticker0;
   private String sticker1;
   private String sticker2;

   public CloudItem(int par1, int par2, String par3) {
      this.value = CloudItemRarity.DEFAULT;
      this.filterInventoryType = "-";
      this.filterMarketType = "-";
      this.author = "CounterCraft";
      this.isDefault = false;
      this.isTradable = true;
      this.isSellable = true;
      this.isShowcaseable = true;
      this.isNameable = true;
      this.isContractable = true;
      this.itemCollection = null;
      this.tradeContractWiehgt = 20;
      this.suffix = "none";
      this.sticker0 = "none";
      this.sticker1 = "none";
      this.sticker2 = "none";
      this.id = par1;
      this.mcId = par2 + 256;
      this.name = par3;
      itemList[par1] = this;
   }

   public void addInformation(CloudItemStack par1, ArrayList par2) {
      par2.add(this.name);
      if (this.suffix != null && !this.suffix.equals("none")) {
         par2.add(this.value.chatColor + this.suffix);
      } else {
         par2.add("");
      }

      if (this.sticker0 != null && !this.sticker0.equals("none")) {
         par2.add("Sticker: " + this.value.chatColor + this.sticker0);
      }

      if (this.sticker1 != null && !this.sticker1.equals("none")) {
         par2.add("Sticker: " + this.value.chatColor + this.sticker1);
      }

      if (this.sticker2 != null && !this.sticker2.equals("none")) {
         par2.add("Sticker: " + this.value.chatColor + this.sticker2);
      }

      par2.add(this.value.chatColor + "" + EnumChatFormatting.ITALIC + this.value.rarityName);
   }

   public CloudItem setFilterIT(String par1) {
      this.filterInventoryType = par1;
      return this;
   }

   public CloudItem setFilterMT(String par1) {
      this.filterMarketType = par1;
      return this;
   }

   public CloudItem setDefault() {
      this.isDefault = true;
      return this;
   }

   public CloudItem disableTrading() {
      this.isTradable = false;
      return this;
   }

   public CloudItem disableSelling() {
      this.isSellable = false;
      return this;
   }

   public CloudItem disableShowcase() {
      this.isShowcaseable = false;
      return this;
   }

   public CloudItem disableNameable() {
      this.isNameable = false;
      return this;
   }

   public CloudItem disableContracts() {
      this.isContractable = false;
      return this;
   }

   public CloudItem setTradeupWeight(int par1) {
      this.tradeContractWiehgt = par1 > 20 ? 20 : par1;
      return this;
   }

   public String getMarketType() {
      return this.filterMarketType;
   }

   public String getInventoryType() {
      return this.filterInventoryType;
   }

   public String getCollection() {
      return this.itemCollection;
   }

   public CloudItem setCollection(String par1) {
      this.itemCollection = par1;
      return this;
   }

   public int getID() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public CloudItem setSuffix(String par1) {
      this.suffix = par1;
      return this;
   }

   public CloudItem setSticker(String par1, int givenPosition) {
      switch(givenPosition) {
      case 0:
         this.sticker0 = par1;
         break;
      case 1:
         this.sticker1 = par1;
         break;
      case 2:
         this.sticker2 = par1;
      }

      return this;
   }

   public String getAuthor() {
      return this.author;
   }

   public CloudItem setAuthor(String par1) {
      this.author = par1;
      return this;
   }

   public boolean isTradable() {
      return this.isTradable;
   }

   public boolean isSellable() {
      return this.isSellable;
   }

   public boolean isShowcaseable() {
      return this.isShowcaseable;
   }

   public boolean isNameable() {
      return this.isNameable;
   }

   public boolean isContrableAble() {
      return this.isContractable;
   }

   public String getDisplayName() {
      String name = this.getName();
      String suffix = this.getSuffix();
      return !suffix.equals("none") ? name + " " + suffix : name;
   }

   public int getMCItemID() {
      return this.mcId;
   }

   public CloudItemRarity getValue() {
      return this.value;
   }

   public CloudItem setValue(CloudItemRarity par1) {
      this.value = par1;
      return this;
   }

   public boolean isDefault() {
      return this.isDefault;
   }

   public String getSticker0() {
      return this.sticker0;
   }

   public String getSticker1() {
      return this.sticker1;
   }

   public String getSticker2() {
      return this.sticker2;
   }

   static {
      M4A1_WAVES = (new CloudItemGun(101, 4000, "M4A4")).setAuthor("JasonPlayGame").setSuffix("Waves").setCollection("Alpha").setValue(CloudItemRarity.TIER2).setFilterMT("rifle");
      M4A1_EVILDAIMYO = (new CloudItemGun(102, 4000, "M4A4")).setAuthor("Nanta18").setSuffix("EvilDaimyo").setCollection("Alpha").setValue(CloudItemRarity.TIER4).setFilterMT("rifle");
      M4A4_CAKEY = (new CloudItemGun(103, 4000, "M4A4")).setAuthor("Icey_Eclipse").setSuffix("Cakey").setCollection("Alpha2").setValue(CloudItemRarity.TIER2).setFilterMT("rifle");
      AK47 = (new CloudItemGun(200, 4001, "AK47")).setDefault().setFilterMT("rifle");
      AK47_PROPAGANDA = (new CloudItemGun(201, 4001, "AK47")).setAuthor("ABrokenHeart").setSuffix("Propaganda").setCollection("Alpha").setValue(CloudItemRarity.TIER4).setFilterMT("rifle");
      AK47_ASMO = (new CloudItemGun(202, 4001, "AK47")).setAuthor("Tylerftw22").setSuffix("Asmo").setCollection("Alpha").setValue(CloudItemRarity.TIER5).setFilterMT("rifle");
      AK47_NEONREVOLUTION = (new CloudItemGun(203, 4001, "AK47")).setAuthor("BGcreeper101").setSuffix("Neon Revolution").setCollection("Alpha2").setValue(CloudItemRarity.TIER4).setFilterMT("rifle");
      FAMAS = (new CloudItemGun(300, 4002, "Famas")).setDefault().setFilterMT("rifle");
      FAMAS_AKARI = (new CloudItemGun(301, 4002, "Famas")).setAuthor("JasonPlayGame").setSuffix("Akari").setCollection("Alpha2").setValue(CloudItemRarity.TIER3).setFilterMT("rifle");
      FNFAL = (new CloudItemGun(400, 4003, "FN FAL")).setDefault().setFilterMT("rifle");
      GALIL = (new CloudItemGun(450, 4004, "Galil AR")).setDefault().setAuthor("Egglanoir").setFilterMT("rifle");
      M1911 = (new CloudItemGun(500, 4010, "M1911")).setDefault().setFilterMT("pistol");
      G18 = (new CloudItemGun(600, 4011, "G18")).setDefault().setFilterMT("pistol");
      TEC9 = (new CloudItemGun(650, 4014, "TEC9")).setDefault().setFilterMT("pistol");
      TEC9_QUAKORONIC = (new CloudItemGun(651, 4014, "TEC9")).setAuthor("BuLgUr").setSuffix("Quakoronic").setCollection("Alpha2").setValue(CloudItemRarity.TIER2).setFilterMT("pistol");
      DEAGLE = (new CloudItemGun(700, 4012, "Deagle")).setDefault().setFilterMT("pistol");
      DEAGLE_NIGHTRAZER = (new CloudItemGun(701, 4012, "Deagle")).setAuthor("BGcreeper101").setSuffix("NightRazer").setCollection("Alpha").setValue(CloudItemRarity.TIER3).setFilterMT("pistol");
      P250 = (new CloudItemGun(750, 4013, "P250")).setDefault().setFilterMT("pistol");
      P250_IRRADIATED = (new CloudItemGun(751, 4013, "P250")).setAuthor("RishFish").setSuffix("Irradiated").setCollection("Alpha").setValue(CloudItemRarity.TIER2).setFilterMT("pistol");
      P250_SEEYALATER = (new CloudItemGun(752, 4013, "P250")).setAuthor("ABrokenHeart").setSuffix("SeeYaLater").setCollection("Alpha2").setValue(CloudItemRarity.TIER5).setFilterMT("pistol");
      P90 = (new CloudItemGun(800, 4020, "P90")).setDefault().setFilterMT("smg");
      P90_RUST = (new CloudItemGun(801, 4020, "P90")).setAuthor("Zoot").setSuffix("Rust").setCollection("Alpha").setValue(CloudItemRarity.TIER2).setFilterMT("smg");
      P90_KRAKEN = (new CloudItemGun(802, 4020, "P90")).setAuthor("ABrokenHeart").setSuffix("Kraken").setCollection("Alpha2").setValue(CloudItemRarity.TIER4).setFilterMT("smg");
      MAC10 = (new CloudItemGun(900, 4021, "MAC10")).setDefault().setFilterMT("smg");
      MAC10_RACE = (new CloudItemGun(901, 4021, "MAC10")).setAuthor("StolyLP").setSuffix("Race").setCollection("Alpha2").setValue(CloudItemRarity.TIER2).setFilterMT("smg");
      VECTOR = (new CloudItemGun(1000, 4022, "Vector")).setDefault().setFilterMT("smg");
      VECTOR_CIRCUIT = (new CloudItemGun(1001, 4022, "Vector")).setAuthor("Nanta18").setSuffix("Circuit").setValue(CloudItemRarity.TIER2).setFilterMT("smg");
      AWP = (new CloudItemGun(1100, 4030, "AWP")).setDefault().setFilterMT("sniper");
      AWP_DRAGON = (new CloudItemGun(1101, 4030, "AWP")).setAuthor("ABrokenHeart").setSuffix("Dragon").setCollection("Alpha").setValue(CloudItemRarity.TIER6).setFilterMT("sniper");
      AWP_MANOWAR = (new CloudItemGun(1102, 4030, "AWP")).setAuthor("YCGamez").setSuffix("Man O War").setCollection("Alpha2").setValue(CloudItemRarity.TIER3).setFilterMT("sniper");
      DRAGUNOV = (new CloudItemGun(1200, 4031, "Dragunov")).setDefault().setFilterMT("sniper");
      DRAGUNOV_SKY = (new CloudItemGun(1201, 4031, "Dragunov")).setAuthor("ABrokenHeart").setSuffix("Sky").setCollection("Alpha").setValue(CloudItemRarity.TIER2).setFilterMT("sniper");
      SCAR20 = (new CloudItemGun(1300, 4032, "Scar20")).setDefault().setFilterMT("sniper");
      SCAR20_CYREX = (new CloudItemGun(1301, 4032, "Scar20")).setAuthor("_Void_YT").setSuffix("Cyrex").setCollection("Alpha").setValue(CloudItemRarity.TIER3).setFilterMT("sniper");
      SCAR20_UNICORN = (new CloudItemGun(1302, 4032, "Scar20")).setAuthor("Overrkill").setSuffix("Unicorn").setCollection("Alpha2").setValue(CloudItemRarity.TIER5).setFilterMT("sniper");
      SSG08 = (new CloudItemGun(1400, 4033, "SSG-08")).setDefault().setAuthor("CreeprfighterYTC").setFilterMT("sniper");
      KNIFE_TAC = (new CloudItemKnife(1500, 4200, "Tac Knife")).setDefault().setFilterMT("knife");
      KNIFE_TAC_DISPLAY = (new CloudItemKnife(1501, 4201, "Special")).setSuffix("Item").setValue(CloudItemRarity.TIER6);
      KNIFE_BAY = (new CloudItemKnife(1510, 4210, "Bayonet")).setValue(CloudItemRarity.TIER5).setFilterMT("knife");
      KNIFE_KAR = (new CloudItemKnife(1520, 4220, "Karambit")).setValue(CloudItemRarity.TIER5).setFilterMT("knife");
      KNIFE_BUT = (new CloudItemKnife(1530, 4230, "Butterfly")).setValue(CloudItemRarity.TIER5).setFilterMT("knife");
      NOVA = (new CloudItemGun(1600, 4040, "Nova")).setDefault().setAuthor("Lizardpile").setFilterMT("heavy");
      NOVA_OXIDEBLAZE = (new CloudItemGun(1601, 4040, "Nova")).setAuthor("Greemon").setSuffix("Oxide Blaze").setCollection("Alpha2").setValue(CloudItemRarity.TIER2).setFilterMT("heavy");
      SAWEDOFF = (new CloudItemGun(1650, 4041, "Sawed-Off")).setDefault().setAuthor("Lizardpile").setFilterMT("heavy");
      SAWEDOFF_WASTELANDPRINCESS = (new CloudItemGun(1651, 4041, "Sawed-Off")).setAuthor("ABrokenHeart").setSuffix("WastelandPrincess").setCollection("Alpha").setValue(CloudItemRarity.TIER4).setFilterMT("heavy");
      SAWEDOFF_KRAKEN = (new CloudItemGun(1652, 4041, "Sawed-Off")).setAuthor("ABrokenHeart").setSuffix("Kraken").setCollection("Alpha2").setValue(CloudItemRarity.TIER3).setFilterMT("heavy");
      MAG7 = (new CloudItemGun(1700, 4042, "Mag-7")).setDefault().setAuthor("Lizardpile").setFilterMT("heavy");
      MAG7_ROYALFIRE = (new CloudItemGun(1701, 4042, "Mag-7")).setAuthor("Nanta18").setSuffix("Royal Fire").setCollection("Alpha").setValue(CloudItemRarity.TIER3).setFilterMT("heavy");
      M249 = (new CloudItemGun(1750, 4043, "M249")).setDefault().setAuthor("Lizardpile").setFilterMT("heavy");
      M249_NEON = (new CloudItemGun(1751, 4043, "M249")).setAuthor("CogHead").setSuffix("Neon").setCollection("Alpha").setValue(CloudItemRarity.TIER3).setFilterMT("heavy");
      CZ75 = (new CloudItemGun(1800, 4015, "CZ75")).setDefault().setAuthor("CreeprfighterYTC").setFilterMT("pistol");
      UMP45 = (new CloudItemGun(1850, 4023, "UMP-45")).setDefault().setAuthor("CreeprfighterYTC").setFilterMT("smg");
      FIVESEVEN = (new CloudItemGun(1815, 4016, "Fiveseven")).setDefault().setAuthor("ABrokenHeart").setFilterMT("pistol");
      R8 = (new CloudItemGun(1826, 4017, "R8")).setDefault().setAuthor("ABrokenHeart").setFilterMT("pistol");
      R8_FADE = (new CloudItemGun(1827, 4017, "R8")).setAuthor("JasonPlayGame").setSuffix("Fade").setCollection("Alpha").setValue(CloudItemRarity.TIER5).setFilterMT("pistol");
      WC_ALPHA = (new CloudItemCase(2000, 4100, "Alpha")).setCostToOpen(100).addContent(KNIFE_TAC_DISPLAY, 0).addContent(AWP_DRAGON, 0).addContent(AK47_ASMO, 1).addContent(R8_FADE, 1).addContent(SAWEDOFF_WASTELANDPRINCESS, 4).addContent(M4A1_EVILDAIMYO, 4).addContent(AK47_PROPAGANDA, 10).addContent(M249_NEON, 10).addContent(DEAGLE_NIGHTRAZER, 20).addContent(MAG7_ROYALFIRE, 40).addContent(SCAR20_CYREX, 60).addContent(P90_RUST, 80).addContent(VECTOR_CIRCUIT, 100).addContent(M4A1_WAVES, 110).addContent(DRAGUNOV_SKY, 110).addContent(P250_IRRADIATED, 110).setSuffix("Case").setValue(CloudItemRarity.TIER1).setFilterMT("case").disableNameable().disableShowcase().disableContracts();
      WC_ALPHA2 = (new CloudItemCase(2001, 4101, "Alpha 2")).setCostToOpen(150).addContent(KNIFE_TAC_DISPLAY, 0).addContent(SCAR20_UNICORN, 1).addContent(P250_SEEYALATER, 1).addContent(AK47_NEONREVOLUTION, 4).addContent(P90_KRAKEN, 4).addContent(FAMAS_AKARI, 10).addContent(SAWEDOFF_KRAKEN, 10).addContent(AWP_MANOWAR, 20).addContent(M4A4_CAKEY, 40).addContent(MAC10_RACE, 60).addContent(TEC9_QUAKORONIC, 80).addContent(NOVA_OXIDEBLAZE, 100).setSuffix("Case").setValue(CloudItemRarity.TIER1).setFilterMT("case").disableNameable().disableShowcase().disableContracts();
      TRADE_CONTRACT = (new CloudItem(2040, 4450, "Trade")).setSuffix("Contract").setDefault();
      NAME_TAG = (new CloudItem(2041, 4451, "Name Tag")).setSuffix("").setFilterMT("misc").setValue(CloudItemRarity.TIER1).disableNameable().disableShowcase().disableContracts();
      COIN_BETA = (new CloudItemCoin(2050, 4460, "Coin")).setSuffix("BETA Age").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_ALPHA = (new CloudItemCoin(2051, 4461, "Coin")).setSuffix("ALPHA Age").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_DISCORD = (new CloudItemCoin(2052, 4462, "Coin")).setSuffix("Discord").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_SKINNER = (new CloudItemCoin(2053, 4463, "Coin")).setSuffix("Skinning").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_DEV = (new CloudItemCoin(2054, 4464, "Coin")).setSuffix("Developer").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_STAFF = (new CloudItemCoin(2055, 4465, "Coin")).setSuffix("Staffing").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_SUPPORTER = (new CloudItemCoin(2056, 4466, " Coin")).setSuffix("Supporter").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_MODELER = (new CloudItemCoin(2057, 4467, "Coin")).setSuffix("Modelling").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_PRESTIGE = (new CloudItemCoin(2058, 4468, "Coin")).setSuffix("Prestige").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      COIN_ASSETDEV = (new CloudItemCoin(2059, 4469, "Coin")).setSuffix("Asset Dev").setValue(CloudItemRarity.TIER5).disableNameable().disableSelling().disableTrading().disableContracts();
      BOOSTER_EXP_2 = (new CloudItemBooster(2100, 4500, "x2 EXP")).setSuffix("30m Boost").setValue(CloudItemRarity.TIER5).setCollection("Booster").disableNameable().disableSelling().disableTrading().disableContracts().disableShowcase();
      BOOSTER_EXP_3 = (new CloudItemBooster(2101, 4501, "x3 EXP")).setSuffix("30m Boost").setValue(CloudItemRarity.TIER5).setCollection("Booster").disableNameable().disableSelling().disableTrading().disableContracts().disableShowcase();
      BOOSTER_EMD_2 = (new CloudItemBooster(2102, 4502, "x2 Coin")).setSuffix("30m Boost").setValue(CloudItemRarity.TIER5).setCollection("Booster").disableNameable().disableSelling().disableTrading().disableContracts().disableShowcase();
      STICKER_CAMP = (new CloudItemSticker(2200, 4550, "Sticker")).setStickerTextureName("stickercamper").setSuffix("Camper").setValue(CloudItemRarity.TIER2).setFilterMT("sticker");
      STICKER_CHICKEN_STRIKE = (new CloudItemSticker(2201, 4551, "Sticker")).setStickerTextureName("stickerchickenstrike").setSuffix("Chicken Strike").setValue(CloudItemRarity.TIER2).setFilterMT("sticker");
      STICKER_DINKED = (new CloudItemSticker(2202, 4552, "Sticker")).setStickerTextureName("stickerdinked").setSuffix("Dinked").setValue(CloudItemRarity.TIER2).setFilterMT("sticker");
      STICKER_KATO2014_IBUYPOWER = (new CloudItemSticker(2203, 4553, "Sticker")).setStickerTextureName("stickeribuypowerkatowice2014").setSuffix("iBuyPower (Katowice 2014)").setValue(CloudItemRarity.TIER3).setFilterMT("sticker");
      STICKER_KAWAIIKILLER = (new CloudItemSticker(2204, 4554, "Sticker")).setStickerTextureName("stickerkawaiikiller").setSuffix("Kawaii Killer").setValue(CloudItemRarity.TIER3).setFilterMT("sticker");
      STICKER_LUCKY13 = (new CloudItemSticker(2205, 4555, "Sticker")).setStickerTextureName("stickerlucky13").setSuffix("Lucky 13").setValue(CloudItemRarity.TIER4).setFilterMT("sticker");
      STICKER_HOWL = (new CloudItemSticker(2206, 4556, "Sticker")).setStickerTextureName("stickerhowl").setSuffix("Howl").setValue(CloudItemRarity.TIER5).setFilterMT("sticker");
      SC_ALPHA = (new CloudItemStickerCapsule(3000, 4600, "Alpha Caps.")).setCostToOpen(100).addContent(STICKER_CAMP, 80).addContent(STICKER_CHICKEN_STRIKE, 80).addContent(STICKER_DINKED, 80).addContent(STICKER_KATO2014_IBUYPOWER, 15).addContent(STICKER_KAWAIIKILLER, 15).addContent(STICKER_LUCKY13, 5).addContent(STICKER_HOWL, 3);
   }
}
