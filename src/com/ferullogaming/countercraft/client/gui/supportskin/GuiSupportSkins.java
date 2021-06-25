package com.ferullogaming.countercraft.client.gui.supportskin;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.ferullogaming.countercraft.CounterCraft;
import com.ferullogaming.countercraft.client.CCRenderHelper;
import com.ferullogaming.countercraft.client.ClientNotification;
import com.ferullogaming.countercraft.client.cloud.packet.PacketClientRequest;
import com.ferullogaming.countercraft.client.cloud.packet.RequestType;
import com.ferullogaming.countercraft.client.gui.GuiCCContainerProfile;
import com.ferullogaming.countercraft.client.gui.GuiCCMenu;
import com.ferullogaming.countercraft.client.gui.StringListHelperCC;
import com.ferullogaming.countercraft.client.gui.api.GuiFGButton;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainer;
import com.ferullogaming.countercraft.client.gui.api.GuiFGContainerText;
import com.ferullogaming.countercraft.client.gui.api.GuiFGScroller;
import com.ferullogaming.countercraft.client.gui.inventory.GuiCCMenuInspect;
import com.ferullogaming.countercraft.item.ItemKnife;
import com.ferullogaming.countercraft.item.ItemManager;
import com.ferullogaming.countercraft.item.gun.ItemGun;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiSupportSkins extends GuiCCMenu {
   public static ItemGun selectedGun;
   public static int selectedID;
   public static String currentSkin = "";
   public GuiScreen parent;
   public float rotationY = 0.0F;
   private static GuiFGButton sk1, sk2, sk3, sk4, sk5, sk6, sk7, sk8, sk9, sk10, dr0, dr1;
   private static boolean isRotate;
   
   public GuiSupportSkins(GuiScreen s) {
	   if (s instanceof GuiCCMenu) this.parent = s;
   }
   
   public void initGui() {
      super.initGui();
      this.initOverheadMenu();
      int x = super.width / 2 - 173;
      PacketClientRequest.sendRequest(RequestType.PLAYER_INVENTORY_DEFAULTS, super.mc.getSession().getUsername());
      this.addContainer(new GuiCCContainerProfile(1, x + 1, 25, 110, 40, this));
      this.addContainer((new GuiFGContainerText(5, x + 1, 68, 110, 14, this)).setText("Выбрано:").setColor(GuiCCMenu.menuTheme3));
      this.addContainer((new GuiFGContainerText(6, x + 114, 25, 238, 14, this)).setText("Выбор оружия").setColor(GuiCCMenu.menuTheme3));
      this.addContainer(new GuiFGContainer(7, x + 1, 83, 110, 150, this));
      this.addContainer(new GuiFGContainer(8, x + 114, 93, 238, 140, this));
      this.addContainer((new GuiFGContainer(9, x + 118, 97, 230, 132, this)).setColor(GuiCCMenu.menuTheme3));
      super.buttonList.add((new GuiFGButton(20, x + 6, 104, 100, 10, "Возврат.")).setToolTip("Вернутся в меню", Color.black));
      super.buttonList.add((new GuiFGButton(21, x + 116, 42, 10, 10, "1")).setToolTip("M4-A4", Color.black));
      super.buttonList.add((new GuiFGButton(22, x + 130, 42, 10, 10, "2")).setToolTip("AK-47", Color.black));
      super.buttonList.add((new GuiFGButton(23, x + 144, 42, 10, 10, "3")).setToolTip("Famas", Color.black));
      super.buttonList.add((new GuiFGButton(24, x + 158, 42, 10, 10, "4")).setToolTip("FN-FAL", Color.black));
      super.buttonList.add((new GuiFGButton(25, x + 172, 42, 10, 10, "5")).setToolTip("Galil-AR", Color.black));
      super.buttonList.add((new GuiFGButton(26, x + 186, 42, 10, 10, "6")).setToolTip("M1911", Color.black));
      super.buttonList.add((new GuiFGButton(27, x + 200, 42, 10, 10, "7")).setToolTip("G18", Color.black));
      super.buttonList.add((new GuiFGButton(28, x + 214, 42, 10, 10, "8")).setToolTip("Deagle", Color.black));
      super.buttonList.add((new GuiFGButton(29, x + 228, 42, 10, 10, "9")).setToolTip("P250", Color.black));
      super.buttonList.add((new GuiFGButton(30, x + 242, 42, 10, 10, "10")).setToolTip("TEC-9", Color.black));
      super.buttonList.add((new GuiFGButton(31, x + 256, 42, 10, 10, "11")).setToolTip("CZ75", Color.black));
      super.buttonList.add((new GuiFGButton(32, x + 270, 42, 10, 10, "12")).setToolTip("Five-Seven", Color.black));
      super.buttonList.add((new GuiFGButton(33, x + 284, 42, 10, 10, "13")).setToolTip("R8 Revolver", Color.black));
      super.buttonList.add((new GuiFGButton(34, x + 298, 42, 10, 10, "14")).setToolTip("P90", Color.black));
      super.buttonList.add((new GuiFGButton(35, x + 312, 42, 10, 10, "15")).setToolTip("MAC-10", Color.black));
      super.buttonList.add((new GuiFGButton(36, x + 326, 42, 10, 10, "16")).setToolTip("Vector", Color.black));
      super.buttonList.add((new GuiFGButton(37, x + 340, 42, 10, 10, "17")).setToolTip("UMP-45", Color.black));
      super.buttonList.add((new GuiFGButton(38, x + 116, 56, 10, 10, "18")).setToolTip("AWP", Color.black));
      super.buttonList.add((new GuiFGButton(39, x + 130, 56, 10, 10, "19")).setToolTip("Dragunov", Color.black));
      super.buttonList.add((new GuiFGButton(40, x + 144, 56, 10, 10, "20")).setToolTip("Scar-20", Color.black));
      super.buttonList.add((new GuiFGButton(41, x + 158, 56, 10, 10, "21")).setToolTip("SSG-08", Color.black));
      super.buttonList.add((new GuiFGButton(42, x + 172, 56, 10, 10, "22")).setToolTip("Nova", Color.black));
      super.buttonList.add((new GuiFGButton(43, x + 186, 56, 10, 10, "23")).setToolTip("Sawed-off", Color.black));
      super.buttonList.add((new GuiFGButton(44, x + 200, 56, 10, 10, "24")).setToolTip("Mag-7", Color.black));
      super.buttonList.add((new GuiFGButton(45, x + 214, 56, 10, 10, "25")).setToolTip("M249", Color.black));
      
      super.buttonList.add(sk1 = (new GuiFGButton(46, x + 11, 120, 10, 10, "1", true).setToolTip("", Color.black)));
      super.buttonList.add(sk2 = (new GuiFGButton(47, x + 31, 120, 10, 10, "2", true)));
      super.buttonList.add(sk3 = (new GuiFGButton(48, x + 51, 120, 10, 10, "3", true)));
      super.buttonList.add(sk4 = (new GuiFGButton(49, x + 71, 120, 10, 10, "4", true)));
      super.buttonList.add(sk5 = (new GuiFGButton(50, x + 91, 120, 10, 10, "5", true)));
      super.buttonList.add(sk6 = (new GuiFGButton(51, x + 11, 140, 10, 10, "6", true)));
      super.buttonList.add(sk7 = (new GuiFGButton(52, x + 31, 140, 10, 10, "7", true)));
      super.buttonList.add(sk8 = (new GuiFGButton(53, x + 51, 140, 10, 10, "8", true)));
      super.buttonList.add(sk9 = (new GuiFGButton(54, x + 71, 140, 10, 10, "9", true)));
      super.buttonList.add(sk10 = (new GuiFGButton(55, x + 91, 140, 10, 10, "10", true)));
	  sk2.setToolTip("Asmo", Color.black);
	  sk3.setToolTip("Cakey", Color.black);
	  sk4.setToolTip("Cyrex", Color.black);
	  sk5.setToolTip("Desert", Color.black);
	  sk6.setToolTip("Evil Daimyo", Color.black);
	  sk7.setToolTip("Howl", Color.black);
	  sk8.setToolTip("Lurker", Color.black);
	  sk9.setToolTip("Waves", Color.black);
	  sk1.drawButton = true;
	  sk2.drawButton = true;
	  sk3.drawButton = true;
	  sk4.drawButton = true;
	  sk5.drawButton = true;
	  sk6.drawButton = true;
	  sk7.drawButton = true;
	  sk8.drawButton = true;
	  sk9.drawButton = true;
	  sk10.drawButton = false;
      super.buttonList.add((new GuiFGButton(60, x + 116, 78, 70, 10, "Сохранить скин ")));
      super.buttonList.add((new GuiFGButton(61, x + 190, 78, 70, 10, "Обнулить скины")).setToolTip("Используется при багах для восстановления.", Color.black));
      super.buttonList.add((new GuiFGButton(62, x + 264, 78, 42, 10, "Стикеры")));
      super.buttonList.add((new GuiFGButton(63, x + 264, 64, 42, 10, "Ножи")));
      super.buttonList.add((new GuiFGButton(64, x + 310, 64, 40, 10, "Вкл. Пов.")).setToolTip("Включить поворот", Color.black));
      super.buttonList.add((new GuiFGButton(65, x + 310, 78, 40, 10, "Выкл. Пов.")).setToolTip("Выключить поворот", Color.black));
      this.clearsk();
   }

   public void actionPerformed(GuiButton a) {
      super.actionPerformed(a);
      this.addOverheadMenuActionPerformed(a);
      if (a.id == 20) {
    	  this.mc.displayGuiScreen(this.parent);
    	  return;
      }
      if (a.id == 46 || a.id == 47 || a.id == 48 || a.id == 49 || a.id == 50 || a.id == 51 || a.id == 52 || a.id == 53 || a.id == 54 || a.id == 55) {
    	  this.currentSkin = ((GuiFGButton)a).toolTip.equalsIgnoreCase("Обычный") ? "" : ((GuiFGButton)a).toolTip;
    	  return;
      }
      if (a.id == 60) {
    	  CounterCraft.instance.getHDD().line(this.selectedID + ":" + this.currentSkin.replaceAll(" ", "").toLowerCase() + ":", this.selectedID, CounterCraft.instance.getHDD().SkinInv);
    	  ClientNotification.createNotification("Сохранено: Скин " + this.currentSkin + " на " + this.selectedGun.getUnlocalizedName().replaceAll("item.", ""));
          CounterCraft.instance.getHDD().Read(CounterCraft.instance.getHDD().SkinInv);
          this.clearsk();
          return;
      }
      if (a.id == 61) {
          CounterCraft.instance.getHDD().SkinInv.delete();
          CounterCraft.instance.getHDD().WriteDefaults(CounterCraft.instance.getHDD().SkinInv);
          CounterCraft.instance.getHDD().Read(CounterCraft.instance.getHDD().SkinInv);
          ClientNotification.createNotification("Скины очищены.");
          this.clearsk();
          return;
      }
      if (a.id == 62) {
    	  this.mc.displayGuiScreen(new GuiSupportStickers(this));
    	  return;
      }
      if (a.id == 63) {
    	  this.mc.displayGuiScreen(new GuiSupportKnifes(this));
    	  return;
      }
      if (a.id == 64 || a.id == 65) {
    	  isRotate = a.id == 64;
    	  return;
      }
      sk1.drawButton = true;
      sk2.drawButton = true;
      sk3.drawButton = true;
      sk4.drawButton = true;
      sk5.drawButton = true;
      sk6.drawButton = true;
      sk7.drawButton = true;
      sk8.drawButton = true;
      sk9.drawButton = true;
      sk10.drawButton = true;
      if (a.id == 21) {
    	  selectedGun = (ItemGun)ItemManager.m4a1;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Asmo", Color.black);
   		  sk3.setToolTip("Cakey", Color.black);
   		  sk4.setToolTip("Cyrex", Color.black);
   		  sk5.setToolTip("Desert", Color.black);
   		  sk6.setToolTip("Evil Daimyo", Color.black);
   		  sk7.setToolTip("Howl", Color.black);
   		  sk8.setToolTip("Lurker", Color.black);
   		  sk9.setToolTip("Waves", Color.black);
   		  sk10.drawButton = false;
    	  selectedID = (a.id - 20);
      }
      if (a.id == 22) {
    	  selectedGun = (ItemGun)ItemManager.ak47;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Asmo", Color.black);
   		  sk3.setToolTip("Bones", Color.black);
   		  sk4.setToolTip("Fuel", Color.black);
   		  sk5.setToolTip("Macaw", Color.black);
   		  sk6.setToolTip("Neon Revolution", Color.black);
   		  sk7.setToolTip("Propaganda", Color.black);
   		  sk8.setToolTip("Shikari", Color.black);
   		  sk9.setToolTip("Stripe", Color.black);
   		  sk10.setToolTip("Vulcan", Color.black);
    	  selectedID = (a.id - 20);
      }
      if (a.id == 23) {
    	  selectedGun = (ItemGun)ItemManager.famas;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Akari", Color.black);
   		  sk3.setToolTip("Cobalt", Color.black);
   		  sk4.setToolTip("Gekko", Color.black);
   		  sk5.setToolTip("Nuclear", Color.black);
   		  sk6.setToolTip("Pulse", Color.black);
   		  sk7.setToolTip("Spooky", Color.black);
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 24) {
    	  selectedGun = (ItemGun)ItemManager.fnfal;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Rebel", Color.black);
   		  sk3.setToolTip("Shocker", Color.black);
   		  sk4.setToolTip("Stain", Color.black);
   		  sk5.setToolTip("Venom", Color.black);
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 25) {
    	  selectedGun = (ItemGun)ItemManager.galil;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.drawButton = false;
 		  sk3.drawButton = false;
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 26) {
    	  selectedGun = (ItemGun)ItemManager.m1911;
    	  currentSkin = "";
    	  sk2.setToolTip("", Color.black);
    	  sk2.setToolTip("Blaze", Color.black);
   		  sk3.setToolTip("Dragon", Color.black);
   		  sk4.setToolTip("Oldwest", Color.black);
   		  sk5.setToolTip("Rain", Color.black);
   		  sk6.setToolTip("Shock", Color.black);
   		  sk7.setToolTip("Triark", Color.black);
   		  sk8.setToolTip("Venom", Color.black);
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 27) {
    	  selectedGun = (ItemGun)ItemManager.g18;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.setToolTip("Candy Apple", Color.black);
   		  sk3.setToolTip("Cyrex", Color.black);
   		  sk4.setToolTip("Fade", Color.black);
   		  sk5.setToolTip("Ghoul", Color.black);
   		  sk6.setToolTip("Hornet", Color.black);
   		  sk7.setToolTip("Urban", Color.black);
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 28) {
    	  selectedGun = (ItemGun)ItemManager.deagle;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Cobalt", Color.black);
   		  sk3.setToolTip("Devi", Color.black);
   		  sk4.setToolTip("Gold", Color.black);
   		  sk5.setToolTip("Grin", Color.black);
   		  sk6.setToolTip("Mystic", Color.black);
   		  sk7.setToolTip("Navy", Color.black);
   		  sk8.setToolTip("Night Razer", Color.black);
   		  sk9.setToolTip("Watermelon", Color.black);
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 29) {
    	  selectedGun = (ItemGun)ItemManager.p250;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Arctic", Color.black);
   		  sk3.setToolTip("Beast", Color.black);
   		  sk4.setToolTip("Dune", Color.black);
   		  sk5.setToolTip("Haunted", Color.black);
   		  sk6.setToolTip("Irradiated", Color.black);
   		  sk7.setToolTip("See ya later", Color.black);
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 30) {
    	  selectedGun = (ItemGun)ItemManager.tec9;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Cherry", Color.black);
   		  sk3.setToolTip("Fuel", Color.black);
   		  sk4.setToolTip("Hornet", Color.black);
   		  sk5.setToolTip("Quakoronic", Color.black);
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 31) {
    	  selectedGun = (ItemGun)ItemManager.cz75;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Green", Color.black);
 		  sk3.drawButton = false;
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 32) {
    	  selectedGun = (ItemGun)ItemManager.fiveSeven;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.drawButton = false;
 		  sk3.drawButton = false;
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 33) {
    	  selectedGun = (ItemGun)ItemManager.r8;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Fade", Color.black);
 		  sk3.drawButton = false;
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 34) {
    	  selectedGun = (ItemGun)ItemManager.p90;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Arrow", Color.black);
   		  sk3.setToolTip("Grim", Color.black);
   		  sk4.setToolTip("Kraken", Color.black);
   		  sk5.setToolTip("Module", Color.black);
   		  sk6.setToolTip("Ruby", Color.black);
   		  sk7.setToolTip("Rust", Color.black);
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 35) {
    	  selectedGun = (ItemGun)ItemManager.mac10;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Chicken", Color.black);
   		  sk3.setToolTip("Fade", Color.black);
   		  sk4.setToolTip("Neon", Color.black);
   		  sk5.setToolTip("Race", Color.black);
   		  sk6.setToolTip("Violet", Color.black);
   		  sk7.drawButton = false;
		  sk8.drawButton = false;
		  sk9.drawButton = false;
		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 36) {
    	  selectedGun = (ItemGun)ItemManager.vector;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Arctic", Color.black);
   		  sk3.setToolTip("Circuit", Color.black);
   		  sk4.setToolTip("Extinct", Color.black);
   		  sk5.setToolTip("Slime", Color.black);
   		  sk6.setToolTip("Sunny", Color.black);
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 37) {
    	  selectedGun = (ItemGun)ItemManager.ump45;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Xeryc", Color.black);
 		  sk3.drawButton = false;
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 38) {
    	  selectedGun = (ItemGun)ItemManager.awp;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Asmo", Color.black);
   		  sk3.setToolTip("Beast", Color.black);
   		  sk4.setToolTip("Boom", Color.black);
   		  sk5.setToolTip("Dragon", Color.black);
   		  sk6.setToolTip("Forest", Color.black);
   		  sk7.setToolTip("Lightning", Color.black);
   		  sk8.setToolTip("Manowar", Color.black);
   		  sk9.setToolTip("Pumpkin", Color.black);
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 39) {
    	  selectedGun = (ItemGun)ItemManager.dragunov;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Elite", Color.black);
   		  sk3.setToolTip("Ghost", Color.black);
   		  sk4.setToolTip("Jungle", Color.black);
   		  sk5.setToolTip("Sky", Color.black);
   		  sk6.setToolTip("Wormgod", Color.black);
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 40) {
    	  selectedGun = (ItemGun)ItemManager.scar20;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Arctic", Color.black);
   		  sk3.setToolTip("Cyrex", Color.black);
   		  sk4.setToolTip("Elite", Color.black);
   		  sk5.setToolTip("Shock", Color.black);
   		  sk6.setToolTip("Sport", Color.black);
   		  sk7.setToolTip("Unicorn", Color.black);
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 41) {
    	  selectedGun = (ItemGun)ItemManager.ssg08;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
    	  sk2.drawButton = false;
 		  sk3.drawButton = false;
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 42) {
    	  selectedGun = (ItemGun)ItemManager.nova;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Infused", Color.black);
   		  sk3.setToolTip("Oxide Blaze", Color.black);
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 43) {
    	  selectedGun = (ItemGun)ItemManager.sawedoff;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Kraken", Color.black);
   		  sk3.setToolTip("Orange", Color.black);
   		  sk4.setToolTip("Wasteland Princess", Color.black);
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 44) {
    	  selectedGun = (ItemGun)ItemManager.mag7;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Bulldozer", Color.black);
   		  sk3.setToolTip("Royal Fire", Color.black);
 		  sk4.drawButton = false;
 		  sk5.drawButton = false;
 		  sk6.drawButton = false;
 		  sk7.drawButton = false;
 		  sk8.drawButton = false;
 		  sk9.drawButton = false;
 		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
      if (a.id == 45) {
    	  selectedGun = (ItemGun)ItemManager.m249;
    	  currentSkin = "";
    	  sk1.setToolTip("", Color.black);
   		  sk2.setToolTip("Heat Sync", Color.black);
   		  sk3.setToolTip("Neon", Color.black);
   		  sk4.drawButton = false;
   		  sk5.drawButton = false;
   		  sk6.drawButton = false;
   		  sk7.drawButton = false;
   		  sk8.drawButton = false;
   		  sk9.drawButton = false;
   		  sk10.drawButton = false;
          selectedID = (a.id - 20);
      }
   }

   public void updateScreen() {
      super.updateScreen();
      if (isRotate) {
    	  if (this.rotationY == 359F) this.rotationY = 0;
    	  else this.rotationY++;
      }
   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawBackground(par3);
      super.drawScreen(par1, par2, par3);
      int x = super.width / 2 - 173;
      int x1 = x + 107;
      int y1 = 41;
      int lineCount = 0;
      CCRenderHelper.drawRectWithShadow((double)(x1 + 7), (double)(y1 - 1), 238.0D, (double)(10 * 5), GuiCCMenu.menuTheme, 1.0F);
      CCRenderHelper.drawImage((double)(x + 118), 97.0D, GuiCCMenuInspect.background, 230.0D, 132.0D);
      if (this.selectedGun != null) {
    	 String textureName = this.currentSkin;
    	 ItemGun gunItem = selectedGun;
         ItemStack gunStack = new ItemStack(gunItem, 1);
         ItemGun.setGunSkin(gunStack, textureName);
         GL11.glPushMatrix();
         CCRenderHelper.renderSpecialItemStackInventoryWithRotation(gunStack, (double)(super.width / 2 + 60), 195.0D, 4.0D, 0.0F, this.rotationY);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         String select = "" + selectedGun.getUnlocalizedName().replaceAll("item.", "") + " - ";
         String select2 = this.currentSkin.equals("") ? "Обычный" : this.currentSkin;
    	 CCRenderHelper.drawTextWithOutline((select+select2).length() > 24 ? (select+select2).substring(0, 24) + "-" : select+select2, x + 3, 86, 0xFFFFFF);
    	 if ((select+select2).length() > 24) CCRenderHelper.drawTextWithOutline("-" + (select+select2).substring(24), x + 3, 94, 0xFFFFFF);
         GL11.glPopMatrix();
      }
      this.drawButtons(par1, par2, par3);
   }

   public void clearsk() {
	   sk1.setToolTip("", Color.black);
	   sk2.setToolTip("Asmo", Color.black);
	   sk3.setToolTip("Cakey", Color.black);
	   sk4.setToolTip("Cyrex", Color.black);
	   sk5.setToolTip("Desert", Color.black);
	   sk6.setToolTip("Evil Daimyo", Color.black);
	   sk7.setToolTip("Howl", Color.black);
	   sk8.setToolTip("Lurker", Color.black);
	   sk9.setToolTip("Waves", Color.black);
	   sk10.setToolTip("", Color.black);
	   sk1.drawButton = true;
	   sk2.drawButton = true;
	   sk3.drawButton = true;
	   sk4.drawButton = true;
	   sk5.drawButton = true;
	   sk6.drawButton = true;
	   sk7.drawButton = true;
	   sk8.drawButton = true;
	   sk9.drawButton = true;
	   sk10.drawButton = false;
	   this.selectedID = 1;
	   this.currentSkin = "";
	   selectedGun = (ItemGun)ItemManager.m4a1;
   }
   
   static {
      selectedGun = (ItemGun)ItemManager.m4a1;
      currentSkin = "";
      selectedID = 1;
      isRotate = false;
   }
}
